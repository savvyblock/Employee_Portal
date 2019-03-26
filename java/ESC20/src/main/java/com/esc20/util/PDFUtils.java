package com.esc20.util;

import com.itextpdf.text.*;

import com.itextpdf.text.pdf.PdfWriter;

import com.itextpdf.tool.xml.Pipeline;

import com.itextpdf.tool.xml.XMLWorker;

import com.itextpdf.tool.xml.XMLWorkerFontProvider;

import com.itextpdf.tool.xml.XMLWorkerHelper;

import com.itextpdf.tool.xml.exceptions.CssResolverException;

import com.itextpdf.tool.xml.html.CssAppliers;

import com.itextpdf.tool.xml.html.CssAppliersImpl;

import com.itextpdf.tool.xml.html.Tags;

import com.itextpdf.tool.xml.parser.XMLParser;

import com.itextpdf.tool.xml.pipeline.css.CSSResolver;

import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;

import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;

import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;

import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

import java.io.*;

public class PDFUtils {

public static byte[] html2pdf(String html) throws IOException, DocumentException,CssResolverException {

Document document = new Document(PageSize.A4);

ByteArrayOutputStream os = new ByteArrayOutputStream();

PdfWriter writer = PdfWriter.getInstance(document,os);

document.open();

XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(){

@Override

public Font getFont(String fontname, String encoding, float size, int style) {

return super.getFont(fontname == null ? "Open Sans" : fontname, encoding, size, style);

}

};

fontProvider.addFontSubstitute("lowagie", "garamond");

fontProvider.setUseUnicode(true);

CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);

HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);

htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());

CSSResolver cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(true);

Pipeline<?> pipeline = new CssResolverPipeline(cssResolver,new HtmlPipeline(htmlContext, new PdfWriterPipeline(document,writer)));

XMLWorker worker = new XMLWorker(pipeline, true);

XMLParser p = new XMLParser(worker);

p.parse(new InputStreamReader(new ByteArrayInputStream(html.getBytes("gbk"))));

document.close();

return os.toByteArray();

}

}