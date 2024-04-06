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
@Table(name="wms_trn_detail_addl_detail",schema = "tenant_default")
public class TrnDetailAddlDetail extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "transaction_sl_no", length = 5, nullable = false)
    private Integer transactionSlNo;

    @Column(name = "field_name",length = 100)
    private String fieldName;

    @Column(name = "transaction_type",length = 30)
    private String transactionType;

    @Column(name="field_date_type",nullable = false)
    private String fieldDataType;

    @Column(name = "char_value",length = 100)
    private String charValue;

    @Column(name = "date_value")
    private Date dateValue;

    @Column(name = "number_value")
    private Double numberValue;

}
