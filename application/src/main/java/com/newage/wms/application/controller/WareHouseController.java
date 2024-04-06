package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.WareHouseMapper;
import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.WareHouseAutoCompleteDTO;
import com.newage.wms.application.dto.requestdto.WareHouseRequestDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.application.dto.responsedto.WareHouseResponseDTO;
import com.newage.wms.entity.QWareHouseMaster;
import com.newage.wms.entity.WareHouseMaster;
import com.newage.wms.entity.WareHouseContactDetail;
import com.newage.wms.service.BranchMasterService;
import com.newage.wms.service.WareHouseService;
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
@RequestMapping("/wareHouse")
@CrossOrigin("*")
public class WareHouseController {

    @Autowired
    private WareHouseService wareHouseService;

    @Autowired
    private WareHouseMapper wareHouseMapper;

    @Autowired
    private BranchMasterService branchMasterService;

    /*
     * Method to fetch all Warehouse with sort, filter and pagination
     */
    @GetMapping
    public ResponseEntity<ResponseDTO> fetchAllWareHouse(@RequestParam(name = "code",required = false) String code,
                                                         @RequestParam(name = "name",required = false) String name,
                                                         @RequestParam(name = "wareHouseLocationPrefix",required = false) String wareHouseLocationPrefix,
                                                         @RequestParam(name = "isBonded",required = false) String isBonded,
                                                         @RequestParam(name = "status",required = false) String status,
                                                         @RequestParam(name = "branchId",required = false) Long branchId,
                                                         @PageableDefault(sort = {"id"},direction = Sort.Direction.ASC,size = 20) @Parameter(hidden = true) Pageable pageable){
        log.info("ENTRY - Fetch all Warehouse with sort, filter and pagination");
        Page<WareHouseMaster> wareHousePage = wareHouseService.fetchWarehouseList(code, name, wareHouseLocationPrefix,isBonded,branchId,status,pageable);
        Page<WareHouseResponseDTO> wareHouseResponseDtoPage = wareHouseMapper.convertWareHousePageToWareHouseResponsePage(wareHousePage);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,wareHouseResponseDtoPage,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to create new Warehouse
     */
    @PostMapping(value="/save",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> createWareHouse(@Valid @RequestBody WareHouseRequestDTO wareHouseRequestDto){
        log.info("ENTRY - Create new WareHouse");
        wareHouseService.validateUniqueWareHouseAttributeSave(wareHouseRequestDto.getCode());
        String wareHouseId = wareHouseService.generateNewWareHouseId();
        DateAndTimeRequestDto dateAndTimeRequestDto = setDateAndTimeRequestDtoCreate(wareHouseRequestDto);
        WareHouseMaster wareHouse = wareHouseMapper.convertWareHouseRequestToWareHouseSave(wareHouseRequestDto,dateAndTimeRequestDto);
        WareHouseContactDetail wareHouseContactDetail = wareHouseMapper.convertWareHouseRequestToWareHouseContactDetailSave(wareHouseRequestDto,dateAndTimeRequestDto);
        wareHouse.setWareHouseId(wareHouseId);
        wareHouse.setWareHouseContactDetail(wareHouseContactDetail);
        wareHouseContactDetail.setWareHouse(wareHouse);
        wareHouse =  wareHouseService.saveWareHouse(wareHouse);
        WareHouseResponseDTO wareHouseResponseDto = wareHouseMapper.convertEntitytoResponseDto(wareHouse);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.CREATED.value(), Boolean.TRUE,wareHouseResponseDto,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to update Warehouse by id
     */
    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> updateWareHouseById(@Valid @RequestBody WareHouseRequestDTO wareHouseRequestDto,
                                                           @PathVariable ("id") Long id){
        log.info("ENTRY - Update WareHouse by Id");
        WareHouseMaster wareHouse = wareHouseService.getWareHouseById(id);
        wareHouseService.validateUniqueWareHouseAttributeUpdate(wareHouseRequestDto.getCode(),id);
        DateAndTimeRequestDto dateAndTimeRequestDto = setDateAndTimeRequestDtoUpdate(wareHouseRequestDto,wareHouse);
        wareHouse = wareHouseMapper.convertWareHouseUpdateRequestToWareHouse(wareHouseRequestDto,wareHouse,dateAndTimeRequestDto);
        WareHouseContactDetail wareHouseContactDetail = wareHouseMapper.convertWareHouseUpdateRequestToWareHouseContactDetail(wareHouseRequestDto,wareHouse,dateAndTimeRequestDto);
        wareHouse.setWareHouseContactDetail(wareHouseContactDetail);
        wareHouseContactDetail.setWareHouse(wareHouse);
        wareHouseContactDetail.setWareHouse(wareHouse);
        wareHouse = wareHouseService.updateWareHouse(wareHouse);
        WareHouseResponseDTO wareHouseResponseDto = wareHouseMapper.convertEntitytoResponseDto(wareHouse);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,wareHouseResponseDto,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to fetch Warehouse by id
     */
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> fetchWareHouseById(@PathVariable("id") Long id){
        log.info("ENTRY - Fetch WareHouse by Id");
        WareHouseResponseDTO wareHouseResponseDto = wareHouseMapper.convertEntitytoResponseDto(wareHouseService.getWareHouseById(id));
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,wareHouseResponseDto,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to fetch Warehouse by id
     */
    @GetMapping(value = "/codeCheck",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> checkCode(@RequestParam("id") String id,@RequestParam("code") String code){
        log.info("ENTRY - Fetch WareHouse by Id");
        String codeCaps = code.toUpperCase();
        Boolean exists = false;
        try{
            if (id.isEmpty() || id.isBlank() ){
                wareHouseService.validateUniqueWareHouseAttributeSave(codeCaps);
            }else {
                wareHouseService.validateUniqueWareHouseAttributeUpdate(codeCaps,Long.parseLong(id));
            }
        }catch (ServiceException serviceException){
            exists=true;
        }
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,exists,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to fetch all CountryMaster with filter
     */
    @ApiOperation(value = "Fetch all WareHouse with filter(Auto-Complete)", notes = " Api is used to fetch all WareHouse with filter(Auto-Complete)")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping("/autoComplete")
    public ResponseEntity<ResponseDTO> fetchWareHouseAutoComplete(@RequestParam(name = "query",required = false) String query,
                                                                  @RequestParam(name = "branchId",required = false) Long branchId,
                                                                  @RequestParam(name = "status",required = false) String status){
        log.info("ENTRY - Fetch all WareHouse with filter(Auto-Complete)");
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QWareHouseMaster.wareHouseMaster.id.isNotNull());
        if (query != null && !query.isEmpty() && !query.isBlank()){
            predicates.add(QWareHouseMaster.wareHouseMaster.name.containsIgnoreCase(query)
                    .or(QWareHouseMaster.wareHouseMaster.code.containsIgnoreCase(query)));
        }
        if (branchId != null){
            predicates.add(QWareHouseMaster.wareHouseMaster.branchMaster.id.eq(branchId));
        }
        if (status != null && !status.isEmpty() && !status.isBlank()){
            predicates.add(QWareHouseMaster.wareHouseMaster.status.eq(status));
        }
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("name").ascending());
        Iterable<WareHouseMaster> wareHouseIterable = wareHouseService.findAll(predicateAll,pageable);
        Iterable<WareHouseAutoCompleteDTO> wareHouseAutoCompleteResponseDtoIterable = wareHouseMapper.convertWareHouseIterableToWareHouseAutoCompleteResponseDtoIterable(wareHouseIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,wareHouseAutoCompleteResponseDtoIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to set Date, Time and Version in Create
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoCreate(WareHouseRequestDTO wareHouseRequestDto){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Create");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(wareHouseRequestDto.getUser());
        dateAndTimeRequestDto.setCreatedDate(new Date());
        dateAndTimeRequestDto.setLastModifiedBy(wareHouseRequestDto.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(1L);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

    /*
     * Method to set Date, Time and Version in Update
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoUpdate(WareHouseRequestDTO wareHouseRequestDto, WareHouseMaster wareHouse){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Update");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(wareHouse.getCreatedBy());
        dateAndTimeRequestDto.setCreatedDate(wareHouse.getCreatedDate());
        dateAndTimeRequestDto.setLastModifiedBy(wareHouseRequestDto.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(wareHouse.getVersion() + 1);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

}
