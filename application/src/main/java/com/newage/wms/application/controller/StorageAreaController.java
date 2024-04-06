package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.StorageAreaMapper;
import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.StorageAreaRequestDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.application.dto.responsedto.StorageAreaResponseDTO;
import com.newage.wms.entity.QStorageAreaMaster;
import com.newage.wms.entity.StorageAreaMaster;
import com.newage.wms.service.StorageAreaMasterService;
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
@RequestMapping("/storageArea")
@CrossOrigin("*")
public class StorageAreaController {

    @Autowired
    private StorageAreaMasterService storageAreaMasterService;

    @Autowired
    private StorageAreaMapper storageAreaMapper;

    /*
     * Method to fetch all StorageArea with sort, filter and pagination
     */
    @ApiOperation(value = "Fetch all StorageArea with pagination", notes = " Api is used to fetch all StorageArea with sort, filter and pagination ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping("/fetchAll")
    public ResponseEntity<ResponseDTO> fetchAllStorageArea(@QuerydslPredicate(root = StorageAreaMaster.class) Predicate predicate,
                                                           @PageableDefault(sort = {"id"},direction = Sort.Direction.ASC,size = 20) Pageable pageable){
        log.info("ENTRY - Fetch all StorageArea with sort, filter and pagination");
        Page<StorageAreaMaster> storageAreaMasterPage = storageAreaMasterService.findAll(predicate,pageable);
        Page<StorageAreaResponseDTO> storageAreaResponseDTOPage = storageAreaMapper.convertStoragePageToStorageResponsePage(storageAreaMasterPage);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,storageAreaResponseDTOPage,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     *Method to Create new StorageType
     */
    @ApiOperation(value = "Create Storage Type", notes = " Api is used to create StorageType ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully created") })
    @PostMapping(value="/save",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> createStorageType(@Valid @RequestBody StorageAreaRequestDTO storageAreaRequestDTO){
        log.info("ENTRY - Create new StorageType");
        storageAreaMasterService.validateUniqueStorageAreaAttributeSave(storageAreaRequestDTO.getCode().toUpperCase());
        DateAndTimeRequestDto dateAndTimeRequestDto = this.setDateAndTimeRequestDtoCreate(storageAreaRequestDTO);
        StorageAreaMaster storageArea = storageAreaMapper.convertRequestDtoToEntity(storageAreaRequestDTO,dateAndTimeRequestDto);
        storageArea = storageAreaMasterService.saveStorageArea(storageArea);
        StorageAreaResponseDTO storageAreaResponseDTO = storageAreaMapper.convertEntityToDTO(storageArea);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.CREATED.value(), Boolean.TRUE,storageAreaResponseDTO ,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     *Method to Update StorageType by id
     */
    @ApiOperation(value = "Update StorageType", notes = "Api is used to update Zone")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated")})
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> updateStorageTypeById(@Valid @RequestBody StorageAreaRequestDTO storageAreaRequestDTO,
                                                             @PathVariable ("id") Long id){
        log.info("ENTRY - Update Zone by Id");
        storageAreaMasterService.validateUniqueStorageAreaAttributeUpdate(storageAreaRequestDTO.getCode().toUpperCase(),id);
        StorageAreaMaster storageAreaMaster=storageAreaMasterService.getStorageAreaMasterById(id);
        DateAndTimeRequestDto dateAndTimeRequestDto=setDateAndTimeRequestDtoUpdate(storageAreaRequestDTO,storageAreaMaster);
        storageAreaMapper.convertUpdateRequestToEntity(storageAreaRequestDTO,storageAreaMaster,dateAndTimeRequestDto);
        storageAreaMaster =storageAreaMasterService.updateStorageArea(storageAreaMaster);
        StorageAreaResponseDTO storageAreaResponseDTO = storageAreaMapper.convertEntityToDTO(storageAreaMaster);
        ResponseDTO responseDto=new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,storageAreaResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to fetch Storage Area by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> fetchStorageAreaById(@PathVariable("id") Long id){
        log.info("ENTRY - Fetch Storage Area by id");
        StorageAreaMaster storageAreaMaster = storageAreaMasterService.getStorageAreaMasterById(id);
        StorageAreaResponseDTO storageAreaResponseDTO = storageAreaMapper.convertEntityToDTO(storageAreaMaster);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,storageAreaResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to fetch all Storage Area for autocomplete
     */
    @GetMapping
    public ResponseEntity<ResponseDTO> fetchAllStorageAreaAutoComplete(@RequestParam(name = "query",required = false) String query,
                                                                       @RequestParam(name = "branchId",required = false) Long branchId,
                                                                       @RequestParam(name = "status",required = false) String status){
        log.info("ENTRY - Fetch all Storage Area for autocomplete");
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QStorageAreaMaster.storageAreaMaster.id.isNotNull());
        if (query != null && !query.isEmpty() && !query.isBlank()){
            predicates.add(QStorageAreaMaster.storageAreaMaster.name.containsIgnoreCase(query)
                    .or(QStorageAreaMaster.storageAreaMaster.code.containsIgnoreCase(query)));
        }
        if (branchId != null){
            predicates.add(QStorageAreaMaster.storageAreaMaster.branchMaster.id.eq(branchId));
        }
        if (status != null && !status.isEmpty() && !status.isBlank()){
            predicates.add(QStorageAreaMaster.storageAreaMaster.status.eq(status));
        }
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("name").ascending());
        Iterable<StorageAreaMaster> storageAreaMasterIterable = storageAreaMasterService.fetchAllStorageArea(predicateAll,pageable);
        Iterable<StorageAreaResponseDTO> storageAreaDTOIterable = storageAreaMapper.convertStorageMasterIterableToStorageMasterDTOIterable(storageAreaMasterIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,storageAreaDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to validate unique StorageType Code
     */
    @GetMapping(value = "/codeCheck",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> checkCode(@RequestParam("id") String id,@RequestParam("code") String code){
        log.info("ENTRY - Validate unique Storage Code");
        String codeCaps = code.toUpperCase();
        Boolean exists = false;
        try{
            if (id.isEmpty() || id.isBlank() ){
                storageAreaMasterService.validateUniqueStorageAreaAttributeSave(codeCaps);
            }else {
                storageAreaMasterService.validateUniqueStorageAreaAttributeUpdate(codeCaps,Long.parseLong(id));
            }
        }catch (ServiceException serviceException){
            exists=true;
        }
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,exists,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to set Date, Time and Version in Create
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoCreate(StorageAreaRequestDTO storageAreaRequestDTO){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Create");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(storageAreaRequestDTO.getUser());
        dateAndTimeRequestDto.setCreatedDate(new Date());
        dateAndTimeRequestDto.setLastModifiedBy(storageAreaRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(1L);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

    public DateAndTimeRequestDto setDateAndTimeRequestDtoUpdate(StorageAreaRequestDTO  storageAreaRequestDTO, StorageAreaMaster storageAreaMaster){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Update");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(storageAreaMaster.getCreatedBy());
        dateAndTimeRequestDto.setCreatedDate(storageAreaMaster.getCreatedDate());
        dateAndTimeRequestDto.setLastModifiedBy(storageAreaRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(storageAreaMaster.getVersion() + 1);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

}
