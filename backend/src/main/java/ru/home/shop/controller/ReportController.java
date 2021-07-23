package ru.home.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.home.shop.service.report.Report;
import ru.home.shop.service.report.ReportService;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RestController
@RequestMapping("/api/presents")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping(value = "/{id}/public-report")
    public ResponseEntity<byte[]> publicReport(@PathVariable("id") UUID id) {
        return toDocumentEntity(
                reportService.generatePublicReport(id)
        );
    }

    @GetMapping(value = "/{id}/private-report")
    public ResponseEntity<byte[]> privateReport(@PathVariable("id") UUID id) {
        return toDocumentEntity(
                reportService.generatePrivateReport(id)
        );
    }

    private ResponseEntity<byte[]> toDocumentEntity(Report report) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
        headers.setContentDispositionFormData("attachment", report.getName());
        headers.setContentDisposition(ContentDisposition.builder("form-data")
                .name("attachment")
                .filename(report.getName(), StandardCharsets.UTF_8)
                .build());

        return new ResponseEntity<>(report.getContent(), headers, HttpStatus.OK);
    }
}
