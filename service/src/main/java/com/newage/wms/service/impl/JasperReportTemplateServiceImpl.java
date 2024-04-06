package com.newage.wms.service.impl;

import com.newage.wms.entity.JasperReportTemplates;
import com.newage.wms.repository.JasperReportTemplatesRepository;
import com.newage.wms.service.JasperReportTemplateService;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class JasperReportTemplateServiceImpl implements JasperReportTemplateService {

    @Autowired
    JasperReportTemplatesRepository jasperReportTemplatesRepository;

    @Override
    public JasperReportTemplates createTemplate(JasperReportTemplates jasperReportTemplates) {
        return jasperReportTemplatesRepository.save(jasperReportTemplates);
    }

    @Override
    public void updateTemplate(JasperReportTemplates jasperReportTemplates) {
        jasperReportTemplatesRepository.save(jasperReportTemplates);
    }

    @Override
    public void delete(Long id) {
        jasperReportTemplatesRepository.deleteById(id);
    }

    @Override
    public JasperReportTemplates getTemplatesById(Long id) {
        return jasperReportTemplatesRepository.findById(id).
                orElseThrow(() -> new ServiceException(ServiceErrors.REPORT_TEMPLATE_DOES_NOT_EXIST.CODE,
                        ServiceErrors.REPORT_TEMPLATE_DOES_NOT_EXIST.KEY));
    }
}

