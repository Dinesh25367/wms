package com.newage.wms.application.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchRequestDTO {

    private String name;
    private String code;
    private String reportingName;
    private Long country;
    private Long state;
    private Long city;
    private Long zone;
    private Long currency;
    private Long agent;
    private Long timeZone;
    private String dateFormat;
    private String status;
    private String gstNumber;
    private String vatNumber;
    private String logo;
    private List<String> modeList;
    List<BranchAddressRequestDTO> branchAddressMasterList;
    List<BranchPortRequestDTO> branchPort;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BranchPortRequestDTO {

        private Long id;
        private Long portId;

    }

}
