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

private fun createXDocReport(templatePath: String): IXDocReport {
    ReportService::class.java.classLoader.getResourceAsStream(templatePath).use {
        return XDocReportRegistry
            .getRegistry()
            .loadReport(it, templatePath, TemplateEngineKind.Freemarker)
    }
}

private val privateReport: IXDocReport = createXDocReport("templates/privateReport.docx")
private val publicReport: IXDocReport = createXDocReport("templates/publicReport.docx")

@Service
class ReportService(
    private val presentRepository: PresentRepository,
    private val candyRepository: CandyRepository
) {
    suspend fun generatePublicReport(presentId: UUID): Report {
        return generateReport(presentId, publicReport)
    }

    suspend fun generatePrivateReport(presentId: UUID): Report {
        return generateReport(presentId, privateReport)
    }

    private suspend fun generateReport(presentId: UUID, report: IXDocReport): Report {
        val present = fetchPresent(presentId)
        return Report(
            formatReportName(present),
            generateReportContent(present, report)
        )
    }

    private fun formatReportName(present: ReportPresent): String {
        return "${present.name} ${present.price} RUB.docx"
    }

    private fun generateReportContent(present: ReportPresent, report: IXDocReport): ByteArray {
        val context: IContext = report.createContext()
        context.put("present", present)
        context.put("costPrice", present.computeCost())

        val bout = ByteArrayOutputStream()
        report.process(context, bout)

        return bout.toByteArray()
    }

    private suspend fun fetchPresent(id: UUID): ReportPresent {
        val present = presentRepository.findById(id)
        val candyIds = present.items
            .stream()
            .map(Present.Item::candyId)
            .collect(Collectors.toSet())
        val candies = candyRepository.findAllById(candyIds).toList()

        return ReportPresent(present, candies)
    }
}
