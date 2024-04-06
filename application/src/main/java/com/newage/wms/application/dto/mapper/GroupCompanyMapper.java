package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.responsedto.GroupCompanyResponseDTO;
import com.newage.wms.entity.GroupCompanyMaster;
import com.newage.wms.service.CompanyMasterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.ArrayList;
import java.util.List;

@Component
public class GroupCompanyMapper  {

    @Autowired
    CompanyMasterService companyMasterService;

    @Autowired
    private ModelMapper modelMapper;

    public GroupCompanyResponseDTO convertEntityToResponseDTO(GroupCompanyMaster groupCompanyMaster) {

        GroupCompanyResponseDTO groupCompanyResponseDTO = modelMapper.map(groupCompanyMaster, GroupCompanyResponseDTO.class);
        groupCompanyResponseDTO.setId(groupCompanyResponseDTO.getId());
        if (groupCompanyMaster.getLogo() != null) {
            String imageURL = ServletUriComponentsBuilder.fromCurrentContextPath().path(
                            "/api/v1/sales/public/group-company/{id}/image.jpg").buildAndExpand(groupCompanyMaster.getId())
                    .toUriString();
            groupCompanyResponseDTO.setLogo(imageURL);
        }

        List<GroupCompanyResponseDTO.CompanyMasterDto> companyMasterDtos = new ArrayList<>();
        groupCompanyResponseDTO.setCompanyDetails(companyMasterDtos);
        return groupCompanyResponseDTO;
    }

    public Page<GroupCompanyResponseDTO> convertEntityPageToResponsePage(Pageable pageRequest, Page<GroupCompanyMaster> groupCompanyMasters) {
        List<GroupCompanyResponseDTO> dtos = new ArrayList<>();
        groupCompanyMasters.forEach(e -> dtos.add(convertEntityToResponseDTO(e)));
        return new PageImpl<>(dtos, pageRequest, groupCompanyMasters.getTotalElements());
    }

}
