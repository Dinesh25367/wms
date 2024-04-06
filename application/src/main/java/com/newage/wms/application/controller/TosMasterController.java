package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.TosMapper;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.application.dto.responsedto.TosResponseDTO;
import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.entity.QTosMaster;
import com.newage.wms.entity.TosMaster;
import com.newage.wms.service.TosMasterService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping(value = "/api/v1/masterdata/tos")
@CrossOrigin("*")
public class TosMasterController {

    @Autowired
    private TosMapper tosMapper;

    @Autowired
    private TosMasterService tosMasterService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> getTosById(@PathVariable("id") Long id) {
        TosResponseDTO result = tosMapper.convertEntityToResponseDTO(tosMasterService.getTosById(id));
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE, result, null);
        return ResponseEntity.ok(response);

    }

    /*
     * Method to fetch all TosMaster for autocomplete
     */
    @GetMapping("/autoComplete")
    public ResponseEntity<ResponseDTO> fetchAllAutoComplete(@RequestParam(name = "query",required = false) String query,
                                                            @QuerydslPredicate(root= TosMaster.class) Predicate predicate){
        log.info("ENTRY - Fetch all TosMaster for autocomplete");
        BooleanBuilder combinedPredicate = new BooleanBuilder();
        if (query != null && !query.isEmpty() && !query.isBlank()){
            BooleanExpression nameOrCodeExpression = QTosMaster.tosMaster.name.containsIgnoreCase(query)
                    .or(QTosMaster.tosMaster.code.containsIgnoreCase(query));
            combinedPredicate.and(predicate)
                    .and(nameOrCodeExpression);
        }
        Pageable pageable = PageRequest.of(0, 20, Sort.by("name").ascending());
        Iterable<TosMaster> tosMasterIterable = tosMasterService.getAllAutoComplete(combinedPredicate,pageable);
        Iterable<TrnResponseDTO.TrnHeaderFreightShippingDTO.TosDTO> tosDTOIterable = tosMapper.convertEntityIterableToAutoCompleteResponseIterable(tosMasterIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,tosDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

}
