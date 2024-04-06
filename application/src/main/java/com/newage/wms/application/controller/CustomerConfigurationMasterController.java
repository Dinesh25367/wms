package com.newage.wms.application.controller;


import com.newage.wms.application.dto.mapper.CustomerConfigurationMapper;
import com.newage.wms.application.dto.requestdto.CustomerConfigurationRequestDTO;
import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.InventoryStatusRequestDTO;
import com.newage.wms.application.dto.responsedto.CustomerConfigurationMasterResponseDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.entity.CustomerConfigurationMaster;
import com.newage.wms.entity.InventoryStatusMaster;
import com.newage.wms.service.CustomerConfigurationMasterService;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@Log4j2
@RestController
@RequestMapping("/customerConfiguration")
@CrossOrigin("*")
public class CustomerConfigurationMasterController {

    @Autowired
    private CustomerConfigurationMasterService customerConfigurationMasterService;

    @Autowired
    private CustomerConfigurationMapper customerConfigurationMapper;

    @ApiOperation(value = "Save CustomerConfiguration Master",notes = "Api is used to save CustomerConfiguration Master")
    @ApiResponses(value = {@ApiResponse(code=200,message="Successfully saved")})
    @PostMapping(value = "/save")
    public ResponseEntity<ResponseDTO> save(@Valid @RequestBody CustomerConfigurationRequestDTO customerConfigurationRequestDTO){
        log.info("ENTRY - Create New CustomerConfigurationMaster");
        DateAndTimeRequestDto dateAndTimeRequestDto=this.setDateAndTimeRequestDtoCreate(customerConfigurationRequestDTO);

        CustomerConfigurationMaster customerConfigurationMaster=customerConfigurationMapper.convertRequestToEntity(customerConfigurationRequestDTO,dateAndTimeRequestDto);
        customerConfigurationMaster=customerConfigurationMasterService.saveCustomer(customerConfigurationMaster);
        CustomerConfigurationMasterResponseDTO customerConfigurationMasterResponseDTO=customerConfigurationMapper.convertEntityToResponse(customerConfigurationMaster);
        ResponseDTO responseDTO=new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,customerConfigurationMasterResponseDTO,null);
        log.info("EXIT");

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseDTO> getCustomerConfigurationById(@PathVariable ("id") Long id){
        log.info("ENTRY - get CustomerConfigurationamster ById");
        CustomerConfigurationMasterResponseDTO customerConfigurationMasterResponseDTO=customerConfigurationMapper.convertEntityToResponse(customerConfigurationMasterService.getById(id));
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,customerConfigurationMasterResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }

    @ApiOperation(value = "List CustomerConfigurationMaster",notes="Api is used to Listed CustomerConfigurationMaster")
    @ApiResponses(value = {@ApiResponse(code=200,message = "Listed Successfully")})
    @GetMapping("/fetchAll")
    public ResponseEntity<ResponseDTO>fetchAll(@QuerydslPredicate(root = CustomerConfigurationMaster.class) Predicate predicate,
                                               @PageableDefault(sort = {"id"},direction = Sort.Direction.ASC,size = 20)@Parameter(hidden = true)Pageable pageable){
        log.info("ENTRY - FetchAll CustomerConfigurationamster with Sort,Filter and pagination");
        Page<CustomerConfigurationMaster>customerConfigurationMasterPage=customerConfigurationMasterService.findAll(predicate,pageable);
        Page<CustomerConfigurationMasterResponseDTO>customerConfigurationMasterResponseDTOPage=customerConfigurationMapper.convertCustomerConfigurationPageToResponsePage(customerConfigurationMasterPage);
        ResponseDTO responseDTO= new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,customerConfigurationMasterResponseDTOPage,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);

    }
    @PutMapping(value = "{id}")
    public ResponseEntity<ResponseDTO>update(@Valid @RequestBody CustomerConfigurationRequestDTO customerConfigurationRequestDTO,
                                             @PathVariable ("id")Long id){
        log.info("ENTRY - Update CustomerConfiguration ById");
        CustomerConfigurationMaster customerConfigurationMaster=customerConfigurationMasterService.getById(id);
        DateAndTimeRequestDto dateAndTimeRequestDto=setDateAndTimeRequestDtoUpdate(customerConfigurationRequestDTO,customerConfigurationMaster);
        customerConfigurationMapper.convertUpdateRequestToEntity(customerConfigurationRequestDTO,customerConfigurationMaster,dateAndTimeRequestDto);
        customerConfigurationMaster=customerConfigurationMasterService.updateCustomer(customerConfigurationMaster);
        CustomerConfigurationMasterResponseDTO customerConfigurationMasterResponseDTO=customerConfigurationMapper.convertEntityToResponse(customerConfigurationMaster);
        ResponseDTO responseDTO=new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,customerConfigurationMasterResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }
    public DateAndTimeRequestDto setDateAndTimeRequestDtoCreate(@Valid CustomerConfigurationRequestDTO customerConfigurationRequestDTO){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Create");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(customerConfigurationRequestDTO.getUser());
        dateAndTimeRequestDto.setCreatedDate(new Date());
        dateAndTimeRequestDto.setLastModifiedBy(customerConfigurationRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(1L);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

    public DateAndTimeRequestDto setDateAndTimeRequestDtoUpdate(@Valid CustomerConfigurationRequestDTO customerConfigurationRequestDTO, CustomerConfigurationMaster customerConfigurationMaster){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Create");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(customerConfigurationRequestDTO.getUser());
        dateAndTimeRequestDto.setCreatedDate(new Date());
        dateAndTimeRequestDto.setLastModifiedBy(customerConfigurationRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(customerConfigurationMaster.getVersion() + 1);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

}

