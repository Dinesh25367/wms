package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.TrnRequestDTO;
import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.entity.*;
import com.newage.wms.service.CarrierMasterService;
import com.newage.wms.service.OriginMasterService;
import com.newage.wms.service.TosMasterService;
import com.newage.wms.service.VesselMasterService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class TrnHeaderFreightShippingMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CarrierMasterService carrierMasterService;

    @Autowired
    private VesselMasterService vesselMasterService;

    @Autowired
    private OriginMasterService originMasterService;

    @Autowired
    private TosMasterService tosMasterService;

    public TrnHeaderFreightShipping convertRequestToEntity(TrnRequestDTO.TrnHeaderFreightShippingDTO trnHeaderFreightShippingDTO, DateAndTimeRequestDto dateAndTimeRequestDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        TrnHeaderFreightShipping trnHeaderFreightShipping = modelMapper.map(trnHeaderFreightShippingDTO, TrnHeaderFreightShipping.class);
        modelMapper.map(dateAndTimeRequestDto,trnHeaderFreightShipping);
        if (trnHeaderFreightShippingDTO.getCarrier() != null){
            CarrierMaster carrierMaster = carrierMasterService.getCarrierById(Long.parseLong(trnHeaderFreightShippingDTO.getCarrier().getId()));
            trnHeaderFreightShipping.setCarrierMaster(carrierMaster);
        }
        if (trnHeaderFreightShippingDTO.getVessel() != null){
            VesselMaster vesselMaster = vesselMasterService.getVesselById(Long.parseLong(trnHeaderFreightShippingDTO.getVessel().getId()));
            trnHeaderFreightShipping.setVesselMaster(vesselMaster);
        }
        if (trnHeaderFreightShippingDTO.getOrigin() != null){
            OriginMaster originMaster = originMasterService.getOriginById(Long.parseLong(trnHeaderFreightShippingDTO.getOrigin().getId()));
            trnHeaderFreightShipping.setOriginMaster(originMaster);
        }
        if (trnHeaderFreightShippingDTO.getTos() != null){
            TosMaster tosMaster = tosMasterService.getTosById(Long.parseLong(trnHeaderFreightShippingDTO.getTos().getId()));
            trnHeaderFreightShipping.setTosMaster(tosMaster);
        }
        trnHeaderFreightShipping.setNullInEmptyString();
        return trnHeaderFreightShipping;
    }

    public void convertUpdateRequestToEntity(TrnRequestDTO.TrnHeaderFreightShippingDTO trnHeaderFreightShippingDTO, TrnHeaderAsn trnHeaderAsnToBeUpdated, DateAndTimeRequestDto dateAndTimeRequestDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        modelMapper.map(trnHeaderFreightShippingDTO, trnHeaderAsnToBeUpdated.getTrnHeaderFreightShipping());
        modelMapper.map(dateAndTimeRequestDto,trnHeaderAsnToBeUpdated.getTrnHeaderFreightShipping());
        if (trnHeaderFreightShippingDTO.getCarrier() != null){
            CarrierMaster carrierMaster = carrierMasterService.getCarrierById(Long.parseLong(trnHeaderFreightShippingDTO.getCarrier().getId()));
            trnHeaderAsnToBeUpdated.getTrnHeaderFreightShipping().setCarrierMaster(carrierMaster);
        }else {trnHeaderAsnToBeUpdated.getTrnHeaderFreightShipping().setCarrierMaster(null);}
        if (trnHeaderFreightShippingDTO.getVessel() != null){
            VesselMaster vesselMaster = vesselMasterService.getVesselById(Long.parseLong(trnHeaderFreightShippingDTO.getVessel().getId()));
            trnHeaderAsnToBeUpdated.getTrnHeaderFreightShipping().setVesselMaster(vesselMaster);
        }else {trnHeaderAsnToBeUpdated.getTrnHeaderFreightShipping().setVesselMaster(null);}
        if (trnHeaderFreightShippingDTO.getOrigin() != null){
            OriginMaster originMaster = originMasterService.getOriginById(Long.parseLong(trnHeaderFreightShippingDTO.getOrigin().getId()));
            trnHeaderAsnToBeUpdated.getTrnHeaderFreightShipping().setOriginMaster(originMaster);
        }else {trnHeaderAsnToBeUpdated.getTrnHeaderFreightShipping().setOriginMaster(null);}
        if (trnHeaderFreightShippingDTO.getTos() != null){
            TosMaster tosMaster = tosMasterService.getTosById(Long.parseLong(trnHeaderFreightShippingDTO.getTos().getId()));
            trnHeaderAsnToBeUpdated.getTrnHeaderFreightShipping().setTosMaster(tosMaster);
        }else {trnHeaderAsnToBeUpdated.getTrnHeaderFreightShipping().setTosMaster(null);}
        trnHeaderAsnToBeUpdated.getTrnHeaderFreightShipping().setNullInEmptyString();
    }

    public TrnResponseDTO.TrnHeaderFreightShippingDTO convertEntityToResponse(TrnHeaderAsn trnHeaderAsn) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        if (trnHeaderAsn.getTrnHeaderFreightShipping() != null) {
            TrnResponseDTO.TrnHeaderFreightShippingDTO trnHeaderFreightShippingDTO = modelMapper.map(trnHeaderAsn.getTrnHeaderFreightShipping(), TrnResponseDTO.TrnHeaderFreightShippingDTO.class);
            if (trnHeaderAsn.getTrnHeaderFreightShipping().getCarrierMaster() != null) {
                TrnResponseDTO.TrnHeaderFreightShippingDTO.CarrierDTO carrierDTO = modelMapper.map(trnHeaderAsn.getTrnHeaderFreightShipping().getCarrierMaster(),
                        TrnResponseDTO.TrnHeaderFreightShippingDTO.CarrierDTO.class);
                trnHeaderFreightShippingDTO.setCarrier(carrierDTO);
            }
            if (trnHeaderAsn.getTrnHeaderFreightShipping().getVesselMaster() != null) {
                TrnResponseDTO.TrnHeaderFreightShippingDTO.VesselDTO vesselDTO = modelMapper.map(trnHeaderAsn.getTrnHeaderFreightShipping().getVesselMaster(),
                        TrnResponseDTO.TrnHeaderFreightShippingDTO.VesselDTO.class);
                trnHeaderFreightShippingDTO.setVessel(vesselDTO);
            }
            if (trnHeaderAsn.getTrnHeaderFreightShipping().getOriginMaster() != null) {
                TrnResponseDTO.TrnHeaderFreightShippingDTO.OriginDTO originDTO = modelMapper.map(trnHeaderAsn.getTrnHeaderFreightShipping().getOriginMaster(),
                        TrnResponseDTO.TrnHeaderFreightShippingDTO.OriginDTO.class);
                trnHeaderFreightShippingDTO.setOrigin(originDTO);
            }
            if (trnHeaderAsn.getTrnHeaderFreightShipping().getTosMaster() != null) {
                TrnResponseDTO.TrnHeaderFreightShippingDTO.TosDTO tosDTO = modelMapper.map(trnHeaderAsn.getTrnHeaderFreightShipping().getTosMaster(),
                        TrnResponseDTO.TrnHeaderFreightShippingDTO.TosDTO.class);
                trnHeaderFreightShippingDTO.setTos(tosDTO);
            }
            trnHeaderFreightShippingDTO.setCreatedUser(trnHeaderAsn.getTrnHeaderFreightShipping().getCreatedBy());
            trnHeaderFreightShippingDTO.setUpdatedUser(trnHeaderAsn.getTrnHeaderFreightShipping().getLastModifiedBy());
            trnHeaderFreightShippingDTO.setUpdatedDate(trnHeaderAsn.getTrnHeaderFreightShipping().getLastModifiedDate());
            return trnHeaderFreightShippingDTO;
        }else {return null;}
    }

}
