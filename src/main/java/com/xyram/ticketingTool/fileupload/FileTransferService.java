package com.xyram.ticketingTool.fileupload;




import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.pdf.codec.Base64.InputStream;

public interface FileTransferService {

	boolean deleteFile(String fileName, String path);

	boolean downloadFile(String localFilePath, MultipartFile file);

	boolean uploadFile(MultipartFile file, String path, String fileName);
	
	boolean uploadTimeSheetFile(InputStream stream, String path, String fileName);

	


}
