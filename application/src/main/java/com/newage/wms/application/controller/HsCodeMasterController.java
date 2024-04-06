package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.HsCodeMapper;
import com.newage.wms.application.dto.requestdto.SkuRequestDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.entity.HsCodeMaster;
import com.newage.wms.entity.QHsCodeMaster;
import com.newage.wms.service.HsCodeMasterService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/hsCode")
@CrossOrigin("*")
public class HsCodeMasterController {

    @Autowired
    private HsCodeMasterService hsCodeMasterService;

    @Autowired
    private HsCodeMapper hsCodeMapper;

    /*
     * Method to fetch all HsCode for autoComplete field
     */
    @ApiOperation(value = "Fetch all HsCode", notes = " Api is used to fetch all HsCode ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping("/autoComplete")
    public ResponseEntity<ResponseDTO> fetchAllAutoComplete(@RequestParam(name = "query",required = false) String query,
                                                                   @QuerydslPredicate(root = HsCodeMaster.class) Predicate predicate){
        log.info("ENTRY - fetch all HsCode for autoComplete field");
        BooleanBuilder combinedPredicate = new BooleanBuilder();
        if (query != null && !query.isEmpty() && !query.isBlank()){
            BooleanExpression nameOrCodeExpression = QHsCodeMaster.hsCodeMaster.name.containsIgnoreCase(query)
                    .or(QHsCodeMaster.hsCodeMaster.code.containsIgnoreCase(query));
            combinedPredicate.and(predicate)
                    .and(nameOrCodeExpression);
        }
        Pageable pageable = PageRequest.of(0, 20, Sort.by("name").ascending());
        Iterable<HsCodeMaster> hsCodeMasterIterable = hsCodeMasterService.getAllAutoComplete(combinedPredicate,pageable);
        Iterable<SkuRequestDTO.HsCodeDTO> hsCodeDTOIterable = hsCodeMapper.convertEntityIterableToHsCodeAutoCompleteDtoIterable(hsCodeMasterIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,hsCodeDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to fetch all HsCode for autoComplete field
     */
    @ApiOperation(value = "Fetch all HsCode", notes = " Api is used to fetch all HsCode ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping("/asn/bulkSave")
    public ResponseEntity<ResponseDTO> fetchAllHsCodeForASNDetailsBulkSave(){
        log.info("ENTRY - fetch all HsCode for autoComplete field");
        BooleanBuilder combinedPredicate = new BooleanBuilder();
        Pageable pageable = Pageable.unpaged();
        BooleanExpression nameOrCodeExpression = QHsCodeMaster.hsCodeMaster.id.isNotNull();
        combinedPredicate.and(nameOrCodeExpression);
        Iterable<HsCodeMaster> hsCodeMasterIterable = hsCodeMasterService.findAll(combinedPredicate,pageable);
        Iterable<SkuRequestDTO.HsCodeDTO> hsCodeDTOIterable = hsCodeMapper.convertEntityIterableToHsCodeAutoCompleteDtoIterable(hsCodeMasterIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,hsCodeDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

}
