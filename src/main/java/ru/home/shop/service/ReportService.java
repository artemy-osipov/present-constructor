package ru.home.shop.service;

import ru.home.shop.domain.model.Present;
import ru.home.shop.domain.model.Report;

public interface ReportService {

    Report publicReport(Present present);
    Report privateReport(Present present);
}
