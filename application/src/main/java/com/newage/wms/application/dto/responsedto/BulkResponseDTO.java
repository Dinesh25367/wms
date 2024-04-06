package com.newage.wms.application.dto.responsedto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import java.util.List;

@Getter
@Setter
@Component
public class BulkResponseDTO {

    private Integer rowNumber;
    private List<String> errorList;

}

