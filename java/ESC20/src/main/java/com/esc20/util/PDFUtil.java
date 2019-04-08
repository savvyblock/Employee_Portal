package com.esc20.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.esc20.model.BhrEmpDemo;
import com.evopdf.HtmlToPdfConverter;
import com.evopdf.PdfPageOrientation;
import com.evopdf.PdfPageSize;
import com.evopdf.TriggeringMode;

public class PDFUtil {

	public static byte[] getCurrentPayInformationPDF(String url,HttpServletRequest request) throws Exception 
	{
	    String serverIP = "127.0.0.1";
	    int port = 40001;
	    HtmlToPdfConverter htmlToPdfConverter = new HtmlToPdfConverter(serverIP, port);
	    htmlToPdfConverter.httpPostFields().add("empNbr", ((BhrEmpDemo) request.getSession().getAttribute("userDetail")).getEmpNbr());
	    htmlToPdfConverter.httpPostFields().add("districtId", (String)request.getSession().getAttribute("districtId"));
		htmlToPdfConverter.httpPostFields().add("language",(String)request.getSession().getAttribute("language"));
	    htmlToPdfConverter.setMediaType("print");
	    htmlToPdfConverter.setLicenseKey("B4mYiJubiJiInoaYiJuZhpmahpGRkZGImg==");
	    htmlToPdfConverter.setRenderedHtmlElementSelector(".toPrint");
	    htmlToPdfConverter.setHtmlViewerWidth(1024);
	    htmlToPdfConverter.setNavigationTimeout(10);
	    htmlToPdfConverter.setTriggeringMode(TriggeringMode.ConversionDelay);
		htmlToPdfConverter.setConversionDelay(5);
		htmlToPdfConverter.pdfDocumentOptions().setPdfPageOrientation(PdfPageOrientation.Portrait);
	    htmlToPdfConverter.pdfDocumentOptions().setPdfPageSize(PdfPageSize.A4);
	    htmlToPdfConverter.pdfFormOptions().setAutoPdfFormEnabled(true);
	    htmlToPdfConverter.pdfDocumentOptions().setFitWidth(true);
	    htmlToPdfConverter.pdfDocumentOptions().setAutoSizePdfPage(true);
	    htmlToPdfConverter.pdfDocumentOptions().setLeftMargin(20);
	    htmlToPdfConverter.pdfDocumentOptions().setRightMargin(20);
	    htmlToPdfConverter.pdfDocumentOptions().setTopMargin(20);
	    htmlToPdfConverter.pdfDocumentOptions().setBottomMargin(20);
	    htmlToPdfConverter.pdfDocumentOptions().setStretchToFit(true);
	    byte[] outPdfBuffer = null;
		outPdfBuffer = htmlToPdfConverter.convertUrl(url);
	    return outPdfBuffer;
	}

	public static byte[] getCalendarYTDPDF(String url,HttpServletRequest request, String year) throws Exception 
	{
	    String serverIP = "127.0.0.1";
	    int port = 40001;
	    HtmlToPdfConverter htmlToPdfConverter = new HtmlToPdfConverter(serverIP, port);
	    htmlToPdfConverter.httpPostFields().add("empNbr", ((BhrEmpDemo) request.getSession().getAttribute("userDetail")).getEmpNbr());
	    htmlToPdfConverter.httpPostFields().add("districtId", (String)request.getSession().getAttribute("districtId"));
		htmlToPdfConverter.httpPostFields().add("language",(String)request.getSession().getAttribute("language"));
		htmlToPdfConverter.httpPostFields().add("year", year);
	    htmlToPdfConverter.setMediaType("print");
	    htmlToPdfConverter.setLicenseKey("B4mYiJubiJiInoaYiJuZhpmahpGRkZGImg==");
	    htmlToPdfConverter.setRenderedHtmlElementSelector(".toPrint");
	    htmlToPdfConverter.setHtmlViewerWidth(1024);
	    htmlToPdfConverter.setNavigationTimeout(10);
	    htmlToPdfConverter.setTriggeringMode(TriggeringMode.ConversionDelay);
		htmlToPdfConverter.setConversionDelay(5);
		htmlToPdfConverter.pdfDocumentOptions().setPdfPageOrientation(PdfPageOrientation.Portrait);
	    htmlToPdfConverter.pdfDocumentOptions().setPdfPageSize(PdfPageSize.A4);
	    htmlToPdfConverter.pdfFormOptions().setAutoPdfFormEnabled(true);
	    htmlToPdfConverter.pdfDocumentOptions().setFitWidth(true);
	    htmlToPdfConverter.pdfDocumentOptions().setAutoSizePdfPage(true);
	    htmlToPdfConverter.pdfDocumentOptions().setLeftMargin(20);
	    htmlToPdfConverter.pdfDocumentOptions().setRightMargin(20);
	    htmlToPdfConverter.pdfDocumentOptions().setTopMargin(20);
	    htmlToPdfConverter.pdfDocumentOptions().setBottomMargin(20);
	    htmlToPdfConverter.pdfDocumentOptions().setStretchToFit(true);
	    byte[] outPdfBuffer = null;
		outPdfBuffer = htmlToPdfConverter.convertUrl(url);
	    return outPdfBuffer;
	}

	public static byte[] getEarningsPDF(String url,HttpServletRequest request, String selectedPayDate) throws Exception 
	{
	    String serverIP = "127.0.0.1";
	    int port = 40001;
	    HtmlToPdfConverter htmlToPdfConverter = new HtmlToPdfConverter(serverIP, port);
	    htmlToPdfConverter.httpPostFields().add("empNbr", ((BhrEmpDemo) request.getSession().getAttribute("userDetail")).getEmpNbr());
	    htmlToPdfConverter.httpPostFields().add("districtId", (String)request.getSession().getAttribute("districtId"));
		htmlToPdfConverter.httpPostFields().add("language",(String)request.getSession().getAttribute("language"));
		htmlToPdfConverter.httpPostFields().add("selectedPayDate", selectedPayDate);
	    htmlToPdfConverter.setMediaType("print");
	    htmlToPdfConverter.setLicenseKey("B4mYiJubiJiInoaYiJuZhpmahpGRkZGImg==");
	    htmlToPdfConverter.setRenderedHtmlElementSelector(".toPrint");
	    htmlToPdfConverter.setHtmlViewerWidth(1024);
	    htmlToPdfConverter.setNavigationTimeout(10);
	    htmlToPdfConverter.setTriggeringMode(TriggeringMode.ConversionDelay);
		htmlToPdfConverter.setConversionDelay(5);
		htmlToPdfConverter.pdfDocumentOptions().setPdfPageOrientation(PdfPageOrientation.Portrait);
	    htmlToPdfConverter.pdfDocumentOptions().setPdfPageSize(PdfPageSize.A4);
	    htmlToPdfConverter.pdfFormOptions().setAutoPdfFormEnabled(true);
	    htmlToPdfConverter.pdfDocumentOptions().setFitWidth(true);
	    htmlToPdfConverter.pdfDocumentOptions().setAutoSizePdfPage(true);
	    htmlToPdfConverter.pdfDocumentOptions().setLeftMargin(20);
	    htmlToPdfConverter.pdfDocumentOptions().setRightMargin(20);
	    htmlToPdfConverter.pdfDocumentOptions().setTopMargin(20);
	    htmlToPdfConverter.pdfDocumentOptions().setBottomMargin(20);
	    htmlToPdfConverter.pdfDocumentOptions().setStretchToFit(true);
	    byte[] outPdfBuffer = null;
		outPdfBuffer = htmlToPdfConverter.convertUrl(url);
	    return outPdfBuffer;
	}
	
	public static byte[] getW2InformationPDF(String url,HttpServletRequest request, String year) throws Exception 
	{
	    String serverIP = "127.0.0.1";
	    int port = 40001;
	    HtmlToPdfConverter htmlToPdfConverter = new HtmlToPdfConverter(serverIP, port);
	    htmlToPdfConverter.httpPostFields().add("empNbr", ((BhrEmpDemo) request.getSession().getAttribute("userDetail")).getEmpNbr());
	    htmlToPdfConverter.httpPostFields().add("districtId", (String)request.getSession().getAttribute("districtId"));
		htmlToPdfConverter.httpPostFields().add("language",(String)request.getSession().getAttribute("language"));
		htmlToPdfConverter.httpPostFields().add("year", year);
	    htmlToPdfConverter.setMediaType("print");
	    htmlToPdfConverter.setLicenseKey("B4mYiJubiJiInoaYiJuZhpmahpGRkZGImg==");
	    htmlToPdfConverter.setRenderedHtmlElementSelector(".toPrint");
	    htmlToPdfConverter.setHtmlViewerWidth(1024);
	    htmlToPdfConverter.setNavigationTimeout(10);
	    htmlToPdfConverter.setTriggeringMode(TriggeringMode.ConversionDelay);
		htmlToPdfConverter.setConversionDelay(5);
		htmlToPdfConverter.pdfDocumentOptions().setPdfPageOrientation(PdfPageOrientation.Portrait);
	    htmlToPdfConverter.pdfDocumentOptions().setPdfPageSize(PdfPageSize.A4);
	    htmlToPdfConverter.pdfFormOptions().setAutoPdfFormEnabled(true);
	    htmlToPdfConverter.pdfDocumentOptions().setFitWidth(true);
	    htmlToPdfConverter.pdfDocumentOptions().setAutoSizePdfPage(true);
	    htmlToPdfConverter.pdfDocumentOptions().setLeftMargin(20);
	    htmlToPdfConverter.pdfDocumentOptions().setRightMargin(20);
	    htmlToPdfConverter.pdfDocumentOptions().setTopMargin(20);
	    htmlToPdfConverter.pdfDocumentOptions().setBottomMargin(20);
	    htmlToPdfConverter.pdfDocumentOptions().setStretchToFit(true);
	    byte[] outPdfBuffer = null;
		outPdfBuffer = htmlToPdfConverter.convertUrl(url);
	    return outPdfBuffer;
	}

	public static byte[] get1095InformationPDF(String url, HttpServletRequest request, String year, Integer BPageNo, Integer CPageNo,
			String sortBy, String sortOrder, String type) throws Exception {
	    String serverIP = "127.0.0.1";
	    int port = 40001;
	    HtmlToPdfConverter htmlToPdfConverter = new HtmlToPdfConverter(serverIP, port);
	    htmlToPdfConverter.httpPostFields().add("empNbr", ((BhrEmpDemo) request.getSession().getAttribute("userDetail")).getEmpNbr());
	    htmlToPdfConverter.httpPostFields().add("districtId", (String)request.getSession().getAttribute("districtId"));
		htmlToPdfConverter.httpPostFields().add("language",(String)request.getSession().getAttribute("language"));
		htmlToPdfConverter.httpPostFields().add("year", year);
		htmlToPdfConverter.httpPostFields().add("BPageNo", BPageNo.toString());
		htmlToPdfConverter.httpPostFields().add("CPageNo", CPageNo.toString());
		htmlToPdfConverter.httpPostFields().add("sortBy", sortBy);
		htmlToPdfConverter.httpPostFields().add("sortOrder", sortOrder);
		htmlToPdfConverter.httpPostFields().add("type", type);
	    htmlToPdfConverter.setMediaType("print");
	    htmlToPdfConverter.setLicenseKey("B4mYiJubiJiInoaYiJuZhpmahpGRkZGImg==");
	    htmlToPdfConverter.setRenderedHtmlElementSelector(".toPrint");
	    htmlToPdfConverter.setHtmlViewerWidth(1024);
	    htmlToPdfConverter.setNavigationTimeout(10);
	    htmlToPdfConverter.setTriggeringMode(TriggeringMode.ConversionDelay);
		htmlToPdfConverter.setConversionDelay(5);
		htmlToPdfConverter.pdfDocumentOptions().setPdfPageOrientation(PdfPageOrientation.Portrait);
	    htmlToPdfConverter.pdfDocumentOptions().setPdfPageSize(PdfPageSize.A4);
	    htmlToPdfConverter.pdfFormOptions().setAutoPdfFormEnabled(true);
	    htmlToPdfConverter.pdfDocumentOptions().setFitWidth(true);
	    htmlToPdfConverter.pdfDocumentOptions().setAutoSizePdfPage(true);
	    htmlToPdfConverter.pdfDocumentOptions().setLeftMargin(20);
	    htmlToPdfConverter.pdfDocumentOptions().setRightMargin(20);
	    htmlToPdfConverter.pdfDocumentOptions().setTopMargin(20);
	    htmlToPdfConverter.pdfDocumentOptions().setBottomMargin(20);
	    htmlToPdfConverter.pdfDocumentOptions().setStretchToFit(true);
	    byte[] outPdfBuffer = null;
		outPdfBuffer = htmlToPdfConverter.convertUrl(url);
	    return outPdfBuffer;
	}
	
}
