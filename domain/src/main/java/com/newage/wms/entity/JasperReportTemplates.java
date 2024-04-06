package com.newage.wms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "nxt_jasper_report_templates")
public class JasperReportTemplates extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "report_category",nullable = false)
    private String reportCategory;

    @Column(name = "template_name",nullable = false)
    private String templateName;

    @Column(name = "file_name",nullable = false)
    private String fileName;

    @NotNull
    @Column(name = "file",nullable = false)
    private byte[] file;

    @Column(name = "version",nullable = false)
    @Version
    private Long version;

}
