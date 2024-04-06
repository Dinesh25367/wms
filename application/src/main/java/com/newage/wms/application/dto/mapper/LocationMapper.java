package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.LocationBulkDTO;
import com.newage.wms.application.dto.requestdto.LocationRequestDTO;
import com.newage.wms.application.dto.requestdto.LocationRequestDTOList;
import com.newage.wms.application.dto.responsedto.BulkResponseDTO;
import com.newage.wms.application.dto.responsedto.LocationBulkResponseDTO;
import com.newage.wms.application.dto.responsedto.LocationResponseDTO;
import com.newage.wms.entity.Location;
import com.newage.wms.entity.UomMaster;
import com.newage.wms.entity.WareHouseMaster;
import com.newage.wms.service.*;
import com.newage.wms.service.exception.ServiceException;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@Component
@Getter
public class LocationMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BranchMasterService branchMasterService;

    @Autowired
    private CompanyMasterService companyMasterService;

    @Autowired
    private GroupCompanyMasterService groupCompanyMasterService;

    @Autowired
    private WareHouseService wareHouseService;

    @Autowired
    private ZoneMasterWMSService zoneMasterWMSService;

    @Autowired
    private AisleMasterService aisleMasterService;

    @Autowired
    private RackMasterService rackMasterService;

    @Autowired
    private StorageAreaMasterService storageAreaMasterService;

    @Autowired
    private StorageTypeMasterService storageTypeMasterService;

    @Autowired
    private LocTypeMasterService locTypeMasterService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private UomMasterService uomMasterService;

    private Integer rowNo1;

    private Integer rowNo2;

    private List<BulkResponseDTO> bulkResponseDTOList;

    private List<String> errorList;

    private List<Location> locationList;

    private Boolean shouldReturnNullSave;

    private Boolean shouldReturnNullUpdate;


    /*
     * Method to convert Location Page to LocationResponse Page
     * @Return Location Response Page
     */
    public Page<LocationResponseDTO> convertLocationPageToLocationResponsePage(Page<Location> locationPage){
        log.info("ENTRY - Location Page to LocationResponse Page mapper");
        List<LocationResponseDTO> locationResponseDtoList = locationPage.getContent()
                .stream()
                .map(this::convertEntitytoResponseDto)
                .collect(Collectors.toList());
        log.info("EXIT");
        return new PageImpl<>(locationResponseDtoList,locationPage.getPageable(),locationPage.getTotalElements());
    }

    /*
     * Method to convert LocationRequest iterable to Location iterable
     * @Return Iterable<LocationResponseDto.MasterLocation>
     */
    public Iterable<LocationResponseDTO.MasterLocation> convertEntityIterableToMasterLocationResponseIterable(Iterable<Location> locationIterable) {
        log.info("ENTRY-EXIT - LocationRequest Iterable to LocationResponseDto.MasterLocation Iterable Page mapper");
        return StreamSupport.stream(locationIterable.spliterator(), false)
                .map(this::convertEntityToMasterLocationResponse)
                .collect(Collectors.toList());
    }

    /*
     * Method to convert LocationRequest list to Location list
     * @Return Location Response Page
     */
    public LocationBulkResponseDTO convertRequestListToEntityList(LocationRequestDTOList locationRequestDtoList, DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - LocationBulKRequest list to Location list mapper");
        rowNo1= 1;
        rowNo2 = 2;
        bulkResponseDTOList = new ArrayList<BulkResponseDTO>();
        errorList = new ArrayList<>();
        locationList = locationService.findAll();
        List<Location> locationEntityList = locationRequestDtoList.getLocationBulkDtoList().stream()
                .map(locationRequestDto -> {
                    if (locationAlreadyExist(locationRequestDto)) {
                        return convertBulkRequestDtoToEntityUpdate(locationRequestDto, dateAndTimeRequestDto);
                    } else {
                        return convertBulkRequestDtoToEntitySave(locationRequestDto, dateAndTimeRequestDto);
                    }
                })
                .collect(Collectors.toList());
        LocationBulkResponseDTO locationBulkResponseDTO = new LocationBulkResponseDTO();
        locationEntityList.removeIf(Objects::isNull);
        if (errorList.isEmpty()){
            locationBulkResponseDTO.setLocationList(locationEntityList);
            locationBulkResponseDTO.setErrorList(null);
        }else {
            locationBulkResponseDTO.setLocationList(null);
            locationBulkResponseDTO.setErrorList(errorList);
        }
        log.info("EXIT");
        return locationBulkResponseDTO;
    }

    /*
     * Method to convert LocationRequestDto to Location entity
     * @Return Location
     */
    public Location convertRequestDtoToEntity(LocationRequestDTO locationRequestDto, DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - LocationRequestDto to Location mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        Location location = modelMapper.map(locationRequestDto,Location.class);
        modelMapper.map(dateAndTimeRequestDto,location);                                                                // map date, time and version
        location.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(locationRequestDto.getGroupCompanyId())));        //Set unmapped objects
        location.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(locationRequestDto.getCompanyId())));
        location.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(locationRequestDto.getBranchId())));
        if (locationRequestDto.getWareHouse() != null){
            location.setWareHouseMaster(wareHouseService.getWareHouseById(Long.parseLong(locationRequestDto.getWareHouse().getId())));
        }
        if (locationRequestDto.getZone() != null){
            location.setZoneMaster(zoneMasterWMSService.getZoneMasterWMSById(Long.parseLong(locationRequestDto.getZone().getId())));
        }
        if (locationRequestDto.getAisle() != null){
            location.setAisleMaster(aisleMasterService.getAisleById(Long.parseLong(locationRequestDto.getAisle().getId())));
        }
        if (locationRequestDto.getRack() != null){
            location.setRackMaster(rackMasterService.getRackMasterById(Long.parseLong(locationRequestDto.getRack().getId())));
        }
        if (locationRequestDto.getStorageArea() != null){
            location.setStorageAreaMaster(storageAreaMasterService.getStorageAreaMasterById(Long.parseLong(locationRequestDto.getStorageArea().getId())));
        }
        if (locationRequestDto.getStorageType() != null){
            location.setStorageTypeMaster(storageTypeMasterService.getStorageTypeMasterById(Long.parseLong(locationRequestDto.getStorageType().getId())));
        }
        if (locationRequestDto.getLocType() != null){
            location.setLocTypeMaster(locTypeMasterService.getLocTypeById(Long.parseLong(locationRequestDto.getLocType().getId())));
        }
        if (locationRequestDto.getMasterLocation() != null){
            Location masterLocation = locationService.getLocationById(Long.valueOf(locationRequestDto.getMasterLocation().getId()));
            location.setMasterLocationId(masterLocation);
        }
        if (locationRequestDto.getLocationHandlingUom() != null){
            UomMaster uomMaster = uomMasterService.getUomById(Long.valueOf(locationRequestDto.getLocationHandlingUom().getId()));
            location.setLocationHandlingUomMaster(uomMaster);
        }
        log.info("EXIT");
        return location;
    }

    /*
     * Method to convert update LocationRequestDto to Location entity
     * @Return Location
     */
    public void convertUpdateRequestToEntity(LocationRequestDTO locationRequestDto, Location location,DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - update LocationRequestDto to Location mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        modelMapper.map(locationRequestDto,location);
        modelMapper.map(dateAndTimeRequestDto,location);                                                                // map date, time and version
        location.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(locationRequestDto.getGroupCompanyId())));        //Set unmapped objects
        location.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(locationRequestDto.getCompanyId())));
        location.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(locationRequestDto.getBranchId())));
        location.setWareHouseMaster(wareHouseService.getWareHouseById(Long.parseLong(locationRequestDto.getWareHouse().getId())));
        if (locationRequestDto.getZone() != null){
            location.setZoneMaster(zoneMasterWMSService.getZoneMasterWMSById(Long.parseLong(locationRequestDto.getZone().getId())));
        }else {location.setZoneMaster(null);}
        if (locationRequestDto.getAisle() != null){
            location.setAisleMaster(aisleMasterService.getAisleById(Long.parseLong(locationRequestDto.getAisle().getId())));
        }else {location.setAisleMaster(null);}
        if (locationRequestDto.getRack() != null){
            location.setRackMaster(rackMasterService.getRackMasterById(Long.parseLong(locationRequestDto.getRack().getId())));
        }else {location.setRackMaster(null);}
        if (locationRequestDto.getStorageArea() != null){
            location.setStorageAreaMaster(storageAreaMasterService.getStorageAreaMasterById(Long.parseLong(locationRequestDto.getStorageArea().getId())));
        }else {location.setStorageAreaMaster(null);}
        if (locationRequestDto.getStorageType() != null){
            location.setStorageTypeMaster(storageTypeMasterService.getStorageTypeMasterById(Long.parseLong(locationRequestDto.getStorageType().getId())));
        }else {location.setStorageTypeMaster(null);}
        if (locationRequestDto.getLocType() != null){
            location.setLocTypeMaster(locTypeMasterService.getLocTypeById(Long.parseLong(locationRequestDto.getLocType().getId())));
        }else {location.setLocTypeMaster(null);}
        if (locationRequestDto.getMasterLocation() != null){
            Location masterLocation = locationService.getLocationById(Long.valueOf(locationRequestDto.getMasterLocation().getId()));
            location.setMasterLocationId(masterLocation);
        }
        if (locationRequestDto.getLocationHandlingUom() != null){
            UomMaster uomMaster = uomMasterService.getUomById(Long.valueOf(locationRequestDto.getLocationHandlingUom().getId()));
            location.setLocationHandlingUomMaster(uomMaster);
        }else {location.setLocationHandlingUomMaster(null);}
        log.info("EXIT");
    }

    /*
     * Method to convert Bulk Request to Entity save
     * @Return Location
     */
    private Location convertBulkRequestDtoToEntitySave(LocationBulkDTO locationRequestDto, DateAndTimeRequestDto dateAndTimeRequestDto) {
        log.info("ENTRY - Bulk Request to Entity mapper save");
        shouldReturnNullSave = false;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        Location location = modelMapper.map(locationRequestDto,Location.class);
        modelMapper.map(dateAndTimeRequestDto,location);
        setBranchCompanyGroupCompany(location,locationRequestDto);                                                                                                               //set Warehouse
        setWarehouseZone(location,locationRequestDto);                                                                                                             //set Aisle
        setAisle(location,locationRequestDto);                                                                                                             //set Rack
        setRack(location,locationRequestDto);                                                                                                             //set StorageArea
        setStorageArea(location,locationRequestDto);
        setStorageType(location,locationRequestDto);
                                                                                                                      //set LocType
        if (locationRequestDto.getLocTypeCode() != null && !locationRequestDto.getLocTypeCode().isEmpty() && !locationRequestDto.getLocTypeCode().isBlank()){
            location.setLocTypeMaster(locTypeMasterService.getLocTypeByCode(locationRequestDto.getLocTypeCode()));
            if (location.getLocTypeMaster() == null){
                errorList.add("Row " + rowNo1 + " Loc Type code does not exist");
                shouldReturnNullSave = true;
            }
        }                                                                                                               //set Master location
        if (locationRequestDto.getLocationHandlingUomCode() != null && !locationRequestDto.getLocationHandlingUomCode().isEmpty() && !locationRequestDto.getLocationHandlingUomCode().isBlank()){
            UomMaster uomMaster = uomMasterService.getUomByCode(locationRequestDto.getLocationHandlingUomCode());
            location.setLocationHandlingUomMaster(uomMaster);
            if (location.getLocationHandlingUomMaster() == null){
                errorList.add("Row " + rowNo1 + " Location Uom code does not exist");
                shouldReturnNullSave = true;
            }
        }                                                                                                               //Return null if any of the above validation fails
        if (locationRequestDto.getIsBinLocation().equals("Yes")){
            errorList.add("Row " + rowNo1 + " Cannot add bin location in bulk save. ");
        }
        log.info("EXIT");
        if (shouldReturnNullSave){
            return null;
        }else {
            return location;
        }
    }

    /*
     * Method to convert Bulk Request to Entity update
     * @Return Location
     */
    private Location convertBulkRequestDtoToEntityUpdate(LocationBulkDTO locationRequestDto, DateAndTimeRequestDto dateAndTimeRequestDto) {
        log.info("ENTRY - Bulk Request to Entity mapper update");
        shouldReturnNullUpdate = false;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        String locationUid = locationRequestDto.getLocationUid();
        String wareHouseCode = locationRequestDto.getWareHouseCode();
        Location location = null;                                                                                       //Get the location that should be updated
        for (Location locationObject : locationList) {
            if (locationUid.equals(locationObject.getLocationUid()) && wareHouseCode.equals(locationObject.getWareHouseMaster().getCode())) {
                location = locationObject;
                break;                                                                                                  //Exit the loop once a matching location is found
            }
        }
        if (location != null) {
            modelMapper.map(locationRequestDto, location);
            modelMapper.map(dateAndTimeRequestDto, location);
            setBranchCompanyGroupCompany(location,locationRequestDto);                                                                                                        //set Warehouse
            setWarehouseUpdate(location,locationRequestDto);                                                                                                        //set Zone
            setZoneUpdate(location,locationRequestDto);                                                                    //set Aisle
            setAisleUpdate(location,locationRequestDto);                                                                //set Rack
            setRackUpdate(location,locationRequestDto);                                                                      //set Storage Area
            setStorageAreaUpdate(location,locationRequestDto);                                                            //set Storage Type
            setStorageTypeUpdate(location,locationRequestDto);                                                        //set Loc Type
            setLocTypeUpdate(location,locationRequestDto);                                                              //set Uom
            setLocationHandlingUomUpdate(location,locationRequestDto);
            if (locationRequestDto.getIsBinLocation().equals("Yes")){
                errorList.add("Row " + rowNo1 + " Cannot add bin location in bulk save. ");
            }
        }else {
            errorList.add("Row " + rowNo1 + " Cannot get Location from given locationUid and Warehouse Code");
            shouldReturnNullUpdate = true;
        }                                                                                                               //Return null if any of the above validation fails
        log.info("EXIT");
        if (shouldReturnNullUpdate){
            return null;
        }else {
            return location;
        }
    }

    private Location setWarehouseUpdate(Location location, LocationBulkDTO locationRequestDto){
        if (locationRequestDto.getWareHouseCode() != null && !locationRequestDto.getWareHouseCode().isEmpty() && !locationRequestDto.getWareHouseCode().isBlank()) {
            location.setWareHouseMaster(wareHouseService.getWareHouseByCode(locationRequestDto.getWareHouseCode()));
            if (location.getWareHouseMaster() == null) {
                errorList.add("Row " + rowNo1 + " WarehouseCode does not exist");
                shouldReturnNullUpdate = true;
            }
        }
        return location;
    }

    private Location setZoneUpdate(Location location, LocationBulkDTO locationRequestDto){
        if (locationRequestDto.getZoneCode() != null && !locationRequestDto.getZoneCode().isEmpty() && !locationRequestDto.getZoneCode().isBlank()) {
            location.setZoneMaster(zoneMasterWMSService.getZoneMasterWMSByCode(locationRequestDto.getZoneCode()));
            if (location.getZoneMaster() == null) {
                errorList.add("Row " + rowNo1 + " ZoneCode does not exist");
                shouldReturnNullUpdate = true;
            }else {                                                                                                 //check if Zone exist in the Warehouse
                if (location.getWareHouseMaster() != null && location.getZoneMaster().getWareHouseMaster() != null &&
                        !location.getWareHouseMaster().getCode().equals(location.getZoneMaster().getWareHouseMaster().getCode())){
                    errorList.add("Row " + rowNo1 + " Zone does not exist for the given Warehouse");
                    shouldReturnNullUpdate = true;
                }
            }
        }else {location.setZoneMaster(null);}
        return location;
    }

    private Location setAisleUpdate(Location location, LocationBulkDTO locationRequestDto){
        if (locationRequestDto.getAisleCode() != null && !locationRequestDto.getAisleCode().isEmpty() && !locationRequestDto.getAisleCode().isBlank()) {
            location.setAisleMaster(aisleMasterService.getAisleByCode(locationRequestDto.getAisleCode()));
            if (location.getAisleMaster() == null) {
                errorList.add("Row " + rowNo1 + " Aisle code does not exist");
                shouldReturnNullUpdate = true;
            }else {                                                                                                 //check if Aisle exist in the Zone
                if (location.getZoneMaster() != null && location.getAisleMaster().getZoneMaster() != null &&
                        !location.getZoneMaster().getCode().equals(location.getAisleMaster().getZoneMaster().getCode())){
                    errorList.add("Row " + rowNo1 + " Aisle does not exist for the given Zone");
                    shouldReturnNullUpdate = true;
                }
            }
        }else {location.setAisleMaster(null);}
        return location;
    }

    private Location setRackUpdate(Location location, LocationBulkDTO locationRequestDto){
        if (locationRequestDto.getRackCode() != null && !locationRequestDto.getRackCode().isEmpty() && !locationRequestDto.getRackCode().isBlank()) {
            location.setRackMaster(rackMasterService.getRackMasterByCode(locationRequestDto.getRackCode()));
            if (location.getRackMaster() == null) {
                errorList.add("Row " + rowNo1 + " Rack code does not exist");
                shouldReturnNullUpdate = true;
            }else {                                                                                                 //check if Rack exist in the Aisle
                if (location.getAisleMaster() != null && location.getRackMaster().getAisleMaster() != null &&
                        !location.getAisleMaster().getCode().equals(location.getRackMaster().getAisleMaster().getCode())){
                    errorList.add("Row " + rowNo1 + " Rack does not exist for the given Aisle");
                    shouldReturnNullUpdate = true;
                }
            }
        }else {location.setRackMaster(null);}
        return location;
    }

    private Location setStorageAreaUpdate(Location location, LocationBulkDTO locationRequestDto){
        if (locationRequestDto.getStorageAreaCode() != null && !locationRequestDto.getStorageAreaCode().isEmpty() && !locationRequestDto.getStorageAreaCode().isBlank()) {
            location.setStorageAreaMaster(storageAreaMasterService.getStorageAreaMasterByCode(locationRequestDto.getStorageAreaCode()));
            if (location.getStorageAreaMaster() == null) {
                errorList.add("Row " + rowNo1 + " Storage Area code does not exist");
                shouldReturnNullUpdate = true;
            }else {                                                                                                 //check if Storage Area exist in the Zone, Aisle and Rack
                String storageAreaCode = getStorageAreaCode(location);
                if (storageAreaCode != null && !storageAreaCode.isBlank() && !storageAreaCode.isEmpty() &&
                        !location.getStorageAreaMaster().getCode().equals(storageAreaCode)){
                    errorList.add("Row " + rowNo1 + " Storage Area code does not exist for the given Zone, Aisle and Rack");
                    shouldReturnNullUpdate = true;
                }
            }
        }else {location.setStorageAreaMaster(null);}
        return location;
    }

    private Location setStorageTypeUpdate(Location location, LocationBulkDTO locationRequestDto){
        if (locationRequestDto.getStorageTypeCode() != null && !locationRequestDto.getStorageTypeCode().isEmpty() && !locationRequestDto.getStorageTypeCode().isBlank()) {
            location.setStorageTypeMaster(storageTypeMasterService.getStorageTypeMasterByCode(locationRequestDto.getStorageTypeCode()));
            if (location.getStorageTypeMaster() == null) {
                errorList.add("Row " + rowNo1 + " Storage Type code does not exist");
                shouldReturnNullUpdate = true;
            }
            else {                                                                                                  //check if Storage Type exist in the Zone, Aisle and Rack
                String storageTypeCode = getStorageTypeCode(location);
                if (storageTypeCode != null && !storageTypeCode.isBlank() && !storageTypeCode.isEmpty() &&
                        !location.getStorageTypeMaster().getCode().equals(storageTypeCode)){
                    errorList.add("Row " + rowNo1 + " Storage Type code does not exist for the given Zone, Aisle and Rack");
                    shouldReturnNullUpdate = true;
                }
            }
        }else {location.setStorageTypeMaster(null);}
        return location;
    }
    private Location setLocTypeUpdate(Location location, LocationBulkDTO locationRequestDto){
        if (locationRequestDto.getLocTypeCode() != null && !locationRequestDto.getLocTypeCode().isEmpty() && !locationRequestDto.getLocTypeCode().isBlank()) {
            location.setLocTypeMaster(locTypeMasterService.getLocTypeByCode(locationRequestDto.getLocTypeCode()));
            if (location.getLocTypeMaster() == null) {
                errorList.add("Row " + rowNo1 + " Loc Type code does not exist");
                shouldReturnNullUpdate = true;
            }
        }else {location.setLocTypeMaster(null);}
        return location;
    }

    private Location setLocationHandlingUomUpdate(Location location, LocationBulkDTO locationRequestDto){
        if (locationRequestDto.getLocationHandlingUomCode() != null && !locationRequestDto.getLocationHandlingUomCode().isEmpty() && !locationRequestDto.getLocationHandlingUomCode().isBlank()) {
            UomMaster uomMaster = uomMasterService.getUomByCode(locationRequestDto.getLocationHandlingUomCode());
            location.setLocationHandlingUomMaster(uomMaster);
            if (location.getLocationHandlingUomMaster() == null) {
                errorList.add("Row " + rowNo1 + " Location Uom code does not exist");
                shouldReturnNullUpdate = true;
            }
        }else {location.setLocationHandlingUomMaster(null);}
        return location;
    }

    /*
     * Method to convert Location entity to LocationResponseDto
     * @Return LocationResponseDto
     */
    public LocationResponseDTO convertEntitytoResponseDto(Location location){
        log.info("ENTRY - Location to LocationResponseDto mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        LocationResponseDTO locationResponseDto = modelMapper.map(location, LocationResponseDTO.class);
        String isBinLocation = characterToString(location.getIsBinLocation());
        String mixedSkuAllowed = characterToString(location.getMixedSkuAllowed());
        String replenishmentAllowed = characterToString(location.getReplenishmentAllowed());
        locationResponseDto.setIsBinLocation(isBinLocation);
        locationResponseDto.setMixedSkuAllowed(mixedSkuAllowed);
        locationResponseDto.setReplenishmentAllowed(replenishmentAllowed);                                              //Set GroupCompanyDTO
        LocationResponseDTO.GroupCompanyDTO groupCompanyDTO= new LocationResponseDTO.GroupCompanyDTO();
        if(location.getGroupCompanyMaster()!=null){
            modelMapper.map(location.getGroupCompanyMaster(),groupCompanyDTO);
            locationResponseDto.setGroupCompany(groupCompanyDTO);
        }                                                                                                               //Set CompanyDTO
        LocationResponseDTO.CompanyDTO companyDTO= new LocationResponseDTO.CompanyDTO();
        if(location.getCompanyMaster()!=null){
            modelMapper.map(location.getCompanyMaster(),companyDTO);
            locationResponseDto.setCompany(companyDTO);
        }                                                                                                               //Set BranchDTO
        LocationResponseDTO.BranchDTO branchDTO=new LocationResponseDTO.BranchDTO();
        if(location.getBranchMaster()!=null){
            modelMapper.map(location.getBranchMaster(),branchDTO);
            locationResponseDto.setBranch(branchDTO);
        }                                                                                                               //Set WareHouseDTO
        LocationResponseDTO.WareHouseDTO wareHouseDTO=new LocationResponseDTO.WareHouseDTO();
        if(location.getWareHouseMaster()!=null){
            modelMapper.map(location.getWareHouseMaster(),wareHouseDTO);
            locationResponseDto.setWareHouse(wareHouseDTO);
        }
        locationResponseDto.setZone(getZoneDTO(location));
        locationResponseDto.setRack(getRackDTO(location));
        locationResponseDto.setAisle(getAisleDTO(location));
        LocationResponseDTO.LocTypeDTO locTypeDTO=new LocationResponseDTO.LocTypeDTO();
        if (location.getLocTypeMaster() != null){modelMapper.map(location.getLocTypeMaster(),locTypeDTO);}
        if (locTypeDTO.getId() != null){locationResponseDto.setLocType(locTypeDTO);}                                    //Set StorageMasterDTO
        LocationResponseDTO.StorageTypeDTO storageTypeDTO=new LocationResponseDTO.StorageTypeDTO();
        if(location.getStorageTypeMaster()!=null){ modelMapper.map(location.getStorageTypeMaster(),storageTypeDTO);}
        if (storageTypeDTO.getId() != null){locationResponseDto.setStorageType(storageTypeDTO);}                        //Set StorageAreaDTO
        LocationResponseDTO.StorageAreaDTO storageAreaDTO=new LocationResponseDTO.StorageAreaDTO();
        if(location.getStorageTypeMaster()!=null){ modelMapper.map(location.getStorageAreaMaster(),storageAreaDTO); }
        if (storageTypeDTO.getId() != null){locationResponseDto.setStorageArea(storageAreaDTO);}                        //Set MasterLocationDTO
        LocationResponseDTO.MasterLocation masterLocationDTO;
        if (location.getMasterLocationId() != null){
            Location masterLocation = location.getMasterLocationId();
            masterLocationDTO = convertEntityToMasterLocationResponse(masterLocation);
            locationResponseDto.setMasterLocation(masterLocationDTO);
        }                                                                                                               //Set LocationUomDTO
        locationResponseDto.setLocationHandlingUom(getLocationUomDTO(location));
        locationResponseDto.setCreatedDate(location.getCreatedDate());
        locationResponseDto.setCreatedUser(location.getCreatedBy());
        locationResponseDto.setUpdatedDate(location.getLastModifiedDate());
        locationResponseDto.setUpdatedUser(location.getLastModifiedBy());
        log.info("EXIT");
        return locationResponseDto;
    }

    /*
     * Method to convert Location Entity to MasterLocation dto
     * @Return LocationResponseDto.MasterLocation
     */
    private LocationResponseDTO.MasterLocation convertEntityToMasterLocationResponse(Location location) {
        log.info("ENTRY - Location Entity to MasterLocation Dto mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        LocationResponseDTO.MasterLocation masterLocationResponse = modelMapper.map(location, LocationResponseDTO.MasterLocation.class);
        log.info("EXIT");
        return masterLocationResponse;
    }

    private Location setBranchCompanyGroupCompany(Location location,LocationBulkDTO locationRequestDto){
        try{                                                                                                            //set Group Company
            location.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(locationRequestDto.getGroupCompanyId())));
        }catch (ServiceException serviceException){
            errorList.add("Row " + rowNo1 + " GroupCompany Id does not exist");
        }
        try{                                                                                                            //set Company
            location.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(locationRequestDto.getCompanyId())));
        }catch (ServiceException serviceException){
            errorList.add("Row " + rowNo1 + " Company Id does not exist");
        }
        try{                                                                                                            //set Branch
            location.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(locationRequestDto.getBranchId())));
        }catch (ServiceException serviceException){
            errorList.add("Row " + rowNo1 + " Branch Id does not exist");
        }
        return location;
    }

    private Location setWarehouseZone(Location location,LocationBulkDTO locationRequestDto){
        if (locationRequestDto.getWareHouseCode() != null && !locationRequestDto.getWareHouseCode().isEmpty() && !locationRequestDto.getWareHouseCode().isBlank()){
            location.setWareHouseMaster(wareHouseService.getWareHouseByCode(locationRequestDto.getWareHouseCode()));
            if (location.getWareHouseMaster() == null){
                errorList.add("Row " + rowNo1 + " WarehouseCode does not exist");
                shouldReturnNullSave = true;
            }
        }                                                                                                               //set Zone
        if (locationRequestDto.getZoneCode() != null && !locationRequestDto.getZoneCode().isEmpty() && !locationRequestDto.getZoneCode().isBlank()){
            location.setZoneMaster(zoneMasterWMSService.getZoneMasterWMSByCode(locationRequestDto.getZoneCode()));
            if (location.getZoneMaster() == null){
                errorList.add("Row " + rowNo1 + " ZoneCode does not exist");
                shouldReturnNullSave = true;
            }else {                                                                                                     //check if Zone exist in the Warehouse
                if (location.getWareHouseMaster() != null && location.getZoneMaster().getWareHouseMaster() != null &&
                        !location.getWareHouseMaster().getCode().equals(location.getZoneMaster().getWareHouseMaster().getCode())){
                    errorList.add("Row " + rowNo1 + " Zone does not exist for the given Warehouse");
                    shouldReturnNullSave = true;
                }
            }
        }
        return location;
    }

    private Location setAisle(Location location,LocationBulkDTO locationRequestDto){
        if (locationRequestDto.getAisleCode() != null  && !locationRequestDto.getAisleCode().isEmpty() && !locationRequestDto.getAisleCode().isBlank()){
            location.setAisleMaster(aisleMasterService.getAisleByCode(locationRequestDto.getAisleCode()));
            if (location.getAisleMaster() == null){
                errorList.add("Row " + rowNo1 + " Aisle code does not exist");
                shouldReturnNullSave = true;
            }else {                                                                                                     //check if Aisle exist in the Zone
                if (location.getZoneMaster() != null && location.getAisleMaster().getZoneMaster() != null &&
                        !location.getZoneMaster().getCode().equals(location.getAisleMaster().getZoneMaster().getCode())){
                    errorList.add("Row " + rowNo1 + " Aisle does not exist for the given Zone");
                    shouldReturnNullSave = true;
                }
            }
        }
        return location;
    }

    private Location setRack(Location location,LocationBulkDTO locationRequestDto){
        if (locationRequestDto.getRackCode() != null && !locationRequestDto.getRackCode().isEmpty() && !locationRequestDto.getRackCode().isBlank()){
            location.setRackMaster(rackMasterService.getRackMasterByCode(locationRequestDto.getRackCode()));
            if (location.getRackMaster() == null){
                errorList.add("Row " + rowNo1 + " Rack code does not exist");
                shouldReturnNullSave = true;
            }else {                                                                                                     //check if Rack exist in the Aisle
                if (location.getAisleMaster() != null && location.getRackMaster().getAisleMaster() != null &&
                        !location.getAisleMaster().getCode().equals(location.getRackMaster().getAisleMaster().getCode())){
                    errorList.add("Row " + rowNo1 + " Rack does not exist for the given Aisle");
                    shouldReturnNullSave = true;
                }
            }
        }
        return location;
    }

    private Location setStorageArea(Location location,LocationBulkDTO locationRequestDto){
        if (locationRequestDto.getStorageAreaCode() != null && !locationRequestDto.getStorageAreaCode().isEmpty() && !locationRequestDto.getStorageAreaCode().isBlank()){
            location.setStorageAreaMaster(storageAreaMasterService.getStorageAreaMasterByCode(locationRequestDto.getStorageAreaCode()));
            if (location.getStorageAreaMaster() == null){
                errorList.add("Row " + rowNo1 + " Storage Area code does not exist");
                shouldReturnNullSave = true;
            }else {                                                                                                     //check if Storage Area exist in the zone, aisle or rack
                String storageAreaCode = getStorageAreaCode(location);
                if (storageAreaCode != null && !storageAreaCode.isBlank() && !storageAreaCode.isEmpty() &&
                        !location.getStorageAreaMaster().getCode().equals(storageAreaCode)){
                    errorList.add("Row " + rowNo1 + " Storage Area code does not exist for the given Zone, Aisle and Rack");
                    shouldReturnNullSave = true;
                }
            }
        }
        return location;
    }

    private Location setStorageType(Location location,LocationBulkDTO locationRequestDto){
        if (locationRequestDto.getStorageTypeCode() != null && !locationRequestDto.getStorageTypeCode().isEmpty() && !locationRequestDto.getStorageTypeCode().isBlank()){
            location.setStorageTypeMaster(storageTypeMasterService.getStorageTypeMasterByCode(locationRequestDto.getStorageTypeCode()));
            if (location.getStorageTypeMaster() == null){
                errorList.add("Row " + rowNo1 + " Storage Type code does not exist");
                shouldReturnNullSave = true;
            }else {                                                                                                     //check if Storage Type exist in the zone, aisle or rack
                String storageTypeCode = getStorageTypeCode(location);
                if (storageTypeCode != null && !storageTypeCode.isBlank() && !storageTypeCode.isEmpty() &&
                        !location.getStorageTypeMaster().getCode().equals(storageTypeCode)){
                    errorList.add("Row " + rowNo1 + " Storage Type code does not exist for the given Zone, Aisle and Rack");
                    shouldReturnNullSave = true;
                }
            }
        }
        return location;
    }

    /*
     * Method to convert Zone to Zone DTO
     * @Return LocationResponseDTO.ZoneDTO
     */
    private LocationResponseDTO.ZoneDTO getZoneDTO(Location location){
        LocationResponseDTO.ZoneDTO zoneDTO=new LocationResponseDTO.ZoneDTO();
        if (location.getZoneMaster() != null){
            modelMapper.map(location.getZoneMaster(),zoneDTO);
            if(location.getWareHouseMaster() != null){zoneDTO.setWarehouseId(location.getWareHouseMaster().getId());}
            if(location.getStorageTypeMaster() !=null){zoneDTO.setStorageTypeId(location.getStorageTypeMaster().getId());}
            if(location.getStorageAreaMaster() !=null ){zoneDTO.setStorageAreaId(location.getStorageAreaMaster().getId());}
            return zoneDTO;
        }
        return null;
    }

    /*
     * Method to convert Aisle to Aisle DTO
     * @Return LocationResponseDTO.AisleDTO
     */
    private LocationResponseDTO.AisleDTO getAisleDTO(Location location){
        LocationResponseDTO.AisleDTO aisleDTO = new LocationResponseDTO.AisleDTO();
        if (location.getAisleMaster() != null){
            modelMapper.map(location.getAisleMaster(),aisleDTO);
            if (location.getWareHouseMaster() != null){aisleDTO.setWarehouseId(location.getWareHouseMaster().getId());}
            if (location.getStorageAreaMaster() != null){aisleDTO.setStorageAreaId(location.getStorageAreaMaster().getId());}
            if (location.getStorageTypeMaster() != null){aisleDTO.setStorageTypeId(location.getStorageTypeMaster().getId());}
            if (location.getZoneMaster() != null){aisleDTO.setZoneId(location.getZoneMaster().getId());}
            return aisleDTO;
        }
        return null;
    }

    /*
     * Method to convert Rack to Rack DTO
     * @Return LocationResponseDTO.RackDTO
     */
    private LocationResponseDTO.RackDTO getRackDTO(Location location){
        LocationResponseDTO.RackDTO rackDTO = new LocationResponseDTO.RackDTO();
        if (location.getRackMaster() != null){
            modelMapper.map(location.getRackMaster(),rackDTO);
            if(location.getWareHouseMaster()!=null){ rackDTO.setWarehouseId(location.getWareHouseMaster().getId());}
            if(location.getStorageTypeMaster()!=null){ rackDTO.setStorageTypeId(location.getStorageTypeMaster().getId());}
            if(location.getStorageAreaMaster()!=null){  rackDTO.setStorageAreaId(location.getStorageAreaMaster().getId());}
            if (location.getAisleMaster() != null){rackDTO.setAisleId(location.getAisleMaster().getId());}
            return rackDTO;
        }
        return null;
    }

    /*
     * Method to convert LocationUom to LocationUom DTO
     * @Return LocationResponseDTO.LocationHandlingUomDTO
     */
    private LocationResponseDTO.LocationHandlingUomDTO getLocationUomDTO(Location location){
        LocationResponseDTO.LocationHandlingUomDTO locationHandlingUomDTO = new LocationResponseDTO.LocationHandlingUomDTO();
        if (location.getLocationHandlingUomMaster() != null){
            modelMapper.map(location.getLocationHandlingUomMaster(),locationHandlingUomDTO);
            if (location.getLocationHandlingUomMaster().getBranchMaster() != null){locationHandlingUomDTO.setBranchId(location.getLocationHandlingUomMaster().getBranchMaster().getId());}
            if (location.getLocationHandlingUomMaster().getCompanyMaster() != null){locationHandlingUomDTO.setCompanyId(location.getLocationHandlingUomMaster().getCompanyMaster().getId());}
            if (location.getLocationHandlingUomMaster().getGroupCompanyMaster() != null){locationHandlingUomDTO.setGroupCompany(location.getLocationHandlingUomMaster().getGroupCompanyMaster().getId());}
            if (location.getLocationHandlingUomMaster().getCategoryMaster() != null){locationHandlingUomDTO.setCategoryId(location.getLocationHandlingUomMaster().getCategoryMaster().getId());}
            return locationHandlingUomDTO;
        }
        return null;
    }

    /*
     * Method to get StorageArea code for bulk save and update
     * @Return String
     */
    private String getStorageAreaCode(Location location){
        log.info("ENTRY - Get StorageArea code for bulk save and update");
        String storageAreaCode = "";                                                                                    //Get storageArea code from zone, aisle or rack whichever is given
        if (location.getZoneMaster() != null && location.getZoneMaster().getStorageAreaMaster() != null
                && !location.getZoneMaster().getStorageAreaMaster().getCode().isEmpty() && !location.getZoneMaster().getStorageAreaMaster().getCode().isBlank()){
            storageAreaCode = location.getZoneMaster().getStorageAreaMaster().getCode();
        }
        if (location.getAisleMaster() != null && location.getAisleMaster().getStorageAreaMaster() != null
                && !location.getAisleMaster().getStorageAreaMaster().getCode().isEmpty() && !location.getAisleMaster().getStorageAreaMaster().getCode().isBlank()){
            storageAreaCode = location.getAisleMaster().getStorageAreaMaster().getCode();
        }
        if (location.getRackMaster() != null && location.getRackMaster().getStorageAreaMaster() != null
                && !location.getRackMaster().getStorageAreaMaster().getCode().isEmpty() && !location.getRackMaster().getStorageAreaMaster().getCode().isBlank()){
            storageAreaCode = location.getRackMaster().getStorageAreaMaster().getCode();
        }
        log.info("EXIT");
        return storageAreaCode;
    }

    /*
     * Method to get StorageType code for bulk save and update
     * @Return String
     */
    private String getStorageTypeCode(Location location){
        log.info("ENTRY - Get StorageType code for bulk save and update");
        String storageTypeCode = "";                                                                                    //Get storageType code from zone, aisle or rack whichever is given
        if (location.getZoneMaster() != null && location.getZoneMaster().getStorageTypeMaster() != null
                && !location.getZoneMaster().getStorageTypeMaster().getCode().isEmpty() && !location.getZoneMaster().getStorageTypeMaster().getCode().isBlank()){
            storageTypeCode = location.getZoneMaster().getStorageTypeMaster().getCode();
        }
        if (location.getAisleMaster() != null && location.getAisleMaster().getStorageTypeMaster() != null
                && !location.getAisleMaster().getStorageTypeMaster().getCode().isEmpty() && !location.getAisleMaster().getStorageTypeMaster().getCode().isBlank()){
            storageTypeCode = location.getAisleMaster().getStorageTypeMaster().getCode();
        }
        if (location.getRackMaster() != null && location.getRackMaster().getStorageTypeMaster() != null
                && !location.getRackMaster().getStorageTypeMaster().getCode().isEmpty() && !location.getRackMaster().getStorageTypeMaster().getCode().isBlank()){
            storageTypeCode = location.getRackMaster().getStorageTypeMaster().getCode();
        }
        log.info("EXIT");
        return storageTypeCode;
    }

    /*
     * Method to convert Boolean to "Yes" or "No" string
     * @Return "Yes" or "No" string
     */
    private String characterToString(Character inputCharacter) {
        log.info("ENTRY - convert Character to \"Yes\" or \"No\" string");
        if (inputCharacter != null) {
            if (inputCharacter.equals('Y')){
                return "Yes";
            }else if (inputCharacter.equals('N')){
                return "No";
            }else {
                return null;
            }
        }
        log.info("EXIT");
        return null;
    }

    /*
     * Method to find if an entry in bulk data is to be saved or updated
     * @Return Boolean
     */
    private Boolean locationAlreadyExist(LocationBulkDTO locationBulkDTO) {
        log.info("ENTRY - Find if an entry in bulk data is to be saved or updated");
        rowNo1++;
        Boolean locationExists = false;
        WareHouseMaster wareHouseMaster = wareHouseService.getWareHouseByCode(locationBulkDTO.getWareHouseCode());
        if (wareHouseMaster != null) {                                                                                  //check for match of both locationUid and WarehouseCode//if match found, it is updated and if no match found it is saved
            locationExists = locationService.findAll()
                    .stream()
                    .anyMatch(location ->
                            location.getLocationUid().equals(locationBulkDTO.getLocationUid()) &&
                                    location.getWareHouseMaster().getCode().equals(wareHouseMaster.getCode())
                    );
        }
        log.info("EXIT");
        return locationExists;
    }

}
