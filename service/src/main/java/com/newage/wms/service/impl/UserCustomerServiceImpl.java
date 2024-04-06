package com.newage.wms.service.impl;

import com.newage.wms.entity.QUserCustomer;
import com.newage.wms.entity.UserCustomer;
import com.newage.wms.repository.UserCustomerRepository;
import com.newage.wms.service.UserCustomerService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
@Log4j2
public class UserCustomerServiceImpl implements UserCustomerService {

    @Autowired
    private UserCustomerRepository userCustomerRepository;

    /*
     * Method to get all UserCustomer with pagination, sort and filter
     * @Return Page<UserCustomer>
     */
    @Override
    public Page<UserCustomer> getAll(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - List all UserCustomer with pagination, sort and filter");
        return userCustomerRepository.findAll(predicate, pageable);
    }

    /*
     * Method to get UserCustomer by id
     * @Return UserCustomer
     */
    @Override
    public UserCustomer getById(Long id) {
        log.info("ENTRY-EXIT - Get UserCustomer by id");
        return userCustomerRepository.findById(id).
                orElseThrow(() -> new ServiceException(ServiceErrors.USER_CUSTOMER_ID_NOT_FOUND.CODE,
                        ServiceErrors.USER_CUSTOMER_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to get all UserCustomer for autoComplete
     * @Return List<UserCustomer>
     */
    @Override
    public List<UserCustomer> getAllList() {
        log.info("ENTRY-EXIT - List all UserCustomer for autoComplete");
        return userCustomerRepository.findAll();
    }

    /*
     * Method to get UserCustomer for given user
     * @Return Page<UserCustomer>
     */
    @Override
    public Page<UserCustomer> fetchAllCustomerAutoCompleteForUserId(String query, Long userId, String defaultFlag) {
        log.info("ENTRY - Fetch all Customer for given User");
        QUserCustomer qUserCustomer = QUserCustomer.userCustomer;
        Page<UserCustomer> userCustomerPage = null;
        UserCustomer firstPrimaryUserCustomer = null;
        BooleanBuilder predicate = new BooleanBuilder();
        Pageable pageable  = PageRequest.of(0, 20, Sort.by("id").ascending());
        predicate.and(qUserCustomer.userMaster.id.eq(userId));                                                          //filter based on userID
        if (query != null && !query.isEmpty() && !query.isBlank()){                                                     //filter based on code and name
            predicate.and(qUserCustomer.customerMaster.name.containsIgnoreCase(query)
                    .or(qUserCustomer.customerMaster.code.containsIgnoreCase(query)));
        }
        userCustomerPage = userCustomerRepository.findAll(predicate,pageable);
        if (defaultFlag != null && !defaultFlag.isEmpty() && !defaultFlag.isBlank() && defaultFlag.equals("yes")){      //if defaultFlag is "yes" return primary customer
            pageable = Pageable.unpaged();
            userCustomerPage = userCustomerRepository.findAll(predicate,pageable);
            firstPrimaryUserCustomer = userCustomerPage.getContent()
                    .stream()
                    .filter(userCustomerPrimary -> userCustomerPrimary.getIsPrimary() != null && userCustomerPrimary.getIsPrimary().equalsIgnoreCase("Yes"))
                    .findFirst()
                    .orElse(null);
        }
        if (firstPrimaryUserCustomer != null){
            userCustomerPage = new PageImpl<>(Collections.singletonList(firstPrimaryUserCustomer));
        }
        return userCustomerPage;
    }

    /*
     * Method to save new userCustomer
     * @return  userCustomer
     */
    @Override
    public UserCustomer saveUserCustomer(UserCustomer userCustomer) {
        log.info("ENTRY - EXIT Save all User Customer");
        return userCustomerRepository.save(userCustomer);
    }
    /*
     * Method to get all UserCustomer
     * @return Page<UserCustomer>
     */
    @Override
    public Page<UserCustomer> findAll(Predicate predicate, Pageable pageable) {
        log.info("ENTRY - EXIT - Get All UserCustomer");
        return userCustomerRepository.findAll(predicate,pageable);
    }
    /*
     * Method to update  UserCustomer
     * @return save updated UserCustomer
     */
    @Override
    public UserCustomer updateUserCustomer(UserCustomer userCustomer) {
        log.info("ENTRY - EXIT Update UserCustomer");
        return userCustomerRepository.save(userCustomer);
    }


}

