package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.TrnRequestDTO;
import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.entity.TrnHeaderAsn;
import com.newage.wms.entity.TrnHeaderFreight;
import com.newage.wms.service.CurrencyMasterService;
import com.newage.wms.service.ShipmentHeaderService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class TrnHeaderFreightMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CurrencyMasterService currencyMasterService;

    @Autowired
    private ShipmentHeaderService freightRefShipmentMasterService;


    /*
     * Method to convert TrnHeaderFreightDTO to TrnHeaderFreight
     * @Return TrnHeaderFreight
     */
    public TrnHeaderFreight convertRequestToEntity(TrnRequestDTO trnRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto) {
        log.info("ENTRY-EXIT - TrnHeaderAsnDTO to TrnHeaderAsn mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        TrnHeaderFreight trnHeaderFreight = modelMapper.map(trnRequestDTO.getTrnHeaderFreight(), TrnHeaderFreight.class);
        modelMapper.map(dateAndTimeRequestDto,trnHeaderFreight);
        trnHeaderFreight.setFreightCurrencyMaster(currencyMasterService.getCurrencyById(Long.parseLong(trnRequestDTO.getTrnHeaderFreight().getFreightCurrency().getId())));
        trnHeaderFreight.setInsuranceCurrencyMaster(currencyMasterService.getCurrencyById(Long.parseLong(trnRequestDTO.getTrnHeaderFreight().getInsuranceCurrency().getId())));
        if (trnRequestDTO.getTrnHeaderFreight().getFreightRefShipment() != null) {
            trnHeaderFreight.setFreightRefShipmentMaster(freightRefShipmentMasterService.getById(Long.parseLong(trnRequestDTO.getTrnHeaderFreight().getFreightRefShipment().getId())));
        }
        trnHeaderFreight.setNullInEmptyString();
        return trnHeaderFreight;
    }

    /*
     * Method to convert TrnHeaderFreightDTO to TrnHeaderFreight
     * @Return TrnHeaderFreight
     */
    public void convertUpdateRequestToEntity(TrnRequestDTO trnRequestDTO,TrnHeaderAsn trnHeaderAsnToBeUpdated, DateAndTimeRequestDto dateAndTimeRequestDto) {
        log.info("ENTRY-EXIT - TrnHeaderAsnDTO to TrnHeaderAsn mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        if(trnRequestDTO.getTrnHeaderFreight()!=null) {
            modelMapper.map(trnRequestDTO.getTrnHeaderFreight(), trnHeaderAsnToBeUpdated.getTrnHeaderFreight());
        }
        modelMapper.map(dateAndTimeRequestDto,trnHeaderAsnToBeUpdated.getTrnHeaderFreight());
        trnHeaderAsnToBeUpdated.getTrnHeaderFreight().setFreightCurrencyMaster(currencyMasterService.getCurrencyById(Long.parseLong(trnRequestDTO.getTrnHeaderFreight().getFreightCurrency().getId())));
        trnHeaderAsnToBeUpdated.getTrnHeaderFreight().setInsuranceCurrencyMaster(currencyMasterService.getCurrencyById(Long.parseLong(trnRequestDTO.getTrnHeaderFreight().getInsuranceCurrency().getId())));
        if (trnRequestDTO.getTrnHeaderFreight().getFreightRefShipment() != null) {
            trnHeaderAsnToBeUpdated.getTrnHeaderFreight().setFreightRefShipmentMaster(freightRefShipmentMasterService.getById(Long.parseLong(trnRequestDTO.getTrnHeaderFreight().getFreightRefShipment().getId())));
        }
        trnHeaderAsnToBeUpdated.getTrnHeaderFreight().setNullInEmptyString();
    }

    public TrnResponseDTO.TrnHeaderFreightDTO convertEntityToResponse(TrnHeaderAsn trnHeaderAsn) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        if (trnHeaderAsn.getTrnHeaderFreight() != null) {
            TrnResponseDTO.TrnHeaderFreightDTO trnHeaderFreightDTO = modelMapper.map(trnHeaderAsn.getTrnHeaderFreight(), TrnResponseDTO.TrnHeaderFreightDTO.class);
            if (trnHeaderAsn.getTrnHeaderFreight().getFreightRefShipmentMaster() != null) {
                TrnResponseDTO.TrnHeaderFreightDTO.FreightRefShipmentDTO freightRefShipmentDTO = modelMapper.map(trnHeaderAsn.getTrnHeaderFreight().getFreightRefShipmentMaster(), TrnResponseDTO.TrnHeaderFreightDTO.FreightRefShipmentDTO.class);
                trnHeaderFreightDTO.setFreightRefShipment(freightRefShipmentDTO);
            }
            if (trnHeaderAsn.getTrnHeaderFreight().getFreightCurrencyMaster() != null) {
                TrnResponseDTO.TrnHeaderFreightDTO.CurrencyDTO freightCurrencyDTO = modelMapper.map(trnHeaderAsn.getTrnHeaderFreight().getFreightCurrencyMaster(), TrnResponseDTO.TrnHeaderFreightDTO.CurrencyDTO.class);
                trnHeaderFreightDTO.setFreightCurrency(freightCurrencyDTO);
            }
            if (trnHeaderAsn.getTrnHeaderFreight().getInsuranceCurrencyMaster() != null) {
                TrnResponseDTO.TrnHeaderFreightDTO.CurrencyDTO insuranceCurrencyDTO = modelMapper.map(trnHeaderAsn.getTrnHeaderFreight().getInsuranceCurrencyMaster(), TrnResponseDTO.TrnHeaderFreightDTO.CurrencyDTO.class);
                trnHeaderFreightDTO.setInsuranceCurrency(insuranceCurrencyDTO);
            }
            trnHeaderFreightDTO.setCreatedUser(trnHeaderAsn.getTrnHeaderFreight().getCreatedBy());
            trnHeaderFreightDTO.setUpdatedUser(trnHeaderAsn.getTrnHeaderFreight().getLastModifiedBy());
            trnHeaderFreightDTO.setUpdatedDate(trnHeaderAsn.getTrnHeaderFreight().getLastModifiedDate());
            return trnHeaderFreightDTO;
        }else {return null;}
    }
}
