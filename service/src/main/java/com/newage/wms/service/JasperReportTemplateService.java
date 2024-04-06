package com.newage.wms.service;

import com.newage.wms.entity.JasperReportTemplates;

public interface JasperReportTemplateService {

    JasperReportTemplates createTemplate(JasperReportTemplates jasperReportTemplates);

    void updateTemplate(JasperReportTemplates jasperReportTemplates);

    void delete(Long id);

    JasperReportTemplates getTemplatesById(Long id);
}
