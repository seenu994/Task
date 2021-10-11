package com.xyram.ticketingTool.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;

public interface ReportService {

public Map getSummaryReportData(Pageable pageable);
public Map prepareReport(Pageable pageable, String date1, String date2);

}
