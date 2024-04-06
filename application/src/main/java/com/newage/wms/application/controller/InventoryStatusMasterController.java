package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.InventoryStatusMapper;
import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.InventoryStatusRequestDTO;
import com.newage.wms.application.dto.responsedto.InventoryStatusResponseDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.entity.InventoryStatusMaster;
import com.newage.wms.entity.QInventoryStatusMaster;
import com.newage.wms.service.InventoryStatusMasterService;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/inventoryStatus")
@CrossOrigin("*")
public class InventoryStatusMasterController {
    @Autowired
    private InventoryStatusMasterService inventoryStatusMasterService;

    @Autowired
    private InventoryStatusMapper inventoryStatusMapper;

    @ApiOperation(value = "save Inventory Status master",notes = "Api is used to save Inventory status master")
    @ApiResponses(value = {@ApiResponse(code =200,message="successfully saved")})
    @PostMapping(value = "/save")
    public ResponseEntity<ResponseDTO> save(@Valid @RequestBody InventoryStatusRequestDTO inventoryStatusRequestDTO){
        log.info("ENTRY - Create new InventoryStatus Master");
        inventoryStatusMasterService.validateUniqueInventoryStatusAttributeSave(inventoryStatusRequestDTO.getCode().toUpperCase());
        DateAndTimeRequestDto dateAndTimeRequestDto=this.setDateAndTimeRequestDtoCreate(inventoryStatusRequestDTO);
        InventoryStatusMaster inventoryStatusMaster=inventoryStatusMapper.convertRequestToEntity(inventoryStatusRequestDTO,dateAndTimeRequestDto);
        inventoryStatusMaster=inventoryStatusMasterService.saveInventory(inventoryStatusMaster);
        InventoryStatusResponseDTO inventoryStatusResponseDTO=inventoryStatusMapper.convertEntityToResponse(inventoryStatusMaster);
        ResponseDTO responseDTO= new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,inventoryStatusResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseDTO> getInventoryById(@PathVariable ("id") Long id){
        log.info("ENTRY - Get InventoryStatus By Id");
        InventoryStatusResponseDTO inventoryStatusResponseDTO =inventoryStatusMapper.convertEntityToResponse(inventoryStatusMasterService.getById(id));
        ResponseDTO responseDTO=new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,inventoryStatusResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }

    @ApiOperation(value = "List Inventory Status master",notes = "Api is used to Listed Inventory status master")
    @ApiResponses(value = {@ApiResponse(code =200,message="Listed successfully")})
    @GetMapping("/fetchAll")
    public ResponseEntity<ResponseDTO>fetchAll(@QuerydslPredicate(root = InventoryStatusMaster.class)Predicate predicate,
                                               @PageableDefault(sort = {"id"},direction=Sort.Direction.ASC,size = 20)@Parameter(hidden = true)Pageable pageable){

        log.info("ENTRY - Fetch All InventoryStatusMaster with sort,filter and pagination");
        Page<InventoryStatusMaster> inventoryStatusMasterPage=inventoryStatusMasterService.findAll(predicate, pageable);
        Page<InventoryStatusResponseDTO> inventoryStatusResponseDTOPage=inventoryStatusMapper.convertInventoryStatusPageToResponsePage(inventoryStatusMasterPage);
        ResponseDTO responseDTO=new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,inventoryStatusResponseDTOPage,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<ResponseDTO> update(@Valid@RequestBody InventoryStatusRequestDTO inventoryStatusRequestDTO,
                                              @PathVariable ("id") Long id){

        log.info("ENTRY - update InventoryStatus By Id");
        inventoryStatusMasterService.validateUniqueInventoryStatusAttributeUpdate(inventoryStatusRequestDTO.getCode().toUpperCase(),id);
        InventoryStatusMaster inventoryStatusMaster=inventoryStatusMasterService.getById(id);
        DateAndTimeRequestDto dateAndTimeRequestDto=setDateAndTimeRequestDtoUpdate(inventoryStatusRequestDTO,inventoryStatusMaster);
        inventoryStatusMapper.convertUpdateRequestToEntity(inventoryStatusRequestDTO,inventoryStatusMaster,dateAndTimeRequestDto);
        inventoryStatusMaster=inventoryStatusMasterService.updateInventory(inventoryStatusMaster);
        InventoryStatusResponseDTO inventoryStatusResponseDTO=inventoryStatusMapper.convertEntityToResponse(inventoryStatusMaster);
        ResponseDTO responseDTO=new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,inventoryStatusResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }

    /*
     * Method to validate unique InventoryStatus Code
     */
    @GetMapping(value = "/codeCheck",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> checkCode(@RequestParam("id")String id,@RequestParam("code")String code){
        log.info("ENTRY - Validate unique InventoryStatus Code");
        String codeCaps=code.toUpperCase();
        Boolean exists=false;
        try {
            if (id.isEmpty()|| id.isBlank()){
                inventoryStatusMasterService.validateUniqueInventoryStatusAttributeSave(codeCaps);
            }else {
                inventoryStatusMasterService.validateUniqueInventoryStatusAttributeUpdate(codeCaps,Long.parseLong(id));
            }

        }catch (ServiceException serviceException){
            exists=true;
        }
        ResponseDTO responseDTO=new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,exists,null);
        log.info("ExIT");
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping("/autoComplete")
    public ResponseEntity<ResponseDTO> inventoryStatusAutoComplete(@RequestParam(name = "query", required = false) String query) {
        log.info("ENTRY - Fetch all Inventory Status autoComplete");

        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QInventoryStatusMaster.inventoryStatusMaster.id.isNotNull());

        if (query != null && !query.isBlank() && !query.isEmpty()) {
            QInventoryStatusMaster inventoryStatusMaster = QInventoryStatusMaster.inventoryStatusMaster;
            predicates.add(
                    inventoryStatusMaster.name.containsIgnoreCase(query)
                            .or(inventoryStatusMaster.code.containsIgnoreCase(query))
            );
        }

        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("id").ascending());

        Iterable<InventoryStatusMaster> inventoryStatusMasterIterable =
                inventoryStatusMasterService.findAll(predicateAll, pageable);

        Iterable<InventoryStatusResponseDTO> inventoryStatusResponseDTOIterable =
            inventoryStatusMapper.convertInventoryStatusIterableToResponseIterable(inventoryStatusMasterIterable);

        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE, inventoryStatusResponseDTOIterable, null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }

    /*
     * Method to set Date, Time and Version in Create
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoCreate(@Valid InventoryStatusRequestDTO inventoryStatusRequestDTO){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Create");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(inventoryStatusRequestDTO.getUser());
        dateAndTimeRequestDto.setCreatedDate(new Date());
        dateAndTimeRequestDto.setLastModifiedBy(inventoryStatusRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(1L);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

    public DateAndTimeRequestDto setDateAndTimeRequestDtoUpdate(@Valid InventoryStatusRequestDTO inventoryStatusRequestDTO, InventoryStatusMaster inventoryStatusMaster){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Create");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(inventoryStatusRequestDTO.getUser());
        dateAndTimeRequestDto.setCreatedDate(new Date());
        dateAndTimeRequestDto.setLastModifiedBy(inventoryStatusRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(inventoryStatusMaster.getVersion() + 1);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

}
