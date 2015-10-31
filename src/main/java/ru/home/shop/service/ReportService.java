package ru.home.shop.service;

import java.io.IOException;

public interface ReportService {

    byte[] publicReport(int present) throws IOException;
    byte[] privateReport(int present) throws IOException;
}
