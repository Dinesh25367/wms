package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.entity.ShipmentHeader;
import com.newage.wms.service.BranchMasterService;
import com.newage.wms.service.CompanyMasterService;
import com.newage.wms.service.GroupCompanyMasterService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@Component
public class ShipmentHeaderMapper {

    @Autowired
    private ModelMapper modelMapper;

    /*
     * Method to convert FreightRefShipmentIterable to TrnHeaderResponseDTO.FreightRefShipmentDTO Iterable
     * @Return Iterable<TrnResponseDTO.TrnHeaderDTO.FreightRefShipmentDTO>
     */
    public Iterable<TrnResponseDTO.TrnHeaderFreightDTO.FreightRefShipmentDTO> convertEntityIterableToAutoCompleteResponseIterable(Iterable<ShipmentHeader> shipmentHeaderIterable){
        log.info("ENTRY-EXIT - FreightRefShipmentIterable to TrnResponseDTO.TrnHeaderDTO.FreightRefShipmentDTO Iterable mapper");
        return StreamSupport.stream(shipmentHeaderIterable.spliterator(),false)
                .map(this::convertEntityToAutoCompleteDTO)
                .collect(Collectors.toList());

    }

    /*
     * Method to convert FreightRefShipment to TrnResponseDTO.TrnHeaderDTO.FreightRefShipmentDTO AutoCompleteDTO
     * @Return TrnResponseDTO.TrnHeaderDTO.FreightRefShipmentDTO
     */
    private TrnResponseDTO.TrnHeaderFreightDTO.FreightRefShipmentDTO convertEntityToAutoCompleteDTO(ShipmentHeader freightRefShipmentMaster) {
        log.info("ENTRY-EXIT - FreightRefShipment to TrnHeaderResponseDTO.FreightRefShipmentDTO mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        return modelMapper.map(freightRefShipmentMaster, TrnResponseDTO.TrnHeaderFreightDTO.FreightRefShipmentDTO.class);
    }

}
