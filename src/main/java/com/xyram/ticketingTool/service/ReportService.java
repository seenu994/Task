package com.xyram.ticketingTool.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;

public interface ReportService {
public Map prepareReport();
public Map getSummaryReportData(Pageable pageable);

}
