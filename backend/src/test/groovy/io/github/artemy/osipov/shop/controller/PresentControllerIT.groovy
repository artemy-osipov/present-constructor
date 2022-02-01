package io.github.artemy.osipov.shop.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import io.github.artemy.osipov.present.exception.EntityNotFoundException
import io.github.artemy.osipov.present.service.present.PresentCommandHandler
import io.github.artemy.osipov.present.service.present.RemovePresentCommand

import static org.hamcrest.CoreMatchers.notNullValue
import static org.hamcrest.collection.IsCollectionWithSize.hasSize
import static org.mockito.Mockito.any
import static org.mockito.Mockito.doThrow
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static io.github.artemy.osipov.present.utils.JsonUtils.toJson
import static io.github.artemy.osipov.present.utils.UuidUtils.newUUID

@WebMvcTest(PresentController.class)
class PresentControllerIT {

    @MockBean
    private PresentCommandHandler commandHandler;

    @Autowired
    private MockMvc mockMvc;

//    private DAddPresent getUpdateDTO() {
//        DAddPresent dto = new DAddPresent();
//        dto.setName('name');
//        dto.setPrice(BigDecimal.valueOf(4.2));
//
//        DPresentItem item1 = new DPresentItem();
//        item1.setCandy(new EntityDTO());
//        item1.getCandy().setId(newUUID());
//        item1.setCount(2);
//
//        DPresentItem item2 = new DPresentItem();
//        item2.setCandy(new EntityDTO());
//        item2.getCandy().setId(newUUID());
//        item2.setCount(6);
//
//        dto.setItems(asList(item1, item2));
//
//        return dto;
//    }

    @Test
    void addPresentWithValidEntityShouldReturnLocation() throws Exception {
        mockMvc.perform(post('/api/presents')
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(getUpdateDTO())))
                .andExpect(status().isCreated())
                .andExpect(header().string('Location', notNullValue()));
    }

    @Test
    void addPresentWithNotValidEntityShouldReturnErrors() throws Exception {
        mockMvc.perform(post('/api/presents')
                .contentType(MediaType.APPLICATION_JSON)
                .content('{}'))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath('$.fieldErrors', hasSize(3)));
    }

    @Test
    void removePresentShouldReturn2xx() throws Exception {
        mockMvc.perform(delete('/api/presents/{id}', newUUID()))
                .andExpect(status().isNoContent());
    }

    @Test
    void removePresentWithNonexistentIdShouldReturn404() throws Exception {
        doThrow(new EntityNotFoundException()).when(commandHandler).on(any(RemovePresentCommand.class));

        mockMvc.perform(delete('/api/presents/{id}', newUUID()))
                .andExpect(status().isNotFound());
    }
}