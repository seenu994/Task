package com.xyram.ticketingTool.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.xyram.ticketingTool.Repository.EmployeeRepository;
import com.xyram.ticketingTool.Repository.ProjectRepository;
import com.xyram.ticketingTool.Repository.TicketAssignRepository;
import com.xyram.ticketingTool.Repository.TicketRepository;
import com.xyram.ticketingTool.entity.Employee;
import com.xyram.ticketingTool.entity.Projects;
import com.xyram.ticketingTool.entity.Ticket;
import com.xyram.ticketingTool.entity.TicketAssignee;
import com.xyram.ticketingTool.enumType.TicketStatus;
import com.xyram.ticketingTool.service.ProjectService;
import com.xyram.ticketingTool.service.ReportService;
import com.xyram.ticketingTool.service.TicketService;
import com.xyram.ticketingTool.util.PdfUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Service
public class ReportServiceImpl implements ReportService{

	@Autowired
		TicketService ticketService;
	  @Autowired 
	  ProjectService projectService;
	  @Autowired
	  ProjectRepository projRepo;
	  @Autowired
	  EmployeeRepository empRepo;
	  @Autowired
	  TicketAssignRepository ticketAssignRepo;
	   @Autowired
	   TicketRepository ticketRepo;
	  
	
	  @Override
	  public Map prepareReport(Pageable pageable,String date1,String date2) {
		  Map response = new HashMap<>();
	  Document document = new Document();
	  ClassLoader classLoader =getClass().getClassLoader(); 
	  File file = new File(classLoader.getResource("Report.pdf").getFile());
	  System.out.println("=====file.getName()======> " + file.getName());
	
	 
	  try { 
		  PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(classLoader.getResource("Report.pdf").getFile()));
	  document.open();
	  
	  Paragraph intro= new Paragraph("Report"); 
	  Paragraph space = new Paragraph(" "); 
	  PdfPTable table1 = new PdfPTable(2);
	  PdfPCell cell = new PdfPCell();
	  cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	  
	  PdfPTable table2 = new PdfPTable(8);
	  
	  
	  table2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
	  table2.addCell(new Paragraph("TicketNo")); 
	  table2.addCell(new Phrase("Project")); 
	  table2.addCell(new Phrase("TicketRaised_By"));
	  table2.addCell(new Paragraph("Date")); 
	 table2.addCell(new Phrase("Assigned_To"));
	  table2.addCell(new Phrase("Status"));
	  table2.addCell(new Phrase("Resovled_By")); 
	table2.addCell(new Phrase("Duration"));
	  
	  table2.setHeaderRows(1);
	  
	
			 SimpleDateFormat sdf = new SimpleDateFormat(  "yyyy-MM-dd");
			 Date startTime,endTime;
			startTime=sdf.parse(date1);
			endTime = sdf.parse(date2);

		Page<Map> allTks =  ticketRepo.getAllTicketsByDuration(pageable, startTime, endTime);

		for(Map map : allTks) {
			String ticketNo=(String) map.get("ticket_id");
			table2.addCell(ticketNo);
			String projectName =  projRepo.getProjectNameByProjectId(map.get("project_id"));
			 table2.addCell(projectName);
		
			 if(map.get("created_by")!=null) {
				 String TicketRaisedBy =(String)map.get("createdByEmp");
				 table2.addCell(TicketRaisedBy);
			 }
			 else {
				 table2.addCell(" ");
			 }
			 Date createdDate= (Date) map.get("created_at");
			 table2.addCell(createdDate.toString());
			 String assignee = (String) map.get("assigneeName");
			 table2.addCell(assignee);
			 //Status
			  Object status= map.get("ticket_status");
			  table2.addCell(status.toString());
			 //ResolvedBy
			  String  empId1=ticketAssignRepo.getAssigneeId(map.get("created_by")); 
			  
			 if(empId1!=null) {
				// ResolvedBy
				 Employee emp2=empRepo.getById(empId1);
				 String ResolvedBy= emp2.getFirstName();
				  table2.addCell(ResolvedBy);
			 }
			 else {
				 table2.addCell(" ");
			 }
			 
			  //Duration
			  if(status.toString().equalsIgnoreCase("completed")) {
				  Date createdAt = (Date) map.get("created_at");
				  Date lastUpated=  (Date) map.get("last_updated_at");
				  String  duration=findDifference(createdAt,lastUpated);
				  table2.addCell(duration);
			  }
			  else {
				  table2.addCell(" ");
			  }
			
		}
	
	
	  document.add(intro);
	  document.add(space); 
	  
	//  document.add(space); 
	  document.add(table2);
	  
	  
	  document.close(); 
	  writer.close(); }
	  catch (Exception e) {
	  e.printStackTrace();
	  
	  } 
	  InputStreamResource resource = null; try 
	  { resource = new
	  InputStreamResource(new FileInputStream(file)); } 
	  catch (FileNotFoundException e1) {
		  e1.printStackTrace(); }
	  ResponseEntity<InputStreamResource> preparePdf = ResponseEntity.ok()
	  .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" +
	  file.getName()) .contentLength(file.length())
	  .contentType(MediaType.parseMediaType(MediaType.
	  APPLICATION_OCTET_STREAM_VALUE)).body(resource);
	  
	  byte[] blob = PdfUtil.toblob(preparePdf); File someFile = new
	  File(classLoader.getResource("Report.pdf").getFile()); FileOutputStream fos;
	  try { fos = new FileOutputStream(someFile); fos.write(blob); fos.flush();
	  fos.close(); } catch (IOException e) {
	  
	  e.printStackTrace(); }
	  
	  
	  response.put("fileName", "Report"); response.put("type", "application/pdf");
	  response.put("blob", blob); return response;
	  
	  }
	 
	
	  public static String findDifference(Date date, Date date2)
	 {
		  long difference_In_Days = 0;
		  String duration=" ";
	      try {
	        	 SimpleDateFormat sdf = new SimpleDateFormat(  "dd-MM-yyyy HH:mm:ss");
	       Date d1 = date;
	       Date d2 = date2;
	  
	          
	            long difference_In_Time
	                = d2.getTime() - d1.getTime();
	  
	            difference_In_Days
	                = (difference_In_Time
	                   / (1000 * 60 * 60 * 24))
	                  % 365;
	            long difference_In_Hours
                = (difference_In_Time
                   / (1000 * 60 * 60))
                  % 24;
	            System.out.println(
	                   
	                    + difference_In_Days
	                    + " days, "
	                    +difference_In_Hours+"hrs"
	                    );
	       duration= String.valueOf(difference_In_Days)+"Days"+String.valueOf(difference_In_Hours)+"hr";
	     
	  
	            return duration;
	        }
	  
	        // Catch the Exception
	        catch (Exception e) {
	            e.printStackTrace();
	        }
			return duration;
			
	    }

	  public Map getSummaryReportData(Pageable pageable) {
		  Map response = new HashMap<>();
	  Document document = new Document(); ClassLoader classLoader =
	  getClass().getClassLoader(); File file = new
	  File(classLoader.getResource("SummaryReport.pdf").getFile());
	  System.out.println("=====file.getName()======> " + file.getName());
	
	 
	  
	  try { 
		  PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(classLoader.getResource("SummaryReport.pdf").getFile()));
	  document.open();
	  
	  Paragraph intro= new Paragraph("SummaryReport"); 
	  Paragraph space = new Paragraph(" "); 
	 
	  PdfPCell cell = new PdfPCell();
	  cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	  
	  PdfPTable table1 = new PdfPTable(3);
	  
	  
	  table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
	  
	  table1.addCell(new Phrase("ProjectName")); 
	  table1.addCell(new Paragraph("Ticket_Status")); 
	  table1.addCell(new Phrase("TotalCount"));
	 
	  
	  table1.setHeaderRows(1);
	  
	  
	  Page<Map> allTks =  ticketRepo.getTicketStatusCountWithProject(pageable);
		
		for(Map map: allTks) {
			
			String projectName=(String) map.get("project_name");
			String ticketStatus = (String) map.get("ticket_status");	
			 Integer count = ((BigInteger)  map.get("TotalCount")).intValue();
			
			
			table1.addCell(projectName);
			table1.addCell(ticketStatus);
			table1.addCell(String.valueOf(count));
			
		}
	
		 
	  
	  document.add(intro);
	  document.add(space); 
	  
	//  document.add(space); 
	  document.add(table1);
	  
	  
	  document.close(); writer.close(); } catch (Exception e) {
	  e.printStackTrace();
	  
	  } InputStreamResource resource = null; try { resource = new
	  InputStreamResource(new FileInputStream(file)); } catch
	  (FileNotFoundException e1) { e1.printStackTrace(); }
	  ResponseEntity<InputStreamResource> preparePdf = ResponseEntity.ok()
	  .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" +
	  file.getName()) .contentLength(file.length())
	  .contentType(MediaType.parseMediaType(MediaType.
	  APPLICATION_OCTET_STREAM_VALUE)).body(resource);
	  
	  byte[] blob = PdfUtil.toblob(preparePdf); File someFile = new
	  File(classLoader.getResource("SummaryReport.pdf").getFile()); FileOutputStream fos;
	  try { fos = new FileOutputStream(someFile); fos.write(blob); fos.flush();
	  fos.close(); } catch (IOException e) {
	  
	  e.printStackTrace(); }
	  
	  
	  response.put("fileName", "Report"); response.put("type", "application/pdf");
	  response.put("blob", blob); return response;
	  
	  }
	 
	  

	 

}


