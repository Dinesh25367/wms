package com.newage.wms.service;

import com.newage.wms.entity.AuthUserProfile;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;

public interface AuthUserProfileService {

    AuthUserProfile getById(Long id);

    Iterable<AuthUserProfile> getAllAutoComplete(Predicate predicate, Pageable pageable);

}
