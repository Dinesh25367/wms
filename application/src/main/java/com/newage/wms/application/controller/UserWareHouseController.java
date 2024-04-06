package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.UserWareHouseMapper;
import com.newage.wms.application.dto.mapper.WareHouseMapper;
import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.UserWareHouseRequestDTO;
import com.newage.wms.application.dto.requestdto.WareHouseAutoCompleteDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.application.dto.responsedto.UserWareHouseResponseDTO;
import com.newage.wms.entity.UserWareHouse;
import com.newage.wms.entity.WareHouseMaster;
import com.newage.wms.service.UserWareHouseService;
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
@RequestMapping("/userWarehouse")
@CrossOrigin("*")
public class UserWareHouseController {

    @Autowired
    private UserWareHouseService userWareHouseService;

    @Autowired
    private WareHouseMapper wareHouseMapper;

    @Autowired
    private UserWareHouseMapper userWareHouseMapper;

    /*
     * Method to fetch all Warehouse for given User
     */
    @GetMapping("/fetchWarehouseForUserId")
    public ResponseEntity<ResponseDTO> fetchAllWareHouseAutoCompleteForUserId(@RequestParam(name = "query",required = false) String query,
                                                                              @RequestParam(name = "userId") Long userId,
                                                                              @RequestParam(name = "defaultFlag",required = false) String defaultFlag,
                                                                              @RequestParam(name = "branchId", required = false) Long branchId){
        log.info("ENTRY - Fetch all Warehouse for given User");
        Page<UserWareHouse> userWareHousePage = userWareHouseService.fetchAllWareHouseAutoCompleteForUserId(query,userId,defaultFlag,branchId);
        Iterable<WareHouseMaster> wareHouseIterable = userWareHousePage.getContent().stream()
                .map(UserWareHouse::getWareHouseMaster)
                .collect(java.util.stream.Collectors.toList());
        Iterable<WareHouseAutoCompleteDTO> wareHouseAutoCompleteDtoIterable = wareHouseMapper.convertWareHouseIterableToWareHouseAutoCompleteResponseDtoIterable(wareHouseIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,wareHouseAutoCompleteDtoIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/grnCancelRights")
    public ResponseEntity<ResponseDTO> fetchUserWareHouseBasedOnUserId(@RequestParam(name = "userId") Long userId){
        log.info("ENTRY - Fetch UserWareHouse for given UserId");
        Boolean exists = false;
        if (userId != null) {
            exists = userWareHouseService.userIdCheckForGrn(userId);
        }
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE, exists, null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/asnCancelRights")
    public ResponseEntity<ResponseDTO> fetchUserWareHouseForAsn(@RequestParam(name = "UserId") Long userId){
        log.info("ENTRY - Fetch UserWareHouse For Asn");
        Boolean exists = false;
        if (userId != null) {
            exists = userWareHouseService.userIdCheckForAsn(userId);
        }
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,exists,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> create(@Valid @RequestBody UserWareHouseRequestDTO userWareHouseRequestDTO){
        log.info("ENTRY - Create New UserWareHouse Master");
        DateAndTimeRequestDto dateAndTimeRequestDto = this.setDateAndTimeRequestDtoCreate(userWareHouseRequestDTO);
        UserWareHouse userWareHouse = userWareHouseMapper.convertRequestToEntity(userWareHouseRequestDTO,dateAndTimeRequestDto);
        userWareHouse=userWareHouseService.saveUserWareHouse(userWareHouse);
        UserWareHouseResponseDTO userWareHouseResponseDTO=userWareHouseMapper.convertEntityToResponse(userWareHouse);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,userWareHouseResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> fetchAllUserWareHouse(@QuerydslPredicate(root = UserWareHouse.class) Predicate predicate,
                                                             @PageableDefault(sort = {"id"},direction = Sort.Direction.ASC,size = 20)@Parameter(hidden = true) Pageable pageable){
        log.info("ENTRY - Fetch All UserWareHouse with sort, filter and pagination");
        Page<UserWareHouse>  userWareHousePage = userWareHouseService.getAll(predicate,pageable);
        Page<UserWareHouseResponseDTO> userWareHouseResponseDTOPage = userWareHouseMapper.convertUserWareHousePageToResponsePage(userWareHousePage);
        ResponseDTO  responseDTO = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,userWareHouseResponseDTOPage,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }
    @PutMapping(value = "{id}")
    public ResponseEntity<ResponseDTO> updateUserWareHouseById(@Valid@RequestBody UserWareHouseRequestDTO userWareHouseRequestDTO,
                                                               @PathVariable("id") Long id){
        log.info("ENTRY - Update  UserWareHouse By Id");
        UserWareHouse userWareHouse = userWareHouseService.getById(id);
        DateAndTimeRequestDto dateAndTimeRequestDto = setDateAndTimeRequestDtoUpdate(userWareHouseRequestDTO,userWareHouse);
        userWareHouseMapper.convertUpdateRequestToEntity(userWareHouseRequestDTO,userWareHouse,dateAndTimeRequestDto);
        userWareHouse =userWareHouseService.updateUserWareHouse(userWareHouse);
        UserWareHouseResponseDTO userWareHouseResponseDTO = userWareHouseMapper.convertEntityToResponse(userWareHouse);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,userWareHouseResponseDTO,null);

        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }

    /*
     * Method to fetch By Id
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseDTO> fetchUserWareHouseById(@PathVariable ("id")Long id){
        log.info("ENTRY - Fetch UserWareHouse By Id");
        UserWareHouseResponseDTO userWareHouseResponseDTO = userWareHouseMapper.convertEntityToResponse(userWareHouseService.getById(id));
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,userWareHouseResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }

    /*
     * Method to set Date, Time and Version in Create
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoCreate(@Valid UserWareHouseRequestDTO userWareHouseRequestDTO){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Create");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(userWareHouseRequestDTO.getUser());
        dateAndTimeRequestDto.setCreatedDate(new Date());
        dateAndTimeRequestDto.setLastModifiedBy(userWareHouseRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(1L);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

    /*
     * Method to set Date, Time and Version in Update
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoUpdate(UserWareHouseRequestDTO userWareHouseRequestDTO, UserWareHouse userWareHouse){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Update");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(userWareHouse.getCreatedBy());
        dateAndTimeRequestDto.setCreatedDate(userWareHouse.getCreatedDate());
        dateAndTimeRequestDto.setLastModifiedBy(userWareHouseRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(userWareHouse.getVersion() + 1);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

}

