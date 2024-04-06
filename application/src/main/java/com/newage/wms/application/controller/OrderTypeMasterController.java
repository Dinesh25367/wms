package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.OrderTypeMapper;
import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.OrderTypeRequestDTO;
import com.newage.wms.application.dto.responsedto.OrderTypeResponseDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.application.dto.responsedto.TrnSoResponseDTO;
import com.newage.wms.entity.OrderTypeMaster;
import com.newage.wms.entity.QOrderTypeMaster;
import com.newage.wms.service.OrderTypeMasterService;
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
@RequestMapping("/orderType")
@CrossOrigin("*")
public class OrderTypeMasterController {
    @Autowired
    private OrderTypeMasterService orderTypeMasterService;

    @Autowired
    private OrderTypeMapper orderTypeMapper;

    /*
     * Method to fetch all OrderTypeMaster for autocomplete
     */
    @GetMapping("/autoComplete")
    public ResponseEntity<ResponseDTO> fetchAllAutoComplete(@RequestParam(name = "query",required = false) String query,
                                                            @RequestParam(name = "warehouseInOut",required = false) String warehouseInOut){
        log.info("ENTRY - Fetch all OrderTypeMaster for autocomplete");
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QOrderTypeMaster.orderTypeMaster.id.isNotNull());
        if (query != null && !query.isEmpty() && !query.isBlank()){
            predicates.add(QOrderTypeMaster.orderTypeMaster.code.containsIgnoreCase(query));
        }
        if (warehouseInOut != null && !warehouseInOut.isEmpty() && !warehouseInOut.isBlank()){
            predicates.add(QOrderTypeMaster.orderTypeMaster.warehouseInOut.eq(warehouseInOut));
        }
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("code").ascending());
        Iterable<OrderTypeMaster> orderTypeMasterIterable = orderTypeMasterService.getAllAutoComplete(predicateAll,pageable);
        Iterable<TrnSoResponseDTO.TrnHeaderSoDTO.OrderTypeDTO> orderTypeDTOIterable = orderTypeMapper.convertEntityIterableToAutoCompleteResponseIterable(orderTypeMasterIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,orderTypeDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> save(@Valid @RequestBody OrderTypeRequestDTO orderTypeRequestDTO){
        log.info("ENTRY - Create a new Order Type Master");
        DateAndTimeRequestDto dateAndTimeRequestDto = this.setDateAndTimeRequestDtoCreate(orderTypeRequestDTO);
        OrderTypeMaster orderTypeMaster = orderTypeMapper.convertRequestToEntity(orderTypeRequestDTO,dateAndTimeRequestDto);
        orderTypeMaster = orderTypeMasterService.save(orderTypeMaster);
       OrderTypeResponseDTO orderTypeResponseDTO = orderTypeMapper.convertEntityToResponse(orderTypeMaster);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,orderTypeResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }


    /*
     * Method to fetch all OrderTypeMaster
     */
    @GetMapping
    public ResponseEntity<ResponseDTO>fetchAll(@QuerydslPredicate(root = OrderTypeMaster.class)Predicate predicate,
                                               @PageableDefault(sort ={"id"},direction = Sort.Direction.ASC,size = 20)@Parameter(hidden = true)Pageable pageable){
        log.info("ENTRY - Fetch All MovementTypeMaster with sort,filter and pagination");
        Page<OrderTypeMaster> orderTypeMaster = orderTypeMasterService.findAll(predicate, pageable);
        Page<OrderTypeResponseDTO> orderTypeResponseDTOPage = orderTypeMapper.convertMovementTypePageToResponsePage(orderTypeMaster);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,orderTypeResponseDTOPage,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }

    /*
     * Method to update OrderTypeMaster
     */
    @PutMapping(value = "{id}")
    public ResponseEntity<ResponseDTO> update(@Valid@RequestBody OrderTypeRequestDTO orderTypeRequestDTO,
                                              @PathVariable ("id") Long id){
        log.info("ENTRY - Update MovementType by Id");
        OrderTypeMaster orderTypeMaster = orderTypeMasterService.getById(id);
        DateAndTimeRequestDto dateAndTimeRequestDto = setDateAndTimeRequestDtoUpdate(orderTypeRequestDTO,orderTypeMaster);
        orderTypeMapper.convertUpdateRequestToEntity(orderTypeRequestDTO,orderTypeMaster,dateAndTimeRequestDto);
        orderTypeMaster = orderTypeMasterService.update(orderTypeMaster);
        OrderTypeResponseDTO orderTypeResponseDTO = orderTypeMapper.convertEntityToResponse(orderTypeMaster);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,orderTypeResponseDTO,null);
        log.info("EXIT");
        return  ResponseEntity.ok(responseDTO);
    }

    /*
     * Method to Fetch MovementTypeMaster By Id
     */
    @GetMapping("{id}")
    public ResponseEntity<ResponseDTO> getMovementTypeById(@PathVariable ("id") Long id){
        log.info("ENTRY - Get MovementType By Id");
        OrderTypeResponseDTO orderTypeResponseDTO = orderTypeMapper.convertEntityToResponse(orderTypeMasterService.getById(id));
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,orderTypeResponseDTO,null);
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
                orderTypeMasterService.validateUniqueRackAttributeSave(codeCaps);
            }else {
                orderTypeMasterService.validateUniqueRackAttributeUpdate(codeCaps,Long.parseLong(id));
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
    public DateAndTimeRequestDto setDateAndTimeRequestDtoCreate(@Valid OrderTypeRequestDTO orderTypeRequestDTO){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Create");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(orderTypeRequestDTO.getUser());
        dateAndTimeRequestDto.setCreatedDate(new Date());
        dateAndTimeRequestDto.setLastModifiedBy(orderTypeRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(1L);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

    /*
     * Method to set Date, Time and Version in Update
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoUpdate(OrderTypeRequestDTO orderTypeRequestDTO,  OrderTypeMaster orderTypeMaster){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Update");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(orderTypeMaster.getCreatedBy());
        dateAndTimeRequestDto.setCreatedDate(orderTypeMaster.getCreatedDate());
        dateAndTimeRequestDto.setLastModifiedBy(orderTypeRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(orderTypeMaster.getVersion() + 1);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }
}
