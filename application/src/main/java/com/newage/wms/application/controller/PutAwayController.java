package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.PutAwayTaskDetailMapper;
import com.newage.wms.application.dto.mapper.PutAwayTaskHeaderMapper;
import com.newage.wms.application.dto.mapper.TransactionHistoryMapper;
import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.PutAwayPopUpRequestDTO;
import com.newage.wms.application.dto.requestdto.PutAwayRequestDTO;
import com.newage.wms.application.dto.responsedto.PutAwayResponseDTO;
import com.newage.wms.application.dto.responsedto.PutAwaySummaryResponseDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.entity.*;
import com.newage.wms.service.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.newage.wms.entity.QGrnHeader;

import java.util.*;

@Log4j2
@RestController
@RequestMapping("/putaway")
@CrossOrigin("*")
public class PutAwayController {

    @Autowired
    private GrnDetailService grnDetailService;

    @Autowired
    private GrnHeaderService grnHeaderService;

    @Autowired
    private PutAwayTaskHeaderMapper putAwayTaskHeaderMapper;

    @Autowired
    private PutAwayTaskDetailMapper putAwayTaskDetailMapper;

    @Autowired
    private PutAwayHeaderService putAwayHeaderService;

    @Autowired
    private TransactionHistoryMapper transactionHistoryMapper;

    @Autowired
    private TransactionHistoryService transactionHistoryService;

    @Autowired
    private AuthUserProfileService authUserProfileService;


    @GetMapping("/fetchAll")
    public ResponseEntity<ResponseDTO> fetchAll(@RequestParam(value = "grnId",required = false) Long grnId,
                                                @RequestParam(name = "customerName", required = false) String customerName,
                                                @RequestParam(name = "orderNumber", required = false) String customerOrderNo,
                                                @RequestParam(name = "team", required = false) String team,
                                                @RequestParam(name = "noOfSku",required = false) String noOfSku,
                                                @RequestParam(name = "volume",required = false) String volume,
                                                @RequestParam(name = "noOfLpn",required = false) String noOfLpn,
                                                @RequestParam(name = "noOfCartons",required = false) String noOfCartons,
                                                @RequestParam(name = "putAwayStatus", required = false) String putAwayStatus,
                                                @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 5) Pageable pageable){
        log.info("ENTRY - Get PutAway List");
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QGrnHeader.grnHeader.id.isNotNull());
        predicates.add(QGrnHeader.grnHeader.status.notEqualsIgnoreCase("Cancelled"));
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        List<GrnHeader> grnHeaderList= grnHeaderService.findAll(predicateAll, Pageable.unpaged()).getContent();
        List<PutAwaySummaryResponseDTO> putAwaySummaryResponseDTOList = putAwayTaskHeaderMapper.convertGrnListToPutAwaySummaryResponseList(grnHeaderList);
        List<PutAwaySummaryResponseDTO> sortedResponseList = putAwayTaskHeaderMapper.sortByAttribute(putAwaySummaryResponseDTOList, pageable);
        List<PutAwaySummaryResponseDTO> putAwayFilteredResponseListSetOne = putAwayTaskHeaderMapper
                .getFilteredResponseListSetOne(sortedResponseList,customerName,customerOrderNo,team,putAwayStatus,pageable);
        List<PutAwaySummaryResponseDTO> putAwayFilteredResponseListSetTwo = putAwayTaskHeaderMapper
                .getFilteredResponseListSetTwo(putAwayFilteredResponseListSetOne,noOfSku,noOfLpn,volume,noOfCartons,pageable);
        Page<PutAwaySummaryResponseDTO> putAwayResponseDTOPage = new PageImpl<>(putAwayFilteredResponseListSetTwo, pageable, putAwaySummaryResponseDTOList.size());
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,putAwayResponseDTOPage,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation(value = "Create putAway", notes = " Api is used to create putAway ")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully created")})
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseDTO> bulkSave(@RequestBody PutAwayRequestDTO putAwayRequestDTO,
                                         @RequestParam (value = "grnId") String grnId,
                                         @RequestParam(value = "userId") Long userId) {
        log.info("ENTRY - create PutAway");
        DateAndTimeRequestDto dateAndTimeRequestDto = this.setDateAndTimeRequestDtoCreate(putAwayRequestDTO);
        List<TransactionHistory> transactionHistoryList = transactionHistoryMapper.convertPutAwayBulkRequestToEntity(putAwayRequestDTO,dateAndTimeRequestDto);
        transactionHistoryService.save(transactionHistoryList);
        Optional<TransactionHistory> optionalTransactionHistory = transactionHistoryService.getPutAwayTaskForGRNDetailIfExists(Long.valueOf(grnId));
        PutAwayResponseDTO putAwayResponseDTO =putAwayTaskHeaderMapper.convertEntityToResponse(Long.parseLong(grnId),optionalTransactionHistory,userId);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,putAwayResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }

    @ApiOperation(value = "Find PutAway by Id", notes = " Api is used to fetch PutAway by Id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping(value = "/fetchById",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseDTO> getById(@RequestParam (value = "grnId") String grnId,
                                        @RequestParam(value = "userId",required = false) Long userId) {
        log.info("ENTRY - Find PutAway By Id");
        Optional<TransactionHistory> optionalTransactionHistory = transactionHistoryService.getPutAwayTaskForGRNDetailIfExists(Long.valueOf(grnId));
        PutAwayResponseDTO putAwayResponseDTO = putAwayTaskHeaderMapper.convertEntityToResponse(Long.parseLong(grnId),optionalTransactionHistory,userId);
        List<PutAwayResponseDTO.PutAwayTaskDetailDTO.PutAwayPopupDetails> completedPutAwayPopupDetailsList = putAwayTaskHeaderMapper.collectCompletedPutAwayDetails(putAwayResponseDTO);
        putAwayResponseDTO.setCompletedPutAwayPopupDetailsList(completedPutAwayPopupDetailsList);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,putAwayResponseDTO, null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }

    @ApiOperation(value = "Create putAway Task Details", notes = "API is used to create putAway Task details")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully created")})
    @PostMapping(value = "/popUpSave", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> saveTaskDetails(@RequestBody PutAwayPopUpRequestDTO putAwayPopUpRequestDTO,
                                                       @RequestParam(value = "grnDetailId") Long grnDetailId,
                                                       @RequestParam (value = "grnId") String grnId,
                                                       @RequestParam(value = "userId") Long userId) {
        log.info("ENTRY - create PutAway");
        DateAndTimeRequestDto dateAndTimeRequestDto = this.setDateAndTimeRequestDtoCreate1(putAwayPopUpRequestDTO);
        // Fetch the transaction history based on the provided grnDetailId
        List<TransactionHistory> filteredList = transactionHistoryService.findByGrnDetailId(grnDetailId);
        List<TransactionHistory> transactionHistoryList=transactionHistoryMapper.convertPopUpRequestToEntityList(filteredList, putAwayPopUpRequestDTO, dateAndTimeRequestDto);
        transactionHistoryService.save(transactionHistoryList);
        Optional<TransactionHistory> optionalTransactionHistory = transactionHistoryService.getPutAwayTaskForGRNDetailIfExists(Long.valueOf(grnId));
        PutAwayResponseDTO putAwayResponseDTO =putAwayTaskHeaderMapper.convertEntityToResponse(Long.parseLong(grnId),optionalTransactionHistory,userId);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,putAwayResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);

    }

    /*
     * Method to set Date, Time and Version in Create
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoCreate(PutAwayRequestDTO putAwayRequestDTO){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Create");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(putAwayRequestDTO.getUser());
        dateAndTimeRequestDto.setCreatedDate(new Date());
        dateAndTimeRequestDto.setLastModifiedBy(putAwayRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(1L);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

    public DateAndTimeRequestDto setDateAndTimeRequestDtoCreate1(PutAwayPopUpRequestDTO putAwayPopUpRequestDTO){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Create");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(putAwayPopUpRequestDTO.getUser());
        dateAndTimeRequestDto.setCreatedDate(new Date());
        dateAndTimeRequestDto.setLastModifiedBy(putAwayPopUpRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(1L);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> deletePopUp(@RequestParam(value = "grnDetailId") Long grnDetailId,
                                                   @RequestParam(value = "taskSlNo") Integer taskSlNo,
                                                   @RequestParam(value = "userId") Long userId) {
        log.info("ENTRY - Fetch matched Data");
        List<TransactionHistory> transactionHistoryList = transactionHistoryService.findFilteredData(grnDetailId, taskSlNo);
        AuthUserProfile authUserProfile = authUserProfileService.getById(userId);
        List<TransactionHistory> saveList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(transactionHistoryList)) {
            for (TransactionHistory transaction : transactionHistoryList) {
                // Modify the properties of each TransactionHistory object
                transaction.setLastModifiedBy(authUserProfile.getUserName());
                transaction.setLastModifiedDate(new Date());
                transaction.setTransactionStatus("CANCELLED");
                transaction.setTaskStatus("CANCELLED");
                // Add the modified transaction to the save list
                saveList.add(transaction);
            }
            // Save the list of modified transactions
            transactionHistoryService.save(saveList);
        }
        // Construct and return the response DTO
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE, true, null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

}
