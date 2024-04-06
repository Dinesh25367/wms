package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.responsedto.CompanyResponseDTO;
import com.newage.wms.entity.CompanyMaster;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class CompanyMapper {

    @Autowired
    private ModelMapper modelMapper;

    public CompanyResponseDTO convertEntityToResponseDTO(CompanyMaster companyMaster) {

        CompanyResponseDTO companyResponseDTO = modelMapper.map(companyMaster, CompanyResponseDTO.class);
        companyResponseDTO.setId(companyResponseDTO.getId());

        if (companyMaster.getGroupCompany() != null) {
            companyResponseDTO.setGroupCompany(new CompanyResponseDTO.GroupCompanyMasterDTO(companyMaster.getGroupCompany().getId(), companyMaster.getGroupCompany().getCode(), companyMaster.getGroupCompany().getName()));
        }

        if (companyMaster.getLogo() != null) {
            String imageURL = ServletUriComponentsBuilder.fromCurrentContextPath().path(
                            "/api/v1/sales/public/company/{id}/image.jpg").buildAndExpand(companyMaster.getId())
                    .toUriString();
            companyResponseDTO.setLogo(imageURL);
        }
        if (companyResponseDTO.getBranchMasterList() != null) {
            companyResponseDTO.getBranchMasterList().clear();
        }
        companyResponseDTO.getBranchMasterList();
        return companyResponseDTO;
    }

}
