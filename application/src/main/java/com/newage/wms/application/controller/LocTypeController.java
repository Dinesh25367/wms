package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.LocTypeMapper;
import com.newage.wms.application.dto.requestdto.*;
import com.newage.wms.application.dto.responsedto.LocTypeResponseDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.entity.LocTypeMaster;
import com.newage.wms.entity.QLocTypeMaster;
import com.newage.wms.entity.QStorageAreaMaster;
import com.newage.wms.service.LocTypeMasterService;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
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
@RequestMapping("/locationType")
@CrossOrigin("*")
public class LocTypeController {

    @Autowired
    private LocTypeMasterService locTypeMasterService;

    @Autowired
    private LocTypeMapper locTypeMapper;

    /*
     * Method to fetch all LocType with sort, filter and pagination
     */
    @ApiOperation(value = "Fetch all LocType with pagination", notes = " Api is used to fetch all LocType with sort, filter and pagination ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping("/fetchAll")
    public ResponseEntity<ResponseDTO> fetchAllLocType(@QuerydslPredicate(root = LocTypeMaster.class) Predicate predicate,
                                                       @PageableDefault(sort = {"id"},direction = Sort.Direction.ASC,size = 20) Pageable pageable){
        log.info("ENTRY - Fetch all LocType with sort, filter and pagination");
        Page<LocTypeMaster> locTypeMasterPage = locTypeMasterService.findAll(predicate,pageable);
        Page<LocTypeResponseDTO> locTypeResponseDTOPage = locTypeMapper.convertLocTypePageToLocTypeResponsePage(locTypeMasterPage);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,locTypeResponseDTOPage,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     *Method to Create new LocType
     */
    @ApiOperation(value = "Create LocType", notes = " Api is used to create LocationType ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully created") })
    @PostMapping(value="/save",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> createLocType(@Valid @RequestBody LocTypeRequestDTO locTypeRequestDTO){
        log.info("ENTRY - Create new LocType");
        locTypeMasterService.validateUniqueLocTypeAttributeSave(locTypeRequestDTO.getCode().toUpperCase());
        DateAndTimeRequestDto dateAndTimeRequestDto = this.setDateAndTimeRequestDtoCreate(locTypeRequestDTO);
        LocTypeMaster locTypeMaster = locTypeMapper.convertRequestDtoToEntity(locTypeRequestDTO,dateAndTimeRequestDto);
        locTypeMaster = locTypeMasterService.saveLocType(locTypeMaster);
        LocTypeResponseDTO locTypeResponseDTO = locTypeMapper.convertEntityToDTO(locTypeMaster);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.CREATED.value(), Boolean.TRUE,locTypeResponseDTO ,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     *Method to Update new LocType
     */
    @ApiOperation(value = "Update LocationType", notes = "Api is used to update Zone")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated")})
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> updateLocTypeById(@Valid @RequestBody LocTypeRequestDTO locTypeRequestDTO,
                                                         @PathVariable ("id") Long id){
        log.info("ENTRY - Update Zone by Id");
        locTypeMasterService.validateUniqueLocTypeAttributeUpdate(locTypeRequestDTO.getCode().toUpperCase(),id);
        LocTypeMaster locTypeMaster=locTypeMasterService.getLocTypeById(id);
        DateAndTimeRequestDto dateAndTimeRequestDto=setDateAndTimeRequestDtoUpdate(locTypeRequestDTO,locTypeMaster);
        locTypeMapper.convertUpdateRequestToEntity(locTypeRequestDTO,locTypeMaster,dateAndTimeRequestDto);
        locTypeMaster=locTypeMasterService.updateLocType(locTypeMaster);
        LocTypeResponseDTO locTypeResponseDTO = locTypeMapper.convertEntityToDTO(locTypeMaster);
        ResponseDTO responseDto=new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,locTypeResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to fetch LocType by id
     */
    @ApiOperation(value = "Fetch LocType by Id", notes = " Api is used to fetch LocType by Id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> fetchZoneById(@PathVariable("id") Long id){
        log.info("ENTRY - Fetch LocType by Id");
        LocTypeResponseDTO locTypeResponseDTO = locTypeMapper.convertEntityToDTO(locTypeMasterService.getLocTypeById(id));
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,locTypeResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to fetch all LocType for autocomplete
     */
    @GetMapping
    public ResponseEntity<ResponseDTO> fetchAllLocTypeAutoComplete(@RequestParam(name = "query",required = false) String query,
                                                                   @RequestParam(name = "branchId",required = false) Long branchId,
                                                                   @RequestParam(name = "status",required = false) String status){
        log.info("ENTRY - Fetch all Location Type for autocomplete");
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QLocTypeMaster.locTypeMaster.id.isNotNull());
        if (query != null && !query.isEmpty() && !query.isBlank()){
            predicates.add(QLocTypeMaster.locTypeMaster.name.containsIgnoreCase(query)
                    .or(QLocTypeMaster.locTypeMaster.code.containsIgnoreCase(query)));
        }
        if (branchId != null){
            predicates.add(QLocTypeMaster.locTypeMaster.branchMaster.id.eq(branchId));
        }
        if (status != null && !status.isEmpty() && !status.isBlank()){
            predicates.add(QLocTypeMaster.locTypeMaster.status.eq(status));
        }
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("name").ascending());
        Iterable<LocTypeMaster> locTypeMasterIterable = locTypeMasterService.fetchAllLocationType(predicateAll,pageable);
        Iterable<LocTypeResponseDTO> locTypeDTOIterable = locTypeMapper.convertLocTypeMasterIterableToLocTypeMasterDTOIterable(locTypeMasterIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,locTypeDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to CodeCheck LocType by id
     */
    @GetMapping(value = "/codeCheck",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> checkCode(@RequestParam("id") String id,@RequestParam("code") String code){
        String codeCaps = code.toUpperCase();
        Boolean exists = false;
        try{
            if (id.isBlank() || id.isEmpty() ){
                locTypeMasterService.validateUniqueLocTypeAttributeSave(codeCaps);
            }else {
                locTypeMasterService.validateUniqueLocTypeAttributeUpdate(codeCaps,Long.parseLong(id));
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
    public DateAndTimeRequestDto setDateAndTimeRequestDtoCreate(LocTypeRequestDTO locTypeRequestDTO){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Create");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(locTypeRequestDTO.getUser());
        dateAndTimeRequestDto.setCreatedDate(new Date());
        dateAndTimeRequestDto.setLastModifiedBy(locTypeRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(1L);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

    /*
     * Method to set Date, Time and Version in Update
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoUpdate(LocTypeRequestDTO  locTypeRequestDTO, LocTypeMaster locTypeMaster){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Update");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(locTypeMaster.getCreatedBy());
        dateAndTimeRequestDto.setCreatedDate(locTypeMaster.getCreatedDate());
        dateAndTimeRequestDto.setLastModifiedBy(locTypeRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(locTypeMaster.getVersion() + 1);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

}
