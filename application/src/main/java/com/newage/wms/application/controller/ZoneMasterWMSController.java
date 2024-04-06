package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.ZoneWMSMapper;
import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.ZoneWMSRequestDTO;
import com.newage.wms.application.dto.responsedto.LocationResponseDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.application.dto.responsedto.ZoneWMSResponseDTO;
import com.newage.wms.entity.*;
import com.newage.wms.entity.QZoneMasterWMS;
import com.newage.wms.service.UserWareHouseService;
import com.newage.wms.service.ZoneMasterWMSService;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Log4j2
@RestController
@RequestMapping("/zone")
@CrossOrigin("*")
public class ZoneMasterWMSController {

    @Autowired
    private ZoneMasterWMSService zoneMasterWMSService;

    @Autowired
    private ZoneWMSMapper zoneWMSMapper;

    @Autowired
    private UserWareHouseService userWareHouseService;

    /*
     * Method to fetch all Zone with sort, filter and pagination
     */
    @ApiOperation(value = "Fetch all Zone with pagination", notes = " Api is used to fetch all Zone with sort, filter and pagination ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping("/fetchAll")
    public ResponseEntity<ResponseDTO> fetchAllZone(@QuerydslPredicate(root = ZoneMasterWMS.class) Predicate predicate,
                                                    @PageableDefault(sort = {"id"},direction = Sort.Direction.ASC,size = 20) @Parameter(hidden = true) Pageable pageable){
        log.info("ENTRY - Fetch all Zone with sort, filter and pagination");
        Page<ZoneMasterWMS> zonePage = zoneMasterWMSService.findAll(predicate,pageable);
        Page<ZoneWMSResponseDTO> zoneResponseDTOPage = zoneWMSMapper.convertZonePageToZoneResponsePage(zonePage);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,zoneResponseDTOPage,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     *Method to Create new Zone
     */
    @ApiOperation(value = "Create Zone", notes = " Api is used to create Zone ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully created") })
    @PostMapping(value="/save",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> createZone(@Valid @RequestBody ZoneWMSRequestDTO zoneRequestDTO){
        log.info("ENTRY - Create new Zone");
        zoneMasterWMSService.validateUniqueZoneAttributeSave(zoneRequestDTO.getCode().toUpperCase(),Long.parseLong(zoneRequestDTO.getWareHouse().getId()));
        DateAndTimeRequestDto dateAndTimeRequestDto = this.setDateAndTimeRequestDtoCreate(zoneRequestDTO);
        ZoneMasterWMS zone = zoneWMSMapper.convertRequestDtoToEntity(zoneRequestDTO,dateAndTimeRequestDto);
        zone = zoneMasterWMSService.saveZoneWMS(zone);
        ZoneWMSResponseDTO zoneResponseDTO = zoneWMSMapper.convertEntityToDTO(zone);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.CREATED.value(), Boolean.TRUE,zoneResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     *Method to Update Zone by id
     */
    @ApiOperation(value = "Update Zone", notes = "Api is used to update Zone")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated")})
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> updateZoneById(@Valid @RequestBody ZoneWMSRequestDTO zoneRequestDTO,
                                                      @PathVariable ("id") Long id){
        log.info("ENTRY - Update Zone by Id");
        ZoneMasterWMS zoneMaster=zoneMasterWMSService.getZoneMasterWMSById(id);
        zoneMasterWMSService.validateUniqueZoneAttributeUpdate(zoneRequestDTO.getCode().toUpperCase(),id,Long.parseLong(zoneRequestDTO.getWareHouse().getId()));
        DateAndTimeRequestDto dateAndTimeRequestDto=setDateAndTimeRequestDtoUpdate(zoneRequestDTO,zoneMaster);
        zoneWMSMapper.convertUpdateRequestToEntity(zoneRequestDTO,zoneMaster,dateAndTimeRequestDto);
        zoneMaster=zoneMasterWMSService.updateZoneMasterWMS(zoneMaster);
        ZoneWMSResponseDTO zoneResponseDTO = zoneWMSMapper.convertEntityToDTO(zoneMaster);
        ResponseDTO responseDto=new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,zoneResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);

    }

    /*
     * Method to fetch Zone by id
     */
    @ApiOperation(value = "Fetch Zone by Id", notes = " Api is used to fetch Zone by Id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> fetchZoneById(@PathVariable("id") Long id){
        log.info("ENTRY - Fetch Zone by Id");
        ZoneWMSResponseDTO zoneResponseDTO = zoneWMSMapper.convertEntityToDTO(zoneMasterWMSService.getZoneMasterWMSById(id));
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,zoneResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to fetch all Zone with filter
     */
    @GetMapping
    public ResponseEntity<ResponseDTO> fetchAllZoneAutoComplete(@RequestParam(name = "query",required = false) String query,
                                                                @RequestParam(name = "warehouseId",required = false) Long warehouseId,
                                                                @RequestParam(name = "branchId",required = false) Long branchId,
                                                                @RequestParam(name = "status",required = false) String status){
        log.info("ENTRY - Fetch all Zone with filter");
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QZoneMasterWMS.zoneMasterWMS.id.isNotNull());
        if (query != null && !query.isEmpty() && !query.isBlank()){
            predicates.add(QZoneMasterWMS.zoneMasterWMS.name.containsIgnoreCase(query)
                    .or(QZoneMasterWMS.zoneMasterWMS.code.containsIgnoreCase(query)));
        }
        if (warehouseId != null){
            predicates.add(QZoneMasterWMS.zoneMasterWMS.wareHouseMaster.id.eq(warehouseId));
        }
        if (branchId != null){
            predicates.add(QZoneMasterWMS.zoneMasterWMS.branchMaster.id.eq(branchId));
        }
        if (status != null && !status.isEmpty() && !status.isBlank()){
            predicates.add(QZoneMasterWMS.zoneMasterWMS.status.eq(status));
        }
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("name").ascending());
        Iterable<ZoneMasterWMS> zoneMasterIterable = zoneMasterWMSService.fetchAllZoneWMS(predicateAll,pageable);
        Iterable<LocationResponseDTO.ZoneDTO> zoneDTOIterable = zoneWMSMapper.convertZoneMasterIterableToZoneMasterDTOIterable(zoneMasterIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,zoneDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to fetch Warehouse by id
     */
    @GetMapping(value = "/codeCheck",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> checkCode(@RequestParam("id") String id,@RequestParam("code") String code,
                                                 @RequestParam("warehouseId") Long warehouseId){
        String codeCaps = code.toUpperCase();
        Boolean exists = false;
        try{
            if (id.isEmpty() || id.isBlank() ){
                zoneMasterWMSService.validateUniqueZoneAttributeSave(codeCaps,warehouseId);
            }else {
                zoneMasterWMSService.validateUniqueZoneAttributeUpdate(codeCaps,Long.parseLong(id),warehouseId);
            }
        }catch (ServiceException serviceException){
            exists=true;
        }
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,exists,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to set Date, Time and Version in Create
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoCreate(ZoneWMSRequestDTO zoneRequestDTO){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Create");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(zoneRequestDTO.getUser());
        dateAndTimeRequestDto.setCreatedDate(new Date());
        dateAndTimeRequestDto.setLastModifiedBy(zoneRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(1L);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

    public DateAndTimeRequestDto setDateAndTimeRequestDtoUpdate(ZoneWMSRequestDTO  zoneRequestDTO, ZoneMasterWMS zoneMaster){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Update");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(zoneMaster.getCreatedBy());
        dateAndTimeRequestDto.setCreatedDate(zoneMaster.getCreatedDate());
        dateAndTimeRequestDto.setLastModifiedBy(zoneRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(zoneMaster.getVersion() + 1);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

}
