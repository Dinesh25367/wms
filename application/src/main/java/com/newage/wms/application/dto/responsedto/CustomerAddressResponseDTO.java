package com.newage.wms.application.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAddressResponseDTO {

    private Long id;

    private AddressTypeDTO addressType;

    private boolean corporateAddress;

    private boolean communicationAddress;

    private String streetName;

    private String poBoxNo;

    private String buildingNoName;

    private String landmark;

    private Double latitude;

    private Double longitude;

    private String locationName;

    private CityMasterDTO city;

    private StateMasterDTO state;

    private CountryMasterDTO country;

    private String contactPerson;

    private List<MobileNumberDTO> mobileNumbers;

    private List<PhoneNumberDTO> phoneNumbers;

    private List<String> email;

    private CustomerMasterDTO customer;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerMasterDTO {

        private Long id;
        private String code;
        private String name;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddressTypeDTO {

        private Long id;
        private String addressType;
        private String status;

    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CityMasterDTO {

        private Long id;
        private String name;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StateMasterDTO {

        private Long id;
        private String code;
        private String name;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CountryMasterDTO {

        private Long id;
        private String code;
        private String name;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MobileNumberDTO {

        private String mobilePrefix;
        private String mobileNumber;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PhoneNumberDTO {

        private String phonePrefix;
        private String phoneNumber;

    }

}
