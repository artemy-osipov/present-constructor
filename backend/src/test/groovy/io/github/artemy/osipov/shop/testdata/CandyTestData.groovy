package io.github.artemy.osipov.shop.testdata

import groovy.transform.CompileStatic
import io.github.artemy.osipov.shop.controller.dto.DEditCandy
import io.github.artemy.osipov.shop.service.candy.Candy

@CompileStatic
class CandyTestData {

    static final UUID CANDY_ID = UUID.fromString('532d5f3b-25e6-4adc-b99a-da74cb5be876')

    static Candy candy() {
        new Candy().tap {
            id = CANDY_ID
            name = 'name'
            firm = 'firm'
            price = 4.2
            order = 51D
            active = true
        }
    }

    static class REST {
        static DEditCandy updateDTO() {
            def candy = candy()
            new DEditCandy().tap {
                name = candy.name
                firm = candy.firm
                price = candy.price
                order = candy.order
            }
        }
    }
}
