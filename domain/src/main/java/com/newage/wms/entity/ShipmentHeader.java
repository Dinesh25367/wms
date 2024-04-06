package com.newage.wms.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "shipment_header",schema = "tenant_default")
public class ShipmentHeader extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_company_id", length = 20)
    private Long groupCompanyId;

    @Column(name = "company_id", length = 20)
    private Long companyId;

    @Column(name = "branch_id", length = 20)
    private Long branchId;

    @Column(name = "shipment_uid", length = 100,nullable = false)
    private String shipmentUid;

    @Column(name = "shipment_date", nullable = false)
    private Date shipmentDate;

    @Column(name = "status", nullable = false, length = 10)
    private String status;

    @Column(name = "customer_id", length = 100,nullable = false)
    private Long customerId;

    @Column(name = "customer_name", length = 20)
    private String customerName;

    @Column(name = "routed", nullable = true, length = 5)
    private String routed;

    @Column(name = "routed_by_id", length = 20)
    private Long routedById;

    @Column(name = "shipment_transport_mode_id", length = 20)
    private String serviceCode;

    @Column(name = "service_type", length = 20)
    private String serviceType;

    @Column(name = "shipment_trade_id", nullable = false, length = 20)
    private Long tradeCode;

    /* Origin Port Id */

    @Column(name = "origin_id", length = 20)
    private Long originId;

    @Column(name = "origin_name", length = 20)
    private String originName;

    /* destination Port Id */

    @Column(name = "destination_id", nullable = false, length = 20)
    private Long destinationId;

    @Column(name = "destination_name", length = 20)
    private String destinationName;

    /* Term of shipment Id */
    @Column(name = "tos_id", nullable = false, length = 20)
    private Long tosId;

    @Column(name = "tos_name", nullable = false, length = 20)
    private String tosName;

    @Column(name = "freight_term", nullable = false, length = 7)
    private String freight;

    @Column(name = "creation_mode", nullable = false, length = 7)
    private String creationMode;

    @Column(name = "business_type", nullable = false, length = 7)
    private String businessType;

    @Column(name = "direct", nullable = false, length = 7)
    private String direct;

    @Column(name = "division_id", nullable = false, length = 7)
    private Long divisionId;

    @Column(name = "note")
    private String notes;

    @Version
    @Column(name = "version")
    private Long version;

}
