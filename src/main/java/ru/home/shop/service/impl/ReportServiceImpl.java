package ru.home.shop.service.impl;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import org.springframework.stereotype.Service;
import ru.home.shop.domain.bean.PresentBean;
import ru.home.shop.service.ReportService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ReportServiceImpl implements ReportService {

    @Override
    public byte[] publicReport(PresentBean present) {
        return generatePresentReport(present, "templates/publicReport.docx");
    }

    @Override
    public byte[] privateReport(PresentBean present) {
        return generatePresentReport(present, "templates/privateReport.docx");
    }

    private byte[] generatePresentReport(PresentBean present, String reportName) {
        if (present == null) {
            throw new IllegalArgumentException();
        }

        try {
            InputStream in = getClass().getClassLoader().getResourceAsStream(reportName);
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Freemarker);


            IContext context = report.createContext();
            context.put("present", present);

            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            report.process(context, bout);

            return bout.toByteArray();
        } catch (XDocReportException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

