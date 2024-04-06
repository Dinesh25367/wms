package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.responsedto.BranchResponseDTO;
import com.newage.wms.entity.*;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class BranchMapper {

    @Autowired
    private ModelMapper modelMapper;

    /*
     * Method to Covert Entity to Response DTO
     * Return  Response DTO
     */
    public BranchResponseDTO convertEntityToResponseDTO(BranchMaster branchMaster) {
        log.info("ENTRY-EXIT - Entity to DTO mapper");
        BranchResponseDTO branchResponseDTO = modelMapper.map(branchMaster, BranchResponseDTO.class);
        branchResponseDTO.setId(branchResponseDTO.getId());
        if (branchMaster.getCountryMaster() != null) {
            branchResponseDTO.setCountry(new BranchResponseDTO.CountryMasterDTO(branchMaster.getCountryMaster().getId(),
                    branchMaster.getCountryMaster().getCode(), branchMaster.getCountryMaster().getName()));
        }
        if (branchMaster.getStateMaster() != null) {
            branchResponseDTO.setState(new BranchResponseDTO.StateMasterDTO(branchMaster.getStateMaster().getId(),
                    branchMaster.getStateMaster().getCode(), branchMaster.getStateMaster().getName()));
        }
        if (branchMaster.getCityMaster() != null) {
            branchResponseDTO.setCity(new BranchResponseDTO.CityMasterDTO(branchMaster.getCityMaster().getId(), branchMaster.getCityMaster().getName()));
        }
        if (branchMaster.getCurrencyMaster() != null) {
            branchResponseDTO.setCurrency(new BranchResponseDTO.CurrencyMasterDTO(branchMaster.getCurrencyMaster().getId(),
                    branchMaster.getCurrencyMaster().getCode(), branchMaster.getCurrencyMaster().getName()));
        }
        if (branchMaster.getZoneMaster() != null) {
            branchResponseDTO.setZone(new BranchResponseDTO.ZoneMasterDTO(branchMaster.getZoneMaster().getId(),
                    branchMaster.getZoneMaster().getCode(), branchMaster.getZoneMaster().getName()));
        }
        if (branchMaster.getAgentMaster() != null) {
            branchResponseDTO.setAgent(new BranchResponseDTO.AgentMasterDTO(branchMaster.getAgentMaster().getId(),
                    branchMaster.getAgentMaster().getCode(), branchMaster.getAgentMaster().getName(), branchMaster.getAgentMaster().getName() + "(" + branchMaster.getAgentMaster().getCode() + ")"));

        }
        if (branchMaster.getCompanyMaster() != null) {
            branchResponseDTO.setCompany(new BranchResponseDTO.CompanyMasterDTO(branchMaster.getCompanyMaster().getId(),
                    branchMaster.getCompanyMaster().getName()));
        }
        return branchResponseDTO;
    }

}