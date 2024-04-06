package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.CustomerMapper;
import com.newage.wms.application.dto.mapper.UserCustomerMapper;
import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.SkuRequestDTO;
import com.newage.wms.application.dto.requestdto.UserCustomerRequestDto;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.application.dto.responsedto.UserCustomerResponseDTO;
import com.newage.wms.entity.CustomerMaster;
import com.newage.wms.entity.UserCustomer;
import com.newage.wms.service.UserCustomerService;
import com.querydsl.core.types.Predicate;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@Log4j2
@RestController
@RequestMapping("/userCustomer")
@CrossOrigin("*")
public class UserCustomerController {

    @Autowired
    private UserCustomerService userCustomerService;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private UserCustomerMapper userCustomerMapper;

    @GetMapping("/fetchCustomerForUserId")
    public ResponseEntity<ResponseDTO> fetchAllCustomerAutoCompleteForUserId(@RequestParam(name = "query",required = false) String query,
                                                                             @RequestParam(name = "userId") Long userId,
                                                                             @RequestParam(name = "defaultFlag",required = false) String defaultFlag){
        log.info("ENTRY - Fetch all Customer for given User");
        Page<UserCustomer> userCustomerPage = userCustomerService.fetchAllCustomerAutoCompleteForUserId(query,userId,defaultFlag);
        Iterable<CustomerMaster> customerMasterIterable = userCustomerPage.getContent().stream()
                .map(UserCustomer::getCustomerMaster)
                .collect(java.util.stream.Collectors.toList());
        Iterable<SkuRequestDTO.CustomerDTO> customerDTOIterable = customerMapper.convertCustomerMasterIterableToCustomerAutoCompleteDtoIterable(customerMasterIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,customerDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> create(@Valid @RequestBody UserCustomerRequestDto userCustomerRequestDto){
        log.info("ENTRY - Create New UserWareHouse Master");
        DateAndTimeRequestDto dateAndTimeRequestDto = this.setDateAndTimeRequestDtoCreate( userCustomerRequestDto);
        UserCustomer userCustomer = userCustomerMapper.convertRequestToEntity( userCustomerRequestDto,dateAndTimeRequestDto);
        userCustomer=userCustomerService.saveUserCustomer(userCustomer);
        UserCustomerResponseDTO userCustomerResponseDto=userCustomerMapper.convertEntityToResponse(userCustomer);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,userCustomerResponseDto,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> fetchAll(@QuerydslPredicate(root = UserCustomer.class)Predicate predicate,
                                                @PageableDefault(sort ={"id"},direction = Sort.Direction.ASC,size = 20)@Parameter (hidden = true)Pageable pageable){
        log.info("ENTRY - Fetch All UserCustomer with sort , filter and pagination");
        Page<UserCustomer> userCustomerPage = userCustomerService.findAll(predicate,pageable);
        Page<UserCustomerResponseDTO> customerResponseDtoPage = userCustomerMapper.convertUserCustomerPageToResponsePage(userCustomerPage);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,customerResponseDtoPage,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseDTO>updateUserCustomerById(@Valid@RequestBody UserCustomerRequestDto userCustomerRequestDto,
                                                             @PathVariable ("id") Long id){
        log.info("ENTRY - Update UserCustomer By Id");
        UserCustomer userCustomer = userCustomerService.getById(id);
        DateAndTimeRequestDto dateAndTimeRequestDto = setDateAndTimeRequestDtoUpdate(userCustomerRequestDto,userCustomer);
        userCustomerMapper.convertUpdateRequestToEntity(userCustomerRequestDto,userCustomer,dateAndTimeRequestDto);
        userCustomer = userCustomerService.updateUserCustomer(userCustomer);
        UserCustomerResponseDTO userCustomerResponseDto = userCustomerMapper.convertEntityToResponse(userCustomer);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,userCustomerResponseDto,null);
        log.info("EXIT");

        return ResponseEntity.ok(responseDTO);
    }
    /*
     * Method to Fetch UserCustomer By Id
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseDTO> fetchUserCustomerById(@PathVariable ("id") Long id){
        log.info("ENTRY - Fetch UserCustomer By Id");
        UserCustomerResponseDTO userCustomerResponseDto = userCustomerMapper.convertEntityToResponse(userCustomerService.getById(id));
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,userCustomerResponseDto,null);
        log.info("EXIt");
        return ResponseEntity.ok(responseDTO);
    }


    /*
     * Method to set Date, Time and Version in Create
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoCreate(@Valid UserCustomerRequestDto userCustomerRequestDto){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Create");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(userCustomerRequestDto.getUser());
        dateAndTimeRequestDto.setCreatedDate(new Date());
        dateAndTimeRequestDto.setLastModifiedBy(userCustomerRequestDto.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(1L);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

    /*
     * Method to set Date, Time and Version in Update
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoUpdate(UserCustomerRequestDto userCustomerRequestDto,  UserCustomer userCustomer){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Update");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(userCustomer.getCreatedBy());
        dateAndTimeRequestDto.setCreatedDate(userCustomer.getCreatedDate());
        dateAndTimeRequestDto.setLastModifiedBy(userCustomerRequestDto.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(userCustomer.getVersion() + 1);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

}

