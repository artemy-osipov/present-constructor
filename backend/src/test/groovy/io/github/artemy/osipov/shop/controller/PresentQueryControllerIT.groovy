package io.github.artemy.osipov.shop.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static io.github.artemy.osipov.present.utils.UuidUtils.newUUID;

@ExtendWith(SpringExtension.class)
//@WebMvcTest(value = PresentQueryController.class)
class PresentQueryControllerIT {

//    @MockBean
//    private PresentQueryRepository repository;

    @Autowired
    private MockMvc mockMvc;

//    private PresentQuery getPresent() {
//        PresentQuery present = new PresentQuery();
//        present.setId(newUUID());
//        present.setName('name');
//        present.setPrice(BigDecimal.valueOf(4.2));
//
//        PresentItemQuery item1 = new PresentItemQuery();
//        item1.setCandy(new CandyQuery());
//        item1.getCandy().setId(newUUID());
//        item1.setCount(2);
//
//        PresentItemQuery item2 = new PresentItemQuery();
//        item2.setCandy(new CandyQuery());
//        item2.getCandy().setId(newUUID());
//        item2.setCount(6);
//
//        present.setItems(asList(item1, item2));
//
//        return present;
//    }

    @Test
    void findExistentPresentShouldReturnIt() throws Exception {
        def present = getPresent();
        doReturn(Optional.of(present)).when(repository).findById(any());

        mockMvc.perform(get('/api/presents/{id}', newUUID()))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.id', equalTo(present.getId().toString())))
                .andExpect(jsonPath('$.name', equalTo(present.getName())))
                .andExpect(jsonPath('$.price', equalTo(present.getPrice().doubleValue())))
                .andExpect(jsonPath('$.items[0].candy.id', equalTo(present.getItems().get(0).getCandy().getId().toString())))
                .andExpect(jsonPath('$.items[0].count', equalTo(present.getItems().get(0).getCount())))
                .andExpect(jsonPath('$.items[1].candy.id', equalTo(present.getItems().get(1).getCandy().getId().toString())))
                .andExpect(jsonPath('$.items[1].count', equalTo(present.getItems().get(1).getCount())));
    }

    @Test
    void findNotExistentPresentReturn404() throws Exception {
        doReturn(Optional.empty()).when(repository).findById(any());

        mockMvc.perform(get('/api/presents/{id}', newUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void listPresentShouldReturnArray() throws Exception {
        doReturn(asList(getPresent(), getPresent())).when(repository).list();

        mockMvc.perform(get('/api/presents'))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$', hasSize(2)));
    }
}