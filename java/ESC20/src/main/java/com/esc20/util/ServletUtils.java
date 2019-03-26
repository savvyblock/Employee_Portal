package com.esc20.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ServletUtils {

public static String forward(HttpServletRequest request, HttpServletResponse response, String src) {
try{
final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
final ServletOutputStream servletOuputStream = new ServletOutputStream() {

@Override

public void write(int b) throws IOException {

byteArrayOutputStream.write(b);

}

@Override

public boolean isReady() {

return false;

}

@Override

public void setWriteListener(WriteListener writeListener) {

}

};

final PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(byteArrayOutputStream, "UTF-8"));

response = new HttpServletResponseWrapper(response) {
	public ServletOutputStream getOutputStream() {
	return servletOuputStream;
	}
	
	public PrintWriter getWriter() {
	return printWriter;
	}
};

request.getRequestDispatcher(src).forward(request,response);

return new String(byteArrayOutputStream.toByteArray(),"utf-8");

}

catch (Exception e){

throw new RuntimeException(e);

}

}

}