package ru.home.shop.service;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import org.springframework.stereotype.Service;
import ru.home.shop.domain.Report;
import ru.home.shop.query.present.PresentEntry;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ReportService {

    private final static String PUBLIC_REPORT_PATH = "templates/publicReport.docx";
    private final static String PRIVATE_REPORT_PATH = "templates/privateReport.docx";

    public Report generatePublicReport(PresentEntry present) {
        return generateReport(present, PUBLIC_REPORT_PATH);
    }

    public Report generatePrivateReport(PresentEntry present) {
        return generateReport(present, PRIVATE_REPORT_PATH);
    }

    private Report generateReport(PresentEntry present, String templatePath) {
        String name = formatReportName(present);
        byte[] content = generateReportContent(present, templatePath);

        return new Report(name, content);
    }

    private String formatReportName(PresentEntry present) {
        return String.format("%s %s RUB.docx", present.getName(), present.getPrice());
    }

    private byte[] generateReportContent(PresentEntry present, String templateName) {
        try (InputStream templateStream = getClass().getClassLoader().getResourceAsStream(templateName)) {
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(templateStream, TemplateEngineKind.Freemarker);

            IContext context = report.createContext();
            context.put("present", present);
            context.put("costPrice", present.computeCost());

            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            report.process(context, bout);

            return bout.toByteArray();
        } catch (XDocReportException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
