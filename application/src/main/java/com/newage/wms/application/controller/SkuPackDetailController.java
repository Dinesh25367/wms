package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.SkuPackDetailMapper;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.application.dto.responsedto.SkuPackDetailResponseDTO;
import com.newage.wms.entity.SkuPackDetail;
import com.newage.wms.service.SkuPackDetailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/skuPackDetail")
@CrossOrigin("*")
public class SkuPackDetailController {

    @Autowired
    private SkuPackDetailService skuPackDetailService;

    @Autowired
    private SkuPackDetailMapper skuPackDetailMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> fetchById(@PathVariable Long id){
        log.info("ENTRY - Fetch SkuPackDetail Header by id");
        SkuPackDetail skuPackDetail = skuPackDetailService.getById(id);
        SkuPackDetailResponseDTO skuPackDetailResponseDTO = skuPackDetailMapper.convertEntityToResponse(skuPackDetail);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,skuPackDetailResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

}
