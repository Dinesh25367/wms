package com.newage.wms.application.dto.requestdto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;
import java.util.Date;

@Getter
@Setter
@Component
@ToString
public class DateAndTimeRequestDto {

    private Date createdDate;
    private Date lastModifiedDate;
    private String createdBy;
    private String lastModifiedBy;
    private Long version;

}
