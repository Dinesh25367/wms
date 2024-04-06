package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.GrnHeaderRequestDTO;
import com.newage.wms.application.dto.requestdto.TrnRequestDTO;
import com.newage.wms.application.dto.responsedto.GrnHeaderResponseDTO;
import com.newage.wms.application.dto.responsedto.SkuResponseDTO;
import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.entity.*;
import com.newage.wms.entity.QConfigurationMaster;
import com.newage.wms.service.*;
import com.newage.wms.service.impl.GrnCalculation;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Component
public class GrnDetailMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TrnDetailMapper trnDetailMapper;

    @Autowired
    private GrnDetailLotMapper grnDetailLotMapper;

    @Autowired
    private TrnDetailService trnDetailService;

    @Autowired
    private TrnHeaderAsnService trnHeaderAsnService;

    @Autowired
    private GrnHeaderService grnHeaderService;

    @Autowired
    private GrnLotDetailService grnLotDetailService;

    @Autowired
    private GrnDetailService grnDetailService;

    @Autowired
    private UomMasterService uomMasterService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private CurrencyMasterService currencyMasterService;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SkuMasterService skuMasterService;

    @Autowired
    private HsCodeMasterService hsCodeMasterService;

    @Autowired
    private InventoryStatusMasterService inventoryStatusMasterService;

    @Autowired
    private TrnHeaderTransportationService trnHeaderTransportationService;

    @Autowired
    private ConfigurationMasterService configurationMasterService;

    @Autowired
    private GrnCalculation grnCalculation;

    private Integer transactionSerialNo;

    private double totalExpQtyInUom;

    private double totalExpQtyInRUom;

    private String status=null;

    public  List<TrnDetail> trnDetailList ;


    public List<GrnDetail> convertRequestListToEntityList(GrnHeader grnHeader,GrnHeaderRequestDTO grnHeaderRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto,boolean isUpdate) {
        if (!CollectionUtils.isEmpty(grnHeaderRequestDTO.getAsnDetailsList())) {
            List<GrnDetail> grnDetailList = new ArrayList<>();
            transactionSerialNo = 1;
            totalExpQtyInUom = 0.0;
            totalExpQtyInRUom = 0.0;
            status = "";
            for (GrnHeaderRequestDTO.TrnDetailDTO trnDetailDTO : grnHeaderRequestDTO.getAsnDetailsList()){
                if (trnDetailDTO.getDetailsForm().getId() != null
                        && !trnDetailDTO.getDetailsForm().getId().isEmpty()
                        && !trnDetailDTO.getDetailsForm().getId().isBlank() ){
                    TrnDetail trnDetail = trnDetailService.getById(Long.valueOf(trnDetailDTO.getDetailsForm().getId()));
                    double allCompletedActualQtyForOneDetail = grnDetailService.getAllCompletedQtyForOneDetail(trnDetail,grnHeader,true);
                    double allCompletedActualRQtyForOneDetail = grnDetailService.getAllCompletedQtyForOneDetail(trnDetail,grnHeader,false);
                    if (trnDetail.getExpQty() - allCompletedActualQtyForOneDetail < 0){
                        totalExpQtyInUom = 0.0;
                        totalExpQtyInRUom = 0.0;
                    }else {
                        totalExpQtyInUom = trnDetail.getExpQty() - allCompletedActualQtyForOneDetail;
                        totalExpQtyInRUom = trnDetail.getRqty() - allCompletedActualRQtyForOneDetail;
                    }
                }else {
                    totalExpQtyInUom = trnDetailDTO.getDetailsForm().getExpQty();
                    totalExpQtyInRUom = trnDetailDTO.getDetailsForm().getExpQty();
                }
                List<GrnDetail> grnDetailList2 = convertRequestListToEntityListForOneTrnDetail(grnHeader,trnDetailDTO,grnHeaderRequestDTO,dateAndTimeRequestDto,isUpdate);
                grnDetailList.addAll(grnDetailList2);
            }
            if (status != null && !status.isEmpty() && !status.isBlank()) {
                grnHeader.setStatus(status);
            }else {
                grnHeader.setStatus("RECEIVED");
            }
            return grnDetailList;
        } else {
            return Collections.emptyList();
        }
    }

    private List<GrnDetail> convertRequestListToEntityListForOneTrnDetail(GrnHeader grnHeader , GrnHeaderRequestDTO.TrnDetailDTO trnDetailDTO, GrnHeaderRequestDTO grnHeaderRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto,boolean isUpdate) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);              // Ensure strict matching
        if (trnDetailDTO.getDetailsForm().getId() != null &&
                (trnDetailDTO.getDetailsForm().getId().isEmpty() || trnDetailDTO.getDetailsForm().getId().isBlank())){
            TrnDetail trnDetail = trnDetailService.save(setTrnDetailFromGrnRequest(trnDetailDTO.getDetailsForm(),dateAndTimeRequestDto,grnHeaderRequestDTO));
            trnDetailDTO.getDetailsForm().setId(trnDetail.getId().toString());
        }
        if (!CollectionUtils.isEmpty(trnDetailDTO.getGrnDetailDTOList())) {
            List<GrnDetail> grnDetailList = new ArrayList<>();
            trnDetailList = new ArrayList<>();
            Long id = 0L;
            double totalReceivingQtyInUom= 0.0;
            double totalActualReceivingQty = 0.0;
            for (GrnHeaderRequestDTO.TrnDetailDTO.GrnDetailDTO grnDetailDTO : trnDetailDTO.getGrnDetailDTOList()){
                TrnDetail trnDetail =trnDetailService.getById(Long.valueOf(trnDetailDTO.getDetailsForm().getId()));
                UomMaster uomMaster = uomMasterService.getUomById(Long.valueOf(grnDetailDTO.getUom().getId()));
                double uomRatio = uomMaster.getRatio();
                double ruomRatioForUOM = trnDetail.getUomMaster().getRatio();
                double roundingUOM = 5;
                if (trnDetail.getUomMaster().getDecimalPlaces() != null) {
                    roundingUOM = trnDetail.getUomMaster().getDecimalPlaces();
                }
                double ruomRatioForRUOM = trnDetail.getRUomMaster().getRatio();
                double roundingRUOM = trnDetail.getRUomMaster().getDecimalPlaces();
                totalReceivingQtyInUom =totalReceivingQtyInUom + grnCalculation.getUomRatioConvertedQty(uomRatio, ruomRatioForUOM,grnDetailDTO.getReceivingQty());
                totalReceivingQtyInUom =  grnCalculation.getRoundedValue(roundingUOM,totalReceivingQtyInUom);
                totalActualReceivingQty =totalActualReceivingQty + grnCalculation.getUomRatioConvertedQty(uomRatio, ruomRatioForRUOM,grnDetailDTO.getReceivingQty());
                totalActualReceivingQty =  grnCalculation.getRoundedValue(roundingRUOM,totalActualReceivingQty);
                GrnDetail grnDetail = convertRequestToEntity(trnDetailDTO.getDetailsForm().getId(),grnDetailDTO,dateAndTimeRequestDto);
                GrnLotDetail grnLotDetail = grnDetailLotMapper.convertRequestToEntity(trnDetailDTO,grnDetailDTO,dateAndTimeRequestDto);
                grnLotDetail.setTransactionSlNo(transactionSerialNo);
                grnDetail.setGrnLotDetail(grnLotDetail);
                grnLotDetail.setGrnDetail(grnDetail);
                id = Long.parseLong(trnDetailDTO.getDetailsForm().getId());
                grnDetail.setTransactionSlNo(transactionSerialNo);
                grnDetail.setGrnHeader(grnHeader);
                grnDetailList.add(grnDetail);
                transactionSerialNo++;
            }
            if (totalExpQtyInUom > totalReceivingQtyInUom) {
                status = "PART RECEIVED";
            }
            TrnDetail trnDetail = trnDetailService.getById(id);
            if (trnDetail.getTrnDetailAsn() != null) {
                double allCompletedActualQtyForOneDetail = grnDetailService.getAllCompletedQtyForOneDetail(trnDetail,grnHeader,true);
                double allCompletedActualRQtyForOneDetail = grnDetailService.getAllCompletedQtyForOneDetail(trnDetail,grnHeader,false);
                double newTotalReceivingQtyInUom = allCompletedActualQtyForOneDetail + totalReceivingQtyInUom;
                double newTotalActualReceivingQty = allCompletedActualRQtyForOneDetail + totalActualReceivingQty;
                double uomRounding = trnDetail.getUomMaster().getDecimalPlaces();
                double rUomRounding = trnDetail.getRUomMaster().getDecimalPlaces();
                newTotalReceivingQtyInUom = grnCalculation.getRoundedValue(uomRounding,newTotalReceivingQtyInUom);
                newTotalActualReceivingQty = grnCalculation.getRoundedValue(rUomRounding,newTotalActualReceivingQty);
                trnDetail.getTrnDetailAsn().setActualQty(newTotalReceivingQtyInUom);
                trnDetail.getTrnDetailAsn().setActualRQty(newTotalActualReceivingQty);
                Double totalAmount = 0.0;
                if (trnDetail.getTrnHeaderAsn() != null) {
                    totalAmount = trnDetail.getTrnDetailAsn().getDocumentLineTotalAmount();
                }
                Double actualAmount = (totalAmount/trnDetail.getExpQty())*newTotalReceivingQtyInUom;
                if (isUpdate){
                    actualAmount = ((totalAmount/trnDetail.getExpQty())*newTotalReceivingQtyInUom);
                }
                actualAmount = grnCalculation.getRoundedValue(5.0,actualAmount);
                trnDetail.getTrnDetailAsn().setActualLineTotalAmount(actualAmount);
            }
            trnDetailList.add(trnDetail);
            if(totalReceivingQtyInUom==0){
                return Collections.emptyList();
            }
            return grnDetailList;
        } else {
            return Collections.emptyList();
        }
    }

    private GrnDetail convertRequestToEntity(String trnDetailId,GrnHeaderRequestDTO.TrnDetailDTO.GrnDetailDTO grnDetailDTO, DateAndTimeRequestDto dateAndTimeRequestDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);              // Ensure strict matching
        GrnDetail grnDetail = modelMapper.map(grnDetailDTO,GrnDetail.class);
        grnDetail.setReceivingRQty(grnDetailDTO.getReceivingQty());
        if (grnDetailDTO.getDimensionalDetails() != null) {
            grnDetail.setHeight(grnDetailDTO.getDimensionalDetails().getHeight());
            grnDetail.setLength(grnDetailDTO.getDimensionalDetails().getLength());
            grnDetail.setWidth(grnDetailDTO.getDimensionalDetails().getWidth());
            grnDetail.setUnit(grnDetailDTO.getDimensionalDetails().getUnit());
            grnDetail.setIsChecked(grnDetailDTO.getDimensionalDetails().getIsChecked());

        }
        modelMapper.map(dateAndTimeRequestDto,grnDetail);
        TrnDetail trnDetail = trnDetailService.getById(Long.parseLong(trnDetailId));
        //set expected qty while receiving
        GrnHeader grnHeader = new GrnHeader();
        double allCompletedActualQtyForOneDetail =  grnDetailService.getAllCompletedQtyForOneDetail(trnDetail, grnHeader,true);
        double expQtyWhileReceiving = trnDetail.getExpQty() - allCompletedActualQtyForOneDetail;
        if (expQtyWhileReceiving <0){
            expQtyWhileReceiving = 0.0;
        }
        double rounding = trnDetail.getUomMaster().getDecimalPlaces();
        expQtyWhileReceiving = grnCalculation.getRoundedValue(rounding,expQtyWhileReceiving);
        grnDetail.setExpQtyWhileReceiving(expQtyWhileReceiving);
        grnDetail.setTrnDetailMaster(trnDetail);
        if (trnDetail.getUomMaster() != null) {
            UomMaster uomMaster = uomMasterService.getUomById(trnDetail.getUomMaster().getId());
            grnDetail.setUomMaster(uomMaster);
        }
        if (grnDetailDTO.getUom() != null){
            UomMaster rUomMaster = uomMasterService.getUomById(Long.parseLong(grnDetailDTO.getUom().getId()));
            grnDetail.setRUomMaster(rUomMaster);
        }
        if (grnDetailDTO.getLocation() != null){
            Location location = locationService.getLocationById(Long.parseLong(grnDetailDTO.getLocation().getId()));
            grnDetail.setLocationMaster(location);
        }
        if (grnDetailDTO.getInventoryStatus() != null) {
            InventoryStatusMaster inventoryStatusMaster = inventoryStatusMasterService.getById(Long.parseLong(grnDetailDTO.getInventoryStatus().getId()));
            grnDetail.setInventoryStatusMaster(inventoryStatusMaster);
        }
        if (grnDetailDTO.getTransportation() != null) {
            TrnHeaderTransportation trnHeaderTransportation = trnHeaderTransportationService.getById(Long.parseLong(grnDetailDTO.getTransportation().getId()));
            grnDetail.setTrnHeaderTransportation(trnHeaderTransportation);
        }
        grnDetail.setDeleted("0");
        return grnDetail;
    }





    public void convertUpdateRequestListToEntityListAndDeleteNonExistingIds(GrnHeader grnHeader,GrnHeaderRequestDTO grnHeaderRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto,boolean isUpdate){
        List<GrnDetail> grnDetailListToBeDeleted = new ArrayList<>();
        if (grnHeader.getGrnDetailList() != null && !grnHeader.getGrnDetailList().isEmpty()) {
            grnDetailListToBeDeleted = grnHeader.getGrnDetailList();
        }
        if (checkIfRequestDetailListIsEmpty(grnHeaderRequestDTO.getAsnDetailsList())) {
            if (!CollectionUtils.isEmpty(grnHeader.getGrnDetailList())) {
                convertUpdateRequestToEntityList(grnHeader,grnHeaderRequestDTO, dateAndTimeRequestDto,true);
                List<GrnHeaderRequestDTO.TrnDetailDTO.GrnDetailDTO> grnDetailDTOList = getAllGrnDetailsFromRequest(grnHeaderRequestDTO.getAsnDetailsList());
                identifyAndDeleteNonExistingIds(grnDetailDTOList,grnDetailListToBeDeleted);
            }else {
                List<GrnDetail> grnDetailList = convertRequestListToEntityList(grnHeader, grnHeaderRequestDTO,dateAndTimeRequestDto,false);
                grnHeader.setGrnDetailList(grnDetailList);
            }
        }else
        {
            grnHeader.setGrnDetailList(null);
            if (grnDetailListToBeDeleted != null) {
                grnDetailService.deleteAllInIterable(grnDetailListToBeDeleted);
            }
        }
    }

    private boolean checkIfRequestDetailListIsEmpty(List<GrnHeaderRequestDTO.TrnDetailDTO> asnDetailsList){
        if (!CollectionUtils.isEmpty(asnDetailsList)){
            List<GrnHeaderRequestDTO.TrnDetailDTO.GrnDetailDTO> grnDetailDTOList = getAllGrnDetailsFromRequest(asnDetailsList);
            if (!CollectionUtils.isEmpty(grnDetailDTOList)) {
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    private List<GrnHeaderRequestDTO.TrnDetailDTO.GrnDetailDTO> getAllGrnDetailsFromRequest(List<GrnHeaderRequestDTO.TrnDetailDTO> asnDetailsList) {
        List<GrnHeaderRequestDTO.TrnDetailDTO.GrnDetailDTO> grnDetailDTOList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(asnDetailsList)) {
            for (GrnHeaderRequestDTO.TrnDetailDTO trnDetailDTO : asnDetailsList) {
                if (!CollectionUtils.isEmpty(trnDetailDTO.getGrnDetailDTOList())) {
                    grnDetailDTOList.addAll(trnDetailDTO.getGrnDetailDTOList());
                }
            }
        }
        return grnDetailDTOList;
    }

    private void identifyAndDeleteNonExistingIds(List<GrnHeaderRequestDTO.TrnDetailDTO.GrnDetailDTO> grnDetailDTOList,List<GrnDetail> grnDetailListToBeDeleted ){
        Set<Long> newIdsList = new HashSet<>();
        for (GrnHeaderRequestDTO.TrnDetailDTO.GrnDetailDTO grnDetailDTO : grnDetailDTOList) {
            if (grnDetailDTO.getGrnDetailId() != null && !grnDetailDTO.getGrnDetailId().isEmpty() &&
                    !grnDetailDTO.getGrnDetailId().isBlank()) {
                newIdsList.add(Long.parseLong(grnDetailDTO.getGrnDetailId()));
            }
        }
        if ( !newIdsList.isEmpty()) {
            Iterator<GrnDetail> iterator = grnDetailListToBeDeleted.iterator();
            while (iterator.hasNext()) {
                GrnDetail grnDetail = iterator.next();
                Long id = grnDetail.getId();
                // Check if the id exists in firstIds set
                if (newIdsList.contains(id)) {
                    iterator.remove(); // Remove the element from secondList
                }
            }
            grnDetailService.deleteAllInIterable(grnDetailListToBeDeleted);
        }else {grnDetailService.deleteAllInIterable(grnDetailListToBeDeleted);}
    }

    public List<GrnDetail> convertUpdateRequestToEntityList(GrnHeader grnHeader,GrnHeaderRequestDTO grnHeaderRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto,boolean isUpdate) {
        if (!CollectionUtils.isEmpty(grnHeaderRequestDTO.getAsnDetailsList())) {
            List<GrnDetail> grnDetailList = new ArrayList<>();
            transactionSerialNo = 1;
            totalExpQtyInUom = 0.0;
            totalExpQtyInRUom = 0.0;
            status = "";
            for (GrnHeaderRequestDTO.TrnDetailDTO trnDetailDTO : grnHeaderRequestDTO.getAsnDetailsList()){
                if (trnDetailDTO.getDetailsForm().getId() != null
                        && !trnDetailDTO.getDetailsForm().getId().isEmpty()
                        && !trnDetailDTO.getDetailsForm().getId().isBlank() ){
                    TrnDetail trnDetail = trnDetailService.getById(Long.valueOf(trnDetailDTO.getDetailsForm().getId()));
                    double allCompletedActualQtyForOneDetail = grnDetailService.getAllCompletedQtyForOneDetail(trnDetail,grnHeader,true);
                    double allCompletedActualRQtyForOneDetail = grnDetailService.getAllCompletedQtyForOneDetail(trnDetail,grnHeader,false);
                    if (trnDetail.getExpQty() - allCompletedActualQtyForOneDetail < 0){
                        totalExpQtyInUom = 0.0;
                        totalExpQtyInRUom = 0.0;
                    }else {
                        totalExpQtyInUom = trnDetail.getExpQty() - allCompletedActualQtyForOneDetail;
                        totalExpQtyInRUom = trnDetail.getRqty() - allCompletedActualRQtyForOneDetail;
                    }
                }else {
                    totalExpQtyInUom = trnDetailDTO.getDetailsForm().getExpQty();
                    totalExpQtyInRUom = trnDetailDTO.getDetailsForm().getExpQty();
                }
                List<GrnDetail> grnDetailList2 = convertUpdateRequestToEntityListForOneTrnDetail(grnHeader,trnDetailDTO,grnHeaderRequestDTO,dateAndTimeRequestDto,isUpdate);
                grnDetailList.addAll(grnDetailList2);
            }
            if (status != null && !status.isEmpty() && !status.isBlank()) {
                grnHeader.setStatus(status);
            }else {
                grnHeader.setStatus("RECEIVED");
            }
            grnHeader.setGrnDetailList(grnDetailList);
            return grnDetailList;
        } else {
            return Collections.emptyList();
        }
    }

    private List<GrnDetail> convertUpdateRequestToEntityListForOneTrnDetail(GrnHeader grnHeader , GrnHeaderRequestDTO.TrnDetailDTO trnDetailDTO, GrnHeaderRequestDTO grnHeaderRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto,boolean isUpdate) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);              // Ensure strict matching
        if (trnDetailDTO.getDetailsForm().getId() != null &&
                (trnDetailDTO.getDetailsForm().getId().isEmpty() || trnDetailDTO.getDetailsForm().getId().isBlank())){
            TrnDetail trnDetail = trnDetailService.save(setTrnDetailFromGrnRequest(trnDetailDTO.getDetailsForm(),dateAndTimeRequestDto,grnHeaderRequestDTO));
            trnDetailDTO.getDetailsForm().setId(trnDetail.getId().toString());
        }
        if (!CollectionUtils.isEmpty(trnDetailDTO.getGrnDetailDTOList())) {
            List<GrnDetail> grnDetailList = new ArrayList<>();
            trnDetailList = new ArrayList<>();
            Long id = 0L;
            double totalReceivingQtyInUom= 0.0;
            double totalActualReceivingQty = 0.0;
            for (GrnHeaderRequestDTO.TrnDetailDTO.GrnDetailDTO grnDetailDTO : trnDetailDTO.getGrnDetailDTOList()){
                TrnDetail trnDetail =trnDetailService.getById(Long.valueOf(trnDetailDTO.getDetailsForm().getId()));
                UomMaster uomMaster = uomMasterService.getUomById(Long.valueOf(grnDetailDTO.getUom().getId()));
                double uomRatio = uomMaster.getRatio();
                double ruomRatioForUOM = trnDetail.getUomMaster().getRatio();
                double roundingUOM = 5;
                if (trnDetail.getUomMaster().getDecimalPlaces() != null) {
                    roundingUOM = trnDetail.getUomMaster().getDecimalPlaces();
                }
                double ruomRatioForRUOM = trnDetail.getRUomMaster().getRatio();
                double roundingRUOM = trnDetail.getRUomMaster().getDecimalPlaces();
                totalReceivingQtyInUom =totalReceivingQtyInUom + grnCalculation.getUomRatioConvertedQty(uomRatio, ruomRatioForUOM,grnDetailDTO.getReceivingQty());
                totalReceivingQtyInUom =  grnCalculation.getRoundedValue(roundingUOM,totalReceivingQtyInUom);
                totalActualReceivingQty =totalActualReceivingQty + grnCalculation.getUomRatioConvertedQty(uomRatio, ruomRatioForRUOM,grnDetailDTO.getReceivingQty());
                totalActualReceivingQty =  grnCalculation.getRoundedValue(roundingRUOM,totalActualReceivingQty);
                GrnDetail grnDetail = convertUpdateRequestToEntity(trnDetailDTO,trnDetailDTO.getDetailsForm().getId(),grnDetailDTO,dateAndTimeRequestDto);
                if (grnDetail.getId() == null) {
                    GrnLotDetail grnLotDetail = grnDetailLotMapper.convertRequestToEntity(trnDetailDTO,grnDetailDTO,dateAndTimeRequestDto);
                    grnLotDetail.setTransactionSlNo(transactionSerialNo);
                    grnDetail.setGrnLotDetail(grnLotDetail);
                    grnLotDetail.setGrnDetail(grnDetail);
                }
                id = Long.parseLong(trnDetailDTO.getDetailsForm().getId());
                grnDetail.setTransactionSlNo(transactionSerialNo);
                grnDetail.setGrnHeader(grnHeader);
                grnDetailList.add(grnDetail);
                transactionSerialNo++;
            }
            if (totalExpQtyInUom > totalReceivingQtyInUom) {
                status = "PART RECEIVED";
            }
            TrnDetail trnDetail = trnDetailService.getById(id);
            if (trnDetail.getTrnDetailAsn() != null) {
                double allCompletedActualQtyForOneDetail = grnDetailService.getAllCompletedQtyForOneDetail(trnDetail,grnHeader,true);
                double allCompletedActualRQtyForOneDetail = grnDetailService.getAllCompletedQtyForOneDetail(trnDetail,grnHeader,false);
                double newTotalReceivingQtyInUom = allCompletedActualQtyForOneDetail + totalReceivingQtyInUom;
                double newTotalActualReceivingQty = allCompletedActualRQtyForOneDetail + totalActualReceivingQty;
                double uomRounding = trnDetail.getUomMaster().getDecimalPlaces();
                double rUomRounding = trnDetail.getRUomMaster().getDecimalPlaces();
                newTotalReceivingQtyInUom = grnCalculation.getRoundedValue(uomRounding,newTotalReceivingQtyInUom);
                newTotalActualReceivingQty = grnCalculation.getRoundedValue(rUomRounding,newTotalActualReceivingQty);
                trnDetail.getTrnDetailAsn().setActualQty(newTotalReceivingQtyInUom);
                trnDetail.getTrnDetailAsn().setActualRQty(newTotalActualReceivingQty);
                Double totalAmount = 0.0;
                if (trnDetail.getTrnHeaderAsn() != null) {
                    totalAmount = trnDetail.getTrnDetailAsn().getDocumentLineTotalAmount();
                }
                Double actualAmount = (totalAmount/trnDetail.getExpQty())*newTotalReceivingQtyInUom;
                if (isUpdate){
                    actualAmount = ((totalAmount/trnDetail.getExpQty())*newTotalReceivingQtyInUom);
                }
                actualAmount = grnCalculation.getRoundedValue(5.0,actualAmount);
                trnDetail.getTrnDetailAsn().setActualLineTotalAmount(actualAmount);
            }
            trnDetailList.add(trnDetail);
            if(totalReceivingQtyInUom==0){
                return Collections.emptyList();
            }
            return grnDetailList;
        } else {
            return Collections.emptyList();
        }
    }


    private TrnDetail setTrnDetailFromGrnRequest(GrnHeaderRequestDTO.TrnDetailDTO.DetailsFormDTO detailsForm,DateAndTimeRequestDto dateAndTimeRequestDto,GrnHeaderRequestDTO grnHeaderRequestDTO) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        transactionSerialNo = trnDetailService.findMaxTransactionSerialNo(Long.parseLong(grnHeaderRequestDTO.getTrnHeaderAsn().getTransaction().getId()));
        transactionSerialNo++;
        TrnDetail trnDetail = modelMapper.map(detailsForm, TrnDetail.class);
        if (detailsForm.getSku() != null){
            trnDetail.setSkuMaster(skuMasterService.getById(Long.parseLong(detailsForm.getSku().getId())));
        }
        if (detailsForm.getHsCode() != null){
            trnDetail.setHsCodeMaster(hsCodeMasterService.getById(Long.parseLong(detailsForm.getHsCode().getId())));
        }
        if (detailsForm.getUom() != null){
            trnDetail.setUomMaster(uomMasterService.getUomById(Long.parseLong(detailsForm.getUom().getId())));
        }
        if (detailsForm.getRuom() != null){
            trnDetail.setRUomMaster(uomMasterService.getUomById(Long.parseLong(detailsForm.getRuom().getId())));
        }
        if (detailsForm.getCurrency() != null){
            trnDetail.setCurrencyMaster(currencyMasterService.getCurrencyById(Long.parseLong(detailsForm.getCurrency().getId())));
        }
        TrnHeaderAsn trnHeaderAsn = trnHeaderAsnService.findById(Long.parseLong(grnHeaderRequestDTO.getTrnHeaderAsn().getTransaction().getId()));
        trnDetail.setTrnHeaderAsn(trnHeaderAsn);
        trnDetail.setTransactionUid(trnHeaderAsn.getTransactionUid());
        trnDetail.setTransactionSlNo(transactionSerialNo);
        modelMapper.map(dateAndTimeRequestDto,trnDetail);
        return trnDetail;
    }

    private GrnDetail convertUpdateRequestToEntity( GrnHeaderRequestDTO.TrnDetailDTO trnDetailDTO,String trnDetailId,GrnHeaderRequestDTO.TrnDetailDTO.GrnDetailDTO grnDetailDTO, DateAndTimeRequestDto dateAndTimeRequestDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);              // Ensure strict matching
        GrnDetail grnDetail = new GrnDetail();
        if (grnDetailDTO.getGrnDetailId() != null && !grnDetailDTO.getGrnDetailId().isBlank() && !grnDetailDTO.getGrnDetailId().isEmpty()){
            grnDetail = grnDetailService.getById(Long.parseLong(grnDetailDTO.getGrnDetailId()));
            grnDetailLotMapper.convertUpdateRequestToEntity(grnDetail,trnDetailDTO,grnDetailDTO,dateAndTimeRequestDto);
        }
        modelMapper.map(grnDetailDTO,grnDetail);

        if (grnDetailDTO.getDimensionalDetails() != null) {
            grnDetail.setHeight(grnDetailDTO.getDimensionalDetails().getHeight());
            grnDetail.setLength(grnDetailDTO.getDimensionalDetails().getLength());
            grnDetail.setWidth(grnDetailDTO.getDimensionalDetails().getWidth());
            grnDetail.setUnit(grnDetailDTO.getDimensionalDetails().getUnit());
            grnDetail.setIsChecked(grnDetailDTO.getDimensionalDetails().getIsChecked());

        }
        modelMapper.map(dateAndTimeRequestDto,grnDetail);
        TrnDetail trnDetail = trnDetailService.getById(Long.parseLong(trnDetailId));
        //set expected qty while receiving
        GrnHeader grnHeader = new GrnHeader();
        double allCompletedActualQtyForOneDetail =  grnDetailService.getAllCompletedQtyForOneDetail(trnDetail, grnHeader,true);
        double expQtyWhileReceiving = trnDetail.getExpQty() - allCompletedActualQtyForOneDetail;
        if (expQtyWhileReceiving <0){
            expQtyWhileReceiving = 0.0;
        }
        double rounding = trnDetail.getUomMaster().getDecimalPlaces();
        expQtyWhileReceiving = grnCalculation.getRoundedValue(rounding,expQtyWhileReceiving);
        grnDetail.setExpQtyWhileReceiving(expQtyWhileReceiving);
        grnDetail.setTrnDetailMaster(trnDetail);
        if (trnDetail.getUomMaster() != null) {
            UomMaster uomMaster = uomMasterService.getUomById(trnDetail.getUomMaster().getId());
            grnDetail.setUomMaster(uomMaster);
        }
        if (grnDetailDTO.getUom() != null){
            UomMaster rUomMaster = uomMasterService.getUomById(Long.parseLong(grnDetailDTO.getUom().getId()));
            grnDetail.setUomMaster(rUomMaster);
        }
        if (grnDetailDTO.getLocation() != null){
            Location location = locationService.getLocationById(Long.parseLong(grnDetailDTO.getLocation().getId()));
            grnDetail.setLocationMaster(location);
        }
        if (grnDetailDTO.getInventoryStatus() != null) {
            InventoryStatusMaster inventoryStatusMaster = inventoryStatusMasterService.getById(Long.parseLong(grnDetailDTO.getInventoryStatus().getId()));
            grnDetail.setInventoryStatusMaster(inventoryStatusMaster);
        }
        if (grnDetailDTO.getTransportation() != null) {
            TrnHeaderTransportation trnHeaderTransportation = trnHeaderTransportationService.getById(Long.parseLong(grnDetailDTO.getTransportation().getId()));
            grnDetail.setTrnHeaderTransportation(trnHeaderTransportation);
        }
        grnDetail.setDeleted("0");
        return grnDetail;
    }

    public List<GrnDetail> getToBeDeletedGrnDetailList(GrnHeader grnHeader){
        List<GrnDetail> grnDetailListToBeDeleted = grnDetailService.findAllGrnDetailForGrnId(grnHeader.getId());
        return grnDetailListToBeDeleted
                .stream()
                .map(this::setDeletedInGrnDetail)
                .collect(Collectors.toList());
    }

    private GrnDetail setDeletedInGrnDetail(GrnDetail grnDetail) {
        grnDetail.setDeleted("1");
        return grnDetail;
    }

    public List<GrnHeaderResponseDTO.TrnDetailDTO> convertEntityListToResponseList(GrnHeader grnHeader) {
        TrnHeaderAsn trnHeaderAsn = grnHeader.getTrnHeaderAsnMaster();
        List<TrnDetail> trnDetailListSorted = trnHeaderAsn
                .getTrnDetailList()
                .stream().sorted(Comparator.comparingLong(TrnDetail::getId))
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(trnDetailListSorted)) {
            List<GrnHeaderResponseDTO.TrnDetailDTO> asnDetailList = new ArrayList<>();
            for (TrnDetail trnDetail : trnDetailListSorted){
                GrnHeaderResponseDTO.TrnDetailDTO asnDetailDTO = convertTrnDetailToGrnResponseList(trnDetail,grnHeader);
                List<TrnResponseDTO.TrnDetailDTO.LotDTO> lotDTOList = trnDetailMapper.convertTrnDetailToLotListDTO(trnDetail);
                asnDetailDTO.setMoreLotList(lotDTOList);
                asnDetailList.add(asnDetailDTO);
            }
            return asnDetailList;
        } else {
            return Collections.emptyList();
        }
    }

    private GrnHeaderResponseDTO.TrnDetailDTO convertTrnDetailToGrnResponseList(TrnDetail trnDetail, GrnHeader grnHeader) {
        GrnHeaderResponseDTO.TrnDetailDTO trnDetailDTO = new GrnHeaderResponseDTO.TrnDetailDTO();
        TrnResponseDTO.TrnDetailDTO.DetailsFormDTO detailsFormDTO = trnDetailMapper.convertTrnDetailToDetailsFormDTO(grnHeader,trnDetail,"Yes");
        List<GrnDetail> filteredGrnDetailList = grnHeader.getGrnDetailList().stream()
                .filter(detail -> detail.getTrnDetailMaster().getId().equals(trnDetail.getId()))
                .filter(detail -> detail.getDeleted() != null && detail.getDeleted().equals("0"))
                .collect(Collectors.toList());List<GrnHeaderResponseDTO.TrnDetailDTO.GrnDetailDTO> grnDetailDTOList = new ArrayList<>();
        Double totalReceiveQty = 0.0;
        Double totalDamgedQty = 0.0;
        double expQtyWhileReceiving = 0.0;
        if (!CollectionUtils.isEmpty(filteredGrnDetailList)) {
            for (GrnDetail grnDetail : filteredGrnDetailList) {
                GrnHeaderResponseDTO.TrnDetailDTO.GrnDetailDTO grnDetailDTO = convertGrnDetailEntityToResponse(grnDetail);
                BooleanBuilder predicate = new BooleanBuilder();
                QConfigurationMaster qConfigurationMaster = QConfigurationMaster.configurationMaster;
                predicate.and(qConfigurationMaster.configurationFlag.equalsIgnoreCase("putaway_cancel_rights"));
                List<ConfigurationMaster> isCancelable = configurationMasterService.findAll(predicate, Pageable.unpaged()).getContent();
                if(!isCancelable.isEmpty()){
                    grnDetailDTO.setIsEditable("Yes");
                } else {
                    grnDetailDTO.setIsEditable("No");
                }


                GrnLotDetail grnLotDetail = grnDetail.getGrnLotDetail();
                List<TrnResponseDTO.TrnDetailDTO.LotDTO> lotDTOList = convertTrnDetailToLotListDTO(grnLotDetail, trnDetail);
                grnDetailDTO.setMoreLotList(lotDTOList);
                modelMapper.map(grnLotDetail,grnDetailDTO);
                if (grnLotDetail.getHsCodeMaster() != null){
                    GrnHeaderResponseDTO.TrnDetailDTO.DetailsFormDTO.HsCodeDTO hsCodeDTO = modelMapper.map(grnLotDetail.getHsCodeMaster(),GrnHeaderResponseDTO.TrnDetailDTO.DetailsFormDTO.HsCodeDTO.class);
                    grnDetailDTO.setHsCode(hsCodeDTO);
                }
                double uomRatio = grnDetail.getUomMaster().getRatio();
                if(grnDetail.getExpQtyWhileReceiving() != null) {
                    expQtyWhileReceiving = grnDetail.getExpQtyWhileReceiving();
                }

                double rUomRatio = trnDetail.getUomMaster().getRatio();
                double rounding = trnDetail.getUomMaster().getDecimalPlaces();
                //if damage qty this block will work
                if ( grnDetailDTO.getInventoryStatus() != null && grnDetailDTO.getInventoryStatus().getName() != null && grnDetailDTO.getInventoryStatus().getCode().equalsIgnoreCase("Damaged")) {
                    totalDamgedQty = totalDamgedQty + grnCalculation.getUomRatioConvertedQty(uomRatio,rUomRatio,grnDetailDTO.getReceivingQty());
                    totalDamgedQty =  grnCalculation.getRoundedValue(rounding,totalDamgedQty);
                }
                totalReceiveQty = totalReceiveQty + grnCalculation.getUomRatioConvertedQty(uomRatio,rUomRatio,grnDetailDTO.getReceivingQty());
                totalReceiveQty =  grnCalculation.getRoundedValue(rounding,totalReceiveQty);
                grnDetailDTOList.add(grnDetailDTO);
            }
        }
        detailsFormDTO.setDamagedQty(totalDamgedQty);
        detailsFormDTO.setReceiveqty(totalReceiveQty);
        detailsFormDTO.setExpQtyWhileReceiving(expQtyWhileReceiving);
        trnDetailDTO.setDetailsForm(detailsFormDTO);
        trnDetailDTO.setGrnDetailDTOList(grnDetailDTOList);
        boolean isQuantityZero = grnDetailService.checkIfDetailIdForDeletable(detailsFormDTO.getId());

        if (isQuantityZero) {
            // Assuming grnDetailList is the list of grndetail objects
            double totalRecQty = filteredGrnDetailList.stream()
                    .mapToDouble(detail -> detail.getReceivingQty())
                    .sum();

            if (totalRecQty != 0.0) {
                detailsFormDTO.setIsQuantityZero("No");
            } else {
                detailsFormDTO.setIsQuantityZero("Yes");
            }
        }

        //set excess qty and shortage qty
        //if damage qty is there
        if (totalDamgedQty != 0.0) {
            if (detailsFormDTO.getExpQtyWhileReceiving() < detailsFormDTO.getDamagedQty()) {
                double excess = (detailsFormDTO.getQtyAlreadyReceived() - detailsFormDTO.getDamagedQty()) - detailsFormDTO.getExpQtyWhileReceiving();
                detailsFormDTO.setExcess(excess>0 ? excess : 0.0);
                double shortage = detailsFormDTO.getExpQtyWhileReceiving() - (detailsFormDTO.getQtyAlreadyReceived() - detailsFormDTO.getDamagedQty());
                detailsFormDTO.setShortage(shortage>0 ? shortage : 0.0);
            } else if (detailsFormDTO.getExpQtyWhileReceiving() > detailsFormDTO.getDamagedQty()) {
                double shortage = detailsFormDTO.getExpQtyWhileReceiving() - (detailsFormDTO.getQtyAlreadyReceived() - detailsFormDTO.getDamagedQty());
                detailsFormDTO.setShortage(shortage>0 ? shortage : 0.0);
                double excess = (detailsFormDTO.getQtyAlreadyReceived() - detailsFormDTO.getDamagedQty()) - detailsFormDTO.getExpQtyWhileReceiving();
                detailsFormDTO.setExcess(excess>0 ? excess : 0.0);
            }
            // if damage qty isn't there
        } else  {
            if (detailsFormDTO.getExpQtyWhileReceiving() < detailsFormDTO.getReceiveqty()) {
                double excess = (detailsFormDTO.getQtyAlreadyReceived() - detailsFormDTO.getDamagedQty()) - detailsFormDTO.getExpQtyWhileReceiving();
//                double excess = detailsFormDTO.getReceiveqty() - detailsFormDTO.getExpQtyWhileReceiving();
                detailsFormDTO.setExcess(excess>0 ? excess : 0.0);
//                detailsFormDTO.setShortage(0.0);
                double shortage = detailsFormDTO.getExpQtyWhileReceiving() - (detailsFormDTO.getQtyAlreadyReceived() - detailsFormDTO.getDamagedQty());
                detailsFormDTO.setShortage(shortage>0 ? shortage : 0.0);
            } else if (detailsFormDTO.getExpQtyWhileReceiving() > detailsFormDTO.getReceiveqty()) {
                double shortage = detailsFormDTO.getExpQtyWhileReceiving() - (detailsFormDTO.getQtyAlreadyReceived() - detailsFormDTO.getDamagedQty());
//                double shortage = detailsFormDTO.getExpQtyWhileReceiving() - detailsFormDTO.getReceiveqty();
                detailsFormDTO.setShortage(shortage>0 ? shortage : 0.0);
//                detailsFormDTO.setExcess(0.0);
                double excess = (detailsFormDTO.getQtyAlreadyReceived() - detailsFormDTO.getDamagedQty()) - detailsFormDTO.getExpQtyWhileReceiving();
                detailsFormDTO.setExcess(excess>0 ? excess : 0.0);
            }
        }
        return trnDetailDTO;
    }

    public List<TrnResponseDTO.TrnDetailDTO.LotDTO> convertTrnDetailToLotListDTO(GrnLotDetail grnLotDetail,TrnDetail trnDetail) {
        List<TrnResponseDTO.TrnDetailDTO.LotDTO> lotDTOList = new ArrayList<>();
        if (grnLotDetail != null) {
            if (!CollectionUtils.isEmpty(trnDetail.getSkuMaster().getSkuLotDetailsList())) {
                List<SkuResponseDTO.LotDTO> lotDTOList1 = skuMapper.convertLotDetailsListToDTOList(trnDetail.getSkuMaster().getSkuLotDetailsList());
                lotDTOList1.addAll(skuMapper.convertStaticLotToDTOList(trnDetail.getSkuMaster()));
                lotDTOList = mapToLotDTO1To5(lotDTOList1, grnLotDetail);
                lotDTOList.addAll(mapToLotDTO6To10(lotDTOList1, grnLotDetail));
            } else {
                return Collections.emptyList();
            }
        }
        return lotDTOList;
    }

    private List<TrnResponseDTO.TrnDetailDTO.LotDTO> mapToLotDTO1To5(List<SkuResponseDTO.LotDTO> lotDTOList1,GrnLotDetail grnLotDetail) {
        List<TrnResponseDTO.TrnDetailDTO.LotDTO> lotDTOList = new ArrayList<>();
        for (SkuResponseDTO.LotDTO lotDTO : lotDTOList1) {
            TrnResponseDTO.TrnDetailDTO.LotDTO lotDTO1 = modelMapper.map(lotDTO, TrnResponseDTO.TrnDetailDTO.LotDTO.class);
            if (lotDTO.getName().equalsIgnoreCase("lot01")) {
                lotDTO1.setValue(grnLotDetail.getLot01());
                lotDTOList.add(lotDTO1);
            }
            if (lotDTO.getName().equalsIgnoreCase("lot02")) {
                lotDTO1.setValue(grnLotDetail.getLot02());
                lotDTOList.add(lotDTO1);
            }
            if (lotDTO.getName().equalsIgnoreCase("lot03")) {
                lotDTO1.setValue(grnLotDetail.getLot03());
                lotDTOList.add(lotDTO1);
            }
            if (lotDTO.getName().equalsIgnoreCase("lot04")) {
                lotDTO1.setValue(grnLotDetail.getLot04());
                lotDTOList.add(lotDTO1);
            }
            if (lotDTO.getName().equalsIgnoreCase("lot05")) {
                lotDTO1.setValue(grnLotDetail.getLot05());
                lotDTOList.add(lotDTO1);
            }
        }
        return lotDTOList;
    }

    private Collection<? extends TrnResponseDTO.TrnDetailDTO.LotDTO> mapToLotDTO6To10(List<SkuResponseDTO.LotDTO> lotDTOList1, GrnLotDetail grnLotDetail) {
        List<TrnResponseDTO.TrnDetailDTO.LotDTO> lotDTOList = new ArrayList<>();
        for (SkuResponseDTO.LotDTO lotDTO : lotDTOList1) {
            TrnResponseDTO.TrnDetailDTO.LotDTO lotDTO1 = modelMapper.map(lotDTO, TrnResponseDTO.TrnDetailDTO.LotDTO.class);
            if (lotDTO.getName().equalsIgnoreCase("lot06")) {
                lotDTO1.setValue(grnLotDetail.getLot06());
                lotDTOList.add(lotDTO1);
            }
            if (lotDTO.getName().equalsIgnoreCase("lot07")) {
                lotDTO1.setValue(grnLotDetail.getLot07());
                lotDTOList.add(lotDTO1);
            }
            if (lotDTO.getName().equalsIgnoreCase("lot08")) {
                lotDTO1.setValue(grnLotDetail.getLot08());
                lotDTOList.add(lotDTO1);
            }
            if (lotDTO.getName().equalsIgnoreCase("lot09")) {
                lotDTO1.setValue(grnLotDetail.getLot09());
                lotDTOList.add(lotDTO1);
            }
            if (lotDTO.getName().equalsIgnoreCase("lot10")) {
                lotDTO1.setValue(grnLotDetail.getLot10());
                lotDTOList.add(lotDTO1);
            }
        }
        return lotDTOList;
    }

    private GrnHeaderResponseDTO.TrnDetailDTO.GrnDetailDTO convertGrnDetailEntityToResponse(GrnDetail grnDetail) {
        GrnHeaderResponseDTO.TrnDetailDTO.GrnDetailDTO grnDetailDTO = modelMapper.map(grnDetail,GrnHeaderResponseDTO.TrnDetailDTO.GrnDetailDTO.class);
        GrnHeaderResponseDTO.TrnDetailDTO.DetailsFormDTO.UomDTO uomDTO = new GrnHeaderResponseDTO.TrnDetailDTO.DetailsFormDTO.UomDTO();
        GrnHeaderResponseDTO.TrnDetailDTO.GrnDetailDTO.DimensionalDTO dimensionalDTO = new GrnHeaderResponseDTO.TrnDetailDTO.GrnDetailDTO.DimensionalDTO();
        dimensionalDTO.setHeight(grnDetail.getHeight());
        dimensionalDTO.setWidth(grnDetail.getWidth());
        dimensionalDTO.setUnit(grnDetail.getUnit());
        dimensionalDTO.setLength(grnDetail.getLength());
        dimensionalDTO.setIsChecked(grnDetail.getIsChecked());
        grnDetailDTO.setDimensionalDetails(dimensionalDTO);

        if (grnDetail.getUomMaster() != null){
            modelMapper.map(grnDetail.getUomMaster(),uomDTO);
            grnDetailDTO.setUom(uomDTO);
        }
        GrnHeaderResponseDTO.TrnDetailDTO.GrnDetailDTO.LocationDTO locationDTO = new GrnHeaderResponseDTO.TrnDetailDTO.GrnDetailDTO.LocationDTO();
        if (grnDetail.getLocationMaster() != null){
            modelMapper.map(grnDetail.getLocationMaster(),locationDTO);
            grnDetailDTO.setLocation(locationDTO);
        }
        GrnHeaderResponseDTO.TrnDetailDTO.GrnDetailDTO.InventoryStatusDTO inventoryStatusDTO = new GrnHeaderResponseDTO.TrnDetailDTO.GrnDetailDTO.InventoryStatusDTO();
        if (grnDetail.getInventoryStatusMaster() != null) {
            modelMapper.map(grnDetail.getInventoryStatusMaster(),inventoryStatusDTO);
            grnDetailDTO.setInventoryStatus(inventoryStatusDTO);
        }
        GrnHeaderResponseDTO.TrnDetailDTO.GrnDetailDTO.TrnHeaderTransportationDTO transportationDTO = new GrnHeaderResponseDTO.TrnDetailDTO.GrnDetailDTO.TrnHeaderTransportationDTO();
        if (grnDetail.getTrnHeaderTransportation() != null) {
            modelMapper.map(grnDetail.getTrnHeaderTransportation(),transportationDTO);
            grnDetailDTO.setTransportation(transportationDTO);
        }
        grnDetailDTO.setGrnDetailId(grnDetail.getId());
        return grnDetailDTO;
    }

    public List<GrnHeaderResponseDTO.TrnDetailDTO> convertEntityListToResponseListWithoutGrn(TrnHeaderAsn trnHeaderAsn) {
        if (!CollectionUtils.isEmpty(trnHeaderAsn.getTrnDetailList())) {
            List<GrnHeaderResponseDTO.TrnDetailDTO> asnDetailList = new ArrayList<>();
            List<TrnDetail> trnDetailListSorted = trnHeaderAsn.getTrnDetailList().stream()
                    .sorted(Comparator.comparingLong(TrnDetail::getId))
                    .collect(Collectors.toList());
            for (TrnDetail trnDetail : trnDetailListSorted){
                GrnHeaderResponseDTO.TrnDetailDTO asnDetailDTO = convertTrnDetailToGrnResponseListWithoutGrn(trnDetail);
                asnDetailList.add(asnDetailDTO);
            }
            return asnDetailList;
        } else {
            return Collections.emptyList();
        }
    }

    private GrnHeaderResponseDTO.TrnDetailDTO convertTrnDetailToGrnResponseListWithoutGrn(TrnDetail trnDetail) {
        GrnHeaderResponseDTO.TrnDetailDTO trnDetailDTO = new GrnHeaderResponseDTO.TrnDetailDTO();
        TrnResponseDTO.TrnDetailDTO.DetailsFormDTO detailsFormDTO = trnDetailMapper.convertTrnDetailToDetailsFormDTO(null,trnDetail,"Yes");
        trnDetailDTO.setDetailsForm(detailsFormDTO);
        List<TrnResponseDTO.TrnDetailDTO.LotDTO> lotDTOList = trnDetailMapper.convertTrnDetailToLotListDTO(trnDetail);
        trnDetailDTO.setMoreLotList(lotDTOList);
        return trnDetailDTO;
    }

    public TrnHeaderAsn deductQtyAndAmount(GrnHeader grnHeader) {
        TrnHeaderAsn trnHeaderAsn = grnHeader.getTrnHeaderAsnMaster();
        List<GrnDetail> grnDetailList = grnHeader.getGrnDetailList();
        List<TrnDetail> trnDetailOldList = trnHeaderAsn.getTrnDetailList();
        List<TrnDetail> trnDetailNewList = new ArrayList<>();
        for (TrnDetail trnDetail : trnDetailOldList){
            Long id = trnDetail.getId();
            List<GrnDetail> grnDetailFilteredList = grnDetailList.stream()
                    .filter(grn -> grn.getTrnDetailMaster().getId().equals(id))
                    .collect(Collectors.toList());
            double actualQty = trnDetail.getTrnDetailAsn().getActualQty() - getActualQty(grnDetailFilteredList,trnDetail);
            double uomRounding = trnDetail.getUomMaster().getDecimalPlaces();
            actualQty = grnCalculation.getRoundedValue(uomRounding,actualQty);
            double actualRQty = trnDetail.getTrnDetailAsn().getActualRQty() - getActualRQty(grnDetailFilteredList,trnDetail);
            double rUomRounding = trnDetail.getRUomMaster().getDecimalPlaces();
            actualRQty = grnCalculation.getRoundedValue(rUomRounding,actualRQty);
            double actualAmount = trnDetail.getTrnDetailAsn().getActualLineTotalAmount() - getActualAmount(trnDetail,trnDetail.getTrnDetailAsn().getActualQty());
            actualAmount = grnCalculation.getRoundedValue(5.0,actualAmount);
            trnDetail.getTrnDetailAsn().setActualQty(actualQty);
            trnDetail.getTrnDetailAsn().setActualRQty(actualRQty);
            trnDetail.getTrnDetailAsn().setActualLineTotalAmount(actualAmount);
            trnDetailNewList.add(trnDetail);
        }
        trnHeaderAsn.setTrnDetailList(trnDetailNewList);
        return trnHeaderAsn;
    }

    private double getActualQty(List<GrnDetail> grnDetailFilteredList,TrnDetail trnDetail) {
        double actualQty = 0.0;
        for (GrnDetail grnDetail : grnDetailFilteredList){
            double uomRatio = grnDetail.getUomMaster().getRatio();
            double ruomRatio = trnDetail.getUomMaster().getRatio();
            double roundingUOM = trnDetail.getUomMaster().getDecimalPlaces();
            actualQty = actualQty + grnCalculation.getUomRatioConvertedQty(uomRatio, ruomRatio,grnDetail.getReceivingQty());
            actualQty = grnCalculation.getRoundedValue(roundingUOM,actualQty);
        }
        return actualQty;
    }

    private double getActualRQty(List<GrnDetail> grnDetailFilteredList,TrnDetail trnDetail) {
        double actualRQty = 0.0;
        for (GrnDetail grnDetail : grnDetailFilteredList){
            double uomRatio = grnDetail.getUomMaster().getRatio();
            double ruomRatio = trnDetail.getRUomMaster().getRatio();
            double roundingRUOM = trnDetail.getRUomMaster().getDecimalPlaces();
            actualRQty = actualRQty + grnCalculation.getUomRatioConvertedQty(uomRatio, ruomRatio,grnDetail.getReceivingQty());
            actualRQty = grnCalculation.getRoundedValue(roundingRUOM,actualRQty);
        }
        return actualRQty;
    }

    private double getActualAmount(TrnDetail trnDetail,Double actualQty) {
        double documentLineTotal = trnDetail.getTrnDetailAsn().getDocumentLineTotalAmount();
        double actualAmountTotal =  ((documentLineTotal/trnDetail.getExpQty())*actualQty);
        actualAmountTotal = grnCalculation.getRoundedValue(5,actualAmountTotal);
        return actualAmountTotal;
    }

}
