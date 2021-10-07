package com.xyram.ticketingTool.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
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
	  
	
	  @Override public Map prepareReport() {
		  Map response = new HashMap<>();
	  Document document = new Document(); ClassLoader classLoader =
	  getClass().getClassLoader(); File file = new
	  File(classLoader.getResource("Report.pdf").getFile());
	  System.out.println("=====file.getName()======> " + file.getName());
	  System.out.println("=====file.length()======> " + file.length()); String
	  projectId="2c9fab1f7c4109ba017c410a3d0c0000"; String
	  name=projRepo.getProjectNameByProjectId(projectId);
	  System.out.println("ProjectName::"+name);
	  
	  Employee emp=empRepo.getById("2c9fab1f7c3eebc6017c4067cade000w");
	  System.out.println("empName"+ emp.getFirstName());
	  
	  String  empId=ticketAssignRepo.getAssigneeId("2c9fab1f7c550c48017c551626780005"); 
	  System.out.println("empId::"+empId);
	  Employee emp1=empRepo.getById(empId);
	  System.out.println("empName::"+ emp1.getFirstName());
	  //testing ticket raised by => created_by column 
	 System.out.println("find out ticket raised by");
	  String created_by="2c9fab1f7c3eebc6017c406295e4000a";
	  String empName=empRepo.getEmpName(created_by) ;
	  System.out.println("empName::"+ empName);
	  
	  
	  try { 
		  PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(classLoader.getResource("Report.pdf").getFile()));
	  document.open();
	  
	  Paragraph intro= new Paragraph("Report"); 
	  Paragraph space = new Paragraph(" "); 
	  PdfPTable table1 = new PdfPTable(2);
	  PdfPCell cell = new PdfPCell();
	  cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	  
	  PdfPTable table2 = new PdfPTable(7);
	  
	  
	  table2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
	  table2.addCell(new Paragraph("TicketNo")); 
	  table2.addCell(new Phrase("Project")); 
	  table2.addCell(new Phrase("TicketRaised_By"));
	  table2.addCell(new Paragraph("Date")); 
	  table2.addCell(new Phrase("Resovled_By")); 
	  table2.addCell(new Phrase("Status"));
	table2.addCell(new Phrase("Duration"));
	  
	  table2.setHeaderRows(1);
	  
	  
	  List<Ticket> tks =ticketRepo.findAll(); 
	 for(Ticket t:  tks) { 
		 if(t.getId()!=null) {
			 table2.addCell(t.getId());
			 table2.addCell(projRepo.getProjectNameByProjectId(t.getProjectId()));
		  //ticketRaised by 
			 if(empRepo.getEmpName(t.getCreatedBy())!=null) {
				 table2.addCell(empRepo.getEmpName(t.getCreatedBy()));
			 }
			 else {
				 table2.addCell(t.getCreatedBy());
			 }
			
			 table2.addCell(t.getCreatedAt().toString()); //resolved by String
					
		  String  empId1=ticketAssignRepo.getAssigneeId(t.getId()); 
		  if(empId1!=null) {
			  Employee emp2=empRepo.getById(empId1); 
				 table2.addCell( emp2.getFirstName());
		  }
		  else {
			  table2.addCell(" ");
		  }
		  
		 table2.addCell(t.getStatus().toString());
		 if(t.getStatus().equals("COMPLETED")) {
			 String  duration=findDifference(t.getCreatedAt(),t.getLastUpdatedAt());
			 table2.addCell(duration); 
		 }
		 else {
			 table2.addCell(" "); 
		 }
		 
		 }
		 else {
			 System.out.println("id cant be null");
			 continue;
		 }
		
	  
	  }
	  
	  document.add(intro);
	  document.add(space); 
	  
	//  document.add(space); 
	  document.add(table2);
	  
	  
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
}


