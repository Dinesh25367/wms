package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.LocationMapper;
import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.LocationBulkDTO;
import com.newage.wms.application.dto.requestdto.LocationRequestDTO;
import com.newage.wms.application.dto.requestdto.LocationRequestDTOList;
import com.newage.wms.application.dto.responsedto.LocationBulkResponseDTO;
import com.newage.wms.application.dto.responsedto.LocationResponseDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.application.dto.responsedto.ResponseError;
import com.newage.wms.entity.Location;
import com.newage.wms.entity.QDoorMaster;
import com.newage.wms.entity.QLocation;
import com.newage.wms.entity.WareHouseMaster;
import com.newage.wms.service.LocationService;
import com.newage.wms.service.WareHouseService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.functors.IfTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@RestController
@RequestMapping("/location")
@CrossOrigin("*")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private WareHouseService wareHouseService;

    /*
     * Method to fetch all Location with sort, filter and pagination
     */
    @ApiOperation(value = "Fetch all Location", notes = " Api is used to fetch all Location with sort, filter and pagination ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping
    public ResponseEntity<ResponseDTO> fetchAllLocation(@QuerydslPredicate(root = Location.class) Predicate predicate,
                                                        @PageableDefault(sort = {"id"},direction = Sort.Direction.ASC,size = 20) @Parameter(hidden = true) Pageable pageable,
                                                        @Parameter(hidden = true) String query){
        log.info("ENTRY - Fetch all Location with sort, filter and pagination");
        Page<Location> locationPage = locationService.findAll(predicate,pageable);
        if (query != null && !query.isEmpty() && !query.isBlank()) {
            List<Location> locationList = locationPage.getContent().stream()
                    .filter(location -> location.getMasterLocationId() != null
                            && location.getMasterLocationId().getLocationUid().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
            locationPage = new PageImpl<>(locationList, pageable, locationList.size());
        }
        Page<LocationResponseDTO> locationResponseDtoPage = locationMapper.convertLocationPageToLocationResponsePage(locationPage);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,locationResponseDtoPage,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to fetch all Location for autocomplete
     */
    @ApiOperation(value = "Fetch all Master Location", notes = " Api is used to fetch all Master Location")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping(value = "/masterLocation/autoComplete",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> fetchMasterLocationAutoComplete(@QuerydslPredicate(root = Location.class) Predicate predicate){
        log.info("ENTRY - Fetch all Master Location for autocomplete");
        Pageable pageable = PageRequest.of(0, 20, Sort.by("id").ascending());
        Iterable<Location> locationIterable = locationService.findAll(predicate,pageable);
        Iterable<LocationResponseDTO.MasterLocation> masterLocationIterable = locationMapper.convertEntityIterableToMasterLocationResponseIterable(locationIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,masterLocationIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to bulk save all Location
     */
    @ApiOperation(value = "Bulk save all Location", notes = " Api is used to bulk save all Location ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully created") })
    @PostMapping(value="/saveAll",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> bulkSaveAllLocation(@Valid @RequestBody LocationRequestDTOList locationRequestDtoList){
        log.info("ENTRY - Bulk save all Location");
        Set<String> combinationSet = new HashSet<>();                                                                   //Check if bulk save data has duplicate values(locationUid and warehouse code)
        Boolean isDuplicate = !locationRequestDtoList.getLocationBulkDtoList().stream()
                .map(locationBulkDTO -> locationBulkDTO.getLocationUid() + "-" + locationBulkDTO.getWareHouseCode())
                .anyMatch(combination -> !combinationSet.add(combination));
        if (!isDuplicate){
            throw new ServiceException(
                    ServiceErrors.DATA_HAS_DUPLICATE_VALUES.CODE,
                    ServiceErrors.DATA_HAS_DUPLICATE_VALUES.KEY);
        }
        DateAndTimeRequestDto dateAndTimeRequestDto = this.setDateAndTimeRequestDtoCreateBulk(locationRequestDtoList.getLocationBulkDtoList().get(0));
        LocationBulkResponseDTO locationBulkResponseDTO = locationMapper.convertRequestListToEntityList(locationRequestDtoList,dateAndTimeRequestDto);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.CREATED.value(), Boolean.FALSE,true,null);
        if (locationBulkResponseDTO.getLocationList() != null){                                                         //Save only if there are no errors
            locationService.saveAll(locationBulkResponseDTO.getLocationList());
            responseDto.setResult(true);
        }
        if (locationBulkResponseDTO.getErrorList() != null){
            List<String> errorList = locationBulkResponseDTO.getErrorList();
            ResponseError responseError = new ResponseError();
            responseError.setCode("MST-1225");
            responseError.setMessage(errorList);
            responseDto.setResult(null);
            responseDto.setError(responseError);
        }
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to create new Location
     */
    @ApiOperation(value = "Create Location", notes = " Api is used to create Location ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully created") })
    @PostMapping(value="/save",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> createLocation(@Valid @RequestBody LocationRequestDTO locationRequestDto){
        log.info("ENTRY - Create new Location");
        String locationUidUpperCase = locationRequestDto.getLocationUid().toUpperCase();
        locationRequestDto.setLocationUid(locationUidUpperCase);
        locationAlreadyExistSave(locationRequestDto.getLocationUid(),locationRequestDto.getWareHouse().getId());                                                                   //validate master location
        if (locationRequestDto.getIsBinLocation() != null && locationRequestDto.getIsBinLocation().equals("Yes")){
            validateMasterLocationSaveAndUpdate(locationRequestDto);
        }
        else {locationRequestDto.setMasterLocation(null);}
        DateAndTimeRequestDto dateAndTimeRequestDto = this.setDateAndTimeRequestDtoCreate(locationRequestDto);
        Location location = locationMapper.convertRequestDtoToEntity(locationRequestDto,dateAndTimeRequestDto);
        validateForRackTypeStorage(location);
        location = locationService.saveLocation(location);
        LocationResponseDTO locationResponseDto = locationMapper.convertEntitytoResponseDto(location);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.CREATED.value(), Boolean.TRUE,locationResponseDto,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to update Location by id
     */
    @ApiOperation(value = "Update Location", notes = " Api is used to update Location ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated") })
    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> updateLocationById(@Valid @RequestBody LocationRequestDTO locationRequestDto,
                                                          @PathVariable ("id") Long id){
        log.info("ENTRY - Update Location by Id");
        String locationUidUpperCase = locationRequestDto.getLocationUid().toUpperCase();
        locationRequestDto.setLocationUid(locationUidUpperCase);
        locationAlreadyExistUpdate(locationRequestDto.getLocationUid(),locationRequestDto.getWareHouse().getId(),id);
        Location location = locationService.getLocationById(id);                                                        //validate master location
        if (locationRequestDto.getIsBinLocation() != null && locationRequestDto.getIsBinLocation().equals("Yes")){
            validateMasterLocationSaveAndUpdate(locationRequestDto);
        }else {
            locationRequestDto.setMasterLocation(null);
            location.setMasterLocationId(null);
        }
        DateAndTimeRequestDto dateAndTimeRequestDto = setDateAndTimeRequestDtoUpdate(locationRequestDto,location);
        locationMapper.convertUpdateRequestToEntity(locationRequestDto,location,dateAndTimeRequestDto);
        validateForRackTypeStorage(location);
        location = locationService.updateLocation(location);
        LocationResponseDTO locationResponseDto = locationMapper.convertEntitytoResponseDto(location);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,locationResponseDto,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to fetch all Location for autocomplete
     */
    @ApiOperation(value = "Fetch all Master Location", notes = " Api is used to fetch all Master Location")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping(value = "/autoComplete",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> fetchMLocationAutoComplete(@RequestParam(name = "query",required = false) String query,
                                                                  @RequestParam(name = "status",required = false) String status,
                                                                  @RequestParam(name = "branchId",required = false) Long branchId,
                                                                  @RequestParam(name = "warehouseId",required = false) Long warehouseId,
                                                                  @RequestParam(name = "isCreate",required = false) String isCreate,
                                                                  @RequestParam(name = "isPutAway",required = false)String isPutAway){
        log.info("ENTRY - Fetch all Master Location for autocomplete");
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QLocation.location.id.isNotNull());
        if (query != null && !query.isEmpty() && !query.isBlank()){
            predicates.add(QLocation.location.locationUid.containsIgnoreCase(query));
        }
        if (branchId != null){
            predicates.add(QLocation.location.branchMaster.id.eq(branchId));
        }
        if (warehouseId != null){
            predicates.add(QLocation.location.wareHouseMaster.id.eq(warehouseId));
        }
        if (status != null && !status.isEmpty() && !status.isBlank()){
            predicates.add(QLocation.location.status.eq(status));
        }
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("id").ascending());
        Iterable<Location> locationIterable = locationService.findAll(predicateAll,pageable);
        Iterable<Location> locationIterable1;
        if (isPutAway != null && isPutAway.equalsIgnoreCase("yes")) {
            locationIterable1 = StreamSupport.stream(locationIterable.spliterator(), false)
                    .filter(location -> !location.getLocTypeMaster().getType().equalsIgnoreCase("DOCK"))
                    .collect(Collectors.toList());
        } else {
            locationIterable1 = locationIterable;
        }
        Iterable<LocationResponseDTO.MasterLocation> masterLocationIterable = locationMapper.convertEntityIterableToMasterLocationResponseIterable(locationIterable1);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,masterLocationIterable,null);
        if (isCreate != null && isCreate.toLowerCase().equals("yes")){
            responseDto.setResult(new ArrayList<>());
        }
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to Check Unique
     */
    @ApiOperation(value = "Check Unique", notes = " Api Check Unique")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping(value = "/codeCheck",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> checkUniqueSave(@RequestParam(name = "locationUid") String locationUid,
                                                       @RequestParam(name = "warehouseId") Long warehouseId,
                                                       @RequestParam(name = "id",required = false) Long id){
        log.info("ENTRY - Check Unique");
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,true,null);
        try {
            if (id != null && !id.toString().isEmpty() && !id.toString().isBlank()){
                locationAlreadyExistUpdate(locationUid,warehouseId.toString(),id);
            }else {
                locationAlreadyExistSave(locationUid,warehouseId.toString());
            }
        }catch (ServiceException serviceException){
            responseDto.setResult(false);
        }
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to fetch Location by id
     */
    @ApiOperation(value = "Fetch Location by Id", notes = " Api is used to fetch Location by Id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> fetchLocationById(@PathVariable("id") Long id){
        log.info("ENTRY - Fetch Location by Id");
        LocationResponseDTO locationResponseDto = locationMapper.convertEntitytoResponseDto(locationService.getLocationById(id));
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,locationResponseDto,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to validate for Rack type Storage
     */
    private void validateForRackTypeStorage(Location location) {
        log.info("ENTRY - validate for Rack type Storage");
        if (location.getStorageTypeMaster() != null && location.getStorageTypeMaster().getCode().equals("RACK")) {
            if (location.getAisleMaster() == null){
                throw new ServiceException(ServiceErrors.AISLE_SHOULD_BE_MANDATORY_FOR_RACK_TYPE_STORAGE.CODE,
                        ServiceErrors.AISLE_SHOULD_BE_MANDATORY_FOR_RACK_TYPE_STORAGE.KEY);
            }
            if (location.getRackMaster() == null){
                throw new ServiceException(ServiceErrors.RACK_SHOULD_BE_MANDATORY_FOR_RACK_TYPE_STORAGE.CODE,
                        ServiceErrors.RACK_SHOULD_BE_MANDATORY_FOR_RACK_TYPE_STORAGE.KEY);
            }
            if (location.getColumnCode() == null || location.getColumnCode().isEmpty() || location.getColumnCode().isBlank()){
                throw new ServiceException(ServiceErrors.COLUMN_CODE_SHOULD_BE_MANDATORY_FOR_RACK_TYPE_STORAGE.CODE,
                        ServiceErrors.COLUMN_CODE_SHOULD_BE_MANDATORY_FOR_RACK_TYPE_STORAGE.KEY);
            }
        }
        log.info("EXIT");
    }

    /*
     * Method to validate Master Location in Save
     */
    private void validateMasterLocationSaveAndUpdate(LocationRequestDTO locationRequestDto) {
        if (locationRequestDto.getMasterLocation() != null){
            Location masterLocation = locationService.getLocationById(Long.parseLong(locationRequestDto.getMasterLocation().getId()));
            String status = masterLocation.getStatus();
            Character isBinLocation = masterLocation.getIsBinLocation();
            if(status.equals("Inactive") && isBinLocation.toString().equals("N")){
                if (Double.parseDouble(masterLocation.getVolume()) < Double.parseDouble(locationRequestDto.getVolume())){
                    throw new ServiceException(
                            ServiceErrors.BIN_LOCATION_SHOULD_HAVE_LESSER_VOLUME_THAN_MASTER_LOCATION.CODE,
                            ServiceErrors.BIN_LOCATION_SHOULD_HAVE_LESSER_VOLUME_THAN_MASTER_LOCATION.KEY);
                }
            }
            else {
                throw new ServiceException(
                        ServiceErrors.MASTER_LOCATION_MUST_BE_INACTIVE_AND_IT_SHOULD_NOT_BIN_LOCATION.CODE,
                        ServiceErrors.MASTER_LOCATION_MUST_BE_INACTIVE_AND_IT_SHOULD_NOT_BIN_LOCATION.KEY);
            }
        }
        else {
            throw new ServiceException(
                    ServiceErrors.MASTER_LOCATION_MUST_NOT_BE_EMPTY_FOR_BIN_LOCATION.CODE,
                    ServiceErrors.MASTER_LOCATION_MUST_NOT_BE_EMPTY_FOR_BIN_LOCATION.KEY);
        }
        log.info("EXIT");
    }

    /*
     * Method to check if Location already exist in save
     */
    private void locationAlreadyExistSave(String locationUid, String warehouseId) {
        log.info("ENTRY - Check if Location already exist in save");
        WareHouseMaster wareHouseMaster = wareHouseService.getWareHouseById(Long.parseLong(warehouseId));
        Boolean locationExists = locationService.findAll()
                .stream()
                .anyMatch(location ->
                        location.getLocationUid().equals(locationUid.toUpperCase()) &&
                                location.getWareHouseMaster().getCode().equals(wareHouseMaster.getCode())
                );
        if (locationExists){
            throw new ServiceException(
                    ServiceErrors.LOCATION_ALREADY_EXISTS_FOR_THIS_WAREHOUSE.CODE,
                    ServiceErrors.LOCATION_ALREADY_EXISTS_FOR_THIS_WAREHOUSE.KEY);
        }
        log.info("EXIT");
    }

    /*
     * Method to check if Location already exist in update
     */
    private void locationAlreadyExistUpdate(String locationUid, String warehouseId, Long id) {
        log.info("ENTRY - Check if Location already exist in update");
        WareHouseMaster wareHouseMaster = wareHouseService.getWareHouseById(Long.parseLong(warehouseId));
        Boolean locationExists = locationService.findAll()
                .stream()
                .anyMatch(location ->
                        location.getLocationUid().equals(locationUid.toUpperCase()) &&
                                location.getWareHouseMaster().getCode().equals(wareHouseMaster.getCode()) &&
                                !location.getId().equals(id)
                );
        if (locationExists){
            throw new ServiceException(
                    ServiceErrors.LOCATION_ALREADY_EXISTS_FOR_THIS_WAREHOUSE.CODE,
                    ServiceErrors.LOCATION_ALREADY_EXISTS_FOR_THIS_WAREHOUSE.KEY);
        }
        log.info("EXIT");
    }

    /*
     * Method to set Date, Time and Version in Create
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoCreate(LocationRequestDTO locationRequestDto){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Create");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(locationRequestDto.getUser());
        dateAndTimeRequestDto.setCreatedDate(new Date());
        dateAndTimeRequestDto.setLastModifiedBy(locationRequestDto.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(1L);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

    /*
     * Method to set Date, Time and Version in Update
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoUpdate(LocationRequestDTO locationRequestDto, Location location){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Update");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(location.getCreatedBy());
        dateAndTimeRequestDto.setCreatedDate(location.getCreatedDate());
        dateAndTimeRequestDto.setLastModifiedBy(locationRequestDto.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(location.getVersion() + 1);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

    /*
     * Method to set Date, Time and Version in Bulk create
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoCreateBulk(LocationBulkDTO locationBulkDTO){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in bulk create");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(locationBulkDTO.getUser());
        dateAndTimeRequestDto.setCreatedDate(new Date());
        dateAndTimeRequestDto.setLastModifiedBy(locationBulkDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(1L);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

}
