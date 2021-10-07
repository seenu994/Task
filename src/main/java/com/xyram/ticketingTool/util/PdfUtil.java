package com.xyram.ticketingTool.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfUtil {

	public static BaseColor headercolor = new BaseColor(0.129411f, 0.5882f, 0.95294f, 0.05f);
	public static BaseColor stroke = new BaseColor(213, 234, 255);
	public static BaseColor textcolor = new BaseColor(52, 46, 129);
	public static BaseColor tableheadingColor = new BaseColor(39, 35, 94);
	public static BaseColor rowTextColor = new BaseColor(0, 0, 0);
	public static BaseColor rowheadercolor = new BaseColor(255, 255, 255);

	public static byte[] toblob(ResponseEntity<InputStreamResource> pdfresponse) {

		byte[] blob = null;

		try {
			if (pdfresponse.getBody() != null) {
				InputStream is = pdfresponse.getBody().getInputStream();
				System.out.println("Begin /GET request!" + is);
				blob = IOUtils.toByteArray(is);
			} else {
				System.out.println("Response for Get Request: NULL");
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		return blob;

	}

	public static ResponseEntity<InputStreamResource> generatePdfReport(List<Map<String, String>> claims,
			List<String> heading, byte[] logo, String fromDate, String toDate) throws IOException, DocumentException {

		Document document = new Document(PageSize.A4.rotate(), 0, 0, 0, 0);

		ByteArrayOutputStream os = new ByteArrayOutputStream();

		PdfWriter.getInstance(document, os);

		document.open();

		PdfPTable titlecell = new PdfPTable(3);
		titlecell.setSpacingAfter(20);
		titlecell.setWidthPercentage(100);

		if (logo != null && logo.length > 0) {
			titlecell.addCell(createImageCell(logo));

		} else {
			titlecell.addCell(title("", Element.ALIGN_RIGHT, tableheadingColor, Font.NORMAL, 12));
		}
		String date = null;
		if (fromDate != null && toDate != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-YYYY");
			;

			try {
				date = sdf1.format(sdf.parse(fromDate)) + " " + "to " + sdf1.format(sdf.parse(toDate));
			} catch (ParseException e1) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Invalid date format date should be yyyy-MM-dd");

			}

			titlecell.addCell(title("Claim Report", Element.ALIGN_CENTER, tableheadingColor, Font.BOLD, 14));
			titlecell.addCell(title(date, Element.ALIGN_RIGHT, tableheadingColor, Font.NORMAL, 12));
		} else {
			titlecell.addCell(title("Patient Report", Element.ALIGN_CENTER, tableheadingColor, Font.BOLD, 14));
			titlecell.addCell(title("", Element.ALIGN_RIGHT, tableheadingColor, Font.NORMAL, 12));
		}
		document.add(titlecell);

	//	PatientPdfUtil.createTableFormDetails(document, claims, "", false, heading, 96, 9);

		document.close();

		ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ClaimsReport.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(is));

	}

	public static Image createImageCell(Document document) throws DocumentException, IOException {

		Image image = Image.getInstance("logo.png");
		image.setAlignment(Image.ALIGN_LEFT);
		image.setWidthPercentage(20);
		return image;

	}

	public static byte[] stampDocument(byte[] orig) {
		try {
			PdfReader pdfReader = new PdfReader(orig);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfStamper pdfStamper = new PdfStamper(pdfReader, baos);
			Image image = Image.getInstance("logo.png");
			image.setAbsolutePosition(400, 200);
			float w = image.getScaledWidth();
			float h = image.getScaledHeight();
			PdfGState gs1 = new PdfGState();
			gs1.setFillOpacity(0.2f);
			PdfContentByte content;
			Rectangle pagesize;
			float x, y;
			for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
				pagesize = pdfReader.getPageSizeWithRotation(i);
				x = (pagesize.getLeft() + pagesize.getRight()) / 2;
				y = (pagesize.getTop() + pagesize.getBottom()) / 2;
				content = pdfStamper.getUnderContent(i);
				content.saveState();
				content.setGState(gs1);
				content.addImage(image, w, 0, 0, h, x - (w / 2), y - (h / 2));
				content.restoreState();

			}

			pdfStamper.close();

			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static PdfPCell createTextCell(String text, int colspan, int rowspan) {
		PdfPCell cell = new PdfPCell();
		Paragraph p = new Paragraph(text);
		cell.addElement(p);
		cell.setColspan(colspan);
		cell.setRowspan(rowspan);
		return cell;
	}

	public static PdfPCell createImageCell(byte[] logo) throws DocumentException, IOException {
		PdfPCell titlecell = new PdfPCell();

		Image image = Image.getInstance(logo);
		// Image image=Image.getInstance("telelinklogo.png");

		image.setWidthPercentage(30);
		image.setAlignment(Element.ALIGN_LEFT);
		titlecell.addElement(image);
		titlecell.setFixedHeight(50);
		titlecell.setPaddingLeft(30);
		titlecell.setBorder(0);
		titlecell.isInline();
		titlecell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		titlecell.setBackgroundColor(headercolor);
		return titlecell;

	}

	public static Document tableHeader(String headerName, Document document) throws DocumentException {
		PdfPTable vitalTable1 = new PdfPTable(2);
		PdfPCell titlecell = new PdfPCell();

		Font rowfont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, tableheadingColor);
		Paragraph tableheader = new Paragraph();
		tableheader.setFont(rowfont);
		tableheader.add(headerName);
		titlecell.setBorderColor(stroke);
		titlecell.setBorderWidthTop(1);
		titlecell.setBorderWidthLeft(0);
		titlecell.setBorderWidthRight(0);
		titlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
		titlecell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		vitalTable1.addCell(titlecell);

		document.add(vitalTable1);
		return document;

	}

	public static PdfPCell title(String text, int alignstyle, BaseColor tableheadingColor2, int style, int fontSize) {

		PdfPCell titlecell = new PdfPCell();
		Font rowfont = FontFactory.getFont(FontFactory.HELVETICA, fontSize, style, textcolor);
		Paragraph tableTitle = new Paragraph();
		tableTitle.add(text);
		tableTitle.setFont(rowfont);
		tableTitle.setIndentationLeft(20);
		titlecell.setBorder(0);
		tableTitle.setAlignment(alignstyle);
		titlecell.setFixedHeight(50);
		titlecell.setPaddingRight(20);
		titlecell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		titlecell.isInline();
		titlecell.setBackgroundColor(headercolor);
		titlecell.addElement(tableTitle);
		return titlecell;

	}

}
