package com.newage.wms.application.dto.responsedto;

import com.newage.wms.application.dto.requestdto.CustomerContactRequestDTOWareHouse;
import lombok.*;
import org.springframework.stereotype.Component;
import java.util.List;

@Getter
@Setter
@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerContactResponseDtoWareHouse {

    private String contactPerson;
    private DesignationResponseDTO designation;
    private List<CustomerContactRequestDTOWareHouse.PhoneDTO> phoneList;
    private List<CustomerContactRequestDTOWareHouse.MobileDTO> mobileList;
    private List<CustomerContactRequestDTOWareHouse.EmailDTO> emailList;
    private CityMasterDTO city;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CityMasterDTO {

        private Long id;
        private String name;

    }

}
