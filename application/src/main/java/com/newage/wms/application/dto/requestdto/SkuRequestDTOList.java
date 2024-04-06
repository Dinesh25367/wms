package com.newage.wms.application.dto.requestdto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import javax.validation.Valid;
import java.util.List;

@Getter
@Setter
@Component
public class SkuRequestDTOList {

    @Valid
    private List<SkuBulkDTO> skuBulkDTOList;

}
