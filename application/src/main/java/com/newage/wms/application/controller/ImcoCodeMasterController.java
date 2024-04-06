package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.ImcoCodeMapper;
import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.ImcoCodeRequestDTO;
import com.newage.wms.application.dto.requestdto.SkuRequestDTO;
import com.newage.wms.application.dto.responsedto.ImcoCodeResponseDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.entity.ImcoCodeMaster;
import com.newage.wms.entity.QAisleMaster;
import com.newage.wms.entity.QImcoCodeMaster;
import com.newage.wms.service.ImcoCodeMasterService;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
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
@RequestMapping("/imcoCode")
@CrossOrigin("*")
public class ImcoCodeMasterController {

    @Autowired
    private ImcoCodeMasterService imcoCodeMasterService;

    @Autowired
    private ImcoCodeMapper imcoCodeMapper;

    /*
     * Method to fetch all ImcoCode for autoComplete field
     */
    @ApiOperation(value = "Fetch all ImcoCode", notes = " Api is used to fetch all ImcoCode ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping("/autoComplete")
    public ResponseEntity<ResponseDTO> fetchAllAutoComplete(@RequestParam(name = "query",required = false) String query,
                                                            @RequestParam(name = "branchId",required = false) Long branchId,
                                                            @RequestParam(name = "status",required = false) String status){
        log.info("ENTRY - fetch all ImcoCode for autoComplete field");
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QImcoCodeMaster.imcoCodeMaster.id.isNotNull());
        if (query != null && !query.isEmpty() && !query.isBlank()){
            predicates.add(QImcoCodeMaster.imcoCodeMaster.name.containsIgnoreCase(query)
                    .or(QImcoCodeMaster.imcoCodeMaster.code.containsIgnoreCase(query)));
        }
        if (status != null && !status.isEmpty() && !status.isBlank()){
            predicates.add(QImcoCodeMaster.imcoCodeMaster.status.eq(status));
        }
        if (branchId != null){
            predicates.add(QImcoCodeMaster.imcoCodeMaster.branchMaster.id.eq(branchId));
        }
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("name").ascending());
        Iterable<ImcoCodeMaster> imcoCodeMasterIterable = imcoCodeMasterService.getAllAutoComplete(predicateAll,pageable);
        Iterable<SkuRequestDTO.ImcoCodeDTO> imcoCodeDTOIterable = imcoCodeMapper.convertEntityIterableToAutoCompleteDtoIterable(imcoCodeMasterIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,imcoCodeDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation(value ="Save ImcoCode MAster", notes = "Api is used to save ImcoCodeMaster")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successfully saved")})
    @PostMapping(value = "/save")
    public ResponseEntity<ResponseDTO> save(@Valid @RequestBody ImcoCodeRequestDTO imcoCodeRequestDTO){
        log.info("ENTRY - Create new ImcoCode Master");
        DateAndTimeRequestDto dateAndTimeRequestDto = this.setDateAndTimeRequestDtoCreate(imcoCodeRequestDTO);
        ImcoCodeMaster imcoCodeMaster = imcoCodeMapper.convertRequestToEntity(imcoCodeRequestDTO,dateAndTimeRequestDto);
        imcoCodeMaster=imcoCodeMasterService.saveImcoCode(imcoCodeMaster);
        ImcoCodeResponseDTO imcoCodeResponseDTO= imcoCodeMapper.convertEntityToResponse(imcoCodeMaster);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,imcoCodeResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO>fetchAll(@QuerydslPredicate(root = ImcoCodeMaster.class)Predicate predicate,
                                               @PageableDefault(sort ={"id"},direction = Sort.Direction.ASC,size = 20)@Parameter(hidden = true)Pageable pageable){
        log.info("ENTRY - Fetch All ImcoCodeMaster with sort,filter and pagination");
        Page<ImcoCodeMaster> imcoCodeMasterPage = imcoCodeMasterService.findAll(predicate, pageable);
        Page<ImcoCodeResponseDTO> imcoCodeResponseDTOPage = imcoCodeMapper.convertImcoCodePageToResponsePage(imcoCodeMasterPage);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,imcoCodeResponseDTOPage,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<ResponseDTO> update(@Valid@RequestBody ImcoCodeRequestDTO imcoCodeRequestDTO,
                                              @PathVariable ("id") Long id){
        log.info("ENTRY - Update ImcoCode by Id");
        ImcoCodeMaster imcoCodeMaster = imcoCodeMasterService.getById(id);
        DateAndTimeRequestDto dateAndTimeRequestDto = setDateAndTimeRequestDtoUpdate(imcoCodeRequestDTO,imcoCodeMaster);
        imcoCodeMapper.convertUpdateRequestToEntity(imcoCodeRequestDTO,imcoCodeMaster,dateAndTimeRequestDto);
        imcoCodeMaster = imcoCodeMasterService.updateImcoCodeMaster(imcoCodeMaster);
        ImcoCodeResponseDTO imcoCodeResponseDTO = imcoCodeMapper.convertEntityToResponse(imcoCodeMaster);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,imcoCodeResponseDTO,null);
        log.info("EXIT");
        return  ResponseEntity.ok(responseDTO);
    }
    /*
     * Method to Fetch ImcoCode By Id
     */
    @GetMapping("{id}")
    public ResponseEntity<ResponseDTO> getImcoById(@PathVariable ("id") Long id){
        log.info("ENTRY - Get ImcoCode By Id");
        ImcoCodeResponseDTO imcoCodeResponseDTO = imcoCodeMapper.convertEntityToResponse(imcoCodeMasterService.getById(id));
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,imcoCodeResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);

    }

    /*
     * Method to set Date, Time and Version in Create
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoCreate(@Valid ImcoCodeRequestDTO imcoCodeRequestDTO){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Create");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(imcoCodeRequestDTO.getUser());
        dateAndTimeRequestDto.setCreatedDate(new Date());
        dateAndTimeRequestDto.setLastModifiedBy(imcoCodeRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(1L);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

    /*
     * Method to set Date, Time and Version in Update
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoUpdate(ImcoCodeRequestDTO imcoCodeRequestDTO,  ImcoCodeMaster imcoCodeMaster){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Update");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(imcoCodeMaster.getCreatedBy());
        dateAndTimeRequestDto.setCreatedDate(imcoCodeMaster.getCreatedDate());
        dateAndTimeRequestDto.setLastModifiedBy(imcoCodeRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(imcoCodeMaster.getVersion() + 1);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

    /*
     * Method to validate unique Imco Code
     */
    @GetMapping(value = "/codeCheck",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> checkCode(@RequestParam("id") String id,@RequestParam("code") String code){
        log.info("ENTRY - Validate unique Imco Code");
        String codeCaps = code.toUpperCase();
        Boolean exists = false;
        try{
            if (id.isEmpty() || id.isBlank() ){
                imcoCodeMasterService.validateUniqueImcoAttributeSave(codeCaps);
            }else {
                imcoCodeMasterService.validateUniqueImcoAttributeUpdate(codeCaps,Long.parseLong(id));
            }
        }catch (ServiceException serviceException){
            exists=true;
        }
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,exists,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }
}
