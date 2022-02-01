package io.github.artemy.osipov.shop.controller

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static io.github.artemy.osipov.present.utils.UuidUtils.newUUID;

@ExtendWith(SpringExtension.class)
//@WebMvcTest(CandyQueryController.class)
@Disabled
class CandyQueryControllerIT {

//    @MockBean
//    private CandyQueryRepository repository;

    @Autowired
    private MockMvc mockMvc;

//    private CandyQuery getCandy() {
//        CandyQuery candy = new CandyQuery();
//        candy.setId(newUUID());
//        candy.setName('name');
//        candy.setFirm('firm');
//        candy.setPrice(BigDecimal.valueOf(4.20));
//        candy.setOrder(51);
//
//        return candy;
//    }

    @Test
    void findExistentCandyReturnIt() throws Exception {
        def candy = getCandy();
        doReturn(Optional.of(candy)).when(repository).findById(any());

        mockMvc.perform(get('/api/candies/{id}', candy.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.id', equalTo(candy.getId().toString())))
                .andExpect(jsonPath('$.name', equalTo(candy.getName())))
                .andExpect(jsonPath('$.firm', equalTo(candy.getFirm())))
                .andExpect(jsonPath('$.price', equalTo(candy.getPrice().doubleValue())))
                .andExpect(jsonPath('$.order', equalTo(candy.getOrder())));
    }

    @Test
    void findNotExistentCandyShouldReturn404() throws Exception {
        doReturn(Optional.empty()).when(repository).findById(any());

        mockMvc.perform(get('/api/candies/{id}', newUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void listCandyShouldReturnArray() throws Exception {
        doReturn(asList(getCandy(), getCandy())).when(repository).list();

        mockMvc.perform(get('/api/candies'))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$', hasSize(2)));
    }
}