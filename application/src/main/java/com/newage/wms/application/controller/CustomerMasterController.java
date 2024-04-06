package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.CustomerMapper;
import com.newage.wms.application.dto.requestdto.SkuRequestDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.entity.CustomerMaster;
import com.newage.wms.entity.QAisleMaster;
import com.newage.wms.entity.QCustomerMaster;
import com.newage.wms.service.CustomerMasterService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
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

import java.util.ArrayList;
import java.util.Collection;

@RestController
@Log4j2
@RequestMapping(value = "/api/v1/masterdata/customers")
@CrossOrigin
public class CustomerMasterController {

    @Autowired
    private CustomerMasterService customerMasterService;

    @Autowired
    private CustomerMapper customerMapper;

    /*
     * Method to fetch all customer for autoComplete field
     */
    @ApiOperation(value = "Fetch all Customer", notes = " Api is used to fetch all Customer ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping("/autoComplete")
    public ResponseEntity<ResponseDTO> fetchAllCustomer(@RequestParam(name = "query",required = false) String query,
                                                        @RequestParam(name = "branchId",required = false) Long branchId,
                                                        @RequestParam(name = "status",required = false) String status){
        log.info("ENTRY - fetch all customer for autoComplete field");
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QCustomerMaster.customerMaster.id.isNotNull());
        if (query != null && !query.isEmpty() && !query.isBlank()){
            predicates.add(QCustomerMaster.customerMaster.name.containsIgnoreCase(query)
                    .or(QCustomerMaster.customerMaster.code.containsIgnoreCase(query)));
        }
        if (branchId != null){
            predicates.add(QCustomerMaster.customerMaster.branchMaster.id.eq(branchId));
        }
        if (status != null && !status.isEmpty() && !status.isBlank()){
            predicates.add(QCustomerMaster.customerMaster.status.eq(status));
        }
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("name").ascending());
        Iterable<CustomerMaster> customerMasterIterable = customerMasterService.getAllCustomer(predicateAll,pageable);
        Iterable<SkuRequestDTO.CustomerDTO> customerAutoCompleteDtoIterable = customerMapper.convertCustomerMasterIterableToCustomerAutoCompleteDtoIterable(customerMasterIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,customerAutoCompleteDtoIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

}

