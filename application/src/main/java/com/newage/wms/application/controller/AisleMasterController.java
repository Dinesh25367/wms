package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.AisleMapper;
import com.newage.wms.application.dto.requestdto.AisleRequestDTO;
import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.responsedto.AisleResponseDTO;
import com.newage.wms.application.dto.responsedto.LocationResponseDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.entity.AisleMaster;
import com.newage.wms.service.AisleMasterService;
import com.newage.wms.service.exception.ServiceException;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Date;

@Log4j2
@RestController
@RequestMapping("/aisle")
@CrossOrigin("*")
public class AisleMasterController {

    @Autowired
    private AisleMasterService aisleMasterService;

    @Autowired
    private AisleMapper aisleMasterMapper;

    /*
     * Method to fetch all Aisle with sort, filter and pagination
     */
    @ApiOperation(value = "Fetch all Aisle with pagination", notes = " Api is used to fetch all Aisle with sort, filter and pagination ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping("/fetchAll")
    public ResponseEntity<ResponseDTO> fetchAllAisle(@QuerydslPredicate(root = AisleMaster.class) Predicate predicate,
                                                     @PageableDefault(sort = {"id"},direction = Sort.Direction.ASC,size = 20) @Parameter(hidden = true) Pageable pageable){
        log.info("ENTRY - Fetch all Aisle with sort, filter and pagination");
        Page<AisleMaster> aislePage = aisleMasterService.findAll(predicate,pageable);
        Page<AisleResponseDTO> aisleResponseDTOPage = aisleMasterMapper.convertAislePageToAisleResponsePage(aislePage);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,aisleResponseDTOPage,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     *Method to Create new Aisle Master
     */
    @ApiOperation(value ="Create Aisle", notes="Api is used to create Aisle")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully created") })
    @PostMapping(value="/save",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> createAisle(@Valid @RequestBody AisleRequestDTO aisleRequestDTO){
        log.info("ENTRY - Create new Aisle");
        aisleMasterService.validateUniqueAisleAttributeSave(aisleRequestDTO.getCode().toUpperCase(),Long.parseLong(aisleRequestDTO.getWareHouse().getId()));
        DateAndTimeRequestDto dateAndTimeRequestDto = this.setDateAndTimeRequestDtoCreate(aisleRequestDTO);
        AisleMaster aisleMaster = aisleMasterMapper.convertRequestDtoToEntity(aisleRequestDTO,dateAndTimeRequestDto);
        aisleMaster = aisleMasterService.saveAisle(aisleMaster);
        AisleResponseDTO aisleResponseDTO = aisleMasterMapper.convertEntityToDTO(aisleMaster);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.CREATED.value(), Boolean.TRUE,aisleResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to update Aisle by id
     */
    @ApiOperation(value = "Update Aisle", notes = " Api is used to update Aisle ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated") })
    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> updateAisleById(@Valid @RequestBody AisleRequestDTO aisleRequestDTO,
                                                       @PathVariable ("id") Long id){
        log.info("ENTRY - Update Aisle by Id");
        AisleMaster aisleMaster = aisleMasterService.getAisleById(id);
        aisleMasterService.validateUniqueAisleAttributeUpdate(aisleRequestDTO.getCode().toUpperCase(),id,Long.parseLong(aisleRequestDTO.getWareHouse().getId()));
        DateAndTimeRequestDto dateAndTimeRequestDto = setDateAndTimeRequestDtoUpdate(aisleRequestDTO,aisleMaster);
        aisleMasterMapper.convertUpdateRequestToEntity(aisleRequestDTO,aisleMaster,dateAndTimeRequestDto);
        aisleMaster = aisleMasterService.updateAisle(aisleMaster);
        AisleResponseDTO aisleResponseDTO = aisleMasterMapper.convertEntityToDTO(aisleMaster);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,aisleResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to fetch Aisle by id
     */
    @ApiOperation(value = "Fetch Aisle by Id", notes = " Api is used to fetch Aisle by Id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> fetchAisleById(@PathVariable("id") Long id){
        log.info("ENTRY - Fetch Aisle by Id");
        AisleResponseDTO aisleResponseDTO = aisleMasterMapper.convertEntityToDTO(aisleMasterService.getAisleById(id));
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,aisleResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to fetch all AisleMaster for auto complete
     */
    @GetMapping
    public ResponseEntity<ResponseDTO> fetchAllAisleAutoComplete(@RequestParam(name = "query",required = false) String query,
                                                                 @RequestParam(name = "warehouseId",required = false) Long warehouseId,
                                                                 @RequestParam(name = "zoneId",required = false) Long zoneId,
                                                                 @RequestParam(name = "userId",required = false) Long userId,
                                                                 @RequestParam(name = "branchId",required = false) Long branchId,
                                                                 @RequestParam(name = "status",required = false) String status){
        log.info("ENTRY - Fetch all AisleMaster for autocomplete");
        Iterable<AisleMaster> aisleMasterIterable = aisleMasterService.fetchAllAisle(query, warehouseId, zoneId, userId, branchId,status);
        Iterable<LocationResponseDTO.AisleDTO> aisleDTOIterable = aisleMasterMapper.convertAisleMasterIterableToAilseMasterDTOIterable(aisleMasterIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,aisleDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to validate unique Aisle Code
     */
    @GetMapping(value = "/codeCheck",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> checkCode(@RequestParam("id") String id,@RequestParam("code") String code,
                                                 @RequestParam("warehouseId") Long warehouseId){
        log.info("ENTRY - Validate unique Aisle Code");
        String codeCaps = code.toUpperCase();
        Boolean exists = false;
        try{
            if (id.isEmpty() || id.isBlank() ){
                aisleMasterService.validateUniqueAisleAttributeSave(codeCaps,warehouseId);
            }else {
                aisleMasterService.validateUniqueAisleAttributeUpdate(codeCaps,Long.parseLong(id),warehouseId);
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
    public DateAndTimeRequestDto setDateAndTimeRequestDtoCreate(@Valid AisleRequestDTO aisleRequestDto){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Create");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(aisleRequestDto.getUser());
        dateAndTimeRequestDto.setCreatedDate(new Date());
        dateAndTimeRequestDto.setLastModifiedBy(aisleRequestDto.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(1L);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

    /*
     * Method to set Date, Time and Version in Update
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoUpdate(AisleRequestDTO aisleRequestDTO, AisleMaster aisleMaster){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Update");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(aisleMaster.getCreatedBy());
        dateAndTimeRequestDto.setCreatedDate(aisleMaster.getCreatedDate());
        dateAndTimeRequestDto.setLastModifiedBy(aisleRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(aisleMaster.getVersion() + 1);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

}
