package com.newage.wms.application.dto.responsedto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TenantResponseDTO {

    private String tenantId;
    private String schema;
    private String aws_key;
    private String aws_secret;
    private String aws_region;
    private String userpool_id;
    private String client_id;
    private String aws_account_id;
    private String aws_iam_admin_uname;
    private String aws_iam_admin_password;
    private String db_url;
    private String db_user;
    private String db_password;
    private String saasId;
    private String alfrescoDirectoryPath;
    private String alfrescoUser;
    private String alfrescoPassword;
    private boolean ipWhitelisting;
    private String issuerUri;

}
