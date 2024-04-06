package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.requestdto.TrnRequestDTO;
import com.newage.wms.application.dto.requestdto.TrnSoRequestDTO;
import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.application.dto.responsedto.TrnSoResponseDTO;
import com.newage.wms.entity.TrnHeaderAsn;
import com.newage.wms.entity.TrnHeaderFreight;
import com.newage.wms.entity.TrnHeaderSo;
import com.newage.wms.service.*;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j2
@Component

public class TrnHeaderSoFreightMapper {
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
    public TrnHeaderSo convertRequestToEntity(TrnSoRequestDTO trnSoRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto) {
        log.info("ENTRY-EXIT - TrnHeaderAsnDTO to TrnHeaderAsn mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        TrnHeaderSo trnHeaderSo = modelMapper.map(trnSoRequestDTO.getTrnHeaderSoFreight(), TrnHeaderSo.class);
        modelMapper.map(dateAndTimeRequestDto,trnHeaderSo);
        trnHeaderSo.setOrderCurrencyMaster(currencyMasterService.getCurrencyById(Long.parseLong(trnSoRequestDTO.getTrnHeaderSoFreight().getOrderCurrency().getId())));
        if (trnSoRequestDTO.getTrnHeaderSoFreight().getBookingRefShipment() != null) {
            trnHeaderSo.setBookingRefShipmentMaster(freightRefShipmentMasterService.getById(Long.parseLong(trnSoRequestDTO.getTrnHeaderSoFreight().getBookingRefShipment().getId())));
        }

        return trnHeaderSo;
    }

    /*
     * Method to convert TrnHeaderFreightDTO to TrnHeaderFreight
     * @Return TrnHeaderFreight
     */
    public void convertUpdateRequestToEntity(TrnSoRequestDTO trnSoRequestDTO,TrnHeaderAsn trnHeaderAsnToBeUpdated, DateAndTimeRequestDto dateAndTimeRequestDto) {
        log.info("ENTRY-EXIT - TrnHeaderAsnDTO to TrnHeaderAsn mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        modelMapper.map(trnSoRequestDTO.getTrnHeaderSoFreight(), trnHeaderAsnToBeUpdated.getTrnHeaderSo());
        modelMapper.map(dateAndTimeRequestDto,trnHeaderAsnToBeUpdated.getTrnHeaderSo());
        trnHeaderAsnToBeUpdated.getTrnHeaderSo().setOrderCurrencyMaster(currencyMasterService.getCurrencyById(Long.parseLong(trnSoRequestDTO.getTrnHeaderSoFreight().getOrderCurrency().getId())));
        if (trnSoRequestDTO.getTrnHeaderSoFreight().getBookingRefShipment() != null) {
            trnHeaderAsnToBeUpdated.getTrnHeaderSo().setBookingRefShipmentMaster(freightRefShipmentMasterService.getById(Long.parseLong(trnSoRequestDTO.getTrnHeaderSoFreight().getBookingRefShipment().getId())));
        }

    }
    public TrnSoResponseDTO.TrnHeaderFreightSoDTO convertEntityToResponse(TrnHeaderAsn trnHeaderAsn) {
        log.info("ENTRY-EXIT - TrnHeaderAsn to TrnHeaderAsn Response mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);// Ensure strict matching
        if (trnHeaderAsn.getTrnHeaderSo()!= null) {
            TrnSoResponseDTO.TrnHeaderFreightSoDTO trnHeaderFreightSoDTO = modelMapper.map(trnHeaderAsn.getTrnHeaderSo(), TrnSoResponseDTO.TrnHeaderFreightSoDTO.class);
            TrnSoResponseDTO.TrnHeaderFreightSoDTO.BookingRefShipmentDTO bookingRefShipmentDTO = new TrnSoResponseDTO.TrnHeaderFreightSoDTO.BookingRefShipmentDTO();
            TrnSoResponseDTO.TrnHeaderFreightSoDTO.CurrencyDTO currencyDTO = new TrnSoResponseDTO.TrnHeaderFreightSoDTO.CurrencyDTO();
            if (trnHeaderAsn.getTrnHeaderSo().getBookingRefShipmentMaster() != null) {
                modelMapper.map(trnHeaderAsn.getTrnHeaderSo().getBookingRefShipmentMaster(), bookingRefShipmentDTO);
                trnHeaderFreightSoDTO.setBookingRefShipment(bookingRefShipmentDTO);
            }
            if (trnHeaderAsn.getTrnHeaderSo().getOrderCurrencyMaster() != null) {
                modelMapper.map(trnHeaderAsn.getTrnHeaderSo().getOrderCurrencyMaster(), currencyDTO);
                trnHeaderFreightSoDTO.setOrderCurrency(currencyDTO);
            }

            trnHeaderFreightSoDTO.setCreatedUser(trnHeaderAsn.getCreatedBy());
            trnHeaderFreightSoDTO.setUpdatedUser(trnHeaderAsn.getLastModifiedBy());
            trnHeaderFreightSoDTO.setUpdatedDate(trnHeaderAsn.getLastModifiedDate());
            return trnHeaderFreightSoDTO;
        }
        else{
            return null;
        }
    }

}
