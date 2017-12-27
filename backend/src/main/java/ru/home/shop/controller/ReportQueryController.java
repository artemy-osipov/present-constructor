package ru.home.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.home.shop.domain.Report;
import ru.home.shop.exception.EntityNotFoundException;
import ru.home.shop.domain.Present;
import ru.home.shop.service.command.present.PresentRepository;
import ru.home.shop.service.ReportService;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.function.Function;

@RestController
@RequestMapping("/api/presents")
public class ReportQueryController {

    private final PresentRepository repository;
    private final ReportService reportService;

    @Autowired
    public ReportQueryController(PresentRepository repository, ReportService reportService) {
        this.repository = repository;
        this.reportService = reportService;
    }

    @GetMapping(value = "/{id}/public-report")
    public ResponseEntity<byte[]> publicReport(@PathVariable("id") UUID id) {
        return report(id, reportService::generatePublicReport);
    }

    @GetMapping(value = "/{id}/private-report")
    public ResponseEntity<byte[]> privateReport(@PathVariable("id") UUID id) {
        return report(id, reportService::generatePrivateReport);
    }

    private ResponseEntity<byte[]> report(UUID id, Function<Present, Report> makeReport) {
        Present present = repository.findOne(id);

        if (present == null) {
            throw new EntityNotFoundException();
        }

        Report report = makeReport.apply(present);

        return toDocumentEntity(report);
    }

    private ResponseEntity<byte[]> toDocumentEntity(Report report) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
        headers.setContentDispositionFormData("attachment", report.getName(), StandardCharsets.UTF_8);

        return new ResponseEntity<>(report.getContent(), headers, HttpStatus.OK);
    }
}
