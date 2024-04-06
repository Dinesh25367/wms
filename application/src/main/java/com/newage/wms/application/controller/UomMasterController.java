package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.UomMapper;
import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.UomRequestDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.application.dto.responsedto.UomResponseDTO;
import com.newage.wms.entity.QUomMaster;
import com.newage.wms.entity.QWareHouseMaster;
import com.newage.wms.entity.UomMaster;
import com.newage.wms.service.UomMasterService;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@RequestMapping("/uom")
@CrossOrigin("*")
public class UomMasterController {

    @Autowired
    private UomMasterService uomMasterService;

    @Autowired
    private UomMapper uomMapper;

    /*
     * Method to fetch all Uom with sort, filter and pagination
     */
    @ApiOperation(value = "Fetch all Zone with pagination", notes = " Api is used to fetch all Zone with sort, filter and pagination ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping("/fetchAll")
    public ResponseEntity<ResponseDTO> fetchAllUom(@QuerydslPredicate(root = UomMaster.class) Predicate predicate,
                                                   @PageableDefault(sort = {"id"},direction = Sort.Direction.ASC,size = 20) Pageable pageable){
        log.info("ENTRY - Fetch all Uom with sort, filter and pagination");
        Page<UomMaster> uomMasterPage = uomMasterService.findAll(predicate,pageable);
        Page<UomResponseDTO> uomResponseDTOPage = uomMapper.convertUomPageToUomResponsePage(uomMasterPage);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,uomResponseDTOPage,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     *Method to Create new Uom Master
     */
    @PostMapping(value="/save",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> createUom(@Valid @RequestBody UomRequestDTO uomRequestDTO){
        log.info("ENTRY - Create new Rack");
        uomMasterService.validateUniqueUomAttributeSave(uomRequestDTO.getCode().toUpperCase());
        DateAndTimeRequestDto dateAndTimeRequestDto = this.setDateAndTimeRequestDtoCreate(uomRequestDTO);
        UomMaster uomMaster=uomMapper.convertRequestDtoToEntity(uomRequestDTO,dateAndTimeRequestDto);
        uomMaster = uomMasterService.saveUom(uomMaster);
        UomResponseDTO uomResponseDTO = uomMapper.convertEntityToDTO(uomMaster);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.CREATED.value(), Boolean.TRUE,uomResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to update Uom by id
     */
    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> updateUomById(@Valid @RequestBody UomRequestDTO uomRequestDTO,
                                                     @PathVariable ("id") Long id){
        log.info("ENTRY - Update Rack by Id");
        uomMasterService.validateUniqueUomAttributeUpdate(uomRequestDTO.getCode().toUpperCase(),id);
        UomMaster uomMaster = uomMasterService.getUomById(id);
        DateAndTimeRequestDto dateAndTimeRequestDto = setDateAndTimeRequestDtoUpdate(uomRequestDTO,uomMaster);
        uomMapper.convertUpdateRequestToEntity(uomRequestDTO,uomMaster,dateAndTimeRequestDto);
        uomMaster = uomMasterService.updateUom(uomMaster);
        UomResponseDTO uomResponseDTO = uomMapper.convertEntityToDTO(uomMaster);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,uomResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to get Uom by id
     */
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> getById(@PathVariable ("id") Long id){
        log.info("ENTRY - Get Uom by Id");
        UomMaster uomMaster = uomMasterService.getUomById(id);
        UomResponseDTO uomResponseDTO = uomMapper.convertEntityToDTO(uomMaster);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,uomResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to fetch all Uom for autocomplete
     */
    @GetMapping
    public ResponseEntity<ResponseDTO> fetchAllUomAutoComplete(@RequestParam(name = "query",required = false) String query,
                                                               @RequestParam(name = "branchId",required = false) Long branchId,
                                                               @RequestParam(name = "status",required = false) String status){
        log.info("ENTRY - Fetch all Uom for autocomplete");
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QUomMaster.uomMaster.id.isNotNull());
        if (query != null && !query.isEmpty() && !query.isBlank()){
            predicates.add(QUomMaster.uomMaster.code.containsIgnoreCase(query));
        }
        if (branchId != null){
            predicates.add(QUomMaster.uomMaster.branchMaster.id.eq(branchId));
        }
        if (status != null && !status.isEmpty() && !status.isBlank()){
            predicates.add(QUomMaster.uomMaster.status.eq(status));
        }
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("id").ascending());
        Iterable<UomMaster> uomMasterIterable = uomMasterService.fetchAllUom(predicateAll,pageable);
        Iterable<UomResponseDTO> uomResponseDTOIterable = uomMapper.convertUomMasterIterableToUomResponseDTOIterable(uomMasterIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,uomResponseDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping(value = "/codeCheck",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> checkCode(@RequestParam("id") String id,@RequestParam("code") String code){
        String codeCaps = code.toUpperCase();
        Boolean exists = false;
        try{
            if (id.isEmpty() || id.isBlank() ){
                uomMasterService.validateUniqueUomAttributeSave(codeCaps);
            }else {
                uomMasterService.validateUniqueUomAttributeUpdate(codeCaps,Long.parseLong(id));
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
    public DateAndTimeRequestDto setDateAndTimeRequestDtoCreate(@Valid UomRequestDTO uomRequestDTO){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Create");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(uomRequestDTO.getUser());
        dateAndTimeRequestDto.setCreatedDate(new Date());
        dateAndTimeRequestDto.setLastModifiedBy(uomRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(1L);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

    /*
     * Method to set Date, Time and Version in Update
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoUpdate(UomRequestDTO uomRequestDTO, UomMaster uomMaster){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Update");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(uomMaster.getCreatedBy());
        dateAndTimeRequestDto.setCreatedDate(uomMaster.getCreatedDate());
        dateAndTimeRequestDto.setLastModifiedBy(uomRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(uomMaster.getVersion() + 1);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

}
