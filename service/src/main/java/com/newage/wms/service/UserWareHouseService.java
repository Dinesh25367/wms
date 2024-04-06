package com.newage.wms.service;

import com.newage.wms.entity.UserWareHouse;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface UserWareHouseService {

    Page<UserWareHouse> getAll(Predicate predicate, Pageable pageable);

    UserWareHouse getById(Long id);

    List<UserWareHouse> getAllList();

    Page<UserWareHouse> fetchAllWareHouseAutoCompleteForUserId(String query,Long userId,String defaultFlag,Long branchId);

    UserWareHouse saveUserWareHouse(UserWareHouse userWareHouse);

    UserWareHouse updateUserWareHouse(UserWareHouse userWareHouse);

    Boolean userIdCheckForGrn(Long userId);

    Boolean userIdCheckForAsn(Long userId);

}
