package com.newage.wms.application.dto.requestdto;

import lombok.*;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WareHouseContactDetailRequestDTO {

    private Date createdDate;
    private Date updatedDate;
    private String createdUser;
    private String updatedUser;
    private Long version;
    private List<CustomerContactRequestDTOWareHouse> customerContactMasterList;

}
