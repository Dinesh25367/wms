package com.newage.wms.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="wms_sku_lot_details",schema = "tenant_default")
public class SkuLotDetails extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "field_name", length = 10)
    private String fieldName;

    @Column(name = "field_label", length = 100)
    private String label;

    @Column(name = "is_mandatory", length = 10)
    private String isMandatory;

}
