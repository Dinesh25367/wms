package com.newage.wms.application.dto.responsedto;

import com.newage.wms.entity.SkuMaster;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import java.util.List;

@Getter
@Setter
@Component
public class SkuBulkResponseDTO {

    List<SkuMaster> skuList;
    List<String> errorList;

}
