package com.newage.wms.application.dto.responsedto;

import com.newage.wms.entity.Location;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import java.util.List;

@Getter
@Setter
@Component
public class LocationBulkResponseDTO {

    List<Location> locationList;
    List<String> errorList;

}

