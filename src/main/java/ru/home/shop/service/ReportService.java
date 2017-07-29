package ru.home.shop.service;

import ru.home.shop.domain.model.Present;
import ru.home.shop.domain.model.Report;

public interface ReportService {

    Report generatePublicReport(Present present);
    Report generatePrivateReport(Present present);
}
