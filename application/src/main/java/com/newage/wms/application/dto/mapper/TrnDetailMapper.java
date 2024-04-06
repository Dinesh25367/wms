package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.GrnHeaderRequestDTO;
import com.newage.wms.application.dto.requestdto.TrnRequestDTO;
import com.newage.wms.application.dto.requestdto.TrnSoRequestDTO;
import com.newage.wms.application.dto.responsedto.SkuPackDetailResponseDTO;
import com.newage.wms.application.dto.responsedto.SkuResponseDTO;
import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.entity.*;
import com.newage.wms.service.*;
import com.newage.wms.service.impl.GrnCalculation;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Component
public class TrnDetailMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private CurrencyMasterService currencyMasterService;

    @Autowired
    private SkuMasterService skuMasterService;

    @Autowired
    private HsCodeMasterService hsCodeMasterService;

    @Autowired
    private UomMasterService uomMasterService;

    @Autowired
    private AuthUserProfileService authUserProfileService;

    @Autowired
    private QcStatusMasterService qcStatusMasterService;

    @Autowired
    private TrnDetailService trnDetailService;

    @Autowired
    private TrnDetailSoService trnDetailSoService;

    @Autowired
    private TrnDetailLotService trnDetailLotService;

    @Autowired
    private TrnDetailQcService trnDetailQcService;

    @Autowired
    private TrnDetailAsnCustomsService trnDetailAsnCustomsService;

    @Autowired
    private GrnDetailMapper grnDetailMapper;

    @Autowired
    private GrnCalculation grnCalculation;

    @Autowired
    private GrnDetailService grnDetailService;

    private final String lot01 = "lot01";

    private final String lot02 = "lot02";

    private final String lot03 = "lot03";

    private final String lot04 = "lot04";

    private final String lot05 = "lot05";

    private final String lot06 = "lot06";

    private final String lot07 = "lot07";

    private final String lot08 = "lot08";

    private final String lot09 = "lot09";

    private final String lot10 = "lot10";


    @Autowired
    private SkuPackDetailMapper skuPackDetailMapper;

    private Integer countUpdate;

    public void convertUpdateRequestListToEntityListAndDeleteNonExistingIds(TrnRequestDTO trnRequestDTO,TrnHeaderAsn trnHeaderAsnToBeUpdated, DateAndTimeRequestDto dateAndTimeRequestDTO){
        List<TrnDetail> trnDetailListToBeDeleted = new ArrayList<>();
        if (trnHeaderAsnToBeUpdated.getTrnDetailList() != null && !trnHeaderAsnToBeUpdated.getTrnDetailList().isEmpty()) {
            trnDetailListToBeDeleted = trnHeaderAsnToBeUpdated.getTrnDetailList();
        }
        if (!CollectionUtils.isEmpty(trnRequestDTO.getAsnDetailsList())) {
            if (!CollectionUtils.isEmpty(trnHeaderAsnToBeUpdated.getTrnDetailList())) {
                convertUpdateRequestListToEntityList(trnRequestDTO.getAsnDetailsList(), trnHeaderAsnToBeUpdated, dateAndTimeRequestDTO);
                identifyAndDeleteNonExistingIds(trnRequestDTO,trnDetailListToBeDeleted);
            }else {
                trnHeaderAsnToBeUpdated.setTrnDetailList(setTrnDetailListInSave(trnRequestDTO,trnHeaderAsnToBeUpdated,dateAndTimeRequestDTO));
            }
        }else
        {
            trnHeaderAsnToBeUpdated.setTrnDetailList(null);
            if (trnDetailListToBeDeleted != null) {
                trnDetailService.deleteAllInIterable(trnDetailListToBeDeleted);
            }
        }
    }

    private List<TrnDetail>  setTrnDetailListInSave(TrnRequestDTO trnRequestDTO,TrnHeaderAsn trnHeaderAsnToBeUpdated,DateAndTimeRequestDto dateAndTimeRequestDTO){
        List<TrnDetail> trnDetailList = new ArrayList<>();
        Integer count = 1;
        for (TrnRequestDTO.TrnDetailDTO trnDetailDTO :trnRequestDTO.getAsnDetailsList() ){
            TrnDetail trnDetail;
            trnDetail = convertTrnDetailDTOToTrnDetail(trnDetailDTO,dateAndTimeRequestDTO);
            trnDetail.setTransactionUid(trnHeaderAsnToBeUpdated.getTransactionUid());
            trnDetail.setTransactionSlNo(count);
            trnDetail.setTrnHeaderAsn(trnHeaderAsnToBeUpdated);
            TrnDetailAsn trnDetailAsn = convertTrnDetailDTOToTrnDetailAsn(trnDetailDTO,dateAndTimeRequestDTO);
            trnDetailAsn.setTransactionSlNo(count);
            trnDetail.setTrnDetailAsn(trnDetailAsn);
            trnDetailAsn.setTrnDetail(trnDetail);
            TrnDetailLot trnDetailLot = convertTrnDetailDTOToTrnDetailLot(trnDetailDTO,dateAndTimeRequestDTO);
            if (trnDetailLot != null) {
                trnDetailLot.setTransactionSlNo(count);
                trnDetail.setTrnDetailLot(trnDetailLot);
                trnDetailLot.setTrnDetail(trnDetail);
            }
            TrnDetailQc trnDetailQc = convertTrnDetailDTOToTrnDetailQc(trnDetailDTO,dateAndTimeRequestDTO);
            if (trnDetailQc != null) {
                trnDetailQc.setTransactionSlNo(count);
                trnDetail.setTrnDetailQc(trnDetailQc);
                trnDetailQc.setTrnDetail(trnDetail);
            }
            TrnDetailAsnCustoms trnDetailAsnCustoms = convertTrnDetailDTOToTrnDetailAsnCustoms(trnDetailDTO,dateAndTimeRequestDTO);
            if (trnDetailAsnCustoms != null) {
                trnDetailAsnCustoms.setTransactionSlNo(count);
                trnDetail.setTrnDetailAsnCustoms(trnDetailAsnCustoms);
                trnDetailAsnCustoms.setTrnDetail(trnDetail);
            }
            count++;
            trnDetailList.add(trnDetail);
        }
        return trnDetailList;
    }


    public void convertUpdateRequestListToEntityList(List<TrnRequestDTO.TrnDetailDTO> asnDetailsList, TrnHeaderAsn trnHeaderAsnToBeUpdated, DateAndTimeRequestDto dateAndTimeRequestDto) {
        countUpdate = 1;
        List<TrnDetail> trnDetailList = asnDetailsList.stream()
                .map(trnDetailDTO -> convertUpdateRequestToEntity(trnDetailDTO,trnHeaderAsnToBeUpdated, dateAndTimeRequestDto))
                .collect(Collectors.toList());
        trnHeaderAsnToBeUpdated.setTrnDetailList(trnDetailList);
    }

    /*
     * Method to convert TrnDetailDTO to TrnDetail
     * @Return TrnHeaderAsn
     */
    public TrnDetail convertTrnDetailDTOToTrnDetail(TrnRequestDTO.TrnDetailDTO trnDetailRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto) {
        log.info("ENTRY-EXIT - TrnDetailDTO to TrnDetail mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        TrnDetail trnDetail = modelMapper.map(trnDetailRequestDTO.getDetailsForm(), TrnDetail.class);
        if (trnDetailRequestDTO.getDetailsForm().getSku() != null){
            trnDetail.setSkuMaster(skuMasterService.getById(Long.parseLong(trnDetailRequestDTO.getDetailsForm().getSku().getId())));
        }
        if (trnDetailRequestDTO.getDetailsForm().getHsCode() != null){
            trnDetail.setHsCodeMaster(hsCodeMasterService.getById(Long.parseLong(trnDetailRequestDTO.getDetailsForm().getHsCode().getId())));
        }
        if (trnDetailRequestDTO.getDetailsForm().getUom() != null){
            trnDetail.setUomMaster(uomMasterService.getUomById(Long.parseLong(trnDetailRequestDTO.getDetailsForm().getUom().getId())));
        }
        if (trnDetailRequestDTO.getDetailsForm().getRuom() != null){
            trnDetail.setRUomMaster(uomMasterService.getUomById(Long.parseLong(trnDetailRequestDTO.getDetailsForm().getRuom().getId())));
        }
        if (trnDetailRequestDTO.getDetailsForm().getCurrency() != null){
            trnDetail.setCurrencyMaster(currencyMasterService.getCurrencyById(Long.parseLong(trnDetailRequestDTO.getDetailsForm().getCurrency().getId())));
        }
        modelMapper.map(dateAndTimeRequestDto,trnDetail);
        return trnDetail;
    }


    /*
     * Method to convert TrnDetailDTO to TrnDetailAsn
     * @Return TrnDetailAsn
     */
    public TrnDetailAsn convertTrnDetailDTOToTrnDetailAsn(TrnRequestDTO.TrnDetailDTO trnDetailRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDTO) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        TrnDetailAsn trnDetailAsn = modelMapper.map(trnDetailRequestDTO.getDetailsForm(), TrnDetailAsn.class);
        double volume = grnCalculation.getRoundedValue(5,trnDetailAsn.getVolume());
        double grossWeight = grnCalculation.getRoundedValue(5,trnDetailAsn.getGrossWeight());
        double netWeight = grnCalculation.getRoundedValue(5,trnDetailAsn.getNetWeight());
        double totalAmount = grnCalculation.getRoundedValue(5,trnDetailAsn.getDocumentLineTotalAmount());
        double totalActualAmount = grnCalculation.getRoundedValue(5,trnDetailAsn.getActualLineTotalAmount());
        trnDetailAsn.setVolume(volume);
        trnDetailAsn.setGrossWeight(grossWeight);
        trnDetailAsn.setNetWeight(netWeight);
        trnDetailAsn.setDocumentLineTotalAmount(totalAmount);
        trnDetailAsn.setActualLineTotalAmount(totalActualAmount);
        modelMapper.map(dateAndTimeRequestDTO,trnDetailAsn);
        return trnDetailAsn;
    }

    public TrnDetailAsn convertTrnDetailDTOToTrnDetailAsnFromGrn(GrnHeaderRequestDTO.TrnDetailDTO trnDetailRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDTO) {
        log.info("ENTRY-EXIT - TrnDetailDTO to TrnDetailAsn mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        TrnDetailAsn trnDetailAsn = modelMapper.map(trnDetailRequestDTO.getDetailsForm(), TrnDetailAsn.class);
        modelMapper.map(dateAndTimeRequestDTO,trnDetailAsn);
        return trnDetailAsn;
    }

    /*
     * Method to convert TrnDetailDTO to TrnDetailLot
     * @Return TrnHeaderAsn
     */
    public TrnDetailLot convertTrnDetailDTOToTrnDetailLot(TrnRequestDTO.TrnDetailDTO trnDetailRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);              // Ensure strict matching
        TrnDetailLot trnDetailLot = modelMapper.map(dateAndTimeRequestDto,TrnDetailLot.class);
        modelMapper.map(trnDetailRequestDTO.getDetailsForm(), trnDetailLot);
        if (!CollectionUtils.isEmpty(trnDetailRequestDTO.getMoreLotList())){
            int i =1;
            for (TrnSoRequestDTO.TrnDetailDTO.LotDTO lotDTO : trnDetailRequestDTO.getMoreLotList()){
                if (lotDTO.getName() == null){
                    lotDTO.setName("lot0"+i);
                }
                if (lotDTO.getName() == null && i == 10){
                    lotDTO.setName("lot10");
                }
                setSoLotEntity(trnDetailLot,lotDTO);
                i++;
            }
        }
        return trnDetailLot;
    }

    public void convertTrnDetailDTOToTrnDetailLot(TrnRequestDTO.TrnDetailDTO trnDetailRequestDTO,TrnDetailLot trnDetailLot){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);              // Ensure strict matching
        if (!CollectionUtils.isEmpty(trnDetailRequestDTO.getMoreLotList())){
            int i =1;
            for (TrnSoRequestDTO.TrnDetailDTO.LotDTO lotDTO : trnDetailRequestDTO.getMoreLotList()){
                if (lotDTO.getName() == null){
                    lotDTO.setName("lot0"+i);
                }
                if (lotDTO.getName() == null && i == 10){
                    lotDTO.setName("lot10");
                }
                setSoLotEntity(trnDetailLot,lotDTO);
                i++;
            }
        }
    }

    private TrnDetailLot setSoLotEntity(TrnDetailLot trnDetailLot,TrnSoRequestDTO.TrnDetailDTO.LotDTO lotDTO) {
        if (lotDTO.getName().equalsIgnoreCase(lot01)){
            trnDetailLot.setLot01(convertLotDTOToLotString(lotDTO));
        }
        if (lotDTO.getName().equalsIgnoreCase(lot02)){
            trnDetailLot.setLot02(convertLotDTOToLotString(lotDTO));
        }
        if (lotDTO.getName().equalsIgnoreCase(lot03)){
            trnDetailLot.setLot03(convertLotDTOToLotString(lotDTO));
        }
        if (lotDTO.getName().equalsIgnoreCase(lot04)){
            trnDetailLot.setLot04(convertLotDTOToLotString(lotDTO));
        }
        if (lotDTO.getName().equalsIgnoreCase(lot05)){
            trnDetailLot.setLot05(convertLotDTOToLotString(lotDTO));
        }
        if (lotDTO.getName().equalsIgnoreCase(lot06)){
            trnDetailLot.setLot06(convertLotDTOToLotString(lotDTO));
        }
        if (lotDTO.getName().equalsIgnoreCase(lot07)){
            trnDetailLot.setLot07(convertLotDTOToLotString(lotDTO));
        }
        if (lotDTO.getName().equalsIgnoreCase(lot08)){
            trnDetailLot.setLot08(convertLotDTOToLotString(lotDTO));
        }
        if (lotDTO.getName().equalsIgnoreCase(lot09)){
            trnDetailLot.setLot09(convertLotDTOToLotString(lotDTO));
        }
        if (lotDTO.getName().equalsIgnoreCase(lot10)){
            trnDetailLot.setLot10(convertLotDTOToLotString(lotDTO));
        }
        return trnDetailLot;
    }

    private String convertLotDTOToLotString(TrnSoRequestDTO.TrnDetailDTO.LotDTO lotDTO) {
        return lotDTO.getValue();
    }

    /*
     * Method to convert TrnDetailDTO to TrnDetailQc
     * @Return TrnDetailQc
     */
    public TrnDetailQc convertTrnDetailDTOToTrnDetailQc(TrnRequestDTO.TrnDetailDTO trnDetailRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDTO) {
        log.info("ENTRY-EXIT - TrnDetailDTO to TrnDetailQc mapper");
        if (trnDetailRequestDTO.getMoreQCForm() != null) {
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
            TrnDetailQc trnDetailQc = modelMapper.map(trnDetailRequestDTO.getMoreQCForm(), TrnDetailQc.class);
            modelMapper.map(dateAndTimeRequestDTO, trnDetailQc);
            if (trnDetailRequestDTO.getMoreQCForm().getInspectedBy() != null) {
                trnDetailQc.setInspectedByMaster(authUserProfileService.getById(Long.parseLong(trnDetailRequestDTO.getMoreQCForm().getInspectedBy().getId())));
            }
            if (trnDetailRequestDTO.getMoreQCForm().getInspectionStatus() != null) {
                trnDetailQc.setInspectionStatusMaster(qcStatusMasterService.getById(Long.parseLong(trnDetailRequestDTO.getMoreQCForm().getInspectionStatus().getId())));
            }
            return trnDetailQc;
        }else {return null;}
    }

    /*
     * Method to convert TrnDetailDTO to TrnDetailAsnCustoms
     * @Return TrnDetailQc
     */
    public TrnDetailAsnCustoms convertTrnDetailDTOToTrnDetailAsnCustoms(TrnRequestDTO.TrnDetailDTO trnDetailRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDTO) {
        log.info("ENTRY-EXIT - TrnDetailDTO to TrnDetailAsnCustoms mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        if (trnDetailRequestDTO.getMoreFtaForm() != null) {
            TrnDetailAsnCustoms trnDetailAsnCustoms = modelMapper.map(trnDetailRequestDTO.getMoreFtaForm(), TrnDetailAsnCustoms.class);
            modelMapper.map(dateAndTimeRequestDTO, trnDetailAsnCustoms);
            return trnDetailAsnCustoms;
        }else {return null;}
    }

    public TrnDetail convertUpdateRequestToEntity(TrnRequestDTO.TrnDetailDTO trnDetailDTO, TrnHeaderAsn trnHeaderAsnToBeUpdated, DateAndTimeRequestDto dateAndTimeRequestDTO) {
        TrnDetail trnDetail;
        if (trnDetailDTO.getDetailsForm().getId() != null && !trnDetailDTO.getDetailsForm().getId().isBlank() && !trnDetailDTO.getDetailsForm().getId().isEmpty()){
            trnDetail = trnDetailService.getById(Long.parseLong(trnDetailDTO.getDetailsForm().getId()));
            trnDetail  = convertTrnDetailDTOToTrnDetailUpdate(trnDetailDTO,trnDetail,dateAndTimeRequestDTO);
            trnDetail.setTransactionSlNo(countUpdate);
            trnDetail.setTrnHeaderAsn(trnHeaderAsnToBeUpdated);
            TrnDetailAsn trnDetailAsn = convertTrnDetailDTOToTrnDetailAsnUpdate(trnDetailDTO,trnDetail,dateAndTimeRequestDTO);
            trnDetailAsn.setTransactionSlNo(countUpdate);
            trnDetail.setTrnDetailAsn(trnDetailAsn);
            trnDetailAsn.setTrnDetail(trnDetail);
            setTrnDetailLotInUpdate(trnDetail,trnDetailDTO,dateAndTimeRequestDTO);
            setTrnDetailQcInUpdate(trnDetail,trnDetailDTO,dateAndTimeRequestDTO);
            setTrnDetailAsnCustoms(trnDetail,trnDetailDTO,dateAndTimeRequestDTO);
        }else {
            trnDetail = convertTrnDetailDTOToTrnDetail(trnDetailDTO,dateAndTimeRequestDTO);
            trnDetail.setTransactionUid(trnHeaderAsnToBeUpdated.getTransactionUid());
            trnDetail.setTransactionSlNo(countUpdate);
            trnDetail.setTrnHeaderAsn(trnHeaderAsnToBeUpdated);
            TrnDetailAsn trnDetailAsn = convertTrnDetailDTOToTrnDetailAsn(trnDetailDTO,dateAndTimeRequestDTO);
            trnDetailAsn.setTransactionSlNo(countUpdate);
            trnDetail.setTrnDetailAsn(trnDetailAsn);
            trnDetailAsn.setTrnDetail(trnDetail);
            TrnDetailLot trnDetailLot = convertTrnDetailDTOToTrnDetailLot(trnDetailDTO,dateAndTimeRequestDTO);
            if (trnDetailLot != null) {
                trnDetailLot.setTransactionSlNo(countUpdate);
                trnDetail.setTrnDetailLot(trnDetailLot);
                trnDetailLot.setTrnDetail(trnDetail);
            }
            TrnDetailQc trnDetailQc = convertTrnDetailDTOToTrnDetailQc(trnDetailDTO,dateAndTimeRequestDTO);
            if (trnDetailQc != null) {
                trnDetailQc.setTransactionSlNo(countUpdate);
                trnDetail.setTrnDetailQc(trnDetailQc);
                trnDetailQc.setTrnDetail(trnDetail);
            }
            TrnDetailAsnCustoms trnDetailAsnCustoms = convertTrnDetailDTOToTrnDetailAsnCustoms(trnDetailDTO,dateAndTimeRequestDTO);
            if (trnDetailAsnCustoms != null) {
                trnDetailAsnCustoms.setTransactionSlNo(countUpdate);
                trnDetail.setTrnDetailAsnCustoms(trnDetailAsnCustoms);
                trnDetailAsnCustoms.setTrnDetail(trnDetail);
            }
        }
        countUpdate++;
        return trnDetail;
    }

    private TrnDetail setTrnDetailLotInUpdate(TrnDetail trnDetail,TrnRequestDTO.TrnDetailDTO trnDetailDTO,DateAndTimeRequestDto dateAndTimeRequestDTO){
        if (trnDetail.getTrnDetailLot() != null){
            TrnDetailLot trnDetailLot = convertTrnDetailDTOToTrnDetailLotUpdate(trnDetailDTO,trnDetail,dateAndTimeRequestDTO);
            if (trnDetailLot != null) {
                trnDetail.setTransactionSlNo(countUpdate);
                trnDetail.setTrnDetailLot(trnDetailLot);
                trnDetailLot.setTrnDetail(trnDetail);
            }else {
                //delete
            }
        }else {
            TrnDetailLot trnDetailLot = convertTrnDetailDTOToTrnDetailLot(trnDetailDTO,dateAndTimeRequestDTO);
            if (trnDetailLot != null) {
                trnDetailLot.setTransactionSlNo(countUpdate);
                trnDetail.setTrnDetailLot(trnDetailLot);
                trnDetailLot.setTrnDetail(trnDetail);
            }
        }
        return trnDetail;
    }

    private TrnDetail setTrnDetailQcInUpdate(TrnDetail trnDetail,TrnRequestDTO.TrnDetailDTO trnDetailDTO,DateAndTimeRequestDto dateAndTimeRequestDTO){
        if (trnDetail.getTrnDetailQc() != null){
            Long trnDetailQcId = trnDetail.getTrnDetailQc().getId();
            TrnDetailQc trnDetailQc = convertTrnDetailDTOToTrnDetailQcUpdate(trnDetailDTO,trnDetail,dateAndTimeRequestDTO);
            if (trnDetailQc != null){
                trnDetailQc.setTransactionSlNo(countUpdate);
                trnDetailQc.setTrnDetail(trnDetail);
                trnDetail.setTrnDetailQc(trnDetailQc);
            }else {
                //delete
                trnDetail.setTrnDetailQc(null);
                trnDetailQcService.deleteById(trnDetailQcId);
            }
        }else {
            TrnDetailQc trnDetailQc = convertTrnDetailDTOToTrnDetailQc(trnDetailDTO,dateAndTimeRequestDTO);
            if (trnDetailQc != null) {
                trnDetailQc.setTransactionSlNo(countUpdate);
                trnDetail.setTrnDetailQc(trnDetailQc);
                trnDetailQc.setTrnDetail(trnDetail);
            }
        }
        return trnDetail;
    }

    private TrnDetail setTrnDetailAsnCustoms(TrnDetail trnDetail,TrnRequestDTO.TrnDetailDTO trnDetailDTO,DateAndTimeRequestDto dateAndTimeRequestDTO){
        if (trnDetail.getTrnDetailAsnCustoms() != null){
            Long trnDetailAsnCustomsId = trnDetail.getTrnDetailAsnCustoms().getId();
            TrnDetailAsnCustoms trnDetailAsnCustoms = convertTrnDetailDTOToTrnDetailAsnCustomsUpdate(trnDetailDTO,trnDetail,dateAndTimeRequestDTO);
            if (trnDetailAsnCustoms != null){
                trnDetailAsnCustoms.setTransactionSlNo(countUpdate);
                trnDetailAsnCustoms.setTrnDetail(trnDetail);
                trnDetail.setTrnDetailAsnCustoms(trnDetailAsnCustoms);
            }else {
                //delete
                trnDetail.setTrnDetailAsnCustoms(null);
                trnDetailAsnCustomsService.deleteById(trnDetailAsnCustomsId);
            }
        }else {
            TrnDetailAsnCustoms trnDetailAsnCustoms = convertTrnDetailDTOToTrnDetailAsnCustoms(trnDetailDTO,dateAndTimeRequestDTO);
            if (trnDetailAsnCustoms != null) {
                trnDetailAsnCustoms.setTransactionSlNo(countUpdate);
                trnDetail.setTrnDetailAsnCustoms(trnDetailAsnCustoms);
                trnDetailAsnCustoms.setTrnDetail(trnDetail);
            }
        }
        return trnDetail;
    }

    /*
     * Method to convert TrnDetailDTO to TrnDetail
     * @Return TrnHeaderAsn
     */
    public TrnDetail convertTrnDetailDTOToTrnDetailUpdate(TrnRequestDTO.TrnDetailDTO trnDetailRequestDTO,TrnDetail trnDetail, DateAndTimeRequestDto dateAndTimeRequestDto) {
        log.info("ENTRY-EXIT - TrnDetailDTO to TrnDetail update mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        modelMapper.map(trnDetailRequestDTO.getDetailsForm(), trnDetail);
        if (trnDetailRequestDTO.getDetailsForm().getSku() != null){
            trnDetail.setSkuMaster(skuMasterService.getById(Long.parseLong(trnDetailRequestDTO.getDetailsForm().getSku().getId())));
        }else {trnDetail.setSkuMaster(null);}
        if (trnDetailRequestDTO.getDetailsForm().getHsCode() != null){
            trnDetail.setHsCodeMaster(hsCodeMasterService.getById(Long.parseLong(trnDetailRequestDTO.getDetailsForm().getHsCode().getId())));
        }else {trnDetail.setHsCodeMaster(null);}
        if (trnDetailRequestDTO.getDetailsForm().getUom() != null){
            trnDetail.setUomMaster(uomMasterService.getUomById(Long.parseLong(trnDetailRequestDTO.getDetailsForm().getUom().getId())));
        }else {trnDetail.setUomMaster(null);}
        if (trnDetailRequestDTO.getDetailsForm().getRuom() != null){
            trnDetail.setRUomMaster(uomMasterService.getUomById(Long.parseLong(trnDetailRequestDTO.getDetailsForm().getRuom().getId())));
        }else {trnDetail.setRUomMaster(null);}
        if (trnDetailRequestDTO.getDetailsForm().getCurrency() != null){
            trnDetail.setCurrencyMaster(currencyMasterService.getCurrencyById(Long.parseLong(trnDetailRequestDTO.getDetailsForm().getCurrency().getId())));
        }else {trnDetail.setCurrencyMaster(null);}
        modelMapper.map(dateAndTimeRequestDto,trnDetail);
        return trnDetail;
    }

    /*
     * Method to convert TrnDetailDTO to TrnDetailAsn
     * @Return TrnDetailAsn
     */
    public TrnDetailAsn convertTrnDetailDTOToTrnDetailAsnUpdate(TrnRequestDTO.TrnDetailDTO trnDetailRequestDTO,TrnDetail trnDetail, DateAndTimeRequestDto dateAndTimeRequestDTO) {
        log.info("ENTRY-EXIT - TrnDetailDTO to TrnDetailAsn mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        Long detailAsnId = trnDetail.getTrnDetailAsn().getId();
        TrnDetailAsn trnDetailAsn = modelMapper.map(trnDetail.getTrnDetailAsn(), TrnDetailAsn.class);
        modelMapper.map(trnDetailRequestDTO.getDetailsForm(),trnDetailAsn);
        double volume = grnCalculation.getRoundedValue(5,trnDetailAsn.getVolume());
        double grossWeight = grnCalculation.getRoundedValue(5,trnDetailAsn.getGrossWeight());
        double netWeight = grnCalculation.getRoundedValue(5,trnDetailAsn.getNetWeight());
        double totalAmount = grnCalculation.getRoundedValue(5,trnDetailAsn.getDocumentLineTotalAmount());
        double totalActualAmount = grnCalculation.getRoundedValue(5,trnDetailAsn.getActualLineTotalAmount());
        trnDetailAsn.setVolume(volume);
        trnDetailAsn.setGrossWeight(grossWeight);
        trnDetailAsn.setNetWeight(netWeight);
        trnDetailAsn.setDocumentLineTotalAmount(totalAmount);
        trnDetailAsn.setActualLineTotalAmount(totalActualAmount);
        trnDetailAsn.setId(detailAsnId);
        modelMapper.map(dateAndTimeRequestDTO,trnDetailAsn);
        return trnDetailAsn;
    }

    /*
     * Method to convert TrnDetailDTO to TrnDetailLot
     * @Return TrnHeaderAsn
     */
    public TrnDetailLot convertTrnDetailDTOToTrnDetailLotUpdate(TrnRequestDTO.TrnDetailDTO trnDetailRequestDTO,TrnDetail trnDetail, DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY-EXIT - TrnDetailDTO to TrnDetail Lot update mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        Long detailLotId = trnDetail.getTrnDetailLot().getId();
        TrnDetailLot trnDetailLot = modelMapper.map(trnDetail.getTrnDetailLot(), TrnDetailLot.class);
        trnDetailLot.setId(detailLotId);
        modelMapper.map(trnDetailRequestDTO.getDetailsForm(),trnDetailLot);
        if (!CollectionUtils.isEmpty(trnDetailRequestDTO.getMoreLotList())) {
            convertTrnDetailDTOToTrnDetailLot(trnDetailRequestDTO,trnDetailLot);
        }
        modelMapper.map(dateAndTimeRequestDto,trnDetailLot);
        trnDetailLot.setId(detailLotId);
        return trnDetailLot;
    }

    /*
     * Method to convert TrnDetailDTO to TrnDetailQc
     * @Return TrnDetailQc
     */
    public TrnDetailQc convertTrnDetailDTOToTrnDetailQcUpdate(TrnRequestDTO.TrnDetailDTO trnDetailRequestDTO,TrnDetail trnDetail, DateAndTimeRequestDto dateAndTimeRequestDTO) {
        log.info("ENTRY-EXIT - TrnDetailDTO to TrnDetailQc mapper");
        TrnDetailQc trnDetailQc = modelMapper.map(trnDetail.getTrnDetailQc(), TrnDetailQc.class);
        if (trnDetailRequestDTO.getMoreQCForm() != null) {
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
            modelMapper.map(trnDetailRequestDTO.getMoreQCForm(), trnDetailQc);
            modelMapper.map(dateAndTimeRequestDTO, trnDetailQc);
            if (trnDetailRequestDTO.getMoreQCForm().getInspectedBy() != null) {
                trnDetailQc.setInspectedByMaster(authUserProfileService.getById(Long.parseLong(trnDetailRequestDTO.getMoreQCForm().getInspectedBy().getId())));
            }else {
                trnDetailQc.setInspectedByMaster(null);
            }
            if (trnDetailRequestDTO.getMoreQCForm().getInspectionStatus() != null) {
                trnDetailQc.setInspectionStatusMaster(qcStatusMasterService.getById(Long.parseLong(trnDetailRequestDTO.getMoreQCForm().getInspectionStatus().getId())));
            }else {
                trnDetailQc.setInspectionStatusMaster(null);
            }
            return trnDetailQc;
        }else {return null;}
    }

    /*
     * Method to convert TrnDetailDTO to TrnDetailAsnCustoms
     * @Return TrnDetailQc
     */
    public TrnDetailAsnCustoms convertTrnDetailDTOToTrnDetailAsnCustomsUpdate(TrnRequestDTO.TrnDetailDTO trnDetailRequestDTO,TrnDetail trnDetail, DateAndTimeRequestDto dateAndTimeRequestDTO) {
        log.info("ENTRY-EXIT - TrnDetailDTO to TrnDetailAsnCustoms mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        TrnDetailAsnCustoms trnDetailAsnCustoms = modelMapper.map(trnDetail.getTrnDetailAsnCustoms(), TrnDetailAsnCustoms.class);
        if (trnDetailRequestDTO.getMoreFtaForm() != null) {
            modelMapper.map(trnDetailRequestDTO.getMoreFtaForm(),trnDetailAsnCustoms);
            modelMapper.map(dateAndTimeRequestDTO, trnDetailAsnCustoms);
            return trnDetailAsnCustoms;
        }else {return null;}
    }

    public TrnResponseDTO.TrnDetailDTO.DetailsFormDTO convertTrnDetailToDetailsFormDTO(GrnHeader grnHeader,TrnDetail trnDetail,String forGrn) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        TrnResponseDTO.TrnDetailDTO.DetailsFormDTO detailsFormDTO = modelMapper.map(trnDetail, TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.class);
        Long trnDetailId = trnDetail.getId();
        Long uomId = null;
        if (trnDetail.getUomMaster() != null){
            uomId = trnDetail.getUomMaster().getId();
            TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.UomDTO uomDTO = modelMapper.map(trnDetail.getUomMaster(),TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.UomDTO.class);
            if (trnDetail.getUomMaster().getCategoryMaster() != null){
                TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.UomDTO.CategoryDTO categoryDTO = modelMapper.map(trnDetail.getUomMaster().getCategoryMaster(),TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.UomDTO.CategoryDTO.class);
                uomDTO.setCategory(categoryDTO);
                // Iterate through the list of SKU Pack details to find the appropriate ID
                for (SkuPackDetail skuPackDetail : trnDetail.getSkuMaster().getSkuPackDetailList()) {
                    // Decide which ID to set as skuPackId, for example, the first one in the list
                    if(trnDetail.getUomMaster().getId() == skuPackDetail.getUomMaster().getId()){
                        uomDTO.setSkuPackId(skuPackDetail.getId());
                        break;
                    }
                }
            }
            detailsFormDTO.setUom(uomDTO);
        }
        if (trnDetail.getSkuMaster() != null){
            TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.SkuDTO skuDTO = modelMapper.map(trnDetail.getSkuMaster(),TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.SkuDTO.class);
            skuDTO.setPackDetails(getPackDetailInfo(uomId,trnDetail.getSkuMaster()));
            SkuResponseDTO.MoreDetailsDTO moreDetailsDTO = modelMapper.map(trnDetail.getSkuMaster(),SkuResponseDTO.MoreDetailsDTO.class);
            skuDTO.setSkuMoreDetails(moreDetailsDTO);
            detailsFormDTO.setSku(skuDTO);
        }
        if (trnDetail.getRUomMaster() != null){
            TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.UomDTO ruomDTO = modelMapper.map(trnDetail.getRUomMaster(),TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.UomDTO.class);
            if (trnDetail.getRUomMaster().getCategoryMaster() != null){
                TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.UomDTO.CategoryDTO categoryDTO = modelMapper.map(trnDetail.getRUomMaster().getCategoryMaster(),TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.UomDTO.CategoryDTO.class);
                ruomDTO.setCategory(categoryDTO);
                for (SkuPackDetail skuPackDetail : trnDetail.getSkuMaster().getSkuPackDetailList()) {
                    if(trnDetail.getRUomMaster().getId() == skuPackDetail.getUomMaster().getId()){
                        ruomDTO.setSkuPackId(skuPackDetail.getId());
                        break;
                    }
                }
            }
            detailsFormDTO.setRuom(ruomDTO);
        }
        if (trnDetail.getHsCodeMaster() != null){
            TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.HsCodeDTO hsCodeDTO = modelMapper.map(trnDetail.getHsCodeMaster(),TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.HsCodeDTO.class);
            detailsFormDTO.setHsCode(hsCodeDTO);
        }
        if (trnDetail.getCurrencyMaster() != null){
            TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.CurrencyDTO currencyDTO = modelMapper.map(trnDetail.getCurrencyMaster(),TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.CurrencyDTO.class);
            detailsFormDTO.setCurrency(currencyDTO);
        }
        if (trnDetail.getTrnDetailAsn() != null){
            modelMapper.map(trnDetail.getTrnDetailAsn(),detailsFormDTO);
            double volume = trnDetail.getTrnDetailAsn().getVolume();
            double grossWeight = trnDetail.getTrnDetailAsn().getGrossWeight();
            double netWeight = trnDetail.getTrnDetailAsn().getNetWeight();
            double totalAmount = trnDetail.getTrnDetailAsn().getDocumentLineTotalAmount();
            double totalActualAmount = trnDetail.getTrnDetailAsn().getActualLineTotalAmount();
            Optional.ofNullable(volume).ifPresent(val -> detailsFormDTO.setVolume(String.format("%.5f", val)));
            Optional.ofNullable(grossWeight).ifPresent(val -> detailsFormDTO.setGrossWeight(String.format("%.5f", val)));
            Optional.ofNullable(netWeight).ifPresent(val -> detailsFormDTO.setNetWeight(String.format("%.5f", val)));
            Optional.ofNullable(totalAmount).ifPresent(val -> detailsFormDTO.setDocumentLineTotalAmount(String.format("%.5f", val)));
            Optional.ofNullable(totalActualAmount).ifPresent(val -> detailsFormDTO.setActualLineTotalAmount(String.format("%.5f", val)));
        }
        if (trnDetail.getTrnDetailLot() != null){
            modelMapper.map(trnDetail.getTrnDetailLot(),detailsFormDTO);
        }
        detailsFormDTO.setId(trnDetailId);
        if (forGrn != null && forGrn.equals("Yes") && grnHeader != null){
            double qtyAlreadyReceived = grnDetailService.getAllCompletedQtyForOneDetail(trnDetail,grnHeader,true);
            double roundingForUom = trnDetail.getUomMaster().getDecimalPlaces();
            double remainingExpQty = trnDetail.getExpQty() - qtyAlreadyReceived;
            remainingExpQty = grnCalculation.getRoundedValue(roundingForUom,remainingExpQty);
            detailsFormDTO.setQtyAlreadyReceived(qtyAlreadyReceived);
            detailsFormDTO.setRemainingExpQty(remainingExpQty);
        }
        return detailsFormDTO;
    }

    public TrnResponseDTO.TrnDetailDTO.MoreLotFormDTO convertTrnDetailToMoreLotFormDTO(TrnDetail trnDetail) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        if (trnDetail.getTrnDetailLot() != null) {
            return modelMapper.map(trnDetail.getTrnDetailLot(), TrnResponseDTO.TrnDetailDTO.MoreLotFormDTO.class);
        }else {return null;}
    }

    public List<TrnResponseDTO.TrnDetailDTO.LotDTO> convertTrnDetailToLotListDTO(TrnDetail trnDetail) {
        List<TrnResponseDTO.TrnDetailDTO.LotDTO> lotDTOList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(trnDetail.getSkuMaster().getSkuLotDetailsList())){
            List<SkuResponseDTO.LotDTO> lotDTOList1 = skuMapper.convertLotDetailsListToDTOList(trnDetail.getSkuMaster().getSkuLotDetailsList());
            if (trnDetail.getTrnDetailLot() != null) {
                lotDTOList = mapToLotDTO1To5(lotDTOList1,trnDetail.getTrnDetailLot());
                lotDTOList.addAll(mapToLotDTO6To10(lotDTOList1,trnDetail.getTrnDetailLot()));
            }
        }else {
            return Collections.emptyList();
        }
        return lotDTOList;
    }

    private List<TrnResponseDTO.TrnDetailDTO.LotDTO> mapToLotDTO1To5(List<SkuResponseDTO.LotDTO> lotDTOList1,TrnDetailLot trnDetailLot) {
        List<TrnResponseDTO.TrnDetailDTO.LotDTO> lotDTOList = new ArrayList<>();
        for (SkuResponseDTO.LotDTO lotDTO : lotDTOList1) {
            TrnResponseDTO.TrnDetailDTO.LotDTO lotDTO1 = modelMapper.map(lotDTO, TrnResponseDTO.TrnDetailDTO.LotDTO.class);
            if (lotDTO.getName().equalsIgnoreCase(lot01)) {
                lotDTO1.setValue(trnDetailLot.getLot01());
                lotDTOList.add(lotDTO1);
            }
            if (lotDTO.getName().equalsIgnoreCase(lot02)) {
                lotDTO1.setValue(trnDetailLot.getLot02());
                lotDTOList.add(lotDTO1);
            }
            if (lotDTO.getName().equalsIgnoreCase(lot03)) {
                lotDTO1.setValue(trnDetailLot.getLot03());
                lotDTOList.add(lotDTO1);
            }
            if (lotDTO.getName().equalsIgnoreCase(lot04)) {
                lotDTO1.setValue(trnDetailLot.getLot04());
                lotDTOList.add(lotDTO1);
            }
            if (lotDTO.getName().equalsIgnoreCase(lot05)) {
                lotDTO1.setValue(trnDetailLot.getLot05());
                lotDTOList.add(lotDTO1);
            }
        }
        return lotDTOList;
    }

    private List<TrnResponseDTO.TrnDetailDTO.LotDTO> mapToLotDTO6To10(List<SkuResponseDTO.LotDTO> lotDTOList1,TrnDetailLot trnDetailLot) {
        List<TrnResponseDTO.TrnDetailDTO.LotDTO> lotDTOList = new ArrayList<>();
        for (SkuResponseDTO.LotDTO lotDTO : lotDTOList1) {
            TrnResponseDTO.TrnDetailDTO.LotDTO lotDTO1 = modelMapper.map(lotDTO, TrnResponseDTO.TrnDetailDTO.LotDTO.class);
            if (lotDTO.getName().equalsIgnoreCase(lot06)) {
                lotDTO1.setValue(trnDetailLot.getLot06());
                lotDTOList.add(lotDTO1);
            }
            if (lotDTO.getName().equalsIgnoreCase(lot07)) {
                lotDTO1.setValue(trnDetailLot.getLot07());
                lotDTOList.add(lotDTO1);
            }
            if (lotDTO.getName().equalsIgnoreCase(lot08)) {
                lotDTO1.setValue(trnDetailLot.getLot08());
                lotDTOList.add(lotDTO1);
            }
            if (lotDTO.getName().equalsIgnoreCase(lot09)) {
                lotDTO1.setValue(trnDetailLot.getLot09());
                lotDTOList.add(lotDTO1);
            }
            if (lotDTO.getName().equalsIgnoreCase(lot10)) {
                lotDTO1.setValue(trnDetailLot.getLot10());
                lotDTOList.add(lotDTO1);
            }
        }
        return lotDTOList;
    }

    public  TrnResponseDTO.TrnDetailDTO.LotDTO convertLotStringToLotDTO(String lotString) {
        if(lotString!= null && !lotString.isEmpty() && !lotString.isBlank()) {
            TrnResponseDTO.TrnDetailDTO.LotDTO lotDTO = new TrnResponseDTO.TrnDetailDTO.LotDTO();
            String[] parts = lotString.split("%");
            if (parts.length > 0) {
                lotDTO.setLabel(parts[0]);
                lotDTO.setValue("");
                lotDTO.setIsMandatory("No");
                if (parts.length > 1) {
                    lotDTO.setValue(parts[1]);
                    lotDTO.setIsMandatory("No");
                    if (parts.length > 2) {
                        lotDTO.setIsMandatory(parts[2]);
                    }
                }
            }
            return lotDTO;
        }
        else{
            return new TrnResponseDTO.TrnDetailDTO.LotDTO();
        }
    }

    private SkuPackDetailResponseDTO getPackDetailInfo(Long uomId, SkuMaster skuMaster) {
        Optional<SkuPackDetail> matchingPackDetail = skuMaster.getSkuPackDetailList().stream()
                .filter(packDetail -> packDetail.getUomMaster() != null && packDetail.getUomMaster().getId().equals(uomId) )
                .findFirst();
        if (matchingPackDetail.isPresent()) {
            SkuPackDetail skuPackDetail = matchingPackDetail.get();
            return skuPackDetailMapper.convertEntityToResponse(skuPackDetail);
        } else {
            // No SkuPackDetail found with the specified version
        }
        return null;
    }

    public TrnResponseDTO.TrnDetailDTO.MoreQCFormDTO convertTrnDetailToMoreQCFormDTO(TrnDetail trnDetail) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        if (trnDetail.getTrnDetailQc() != null) {
            TrnResponseDTO.TrnDetailDTO.MoreQCFormDTO moreQCFormDTO = modelMapper.map(trnDetail.getTrnDetailQc(), TrnResponseDTO.TrnDetailDTO.MoreQCFormDTO.class);
            if (trnDetail.getTrnDetailQc().getInspectionStatusMaster() != null){
                TrnResponseDTO.TrnDetailDTO.MoreQCFormDTO.QcStatusDTO qcStatusDTO = modelMapper.map(trnDetail.getTrnDetailQc().getInspectionStatusMaster(),TrnResponseDTO.TrnDetailDTO.MoreQCFormDTO.QcStatusDTO.class);
                moreQCFormDTO.setInspectionStatus(qcStatusDTO);
            }
            if (trnDetail.getTrnDetailQc().getInspectedByMaster() != null){
                TrnResponseDTO.TrnDetailDTO.MoreQCFormDTO.AuthUserProfileDTO authUserProfileDTO = modelMapper.map(trnDetail.getTrnDetailQc().getInspectedByMaster(),TrnResponseDTO.TrnDetailDTO.MoreQCFormDTO.AuthUserProfileDTO.class);
                moreQCFormDTO.setInspectedBy(authUserProfileDTO);
            }
            return moreQCFormDTO;
        }else {return null;}
    }

    public TrnResponseDTO.TrnDetailDTO.MoreFtaFormDTO convertTrnDetailToMoreFtaFormDTO(TrnDetail trnDetail) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        if (trnDetail.getTrnDetailAsnCustoms() != null) {
            return modelMapper.map(trnDetail.getTrnDetailAsnCustoms(), TrnResponseDTO.TrnDetailDTO.MoreFtaFormDTO.class);
        }else {return null;}
    }

    private void identifyAndDeleteNonExistingIds(TrnRequestDTO trnRequestDTO,List<TrnDetail> trnDetailListToBeDeleted){
        Set<Long> newIdsList = new HashSet<>();
        for (TrnRequestDTO.TrnDetailDTO trnDetailDTO : trnRequestDTO.getAsnDetailsList()) {
            if (trnDetailDTO.getDetailsForm().getId() != null && !trnDetailDTO.getDetailsForm().getId().isEmpty() &&
                    !trnDetailDTO.getDetailsForm().getId().isBlank()) {
                newIdsList.add(Long.parseLong(trnDetailDTO.getDetailsForm().getId()));
            }
        }
        if (!newIdsList.isEmpty()) {
            Iterator<TrnDetail> iterator = trnDetailListToBeDeleted.iterator();
            while (iterator.hasNext()) {
                TrnDetail trnDetail = iterator.next();
                Long id = trnDetail.getId();
                // Check if the id exists in firstIds set
                if (newIdsList.contains(id)) {
                    iterator.remove(); // Remove the element from secondList
                }
            }
            trnDetailService.deleteAllInIterable(trnDetailListToBeDeleted);
        }else {trnDetailService.deleteAllInIterable(trnDetailListToBeDeleted);}
    }

    public List<TrnResponseDTO.TrnDetailDTO.LotDTO> convertTrnDetailToMoreLotList(TrnDetail trnDetail){
        SkuMaster skuMaster=trnDetail.getSkuMaster();
        TrnDetailLot trnDetailLot = trnDetail.getTrnDetailLot();
        SkuResponseDTO skuResponseDTO = skuMapper.convertEntityToDTO(skuMaster);
        skuResponseDTO.setSkulotDetailsList(skuMapper.convertLotDetailsListToDTOList(skuMaster.getSkuLotDetailsList()));
        List<TrnResponseDTO.TrnDetailDTO.LotDTO> lotDTOList;
        lotDTOList = mapToLotDTO1To4Two(skuResponseDTO,trnDetailLot);
        lotDTOList.addAll(mapToLotDTO5To8Two(skuResponseDTO,trnDetailLot));
        lotDTOList.addAll(mapToLotDTO9To10Two(skuResponseDTO,trnDetailLot));
        return lotDTOList;
    }

    private List<TrnResponseDTO.TrnDetailDTO.LotDTO>  mapToLotDTO1To4Two(SkuResponseDTO skuResponseDTO,TrnDetailLot trnDetailLot) {
        List<TrnResponseDTO.TrnDetailDTO.LotDTO> lotDTOList = new ArrayList<>();
        for(SkuResponseDTO.LotDTO lotDTO: skuResponseDTO.getSkulotDetailsList()){
            if (trnDetailLot != null) {
                if (lotDTO.getName().equalsIgnoreCase(lot01)) {
                    TrnResponseDTO.TrnDetailDTO.LotDTO lotDTO1 = modelMapper.map(lotDTO, TrnResponseDTO.TrnDetailDTO.LotDTO.class);
                    lotDTO1.setValue(trnDetailLot.getLot01());
                    lotDTOList.add(lotDTO1);
                }
                if (lotDTO.getName().equalsIgnoreCase(lot02)) {
                    TrnResponseDTO.TrnDetailDTO.LotDTO lotDTO2 = modelMapper.map(lotDTO, TrnResponseDTO.TrnDetailDTO.LotDTO.class);
                    lotDTO2.setValue(trnDetailLot.getLot02());
                    lotDTOList.add(lotDTO2);
                }
                if (lotDTO.getName().equalsIgnoreCase(lot03)) {
                    TrnResponseDTO.TrnDetailDTO.LotDTO lotDTO3 = modelMapper.map(lotDTO, TrnResponseDTO.TrnDetailDTO.LotDTO.class);
                    lotDTO3.setValue(trnDetailLot.getLot03());
                    lotDTOList.add(lotDTO3);
                }
                if (lotDTO.getName().equalsIgnoreCase(lot04)) {
                    TrnResponseDTO.TrnDetailDTO.LotDTO lotDTO4 = modelMapper.map(lotDTO, TrnResponseDTO.TrnDetailDTO.LotDTO.class);
                    lotDTO4.setValue(trnDetailLot.getLot04());
                    lotDTOList.add(lotDTO4);
                }
            }
        }
        return lotDTOList;
    }

    private List<TrnResponseDTO.TrnDetailDTO.LotDTO> mapToLotDTO5To8Two(SkuResponseDTO skuResponseDTO,TrnDetailLot trnDetailLot){
        List<TrnResponseDTO.TrnDetailDTO.LotDTO> lotDTOList = new ArrayList<>();
        for(SkuResponseDTO.LotDTO lotDTO: skuResponseDTO.getSkulotDetailsList()){
            if (trnDetailLot != null) {
                if (lotDTO.getName().equalsIgnoreCase(lot05)) {
                    TrnResponseDTO.TrnDetailDTO.LotDTO lotDTO5 = modelMapper.map(lotDTO, TrnResponseDTO.TrnDetailDTO.LotDTO.class);
                    lotDTO5.setValue(trnDetailLot.getLot05());
                    lotDTOList.add(lotDTO5);
                }
                if (lotDTO.getName().equalsIgnoreCase(lot06)) {
                    TrnResponseDTO.TrnDetailDTO.LotDTO lotDTO6 = modelMapper.map(lotDTO, TrnResponseDTO.TrnDetailDTO.LotDTO.class);
                    lotDTO6.setValue(trnDetailLot.getLot06());
                    lotDTOList.add(lotDTO6);
                }
                if (lotDTO.getName().equalsIgnoreCase(lot07)) {
                    TrnResponseDTO.TrnDetailDTO.LotDTO lotDTO7 = modelMapper.map(lotDTO, TrnResponseDTO.TrnDetailDTO.LotDTO.class);
                    lotDTO7.setValue(trnDetailLot.getLot07());
                    lotDTOList.add(lotDTO7);
                }
                if (lotDTO.getName().equalsIgnoreCase(lot08)) {
                    TrnResponseDTO.TrnDetailDTO.LotDTO lotDTO8 = modelMapper.map(lotDTO, TrnResponseDTO.TrnDetailDTO.LotDTO.class);
                    lotDTO8.setValue(trnDetailLot.getLot08());
                    lotDTOList.add(lotDTO8);
                }
            }
        }
        return lotDTOList;
    }

    private List<TrnResponseDTO.TrnDetailDTO.LotDTO> mapToLotDTO9To10Two(SkuResponseDTO skuResponseDTO, TrnDetailLot trnDetailLot) {
        List<TrnResponseDTO.TrnDetailDTO.LotDTO> lotDTOList = new ArrayList<>();
        for(SkuResponseDTO.LotDTO lotDTO: skuResponseDTO.getSkulotDetailsList()){
            if (trnDetailLot != null) {
                if (lotDTO.getName().equalsIgnoreCase(lot09)) {
                    TrnResponseDTO.TrnDetailDTO.LotDTO lotDTO9 = modelMapper.map(lotDTO, TrnResponseDTO.TrnDetailDTO.LotDTO.class);
                    lotDTO9.setValue(trnDetailLot.getLot09());
                    lotDTOList.add(lotDTO9);
                }
                if (lotDTO.getName().equalsIgnoreCase(lot10)) {
                    TrnResponseDTO.TrnDetailDTO.LotDTO lotDT10 = modelMapper.map(lotDTO, TrnResponseDTO.TrnDetailDTO.LotDTO.class);
                    lotDT10.setValue(trnDetailLot.getLot10());
                    lotDTOList.add(lotDT10);
                }
            }
        }
        return lotDTOList;
    }

    public List<TrnResponseDTO.TrnDetailDTO.LotDTO> convertStaticLotToResponseList(SkuMaster skuMaster) {
        List<SkuResponseDTO.LotDTO> lotDTOList = skuMapper.convertStaticLotToDTOList(skuMaster);
        List<TrnResponseDTO.TrnDetailDTO.LotDTO> lotDTOList1 = new ArrayList<>();
        for (SkuResponseDTO.LotDTO lotDTO : lotDTOList){
            TrnResponseDTO.TrnDetailDTO.LotDTO lotDTO1 = modelMapper.map(lotDTO,TrnResponseDTO.TrnDetailDTO.LotDTO.class);
            lotDTOList1.add(lotDTO1);
        }
        return lotDTOList1;
    }



}
