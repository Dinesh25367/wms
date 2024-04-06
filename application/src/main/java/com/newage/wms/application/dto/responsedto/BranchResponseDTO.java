package com.newage.wms.application.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchResponseDTO {

    private Long id;
    private String name;
    private String code;
    private String reportingName;
    private CountryMasterDTO country;
    private StateMasterDTO state;
    private CityMasterDTO city;
    private ZoneMasterDTO zone;
    private AgentMasterDTO agent;
    private CompanyMasterDTO company;
    private CurrencyMasterDTO currency;
    private TimeZoneMasterDTO timeZone;
    private String dateFormat;
    private String status;
    private String gstNumber;
    private String vatNumber;
    private String logo;
    private String logoUrl;
    private List<String> modeList;
    private List<BranchAddressResponseDTO> branchAddressMasterList;
    private List<BranchPortResponseDTO> branchPort;

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
    public static class CityMasterDTO {

        private Long id;
        private String name;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ZoneMasterDTO {

        private Long id;
        private String code;
        private String name;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CurrencyMasterDTO {

        private Long id;
        private String code;
        private String name;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AgentMasterDTO {

        private Long id;
        private String code;
        private String name;
        private String nameCode;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CompanyMasterDTO {

        private Long id;
        private String name;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TimeZoneMasterDTO {

        private Long id;
        private String countryCode;
        private String zoneName;
        private String abbreviation;
        private String std;
        private String dst;

    }

}
