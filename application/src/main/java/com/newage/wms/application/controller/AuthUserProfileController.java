package com.newage.wms.application.controller;

import com.newage.wms.application.dto.mapper.AuthUserProfileMapper;
import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.entity.AuthUserProfile;
import com.newage.wms.entity.QAuthUserProfile;
import com.newage.wms.service.AuthUserProfileService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping(value = "/authUserProfile")
@CrossOrigin("*")
public class AuthUserProfileController {

    @Autowired
    private AuthUserProfileService authUserProfileService;

    @Autowired
    private AuthUserProfileMapper authUserProfileMapper;

    /*
     * Method to fetch all MovementTypeMaster for autocomplete
     */
    @GetMapping("/autoComplete")
    public ResponseEntity<ResponseDTO> fetchAllAutoComplete(@RequestParam(name = "query",required = false) String query,
                                                            @QuerydslPredicate(root= AuthUserProfile.class) Predicate predicate){
        log.info("ENTRY - Fetch all MovementTypeMaster for autocomplete");
        BooleanBuilder combinedPredicate = new BooleanBuilder();
        if (query != null && !query.isEmpty() && !query.isBlank()){
            BooleanExpression nameOrCodeExpression = QAuthUserProfile.authUserProfile.userName.containsIgnoreCase(query);
            combinedPredicate.and(predicate)
                    .and(nameOrCodeExpression);
        }else {
            combinedPredicate.and(predicate);
        }
        Pageable pageable = PageRequest.of(0, 20, Sort.by("userName").ascending());
        Iterable<AuthUserProfile> authUserProfileIterable = authUserProfileService.getAllAutoComplete(combinedPredicate,pageable);
        Iterable<TrnResponseDTO.TrnDetailDTO.MoreQCFormDTO.AuthUserProfileDTO> authUserProfileDTOIterable = authUserProfileMapper.convertEntityIterableToAutoCompleteResponseIterable(authUserProfileIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,authUserProfileDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

    /*
     * Method to fetch all MovementTypeMaster for autocomplete
     */
    @GetMapping("/asn/bulkSave")
    public ResponseEntity<ResponseDTO> fetchAllAsnBulk(){
        log.info("ENTRY - Fetch all MovementTypeMaster for autocomplete");
        BooleanBuilder combinedPredicate = new BooleanBuilder();
        BooleanExpression nameOrCodeExpression = QAuthUserProfile.authUserProfile.id.isNotNull();
        combinedPredicate.and(nameOrCodeExpression);
        Pageable pageable = Pageable.unpaged();
        Iterable<AuthUserProfile> authUserProfileIterable = authUserProfileService.getAllAutoComplete(combinedPredicate,pageable);
        Iterable<TrnResponseDTO.TrnDetailDTO.MoreQCFormDTO.AuthUserProfileDTO> authUserProfileDTOIterable = authUserProfileMapper.convertEntityIterableToAutoCompleteResponseIterable(authUserProfileIterable);
        ResponseDTO responseDto = new ResponseDTO(HttpStatus.OK.value(), Boolean.TRUE,authUserProfileDTOIterable,null);
        log.info("EXIT");
        return ResponseEntity.ok(responseDto);
    }

}
