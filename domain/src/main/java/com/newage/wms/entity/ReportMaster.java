package com.newage.wms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "nxt_report_master")
public class ReportMaster extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "screen",nullable = false)
    private String screen;

    @Column(name = "service",nullable = true)
    private String service;

    @Column(name = "trade",nullable = true)
    private String trade;

    @Column(name = "applicable_status",nullable = true)
    private String status;

    @Column(name = "report_name",nullable = false)
    private String reportName;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    @JoinColumn(name = "template_id", foreignKey = @ForeignKey(name = "FK_JASPER_TEMPLATE_ID"),nullable = false)
    JasperReportTemplates jasperReportTemplate;

    @Column(name = "is_created_by_user", nullable = true, length = 10)
    private String createdByUser;

    @Column(name = "version",nullable = false)
    @Version
    private Long version;

}
