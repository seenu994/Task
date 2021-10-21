package com.xyram.ticketingTool.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.xyram.ticketingTool.entity.Ticket;

public interface ReportService {

public Map getSummaryReportData(Pageable pageable);
public Map prepareReport(Pageable pageable, String date1, String date2);

public Map prepareReportOnProjectNameAndTksStatus(Map<String, Object> filter,Pageable pageable);

}
