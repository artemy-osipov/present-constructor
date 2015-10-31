package ru.home.shop.service.impl;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.shop.domain.bean.PresentBean;
import ru.home.shop.service.PresentService;
import ru.home.shop.service.ReportService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ReportServiceImpl implements ReportService {

    private PresentService presentService;

    @Autowired
    public ReportServiceImpl(PresentService presentService) {
        this.presentService = presentService;
    }

    @Override
    public byte[] publicReport(int presentId) throws IOException {
        return generatePresentReport(presentId, "templates/publicReport.docx");
    }

    @Override
    public byte[] privateReport(int presentId) throws IOException {
        return generatePresentReport(presentId, "templates/privateReport.docx");
    }

    private byte[] generatePresentReport(int presentId, String reportName) throws IOException {
        try {
            PresentBean present = presentService.find(presentId);

            if (present == null) {
                throw new IllegalArgumentException("unknown id");
            }

            InputStream in = getClass().getClassLoader().getResourceAsStream(reportName);
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Freemarker);


            IContext context = report.createContext();
            context.put("present", present);

            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            report.process(context, bout);

            return bout.toByteArray();
        } catch (XDocReportException e) {
            throw new IOException(e);
        }
    }
}

