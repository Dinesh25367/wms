package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.VesselMasterMapper;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.application.dto.responsedto.VesselMasterResponseDTO;
import com.newage.wms.entity.QVesselMaster;
import com.newage.wms.entity.QWareHouseMaster;
import com.newage.wms.entity.VesselMaster;
import com.newage.wms.service.VesselMasterService;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
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
import java.util.ArrayList;
import java.util.Collection;

@Log4j2
@RestController
@RequestMapping("/api/v1/masterdata/vessel")
@CrossOrigin("*")
public class VesselMasterController {

    @Autowired
    private VesselMasterService vesselMasterService;

    @Autowired
    private VesselMasterMapper vesselMasterMapper;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> getVesselById(@PathVariable("id") Long id) {

        VesselMaster vesselMaster = vesselMasterService.getVesselById(id);
        VesselMasterResponseDTO vesselMasterResponseDTO = vesselMasterMapper.convertEntityToResponseDTO(vesselMaster);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE, vesselMasterResponseDTO, null);
        return ResponseEntity.ok(responseDTO);
    }

    /*
     * Method to fetch all VesselMaster for autocomplete
     */
    @GetMapping("/autoComplete")
    public ResponseEntity<ResponseDTO> fetchAllAutoComplete(@RequestParam(name = "query",required = false) String query,
                                                            @QuerydslPredicate(root= VesselMaster.class) Predicate predicate){
        log.info("ENTRY - Fetch all VesselMaster for autocomplete");
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QVesselMaster.vesselMaster.id.isNotNull());
        if (query != null && !query.isEmpty() && !query.isBlank()){
            predicates.add(QVesselMaster.vesselMaster.name.containsIgnoreCase(query)
                    .or(QVesselMaster.vesselMaster.shortName.containsIgnoreCase(query)));
        }
        predicates.add(predicate);
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("name").ascending());
        Iterable<VesselMaster> vesselMasterIterable = vesselMasterService.getAllAutoComplete(predicateAll,pageable);
        Iterable<TrnResponseDTO.TrnHeaderFreightShippingDTO.VesselDTO> vesselDTOIterable = vesselMasterMapper.convertEntityIterableToAutoCompleteResponseIterable(vesselMasterIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,vesselDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

}
