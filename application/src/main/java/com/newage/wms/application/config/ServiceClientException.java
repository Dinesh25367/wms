package com.newage.wms.application.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ServiceClientException extends RuntimeException {

    private Integer httpStatus;
    private String errorCode;
    private List<String> errorMessages;

}
