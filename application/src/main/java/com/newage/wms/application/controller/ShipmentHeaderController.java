package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.ShipmentHeaderMapper;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.entity.QShipmentHeader;
import com.newage.wms.entity.ShipmentHeader;
import com.newage.wms.service.ShipmentHeaderService;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@Log4j2
@RestController
@RequestMapping("/freightRefShipment")
@CrossOrigin("*")
public class ShipmentHeaderController {

    @Autowired
    private ShipmentHeaderService shipmentHeaderService;

    @Autowired
    private ShipmentHeaderMapper shipmentHeaderMapper;

    /*
     * Method to fetch all ShipmentHeader for autocomplete
     */
    @GetMapping("/autoComplete")
    public ResponseEntity<ResponseDTO> fetchAllAutoComplete(@RequestParam(name = "query",required = false) String query,
                                                            @RequestParam(name = "status",required = false) String status,
                                                            @RequestParam(name ="branchId",required = false) Long branchId,
                                                            @RequestParam(name ="tradeCode",required = false) Long tradeCode) {
        log.info("ENTRY - Fetch all ShipmentHeader for autocomplete");
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QShipmentHeader.shipmentHeader.id.isNotNull());
        if (query != null && !query.isEmpty() && !query.isBlank()){
            predicates.add(QShipmentHeader.shipmentHeader.shipmentUid.containsIgnoreCase(query));
        }
        if (status != null && !status.isEmpty() && !status.isBlank()){
            predicates.add(QShipmentHeader.shipmentHeader.status.eq(status));
        }
        if (branchId != null){
            predicates.add(QShipmentHeader.shipmentHeader.branchId.eq(branchId));
        }
        if (tradeCode != null){
            predicates.add(QShipmentHeader.shipmentHeader.tradeCode.eq(tradeCode));
        }
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("id").ascending());
        Iterable<ShipmentHeader> shipmentHeaderIterable = shipmentHeaderService.getAllAutoComplete(predicateAll,pageable);
        Iterable<TrnResponseDTO.TrnHeaderFreightDTO.FreightRefShipmentDTO> freightRefShipmentDTOIterable = shipmentHeaderMapper.convertEntityIterableToAutoCompleteResponseIterable(shipmentHeaderIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,freightRefShipmentDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

}
