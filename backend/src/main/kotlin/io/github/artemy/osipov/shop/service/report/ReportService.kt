package io.github.artemy.osipov.shop.service.report

import fr.opensagres.xdocreport.document.IXDocReport
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry
import fr.opensagres.xdocreport.template.IContext
import fr.opensagres.xdocreport.template.TemplateEngineKind
import io.github.artemy.osipov.shop.service.candy.CandyRepository
import io.github.artemy.osipov.shop.service.present.Present
import io.github.artemy.osipov.shop.service.present.PresentRepository
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
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

    suspend fun generatePublicReport(presentId: UUID): Report {
        return generateReport(presentId, PUBLIC_REPORT_PATH)
    }

    suspend fun generatePrivateReport(presentId: UUID): Report {
        return generateReport(presentId, PRIVATE_REPORT_PATH)
    }

    private suspend fun generateReport(presentId: UUID, templatePath: String): Report {
        val present = fetchPresent(presentId)
        return Report(
            formatReportName(present),
            generateReportContent(present, templatePath)
        )
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

    private suspend fun fetchPresent(id: UUID): ReportPresent {
        val present =  presentRepository.findById(id)
        val candyIds = present.items
            .stream()
            .map(Present.Item::candyId)
            .collect(Collectors.toSet())
        val candies = candyRepository.findAllById(candyIds).toList()

        return ReportPresent(present, candies)
    }
}
