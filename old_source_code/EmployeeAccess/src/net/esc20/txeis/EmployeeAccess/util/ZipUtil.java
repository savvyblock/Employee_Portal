package net.esc20.txeis.EmployeeAccess.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil
{
	public static void zip(String sourceDirectory, String zipPath, String baseDirectory) throws IOException
	{
		File f = new File(zipPath);
		FileOutputStream fos = new FileOutputStream(f);
		ZipOutputStream zos = new ZipOutputStream(fos);
		zip(new File(sourceDirectory), new File(baseDirectory), zos);
		zos.close();
		fos.close();
	}
	
	private static void zip(File directory, File base, ZipOutputStream zos) throws IOException 
	{
		File[] files = directory.listFiles();
		byte[] buffer = new byte[8192];
		int read = 0;
		
		for (int i = 0, n = files.length; i < n; i++) 
		{
			if (files[i].isDirectory()) 
			{
				zip(files[i], base, zos);
			} 
			else 
			{
		        FileInputStream in = new FileInputStream(files[i]);
		        ZipEntry entry = new ZipEntry(files[i].getPath().substring(
		            base.getPath().length() + 1));
		        zos.putNextEntry(entry);
		        
		        while (-1 != (read = in.read(buffer))) 
		        {
		        	zos.write(buffer, 0, read);
		        }
		        
		        in.close();
			}
		}
	}
	
	public static void unzip(String path) throws IOException
	{
		FileInputStream fin = new FileInputStream(path);
		ZipInputStream zin = new ZipInputStream(fin);
		ZipEntry ze = null;
		File o = new File(path);
		
		while ((ze = zin.getNextEntry()) != null) 
		{
			String name = ze.getName();
			int nameIndex = name.lastIndexOf("\\");
			
			if(nameIndex >= 0)
			{
				name = name.substring(nameIndex + 1, name.length());
			}
			
			String filePath = o.getParentFile().getPath() + "\\" + name;
			File f = new File(filePath);
			f.createNewFile();
			FileOutputStream fout = new FileOutputStream(filePath);
			StringBuffer s = new StringBuffer();
			
			for (int c = zin.read(); c != -1; c = zin.read()) {
				s.append((char)c);
		    }
			
			fout.write(s.toString().getBytes("UTF-8"));
			zin.closeEntry();
		    fout.close();
		}
		
		zin.close();
		fin.close();
	}
}