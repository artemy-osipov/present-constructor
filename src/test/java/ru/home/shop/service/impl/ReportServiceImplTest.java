package ru.home.shop.service.impl;

import org.junit.Test;
import ru.home.shop.domain.bean.CandyBean;
import ru.home.shop.domain.bean.PresentBean;
import ru.home.shop.service.PresentService;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReportServiceImplTest {

    private PresentBean getPresent() {
        PresentBean present = new PresentBean();
        present.setName("name");
        present.setPrice(BigDecimal.valueOf(4.2));

        CandyBean candy1 = new CandyBean();
        candy1.setId(1);
        candy1.setName("name1");
        candy1.setFirm("firm1");
        candy1.setPrice(BigDecimal.valueOf(1.1));
        candy1.setCount(2);

        CandyBean candy2 = new CandyBean();
        candy2.setId(3);
        candy2.setName("name2");
        candy2.setFirm("firm2");
        candy2.setPrice(BigDecimal.valueOf(2.2));
        candy2.setCount(6);

        present.getCandies().add(candy1);
        present.getCandies().add(candy2);

        return present;
    }

    @Test
    public void publicReport_validId_shouldGenerateSomeReport() throws IOException {
        assertNotNull(new ReportServiceImpl().publicReport(getPresent()));
    }

    @Test
    public void publicReport_notValidId_shouldThrowException() throws IOException {
        PresentService mock = mock(PresentService.class);
        int id = 1;
        when(mock.find(id)).thenReturn(null);

        try {
            new ReportServiceImpl().publicReport(null);
            fail();
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Test
    public void privateReport_validId_shouldGenerateSomeReport() throws IOException {
        assertNotNull(new ReportServiceImpl().privateReport(getPresent()));
    }

    @Test
    public void private_notValidId_shouldThrowException() throws IOException {
        try {
            new ReportServiceImpl().privateReport(null);
            fail();
        } catch (IllegalArgumentException ignored) {
        }
    }
}