package com.newage.wms.service.impl;

import com.newage.wms.entity.AuthUserProfile;
import com.newage.wms.repository.AuthUserProfileRepository;
import com.newage.wms.service.AuthUserProfileService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class AuthUserProfileServiceImpl implements AuthUserProfileService {

    @Autowired
    private AuthUserProfileRepository authUserProfileRepository;

    /*
     * Method to get AuthUserProfile by id
     * @return AuthUserProfile
     */
    @Override
    public AuthUserProfile getById(Long id) {
        log.info("ENTRY-EXIT - Get AuthUserProfile by id");
        return authUserProfileRepository.findById(id).
                orElseThrow(() -> new ServiceException(
                        ServiceErrors.USER_ID_NOT_FOUND.CODE,
                        ServiceErrors.USER_ID_NOT_FOUND.KEY));
    }

    /*
     * Method to get AuthUserProfile for AutoComplete
     * @Return AuthUserProfile
     */
    @Override
    public Iterable<AuthUserProfile> getAllAutoComplete(Predicate predicate, Pageable pageable) {
        log.info("ENTRY-EXIT - Get all AuthUserProfile for AutoComplete");
        return authUserProfileRepository.findAll(predicate,pageable);
    }

}

