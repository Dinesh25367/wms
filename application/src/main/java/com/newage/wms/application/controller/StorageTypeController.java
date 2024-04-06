package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.StorageTypeMapper;
import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.StorageTypeRequestDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.application.dto.responsedto.StorageTypeResponseDTO;
import com.newage.wms.entity.QStorageTypeMaster;
import com.newage.wms.entity.StorageTypeMaster;
import com.newage.wms.service.StorageTypeMasterService;
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
@RequestMapping("/storageType")
@CrossOrigin("*")
public class StorageTypeController {

    @Autowired
    private StorageTypeMasterService storageTypeMasterService;

    @Autowired
    private StorageTypeMapper storageTypeMapper;

    /*
     * Method to fetch all StorageType with sort, filter and pagination
     */
    @ApiOperation(value = "Fetch all StorageType with pagination", notes = " Api is used to fetch all StorageType with sort, filter and pagination ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping("/fetchAll")
    public ResponseEntity<ResponseDTO> fetchAllStorageType(@QuerydslPredicate(root = StorageTypeMaster.class) Predicate predicate,
                                                           @PageableDefault(sort = {"id"},direction = Sort.Direction.ASC,size = 20) Pageable pageable){
        log.info("ENTRY - Fetch all Zone with sort, filter and pagination");
        Page<StorageTypeMaster> storageTypeMasterPage = storageTypeMasterService.findAll(predicate,pageable);
        Page<StorageTypeResponseDTO> storageTypeResponseDTOPage = storageTypeMapper.convertStoragePageToStorageResponsePage(storageTypeMasterPage);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,storageTypeResponseDTOPage,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     *Method to Create new StorageType
     */
    @ApiOperation(value = "Create Storage Type", notes = " Api is used to create StorageType ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully created") })
    @PostMapping(value="/save",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> createStorageType(@Valid @RequestBody StorageTypeRequestDTO storageTypeRequestDTO){
        log.info("ENTRY - Create new StorageType");
        storageTypeMasterService.validateUniqueStorageTypeAttributeSave(storageTypeRequestDTO.getCode().toUpperCase());
        DateAndTimeRequestDto dateAndTimeRequestDto = this.setDateAndTimeRequestDtoCreate(storageTypeRequestDTO);
        StorageTypeMaster storageType = storageTypeMapper.convertRequestDtoToEntity(storageTypeRequestDTO,dateAndTimeRequestDto);
        storageType = storageTypeMasterService.saveStorageType(storageType);
        StorageTypeResponseDTO storageTypeResponseDTO = storageTypeMapper.convertEntityToDTO(storageType);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.CREATED.value(), Boolean.TRUE,storageTypeResponseDTO ,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     *Method to Update StorageType by id
     */
    @ApiOperation(value = "Update StorageType", notes = "Api is used to update Zone")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated")})
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> updateStorageTypeById(@Valid @RequestBody StorageTypeRequestDTO storageTypeRequestDTO,
                                                             @PathVariable ("id") Long id){
        log.info("ENTRY - Update Zone by Id");
        storageTypeMasterService.validateUniqueStorageTypeAttributeUpdate(storageTypeRequestDTO.getCode().toUpperCase(),id);
        StorageTypeMaster storageTypeMaster=storageTypeMasterService.getStorageTypeMasterById(id);
        DateAndTimeRequestDto dateAndTimeRequestDto=setDateAndTimeRequestDtoUpdate(storageTypeRequestDTO,storageTypeMaster);
        storageTypeMapper.convertUpdateRequestToEntity(storageTypeRequestDTO,storageTypeMaster,dateAndTimeRequestDto);
        storageTypeMaster=storageTypeMasterService.updateStorageType(storageTypeMaster);
        StorageTypeResponseDTO storageTypeResponseDTO = storageTypeMapper.convertEntityToDTO(storageTypeMaster);
        ResponseDTO responseDto=new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,storageTypeResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to fetch StorageType by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> fetchStorageTypeById(@PathVariable("id") Long id){
        log.info("ENTRY - Fetch Storage Type by id");
        StorageTypeMaster storageTypeMaster = storageTypeMasterService.getStorageTypeMasterById(id);
        StorageTypeResponseDTO storageTypeResponseDTO = storageTypeMapper.convertEntityToDTO(storageTypeMaster);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,storageTypeResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to fetch all StorageType for autocomplete
     */
    @GetMapping
    public ResponseEntity<ResponseDTO> fetchAllStorageTypeAutoComplete(@RequestParam(name = "query",required = false) String query,
                                                                       @RequestParam(name = "branchId",required = false) Long branchId,
                                                                       @RequestParam(name = "status",required = false) String status){
        log.info("ENTRY - Fetch all Storage Type with filter");
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QStorageTypeMaster.storageTypeMaster.id.isNotNull());
        if (query != null && !query.isEmpty() && !query.isBlank()){
            predicates.add(QStorageTypeMaster.storageTypeMaster.name.containsIgnoreCase(query)
                    .or(QStorageTypeMaster.storageTypeMaster.code.containsIgnoreCase(query)));
        }
        if (branchId != null){
            predicates.add(QStorageTypeMaster.storageTypeMaster.branchMaster.id.eq(branchId));
        }
        if (status != null && !status.isEmpty() && !status.isBlank()){
            predicates.add(QStorageTypeMaster.storageTypeMaster.status.eq(status));
        }
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("name").ascending());
        Iterable<StorageTypeMaster> storageTypeMasterIterable = storageTypeMasterService.fetchAllStorageType(predicateAll,pageable);
        Iterable<StorageTypeResponseDTO> storageTypeDTOIterable = storageTypeMapper.convertStorageMasterIterableToStorageMasterDTOIterable(storageTypeMasterIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,storageTypeDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to validate unique StorageType Code
     */
    @GetMapping(value = "/codeCheck",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> checkCode(@RequestParam("id") String id,@RequestParam("code") String code){
        log.info("ENTRY - Validate unique Rack Code");
        String codeCaps = code.toUpperCase();
        Boolean exists = false;
        try{
            if (id.isEmpty() || id.isBlank() ){
                storageTypeMasterService.validateUniqueStorageTypeAttributeSave(codeCaps);
            }else {
                storageTypeMasterService.validateUniqueStorageTypeAttributeUpdate(codeCaps,Long.parseLong(id));
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
    public DateAndTimeRequestDto setDateAndTimeRequestDtoCreate(StorageTypeRequestDTO storageTypeRequestDTO){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Create");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(storageTypeRequestDTO.getUser());
        dateAndTimeRequestDto.setCreatedDate(new Date());
        dateAndTimeRequestDto.setLastModifiedBy(storageTypeRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(1L);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

    public DateAndTimeRequestDto setDateAndTimeRequestDtoUpdate(StorageTypeRequestDTO  storageTypeRequestDTO, StorageTypeMaster storageTypeMaster){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Update");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(storageTypeMaster.getCreatedBy());
        dateAndTimeRequestDto.setCreatedDate(storageTypeMaster.getCreatedDate());
        dateAndTimeRequestDto.setLastModifiedBy(storageTypeRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(storageTypeMaster.getVersion() + 1);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

}
