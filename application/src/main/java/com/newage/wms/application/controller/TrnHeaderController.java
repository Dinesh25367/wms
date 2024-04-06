package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.*;
import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.GrnHeaderRequestDTO;
import com.newage.wms.application.dto.requestdto.TrnRequestDTO;
import com.newage.wms.application.dto.responsedto.*;
import com.newage.wms.entity.*;
import com.newage.wms.entity.QTrnHeaderAsn;
import com.newage.wms.repository.TrnHeaderAsnRepository;
import com.newage.wms.service.*;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping("/trnHeader")
@CrossOrigin("*")
public class TrnHeaderController {

    @Autowired
    private TrnHeaderAsnService trnHeaderAsnService;

    @Autowired
    private TrnHeaderFreightShippingService trnHeaderFreightShippingService;

    @Autowired
    private TrnHeaderCustomsDocumentService trnHeaderCustomsDocumentService;

    @Autowired
    private UserWareHouseService userWareHouseService;

    @Autowired
    private UserCustomerService userCustomerService;

    @Autowired
    private ConfigurationMasterService configurationMasterService;

    @Autowired
    private CustomerConfigurationMasterService customerConfigurationMasterService;

    @Autowired
    private TrnHeaderASNMapper trnHeaderASNMapper;

    @Autowired
    private TrnHeaderFreightMapper trnHeaderFreightMapper;

    @Autowired
    private TrnHeaderPartyMapper trnHeaderPartyMapper;

    @Autowired
    private TrnHeaderTransportationMapper trnHeaderTransportationMapper;

    @Autowired
    private TrnHeaderFreightShippingMapper trnHeaderFreightShippingMapper;

    @Autowired
    private TrnHeaderCustomsDocumentMapper trnHeaderCustomsDocumentMapper;

    @Autowired
    private TrnHeaderCustomsAddlDetailsMapper trnHeaderCustomsAddlDetailsMapper;

    @Autowired
    private TrnHeaderAddlDetailsMapper trnHeaderAddlDetailsMapper;

    @Autowired
    private TrnDetailMapper trnDetailMapper;

    @Autowired
    private ConfigurationMapper configurationMapper;

    @Autowired
    private CustomerConfigurationMapper customerConfigurationMapper;

    @Autowired
    private GrnHeaderService grnHeaderService;

    @Autowired
    private GrnDetailService grnDetailService;

    @Autowired
    private TrnHeaderAsnRepository trnHeaderAsnRepository;

    private final String BOOKED = "BOOKED";

    @GetMapping
    public ResponseEntity<ResponseDTO> fetchAll(@QuerydslPredicate(root = TrnHeaderAsn.class) Predicate predicate,
                                                @PageableDefault(sort = {"id"},direction = Sort.Direction.ASC,size = 20) Pageable pageable,
                                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
                                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
                                                @RequestParam(required = false) String dateFilter,
                                                @RequestParam Long userId,
                                                @RequestParam(name = "query",required = false) String query){
        log.info("ENTRY - Fetch all TRN Header with pagination, sorting and filter");
        Collection<Predicate> predicates = new ArrayList<>();
        Page<TrnHeaderAsn> trnHeaderPage = trnHeaderAsnService.findAll(predicate, pageable,fromDate,toDate,dateFilter);
        List<TrnResponseDTO> trnResponseDTOList = trnHeaderPage.getContent()
                .stream()
                .map(trnHeaderAsn -> convertHeaderEntityToResponse(trnHeaderAsn,userId))
                .collect(Collectors.toList());
        predicates.add(predicate);
        if (query != null && !query.isEmpty() && !query.isBlank()){
            predicates.add(QTrnHeaderAsn.trnHeaderAsn.customerMaster.code.containsIgnoreCase(query)
                    .or(QTrnHeaderAsn.trnHeaderAsn.wareHouseMaster.name.containsIgnoreCase(query))
                    .or(QTrnHeaderAsn.trnHeaderAsn.transactionUid.containsIgnoreCase(query))
                    .or(QTrnHeaderAsn.trnHeaderAsn.trnHeaderParty.shipperName.containsIgnoreCase(query))
                    .or(QTrnHeaderAsn.trnHeaderAsn.transactionStatus.containsIgnoreCase(query)));
        }
        Page<TrnResponseDTO> trnResponseDTOPage = new PageImpl<>(trnResponseDTOList,trnHeaderPage.getPageable(),trnHeaderPage.getTotalElements());
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,trnResponseDTOPage,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> create(@Valid @RequestBody TrnRequestDTO trnRequestDTO ){
        log.info("ENTRY - Create TRN Header");
        validateTrnHeader(trnRequestDTO);
        trnHeaderAsnService.validateUniqueCustomerInvoiceNoSave(trnRequestDTO.getTrnHeaderAsn().getCustomerInvoiceNo(),Long.parseLong(trnRequestDTO.getTrnHeaderAsn().getWareHouse().getId()),Long.parseLong(trnRequestDTO.getTrnHeaderAsn().getCustomer().getId()));
        trnHeaderAsnService.validateUniqueCustomerOrderNoSave(trnRequestDTO.getTrnHeaderAsn().getCustomerOrderNo(),Long.parseLong(trnRequestDTO.getTrnHeaderAsn().getWareHouse().getId()),Long.parseLong(trnRequestDTO.getTrnHeaderAsn().getCustomer().getId()));
        DateAndTimeRequestDto dateAndTimeRequestDto = this.setDateAndTimeRequestDtoCreate(trnRequestDTO);
        TrnHeaderAsn trnHeaderAsn = setTrnHeaderAsnSave(trnRequestDTO,dateAndTimeRequestDto);
        if (!CollectionUtils.isEmpty(trnRequestDTO.getAsnDetailsList())){
            List<TrnDetail> trnDetailList = setTrnDetailListSave(trnRequestDTO.getAsnDetailsList(),dateAndTimeRequestDto,trnHeaderAsn);
            trnHeaderAsn.setTrnDetailList(trnDetailList);
        }
        trnHeaderAsn = trnHeaderAsnService.save(trnHeaderAsn);
        TrnResponseDTO trnResponseDTO = convertHeaderEntityToResponse(trnHeaderAsn,Long.parseLong(trnRequestDTO.getUserId()));
        if (!CollectionUtils.isEmpty(trnHeaderAsn.getTrnDetailList())) {
            List<TrnResponseDTO.TrnDetailDTO> trnDetailDTOList = convertDetailEntityToResponse(trnHeaderAsn.getTrnDetailList(),"No",trnHeaderAsn.getTransactionStatus());
            trnResponseDTO.setAsnDetailsList(trnDetailDTOList);
        }
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,trnResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> update(@Valid @RequestBody TrnRequestDTO trnRequestDTO,
                                              @PathVariable ("id") Long id){
        log.info("ENTRY - Update TRN Header");
        validateTrnHeader(trnRequestDTO);
        trnHeaderAsnService.validateUniqueCustomerInvoiceNoUpdate(trnRequestDTO.getTrnHeaderAsn().getCustomerInvoiceNo(),id,Long.parseLong(trnRequestDTO.getTrnHeaderAsn().getWareHouse().getId()),Long.parseLong(trnRequestDTO.getTrnHeaderAsn().getCustomer().getId()));
        trnHeaderAsnService.validateUniqueCustomerOrderNoUpdate(trnRequestDTO.getTrnHeaderAsn().getCustomerOrderNo(),id,Long.parseLong(trnRequestDTO.getTrnHeaderAsn().getWareHouse().getId()),Long.parseLong(trnRequestDTO.getTrnHeaderAsn().getCustomer().getId()));
        TrnHeaderAsn trnHeaderAsnToBeUpdated = trnHeaderAsnService.findById(id);
        DateAndTimeRequestDto dateAndTimeRequestDto = this.setDateAndTimeRequestDtoUpdate(trnRequestDTO, trnHeaderAsnToBeUpdated);
        setTrnHeaderAsnUpdate(trnRequestDTO, trnHeaderAsnToBeUpdated, dateAndTimeRequestDto);
        trnDetailMapper.convertUpdateRequestListToEntityListAndDeleteNonExistingIds(trnRequestDTO,trnHeaderAsnToBeUpdated,dateAndTimeRequestDto);
        TrnHeaderAsn trnHeaderAsn = trnHeaderAsnService.update(trnHeaderAsnToBeUpdated);
        TrnResponseDTO trnResponseDTO = convertHeaderEntityToResponse(trnHeaderAsn,Long.parseLong(trnRequestDTO.getUserId()));
        if (!CollectionUtils.isEmpty(trnHeaderAsn.getTrnDetailList())) {
            List<TrnResponseDTO.TrnDetailDTO> trnDetailDTOList = convertDetailEntityToResponse(trnHeaderAsn.getTrnDetailList(),"No",trnHeaderAsn.getTransactionStatus());
            trnResponseDTO.setAsnDetailsList(trnDetailDTOList);
        }
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,trnResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/cancel/{id}")
    public ResponseEntity<ResponseDTO> statusUpdate(@PathVariable ("id") Long id){
        log.info("ENTRY - Update TRN Status");
        TrnHeaderAsn trnHeaderAsnStatusToBeUpdated = trnHeaderAsnService.findById(id);
        trnHeaderAsnStatusToBeUpdated.setTransactionStatus("Cancelled");
        trnHeaderAsnService.update(trnHeaderAsnStatusToBeUpdated);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,true,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> fetchById(@PathVariable Long id,
                                                 @RequestParam Long userId,
                                                 @RequestParam(required = false) String forGrn){
        log.info("ENTRY - Fetch TRN Header by id");
        TrnHeaderAsn trnHeaderAsn = trnHeaderAsnService.findById(id);
        TrnResponseDTO trnResponseDTO = convertHeaderEntityToResponse(trnHeaderAsn,userId);
        if (!CollectionUtils.isEmpty(trnHeaderAsn.getTrnDetailList())) {
            forGrn = Optional.ofNullable(forGrn).orElse("No");
            List<TrnResponseDTO.TrnDetailDTO> trnDetailDTOList = convertDetailEntityToResponse(trnHeaderAsn.getTrnDetailList(),forGrn,trnHeaderAsn.getTransactionStatus());
            trnResponseDTO.setAsnDetailsList(trnDetailDTOList);
        }
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,trnResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/autoComplete")
    public ResponseEntity<ResponseDTO> fetchAllAutoComplete(@RequestParam(name = "query",required = false) String query,
                                                            @RequestParam(name = "branchId",required = false) Long branchId,
                                                            @RequestParam(name = "transactionType",required = false) String transactionType){
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QTrnHeaderAsn.trnHeaderAsn.id.isNotNull());
        predicates.add(QTrnHeaderAsn.trnHeaderAsn.transactionStatus.containsIgnoreCase(BOOKED)
                .or(QTrnHeaderAsn.trnHeaderAsn.transactionStatus.containsIgnoreCase("RECEIVED")));
        if (query != null && !query.isEmpty() && !query.isBlank()){
            predicates.add(QTrnHeaderAsn.trnHeaderAsn.customerOrderNo.containsIgnoreCase(query)
                    .or(QTrnHeaderAsn.trnHeaderAsn.customerInvoiceNo.containsIgnoreCase(query))
                    .or(QTrnHeaderAsn.trnHeaderAsn.transactionUid.containsIgnoreCase(query)));
        }
        if (branchId != null){
            predicates.add(QTrnHeaderAsn.trnHeaderAsn.branchMaster.id.eq(branchId));
        }
        if (transactionType != null && !transactionType.isEmpty() && !transactionType.isBlank()){
            predicates.add(QTrnHeaderAsn.trnHeaderAsn.transactionType.eq(transactionType));
        }
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("id").ascending());
        Iterable<TrnHeaderAsn> trnHeaderAsnIterable = trnHeaderAsnService.findAll(predicateAll,pageable);
        Iterator<TrnHeaderAsn> iterator = trnHeaderAsnIterable.iterator();
        while (iterator.hasNext()) {
            TrnHeaderAsn trnHeaderAsn = iterator.next();
            Long id = trnHeaderAsn.getId();
            boolean isUnCompletedGrnPresent = grnHeaderService.isUnCompletedGrnPresent(id);
            if (!isUnCompletedGrnPresent) {
                iterator.remove(); // Remove the element from secondList
            }
        }
        Iterable<GrnHeaderRequestDTO.TrnHeaderAsnDTO.TransactionDTO> transactionDTOIterable = trnHeaderASNMapper.convertEntityIterableToDtoIterable(trnHeaderAsnIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,
                transactionDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/autoComplete/grn")
    public ResponseEntity<ResponseDTO> fetchAllAutoCompleteForGrn(@RequestParam(name = "query",required = false) String query,
                                                                  @RequestParam(name = "branchId",required = false) Long branchId,
                                                                  @RequestParam(name = "transactionType",required = false) String transactionType){
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QTrnHeaderAsn.trnHeaderAsn.id.isNotNull());
        predicates.add(QTrnHeaderAsn.trnHeaderAsn.transactionStatus.equalsIgnoreCase(BOOKED)
                .or(QTrnHeaderAsn.trnHeaderAsn.transactionStatus.equalsIgnoreCase("RECEIVED")));
        if (query != null && !query.isEmpty() && !query.isBlank()){
            predicates.add(QTrnHeaderAsn.trnHeaderAsn.customerOrderNo.containsIgnoreCase(query)
                    .or(QTrnHeaderAsn.trnHeaderAsn.customerInvoiceNo.containsIgnoreCase(query))
                    .or(QTrnHeaderAsn.trnHeaderAsn.transactionUid.containsIgnoreCase(query)));
        }
        if (branchId != null){
            predicates.add(QTrnHeaderAsn.trnHeaderAsn.branchMaster.id.eq(branchId));
        }
        if (transactionType != null && !transactionType.isEmpty() && !transactionType.isBlank()){
            predicates.add(QTrnHeaderAsn.trnHeaderAsn.transactionType.eq(transactionType));
        }
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        Iterable<TrnHeaderAsn> trnHeaderAsnIterable = trnHeaderAsnService.findAll(predicateAll,Pageable.unpaged());
        Iterator<TrnHeaderAsn> iterator = trnHeaderAsnIterable.iterator();
        List<TrnHeaderAsn> trnHeaderAsnList = new ArrayList<>();
        while (iterator.hasNext()) {
            TrnHeaderAsn element = iterator.next();
            GrnHeader grnHeader = grnHeaderService.getIncompleteGrn(element.getId());
            if (grnHeader == null){
                trnHeaderAsnList.add(element);
            }
        }
        Iterable<GrnHeaderRequestDTO.TrnHeaderAsnDTO.TransactionDTO> transactionDTOIterable = trnHeaderASNMapper.convertEntityIterableToDtoIterable(trnHeaderAsnList);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,
                transactionDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/customsAddlDetails/configuration")
    public ResponseEntity<ResponseDTO> fetchCustomsAddlDetailsConfig(){
        Iterable<ConfigurationMaster> configurationMasterIterable = configurationMasterService.getAllForWmsAsnCustoms();
        Iterable<ConfigurationMasterResponseDTO> configurationMasterResponseDTOIterable = configurationMapper.
                convertEntityIterableToResponseIterable(configurationMasterIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,
                configurationMasterResponseDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/addlDetails/configuration/{id}")
    public ResponseEntity<ResponseDTO> fetchAddlDetailsConfig(@PathVariable Long id){
        Iterable<CustomerConfigurationMaster> customerConfigurationMasterIterable = customerConfigurationMasterService.
                getAllForWmsCustomer(id);
        Iterable<CustomerConfigurationMasterResponseDTO> customerConfigurationMasterResponseDTOIterable = customerConfigurationMapper.
                convertEntityIterableToResponseIterable(customerConfigurationMasterIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,
                customerConfigurationMasterResponseDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable Long id){
        trnHeaderAsnRepository.deleteById(id);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,
                true,null);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("transportation/autocomplete/{id}")
    public ResponseEntity<ResponseDTO> fetchTransportationAutoCompleteById(@PathVariable Long id,
                                                                           @RequestParam(name = "query", required = false) String query) {
        log.info("ENTRY - Fetch Transportation AutoComplete by Id");
        TrnHeaderAsn trnHeaderAsn = trnHeaderAsnService.findById(id);
        List<TrnHeaderTransportation> trnHeaderTransportationList = trnHeaderAsn.getTrnHeaderTransportationList();
        // Filter by query
        if (query != null && !query.isEmpty() && !query.isBlank() && trnHeaderTransportationList != null) {
            trnHeaderTransportationList = trnHeaderTransportationList.stream()
                    .filter(transportation ->
                            (transportation.getContainerNumber() != null && transportation.getContainerNumber().contains(query)) ||
                                    (transportation.getVehicleNumber() != null && transportation.getVehicleNumber().contains(query)))
                    .collect(Collectors.toList());
        }
        List<TrnResponseDTO.TrnHeaderTransportationDTO> trnHeaderTransportationDTOList =
                trnHeaderTransportationMapper.convertEntityListToResponseList(trnHeaderTransportationList);
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE, trnHeaderTransportationDTOList, null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping
    public ResponseEntity<ResponseDTO> deleteAll(){
        trnHeaderAsnRepository.deleteAll();
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,
                true,null);
        return ResponseEntity.ok(responseDto);
    }

    private TrnHeaderAsn setTrnHeaderAsnSave(TrnRequestDTO trnRequestDTO,DateAndTimeRequestDto dateAndTimeRequestDto){
        TrnHeaderAsn trnHeaderAsn = trnHeaderASNMapper.convertRequestToEntity(trnRequestDTO,dateAndTimeRequestDto);
        trnHeaderAsn.setTransactionType("ASN");
        trnHeaderAsn.setTransactionUid(trnHeaderAsnService.generateTransactionUid());
        trnHeaderAsn.setTransactionStatus(BOOKED);
        List<TrnHeaderTransportation> trnHeaderTransportationList = trnHeaderTransportationMapper.convertRequestListToEntityList(trnRequestDTO.getTrnHeaderTransportationList(),dateAndTimeRequestDto);
        trnHeaderAsn.setTrnHeaderTransportationList(trnHeaderTransportationList);
        List<TrnHeaderCustomsAddlDetails> trnHeaderCustomsAddlDetailsList = trnHeaderCustomsAddlDetailsMapper.convertRequestListToEntityList(trnRequestDTO.getTrnHeaderCustomsAddlDetailsList(),dateAndTimeRequestDto);
        trnHeaderAsn.setTrnHeaderCustomsAddlDetailsList(trnHeaderCustomsAddlDetailsList);
        List<TrnHeaderAddlDetails> trnHeaderAddlDetailsList = trnHeaderAddlDetailsMapper.convertRequestListToEntityList(trnRequestDTO.getTrnHeaderAddlDetailsList(), dateAndTimeRequestDto);
        List<TrnHeaderAddlDetails> trnHeaderAddlDetailsNewFlexiFieldsList = trnHeaderAddlDetailsMapper.convertRequestListToEntityFlexiList(trnRequestDTO.getTrnHeaderAddlDetailsNewFlexiFieldsList(), dateAndTimeRequestDto);
        if(trnHeaderAddlDetailsNewFlexiFieldsList!=null && !trnHeaderAddlDetailsNewFlexiFieldsList.isEmpty()) {
            trnHeaderAddlDetailsNewFlexiFieldsList.addAll(trnHeaderAddlDetailsList);
            trnHeaderAsn.setTrnHeaderAddlDetailsList(trnHeaderAddlDetailsNewFlexiFieldsList);
        }
        else{
            trnHeaderAsn.setTrnHeaderAddlDetailsList(trnHeaderAddlDetailsList);
        }
        TrnHeaderFreight trnHeaderFreight = trnHeaderFreightMapper.convertRequestToEntity(trnRequestDTO,dateAndTimeRequestDto);
        TrnHeaderParty trnHeaderParty = trnHeaderPartyMapper.convertRequestToEntity(trnRequestDTO.getParty(),dateAndTimeRequestDto,"asn");
        if (trnRequestDTO.getTrnHeaderFreightShipping() != null){
            TrnHeaderFreightShipping trnHeaderFreightShipping = trnHeaderFreightShippingMapper.convertRequestToEntity(trnRequestDTO.getTrnHeaderFreightShipping(),dateAndTimeRequestDto);
            trnHeaderAsn.setTrnHeaderFreightShipping(trnHeaderFreightShipping);
            trnHeaderFreightShipping.setTrnHeaderAsn(trnHeaderAsn);
        }
        if (trnRequestDTO.getTrnHeaderCustomsDocument() != null){
            TrnHeaderCustomsDocument trnHeaderCustomsDocument = trnHeaderCustomsDocumentMapper.convertRequestToEntity(trnRequestDTO.getTrnHeaderCustomsDocument(),dateAndTimeRequestDto);
            trnHeaderAsn.setTrnHeaderCustomsDocument(trnHeaderCustomsDocument);
            trnHeaderCustomsDocument.setTrnHeaderAsn(trnHeaderAsn);
        }
        trnHeaderAsn.setTrnHeaderFreight(trnHeaderFreight);
        trnHeaderAsn.setTrnHeaderParty(trnHeaderParty);
        trnHeaderFreight.setTrnHeaderAsn(trnHeaderAsn);
        trnHeaderParty.setTrnHeaderAsn(trnHeaderAsn);
        return trnHeaderAsn;
    }

    private void setTrnHeaderAsnUpdate(TrnRequestDTO trnRequestDTO, TrnHeaderAsn trnHeaderAsnToBeUpdated, DateAndTimeRequestDto dateAndTimeRequestDto){
        trnHeaderASNMapper.convertUpdateRequestToEntity(trnRequestDTO, trnHeaderAsnToBeUpdated,dateAndTimeRequestDto);
        trnHeaderFreightMapper.convertUpdateRequestToEntity(trnRequestDTO, trnHeaderAsnToBeUpdated, dateAndTimeRequestDto);
        trnHeaderPartyMapper.convertUpdateRequestToEntity(trnRequestDTO.getParty(), trnHeaderAsnToBeUpdated, dateAndTimeRequestDto,"asn");
        if (trnHeaderAsnToBeUpdated.getTrnHeaderFreightShipping() != null) {
            if (trnRequestDTO.getTrnHeaderFreightShipping() != null) {
                trnHeaderFreightShippingMapper.convertUpdateRequestToEntity(trnRequestDTO.getTrnHeaderFreightShipping(), trnHeaderAsnToBeUpdated, dateAndTimeRequestDto);
            } else {
                Long trnHeaderFreightShippingId = trnHeaderAsnToBeUpdated.getTrnHeaderFreightShipping().getId();
                trnHeaderAsnToBeUpdated.setTrnHeaderFreightShipping(null);
                trnHeaderFreightShippingService.deleteById(trnHeaderFreightShippingId);
            }
        }else if (trnRequestDTO.getTrnHeaderFreightShipping() != null){
            TrnHeaderFreightShipping trnHeaderFreightShipping = trnHeaderFreightShippingMapper.convertRequestToEntity(trnRequestDTO.getTrnHeaderFreightShipping(), dateAndTimeRequestDto);
            trnHeaderAsnToBeUpdated.setTrnHeaderFreightShipping(trnHeaderFreightShipping);
            trnHeaderFreightShipping.setTrnHeaderAsn(trnHeaderAsnToBeUpdated);
        }
        if (trnHeaderAsnToBeUpdated.getTrnHeaderCustomsDocument() != null) {
            if (trnRequestDTO.getTrnHeaderCustomsDocument() != null) {
                trnHeaderCustomsDocumentMapper.convertUpdateRequestToEntity(trnRequestDTO.getTrnHeaderCustomsDocument(), trnHeaderAsnToBeUpdated, dateAndTimeRequestDto);
            } else {
                Long trnHeaderCustomsDocumentId = trnHeaderAsnToBeUpdated.getTrnHeaderCustomsDocument().getId();
                trnHeaderAsnToBeUpdated.setTrnHeaderCustomsDocument(null);
                trnHeaderCustomsDocumentService.deleteById(trnHeaderCustomsDocumentId);
            }
        }else if (trnRequestDTO.getTrnHeaderCustomsDocument() != null){
            TrnHeaderCustomsDocument trnHeaderCustomsDocument = trnHeaderCustomsDocumentMapper.convertRequestToEntity(trnRequestDTO.getTrnHeaderCustomsDocument(), dateAndTimeRequestDto);
            trnHeaderAsnToBeUpdated.setTrnHeaderCustomsDocument(trnHeaderCustomsDocument);
            trnHeaderCustomsDocument.setTrnHeaderAsn(trnHeaderAsnToBeUpdated);
        }
        trnHeaderTransportationMapper.convertUpdateRequestListToEntityListAndDeleteNonExistingIds(trnRequestDTO.getTrnHeaderTransportationList(),trnHeaderAsnToBeUpdated,dateAndTimeRequestDto);
        trnHeaderCustomsAddlDetailsMapper.convertUpdateRequestListToEntityListAndDeleteNonExistingIds(trnRequestDTO.getTrnHeaderCustomsAddlDetailsList(),trnHeaderAsnToBeUpdated,dateAndTimeRequestDto);
        List<TrnHeaderAddlDetails> trnHeaderAddlDetailsListCombined = new ArrayList<>();
        List<TrnHeaderAddlDetails> trnHeaderAddlDetailsList = trnHeaderAddlDetailsMapper.convertRequestListToEntityList(trnRequestDTO.getTrnHeaderAddlDetailsList(), dateAndTimeRequestDto);
        List<TrnHeaderAddlDetails> trnHeaderAddlDetailsNewFlexiFieldsList = trnHeaderAddlDetailsMapper.convertRequestListToEntityFlexiList(trnRequestDTO.getTrnHeaderAddlDetailsNewFlexiFieldsList(), dateAndTimeRequestDto);
        if(trnHeaderAddlDetailsNewFlexiFieldsList!=null&& !trnHeaderAddlDetailsNewFlexiFieldsList.isEmpty()) {
            trnHeaderAddlDetailsListCombined.addAll(trnHeaderAddlDetailsNewFlexiFieldsList);
        }
        if(trnHeaderAddlDetailsList!=null&& !trnHeaderAddlDetailsList.isEmpty()) {
            trnHeaderAddlDetailsListCombined.addAll(trnHeaderAddlDetailsList);
        }
        trnHeaderAsnToBeUpdated.setTrnHeaderAddlDetailsList(trnHeaderAddlDetailsListCombined);
    }

    private List<TrnDetail> setTrnDetailListSave(List<TrnRequestDTO.TrnDetailDTO> asnDetailsList,DateAndTimeRequestDto dateAndTimeRequestDTO,TrnHeaderAsn trnHeaderAsn){
        List<TrnDetail> trnDetailList = new ArrayList<>();
        Integer count = 1;
        for (TrnRequestDTO.TrnDetailDTO trnDetailRequestDTO : asnDetailsList) {
            TrnDetail trnDetail = trnDetailMapper.convertTrnDetailDTOToTrnDetail(trnDetailRequestDTO,dateAndTimeRequestDTO);
            trnDetail.setTransactionUid(trnHeaderAsn.getTransactionUid());
            trnDetail.setTransactionSlNo(count);
            TrnDetailAsn trnDetailAsn = trnDetailMapper.convertTrnDetailDTOToTrnDetailAsn(trnDetailRequestDTO,dateAndTimeRequestDTO);
            trnDetailAsn.setTransactionSlNo(count);
            trnDetail.setTrnDetailAsn(trnDetailAsn);
            trnDetailAsn.setTrnDetail(trnDetail);
            TrnDetailLot trnDetailLot = trnDetailMapper.convertTrnDetailDTOToTrnDetailLot(trnDetailRequestDTO,dateAndTimeRequestDTO);
            if (trnDetailLot != null) {
                trnDetailLot.setTransactionSlNo(count);
                trnDetail.setTrnDetailLot(trnDetailLot);
                trnDetailLot.setTrnDetail(trnDetail);
            }
            TrnDetailQc trnDetailQc = trnDetailMapper.convertTrnDetailDTOToTrnDetailQc(trnDetailRequestDTO,dateAndTimeRequestDTO);
            if(trnDetailQc != null) {
                trnDetailQc.setTransactionSlNo(count);
                trnDetail.setTrnDetailQc(trnDetailQc);
                trnDetailQc.setTrnDetail(trnDetail);
            }
            TrnDetailAsnCustoms trnDetailAsnCustoms = trnDetailMapper.convertTrnDetailDTOToTrnDetailAsnCustoms(trnDetailRequestDTO,dateAndTimeRequestDTO);
            if (trnDetailAsnCustoms != null) {
                trnDetailAsnCustoms.setTransactionSlNo(count);
                trnDetail.setTrnDetailAsnCustoms(trnDetailAsnCustoms);
                trnDetailAsnCustoms.setTrnDetail(trnDetail);
            }
            trnDetail.setTrnHeaderAsn(trnHeaderAsn);
            trnDetailList.add(trnDetail);
            count++;
        }
        return trnDetailList;
    }

    public TrnResponseDTO convertHeaderEntityToResponse(TrnHeaderAsn trnHeaderAsn,Long userId){
        TrnResponseDTO trnResponseDTO = new TrnResponseDTO();
        TrnResponseDTO.TrnHeaderAsnDTO trnHeaderAsnDTO = trnHeaderASNMapper.convertEntityToResponse(trnHeaderAsn);
        trnResponseDTO.setTrnHeaderAsn(trnHeaderAsnDTO);
        trnResponseDTO.setTrnHeaderFreight(trnHeaderFreightMapper.convertEntityToResponse(trnHeaderAsn));
        if (trnHeaderAsn.getTrnHeaderParty() != null){
            List<TrnResponseDTO.PartyCustomerDTO> partyCustomerDTOList = trnHeaderPartyMapper.convertEntityToResponseList(trnHeaderAsn);
            trnResponseDTO.setParty(partyCustomerDTOList);
        }
        trnResponseDTO.setTrnHeaderTransportationList(trnHeaderTransportationMapper.
                convertEntityListToResponseList(trnHeaderAsn.getTrnHeaderTransportationList()));
        trnResponseDTO.setTrnHeaderFreightShipping(trnHeaderFreightShippingMapper.convertEntityToResponse(trnHeaderAsn));
        trnResponseDTO.setTrnHeaderCustomsDocument(trnHeaderCustomsDocumentMapper.convertEntityToResponse(trnHeaderAsn));
        trnResponseDTO.setTrnHeaderCustomsAddlDetailsList(trnHeaderCustomsAddlDetailsMapper.
                convertEntityListToResponseList(trnHeaderAsn.getTrnHeaderCustomsAddlDetailsList()));
        trnResponseDTO.setTrnHeaderAddlDetailsList(trnHeaderAddlDetailsMapper.
                convertEntityListToResponseList(trnHeaderAsn.getTrnHeaderAddlDetailsList()));
        trnResponseDTO.setTrnHeaderAddlDetailsNewFlexiFieldsList(trnHeaderAddlDetailsMapper.
                convertEntityListToResponseFlexiList(trnHeaderAsn.getTrnHeaderAddlDetailsList()));
        trnHeaderASNMapper.setCancelUpdateDeleteInformation(trnHeaderAsn,trnResponseDTO,userId);
        return trnResponseDTO;
    }

    public List<TrnResponseDTO.TrnDetailDTO> convertDetailEntityToResponse(List<TrnDetail> trnDetailList,String forGrn,String status){
        List<TrnDetail> trnDetailListSorted = trnDetailList.stream().sorted(Comparator.comparingLong(TrnDetail::getId)).collect(Collectors.toList());
        List<TrnResponseDTO.TrnDetailDTO> trnDetailDTOList = new ArrayList<>();
        for (TrnDetail trnDetail : trnDetailListSorted) {
            TrnResponseDTO.TrnDetailDTO trnDetailDTO = new TrnResponseDTO.TrnDetailDTO();
            TrnResponseDTO.TrnDetailDTO.DetailsFormDTO detailsFormDTO = trnDetailMapper.convertTrnDetailToDetailsFormDTO(null,trnDetail,forGrn);
            trnDetailDTO.setDetailsForm(detailsFormDTO);
            TrnResponseDTO.TrnDetailDTO.MoreLotFormDTO moreLotFormDTO = trnDetailMapper.convertTrnDetailToMoreLotFormDTO(trnDetail);
            trnDetailDTO.setMoreLotForm(moreLotFormDTO);
            TrnResponseDTO.TrnDetailDTO.MoreQCFormDTO moreQCFormDTO = trnDetailMapper.convertTrnDetailToMoreQCFormDTO(trnDetail);
            trnDetailDTO.setMoreQCForm(moreQCFormDTO);
            TrnResponseDTO.TrnDetailDTO.MoreFtaFormDTO moreFtaFormDTO = trnDetailMapper.convertTrnDetailToMoreFtaFormDTO(trnDetail);
            trnDetailDTO.setMoreFtaForm(moreFtaFormDTO);
            List<TrnResponseDTO.TrnDetailDTO.LotDTO> lotDTOList = trnDetailMapper.convertTrnDetailToLotListDTO(trnDetail);
            trnDetailDTO.setMoreLotList(lotDTOList);
            List<TrnResponseDTO.TrnDetailDTO.LotDTO> lotDTOListForAsn = trnDetailMapper.convertTrnDetailToLotListDTO(trnDetail);
            trnDetailDTO.setMoreLotListForAsn(lotDTOListForAsn);
            boolean isDetailDeletable = grnDetailService.checkIfDetailIdForDeletable(detailsFormDTO.getId());
            if (isDetailDeletable) {
                trnDetailDTO.setIsDetailDeletable("Yes");
            } else {
                trnDetailDTO.setIsDetailDeletable("No");
            }
            if(status.equalsIgnoreCase("Generated")||status.equalsIgnoreCase("Cancelled")){
                trnDetailDTO.setIsDetailDeletable("No");
            } else {
                trnDetailDTO.setIsDetailDeletable("Yes");
            }
            trnDetailDTOList.add(trnDetailDTO);

        }
        return trnDetailDTOList;
    }

    private void validateTrnHeader(TrnRequestDTO trnRequestDTO) {
        log.info("ENTRY - Validate TRN Header");
        Long userId = Long.parseLong(trnRequestDTO.getUserId());
        Long wareHouseId = Long.parseLong(trnRequestDTO.getTrnHeaderAsn().getWareHouse().getId());
        Long customerId = Long.parseLong(trnRequestDTO.getTrnHeaderAsn().getCustomer().getId());
        Boolean wareHouseExist = false;
        if (!userWareHouseService.getAllList().isEmpty()){
            wareHouseExist = userWareHouseService.getAllList().stream()
                    .anyMatch(userWareHouse ->
                            userWareHouse.getUserMaster().getId().equals(userId) &&
                                    userWareHouse.getWareHouseMaster().getId().equals(wareHouseId)
                    );
        }else {
            throw new ServiceException(ServiceErrors.USER_DOES_NOT_HAVE_ACCESS_TO_WAREHOUSE.CODE,
                    ServiceErrors.USER_DOES_NOT_HAVE_ACCESS_TO_WAREHOUSE.KEY);
        }
        if (!wareHouseExist){
            throw new ServiceException(ServiceErrors.USER_DOES_NOT_HAVE_ACCESS_TO_WAREHOUSE.CODE,
                    ServiceErrors.USER_DOES_NOT_HAVE_ACCESS_TO_WAREHOUSE.KEY);
        }
        Boolean customerExist = false;
        if (!userCustomerService.getAllList().isEmpty()){
            customerExist = userCustomerService.getAllList().stream()
                    .anyMatch(userCustomer ->
                            userCustomer.getUserMaster().getId().equals(userId) &&
                                    userCustomer.getCustomerMaster().getId().equals(customerId)
                    );
        }else {
            throw new ServiceException(ServiceErrors.USER_DOES_NOT_HAVE_ACCESS_TO_CUSTOMER.CODE,
                    ServiceErrors.USER_DOES_NOT_HAVE_ACCESS_TO_CUSTOMER.KEY);
        }
        if (!customerExist){
            throw new ServiceException(ServiceErrors.USER_DOES_NOT_HAVE_ACCESS_TO_CUSTOMER.CODE,
                    ServiceErrors.USER_DOES_NOT_HAVE_ACCESS_TO_CUSTOMER.KEY);
        }
        log.info("EXIT");
    }

    /*
     * Method to set Date, Time and Version in Create
     */
    public DateAndTimeRequestDto setDateAndTimeRequestDtoCreate(TrnRequestDTO trnRequestDTO){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Create");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(trnRequestDTO.getUser());
        dateAndTimeRequestDto.setCreatedDate(new Date());
        dateAndTimeRequestDto.setLastModifiedBy(trnRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(1L);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

    public DateAndTimeRequestDto setDateAndTimeRequestDtoUpdate(TrnRequestDTO trnRequestDTO, TrnHeaderAsn trnHeaderAsn) {
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Update");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(trnHeaderAsn.getCreatedBy());
        dateAndTimeRequestDto.setCreatedDate(trnHeaderAsn.getCreatedDate());
        dateAndTimeRequestDto.setLastModifiedBy(trnRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(trnHeaderAsn.getVersion() + 1);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }
    /*
     * Method to validate customerOrderNo Check
     */
    @GetMapping(value = "/customerOrderNoCheck",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> checkCustomerOrderNo(@RequestParam("id") String id,
                                                            @RequestParam("customerOrderNo") String customerOrderNo,
                                                            @RequestParam("warehouseId") Long warehouseId,
                                                            @RequestParam("customerId") Long customerId){
        log.info("ENTRY - Validate unique customerOrderNo");
        String codeCaps = customerOrderNo.toUpperCase();
        Boolean exits = false;
        try {
            if (id.isEmpty() || id.isBlank()) {
                trnHeaderAsnService.validateUniqueCustomerOrderNoSave(codeCaps,warehouseId,customerId);
            } else {
                trnHeaderAsnService.validateUniqueCustomerOrderNoUpdate(codeCaps, Long.parseLong(id),warehouseId,customerId);
            }
        } catch (ServiceException serviceException) {
            exits = true;
        }
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,exits,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }

    /*
     * Method to validate customerInvoiceNo Check
     */
    @GetMapping(value = "/customerInvoiceNoCheck",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> checkCustomerInvoiceNo(@RequestParam("id") String id,
                                                              @RequestParam("customerInvoiceNo") String customerInvoiceNo,
                                                              @RequestParam("warehouseId") Long warehouseId,
                                                              @RequestParam("customerId") Long customerId) {
        log.info("ENTRY - Validate unique customerInvoiceNo");
        String codeCaps = customerInvoiceNo.toUpperCase();
        Boolean exits = false;
        try {
            if (id.isEmpty() || id.isBlank()) {
                trnHeaderAsnService.validateUniqueCustomerInvoiceNoSave(codeCaps,warehouseId,customerId);
            } else {
                trnHeaderAsnService.validateUniqueCustomerInvoiceNoUpdate(codeCaps, Long.parseLong(id),warehouseId,customerId);
            }
        } catch (ServiceException serviceException) {
            exits = true;
        }
        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,exits,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDTO);
    }

}
