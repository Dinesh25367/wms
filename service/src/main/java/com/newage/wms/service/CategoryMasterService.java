package com.newage.wms.service;

import com.newage.wms.entity.CategoryMaster;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryMasterService {

    CategoryMaster getCategoryById(Long id);

    Iterable <CategoryMaster> fetchAllCategory(String query,String status,Long branchId);

    CategoryMaster saveCategory(CategoryMaster categoryMaster);

    Page<CategoryMaster> fetchAll(Predicate predicate, Pageable pageable);

    void validateUniqueCategoryAttributeSave(String code);

    void validateUniqueCategoryAttributeUpdate(String code,Long id);

}
