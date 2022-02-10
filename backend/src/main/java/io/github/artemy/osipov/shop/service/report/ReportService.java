package io.github.artemy.osipov.shop.service.report;

import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import io.github.artemy.osipov.shop.service.candy.Candy;
import io.github.artemy.osipov.shop.service.candy.CandyRepository;
import io.github.artemy.osipov.shop.service.present.Present;
import io.github.artemy.osipov.shop.service.present.PresentRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private static final String PUBLIC_REPORT_PATH = "templates/publicReport.docx";
    private static final String PRIVATE_REPORT_PATH = "templates/privateReport.docx";

    private final PresentRepository presentRepository;
    private final CandyRepository candyRepository;

    public Report generatePublicReport(UUID presentId) {
        return generateReport(presentId, PUBLIC_REPORT_PATH);
    }

    public Report generatePrivateReport(UUID presentId) {
        return generateReport(presentId, PRIVATE_REPORT_PATH);
    }

    private Report generateReport(UUID presentId, String templatePath) {
        ReportPresent present = fetchPresent(presentId);
        return new Report(
                formatReportName(present),
                generateReportContent(present, templatePath)
        );
    }

    private String formatReportName(ReportPresent present) {
        return String.format("%s %s RUB.docx", present.getName(), present.getPrice());
    }

    @SneakyThrows
    private byte[] generateReportContent(ReportPresent present, String templateName) {
        try (InputStream templateStream = getClass().getClassLoader().getResourceAsStream(templateName)) {
            IXDocReport report = XDocReportRegistry.getRegistry()
                    .loadReport(templateStream, templateName, TemplateEngineKind.Freemarker);

            IContext context = report.createContext();
            context.put("present", present);
            context.put("costPrice", present.computeCost());

            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            report.process(context, bout);

            return bout.toByteArray();
        }
    }

    private ReportPresent fetchPresent(UUID id) {
        Present present = presentRepository.getById(id);
        Set<UUID> candyIds = present.getItems()
                .stream()
                .map(Present.Item::getCandyId)
                .collect(Collectors.toSet());

        List<Candy> candies = new ArrayList<>();
        candyRepository.findAllById(candyIds)
                .forEach(candies::add);

        return new ReportPresent(present, candies);
    }
}
