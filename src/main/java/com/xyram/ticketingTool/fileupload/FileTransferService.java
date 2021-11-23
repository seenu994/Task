package com.xyram.ticketingTool.fileupload;




import org.springframework.web.multipart.MultipartFile;

public interface FileTransferService {

	boolean deleteFile(String fileName, String path);

	boolean downloadFile(String localFilePath, MultipartFile file);

	boolean uploadFile(MultipartFile file, String path, String fileName);


}
