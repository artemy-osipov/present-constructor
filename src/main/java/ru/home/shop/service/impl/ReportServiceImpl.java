package ru.home.shop.service.impl;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import org.springframework.stereotype.Service;
import ru.home.shop.domain.bean.PresentBean;
import ru.home.shop.domain.bean.Report;
import ru.home.shop.service.ReportService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

@Service
public class ReportServiceImpl implements ReportService {

    @Override
    public Report publicReport(PresentBean present) {
        return generateReport(present, "templates/publicReport.docx");
    }

    @Override
    public Report privateReport(PresentBean present) {
        return generateReport(present, "templates/privateReport.docx");
    }

    private Report generateReport(PresentBean present, String templateName) {
        if (present == null) {
            throw new IllegalArgumentException();
        }

        String name = formatReportName(present);
        byte[] content = generateReportContent(present, templateName);

        return new Report(name, content);
    }

    private String formatReportName(PresentBean present) {
        return String.format("%s %s RUB.docx", present.getName(), present.getPrice());
    }

    private byte[] generateReportContent(PresentBean present, String templateName) {
        try {
            InputStream in = getClass().getClassLoader().getResourceAsStream(templateName);
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Freemarker);


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

    BigDecimal computeCostPrice(PresentBean present) {
        if (present.getCandies() == null) {
            return BigDecimal.ZERO;
        } else {
            return present.getCandies().stream()
                    .map(c ->
                            (c.getPrice() == null ? BigDecimal.ZERO : c.getPrice())
                                    .multiply(BigDecimal.valueOf(c.getCount())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }
}

