package ru.home.shop.service;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.home.shop.domain.Report;
import ru.home.shop.domain.Present;
import ru.home.shop.service.command.present.PresentRepository;

import javax.persistence.EntityNotFoundException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportService {

    private static final String PUBLIC_REPORT_PATH = "templates/publicReport.docx";
    private static final String PRIVATE_REPORT_PATH = "templates/privateReport.docx";

    private final PresentRepository presentRepository;

    public Report generatePublicReport(UUID presentId) {
        return generateReport(presentId, PUBLIC_REPORT_PATH);
    }

    public Report generatePrivateReport(UUID presentId) {
        return generateReport(presentId, PRIVATE_REPORT_PATH);
    }

    private Report generateReport(UUID presentId, String templatePath) {
        Present present = presentRepository.findById(presentId)
                .orElseThrow(EntityNotFoundException::new);

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
            context.put("costPrice", present.computeCost());

            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            report.process(context, bout);

            return bout.toByteArray();
        } catch (XDocReportException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
