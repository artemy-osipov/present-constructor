package io.github.artemy.osipov.shop.service.report

import io.github.artemy.osipov.shop.exception.EntityNotFoundException
import io.github.artemy.osipov.shop.service.candy.CandyRepository
import io.github.artemy.osipov.shop.service.present.PresentRepository
import io.github.artemy.osipov.shop.testdata.CandyTestData
import io.github.artemy.osipov.shop.testdata.PresentTestData
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart
import org.docx4j.wml.Text
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import javax.xml.bind.JAXBElement

import static io.github.artemy.osipov.shop.testdata.PresentTestData.PRESENT_ID
import static io.github.artemy.osipov.shop.testdata.PresentTestData.REPORT_NAME
import static io.github.artemy.osipov.shop.utils.UuidUtils.newUUID
import static org.junit.jupiter.api.Assertions.assertThrows
import static org.mockito.Mockito.*

class ReportServiceIT {
    def presentRepository = mock(PresentRepository)
    def candyRepository = mock(CandyRepository)
    def service = new ReportService(
            presentRepository,
            candyRepository
    )

    @BeforeEach
    void setup() {
        doReturn(PresentTestData.present())
                .when(presentRepository)
                .getById(PRESENT_ID)
        doReturn([CandyTestData.candy()])
                .when(candyRepository)
                .findAllById(any())
    }

    @Test
    void 'should fail generate report by nonexistent present'() {
        def unknownId = newUUID()
        doThrow(EntityNotFoundException)
                .when(presentRepository)
                .getById(unknownId)

        assertThrows(EntityNotFoundException) {
            service.generatePrivateReport(unknownId)
        }
    }

    @Test
    void 'should generate public report'() {
        Report report = service.generatePublicReport(PRESENT_ID)

        assert report.name == REPORT_NAME
        assert tableData(report, 4) == [
                ['1', 'name', 'firm', '5']
        ]
    }

    @Test
    void 'should generate private report'() {
        Report report = service.generatePrivateReport(PRESENT_ID)

        assert report.name == REPORT_NAME
        assert tableData(report, 5) == [
                ['1', 'name', 'firm', '4.2', '5']
        ]
    }

    private List<List<String>> tableData(Report report, int tableWidth) {
        MainDocumentPart docx = WordprocessingMLPackage
                .load(new ByteArrayInputStream(report.content))
                .getMainDocumentPart()
        List<String> tableCells = docx.getJAXBNodesViaXPath(
                "//w:document/w:body/w:tbl/w:tr[position()>1]/w:tc/w:p/w:r/w:t",
                false
        )
                .collect { (it as JAXBElement<Text>).value.value }

        int rows = tableCells.size() / tableWidth
        def res = new ArrayList<List<String>>(rows)
        for (int i = 0; i < tableCells.size(); i++) {
            int row = i / tableWidth
            if (i % tableWidth == 0) {
                res.add(new ArrayList<String>())
            }
            res.get(row).add(tableCells.get(i))
        }
        return res
    }
}