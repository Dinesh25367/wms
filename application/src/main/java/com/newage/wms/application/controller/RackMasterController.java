package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.RackMapper;
import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.RackRequestDTO;
import com.newage.wms.application.dto.responsedto.LocationResponseDTO;
import com.newage.wms.application.dto.responsedto.RackResponseDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.entity.QRackMaster;
import com.newage.wms.entity.RackMaster;
import com.newage.wms.service.RackMasterService;
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
@RequestMapping("/rack")
@CrossOrigin("*")
public class RackMasterController {

    @Autowired
    private RackMasterService rackMasterService;

    @Autowired
    private RackMapper rackMapper;

    /*
     * Method to fetch all Rack with sort, filter and pagination
     */
    @ApiOperation(value = "Fetch all Rack with pagination", notes = " Api is used to fetch all Rack with sort, filter and pagination ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping("/fetchAll")
    public ResponseEntity<ResponseDTO> fetchAllRack(@QuerydslPredicate(root = RackMaster.class) Predicate predicate,
                                                    @PageableDefault(sort = {"id"},direction = Sort.Direction.ASC,size = 20) @Parameter(hidden = true) Pageable pageable){
        log.info("ENTRY - Fetch all Rack with sort, filter and pagination");
        Page<RackMaster> rackPage = rackMasterService.findAll(predicate,pageable);
        Page<RackResponseDTO> rackResponseDTOPage = rackMapper.convertRackPageToRackResponsePage(rackPage);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,rackResponseDTOPage,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to fetch all Rack for autocomplete
     */
    @GetMapping
    public ResponseEntity<ResponseDTO> fetchAllRackAutoComplete(@RequestParam(name = "query",required = false) String query,
                                                                @RequestParam(name = "warehouseId",required = false) Long warehouseId,
                                                                @RequestParam(name = "aisleId",required = false) Long aisleId,
                                                                @RequestParam(name = "branchId",required = false) Long branchId,
                                                                @RequestParam(name = "status",required = false) String status){
        log.info("ENTRY - Fetch all Rack for autocomplete");
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QRackMaster.rackMaster.id.isNotNull());
        if (query != null && !query.isEmpty() && !query.isBlank()){
            predicates.add(QRackMaster.rackMaster.name.containsIgnoreCase(query)
                    .or(QRackMaster.rackMaster.code.containsIgnoreCase(query)));
        }
        if (warehouseId != null){
            predicates.add(QRackMaster.rackMaster.wareHouseMaster.id.eq(warehouseId));
        }
        if (aisleId != null){
            predicates.add(QRackMaster.rackMaster.aisleMaster.id.eq(aisleId));
        }
        if (branchId != null){
            predicates.add(QRackMaster.rackMaster.branchMaster.id.eq(branchId));
        }
        if (status != null && !status.isEmpty() && !status.isBlank()){
            predicates.add(QRackMaster.rackMaster.status.eq(status));
        }
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("name").ascending());
        Iterable<RackMaster> rackMasterIterable = rackMasterService.fetchAllRack(predicateAll,pageable);
        Iterable<LocationResponseDTO.RackDTO> rackDTOIterable = rackMapper.convertRackMasterIterableToRackMasterDTOIterable(rackMasterIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,rackDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     *Method to Create new Rack Master
     */
    @ApiOperation(value ="Create Rack", notes="Api is used to create Rack")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully created") })
    @PostMapping(value="/save",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> createRack(@Valid @RequestBody RackRequestDTO rackRequestDTO){
        log.info("ENTRY - Create new Rack");
        rackMasterService.validateUniqueRackAttributeSave(rackRequestDTO.getCode().toUpperCase(),Long.parseLong(rackRequestDTO.getWareHouse().getId()));
        DateAndTimeRequestDto dateAndTimeRequestDto = this.setDateAndTimeRequestDtoCreate(rackRequestDTO);
        RackMaster rackMaster=rackMapper.convertRequestDtoToEntity(rackRequestDTO,dateAndTimeRequestDto);
        rackMaster = rackMasterService.saveRack(rackMaster);
        RackResponseDTO rackResponseDTO = rackMapper.convertEntityToDTO(rackMaster);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.CREATED.value(), Boolean.TRUE,rackResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to update Rack by id
     */
    @ApiOperation(value = "Update Rack", notes = " Api is used to update Rack ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated") })
    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> updateRackById(@Valid @RequestBody RackRequestDTO rackRequestDTO,
                                                      @PathVariable ("id") Long id){
        log.info("ENTRY - Update Rack by Id");
        RackMaster rackMaster = rackMasterService.getRackMasterById(id);
        rackMasterService.validateUniqueRackAttributeUpdate(rackRequestDTO.getCode().toUpperCase(),id,Long.parseLong(rackRequestDTO.getWareHouse().getId()));
        DateAndTimeRequestDto dateAndTimeRequestDto = setDateAndTimeRequestDtoUpdate(rackRequestDTO,rackMaster);
        rackMapper.convertUpdateRequestToEntity(rackRequestDTO,rackMaster,dateAndTimeRequestDto);
        rackMaster = rackMasterService.updateRack(rackMaster);
        RackResponseDTO rackResponseDTO = rackMapper.convertEntityToDTO(rackMaster);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,rackResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to fetch Rack by id
     */
    @ApiOperation(value = "Fetch Rack by Id", notes = " Api is used to fetch Rack by Id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> fetchZoneById(@PathVariable("id") Long id){
        log.info("ENTRY - Fetch Rack by Id");
        RackResponseDTO rackResponseDTO = rackMapper.convertEntityToDTO(rackMasterService.getRackMasterById(id));
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,rackResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to validate unique Rack Code
     */
    @GetMapping(value = "/codeCheck",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> checkCode(@RequestParam("id") String id,@RequestParam("code") String code,
                                                 @RequestParam("warehouseId") Long warehouseId){
        log.info("ENTRY - Validate unique Rack Code");
        String codeCaps = code.toUpperCase();
        Boolean exists = false;
        try{
            if (id.isEmpty() || id.isBlank() ){
                rackMasterService.validateUniqueRackAttributeSave(codeCaps,warehouseId);
            }else {
                rackMasterService.validateUniqueRackAttributeUpdate(codeCaps,Long.parseLong(id),warehouseId);
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
    public DateAndTimeRequestDto setDateAndTimeRequestDtoCreate(@Valid RackRequestDTO rackRequestDTO){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Create");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(rackRequestDTO.getUser());
        dateAndTimeRequestDto.setCreatedDate(new Date());
        dateAndTimeRequestDto.setLastModifiedBy(rackRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(1L);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

    /*
     * Method to set Date, Time and Version in Update
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoUpdate(RackRequestDTO rackRequestDTO, RackMaster rackMaster){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Update");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(rackMaster.getCreatedBy());
        dateAndTimeRequestDto.setCreatedDate(rackMaster.getCreatedDate());
        dateAndTimeRequestDto.setLastModifiedBy(rackRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(rackMaster.getVersion() + 1);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }


}
