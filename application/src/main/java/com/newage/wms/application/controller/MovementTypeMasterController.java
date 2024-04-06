package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.MovementTypeMapper;
import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.MovementTypeRequestDTO;
import com.newage.wms.application.dto.responsedto.MovementTypeResponseDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.entity.MovementTypeMaster;
import com.newage.wms.entity.QAisleMaster;
import com.newage.wms.entity.QMovementTypeMaster;
import com.newage.wms.service.MovementTypeMasterService;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
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
@RequestMapping("/movementType")
@CrossOrigin("*")
public class MovementTypeMasterController {

    @Autowired
    private MovementTypeMasterService movementTypeMasterService;

    @Autowired
    private MovementTypeMapper movementTypeMapper;



    /*
     * Method to fetch all MovementTypeMaster for autocomplete
     */
    @GetMapping("/autoComplete")
    public ResponseEntity<ResponseDTO> fetchAllAutoComplete(@RequestParam(name = "query",required = false) String query,
                                                            @RequestParam(name = "warehouseInOut",required = false) String warehouseInOut){
        log.info("ENTRY - Fetch all MovementTypeMaster for autocomplete");
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QMovementTypeMaster.movementTypeMaster.id.isNotNull());
        if (query != null && !query.isEmpty() && !query.isBlank()){
            predicates.add(QMovementTypeMaster.movementTypeMaster.code.containsIgnoreCase(query));
        }
        if (warehouseInOut != null && !warehouseInOut.isEmpty() && !warehouseInOut.isBlank()){
            predicates.add(QMovementTypeMaster.movementTypeMaster.warehouseInOut.eq(warehouseInOut));
        }
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("code").ascending());
        Iterable<MovementTypeMaster> movementTypeMasterIterable = movementTypeMasterService.getAllAutoComplete(predicateAll,pageable);
        Iterable<TrnResponseDTO.TrnHeaderAsnDTO.MovementTypeDTO> movementTypeDTOIterable = movementTypeMapper.convertEntityIterableToAutoCompleteResponseIterable(movementTypeMasterIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,movementTypeDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }
    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> save(@Valid @RequestBody MovementTypeRequestDTO movementTypeRequestDTO){
        log.info("ENTRY - Create a new Movement Type Master");
        DateAndTimeRequestDto dateAndTimeRequestDto = this.setDateAndTimeRequestDtoCreate(movementTypeRequestDTO);
        MovementTypeMaster movementTypeMaster = movementTypeMapper.convertRequestToEntity(movementTypeRequestDTO,dateAndTimeRequestDto);
        movementTypeMaster = movementTypeMasterService.save(movementTypeMaster);
        MovementTypeResponseDTO movementTypeResponseDTO = movementTypeMapper.convertEntityToResponse(movementTypeMaster);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,movementTypeResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }

    /*
     * Method to fetch all MovementTypeMaster
     */
    @GetMapping
    public ResponseEntity<ResponseDTO>fetchAll(@QuerydslPredicate(root = MovementTypeMaster.class)Predicate predicate,
                                               @PageableDefault(sort ={"id"},direction = Sort.Direction.ASC,size = 20)@Parameter(hidden = true)Pageable pageable){
        log.info("ENTRY - Fetch All MovementTypeMaster with sort,filter and pagination");
        Page<MovementTypeMaster> movementTypeMasterPage = movementTypeMasterService.findAll(predicate, pageable);
        Page<MovementTypeResponseDTO> movementTypeResponseDTOPage = movementTypeMapper.convertMovementTypePageToResponsePage(movementTypeMasterPage);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,movementTypeResponseDTOPage,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }

    /*
     * Method to update MovementTypeMaster
     */
    @PutMapping(value = "{id}")
    public ResponseEntity<ResponseDTO> update(@Valid@RequestBody MovementTypeRequestDTO movementTypeRequestDTO,
                                              @PathVariable ("id") Long id){
        log.info("ENTRY - Update MovementType by Id");
        MovementTypeMaster movementTypeMaster = movementTypeMasterService.getById(id);
        DateAndTimeRequestDto dateAndTimeRequestDto = setDateAndTimeRequestDtoUpdate(movementTypeRequestDTO,movementTypeMaster);
        movementTypeMapper.convertUpdateRequestToEntity(movementTypeRequestDTO,movementTypeMaster,dateAndTimeRequestDto);
        movementTypeMaster = movementTypeMasterService.update(movementTypeMaster);
        MovementTypeResponseDTO movementTypeResponseDTO = movementTypeMapper.convertEntityToResponse(movementTypeMaster);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,movementTypeResponseDTO,null);
        log.info("EXIT");
        return  ResponseEntity.ok(responseDTO);
    }

    /*
     * Method to Fetch MovementTypeMaster By Id
     */
    @GetMapping("{id}")
    public ResponseEntity<ResponseDTO> getMovementTypeById(@PathVariable ("id") Long id){
        log.info("ENTRY - Get MovementType By Id");
        MovementTypeResponseDTO movementTypeResponseDTO = movementTypeMapper.convertEntityToResponse(movementTypeMasterService.getById(id));
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,movementTypeResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);

    }
    /*
     * Method to validate unique Rack Code
     */
    @GetMapping(value = "/codeCheck",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO>checkCode(@RequestParam ("id") String id,@RequestParam("code") String code){
        log.info("ENTRY - Validate unique Movement Code");
        String codeCaps = code.toUpperCase();
        Boolean exists = false;
        try{
            if (id.isEmpty() || id.isBlank()){
                movementTypeMasterService.validateUniqueRackAttributeSave(codeCaps);
            }else {
                movementTypeMasterService.validateUniqueRackAttributeUpdate(codeCaps,Long.parseLong(id));
            }
        }catch (ServiceException serviceException){
            exists = true;
        }
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,exists,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }





    /*
     * Method to set Date, Time and Version in Create
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoCreate(@Valid MovementTypeRequestDTO movementTypeRequestDTO){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Create");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(movementTypeRequestDTO.getUser());
        dateAndTimeRequestDto.setCreatedDate(new Date());
        dateAndTimeRequestDto.setLastModifiedBy(movementTypeRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(1L);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

    /*
     * Method to set Date, Time and Version in Update
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoUpdate(MovementTypeRequestDTO movementTypeRequestDTO,  MovementTypeMaster movementTypeMaster){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Update");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(movementTypeMaster.getCreatedBy());
        dateAndTimeRequestDto.setCreatedDate(movementTypeMaster.getCreatedDate());
        dateAndTimeRequestDto.setLastModifiedBy(movementTypeRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(movementTypeMaster.getVersion() + 1);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }
}
