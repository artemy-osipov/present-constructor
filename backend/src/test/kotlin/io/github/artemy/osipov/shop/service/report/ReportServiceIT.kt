package io.github.artemy.osipov.shop.service.report

import io.github.artemy.osipov.shop.exception.EntityNotFoundException
import io.github.artemy.osipov.shop.service.candy.CandyRepository
import io.github.artemy.osipov.shop.service.present.PresentRepository
import io.github.artemy.osipov.shop.testdata.CandyTestData
import io.github.artemy.osipov.shop.testdata.PresentTestData
import io.github.artemy.osipov.shop.testdata.PresentTestData.PRESENT_ID
import io.github.artemy.osipov.shop.testdata.ReportTestData.REPORT_NAME
import io.github.artemy.osipov.shop.utils.UuidUtils.newUUID
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart
import org.docx4j.wml.Text
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import java.io.ByteArrayInputStream

import javax.xml.bind.JAXBElement

class ReportServiceIT {
    val presentRepository = mock(PresentRepository::class.java)
    val candyRepository = mock(CandyRepository::class.java)
    val service = ReportService(
            presentRepository,
            candyRepository
    )

    @BeforeEach
    fun setup() {
        doReturn(PresentTestData.present())
                .`when`(presentRepository)
                .getById(PRESENT_ID)
        doReturn(listOf(CandyTestData.candy()))
                .`when`(candyRepository)
                .findAllById(any())
    }

    @Test
    fun `should fail generate report by nonexistent present`() {
        val unknownId = newUUID()
        doThrow(EntityNotFoundException::class.java)
                .`when`(presentRepository)
                .getById(unknownId)

        assertThrows<EntityNotFoundException> {
            service.generatePrivateReport(unknownId)
        }
    }

    @Test
    fun `should generate public report`() {
        val report = service.generatePublicReport(PRESENT_ID)

        assert(report.name == REPORT_NAME)
        assert(
                tableData(report, 4) == listOf(
                        listOf("1", "name", "firm", "5")
                )
        )
    }

    @Test
    fun `should generate private report`() {
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