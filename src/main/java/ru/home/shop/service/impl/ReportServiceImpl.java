package ru.home.shop.service.impl;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import org.springframework.stereotype.Service;
import ru.home.shop.domain.model.Present;
import ru.home.shop.domain.model.Report;
import ru.home.shop.service.ReportService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

@Service
public class ReportServiceImpl implements ReportService {

    private final static String PUBLIC_REPORT_PATH = "templates/publicReport.docx";
    private final static String PRIVATE_REPORT_PATH = "templates/privateReport.docx";

    @Override
    public Report generatePublicReport(Present present) {
        return generateReport(present, PUBLIC_REPORT_PATH);
    }

    @Override
    public Report generatePrivateReport(Present present) {
        return generateReport(present, PRIVATE_REPORT_PATH);
    }

    private Report generateReport(Present present, String templatePath) {
        String name = formatReportName(present);
        byte[] content = generateReportContent(present, templatePath);

        return new Report(name, content);
    }

    private String formatReportName(Present present) {
        return String.format("%s %s RUB.docx", present.getName(), present.getPrice());
    }

    private byte[] generateReportContent(Present present, String templateName) {
        try (InputStream templateStream = getClass().getClassLoader().getResourceAsStream(templateName)) {
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(templateStream, TemplateEngineKind.Freemarker);

            IContext context = report.createContext();
            context.put("present", present);
            context.put("costPrice", computeCostPrice(present));

            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            report.process(context, bout);

            return bout.toByteArray();
        } catch (XDocReportException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    BigDecimal computeCostPrice(Present present) {
        return present.getItems().stream()
                .map(c -> c.getPrice().multiply(BigDecimal.valueOf(c.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

