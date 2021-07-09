package com.esc20.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileUtil {
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
   
	private static String picturesDirectory;

	@Value("${PicturesDirectory}")
	public void setPicturesDirectory(String picturesDirectory) {
		FileUtil.picturesDirectory = picturesDirectory;
	}

	public String getPicturesDirectory() {
		return FileUtil.picturesDirectory;
	}

	public static String DEFAULT_UPLOAD_STU_PIC_PATH = "/PicturesDirectory/";
	public static String DEFAULT_DISTRICT_PIC_FILE = "images/ascender_pecan_logo.jpg";

	public static String getStuPicPhysicalPath(HttpServletRequest req, String pStudentId) {
		String districtId = (String) req.getSession().getAttribute("srvcId");
		String studentPicturePath = getStudentPictureUploadPath(districtId);

		List<String> fileExtensionsList = Arrays.asList(".jpg", ".jpeg", ".gif", ".png", ".JPG", ".JPEG", ".GIF",
				".PNG");

		String temp = getPathDir(studentPicturePath);

		for (String extension : fileExtensionsList) {
			studentPicturePath = temp + StringUtil.trim(pStudentId) + extension;
			if (isFileExist(studentPicturePath)) {
				return studentPicturePath;
			}
		}

		return getDefaultPicturePhysicalPath(req);
	}

	public static boolean isFileExist(String filePath) {
		File file = new File(filePath);

		return file.exists();
	}

	public static String getPathDir(String dir) {

		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}

		return dir;
	}

	public static String getStudentPictureUploadPath(String districtId) {

		return getPathDir(picturesDirectory) + districtId;
	}

	
	public static String getDistrictPicPhysicalPath(HttpServletRequest req) {
		String districtId = (String) req.getSession().getAttribute("srvcId");
		String studentPicturePath = getStudentPictureUploadPath(districtId);

//        studentPicturePath =  getPathDir(studentPicturePath) + "dist_picture/dist_picture.jpg";
//
//        if (isFileExist(studentPicturePath)) {
//            return studentPicturePath;
//        }
		List<String> fileExtensionsList = Arrays.asList(".jpg", ".jpeg", ".gif", ".png", ".JPG", ".JPEG", ".GIF",
				".PNG");

		String temp = getPathDir(studentPicturePath);

		for (String extension : fileExtensionsList) {
			studentPicturePath = temp + "dist_picture/dist_picture" + extension;
			if (isFileExist(studentPicturePath)) {
				return studentPicturePath;
			}
		}
        //ALC-13  if there is not pic in folder then did not show up the picture
		return "";
		//return getDefaultDistrictPicPhysicalPath(req);
	}

	public static String getDistrictLogoPhysicalPath(HttpServletRequest req) {
		String districtId = (String) req.getSession().getAttribute("srvcId");
		String logoPicturePath = getStudentPictureUploadPath(districtId);

		List<String> fileExtensionsList = Arrays.asList(".jpg", ".jpeg", ".gif", ".png", ".JPG", ".JPEG", ".GIF",
				".PNG");

		String temp = getPathDir(logoPicturePath);

		for (String extension : fileExtensionsList) {
			logoPicturePath = temp + "dist_picture/dist_logo" + extension;
			if (isFileExist(logoPicturePath)) {
				return logoPicturePath;
			}
		}
		
		return getDefaultLogoPhysicalPath(req);
	}
	
	
	
	private static String getDefaultLogoPhysicalPath(HttpServletRequest req) {
		String defaultPicturePath = getPathDir(picturesDirectory) + "default_dist_logo.jpg";
		if (!isFileExist(defaultPicturePath)) {
			String url = req.getRequestURL().toString();
			int postion = url.indexOf("EmployeePortal");
			String urlPath = url.substring(0, postion) + "EmployeePortal/images/bestview_logo.gif";

			downloadDefaultPicture(urlPath, defaultPicturePath);
		}

		return defaultPicturePath;
	}
	
	
	private static String getDefaultPicturePhysicalPath(HttpServletRequest req) {
		String defaultPicturePath = getPathDir(picturesDirectory) + "default.jpg";
		if (!isFileExist(defaultPicturePath)) {
			String url = req.getRequestURL().toString();
			int postion = url.indexOf("EmployeePortal");
			String urlPath = url.substring(0, postion) + "EmployeePortal/images/GetStudentPicture.png";

			downloadDefaultPicture(urlPath, defaultPicturePath);
		}

		return defaultPicturePath;
	}

	private static void downloadDefaultPicture(String picUrl, String path) {
		try {
			File file = new File(path);

			URL url = new URL(picUrl);
			DataInputStream dataInputStream = new DataInputStream(url.openStream());
			FileOutputStream fileOutputStream = new FileOutputStream(file);

			byte[] buffer = new byte[1024];
			int length;

			while ((length = dataInputStream.read(buffer)) > 0) {
				fileOutputStream.write(buffer, 0, length);
			}

			dataInputStream.close();
			fileOutputStream.close();
		} catch (Exception e) {
			logger.error("Exception", e);
		}
	}

	public static String getDefaultDistrictPicturePath() {
		String defaultPicturePath = getPathDir(picturesDirectory) + "dist_picture_TP.jpg";
		if (isFileExist(defaultPicturePath)) {
			return FileUtil.DEFAULT_UPLOAD_STU_PIC_PATH + "dist_picture_TP.jpg";
		}

		return FileUtil.DEFAULT_DISTRICT_PIC_FILE;
	}

	public static String getDefaultDistrictPicPhysicalPath(HttpServletRequest req) {
		String defaultPicturePath = getPathDir(picturesDirectory) + "dist_picture_TP.jpg";
		if (!isFileExist(defaultPicturePath)) {
			String url = req.getRequestURL().toString();
			int postion = url.indexOf("EmployeePortal");
			String urlPath = url.substring(0, postion) + "EmployeePortal/images/ascender_pecan_logo.jpg";

			// downloadDefaultPicture(urlPath, defaultPicturePath);

			return urlPath;
		}

		return defaultPicturePath;
	}

	public static String readTxt(String filepath){
		try {
			BufferedReader read = new BufferedReader(new FileReader(filepath));
			String line = "";
			StringBuffer sb = new StringBuffer();
			while ((line = read.readLine()) != null) {
				sb.append(line);
			}
			if (read != null) {
				read.close();
			}
			return sb.toString();
		} catch (Exception e) {
			logger.error("Exception", e);
			return "";
		}
	}
}
