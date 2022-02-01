package io.github.artemy.osipov.shop.service.report;

import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import io.github.artemy.osipov.shop.service.present.Present;
import io.github.artemy.osipov.shop.service.present.PresentService;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportService {

    private static final String PUBLIC_REPORT_PATH = "templates/publicReport.docx";
    private static final String PRIVATE_REPORT_PATH = "templates/privateReport.docx";

    private final PresentService presentService;

    public Report generatePublicReport(UUID presentId) {
        return generateReport(presentId, PUBLIC_REPORT_PATH);
    }

    public Report generatePrivateReport(UUID presentId) {
        return generateReport(presentId, PRIVATE_REPORT_PATH);
    }

    private Report generateReport(UUID presentId, String templatePath) {
        Present present = presentService.getPresent(presentId);

        String name = formatReportName(present);
        byte[] content = generateReportContent(present, templatePath);

        return new Report(name, content);
    }

    private String formatReportName(Present present) {
        return String.format("%s %s RUB.docx", present.getName(), present.getPrice());
    }

    @SneakyThrows
    private byte[] generateReportContent(Present present, String templateName) {
        try (InputStream templateStream = getClass().getClassLoader().getResourceAsStream(templateName)) {
            IXDocReport report = XDocReportRegistry.getRegistry()
                    .loadReport(templateStream, TemplateEngineKind.Freemarker);

            IContext context = report.createContext();
            context.put("present", present);
            context.put("costPrice", present.computeCost());

            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            report.process(context, bout);

            return bout.toByteArray();
        }
    }
}
