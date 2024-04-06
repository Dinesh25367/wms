package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.DoorMapper;
import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.DoorRequestDTO;
import com.newage.wms.application.dto.responsedto.DoorResponseDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.application.dto.responsedto.ZoneWMSResponseDTO;
import com.newage.wms.entity.DoorMaster;
import com.newage.wms.service.DoorMasterService;
import com.newage.wms.service.exception.ServiceException;
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
import java.util.Date;

@Log4j2
@RestController
@RequestMapping("/door")
@CrossOrigin("*")
public class DoorMasterController {

    @Autowired
    private DoorMasterService doorMasterService;

    @Autowired
    private DoorMapper doorMapper;

    /*
     * Method to fetch all Door with sort, filter and pagination
     */
    @ApiOperation(value = "Fetch all Door with pagination", notes = " Api is used to fetch all Door with sort, filter and pagination ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping("/fetchAll")
    public ResponseEntity<ResponseDTO> fetchAllDoor(@QuerydslPredicate(root = DoorMaster.class) Predicate predicate,
                                                    @PageableDefault(sort = {"id"},direction = Sort.Direction.ASC,size = 20) Pageable pageable){
        log.info("ENTRY - Fetch all Door with sort, filter and pagination");
        Page<DoorMaster> doorMasterPage = doorMasterService.findAll(predicate,pageable);
        Page<DoorResponseDTO> doorResponseDTOPage = doorMapper.convertDoorPageToDoorResponsePage(doorMasterPage);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,doorResponseDTOPage,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     *Method to Create new Door
     */
    @ApiOperation(value = "Create Door", notes = " Api is used to create Door ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully created") })
    @PostMapping(value="/save",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> createDoor(@Valid @RequestBody DoorRequestDTO doorRequestDTO){
        log.info("ENTRY - Create new Door");
        doorMasterService.validateUniqueDoorAttributeSave(doorRequestDTO.getCode().toUpperCase());
        DateAndTimeRequestDto dateAndTimeRequestDto = this.setDateAndTimeRequestDtoCreate(doorRequestDTO);
        DoorMaster doorMaster = doorMapper.convertRequestDtoToEntity(doorRequestDTO,dateAndTimeRequestDto);
        doorMaster = doorMasterService.saveDoor(doorMaster);
        DoorResponseDTO doorResponseDTO = doorMapper.convertEntityToDTO(doorMaster);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.CREATED.value(), Boolean.TRUE,doorResponseDTO ,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     *Method to Update new Door
     */
    @ApiOperation(value = "Update Door", notes = "Api is used to update Zone")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated")})
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> updateDoorById(@Valid @RequestBody DoorRequestDTO doorRequestDTO,
                                                      @PathVariable ("id") Long id){
        log.info("ENTRY - Update Door by Id");
        doorMasterService.validateUniqueDoorAttributeUpdate(doorRequestDTO.getCode().toUpperCase(),id);
        DoorMaster doorMaster=doorMasterService.findById(id);
        DateAndTimeRequestDto dateAndTimeRequestDto=setDateAndTimeRequestDtoUpdate(doorRequestDTO,doorMaster);
        doorMapper.convertUpdateRequestToEntity(doorRequestDTO,doorMaster,dateAndTimeRequestDto);
        doorMaster=doorMasterService.updateDoor(doorMaster);
        DoorResponseDTO doorResponseDTO = doorMapper.convertEntityToDTO(doorMaster);
        ResponseDTO responseDto=new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,doorResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to fetch Door by id
     */
    @ApiOperation(value = "Fetch Door by Id", notes = " Api is used to fetch Door by Id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> fetchDoorById(@PathVariable("id") Long id){
        log.info("ENTRY - Fetch Door by Id");
        DoorResponseDTO doorResponseDTO = doorMapper.convertEntityToDTO(doorMasterService.findById(id));
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,doorResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to fetch all Door for autoComplete
     */
    @GetMapping(value = "/autoComplete",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> findAllAutoComplete(@RequestParam(name = "query",required = false) String query,
                                                           @RequestParam(name = "status",required = false) String status,
                                                           @RequestParam(name = "branchId",required = false) Long branchId){
        log.info("ENTRY - fetch all Door for autoComplete");
        Iterable<DoorMaster> doorMasterIterable = doorMasterService.findAllAutoComplete(query,status,branchId);
        Iterable<ZoneWMSResponseDTO.DoorDTO> doorDTOIterable = doorMapper.convertEntityIterableToResponseIterable(doorMasterIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,doorDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to CodeCheck Door by id
     */
    @GetMapping(value = "/codeCheck",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> checkCode(@RequestParam("id") String id,@RequestParam("code") String code){
        String codeCaps = code.toUpperCase();
        Boolean exists = false;
        try{
            if (id.isBlank() || id.isEmpty() ){
                doorMasterService.validateUniqueDoorAttributeSave(codeCaps);
            }else {
                doorMasterService.validateUniqueDoorAttributeUpdate(codeCaps,Long.parseLong(id));
            }
        }catch (ServiceException serviceException){
            exists=true;
        }
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,exists,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to set Date, Time and Version in Update
     */
    private DateAndTimeRequestDto setDateAndTimeRequestDtoUpdate(DoorRequestDTO doorRequestDTO, DoorMaster doorMaster) {
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Update");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(doorMaster.getCreatedBy());
        dateAndTimeRequestDto.setCreatedDate(doorMaster.getCreatedDate());
        dateAndTimeRequestDto.setLastModifiedBy(doorRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(doorMaster.getVersion() + 1);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

    /*
     * Method to set Date, Time and Version in Create
     */
    private DateAndTimeRequestDto setDateAndTimeRequestDtoCreate(DoorRequestDTO doorRequestDTO) {
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Create");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(doorRequestDTO.getUser());
        dateAndTimeRequestDto.setCreatedDate(new Date());
        dateAndTimeRequestDto.setLastModifiedBy(doorRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(1L);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

}

