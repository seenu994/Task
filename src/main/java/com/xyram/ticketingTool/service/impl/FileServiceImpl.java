/*
 * package com.xyram.ticketingTool.service.impl;
 * 
 * import java.io.IOException; import java.util.HashMap; import java.util.Map;
 * 
 * import javax.transaction.Transactional;
 * 
 * import org.slf4j.Logger; import org.slf4j.LoggerFactory; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Service; import
 * org.springframework.web.multipart.MultipartFile;
 * 
 * import com.xyram.ticketingTool.dao.AbstractDao; import
 * com.xyram.ticketingTool.entity.TicketToolFile; import
 * com.xyram.ticketingTool.service.FileService;
 * 
 * 
 * @Service
 * 
 * @Transactional public class FileServiceImpl extends AbstractDao implements
 * FileService {
 * 
 * private final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
 * 
 * 
 * 
 * 
 * @Override public Map saveFileToDB(MultipartFile logo) {
 * 
 * Map fileMap = new HashMap(); TicketToolFile file=new TicketToolFile(); byte[]
 * filearray; try { filearray=logo.getBytes(); String
 * filename=logo.getOriginalFilename();
 * file.setFileExtension(filename.substring(filename.lastIndexOf(".")+1));
 * file.setFileName("tickettoollogo"); file.setFileType(logo.getContentType());
 * file.setMimeType(logo.getContentType()); file.setImage(filearray);
 * 
 * getSession().save(file); fileMap .put("message",
 * "image is updated sucessfully"); } catch (IOException e) {
 * e.printStackTrace(); }
 * 
 * return fileMap ;
 * 
 * }
 * 
 * @Override public TicketToolFile getFileByFileName(String fileName) {
 * logger.info("Received request for get file: "+fileName); return
 * getSession().getFileByFileName(fileName); }
 * 
 * }
 */