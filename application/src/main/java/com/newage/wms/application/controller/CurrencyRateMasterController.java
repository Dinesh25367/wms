package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.CurrencyRateMapper;
import com.newage.wms.application.dto.responsedto.CurrencyRateResponseDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.entity.CurrencyRateMaster;
import com.newage.wms.service.CurrencyRateMasterService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Comparator;
import java.util.Optional;

@RestController
@Log4j2
@RequestMapping(value = "/api/v1/masterdata/currencyRate")
@CrossOrigin("*")
public class CurrencyRateMasterController {

    @Autowired
    private CurrencyRateMasterService currencyRateMasterService;

    @Autowired
    private CurrencyRateMapper currencyRateMapper;

    @GetMapping(value = "/usingCurrencyIds/{fromCurrencyId}/{toCurrencyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> getCurrRateByFromAndToCurrency(@PathVariable("fromCurrencyId") Long fromCurrencyId, @PathVariable("toCurrencyId") Long toCurrencyId, @QuerydslPredicate(root = CurrencyRateMaster.class) Predicate predicate,
                                                                      @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 20) Pageable pageRequest) {

        Page<CurrencyRateMaster> currencyRateMasterPage = currencyRateMasterService.getCurrencyRateByFromAndToCurrency(predicate, pageRequest, fromCurrencyId, toCurrencyId);
        Optional<CurrencyRateResponseDTO> maxDateObject = currencyRateMapper.convertEntityPageToResponsePage(pageRequest, currencyRateMasterPage)
                .stream()
                .max(Comparator.comparing(CurrencyRateResponseDTO::getCurrencyDate));
        if (maxDateObject.isPresent()) {
            ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE, maxDateObject.get(), null);
            return ResponseEntity.ok(response);
        } else {
            throw new ServiceException(ServiceErrors.CURRENCY_RATE_NOT_FOUND.CODE,
                    ServiceErrors.CURRENCY_RATE_NOT_FOUND.KEY);
        }
    }

    @GetMapping(value = "/currencyCode", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> getCurrencyByCode(@QuerydslPredicate(root = CurrencyRateMaster.class) Predicate predicate,
                                                         @PageableDefault(sort = {"currencyDate"}, direction = Sort.Direction.DESC, size = 1)
                                                         Pageable pageRequest,
                                                         @RequestParam(value = "code", required = true) String code) {
        Page<CurrencyRateMaster> currencyRateMasters = currencyRateMasterService.getCurrencyRateByCode(predicate, pageRequest, code);
        Page<CurrencyRateResponseDTO> result = currencyRateMapper.convertEntityPageToResponsePage(pageRequest, currencyRateMasters);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE, result, null);
        return ResponseEntity.ok(response);
    }

}
