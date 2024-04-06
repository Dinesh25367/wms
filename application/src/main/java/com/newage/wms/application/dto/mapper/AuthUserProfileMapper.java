package com.newage.wms.application.dto.mapper;

import com.newage.wms.application.dto.responsedto.TrnResponseDTO;
import com.newage.wms.entity.AuthUserProfile;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@Component
public class AuthUserProfileMapper {

    @Autowired
    private ModelMapper modelMapper;

    /*
     * Method to convert AuthUserProfile Iterable to AuthUserProfile AutoCompleteDTO Iterable
     * @Return Iterable<TrnResponseDTO.TrnDetailDTO.MoreQCFormDTO.AuthUserProfileDTO>
     */
    public Iterable<TrnResponseDTO.TrnDetailDTO.MoreQCFormDTO.AuthUserProfileDTO> convertEntityIterableToAutoCompleteResponseIterable(Iterable<AuthUserProfile> authUserProfileIterable){
        log.info("ENTRY-EXIT - AuthUserProfile Iterable to AuthUserProfile autoComplete Iterable mapper");
        return StreamSupport.stream(authUserProfileIterable.spliterator(),false)
                .map(this::convertEntityToAutoCompleteDTO)
                .collect(Collectors.toList());
    }

    /*
     * Method to convert AuthUserProfile to AuthUserProfile AutoCompleteDTO
     * @Return TrnResponseDTO.TrnDetailDTO.MoreQCFormDTO.AuthUserProfileDTO
     */
    private TrnResponseDTO.TrnDetailDTO.MoreQCFormDTO.AuthUserProfileDTO convertEntityToAutoCompleteDTO(AuthUserProfile authUserProfile) {
        log.info("ENTRY-EXIT - AuthUserProfile to AuthUserProfile AutoCompleteDTO");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);                                  // Ensure strict matching
        return modelMapper.map(authUserProfile, TrnResponseDTO.TrnDetailDTO.MoreQCFormDTO.AuthUserProfileDTO.class);
    }

}

