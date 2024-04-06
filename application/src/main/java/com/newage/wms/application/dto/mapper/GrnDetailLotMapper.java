package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.GrnHeaderRequestDTO;
import com.newage.wms.application.dto.responsedto.GrnHeaderResponseDTO;
import com.newage.wms.entity.*;
import com.newage.wms.service.GrnLotDetailService;
import com.newage.wms.service.HsCodeMasterService;
import com.newage.wms.service.TrnDetailService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Component
public class GrnDetailLotMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TrnDetailService trnDetailService;

    @Autowired
    private GrnLotDetailService grnLotDetailService;

    @Autowired
    private HsCodeMasterService hsCodeMasterService;

    public GrnLotDetail convertRequestToEntity(GrnHeaderRequestDTO.TrnDetailDTO trnDetailDTO,GrnHeaderRequestDTO.TrnDetailDTO.GrnDetailDTO grnDetailDTO, DateAndTimeRequestDto dateAndTimeRequestDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);              // Ensure strict matching
        GrnLotDetail grnLotDetail = modelMapper.map(dateAndTimeRequestDto,GrnLotDetail.class);
        modelMapper.map(grnDetailDTO,grnLotDetail);
        if (grnDetailDTO.getHsCode() != null){
            grnLotDetail.setHsCodeMaster(hsCodeMasterService.getById(Long.valueOf(grnDetailDTO.getHsCode().getId())));
        }
        if (!CollectionUtils.isEmpty(grnDetailDTO.getMoreLotList())){
            for (GrnHeaderRequestDTO.TrnDetailDTO.LotDTO lotDTO : grnDetailDTO.getMoreLotList()){
                setGrnLotEntity(grnLotDetail,lotDTO);
            }
        }
        TrnDetail trnDetail = trnDetailService.getById(Long.parseLong(trnDetailDTO.getDetailsForm().getId()));
        grnLotDetail.setTrnDetailMaster(trnDetail);
        grnLotDetail.setDeleted("0");
        return grnLotDetail;
    }

    private GrnLotDetail setGrnLotEntity(GrnLotDetail grnLotDetail,GrnHeaderRequestDTO.TrnDetailDTO.LotDTO lotDTO) {
        if (lotDTO.getName().equalsIgnoreCase("lot01")){
            grnLotDetail.setLot01(convertLotDTOToLotString(lotDTO));
        }
        if (lotDTO.getName().equalsIgnoreCase("lot02")){
            grnLotDetail.setLot02(convertLotDTOToLotString(lotDTO));
        }
        if (lotDTO.getName().equalsIgnoreCase("lot03")){
            grnLotDetail.setLot03(convertLotDTOToLotString(lotDTO));
        }
        if (lotDTO.getName().equalsIgnoreCase("lot04")){
            grnLotDetail.setLot04(convertLotDTOToLotString(lotDTO));
        }
        if (lotDTO.getName().equalsIgnoreCase("lot05")){
            grnLotDetail.setLot05(convertLotDTOToLotString(lotDTO));
        }
        if (lotDTO.getName().equalsIgnoreCase("lot06")){
            grnLotDetail.setLot06(convertLotDTOToLotString(lotDTO));
        }
        if (lotDTO.getName().equalsIgnoreCase("lot07")){
            grnLotDetail.setLot07(convertLotDTOToLotString(lotDTO));
        }
        if (lotDTO.getName().equalsIgnoreCase("lot08")){
            grnLotDetail.setLot08(convertLotDTOToLotString(lotDTO));
        }
        if (lotDTO.getName().equalsIgnoreCase("lot09")){
            grnLotDetail.setLot09(convertLotDTOToLotString(lotDTO));
        }
        if (lotDTO.getName().equalsIgnoreCase("lot10")){
            grnLotDetail.setLot10(convertLotDTOToLotString(lotDTO));
        }
        return grnLotDetail;
    }

    private String convertLotDTOToLotString(GrnHeaderRequestDTO.TrnDetailDTO.LotDTO lotDTO) {
        if (lotDTO.getValue() == null){
            return "";
        }
        return lotDTO.getValue();
    }

    private GrnLotDetail setDeletedInGrnLotDetail(GrnLotDetail grnLotDetail) {
        grnLotDetail.setDeleted("1");
        return grnLotDetail;
    }

    public GrnHeaderResponseDTO.TrnDetailDTO.MoreLotFormDTO convertEntityToResponse(GrnLotDetail grnLotDetail) {
        GrnHeaderResponseDTO.TrnDetailDTO.MoreLotFormDTO moreLotFormDTO = modelMapper.map(grnLotDetail,GrnHeaderResponseDTO.TrnDetailDTO.MoreLotFormDTO.class);
        moreLotFormDTO.setGrnLotId(grnLotDetail.getId());
        return moreLotFormDTO;
    }

    public void convertUpdateRequestToEntity(GrnDetail grnDetail, GrnHeaderRequestDTO.TrnDetailDTO trnDetailDTO, GrnHeaderRequestDTO.TrnDetailDTO.GrnDetailDTO grnDetailDTO, DateAndTimeRequestDto dateAndTimeRequestDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);              // Ensure strict matching
        modelMapper.map(dateAndTimeRequestDto,grnDetail.getGrnLotDetail());
        modelMapper.map(grnDetailDTO,grnDetail.getGrnLotDetail());
        if (grnDetailDTO.getHsCode() != null){
            grnDetail.getGrnLotDetail().setHsCodeMaster(hsCodeMasterService.getById(Long.valueOf(grnDetailDTO.getHsCode().getId())));
        }
        if (!CollectionUtils.isEmpty(grnDetailDTO.getMoreLotList())){
            for (GrnHeaderRequestDTO.TrnDetailDTO.LotDTO lotDTO : grnDetailDTO.getMoreLotList()){
                setGrnLotEntity(grnDetail.getGrnLotDetail(),lotDTO);
            }
        }
        grnDetail.getGrnLotDetail().setDeleted("0");
    }
}
