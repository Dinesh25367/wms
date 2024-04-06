package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.*;
import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.GrnHeaderRequestDTO;
import com.newage.wms.application.dto.responsedto.GrnHeaderResponseDTO;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.entity.*;
import com.newage.wms.entity.QTrnHeaderAsn;
import com.newage.wms.repository.GrnDetailRepository;
import com.newage.wms.repository.GrnHeaderRepository;
import com.newage.wms.service.*;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/grnHeader")
@CrossOrigin("*")
public class GrnHeaderController {

    @Autowired
    private GrnHeaderService grnHeaderService;

    @Autowired
    private TrnHeaderAsnService trnHeaderAsnService;

    @Autowired
    private GrnDetailService grnDetailService;

    @Autowired
    private GrnLotDetailService grnLotDetailService;

    @Autowired
    private UserWareHouseService userWareHouseService;

    @Autowired
    private GrnHeaderMapper grnHeaderMapper;

    @Autowired
    private TrnHeaderASNMapper trnHeaderASNMapper;

    @Autowired
    private TrnHeaderPartyMapper trnHeaderPartyMapper;

    @Autowired
    private GrnDetailMapper grnDetailMapper;

    @Autowired
    private GrnDetailLotMapper grnDetailLotMapper;

    @Autowired
    private GrnDetailRepository grnDetailRepository;

    @Autowired
    private GrnHeaderRepository grnHeaderRepository;

    private String booked = "BOOKED";

    private String received = "RECEIVED";

    private String generated = "GENERATED";

    private String completed = "COMPLETED";

    /*
     * Method to Find all GrnHeader with sort, filter and pagination
     */
    @ApiOperation(value = "Find all GrnHeader with pagination", notes = " Api is used to fetch all GrnHeader with sort, filter and pagination ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping
    public ResponseEntity<ResponseDTO> findAll(@RequestParam(name = "customerName",required = false) String customerName,
                                               @RequestParam(name = "branchId",required = false) Long branchId,
                                               @RequestParam(name = "transactionUid",required = false) String transactionUid,
                                               @RequestParam(name = "transactionType",required = false) String transactionType,
                                               @RequestParam(name = "customerOrderNo",required = false) String customerOrderNo,
                                               @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
                                               @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
                                               @RequestParam(required = false) String dateFilter,
                                               @RequestParam(name = "createdDateOrgrnDate",required = false) String createdDateOrgrnDate,
                                               @RequestParam(name = "grnRef",required = false) String grnRef,
                                               @RequestParam(name = "grnStatus",required = false) String grnStatus,
                                               @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 5) Pageable pageable,
                                               @RequestParam(name = "userId") Long userId){
        log.info("ENTRY - Find all GrnHeader with sort, filter and pagination");
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QTrnHeaderAsn.trnHeaderAsn.id.isNotNull());
        if (branchId != null){
            predicates.add(QTrnHeaderAsn.trnHeaderAsn.branchMaster.id.eq(branchId));
        }
        predicates.add(QTrnHeaderAsn.trnHeaderAsn.transactionType.containsIgnoreCase("ASN")
                .or(QTrnHeaderAsn.trnHeaderAsn.transactionType.containsIgnoreCase("DIRECT")));
        predicates.add(QTrnHeaderAsn.trnHeaderAsn.transactionStatus.containsIgnoreCase(booked)
                .or(QTrnHeaderAsn.trnHeaderAsn.transactionStatus.containsIgnoreCase(received)
                        .or(QTrnHeaderAsn.trnHeaderAsn.transactionStatus.containsIgnoreCase(generated)
                                .or(QTrnHeaderAsn.trnHeaderAsn.transactionStatus.containsIgnoreCase("PART RECEIVED")))));
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        List<GrnHeaderResponseDTO> grnHeaderResponseDTOListSorted = grnHeaderMapper.getSortedResponseList(pageable,predicateAll,userId);
        List<GrnHeaderResponseDTO> grnHeaderResponseDTOListGRNSortedAndFiltered = grnHeaderMapper.
                getFilteredResponseListWithGrnFilter(grnHeaderResponseDTOListSorted,grnRef,grnStatus);
        List<GrnHeaderResponseDTO> grnHeaderResponseDTOListGRNTRNSortedAndFiltered = grnHeaderMapper.
                getFilteredResponseListWithTrnFilter(grnHeaderResponseDTOListGRNSortedAndFiltered,customerName,transactionUid,transactionType,customerOrderNo);
        List<GrnHeaderResponseDTO> grnHeaderResponseDTOListGRNTRNSortedAndDateFiltered = grnHeaderMapper.
                getFilteredResponseListWithTrnDateFilter(grnHeaderResponseDTOListGRNTRNSortedAndFiltered,fromDate,toDate,dateFilter,createdDateOrgrnDate);
        List<GrnHeaderResponseDTO> pagedGrnHeaderResponseDTOList = grnHeaderMapper.getPagedResponseList(pageable,grnHeaderResponseDTOListGRNTRNSortedAndDateFiltered);
        Page<GrnHeaderResponseDTO> grnHeaderResponseDTOPage = new PageImpl<>(pagedGrnHeaderResponseDTOList, pageable, grnHeaderResponseDTOListGRNTRNSortedAndDateFiltered.size());
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,grnHeaderResponseDTOPage,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     *Method to Create new GrnHeader
     */
    @ApiOperation(value = "Create GrnHeader", notes = " Api is used to create GrnHeader ")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully created") })
    @PostMapping(value="/save",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> save(@Valid @RequestBody GrnHeaderRequestDTO grnHeaderRequestDTO,
                                            @RequestParam(name = "status") String status,
                                            @RequestParam(name = "userId") Long userId,
                                            @RequestParam(name = "asnClose",required = false) String asnClose){
        log.info("ENTRY - Create new GrnHeader");
        DateAndTimeRequestDto dateAndTimeRequestDto = this.setDateAndTimeRequestDtoCreate(grnHeaderRequestDTO);
        if (grnHeaderRequestDTO.getTrnHeaderAsn().getFrom().equalsIgnoreCase("direct")){
            grnHeaderRequestDTO = saveDirectGrn(grnHeaderRequestDTO,dateAndTimeRequestDto);
        }
        GrnHeader grnHeader = grnHeaderMapper.convertRequestToEntity(grnHeaderRequestDTO,dateAndTimeRequestDto,status);
        List<GrnDetail> grnDetailList = grnDetailMapper.convertRequestListToEntityList(grnHeader,grnHeaderRequestDTO,dateAndTimeRequestDto,false);
        String transactionStatus = grnHeader.getStatus();
        grnHeader.setGrnDetailList(grnDetailList);
        if (status.equalsIgnoreCase(completed)) {
            grnHeader.setStatus(completed);
        }
        grnHeader = grnHeaderService.save(grnHeader);
        TrnHeaderAsn trnHeaderAsn = grnHeader.getTrnHeaderAsnMaster();
        trnHeaderAsn.setTrnDetailList(grnDetailMapper.trnDetailList);
        trnHeaderAsn.setTransactionStatus(transactionStatus);
        if (status.equalsIgnoreCase(completed) && asnClose != null && asnClose.contains("Yes")) {
            trnHeaderAsn.setTransactionStatus(generated);
        }
        trnHeaderAsnService.update(trnHeaderAsn);
        if(grnHeader.getStatus().equalsIgnoreCase(completed)) {
            grnHeaderMapper.saveTransactionHistory(grnHeader, dateAndTimeRequestDto,completed,true);

        }else {
            grnHeaderMapper.saveTransactionHistory(grnHeader, dateAndTimeRequestDto,"pending",true);
        }
        GrnHeaderResponseDTO grnHeaderResponseDTO = grnHeaderMapper.convertAndSetGrnResponse(grnHeader,userId);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.CREATED.value(), Boolean.TRUE,grnHeaderResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }
    /*
     *Method to Update GrnHeader by id
     */
    @ApiOperation(value = "Update GrnHeader", notes = "Api is used to update GrnHeader")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated")})
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> update(@Valid @RequestBody GrnHeaderRequestDTO grnHeaderRequestDTO,
                                              @PathVariable ("id") Long id,
                                              @RequestParam(name = "status") String status,
                                              @RequestParam(name = "userId") Long userId,
                                              @RequestParam(name = "asnClose",required = false) String asnClose){
        log.info("ENTRY - Update GrnHeader by Id");
        GrnHeader grnHeader=grnHeaderService.findById(id);
        if (grnHeader.getStatus().equalsIgnoreCase("cancelled") || grnHeader.getStatus().equalsIgnoreCase(completed)){
            throw new ServiceException(
                    ServiceErrors.CANCELLED_OR_GRN_CANNOT_BE_EDITED.CODE,
                    ServiceErrors.CANCELLED_OR_GRN_CANNOT_BE_EDITED.KEY);
        }
        String oldStatus = grnHeader.getStatus();
        DateAndTimeRequestDto dateAndTimeRequestDto=setDateAndTimeRequestDtoUpdate(grnHeaderRequestDTO,grnHeader);
        grnHeaderMapper.convertUpdateRequestToEntity(grnHeaderRequestDTO,grnHeader,dateAndTimeRequestDto,status);
        grnDetailMapper.convertUpdateRequestListToEntityListAndDeleteNonExistingIds(grnHeader,grnHeaderRequestDTO,dateAndTimeRequestDto,true);
        String transactionStatus = grnHeader.getStatus();
        if (status.equalsIgnoreCase(completed)){
            grnHeader.setStatus(completed);
            grnHeaderMapper.saveTransactionHistory(grnHeader, dateAndTimeRequestDto,completed,false);
        }else {
            grnHeaderMapper.saveTransactionHistory(grnHeader, dateAndTimeRequestDto,"pending",false);
        }
        grnHeader=grnHeaderService.update(grnHeader);
        TrnHeaderAsn trnHeaderAsn = grnHeader.getTrnHeaderAsnMaster();
        trnHeaderAsn.setTransactionStatus(transactionStatus);
        if (status.equalsIgnoreCase(completed) &&
                !(oldStatus != null && oldStatus.equalsIgnoreCase(completed))
                && asnClose != null && asnClose.contains("Yes")) {
            trnHeaderAsn.setTransactionStatus(generated);
        }
        trnHeaderAsn.setTrnDetailList(grnDetailMapper.trnDetailList);
        trnHeaderAsnService.update(trnHeaderAsn);
        GrnHeaderResponseDTO grnHeaderResponseDTO = grnHeaderMapper.convertAndSetGrnResponse(grnHeader,userId);
        ResponseDTO responseDto=new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,grnHeaderResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to fetch GrnHeader by id
     */
    @ApiOperation(value = "Find GrnHeader by Id", notes = " Api is used to fetch GrnHeader by Id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched") })
    @GetMapping(value = "/fetchById",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> findById(@RequestParam(value = "transactionId") String transactionId,
                                                @RequestParam(value = "grnId") String grnId,
                                                @RequestParam(name = "userId") Long userId){
        log.info("ENTRY - Find GrnHeader by Id");
        GrnHeaderResponseDTO grnHeaderResponseDTO;
        if (grnId != null && !grnId.isEmpty() && !grnId.isBlank()){
            GrnHeader grnHeader = grnHeaderService.findById(Long.parseLong(grnId));
            grnHeaderResponseDTO = grnHeaderMapper.convertAndSetGrnResponse(grnHeader,userId);
            grnHeaderResponseDTO.setAsnDetailsList(grnDetailMapper.convertEntityListToResponseList(grnHeader));
        }else {
            TrnHeaderAsn trnHeaderAsn = trnHeaderAsnService.findById(Long.parseLong(transactionId));
            grnHeaderResponseDTO = grnHeaderMapper.setGrnHeaderResponseWithOnlyTrn(trnHeaderAsn);
            grnHeaderResponseDTO.setAsnDetailsList(grnDetailMapper.convertEntityListToResponseListWithoutGrn(trnHeaderAsn));
        }
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,grnHeaderResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/cancel/{id}")
    public ResponseEntity<ResponseDTO> setStatusCancel(@PathVariable ("id") Long id){
        log.info("ENTRY - Update GRN Status");
        GrnHeader grnHeader = grnHeaderService.findById(id);
        if (grnHeader.getStatus().equalsIgnoreCase(completed)) {
            throw new ServiceException(
                    ServiceErrors.GRN_CANNOT_BE_CANCELLED.CODE,
                    ServiceErrors.GRN_CANNOT_BE_CANCELLED.KEY);
        }
        if (grnHeader.getStatus().equalsIgnoreCase("cancelled")){
            throw new ServiceException(
                    ServiceErrors.GRN_ALREADY_CANCELLED.CODE,
                    ServiceErrors.GRN_ALREADY_CANCELLED.KEY);
        }
        grnHeader.setStatus("CANCELLED");
        grnHeaderService.update(grnHeader);
        TrnHeaderAsn trnHeaderAsn = grnDetailMapper.deductQtyAndAmount(grnHeader);
        String transactionStatus = trnHeaderAsnService.getTransactionStatus(trnHeaderAsn);
        trnHeaderAsn.setTransactionStatus(transactionStatus.toUpperCase());
        trnHeaderAsnService.update(trnHeaderAsn);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,true,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/close/{id}")
    public ResponseEntity<ResponseDTO> setStatusClose(@PathVariable ("id") Long id){
        log.info("ENTRY - Update GRN Status");
        GrnHeader grnHeader = grnHeaderService.findById(id);
        if (grnHeader.getStatus().equalsIgnoreCase(completed)) {
            grnHeader.setStatus("CLOSED");
        }
        grnHeaderService.update(grnHeader);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,true,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/cancel/rights/{userId}")
    public ResponseEntity<ResponseDTO> getGrnCancelRights(@PathVariable ("userId") Long userId){
        boolean hasCancelUserRights = userWareHouseService.userIdCheckForGrn(userId);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,hasCancelUserRights,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id){
        grnHeaderRepository.deleteAll();
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,true,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    private GrnHeaderRequestDTO saveDirectGrn(GrnHeaderRequestDTO grnHeaderRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto) {
        TrnHeaderAsn trnHeaderAsn = setTrnHeaderAsnSave(grnHeaderRequestDTO,dateAndTimeRequestDto);
        trnHeaderAsn = trnHeaderAsnService.save(trnHeaderAsn);
        GrnHeaderRequestDTO.TrnHeaderAsnDTO.TransactionDTO transactionDTO = new GrnHeaderRequestDTO.TrnHeaderAsnDTO.TransactionDTO();
        transactionDTO.setId(trnHeaderAsn.getId().toString());
        transactionDTO.setTransactionUid(trnHeaderAsn.getTransactionUid());
        grnHeaderRequestDTO.getTrnHeaderAsn().setTransaction(transactionDTO);
        return grnHeaderRequestDTO;
    }

    private TrnHeaderAsn setTrnHeaderAsnSave(GrnHeaderRequestDTO grnHeaderRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto){
        TrnHeaderAsn trnHeaderAsn = trnHeaderASNMapper.convertGrnRequestToTrnEntity(grnHeaderRequestDTO,dateAndTimeRequestDto);
        trnHeaderAsn.setTransactionType("DIRECT");
        trnHeaderAsn.setTransactionUid(grnHeaderService.generateTransactionUidDirect());
        trnHeaderAsn.setTransactionStatus(booked);
        TrnHeaderParty trnHeaderParty = trnHeaderPartyMapper.convertGrnRequestToTrnEntity(grnHeaderRequestDTO,dateAndTimeRequestDto);
        trnHeaderAsn.setTrnHeaderParty(trnHeaderParty);
        trnHeaderParty.setTrnHeaderAsn(trnHeaderAsn);
        return trnHeaderAsn;
    }

    /*
     * Method to set Date, Time and Version in Create
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoCreate(GrnHeaderRequestDTO grnHeaderRequestDTO){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Create");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(grnHeaderRequestDTO.getUser());
        dateAndTimeRequestDto.setCreatedDate(new Date());
        dateAndTimeRequestDto.setLastModifiedBy(grnHeaderRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(1L);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

    /*
     * Method to set Date, Time and Version in Update
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoUpdate(GrnHeaderRequestDTO grnHeaderRequestDTO, GrnHeader grnHeader) {
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Update");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(grnHeader.getCreatedBy());
        dateAndTimeRequestDto.setCreatedDate(grnHeader.getCreatedDate());
        dateAndTimeRequestDto.setLastModifiedBy(grnHeaderRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(grnHeader.getVersion() + 1);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

}