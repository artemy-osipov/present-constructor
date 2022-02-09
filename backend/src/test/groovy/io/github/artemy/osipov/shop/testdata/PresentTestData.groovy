package io.github.artemy.osipov.shop.testdata

import groovy.transform.CompileStatic
import io.github.artemy.osipov.shop.controller.dto.DAddPresent
import io.github.artemy.osipov.shop.controller.dto.DPresentItem

import io.github.artemy.osipov.shop.service.present.Present
import io.github.artemy.osipov.shop.service.report.ReportPresent

import java.time.LocalDateTime

import static io.github.artemy.osipov.shop.testdata.CandyTestData.CANDY_ID

@CompileStatic
class PresentTestData {

    static final UUID PRESENT_ID = UUID.fromString('89a83839-5a67-41a4-9d8f-be2035b2f58a')
    static final String REPORT_NAME = 'some name 42.25 RUB.docx'

    static Present present() {
        new Present().tap {
            id = PRESENT_ID
            name = 'some name'
            price = 42.25
            date = LocalDateTime.of(2022, 1, 10, 14, 25)
            items = [
                    new Present.Item().tap {
                        candyId = CANDY_ID
                        count = 5
                    }
            ]
        }
    }

    static class REST {

        static DAddPresent addDTO() {
            new DAddPresent().tap {
                name = 'some name'
                price = 42.25
                items = [
                        new DPresentItem().tap {
                            candyId = CANDY_ID
                            count = 5
                        }
                ]
            }
        }
    }
}
