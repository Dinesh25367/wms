package com.newage.wms.application.dto.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GlobalDTOMapper<Entity, ResponseDTO> {

    ResponseDTO convertEntityToResponseDTO(Entity entity);
    Page<ResponseDTO> convertEntityPageToResponsePage(Pageable pageRequest, Page<Entity> entity);

}