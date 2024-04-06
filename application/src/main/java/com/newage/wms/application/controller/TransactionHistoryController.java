package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.TransactionHistoryMapper;
import com.newage.wms.application.dto.responsedto.PutAwaySummaryResponseDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.entity.QGrnHeader;
import com.newage.wms.entity.QTransactionHistory;
import com.newage.wms.repository.TransactionHistoryRepository;
import com.newage.wms.repository.TransactionLotRepository;
import com.newage.wms.service.TransactionHistoryService;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Log4j2
@RestController
@RequestMapping(value = "/transactionHistory")
@CrossOrigin("*")
public class TransactionHistoryController {

    @Autowired
    private TransactionHistoryService transactionHistoryService;

    @Autowired
    private TransactionHistoryMapper transactionHistoryMapper;

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    private TransactionLotRepository transactionLotRepository;

    @GetMapping
    public ResponseEntity<ResponseDTO> fetchAll(@RequestParam(value = "grnId",required = false) Long grnId,
                                                @RequestParam(name = "customerName", required = false) String customerName,
                                                @RequestParam(name = "customerOrderNo", required = false) String customerOrderNo,
                                                @RequestParam(name = "team", required = false) String team,
                                                @RequestParam(name = "status", required = false) String status,
                                                @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 5) Pageable pageable){
        log.info("ENTRY - Find Transaction");
        Collection<Predicate> predicates = new ArrayList<>();
        List<PutAwaySummaryResponseDTO> putAwaySummaryResponseDTOList;
        predicates.add(QTransactionHistory.transactionHistory.id.isNotNull());
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        putAwaySummaryResponseDTOList= transactionHistoryMapper.getAll(grnId,customerName,customerOrderNo);
        transactionHistoryMapper.getSortedResponseList(pageable,predicateAll);
        List<PutAwaySummaryResponseDTO> pagedPutAwaySummaryResponseDTOList = transactionHistoryMapper.getPagedResponseList(pageable, putAwaySummaryResponseDTOList);
        Page<PutAwaySummaryResponseDTO> putAwayResponseDTOPage = new PageImpl<>(pagedPutAwaySummaryResponseDTOList, pageable, putAwaySummaryResponseDTOList.size());
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,putAwayResponseDTOPage,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping
    public ResponseEntity<ResponseDTO> delete(){
        transactionHistoryRepository.deleteAll();
        transactionLotRepository.deleteAll();
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,true,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }



}
