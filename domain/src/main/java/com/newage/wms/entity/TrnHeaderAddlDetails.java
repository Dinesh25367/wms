package com.newage.wms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="wms_trn_header_addl_details",schema = "tenant_default")
public class TrnHeaderAddlDetails extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "field_name",length = 100,nullable = false)
    private String fieldName;

    @Column(name="transaction_type",length = 30,nullable = false)
    private String transactionType;

    @Column(name="field_date_type",nullable = false)
    private String fieldDataType;

    @Column(name="is_mandatory",nullable = false)
    private String isMandatory;

    @Column(name = "char_value",length = 100)
    private String charValue;

    @Column(name = "date_value")
    private Date dateValue;

    @Column(name = "number_value")
    private Double numberValue;


    @ManyToOne
    @JoinColumn(name = "customer_configuration_id", foreignKey = @ForeignKey(name = "FK_CUSTOMER_CONFIGURATION_ID"))
    private CustomerConfigurationMaster customerConfigurationMaster;

}
