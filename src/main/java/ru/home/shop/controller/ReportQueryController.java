package ru.home.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;
import ru.home.shop.domain.model.Present;
import ru.home.shop.domain.model.Report;
import ru.home.shop.exception.EntityNotFoundException;
import ru.home.shop.service.PresentService;
import ru.home.shop.service.ReportService;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.function.Function;

@RestController
@RequestMapping("/presents")
public class ReportQueryController {

    private final PresentService presentService;
    private final ReportService reportService;

    @Autowired
    public ReportQueryController(PresentService presentService, ReportService reportService) {
        this.presentService = presentService;
        this.reportService = reportService;
    }

    @RequestMapping(value = "/{id}/public-report", method = RequestMethod.GET)
    public ResponseEntity<byte[]> publicReport(@PathVariable("id") UUID id) {
        return report(id, reportService::publicReport);
    }

    @RequestMapping(value = "/{id}/private-report", method = RequestMethod.GET)
    public ResponseEntity<byte[]> privateReport(@PathVariable("id") UUID id) {
        return report(id, reportService::privateReport);
    }

    private ResponseEntity<byte[]> report(UUID id, Function<Present, Report> makeReport) {
        Present present = presentService.find(id);

        if (present == null) {
            throw new EntityNotFoundException();
        }

        Report report = makeReport.apply(present);

        return toDocumentEntity(report);
    }

    private ResponseEntity<byte[]> toDocumentEntity(Report report) {
        String fileName;
        try {
            fileName = UriUtils.encode(report.getName(), StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        headers.set("Content-Disposition", "attachment; filename*=UTF-8''" + fileName);

        return new ResponseEntity<>(report.getContent(), headers, HttpStatus.OK);
    }
}
