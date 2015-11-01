package ru.home.shop.service;

import ru.home.shop.domain.bean.PresentBean;

public interface ReportService {

    byte[] publicReport(PresentBean present);
    byte[] privateReport(PresentBean present);
}
