/**
 * 
 */
package net.esc20.txeis.EmployeeAccess.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import net.esc20.txeis.EmployeeAccess.domainobject.VersionInfo;

import org.springframework.stereotype.Service;

/**
 * @author drodriguez
 * Purpose of this class is to return the application version information
 */
@Service
public final class VersionService {

	private String displayBuild;
	
	public void setDisplayBuild(String displayBuild) {
		this.displayBuild = displayBuild;
	}

	@SuppressWarnings("unchecked")
	public VersionInfo createVersionInfo(String warname) {
		Manifest manifest = null;
		URL resourceURL = null;
		Class clazz= this.getClass();
		
		if (clazz == null) {
			return createEmptyVersionInfo();
		}
	
		resourceURL = clazz.getResource(clazz.getSimpleName() + ".class");
		
		//war file is packaged
		if (resourceURL.getPath().indexOf(warname) == -1) 
		{
			manifest = getPackedManifest(resourceURL.toString(), warname);
		}
		else
		//The war file is expanded
		{  
			manifest = getExpandedManifest(resourceURL.toString(), warname);
		}
	
		if (manifest == null) {
			return null;
		}
		
		return getVersionInfo(manifest.getMainAttributes());
	}
	
	private VersionInfo createEmptyVersionInfo() {
		return new VersionInfo("no version number", "no build number", false);
	}
		
	private VersionInfo getVersionInfo(Attributes attr) {
		boolean showBuild = true;
		
		if (displayBuild != null) {
			showBuild = Boolean.parseBoolean(displayBuild);
		} else {
			showBuild = Boolean.parseBoolean(attr.getValue("Show_Build"));
		}

		return new VersionInfo(attr.getValue("Version"), attr.getValue("Build_Number"), showBuild);
	}
	
	private Manifest getPackedManifest(String pathToThisClass, String warname) {
		int mark = 0;
		String pathToManifest;
		
		mark = pathToThisClass.indexOf("default") ;
		if (mark == -1) {
			return null;
		}
		pathToManifest = pathToThisClass.toString().substring(0, mark) ;
		pathToManifest = pathToManifest.substring(6); //Need to strip out the 'file:/'
		pathToManifest += "default/deploy/" + warname;
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(java.net.URLDecoder.decode(pathToManifest, "UTF-8"));
			return jarFile.getManifest();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}			
	}
	
	private Manifest getExpandedManifest(String pathToThisClass, String warname) {
		int mark = 0;
		String pathToManifest;
		URL pathURL = null;
		
		mark = pathToThisClass.indexOf(warname) ;
		if (mark == -1) {
			return null;
		}
		pathToManifest = pathToThisClass.toString().substring(0, mark + warname.length()) ;
		pathToManifest += "/META-INF/MANIFEST.MF" ;
		try {
			pathURL = new URL(pathToManifest);
			return new Manifest(pathURL.openStream());	
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public String getHelpURL() {
		try {
			Properties properties = new Properties();
			properties.load(this.getClass().getResourceAsStream("/txeis.properties"));
			
			//Form the new Help URL
			String helpURL  =  properties.getProperty("HelpURL");
			String helpURLBase = "";
			helpURLBase = helpURL+"employeeaccess\\doku.php\\";
			return helpURLBase;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}

