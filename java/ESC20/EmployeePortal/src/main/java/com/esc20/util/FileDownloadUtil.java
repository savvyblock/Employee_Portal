package com.esc20.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;


public class FileDownloadUtil {

	public static void downloadPictureFile(HttpServletRequest request, String fileName, HttpServletResponse response) {
		if (StringUtils.isEmpty(fileName)) {
			//throw new CustomException(0, "File name does not exist");
		}
		try {
			if (fileName.startsWith("http")) {

				URL url = new URL(fileName);
				DataInputStream dataInputStream = new DataInputStream(url.openStream());
				byte[] buffer = new byte[1024];
				int length;
				// length = dataInputStream.read(buffer);

				response.reset();
				response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes()));
				if (fileName.indexOf("ascender_pecan_logo.jpg") >= 0) {
					response.addHeader("Content-Length", "109763");
				} else if (fileName.indexOf("GetStudentPicture.png") >= 0) {
					response.addHeader("Content-Length", "2799");
				} else {
					response.addHeader("Content-Length", "112048");
				}

				OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
				while ((length = dataInputStream.read(buffer)) > 0) {
					toClient.write(buffer, 0, length);
				}
				toClient.flush();
				toClient.close();
			} else {
				String filePath = fileName;
				File file = new File(filePath);
				InputStream fis = new BufferedInputStream(new FileInputStream(filePath));
				byte[] buffer = new byte[fis.available()];
				fis.read(buffer);
				fis.close();
				response.reset();
				response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes()));
				response.addHeader("Content-Length", "" + file.length());
				OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
				toClient.write(buffer);
				toClient.flush();
				toClient.close();
			}

		} catch (Exception e) {
			//throw new CustomException(0, "Download file error");
		}
	}
}
