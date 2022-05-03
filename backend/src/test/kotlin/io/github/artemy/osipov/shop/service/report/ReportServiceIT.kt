package io.github.artemy.osipov.shop.service.report

import io.github.artemy.osipov.shop.service.candy.CandyRepository
import io.github.artemy.osipov.shop.service.present.PresentRepository
import io.github.artemy.osipov.shop.testdata.CandyTestData
import io.github.artemy.osipov.shop.testdata.PresentTestData
import io.github.artemy.osipov.shop.testdata.PresentTestData.PRESENT_ID
import io.github.artemy.osipov.shop.testdata.ReportTestData.REPORT_NAME
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.test.runTest
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart
import org.docx4j.wml.Text
import org.junit.jupiter.api.Test
import org.mockito.Mockito.anyIterable
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.io.ByteArrayInputStream
import javax.xml.bind.JAXBElement

class ReportServiceIT {
    val presentRepository = mock<PresentRepository>() {
        onBlocking { findById(PRESENT_ID) } doReturn PresentTestData.present()
    }
    val candyRepository = mock<CandyRepository>() {
        on { findAllById(anyIterable()) } doReturn listOf(CandyTestData.candy()).asFlow()
    }
    val service = ReportService(
        presentRepository,
        candyRepository
    )

    @Test
    fun `should generate public report`() = runTest {
        val report = service.generatePublicReport(PRESENT_ID)

        assert(report.name == REPORT_NAME)
        assert(
            tableData(report, 4) == listOf(
                listOf("1", "name", "firm", "5")
            )
        )
    }

    @Test
    fun `should generate private report`() = runTest {
        val report = service.generatePrivateReport(PRESENT_ID)

        assert(report.name == REPORT_NAME)
        assert(
            tableData(report, 5) == listOf(
                listOf("1", "name", "firm", "4.2", "5")
            )
        )
    }

    private fun tableData(report: Report, tableWidth: Int): List<List<String>> {
        val docx: MainDocumentPart = WordprocessingMLPackage
            .load(ByteArrayInputStream(report.content))
            .mainDocumentPart

        @Suppress("UNCHECKED_CAST")
        val tableCells: List<String> = docx.getJAXBNodesViaXPath(
            "//w:document/w:body/w:tbl/w:tr[position()>1]/w:tc/w:p/w:r/w:t",
            false
        )
            .map { (it as JAXBElement<Text>).value.value }

        val rows: Int = tableCells.size / tableWidth
        val res = ArrayList<MutableList<String>>(rows)
        tableCells.indices.forEach {
            val row: Int = it / tableWidth
            if (it % tableWidth == 0) {
                res.add(ArrayList())
            }
            res[row].add(tableCells[it])
        }
        return res
    }
}