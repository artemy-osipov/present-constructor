package io.github.artemy.osipov.shop.testdata

import groovy.transform.CompileStatic

import java.time.LocalDateTime

@CompileStatic
class PresentTestData {

    static io.github.artemy.osipov.shop.service.present.PresentModel presentModel() {
        new io.github.artemy.osipov.shop.service.present.PresentModel().tap {
            id = UUID.fromString('89a83839-5a67-41a4-9d8f-be2035b2f58a')
            name = 'present name'
            price = 2.2
            date = LocalDateTime.now()
            items = [
                    new io.github.artemy.osipov.shop.service.present.PresentModel.Item().tap {
                        candyId = CandyTestData.CANDY_ID
                        count = 5
                    }
            ]
        }
    }

    static io.github.artemy.osipov.shop.service.present.Present present() {
        new io.github.artemy.osipov.shop.service.present.Present(
                presentModel(),
                [CandyTestData.candyModel()]
        )
    }
}
