package com.newage.wms.service.impl;

import com.newage.wms.entity.QAisleMaster;
import com.newage.wms.entity.UserWareHouse;
import com.newage.wms.entity.QUserWareHouse;
import com.newage.wms.repository.UserWareHouseRepository;
import com.newage.wms.service.UserWareHouseService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@Log4j2
public class UserWareHouseServiceImpl implements UserWareHouseService {

    @Autowired
    private UserWareHouseRepository userWareHouseRepository;

    /*
     * Method to get all UserWareHouse with pagination, sort and filter
     * @Return Page<UserWareHouse>
     */
    @Override
    public Page<UserWareHouse> getAll(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - List all UserWareHouse with pagination, sort and filter");
        return userWareHouseRepository.findAll(predicate, pageable);
    }

    /*
     * Method to get UserWareHouse by id
     * @Return UserWareHouse
     */
    @Override
    public UserWareHouse getById(Long id) {
        log.info("ENTRY-EXIT - Get UserWareHouse by id");
        return userWareHouseRepository.findById(id).
                orElseThrow(() -> new ServiceException(ServiceErrors.USER_WAREHOUSE_ID_NOT_FOUND.CODE,
                        ServiceErrors.USER_WAREHOUSE_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to get all UserWareHouse
     * @Return List<UserWareHouse>
     */
    @Override
    public List<UserWareHouse> getAllList() {
        log.info("ENTRY-EXIT - List all UserWareHouse");
        return userWareHouseRepository.findAll();
    }

    /*
     * Method to get UserWareHouse for given user
     * @Return Page<UserWareHouse>
     */
    @Override
    public Page<UserWareHouse> fetchAllWareHouseAutoCompleteForUserId(String query, Long userId, String defaultFlag,Long branchId) {
        log.info("ENTRY - Fetch all Warehouse for given User");
        QUserWareHouse qUserWareHouse = QUserWareHouse.userWareHouse;
        Page<UserWareHouse> userWareHousePage = null;
        UserWareHouse firstPrimaryUserWarehouse = null;
        BooleanBuilder predicate = new BooleanBuilder();
        Pageable pageable = PageRequest.of(0, 20, Sort.by("id").ascending());
        predicate.and(qUserWareHouse.userMaster.id.eq(userId));                                                         //filter based on userID
        if (query != null && !query.isEmpty() && !query.isBlank()){                                                     //filter based on code and name
            predicate.and(qUserWareHouse.wareHouseMaster.name.containsIgnoreCase(query)
                    .or(qUserWareHouse.wareHouseMaster.code.containsIgnoreCase(query)));
        }
        if (branchId != null){
            predicate.and(qUserWareHouse.wareHouseMaster.branchMaster.id.eq(branchId));
        }
        userWareHousePage = userWareHouseRepository.findAll(predicate,pageable);
        if (defaultFlag != null && !defaultFlag.isEmpty() && !defaultFlag.isBlank() && defaultFlag.equals("yes")){      //if defaultFlag is "yes" return primary Warehouse
            pageable = Pageable.unpaged();
            userWareHousePage = userWareHouseRepository.findAll(predicate,pageable);
            firstPrimaryUserWarehouse = userWareHousePage.getContent()
                    .stream()
                    .filter(userWareHousePrimary -> userWareHousePrimary.getIsPrimary() != null && userWareHousePrimary.getIsPrimary().equalsIgnoreCase("Yes"))
                    .findFirst()
                    .orElse(null);
        }
        if (firstPrimaryUserWarehouse != null){
            userWareHousePage = new PageImpl<>(Collections.singletonList(firstPrimaryUserWarehouse));
        }
        log.info("EXIT");
        return userWareHousePage;
    }

    /*
     * Method to save new userWareHouse
     * @return userWareHouse
     */
    @Override
    public UserWareHouse saveUserWareHouse(UserWareHouse userWareHouse) {
        log.info("ENTRY - EXIT Save new UserWareHouse");
        return userWareHouseRepository.save(userWareHouse);
    }

    /*
     * Method to update  UserWareHouse
     * @return save updated userWareHouse
     */
    @Override
    public UserWareHouse updateUserWareHouse(UserWareHouse userWareHouse) {
        log.info("ENTRY - EXIT Update UserWareHouse");
        return userWareHouseRepository.save(userWareHouse);
    }

    @Override
    public Boolean userIdCheckForGrn(Long userId) {
        log.info("ENTRY - Fetch All Warehouse for given UserId");
        QUserWareHouse qUserWareHouse= QUserWareHouse.userWareHouse;
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(qUserWareHouse.userMaster.id.eq(userId))
                .and(qUserWareHouse.grnCancel.equalsIgnoreCase("Yes"));
        List<UserWareHouse> userWareHouseList = userWareHouseRepository.findAll(predicate,Pageable.unpaged()).getContent();
        return !userWareHouseList.isEmpty();
    }

    @Override
    public Boolean userIdCheckForAsn(Long userId) {
        log.info("ENTRY - Fetch All Warehouse for given UserId");
        QUserWareHouse  qUserWareHouse = QUserWareHouse.userWareHouse;
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(qUserWareHouse.userMaster.id.eq(userId))
                .and(qUserWareHouse.asnCancel.equalsIgnoreCase("Yes"));
        List<UserWareHouse> userWareHouseList = userWareHouseRepository.findAll(predicate,Pageable.unpaged()).getContent();
        return !userWareHouseList.isEmpty();
    }

}

