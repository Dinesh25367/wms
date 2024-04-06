package com.newage.wms.application.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TosRequestDTO {

    private String code;

    private String name;

    private String freightPPCC;

    private Collection<String> applicability;

    private String status;

    private String note;

}

