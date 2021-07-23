package ru.home.shop.testdata

import groovy.transform.CompileStatic
import ru.home.shop.service.present.Present
import ru.home.shop.service.present.PresentModel

import java.time.LocalDateTime

@CompileStatic
class PresentTestData {

    static PresentModel presentModel() {
        new PresentModel().tap {
            id = UUID.fromString('89a83839-5a67-41a4-9d8f-be2035b2f58a')
            name = 'present name'
            price = 2.2
            date = LocalDateTime.now()
            items = [
                    new PresentModel.Item().tap {
                        candyId = CandyTestData.CANDY_ID
                        count = 5
                    }
            ]
        }
    }

    static Present present() {
        new Present(
                presentModel(),
                [CandyTestData.candyModel()]
        )
    }
}
