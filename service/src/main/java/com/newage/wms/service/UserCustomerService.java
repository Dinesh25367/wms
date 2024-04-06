package com.newage.wms.service;

import com.newage.wms.entity.UserCustomer;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface UserCustomerService {

    Page<UserCustomer> getAll(Predicate predicate, Pageable pageable);

    UserCustomer getById(Long id);

    List<UserCustomer> getAllList();

    Page<UserCustomer> fetchAllCustomerAutoCompleteForUserId(String query,Long userId,String defaultFlag);

    UserCustomer saveUserCustomer (UserCustomer userCustomer);

    Page<UserCustomer> findAll(Predicate predicate,Pageable pageable);

    UserCustomer updateUserCustomer(UserCustomer userCustomer);

}

