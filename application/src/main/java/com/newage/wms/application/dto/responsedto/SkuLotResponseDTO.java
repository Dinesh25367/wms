package com.newage.wms.application.dto.responsedto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Component

public class SkuLotResponseDTO {
    private Long id;
    private String name;
    private String label;
    private Boolean isMandatory;


}

