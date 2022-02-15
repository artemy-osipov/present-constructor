package io.github.artemy.osipov.shop.service.report

import fr.opensagres.xdocreport.document.IXDocReport
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry
import fr.opensagres.xdocreport.template.IContext
import fr.opensagres.xdocreport.template.TemplateEngineKind
import io.github.artemy.osipov.shop.service.candy.CandyRepository
import io.github.artemy.osipov.shop.service.present.Present
import io.github.artemy.osipov.shop.service.present.PresentRepository
import io.github.artemy.osipov.shop.service.present.PresentRepository.Companion.getById
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.io.ByteArrayOutputStream
import java.util.*
import java.util.stream.Collectors

private const val PUBLIC_REPORT_PATH = "templates/publicReport.docx"
private const val PRIVATE_REPORT_PATH = "templates/privateReport.docx"

@Service
class ReportService(
    private val presentRepository: PresentRepository,
    private val candyRepository: CandyRepository
) {

    fun generatePublicReport(presentId: UUID): Mono<Report> {
        return generateReport(presentId, PUBLIC_REPORT_PATH)
    }

    fun generatePrivateReport(presentId: UUID): Mono<Report> {
        return generateReport(presentId, PRIVATE_REPORT_PATH)
    }

    private fun generateReport(presentId: UUID, templatePath: String): Mono<Report> {
        return fetchPresent(presentId)
            .map { present ->
                Report(
                    formatReportName(present),
                    generateReportContent(present, templatePath)
                )
            }
    }

    private fun formatReportName(present: ReportPresent): String {
        return "${present.name} ${present.price} RUB.docx"
    }

    private fun generateReportContent(present: ReportPresent, templateName: String): ByteArray {
        javaClass.classLoader.getResourceAsStream(templateName).use {
            val report: IXDocReport = XDocReportRegistry.getRegistry()
                .loadReport(it, templateName, TemplateEngineKind.Freemarker)

            val context: IContext = report.createContext()
            context.put("present", present)
            context.put("costPrice", present.computeCost())

            val bout = ByteArrayOutputStream()
            report.process(context, bout)

            return bout.toByteArray()
        }
    }

    private fun fetchPresent(id: UUID): Mono<ReportPresent> {
        val present = presentRepository.getById(id)
        val candyIds = present.flatMapIterable {
            p -> p.items
            .stream()
            .map(Present.Item::candyId)
            .collect(Collectors.toSet())
        }
        val candies = candyRepository.findAllById(candyIds).collectList()

        return Mono.zip(present, candies) {
            p, cs -> ReportPresent(p, cs)
        }
    }
}
