package ru.home.shop.service;

import ru.home.shop.domain.bean.PresentBean;
import ru.home.shop.domain.bean.Report;

public interface ReportService {

    Report publicReport(PresentBean present);
    Report privateReport(PresentBean present);
}
