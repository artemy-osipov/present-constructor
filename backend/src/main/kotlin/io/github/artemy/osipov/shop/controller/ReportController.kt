package io.github.artemy.osipov.shop.controller

import io.github.artemy.osipov.shop.service.report.Report
import io.github.artemy.osipov.shop.service.report.ReportService
import org.springframework.http.ContentDisposition
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.nio.charset.StandardCharsets
import java.util.*

@RestController
@RequestMapping("/api/presents")
class ReportController(
    private val reportService: ReportService
) {
    @GetMapping("/{id}/public-report")
    fun publicReport(@PathVariable("id") id: UUID): ResponseEntity<ByteArray> {
        return toDocumentEntity(
            reportService.generatePublicReport(id)
        )
    }

    @GetMapping("/{id}/private-report")
    fun privateReport(@PathVariable("id") id: UUID): ResponseEntity<ByteArray> {
        return toDocumentEntity(
            reportService.generatePrivateReport(id)
        )
    }

    private fun toDocumentEntity(report: Report): ResponseEntity<ByteArray> {
        val headers = HttpHeaders().apply {
            contentType =
                MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
            setContentDispositionFormData("attachment", report.name)
            contentDisposition = ContentDisposition.builder("form-data")
                .name("attachment")
                .filename(report.name, StandardCharsets.UTF_8)
                .build()
        }
        return ResponseEntity(report.content, headers, HttpStatus.OK)
    }
}