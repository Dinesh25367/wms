package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.requestdto.CategoryRequestDTO;
import com.newage.wms.application.dto.requestdto.DateAndTimeRequestDto;
import com.newage.wms.application.dto.responsedto.CategoryResponseDTO;
import com.newage.wms.entity.CategoryMaster;
import com.newage.wms.service.BranchMasterService;
import com.newage.wms.service.CategoryMasterService;
import com.newage.wms.service.CompanyMasterService;
import com.newage.wms.service.GroupCompanyMasterService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@Component
public class CategoryMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BranchMasterService branchMasterService;

    @Autowired
    private CompanyMasterService companyMasterService;

    @Autowired
    private GroupCompanyMasterService groupCompanyMasterService;

    /*
     * Method to convert CategoryMasterIterable to CategoryMasterDTOIterable
     * @Return Iterable<CategoryMasterDto>
     */
    public Iterable<CategoryResponseDTO> convertCategoryMasterIterableToCategoryDTOIterable(Iterable<CategoryMaster> categoryMasterIterable){
        log.info("ENTRY - CategoryMasterIterable to CategoryMasterDTOIterable mapper");
        return StreamSupport.stream(categoryMasterIterable.spliterator(),false)
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());

    }

    /*
     * Method to convert Category Page to Category Response Page
     * @Return Category response Page
     */
    public Page<CategoryResponseDTO> convertCategoryPageTOResponsePage(Page<CategoryMaster> categoryMasterPage){
        log.info("ENTRY - CategoryMaster Page to Category Response Page Mapper");
        List<CategoryResponseDTO> categoryResponseDTOList = categoryMasterPage.getContent()
                .stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
        log.info("EXIT");
        return new PageImpl<>(categoryResponseDTOList,categoryMasterPage.getPageable(),categoryMasterPage.getTotalElements());
    }

    /*
     * Method to convert CategoryRequestDto to CategoryMaster entity
     * @Return CategoryMaster entity
     */
    public CategoryMaster convertRequestToEntity(CategoryRequestDTO categoryRequestDTO, DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - CategoryRequest to Category Mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CategoryMaster categoryMaster = modelMapper.map(categoryRequestDTO,CategoryMaster.class);
        modelMapper.map(dateAndTimeRequestDto,categoryMaster);
        categoryMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(categoryRequestDTO.getGroupCompanyId())));
        categoryMaster.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(categoryRequestDTO.getCompanyId())));
        categoryMaster.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(categoryRequestDTO.getBranchId())));
        categoryMaster.setCode(categoryRequestDTO.getCode().toUpperCase());
        categoryMaster.setName(categoryRequestDTO.getName().toUpperCase());
        log.info("EXIT");
        return categoryMaster;
    }

    /*
     * Method to convert update CategoryRequestDto to CategoryMaster entity
     */
    public void convertUpdateRequestToEntity(CategoryRequestDTO categoryRequestDTO,CategoryMaster categoryMaster,DateAndTimeRequestDto dateAndTimeRequestDto){
        log.info("ENTRY - Update Category RequestDTO to CategoryMaster mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(categoryRequestDTO,categoryMaster);
        modelMapper.map(dateAndTimeRequestDto,categoryMaster);
        categoryMaster.setGroupCompanyMaster(groupCompanyMasterService.getGroupCompanyById(Long.parseLong(categoryRequestDTO.getGroupCompanyId())));
        categoryMaster.setCompanyMaster(companyMasterService.getCompanyById(Long.parseLong(categoryRequestDTO.getCompanyId())));
        categoryMaster.setBranchMaster(branchMasterService.getBranchById(Long.parseLong(categoryRequestDTO.getBranchId())));
        categoryMaster.setCode(categoryRequestDTO.getCode().toUpperCase());
        categoryMaster.setName(categoryRequestDTO.getName().toUpperCase());
        log.info("EXIT");

    }

    /*
     * Method to convert CategoryMaster entity to CategoryMasterDTO
     * @Return CategoryDTO
     */
    public CategoryResponseDTO convertEntityToDTO(CategoryMaster categoryMaster){
        log.info("ENTRY -  CategoryMaster to CategoryMasterDTO mapper");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CategoryResponseDTO categoryResponseDTO = modelMapper.map(categoryMaster, CategoryResponseDTO.class);
        CategoryResponseDTO.GroupCompanyDTO groupCompanyDTO = new CategoryResponseDTO.GroupCompanyDTO();
        if(categoryMaster.getGroupCompanyMaster()!=null){
            modelMapper.map(categoryMaster.getGroupCompanyMaster(),groupCompanyDTO);
            categoryResponseDTO.setGroupCompany(groupCompanyDTO);
        }
        CategoryResponseDTO.CompanyDTO companyDTO = new CategoryResponseDTO.CompanyDTO();
        if(categoryMaster.getCompanyMaster()!=null){
            modelMapper.map(categoryMaster.getCompanyMaster(),companyDTO);
            categoryResponseDTO.setCompany(companyDTO);
        }
        CategoryResponseDTO.BranchDTO branchDTO = new CategoryResponseDTO.BranchDTO();
        if(categoryMaster.getBranchMaster()!=null){
            modelMapper.map(categoryMaster.getBranchMaster(),branchDTO);
            categoryResponseDTO.setBranch(branchDTO);
        }
        categoryResponseDTO.setCreatedUser(categoryMaster.getCreatedBy());
        categoryResponseDTO.setCreatedDate(categoryMaster.getCreatedDate());
        categoryResponseDTO.setUpdatedDate(categoryMaster.getLastModifiedDate());
        categoryResponseDTO.setUpdatedUser(categoryMaster.getLastModifiedBy());
        return categoryResponseDTO;
    }

}
