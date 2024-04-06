package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.TrnSoRequestDTO;
import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.entity.*;
import com.newage.wms.service.*;
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

public class TrnDetailSoMapper {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TrnDetailMapper trnDetailMapper;

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
    private TrnDetailLotService trnDetailLotService;

    @Autowired
    private TrnDetailQcService trnDetailQcService;

    @Autowired
    private TrnDetailSoService trnDetailSoService;


    private Integer countUpdate;

    public void convertUpdateRequestListToEntityListAndDeleteNonExistingIds(TrnSoRequestDTO trnSoRequestDTO,TrnHeaderAsn trnHeaderAsnToBeUpdated, DateAndTimeRequestDto dateAndTimeRequestDTO){
        List<TrnDetail> trnDetailListToBeDeleted = new ArrayList<>();
        if (trnHeaderAsnToBeUpdated.getTrnDetailList() != null && !trnHeaderAsnToBeUpdated.getTrnDetailList().isEmpty()) {
            trnDetailListToBeDeleted = trnHeaderAsnToBeUpdated.getTrnDetailList();
        }
        if (!CollectionUtils.isEmpty(trnSoRequestDTO.getSoDetailsList())) {
            if (!CollectionUtils.isEmpty(trnHeaderAsnToBeUpdated.getTrnDetailList())) {
                convertUpdateRequestListToEntityList(trnSoRequestDTO.getSoDetailsList(), trnHeaderAsnToBeUpdated, dateAndTimeRequestDTO);
                identifyAndDeleteNonExistingIds(trnSoRequestDTO,trnDetailListToBeDeleted);
            }else {
                trnHeaderAsnToBeUpdated.setTrnDetailList(setTrnDetailListInSave(trnSoRequestDTO,trnHeaderAsnToBeUpdated,dateAndTimeRequestDTO));
            }
        }else
        {
            trnHeaderAsnToBeUpdated.setTrnDetailList(null);
            if (trnDetailListToBeDeleted != null) {
                trnDetailService.deleteAllInIterable(trnDetailListToBeDeleted);
            }
        }
    }

    private List<TrnDetail>  setTrnDetailListInSave(TrnSoRequestDTO trnSoRequestDTO,TrnHeaderAsn trnHeaderAsnToBeUpdated,DateAndTimeRequestDto dateAndTimeRequestDTO){
        List<TrnDetail> trnDetailList = new ArrayList<>();
        Integer count = 1;
        for (TrnSoRequestDTO.TrnDetailDTO trnDetailDTO :trnSoRequestDTO.getSoDetailsList() ){
            TrnDetail trnDetail;
            trnDetail = convertTrnDetailDTOToTrnDetail(trnDetailDTO,dateAndTimeRequestDTO);
            trnDetail.setTransactionUid(trnHeaderAsnToBeUpdated.getTransactionUid());
            trnDetail.setTransactionSlNo(count);
            TrnDetailAsn trnDetailAsn = convertTrnDetailDTOToTrnDetailAsn(trnDetailDTO,dateAndTimeRequestDTO);
            trnDetailAsn.setTransactionSlNo(count);
            trnDetail.setTrnDetailAsn(trnDetailAsn);
            trnDetailAsn.setTrnDetail(trnDetail);
            TrnDetailLot trnDetailLot  = convertTrnDetailDTOToLotList(trnDetailDTO,dateAndTimeRequestDTO);
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
            TrnDetailSo trnDetailSo = convertTrnDetailDTOToTrnDetailSoCustoms(trnDetailDTO,dateAndTimeRequestDTO);
            if (trnDetailSo != null) {
                trnDetailSo.setTransactionSlNo(count);
                trnDetail.setTrnDetailSo(trnDetailSo);
                trnDetailSo.setTrnDetail(trnDetail);
            }
            trnDetail.setTrnHeaderAsn(trnHeaderAsnToBeUpdated);
            count++;
            trnDetailList.add(trnDetail);
        }
        return trnDetailList;
    }


    public void convertUpdateRequestListToEntityList(List<TrnSoRequestDTO.TrnDetailDTO> soDetailsList, TrnHeaderAsn trnHeaderAsnToBeUpdated, DateAndTimeRequestDto dateAndTimeRequestDto) {
        countUpdate = 1;
        List<TrnDetail> trnDetailList = soDetailsList.stream()
                .map(trnDetailDTO -> convertUpdateRequestToEntity(trnDetailDTO,trnHeaderAsnToBeUpdated, dateAndTimeRequestDto))
                .collect(Collectors.toList());
        trnHeaderAsnToBeUpdated.setTrnDetailList(trnDetailList);
    }

    /*
     * Method to convert TrnDetailDTO to TrnDetail
     * @Return TrnHeaderAsn
     */
    public TrnDetail convertTrnDetailDTOToTrnDetail(TrnSoRequestDTO.TrnDetailDTO trnDetailRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto) {
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
    public TrnDetailAsn convertTrnDetailDTOToTrnDetailAsn(TrnSoRequestDTO.TrnDetailDTO trnDetailRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDTO) {
        log.info("ENTRY-EXIT - TrnDetailDTO to TrnDetailAsn mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        TrnDetailAsn trnDetailAsn = modelMapper.map(trnDetailRequestDTO.getDetailsForm(), TrnDetailAsn.class);
        modelMapper.map(dateAndTimeRequestDTO,trnDetailAsn);
        return trnDetailAsn;
    }

    /*
     * Method to convert TrnDetailDTO to TrnDetailQc
     * @Return TrnDetailQc
     */
    public TrnDetailQc convertTrnDetailDTOToTrnDetailQc(TrnSoRequestDTO.TrnDetailDTO trnDetailRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDTO) {
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
    public TrnDetailSo convertTrnDetailDTOToTrnDetailSoCustoms(TrnSoRequestDTO.TrnDetailDTO trnDetailSoRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDTO) {
        log.info("ENTRY-EXIT - TrnDetailDTO to TrnDetailSoCustoms mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        if (trnDetailSoRequestDTO.getMoreSoForm() != null) {
            TrnDetailSo trnDetailSo = modelMapper.map(trnDetailSoRequestDTO.getMoreSoForm(), TrnDetailSo.class);
            modelMapper.map(dateAndTimeRequestDTO, trnDetailSo);
            return trnDetailSo;
        }else {return null;}
    }

    public TrnDetail convertUpdateRequestToEntity(TrnSoRequestDTO.TrnDetailDTO trnDetailDTO, TrnHeaderAsn trnHeaderAsnToBeUpdated, DateAndTimeRequestDto dateAndTimeRequestDTO) {
        TrnDetail trnDetail;
        if (trnDetailDTO.getDetailsForm().getId() != null && !trnDetailDTO.getDetailsForm().getId().isBlank() && !trnDetailDTO.getDetailsForm().getId().isEmpty()){
            trnDetail = trnDetailService.getById(Long.parseLong(trnDetailDTO.getDetailsForm().getId()));
            trnDetail  = convertTrnDetailDTOToTrnDetailUpdate(trnDetailDTO,trnDetail,dateAndTimeRequestDTO);
            trnDetail.setTransactionSlNo(countUpdate);
            TrnDetailAsn trnDetailAsn = convertTrnDetailDTOToTrnDetailAsnUpdate(trnDetailDTO,trnDetail,dateAndTimeRequestDTO);
            trnDetailAsn.setTransactionSlNo(countUpdate);
            trnDetail.setTrnDetailAsn(trnDetailAsn);
            trnDetailAsn.setTrnDetail(trnDetail);
            setTrnDetailLotInUpdate(trnDetail,trnDetailDTO,dateAndTimeRequestDTO);
            setTrnDetailQcInUpdate(trnDetail,trnDetailDTO,dateAndTimeRequestDTO);
            setTrnDetailSoCustoms(trnDetail,trnDetailDTO,dateAndTimeRequestDTO);
            trnDetail.setTrnHeaderAsn(trnHeaderAsnToBeUpdated);
        }else {
            trnDetail = convertTrnDetailDTOToTrnDetail(trnDetailDTO,dateAndTimeRequestDTO);
            trnDetail.setTrnHeaderAsn(trnHeaderAsnToBeUpdated);
            trnDetail.setTransactionUid(trnHeaderAsnToBeUpdated.getTransactionUid());
            trnDetail.setTransactionSlNo(countUpdate);
            TrnDetailAsn trnDetailAsn = convertTrnDetailDTOToTrnDetailAsn(trnDetailDTO,dateAndTimeRequestDTO);
            trnDetailAsn.setTransactionSlNo(countUpdate);
            trnDetail.setTrnDetailAsn(trnDetailAsn);
            trnDetailAsn.setTrnDetail(trnDetail);
            TrnDetailLot trnDetailLot  = convertTrnDetailDTOToLotList(trnDetailDTO,dateAndTimeRequestDTO);
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
            TrnDetailSo trnDetailSo = convertTrnDetailDTOToTrnDetailSoCustoms(trnDetailDTO,dateAndTimeRequestDTO);
            if (trnDetailSo != null) {
                trnDetailSo.setTransactionSlNo(countUpdate);
                trnDetail.setTrnDetailSo(trnDetailSo);
                trnDetailSo.setTrnDetail(trnDetail);
            }
        }
        countUpdate++;
        return trnDetail;
    }

    private TrnDetail setTrnDetailLotInUpdate(TrnDetail trnDetail,TrnSoRequestDTO.TrnDetailDTO trnDetailDTO,DateAndTimeRequestDto dateAndTimeRequestDTO){
        if (trnDetail.getTrnDetailLot() != null){
            Long detailLotId =  trnDetail.getTrnDetailLot().getId();
            TrnDetailLot trnDetailLot = convertTrnDetailDTOToTrnDetailLotUpdate(trnDetailDTO,trnDetail,dateAndTimeRequestDTO);
            if (trnDetailLot != null) {
                trnDetailLot.setTransactionSlNo(countUpdate);
                trnDetailLot.setTrnDetail(trnDetail);
                trnDetail.setTrnDetailLot(trnDetailLot);
            }else {
                //delete
                trnDetail.setTrnDetailLot(null);
                trnDetailLotService.deleteById(detailLotId);
            }
        }else {
            TrnDetailLot trnDetailLot  = convertTrnDetailDTOToLotList(trnDetailDTO,dateAndTimeRequestDTO);
            if (trnDetailLot != null) {
                trnDetailLot.setTransactionSlNo(countUpdate);
                trnDetail.setTrnDetailLot(trnDetailLot);
                trnDetailLot.setTrnDetail(trnDetail);
            }
        }
        return trnDetail;
    }

    private TrnDetail setTrnDetailQcInUpdate(TrnDetail trnDetail,TrnSoRequestDTO.TrnDetailDTO trnDetailDTO,DateAndTimeRequestDto dateAndTimeRequestDTO){
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

    private TrnDetail setTrnDetailSoCustoms(TrnDetail trnDetail,TrnSoRequestDTO.TrnDetailDTO trnDetailDTO,DateAndTimeRequestDto dateAndTimeRequestDTO){
        if (trnDetail.getTrnDetailSo() != null){
            Long trnDetailSoCustomsId = trnDetail.getTrnDetailSo().getId();
            TrnDetailSo trnDetailSo = convertTrnDetailDTOToTrnDetailSoCustomsUpdate(trnDetailDTO,trnDetail,dateAndTimeRequestDTO);
            if (trnDetailSo != null){
                trnDetailSo.setTransactionSlNo(countUpdate);
                trnDetailSo.setTrnDetail(trnDetail);
                trnDetail.setTrnDetailSo(trnDetailSo);
            }else {
                //delete
                trnDetail.setTrnDetailSo(null);
                trnDetailSoService.deleteById(trnDetailSoCustomsId);
            }
        }else {
            TrnDetailSo trnDetailSo = convertTrnDetailDTOToTrnDetailSoCustoms(trnDetailDTO,dateAndTimeRequestDTO);
            if (trnDetailSo != null) {
                trnDetailSo.setTransactionSlNo(countUpdate);
                trnDetail.setTrnDetailSo(trnDetailSo);
                trnDetailSo.setTrnDetail(trnDetail);
            }
        }
        return trnDetail;
    }

    /*
     * Method to convert TrnDetailDTO to TrnDetail
     * @Return TrnHeaderAsn
     */
    public TrnDetail convertTrnDetailDTOToTrnDetailUpdate(TrnSoRequestDTO.TrnDetailDTO trnDetailRequestDTO,TrnDetail trnDetail, DateAndTimeRequestDto dateAndTimeRequestDto) {
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
    public TrnDetailAsn convertTrnDetailDTOToTrnDetailAsnUpdate(TrnSoRequestDTO.TrnDetailDTO trnDetailRequestDTO,TrnDetail trnDetail, DateAndTimeRequestDto dateAndTimeRequestDTO) {
        log.info("ENTRY-EXIT - TrnDetailDTO to TrnDetailAsn mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        Long detailAsnId = trnDetail.getTrnDetailAsn().getId();
        TrnDetailAsn trnDetailAsn = modelMapper.map(trnDetail.getTrnDetailAsn(), TrnDetailAsn.class);
        modelMapper.map(trnDetailRequestDTO.getDetailsForm(),trnDetailAsn);
        trnDetailAsn.setId(detailAsnId);
        modelMapper.map(dateAndTimeRequestDTO,trnDetailAsn);
        return trnDetailAsn;
    }

    /*
     * Method to convert TrnDetailDTO to TrnDetailLot
     * @Return TrnHeaderAsn
     */
    public TrnDetailLot convertTrnDetailDTOToTrnDetailLotUpdate(TrnSoRequestDTO.TrnDetailDTO trnDetailRequestDTO,TrnDetail trnDetail, DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY-EXIT - TrnDetailDTO to TrnDetail Lot update mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        Long detailLotId = trnDetail.getTrnDetailLot().getId();
        TrnDetailLot trnDetailLot = modelMapper.map(trnDetail.getTrnDetailLot(), TrnDetailLot.class);
        modelMapper.map(trnDetailRequestDTO.getDetailsForm(),trnDetailLot);
        if (!CollectionUtils.isEmpty(trnDetailRequestDTO.getMoreLotList())) {
            for (TrnSoRequestDTO.TrnDetailDTO.LotDTO lotDTO : trnDetailRequestDTO.getMoreLotList()){
                setSoLotEntity(trnDetailLot,lotDTO);
            }
        }else {
            if ((trnDetailRequestDTO.getDetailsForm().getCoo() == null || trnDetailRequestDTO.getDetailsForm().getCoo().isEmpty()) &&
                    (trnDetailRequestDTO.getDetailsForm().getBatch() == null || trnDetailRequestDTO.getDetailsForm().getBatch().isEmpty()) &&
                    (trnDetailRequestDTO.getDetailsForm().getExpDate() == null || trnDetailRequestDTO.getDetailsForm().getExpDate().toString().isEmpty()) &&
                    (trnDetailRequestDTO.getDetailsForm().getMfgDate() == null || trnDetailRequestDTO.getDetailsForm().getMfgDate().toString().isEmpty()) &&
                    (trnDetailRequestDTO.getDetailsForm().getSerialNumber() == null || trnDetailRequestDTO.getDetailsForm().getSerialNumber().isEmpty())
            )  {
                trnDetailLot.setId(detailLotId);
                return null;
            }
        }
        modelMapper.map(dateAndTimeRequestDto,trnDetailLot);
        trnDetailLot.setId(detailLotId);
        return trnDetailLot;
    }

    /*
     * Method to convert TrnDetailDTO to TrnDetailQc
     * @Return TrnDetailQc
     */
    public TrnDetailQc convertTrnDetailDTOToTrnDetailQcUpdate(TrnSoRequestDTO.TrnDetailDTO trnDetailRequestDTO,TrnDetail trnDetail, DateAndTimeRequestDto dateAndTimeRequestDTO) {
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
    public TrnDetailSo convertTrnDetailDTOToTrnDetailSoCustomsUpdate(TrnSoRequestDTO.TrnDetailDTO trnDetailRequestDTO,TrnDetail trnDetail, DateAndTimeRequestDto dateAndTimeRequestDTO) {
        log.info("ENTRY-EXIT - TrnDetailDTO to TrnDetailAsnCustoms mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        TrnDetailSo trnDetailSo = modelMapper.map(trnDetail.getTrnDetailSo(), TrnDetailSo.class);
        if (trnDetailRequestDTO.getMoreSoForm() != null) {
            modelMapper.map(trnDetailRequestDTO.getMoreSoForm(),trnDetailSo);
            modelMapper.map(dateAndTimeRequestDTO, trnDetailSo);
            return trnDetailSo;
        }else {return null;}
    }

    public TrnResponseDTO.TrnDetailDTO.DetailsFormDTO convertTrnDetailToDetailsFormDTO(TrnDetail trnDetail) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        TrnResponseDTO.TrnDetailDTO.DetailsFormDTO detailsFormDTO = modelMapper.map(trnDetail, TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.class);
        Long trnDetailId = trnDetail.getId();
        if (trnDetail.getUomMaster() != null){
            TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.UomDTO uomDTO = modelMapper.map(trnDetail.getUomMaster(),TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.UomDTO.class);
            if (trnDetail.getUomMaster().getCategoryMaster() != null){
                TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.UomDTO.CategoryDTO categoryDTO = modelMapper.map(trnDetail.getUomMaster().getCategoryMaster(),TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.UomDTO.CategoryDTO.class);
                uomDTO.setCategory(categoryDTO);
            }
            detailsFormDTO.setUom(uomDTO);
        }
        if (trnDetail.getSkuMaster() != null){
            TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.SkuDTO skuDTO = modelMapper.map(trnDetail.getSkuMaster(),TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.SkuDTO.class);
            detailsFormDTO.setSku(skuDTO);
        }
        if (trnDetail.getRUomMaster() != null){
            TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.UomDTO ruomDTO = modelMapper.map(trnDetail.getRUomMaster(),TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.UomDTO.class);
            if (trnDetail.getRUomMaster().getCategoryMaster() != null){
                TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.UomDTO.CategoryDTO categoryDTO = modelMapper.map(trnDetail.getRUomMaster().getCategoryMaster(),TrnResponseDTO.TrnDetailDTO.DetailsFormDTO.UomDTO.CategoryDTO.class);
                ruomDTO.setCategory(categoryDTO);
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
        }
        if (trnDetail.getTrnDetailLot() != null){
            modelMapper.map(trnDetail.getTrnDetailLot(),detailsFormDTO);
        }
        detailsFormDTO.setId(trnDetailId);
        return detailsFormDTO;
    }

    public TrnResponseDTO.TrnDetailDTO.MoreLotFormDTO convertTrnDetailToMoreLotFormDTO(TrnDetail trnDetail) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        if (trnDetail.getTrnDetailLot() != null) {
            return modelMapper.map(trnDetail.getTrnDetailLot(), TrnResponseDTO.TrnDetailDTO.MoreLotFormDTO.class);
        }else {return null;}
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

    public TrnResponseDTO.TrnDetailDTO.MoreSoFormDTO convertTrnDetailToMoreSoFormDTO(TrnDetail trnDetail) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        if (trnDetail.getTrnDetailSo() != null) {
            return modelMapper.map(trnDetail.getTrnDetailSo(), TrnResponseDTO.TrnDetailDTO.MoreSoFormDTO.class);
        }else {return null;}
    }

    private void identifyAndDeleteNonExistingIds(TrnSoRequestDTO trnSoRequestDTO,List<TrnDetail> trnDetailListToBeDeleted){
        Set<Long> newIdsList = new HashSet<>();
        for (TrnSoRequestDTO.TrnDetailDTO trnDetailDTO : trnSoRequestDTO.getSoDetailsList()) {
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

    public TrnDetailLot convertTrnDetailDTOToLotList(TrnSoRequestDTO.TrnDetailDTO trnDetailDTO, DateAndTimeRequestDto dateAndTimeRequestDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);              // Ensure strict matching
        TrnDetailLot trnDetailLot = modelMapper.map(dateAndTimeRequestDto,TrnDetailLot.class);
        modelMapper.map(trnDetailDTO.getDetailsForm(), trnDetailLot);
        if (!CollectionUtils.isEmpty(trnDetailDTO.getMoreLotList())){
            for (TrnSoRequestDTO.TrnDetailDTO.LotDTO lotDTO : trnDetailDTO.getMoreLotList()){
                setSoLotEntity(trnDetailLot,lotDTO);
            }
        }
        return trnDetailLot;
    }

    private TrnDetailLot setSoLotEntity(TrnDetailLot trnDetailLot,TrnSoRequestDTO.TrnDetailDTO.LotDTO lotDTO) {
        if (lotDTO.getName().equalsIgnoreCase("lot01")){
            trnDetailLot.setLot01(convertLotDTOToLotString(lotDTO));
        }
        if (lotDTO.getName().equalsIgnoreCase("lot02")){
            trnDetailLot.setLot02(convertLotDTOToLotString(lotDTO));
        }
        if (lotDTO.getName().equalsIgnoreCase("lot03")){
            trnDetailLot.setLot03(convertLotDTOToLotString(lotDTO));
        }
        if (lotDTO.getName().equalsIgnoreCase("lot04")){
            trnDetailLot.setLot04(convertLotDTOToLotString(lotDTO));
        }
        if (lotDTO.getName().equalsIgnoreCase("lot05")){
            trnDetailLot.setLot05(convertLotDTOToLotString(lotDTO));
        }
        if (lotDTO.getName().equalsIgnoreCase("lot06")){
            trnDetailLot.setLot06(convertLotDTOToLotString(lotDTO));
        }
        if (lotDTO.getName().equalsIgnoreCase("lot07")){
            trnDetailLot.setLot07(convertLotDTOToLotString(lotDTO));
        }
        if (lotDTO.getName().equalsIgnoreCase("lot08")){
            trnDetailLot.setLot08(convertLotDTOToLotString(lotDTO));
        }
        if (lotDTO.getName().equalsIgnoreCase("lot09")){
            trnDetailLot.setLot09(convertLotDTOToLotString(lotDTO));
        }
        if (lotDTO.getName().equalsIgnoreCase("lot10")){
            trnDetailLot.setLot10(convertLotDTOToLotString(lotDTO));
        }
        return trnDetailLot;
    }

    private String convertLotDTOToLotString(TrnSoRequestDTO.TrnDetailDTO.LotDTO lotDTO) {
        return lotDTO.getValue();
    }

    public List<TrnDetail> removeDuplicateDetails(List<TrnDetail> trnDetailList) {
        List<TrnDetail> trnDetailListWithDuplicateRemoved = new ArrayList<>();
        Set<Long> seenIds = new HashSet<>();
        for (TrnDetail trnDetail : trnDetailList) {
            if (!seenIds.contains(trnDetail.getId())) {
                seenIds.add(trnDetail.getId());
                trnDetailListWithDuplicateRemoved.add(trnDetail);
            }
        }
        return trnDetailListWithDuplicateRemoved;
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
            TrnResponseDTO.TrnDetailDTO.MoreSoFormDTO moreSoFormDTO= convertTrnDetailToMoreSoFormDTO(trnDetail);
            trnDetailDTO.setMoreSoForm(moreSoFormDTO);
            trnDetailDTOList.add(trnDetailDTO);
        }
        return trnDetailDTOList;
    }
}
