package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.CurrencyMapper;
import com.newage.wms.application.dto.responsedto.CurrencyResponseDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.application.dto.responsedto.SkuResponseDTO;
import com.newage.wms.entity.CurrencyMaster;
import com.newage.wms.entity.QCurrencyMaster;
import com.newage.wms.service.CurrencyMasterService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
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

@Log4j2
@RestController
@RequestMapping("/api/v1/masterdata/currency")
@CrossOrigin("*")
public class CurrencyMasterController {

    @Autowired
    private CurrencyMasterService currencyMasterService;

    @Autowired
    private CurrencyMapper currencyMapper;

    /*
     * Method to fetch all Currency with pagination, sort and filter
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> fetchAllCurrency(@QuerydslPredicate(root = CurrencyMaster.class) Predicate predicate,
                                                        @PageableDefault(sort = {"code"}, direction = Sort.Direction.ASC, size = 20) Pageable pageable) {
        log.info("ENTRY - fetch all Currency with pagination, sort and filter");
        Page<CurrencyMaster> currencyMasterPage = currencyMasterService.findAll(predicate, pageable);
        Page<CurrencyResponseDTO> result = currencyMapper.convertEntityPageToResponsePage(pageable, currencyMasterPage);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE, result, null);
        return ResponseEntity.ok(response);
    }

    /*
     * Method to fetch all Currency for autocomplete
     */
    @GetMapping(value = "/autoComplete",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> fetchAllCurrencyAutoComplete(@RequestParam(name = "query",required = false) String query,
                                                                    @QuerydslPredicate(root = CurrencyMaster.class) Predicate predicate) {
        log.info("ENTRY - fetch all Currency for autocomplete");
        BooleanBuilder combinedPredicate = new BooleanBuilder();
        if (query != null && !query.isEmpty() && !query.isBlank()){
            BooleanExpression nameOrCodeExpression = QCurrencyMaster.currencyMaster.name.containsIgnoreCase(query)
                    .or(QCurrencyMaster.currencyMaster.code.containsIgnoreCase(query));
            combinedPredicate.and(predicate)
                    .and(nameOrCodeExpression);
        }
        Pageable pageable = PageRequest.of(0, 20, Sort.by("code").ascending());
        Iterable<CurrencyMaster> currencyMasterIterable = currencyMasterService.fetchAllCurrency(combinedPredicate,pageable);
        Iterable<SkuResponseDTO.CurrencyDTO> currencyDTOIterable = currencyMapper.convertCurrencyIterableToCurrencyDTOIterable(currencyMasterIterable);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE, currencyDTOIterable, null);
        return ResponseEntity.ok(response);
    }

    /*
     * Method to fetch all Currency for ASN bulk save
     */
    @GetMapping(value = "/asn/bulkSave",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> fetchAllCurrencyAsnBulkSave() {
        log.info("ENTRY - fetch all Currency for ASN bulk save");
        BooleanBuilder combinedPredicate = new BooleanBuilder();
        BooleanExpression nameOrCodeExpression = QCurrencyMaster.currencyMaster.id.isNotNull();
        combinedPredicate.and(nameOrCodeExpression);
        Pageable pageable = Pageable.unpaged();
        Iterable<CurrencyMaster> currencyMasterIterable = currencyMasterService.fetchAllCurrency(combinedPredicate,pageable);
        Iterable<SkuResponseDTO.CurrencyDTO> currencyDTOIterable = currencyMapper.convertCurrencyIterableToCurrencyDTOIterable(currencyMasterIterable);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE, currencyDTOIterable, null);
        return ResponseEntity.ok(response);
    }

    /*
     * Method to fetch Currency by id
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> fetchCurrencyById(@PathVariable("id") Long id) {
        log.info("ENTRY - Fetch Currency by id");
        CurrencyResponseDTO result = currencyMapper.convertEntityToResponseDTO(currencyMasterService.getCurrencyById(id));
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE, result, null);
        return ResponseEntity.ok(response);
    }

}

