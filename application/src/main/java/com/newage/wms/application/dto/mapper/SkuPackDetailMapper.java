package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.responsedto.DefaultFieldsResponseDTO;
import com.newage.wms.application.dto.responsedto.SkuPackDetailResponseDTO;
import com.newage.wms.entity.SkuPackDetail;
import com.newage.wms.service.BranchMasterService;
import com.newage.wms.service.CompanyMasterService;
import com.newage.wms.service.GroupCompanyMasterService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class SkuPackDetailMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BranchMasterService branchMasterService;

    @Autowired
    private CompanyMasterService companyMasterService;

    @Autowired
    private GroupCompanyMasterService groupCompanyMasterService;

    public SkuPackDetailResponseDTO convertEntityToResponse(SkuPackDetail skuPackDetail) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        SkuPackDetailResponseDTO skuPackDetailResponseDTO = modelMapper.map(skuPackDetail, SkuPackDetailResponseDTO.class);
        if (skuPackDetail.getBranchMaster() != null){
            DefaultFieldsResponseDTO.BranchDTO branchDTO = modelMapper.map(skuPackDetail.getBranchMaster(),DefaultFieldsResponseDTO.BranchDTO.class);
            skuPackDetailResponseDTO.setBranch(branchDTO);
        }
        if (skuPackDetail.getCompanyMaster() != null){
            DefaultFieldsResponseDTO.CompanyDTO companyDTO = modelMapper.map(skuPackDetail.getBranchMaster(),DefaultFieldsResponseDTO.CompanyDTO.class);
            skuPackDetailResponseDTO.setCompany(companyDTO);
        }
        if (skuPackDetail.getGroupCompanyMaster() != null){
            DefaultFieldsResponseDTO.GroupCompanyDTO groupCompanyDTO = modelMapper.map(skuPackDetail.getBranchMaster(),DefaultFieldsResponseDTO.GroupCompanyDTO.class);
            skuPackDetailResponseDTO.setGroupCompany(groupCompanyDTO);
        }
        if (skuPackDetail.getUomMaster() != null){
            SkuPackDetailResponseDTO.UomDTO uomDTO = modelMapper.map(skuPackDetail.getUomMaster(),SkuPackDetailResponseDTO.UomDTO.class);
            if (skuPackDetail.getUomMaster().getCategoryMaster() != null){
                SkuPackDetailResponseDTO.UomDTO.CategoryDTO categoryDTO = modelMapper.map(skuPackDetail.getUomMaster().getCategoryMaster(),SkuPackDetailResponseDTO.UomDTO.CategoryDTO.class);
                uomDTO.setCategory(categoryDTO);
            }
            skuPackDetailResponseDTO.setUom(uomDTO);
        }
        skuPackDetailResponseDTO.setCreatedUser(skuPackDetail.getCreatedBy());
        skuPackDetailResponseDTO.setUpdatedDate(skuPackDetail.getLastModifiedDate());
        skuPackDetailResponseDTO.setUpdatedUser(skuPackDetail.getLastModifiedBy());
        return skuPackDetailResponseDTO;
    }

}