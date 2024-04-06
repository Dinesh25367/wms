package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.*;
import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.TrnSoRequestDTO;
import com.newage.wms.application.dto.responsedto.*;
import com.newage.wms.entity.*;
import com.newage.wms.repository.TrnHeaderAsnRepository;
import com.newage.wms.service.*;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping("/trnHeaderSo")
@CrossOrigin("*")
public class TrnHeaderSoController {

    @Autowired
    private TrnHeaderAsnService trnHeaderAsnService;

    @Autowired
    private TrnDetailSoService trnDetailSoService;

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
    private TrnHeaderSoMapper trnHeaderSoMapper;

    @Autowired
    private TrnHeaderFreightMapper trnHeaderFreightMapper;

    @Autowired
    private TrnHeaderSoFreightMapper trnHeaderSoFreightMapper;

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
    private TrnDetailSoMapper trnDetailSoMapper;

    @Autowired
    private ConfigurationMapper configurationMapper;

    @Autowired
    private CustomerConfigurationMapper customerConfigurationMapper;

    @Autowired
    private TrnHeaderAsnRepository trnHeaderAsnRepository;

    @GetMapping
    public ResponseEntity<ResponseDTO> fetchAll(@QuerydslPredicate(root = TrnHeaderAsn.class) Predicate predicate,
                                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
                                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
                                                @RequestParam(required = false) String dateFilter,
                                                @PageableDefault(sort = {"id"},direction = Sort.Direction.ASC,size = 20)
                                                Pageable pageable){
        log.info("ENTRY - Fetch all TRN Header with pagination, sorting and filter");
        Page<TrnHeaderAsn> trnHeaderPage = trnHeaderAsnService.findAll(predicate, pageable,fromDate,toDate,dateFilter);
        List<TrnSoResponseDTO> trnSoResponseDTOList = trnHeaderPage.getContent()
                .stream()
                .map(this::convertHeaderEntityToResponse)
                .collect(Collectors.toList());
        Page<TrnSoResponseDTO> trnResponseDTOPage = new PageImpl<>(trnSoResponseDTOList,trnHeaderPage.getPageable(),trnHeaderPage.getTotalElements());
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,trnResponseDTOPage,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> create(@Valid @RequestBody TrnSoRequestDTO trnSoRequestDTO ){
        log.info("ENTRY - Create TRN Header");
        validateTrnHeader(trnSoRequestDTO);
        DateAndTimeRequestDto dateAndTimeRequestDto = this.setDateAndTimeRequestDtoCreate(trnSoRequestDTO);
        TrnHeaderAsn trnHeaderAsn = setTrnHeaderSoSave(trnSoRequestDTO,dateAndTimeRequestDto);
        if (!CollectionUtils.isEmpty(trnSoRequestDTO.getSoDetailsList())){
            List<TrnDetail> trnDetailList = setTrnDetailListSave(trnSoRequestDTO.getSoDetailsList(),dateAndTimeRequestDto,trnHeaderAsn);
            trnHeaderAsn.setTrnDetailList(trnDetailList);
        }
        trnHeaderAsn = trnHeaderAsnService.save(trnHeaderAsn);
        TrnSoResponseDTO trnSoResponseDTO = convertHeaderEntityToResponse(trnHeaderAsn);
        if (!CollectionUtils.isEmpty(trnHeaderAsn.getTrnDetailList())) {
            List<TrnResponseDTO.TrnDetailDTO> trnDetailDTOList = convertDetailEntityToResponse(trnHeaderAsn.getTrnDetailList(),"No");
            trnSoResponseDTO.setSoDetailsList(trnDetailDTOList);
        }
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,trnSoResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> update(@Valid @RequestBody TrnSoRequestDTO trnSoRequestDTO,
                                              @PathVariable ("id") Long id){
        log.info("ENTRY - Update TRN Header");
        validateTrnHeader(trnSoRequestDTO);
        TrnHeaderAsn trnHeaderAsnToBeUpdated = trnHeaderAsnService.findById(id);
        Boolean flag = false;
        if (trnHeaderAsnToBeUpdated.getTransactionId() == null){
            flag = true;
        }
        DateAndTimeRequestDto dateAndTimeRequestDto = this.setDateAndTimeRequestDtoUpdate(trnSoRequestDTO, trnHeaderAsnToBeUpdated);
        setTrnHeaderSoUpdate(trnSoRequestDTO,trnHeaderAsnToBeUpdated,dateAndTimeRequestDto);
        if (flag && !CollectionUtils.isEmpty(trnSoRequestDTO.getSoDetailsList())){
            for (TrnSoRequestDTO.TrnDetailDTO trnDetailDTO : trnSoRequestDTO.getSoDetailsList()){
                trnDetailDTO.getDetailsForm().setId(null);
            }
        }
        trnDetailSoMapper.convertUpdateRequestListToEntityListAndDeleteNonExistingIds(trnSoRequestDTO,trnHeaderAsnToBeUpdated,dateAndTimeRequestDto);
        TrnHeaderAsn trnHeaderAsn = trnHeaderAsnService.update(trnHeaderAsnToBeUpdated);
        TrnSoResponseDTO trnSoResponseDTO = convertHeaderEntityToResponse(trnHeaderAsn);
        if (!CollectionUtils.isEmpty(trnHeaderAsn.getTrnDetailList())) {
            List<TrnResponseDTO.TrnDetailDTO> trnDetailDTOList = convertDetailEntityToResponse(trnHeaderAsn.getTrnDetailList(),"No");
            trnSoResponseDTO.setSoDetailsList(trnDetailDTOList);
        }
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,trnSoResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> fetchById(@PathVariable Long id,@RequestParam(required = false) String grnId){
        log.info("ENTRY - Fetch TRN Header by id");
        TrnHeaderAsn trnHeaderAsn = trnHeaderAsnService.findById(id);
        TrnSoResponseDTO trnSoResponseDTO = convertHeaderEntityToResponse(trnHeaderAsn);
        if (!CollectionUtils.isEmpty(trnHeaderAsn.getTrnDetailList())) {
            List<TrnDetail> trnDetailList = trnDetailSoMapper.removeDuplicateDetails(trnHeaderAsn.getTrnDetailList());
            List<TrnResponseDTO.TrnDetailDTO> trnDetailDTOList = convertDetailEntityToResponse(trnDetailList,grnId);
            trnSoResponseDTO.setSoDetailsList(trnDetailDTOList);
        }
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,trnSoResponseDTO,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/customsAddlDetails/configuration")
    public ResponseEntity<ResponseDTO> fetchCustomsAddlDetailsConfig(){
        Iterable<ConfigurationMaster> configurationMasterIterable = configurationMasterService.getAllForWmsSoCustoms();
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
                getAllForWmsCustomerSo(id);
        Iterable<CustomerConfigurationMasterResponseDTO> customerConfigurationMasterResponseDTOIterable = customerConfigurationMapper.
                convertEntityIterableToResponseIterable(customerConfigurationMasterIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(),Boolean.TRUE,
                customerConfigurationMasterResponseDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    private TrnHeaderAsn setTrnHeaderSoSave(TrnSoRequestDTO trnSoRequestDTO,DateAndTimeRequestDto dateAndTimeRequestDto){
        TrnHeaderAsn trnHeaderAsn = trnHeaderSoMapper.convertRequestToEntity(trnSoRequestDTO,dateAndTimeRequestDto);
        trnHeaderAsn.setTransactionType("SO");
        trnHeaderAsn.setTransactionUid(trnDetailSoService.generateTransactionUid());
        trnHeaderAsn.setTransactionStatus("Booked");
        List<TrnHeaderTransportation> trnHeaderTransportationList = trnHeaderTransportationMapper.convertRequestListToEntityList(trnSoRequestDTO.getTrnHeaderTransportationList(),dateAndTimeRequestDto);
        trnHeaderAsn.setTrnHeaderTransportationList(trnHeaderTransportationList);
        List<TrnHeaderCustomsAddlDetails> trnHeaderCustomsAddlDetailsList = trnHeaderCustomsAddlDetailsMapper.convertRequestListToEntityList(trnSoRequestDTO.getTrnHeaderCustomsAddlDetailsList(),dateAndTimeRequestDto);
        trnHeaderAsn.setTrnHeaderCustomsAddlDetailsList(trnHeaderCustomsAddlDetailsList);
        List<TrnHeaderAddlDetails> trnHeaderAddlDetailsList = trnHeaderAddlDetailsMapper.convertRequestListToEntityList(trnSoRequestDTO.getTrnHeaderAddlDetailsList(),dateAndTimeRequestDto);
        trnHeaderAsn.setTrnHeaderAddlDetailsList(trnHeaderAddlDetailsList);
        TrnHeaderSo trnHeaderSo = trnHeaderSoFreightMapper.convertRequestToEntity(trnSoRequestDTO,dateAndTimeRequestDto);
        TrnHeaderParty trnHeaderParty = trnHeaderPartyMapper.convertRequestToEntity(trnSoRequestDTO.getParty(),dateAndTimeRequestDto,"so");
        if (trnSoRequestDTO.getTrnHeaderFreightShipping() != null){
            TrnHeaderFreightShipping trnHeaderFreightShipping = trnHeaderFreightShippingMapper.convertRequestToEntity(trnSoRequestDTO.getTrnHeaderFreightShipping(),dateAndTimeRequestDto);
            trnHeaderAsn.setTrnHeaderFreightShipping(trnHeaderFreightShipping);
            trnHeaderFreightShipping.setTrnHeaderAsn(trnHeaderAsn);
        }
        if (trnSoRequestDTO.getTrnHeaderCustomsDocument() != null){
            TrnHeaderCustomsDocument trnHeaderCustomsDocument = trnHeaderCustomsDocumentMapper.convertRequestToEntity(trnSoRequestDTO.getTrnHeaderCustomsDocument(),dateAndTimeRequestDto);
            trnHeaderAsn.setTrnHeaderCustomsDocument(trnHeaderCustomsDocument);
            trnHeaderCustomsDocument.setTrnHeaderAsn(trnHeaderAsn);
        }
        trnHeaderAsn.setTrnHeaderSo(trnHeaderSo);
        trnHeaderAsn.setTrnHeaderParty(trnHeaderParty);
        trnHeaderSo.setTrnHeaderAsn(trnHeaderAsn);
        trnHeaderParty.setTrnHeaderAsn(trnHeaderAsn);
        return trnHeaderAsn;
    }

    private void setTrnHeaderSoUpdate(TrnSoRequestDTO trnSoRequestDTO, TrnHeaderAsn trnHeaderAsnToBeUpdated, DateAndTimeRequestDto dateAndTimeRequestDto){
        trnHeaderSoMapper.convertUpdateRequestToEntity(trnSoRequestDTO, trnHeaderAsnToBeUpdated,dateAndTimeRequestDto);
        trnHeaderSoFreightMapper.convertUpdateRequestToEntity(trnSoRequestDTO, trnHeaderAsnToBeUpdated, dateAndTimeRequestDto);
        trnHeaderPartyMapper.convertUpdateRequestToEntity(trnSoRequestDTO.getParty(), trnHeaderAsnToBeUpdated, dateAndTimeRequestDto,"so");
        if (trnHeaderAsnToBeUpdated.getTrnHeaderFreightShipping() != null) {
            if (trnSoRequestDTO.getTrnHeaderFreightShipping() != null) {
                trnHeaderFreightShippingMapper.convertUpdateRequestToEntity(trnSoRequestDTO.getTrnHeaderFreightShipping(), trnHeaderAsnToBeUpdated, dateAndTimeRequestDto);
            } else {
                Long trnHeaderFreightShippingId = trnHeaderAsnToBeUpdated.getTrnHeaderFreightShipping().getId();
                trnHeaderAsnToBeUpdated.setTrnHeaderFreightShipping(null);
                trnHeaderFreightShippingService.deleteById(trnHeaderFreightShippingId);
            }
        }else if (trnSoRequestDTO.getTrnHeaderFreightShipping() != null){
            TrnHeaderFreightShipping trnHeaderFreightShipping = trnHeaderFreightShippingMapper.convertRequestToEntity(trnSoRequestDTO.getTrnHeaderFreightShipping(), dateAndTimeRequestDto);
            trnHeaderAsnToBeUpdated.setTrnHeaderFreightShipping(trnHeaderFreightShipping);
            trnHeaderFreightShipping.setTrnHeaderAsn(trnHeaderAsnToBeUpdated);
        }
        if (trnHeaderAsnToBeUpdated.getTrnHeaderCustomsDocument() != null) {
            if (trnSoRequestDTO.getTrnHeaderCustomsDocument() != null) {
                trnHeaderCustomsDocumentMapper.convertUpdateRequestToEntity(trnSoRequestDTO.getTrnHeaderCustomsDocument(), trnHeaderAsnToBeUpdated, dateAndTimeRequestDto);
            } else {
                Long trnHeaderCustomsDocumentId = trnHeaderAsnToBeUpdated.getTrnHeaderCustomsDocument().getId();
                trnHeaderAsnToBeUpdated.setTrnHeaderCustomsDocument(null);
                trnHeaderCustomsDocumentService.deleteById(trnHeaderCustomsDocumentId);
            }
        }else if (trnSoRequestDTO.getTrnHeaderCustomsDocument() != null){
            TrnHeaderCustomsDocument trnHeaderCustomsDocument = trnHeaderCustomsDocumentMapper.convertRequestToEntity(trnSoRequestDTO.getTrnHeaderCustomsDocument(), dateAndTimeRequestDto);
            trnHeaderAsnToBeUpdated.setTrnHeaderCustomsDocument(trnHeaderCustomsDocument);
            trnHeaderCustomsDocument.setTrnHeaderAsn(trnHeaderAsnToBeUpdated);
        }
        trnHeaderTransportationMapper.convertUpdateRequestListToEntityListAndDeleteNonExistingIds(trnSoRequestDTO.getTrnHeaderTransportationList(),trnHeaderAsnToBeUpdated,dateAndTimeRequestDto);
        trnHeaderCustomsAddlDetailsMapper.convertUpdateRequestListToEntityListAndDeleteNonExistingIds(trnSoRequestDTO.getTrnHeaderCustomsAddlDetailsList(),trnHeaderAsnToBeUpdated,dateAndTimeRequestDto);
        trnHeaderAddlDetailsMapper.convertUpdateRequestListToEntityListAndDeleteNonExistingIds(trnSoRequestDTO.getTrnHeaderAddlDetailsList(),trnHeaderAsnToBeUpdated,dateAndTimeRequestDto);
    }

    private List<TrnDetail> setTrnDetailListSave(List<TrnSoRequestDTO.TrnDetailDTO> soDetailsList,DateAndTimeRequestDto dateAndTimeRequestDTO,TrnHeaderAsn trnHeaderAsn){
        List<TrnDetail> trnDetailList = new ArrayList<>();
        Integer count = 1;
        for (TrnSoRequestDTO.TrnDetailDTO trnDetailRequestDTO : soDetailsList) {
            TrnDetail trnDetail = trnDetailSoMapper.convertTrnDetailDTOToTrnDetail(trnDetailRequestDTO,dateAndTimeRequestDTO);
            trnDetail.setTransactionUid(trnHeaderAsn.getTransactionUid());
            trnDetail.setTransactionSlNo(count);
            TrnDetailAsn trnDetailAsn = trnDetailSoMapper.convertTrnDetailDTOToTrnDetailAsn(trnDetailRequestDTO,dateAndTimeRequestDTO);
            trnDetailAsn.setTransactionSlNo(count);
            trnDetail.setTrnDetailAsn(trnDetailAsn);
            trnDetailAsn.setTrnDetail(trnDetail);
            TrnDetailLot trnDetailLot  = trnDetailSoMapper.convertTrnDetailDTOToLotList(trnDetailRequestDTO,dateAndTimeRequestDTO);
            if (trnDetailLot != null) {
                trnDetailLot.setTransactionSlNo(count);
                trnDetail.setTrnDetailLot(trnDetailLot);
                trnDetailLot.setTrnDetail(trnDetail);
            }
            TrnDetailQc trnDetailQc = trnDetailSoMapper.convertTrnDetailDTOToTrnDetailQc(trnDetailRequestDTO,dateAndTimeRequestDTO);
            if(trnDetailQc != null) {
                trnDetailQc.setTransactionSlNo(count);
                trnDetail.setTrnDetailQc(trnDetailQc);
                trnDetailQc.setTrnDetail(trnDetail);
            }
            TrnDetailSo trnDetailSo = trnDetailSoMapper.convertTrnDetailDTOToTrnDetailSoCustoms(trnDetailRequestDTO,dateAndTimeRequestDTO);
            if (trnDetailSo != null) {
                trnDetailSo.setTransactionSlNo(count);
                trnDetail.setTrnDetailSo(trnDetailSo);
                trnDetailSo.setTrnDetail(trnDetail);
            }
            trnDetail.setId(null);
            trnDetail.setTrnHeaderAsn(trnHeaderAsn);
            trnDetailList.add(trnDetail);
            count++;
        }
        return trnDetailList;
    }

    public TrnSoResponseDTO convertHeaderEntityToResponse(TrnHeaderAsn trnHeaderAsn){
        TrnSoResponseDTO trnSoResponseDTO = new TrnSoResponseDTO();
        TrnSoResponseDTO.TrnHeaderSoDTO trnHeaderSoDTO = trnHeaderSoMapper.convertEntityToResponse(trnHeaderAsn);
        trnSoResponseDTO.setTrnHeaderSo(trnHeaderSoDTO);
        trnSoResponseDTO.setTrnHeaderSoFreight(trnHeaderSoFreightMapper.convertEntityToResponse(trnHeaderAsn));
        if (trnHeaderAsn.getTrnHeaderParty() != null){
            List<TrnResponseDTO.PartyCustomerDTO> partyCustomerDTOList = trnHeaderPartyMapper.convertEntityToResponseList(trnHeaderAsn);
            trnSoResponseDTO.setParty(partyCustomerDTOList);
        }
        trnSoResponseDTO.setTrnHeaderTransportationList(trnHeaderTransportationMapper.
                convertEntityListToResponseList(trnHeaderAsn.getTrnHeaderTransportationList()));
        trnSoResponseDTO.setTrnHeaderFreightShipping(trnHeaderFreightShippingMapper.convertEntityToResponse(trnHeaderAsn));
        trnSoResponseDTO.setTrnHeaderCustomsDocument(trnHeaderCustomsDocumentMapper.convertEntityToResponse(trnHeaderAsn));
        trnSoResponseDTO.setTrnHeaderCustomsAddlDetailsList(trnHeaderCustomsAddlDetailsMapper.
                convertEntityListToResponseList(trnHeaderAsn.getTrnHeaderCustomsAddlDetailsList()));
        trnSoResponseDTO.setTrnHeaderAddlDetailsList(trnHeaderAddlDetailsMapper.
                convertEntityListToResponseList(trnHeaderAsn.getTrnHeaderAddlDetailsList()));
        trnSoResponseDTO.setSoDetailsList(trnDetailSoMapper.
                convertDetailEntityToResponse(trnHeaderAsn.getTrnDetailList(),"No"));
        Boolean isBackOrder=trnDetailSoService.isBackOrder(trnHeaderAsn.getId());
        if(isBackOrder){
            trnSoResponseDTO.setIsBackOrder("Yes");
        }else {
            trnSoResponseDTO.setIsBackOrder("No");
        }
        return trnSoResponseDTO;
    }

    public List<TrnResponseDTO.TrnDetailDTO> convertDetailEntityToResponse(List<TrnDetail> trnDetailList,String forGrn){
        List<TrnResponseDTO.TrnDetailDTO> trnDetailDTOList = new ArrayList<>();
        for (TrnDetail trnDetail : trnDetailList) {
            TrnResponseDTO.TrnDetailDTO trnDetailDTO = new TrnResponseDTO.TrnDetailDTO();
            TrnResponseDTO.TrnDetailDTO.DetailsFormDTO detailsFormDTO = trnDetailMapper.convertTrnDetailToDetailsFormDTO(null,trnDetail,forGrn);
            trnDetailDTO.setDetailsForm(detailsFormDTO);
            TrnResponseDTO.TrnDetailDTO.MoreLotFormDTO moreLotFormDTO = trnDetailMapper.convertTrnDetailToMoreLotFormDTO(trnDetail);
            trnDetailDTO.setMoreLotForm(moreLotFormDTO);
            List<TrnResponseDTO.TrnDetailDTO.LotDTO> lotDTOList= trnDetailMapper.convertTrnDetailToMoreLotList(trnDetail);
            trnDetailDTO.setMoreLotList(lotDTOList);
            TrnResponseDTO.TrnDetailDTO.MoreQCFormDTO moreQCFormDTO = trnDetailMapper.convertTrnDetailToMoreQCFormDTO(trnDetail);
            trnDetailDTO.setMoreQCForm(moreQCFormDTO);
            TrnResponseDTO.TrnDetailDTO.MoreFtaFormDTO moreFtaFormDTO = trnDetailMapper.convertTrnDetailToMoreFtaFormDTO(trnDetail);
            trnDetailDTO.setMoreFtaForm(moreFtaFormDTO);
            TrnResponseDTO.TrnDetailDTO.MoreSoFormDTO moreSoFormDTO= trnDetailSoMapper.convertTrnDetailToMoreSoFormDTO(trnDetail);
            trnDetailDTO.setMoreSoForm(moreSoFormDTO);
            trnDetailDTOList.add(trnDetailDTO);
        }
        return trnDetailDTOList;
    }

    private void validateTrnHeader(TrnSoRequestDTO trnSoRequestDTO) {
        log.info("ENTRY - Validate TRN Header");
        Long userId = Long.parseLong(trnSoRequestDTO.getUserId());
        Long wareHouseId = Long.parseLong(trnSoRequestDTO.getTrnHeaderSo().getWareHouse().getId());
        Long customerId = Long.parseLong(trnSoRequestDTO.getTrnHeaderSo().getCustomer().getId());
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
    public DateAndTimeRequestDto setDateAndTimeRequestDtoCreate(TrnSoRequestDTO trnSoRequestDTO){
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Create");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(trnSoRequestDTO.getUser());
        dateAndTimeRequestDto.setCreatedDate(new Date());
        dateAndTimeRequestDto.setLastModifiedBy(trnSoRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(1L);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

    public DateAndTimeRequestDto setDateAndTimeRequestDtoUpdate(TrnSoRequestDTO trnSoRequestDTO, TrnHeaderAsn trnHeaderAsn) {
        log.info("ENTRY - Set createdUser, createdDate, updatedUser, updatedDate and version in Update");
        DateAndTimeRequestDto dateAndTimeRequestDto = new DateAndTimeRequestDto();
        dateAndTimeRequestDto.setCreatedBy(trnHeaderAsn.getCreatedBy());
        dateAndTimeRequestDto.setCreatedDate(trnHeaderAsn.getCreatedDate());
        dateAndTimeRequestDto.setLastModifiedBy(trnSoRequestDTO.getUser());
        dateAndTimeRequestDto.setLastModifiedDate(new Date());
        dateAndTimeRequestDto.setVersion(trnHeaderAsn.getVersion() + 1);
        log.info("EXIT");
        return dateAndTimeRequestDto;
    }

}
