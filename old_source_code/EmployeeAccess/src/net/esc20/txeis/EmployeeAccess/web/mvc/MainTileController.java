package net.esc20.txeis.EmployeeAccess.web.mvc;

import java.util.Comparator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.esc20.txeis.EmployeeAccess.domainobject.VersionInfo;
import net.esc20.txeis.EmployeeAccess.service.VersionService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class MainTileController extends AbstractController {
	protected static final Log logger = LogFactory.getLog(MainTileController.class);

	private static VersionInfo versionInfo = null;

	private VersionService versionService;
	private String view = null;


	public void setView(String view) {
		this.view = view;
	}
	
	public void setVersionService(VersionService versionService) {
		this.versionService = versionService;
	}
	
	private VersionInfo getVersionInfo() {
		if (versionInfo == null) {
			versionInfo = versionService.createVersionInfo("EmployeeAccess.war");
		}
		return versionInfo;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ModelAndView mav = new ModelAndView(view);

		mav.addObject("versionInfo", getVersionInfo());
	
		return mav;
	}

	private static final Comparator<String> COMPARE_STRING_DESC = new Comparator<String>(){
		public int compare(String o1, String o2) {
			return o2.compareTo(o1);
		}
	};

}
