package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.ConfigurationFlagMasterMapper;
import com.newage.wms.application.dto.requestdto.ConfigurationFlagMasterRequestDTO;
import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.responsedto.ConfigurationFlagMasterResponseDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.entity.ConfigurationFlagMaster;
import com.newage.wms.entity.QConfigurationFlagMaster;
import com.newage.wms.service.ConfigurationFlagMasterService;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Log4j2
@RestController
@RequestMapping("/configurationFlag")
@CrossOrigin("*")
public class ConfigurationFlagMasterController {
    @Autowired
    ConfigurationFlagMasterService configurationFlagMasterService;

    @Autowired
    ConfigurationFlagMasterMapper configurationFlagMasterMapper;

    /*
     *Method to FetchAll ConfigurationFlag Master with pagination, sort and filter
     */
    @GetMapping
    public ResponseEntity<ResponseDTO> findAll(@QuerydslPredicate(root = ConfigurationFlagMaster.class) Predicate predicate,
                                               @PageableDefault(sort = {"id"},direction = Sort.Direction.ASC,size = 20) Pageable pageable){
        log.info("ENTRY - FetchAll ConfigurationFlag Master with pagination, sort and filter");
        Page<ConfigurationFlagMaster> configurationFlagMasterPage = configurationFlagMasterService.findAll(predicate,pageable);
        Page<ConfigurationFlagMasterResponseDTO> configurationFlagMasterResponseDTOPage = configurationFlagMasterMapper.convertEntityPageToResponsePage(configurationFlagMasterPage);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,configurationFlagMasterResponseDTOPage,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     *Method to Create new configurationFlag Master
     */
    @ApiOperation(value ="Create configurationFlag ", notes="Api is used to create configurationFlag ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully created") })
    @PostMapping(value="/save",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> save(@Valid @RequestBody ConfigurationFlagMasterRequestDTO configurationFlagMasterRequestDTO,
                                            @RequestParam(value = "userId") Long userId){
        log.info("ENTRY - Create new Configuration");
        DateAndTimeRequestDto dateAndTimeRequestDto1 = this.setDateAndTimeRequestDtoCreate(configurationFlagMasterRequestDTO);
        ConfigurationFlagMaster configurationFlagMaster= configurationFlagMasterMapper.convertRequestToEntity(configurationFlagMasterRequestDTO,dateAndTimeRequestDto1,userId);
        configurationFlagMaster = configurationFlagMasterService.save(configurationFlagMaster);
        ConfigurationFlagMasterResponseDTO configurationFlagMasterResponseDTO = configurationFlagMasterMapper.convertEntityToResponse(configurationFlagMaster);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,configurationFlagMasterResponseDTO,null);
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to update configurationFlag by id
     */
    @ApiOperation(value = "Update configurationFlag", notes = " Api is used to update configurationFlag ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated") })
    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> update(@Valid @RequestBody ConfigurationFlagMasterRequestDTO configurationFlagMasterRequestDTO,
                                              @PathVariable ("id") Long id){
        log.info("ENTRY - Update configurationFlag by Id");
        ConfigurationFlagMaster configurationFlagMaster = configurationFlagMasterService.getById(id);
        DateAndTimeRequestDto dateAndTimeRequestDto1 = setDateAndTimeRequestDtoUpdate(configurationFlagMasterRequestDTO,configurationFlagMaster);
        configurationFlagMasterMapper.convertUpdateRequestToEntity(configurationFlagMasterRequestDTO,dateAndTimeRequestDto1,configurationFlagMaster);
        configurationFlagMaster = configurationFlagMasterService.update(configurationFlagMaster);
        ConfigurationFlagMasterResponseDTO configurationFlagMasterResponseDTO = configurationFlagMasterMapper.convertEntityToResponse(configurationFlagMaster);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,configurationFlagMasterResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to fetch ConfigurationFlag by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getById(@PathVariable("id") Long id){
        log.info("ENTRY - Fetch Configuration by id");
        ConfigurationFlagMaster configurationFlagMaster = configurationFlagMasterService.getById(id);
        ConfigurationFlagMasterResponseDTO configurationFlagMasterResponseDTO = configurationFlagMasterMapper.convertEntityToResponse(configurationFlagMaster);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,configurationFlagMasterResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to set Date, Time and Version in Create
     */
    private DateAndTimeRequestDto setDateAndTimeRequestDtoUpdate(ConfigurationFlagMasterRequestDTO configurationFlagMasterRequestDTO, ConfigurationFlagMaster configurationFlagMaster) {
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Update");
        DateAndTimeRequestDto dateAndTimeRequestDto1 = new DateAndTimeRequestDto();
        dateAndTimeRequestDto1.setCreatedBy(configurationFlagMaster.getCreatedBy());
        dateAndTimeRequestDto1.setCreatedDate(configurationFlagMaster.getCreatedDate());
        dateAndTimeRequestDto1.setLastModifiedBy(configurationFlagMasterRequestDTO.getUser());
        dateAndTimeRequestDto1.setLastModifiedDate(new Date());
        dateAndTimeRequestDto1.setVersion(configurationFlagMaster.getVersion() + 1);
        log.info("EXIT");
        return dateAndTimeRequestDto1;
    }

    /*
     * Method to set Date, Time and Version in Update
     */
    private DateAndTimeRequestDto setDateAndTimeRequestDtoCreate(ConfigurationFlagMasterRequestDTO configurationFlagMasterRequestDTO) {
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Create");
        DateAndTimeRequestDto dateAndTimeRequestDto1 = new DateAndTimeRequestDto();
        dateAndTimeRequestDto1.setCreatedBy(configurationFlagMasterRequestDTO.getUser());
        dateAndTimeRequestDto1.setCreatedDate(new Date());
        dateAndTimeRequestDto1.setLastModifiedBy(configurationFlagMasterRequestDTO.getUser());
        dateAndTimeRequestDto1.setLastModifiedDate(new Date());
        dateAndTimeRequestDto1.setVersion(1L);
        log.info("EXIT");
        return dateAndTimeRequestDto1;
    }

    /*
     * Method to validate unique Configuration Flag Code
     */
    @GetMapping(value = "/codeCheck",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO>checkCode(@RequestParam ("id") String id,@RequestParam("code") String code){
        log.info("ENTRY - Validate unique Configuration Flag Code");
        String codeCaps = code.toUpperCase();
        Boolean exists = false;
        try{
            if (id.isEmpty() || id.isBlank()){
                configurationFlagMasterService.validateUniqueConfigurationFlagAttributeSave(codeCaps);
            }else {
                configurationFlagMasterService.validateUniqueConfigurationFlagAttributeUpdate(codeCaps,Long.parseLong(id));
            }
        }catch (ServiceException serviceException){
            exists = true;
        }
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,exists,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }

    /*
     * Method to fetch all Storage Area for autocomplete
     */
    @GetMapping(value = "/fetchAllConfigurationFlagAutoComplete")
    public ResponseEntity<ResponseDTO> fetchAllConfigurationFlagAutoComplete(@RequestParam(name = "query",required = false) String query,
                                                                       @RequestParam(name = "branchId",required = false) Long branchId,
                                                                       @RequestParam(name = "status",required = false) String status){
        log.info("ENTRY - Fetch all Configuration Flag for autocomplete");
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QConfigurationFlagMaster.configurationFlagMaster.id.isNotNull());
        if (query != null && !query.isEmpty() && !query.isBlank()){
            predicates.add(QConfigurationFlagMaster.configurationFlagMaster.configurationFlag.containsIgnoreCase(query)
                    .or(QConfigurationFlagMaster.configurationFlagMaster.code.containsIgnoreCase(query)));
        }
        if (branchId != null){
            predicates.add(QConfigurationFlagMaster.configurationFlagMaster.branchMaster.id.eq(branchId));
        }
        if (status != null && !status.isEmpty() && !status.isBlank()){
            predicates.add(QConfigurationFlagMaster.configurationFlagMaster.status.eq(status));
        }
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("configurationFlag").ascending());
        Iterable<ConfigurationFlagMaster> configurationFlagMasterIterable = configurationFlagMasterService.fetchAllConfigurationFlag(predicateAll,pageable);
        Iterable<ConfigurationFlagMasterResponseDTO> configurationFlagMasterResponseDTOIterable = configurationFlagMasterMapper.convertConfigFlagMasterIterableToConfigFlagMasterDTOIterable(configurationFlagMasterIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,configurationFlagMasterResponseDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

}
