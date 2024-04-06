package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.ConfigurationMapper;
import com.newage.wms.application.dto.requestdto.ConfigurationRequestDTO;
import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.responsedto.ConfigurationMasterResponseDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.entity.ConfigurationMaster;
import com.newage.wms.entity.QConfigurationMaster;
import com.newage.wms.entity.QSkuMaster;
import com.newage.wms.service.ConfigurationMasterService;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Log4j2
@RestController
@RequestMapping("/configuration")
@CrossOrigin("*")
public class ConfigurationController {

    @Autowired
    ConfigurationMasterService configurationMasterService;

    @Autowired
    ConfigurationMapper configurationMapper;

    private String number="number";

    private String date="date";

    private String character="character";

    private String textbox="textbox";

    /*
     *Method to FetchAll Configuration Master with pagination, sort and filter
     */
    @GetMapping
    public ResponseEntity<ResponseDTO> findAll(@QuerydslPredicate(root = ConfigurationMaster.class) Predicate predicate,
                                               @PageableDefault(sort = {"id"},direction = Sort.Direction.ASC,size = 20) Pageable pageable){
        log.info("ENTRY - FetchAll Configuration Master with pagination, sort and filter");
        Page<ConfigurationMaster> configurationMasterPage = configurationMasterService.findAll(predicate,pageable);
        Page<ConfigurationMasterResponseDTO> configurationMasterResponseDTOPage = configurationMapper.convertEntityPageToResponsePage(configurationMasterPage);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,configurationMasterResponseDTOPage,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     *Method to Create new Configuration Master
     */
    @ApiOperation(value ="Create configuration ", notes="Api is used to create Configuration ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully created") })
    @PostMapping(value="/save",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> save(@Valid @RequestBody ConfigurationRequestDTO configurationRequestDTO,
                                            @RequestParam(value = "userId") Long userId){
        log.info("ENTRY - Create new Configuration");
        String dataType = configurationRequestDTO.getDataType();
        DateAndTimeRequestDto dateAndTimeRequestDto1 = this.setDateAndTimeRequestDtoCreate(configurationRequestDTO);
        if (dataType.toLowerCase().equals(number)){
            configurationRequestDTO.setDataType(number);
        }
        if (dataType.toLowerCase().equals(date)){
            configurationRequestDTO.setDataType(date);
        }
        if (dataType.toLowerCase().equals(character)){
            configurationRequestDTO.setDataType(textbox);
        }
        ConfigurationMaster configurationMaster= configurationMapper.convertRequestToEntity(configurationRequestDTO,dateAndTimeRequestDto1,userId);
        configurationMaster = configurationMasterService.save(configurationMaster);
        ConfigurationMasterResponseDTO configurationMasterResponseDTO = configurationMapper.convertEntityToResponse(configurationMaster);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,configurationMasterResponseDTO,null);
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to update Configuration by id
     */
    @ApiOperation(value = "Update Configuration", notes = " Api is used to update Configuration ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated") })
    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> update(@Valid @RequestBody ConfigurationRequestDTO configurationRequestDTO,
                                              @PathVariable ("id") Long id){
        log.info("ENTRY - Update Configuration by Id");
        String dataType = configurationRequestDTO.getDataType();
        if (dataType.toLowerCase().equals(number)){
            configurationRequestDTO.setDataType(number);
        }
        if (dataType.toLowerCase().equals(date)){
            configurationRequestDTO.setDataType(date);
        }
        if (dataType.toLowerCase().equals(character)){
            configurationRequestDTO.setDataType(textbox);
        }
        ConfigurationMaster configurationMaster = configurationMasterService.getById(id);
        DateAndTimeRequestDto dateAndTimeRequestDto1 = setDateAndTimeRequestDtoUpdate(configurationRequestDTO,configurationMaster);
        configurationMapper.convertUpdateRequestToEntity(configurationRequestDTO,dateAndTimeRequestDto1,configurationMaster);
        configurationMaster = configurationMasterService.update(configurationMaster);
        ConfigurationMasterResponseDTO configurationMasterResponseDTO = configurationMapper.convertEntityToResponse(configurationMaster);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,configurationMasterResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to fetch Configuration by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getById(@PathVariable("id") Long id){
        log.info("ENTRY - Fetch Configuration by id");
        ConfigurationMaster configurationMaster = configurationMasterService.getById(id);
        ConfigurationMasterResponseDTO configurationMasterResponseDTO = configurationMapper.convertEntityToResponse(configurationMaster);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,configurationMasterResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to set Date, Time and Version in Create
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoCreate(@Valid ConfigurationRequestDTO configurationRequestDTO){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Create");
        DateAndTimeRequestDto dateAndTimeRequestDto1 = new DateAndTimeRequestDto();
        dateAndTimeRequestDto1.setCreatedBy(configurationRequestDTO.getUser());
        dateAndTimeRequestDto1.setCreatedDate(new Date());
        dateAndTimeRequestDto1.setLastModifiedBy(configurationRequestDTO.getUser());
        dateAndTimeRequestDto1.setLastModifiedDate(new Date());
        dateAndTimeRequestDto1.setVersion(1L);
        log.info("EXIT");
        return dateAndTimeRequestDto1;
    }
    /*
     * Method to set Date, Time and Version in Update
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoUpdate(ConfigurationRequestDTO configurationRequestDTO,ConfigurationMaster configurationMaster){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Update");
        DateAndTimeRequestDto dateAndTimeRequestDto1 = new DateAndTimeRequestDto();
        dateAndTimeRequestDto1.setCreatedBy(configurationMaster.getCreatedBy());
        dateAndTimeRequestDto1.setCreatedDate(configurationMaster.getCreatedDate());
        dateAndTimeRequestDto1.setLastModifiedBy(configurationRequestDTO.getUser());
        dateAndTimeRequestDto1.setLastModifiedDate(new Date());
        dateAndTimeRequestDto1.setVersion(configurationMaster.getVersion() + 1);
        log.info("EXIT");
        return dateAndTimeRequestDto1;
    }

}

