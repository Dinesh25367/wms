package com.newage.wms.service.impl;

import com.newage.wms.entity.CategoryMaster;
import com.newage.wms.entity.QCategoryMaster;
import com.newage.wms.repository.CategoryMasterRepository;
import com.newage.wms.service.CategoryMasterService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Log4j2
@Service
public class CategoryMasterServiceImpl implements CategoryMasterService {

    @Autowired
    private CategoryMasterRepository categoryMasterRepository;

    /*
     * Method to get Category by id
     * @return Category
     */
    @Override
    public CategoryMaster getCategoryById(Long id){
        log.info("ENTRY-EXIT - Get Category by id");
        return categoryMasterRepository.findById(id).
                orElseThrow(()->new ServiceException(
                        ServiceErrors.CATEGORY_MASTER_ID_NOT_FOUND.CODE,
                        ServiceErrors.CATEGORY_MASTER_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to get all Category in AutoComplete
     * @return Iterable<CategoryMaster>
     */
    @Override
    public Iterable<CategoryMaster> fetchAllCategory(String query,String status,Long branchId){
        log.info("ENTRY-EXIT - Get all Category in AutoComplete");
        Collection<Predicate> predicates = new ArrayList<>();
        predicates.add(QCategoryMaster.categoryMaster.id.isNotNull());
        if (query != null && !query.isEmpty() && !query.isBlank()){
            predicates.add(QCategoryMaster.categoryMaster.name.containsIgnoreCase(query)
                    .or(QCategoryMaster.categoryMaster.code.containsIgnoreCase(query)));
        }
        if (status != null && !status.isEmpty() && !status.isBlank()){
            predicates.add(QCategoryMaster.categoryMaster.status.eq(status));
        }
        if (branchId != null){
            predicates.add(QCategoryMaster.categoryMaster.branchMaster.id.eq(branchId));
        }
        Predicate predicateAll = ExpressionUtils.allOf(predicates);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("name").ascending());
        return categoryMasterRepository.findAll(predicateAll,pageable);
    }
    /*
     * Method to save new CategoryMaster
     * @return CategoryMaster
     */
    @Override
    public CategoryMaster saveCategory(CategoryMaster categoryMaster) {
        log.info("ENTRY - EXIT - Save new CategoryMaster");
        return categoryMasterRepository.save(categoryMaster);
    }
    /*
     * Method to get all CategoryMaster
     * @return Page<CategoryMaster>
     */
    @Override
    public Page<CategoryMaster> fetchAll(Predicate predicate, Pageable pageable) {
        log.info("ENTRY - EXIT - Get All CategoryMaster");
        return categoryMasterRepository.findAll(predicate,pageable);
    }

    @Override
    public void validateUniqueCategoryAttributeSave(String code){
        log.info("ENTRY - validate unique category attributes in save");
        List<CategoryMaster> categoryMasterList = categoryMasterRepository.findAll();
        Boolean codeExists = categoryMasterList.stream()
                .anyMatch(category -> category.getCode().equals(code));
        if (codeExists){
            throw new ServiceException(
                    ServiceErrors.AISLE_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.AISLE_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
    }

    /*
     * Method to validate unique aisle attributes in Update
     */
    @Override
    public void validateUniqueCategoryAttributeUpdate(String code,Long id){
        log.info("ENTRY - validate unique aisle attributes in update");
        List<CategoryMaster> categoryMasterList = categoryMasterRepository.findAll();
        Boolean codeExists = categoryMasterList.stream()
                .anyMatch(category -> category.getCode().equals(code) && !category.getId().equals(id));
        if (codeExists){
            throw new ServiceException(
                    ServiceErrors.AISLE_ATTRIBUTE_SHOULD_BE_UNIQUE.CODE,
                    ServiceErrors.AISLE_ATTRIBUTE_SHOULD_BE_UNIQUE.KEY);
        }
    }

}
