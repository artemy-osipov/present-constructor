package ru.home.shop.testdata

import groovy.transform.CompileStatic
import ru.home.shop.service.candy.CandyModel

@CompileStatic
class CandyTestData {

    static final UUID CANDY_ID = UUID.fromString('532d5f3b-25e6-4adc-b99a-da74cb5be876')

    static CandyModel candyModel() {
        new CandyModel().tap {
            id = CANDY_ID
            name = 'candy name'
            firm = 'candy firm'
            price = 1.1
            order = 1
            active = true
        }
    }
}
