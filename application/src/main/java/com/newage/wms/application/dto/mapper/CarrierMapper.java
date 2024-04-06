package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.responsedto.CarrierMasterContactResponseDTO;
import com.newage.wms.application.dto.responsedto.CarrierMasterResponseDTO;
import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.entity.CarrierMaster;
import com.newage.wms.entity.CarrierMasterContact;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@Component
public class CarrierMapper {

    @Autowired
    private ModelMapper modelMapper;


    public CarrierMasterResponseDTO convertEntityToResponseDTO(CarrierMaster carrierMaster) {
        CarrierMasterResponseDTO carrierMasterResponseDTO = modelMapper.map(carrierMaster, CarrierMasterResponseDTO.class);

        if (!CollectionUtils.isEmpty(carrierMaster.getContacts())) {
            List<CarrierMasterContactResponseDTO> carrierMasterContactResponseDTOList = new ArrayList<>();
            for (CarrierMasterContact carrierMasterContact : carrierMaster.getContacts()) {
                CarrierMasterContactResponseDTO carrierMasterContactResponseDTO = modelMapper.map(carrierMasterContact, CarrierMasterContactResponseDTO.class);
                carrierMasterContactResponseDTOList.add(carrierMasterContactResponseDTO);
                carrierMasterContactResponseDTO.setAgentEmail(carrierMasterContact.getAgentEmail());
            }
            carrierMasterResponseDTO.setContacts(carrierMasterContactResponseDTOList);
        }

        if (carrierMasterResponseDTO.getImage() != null) {
            String imageURL = ServletUriComponentsBuilder.fromCurrentContextPath().path(
                            "/api/v1/masterdata/carrier/{id}/image.jpg").buildAndExpand(carrierMaster.getId())
                    .toUriString();
            carrierMasterResponseDTO.setImage(imageURL);
        }
        return carrierMasterResponseDTO;
    }

    public Page<CarrierMasterResponseDTO> convertEntityPageToResponsePage(Pageable pageRequest, Page<CarrierMaster> carrierMasters) {
        List<CarrierMasterResponseDTO> list = new ArrayList<>();
        carrierMasters.forEach(e -> list.add(convertEntityToResponseDTO(e)));
        return new PageImpl<>(list, pageRequest, carrierMasters.getTotalElements());
    }

    /*
     * Method to convert MovementTypeMaster Iterable to MovementType AutoCompleteDTO Iterable
     * @Return Iterable<TrnResponseDTO.TrnHeaderDTO.MovementTypeDTO>
     */
    public Iterable<TrnResponseDTO.TrnHeaderFreightShippingDTO.CarrierDTO> convertEntityIterableToAutoCompleteResponseIterable(Iterable<CarrierMaster> carrierMasterIterable){
        log.info("ENTRY-EXIT - MovementTypeMaster Iterable to MovementType AutoCompleteDTO Iterable mapper");
        return StreamSupport.stream(carrierMasterIterable.spliterator(),false)
                .map(this::convertEntityToAutoCompleteDTO)
                .collect(Collectors.toList());
    }

    /*
     * Method to convert MovementTypeMaster to MovementType AutoCompleteDTO
     * @Return Iterable<TrnResponseDTO.TrnHeaderDTO.MovementTypeDTO>
     */
    private TrnResponseDTO.TrnHeaderFreightShippingDTO.CarrierDTO convertEntityToAutoCompleteDTO(CarrierMaster carrierMaster) {
        log.info("ENTRY-EXIT - MovementTypeMaster Iterable to MovementType AutoCompleteDTO Iterable mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        return modelMapper.map(carrierMaster, TrnResponseDTO.TrnHeaderFreightShippingDTO.CarrierDTO.class);
    }

}
