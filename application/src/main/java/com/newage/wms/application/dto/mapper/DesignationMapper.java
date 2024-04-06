package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.DesignationRequestDTO;
import com.newage.wms.application.dto.responsedto.DesignationResponseDTO;
import com.newage.wms.entity.DesignationMaster;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@Log4j2
public class DesignationMapper {

    @Autowired
    private ModelMapper modelMapper;

    /*
     *Method to Convert Entity to Response DTO
     * Return DesignationResponseDTO
     */
    public DesignationResponseDTO convertEntityToResponseDTO(DesignationMaster designationMaster) {
        log.info("ENTRY-EXIT - Entity to Response mapper");
        DesignationResponseDTO designationResponseDTO = modelMapper.map(designationMaster, DesignationResponseDTO.class);
        designationResponseDTO.setId(designationResponseDTO.getId());
        if (designationMaster.getDepartment() != null) {
            designationResponseDTO.setDepartment(new DesignationResponseDTO.DepartmentMasterDTO(designationMaster.getDepartment().getId(), designationMaster.getDepartment().getName()));
        }
        return designationResponseDTO;
    }

    /*
     *Method to Convert Create Request To Entity
     * Return DesignationMaster
     */
    public DesignationMaster convertCreateRequestToEntity(DesignationRequestDTO designationRequestDTO) {
        log.info("ENTRY-EXIT - CreateRequest to Entity mapper");
        return modelMapper.map(designationRequestDTO, DesignationMaster.class);
    }

    /*
     *Method to Convert Update Request To Entity
     */
    public void convertUpdateRequestToEntity(DesignationRequestDTO designationRequestDTO, DesignationMaster designationMaster) {
        log.info("ENTRY-EXIT - UpdateRequest to Entity mapper");
        modelMapper.map(designationRequestDTO, designationMaster);
    }

    /*
     *Method to convert Entity Page To Response Page
     * Return Page<DesignationResponseDTO>
     */
    public Page<DesignationResponseDTO> convertEntityPageToResponsePage(Pageable pageRequest, Page<DesignationMaster> designationMasters) {
        log.info("ENTRY-EXIT - Entity Page to Response Page mapper");
        List<DesignationResponseDTO> dtos = new ArrayList<>();
        designationMasters.forEach(e -> dtos.add(convertEntityToResponseDTO(e)));
        return new PageImpl<>(dtos, pageRequest, designationMasters.getTotalElements());
    }

    /*
     * Method to fetch all Designation with filter
     * Return Iterable<DesignationResponseDTO>
     */
    public Iterable<DesignationResponseDTO> convertDesignationMasterIterableToDesignationResponseDTOIterable(Iterable<DesignationMaster> designationMasterIterable) {
        log.info("ENTRY - DesignationMasterIterable to DesignationResponseDTO mapper");
        log.info("EXIT");
        return StreamSupport.stream(designationMasterIterable.spliterator(), false)
                .map(this::convertEntityToResponseDTO)
                .collect(Collectors.toList());
    }

}
