package net.esc20.txeis.EmployeeAccess.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tiles.Attribute;
import org.apache.tiles.Definition;
import org.apache.tiles.impl.BasicTilesContainer;
import org.apache.tiles.servlet.context.ServletTilesRequestContext;
import org.apache.tiles.servlet.context.ServletUtil;
import org.springframework.js.ajax.AjaxHandler;
import org.springframework.js.ajax.SpringJavascriptAjaxHandler;
import org.springframework.web.servlet.support.JstlUtils;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.view.tiles2.TilesView;
import org.springframework.webflow.mvc.view.FlowAjaxTilesView;

/*
 * 
 * When upgrading Employee Access to Spring 3.2.11 and Tiles 2.2.2, existing tiles definitions rendering the results of WebFlow AJAX calls fails, 
 * at least when using the Spring class FlowAjaxTilesView as the view class for the tilesViewResolver bean.  Attempting to fix the problem by using 
 * Spring's AjaxTilesView as the view class, which should be compatible with Spring 3.2.11 and Tiles 2.2.2, proved elusive.
 * This class changes the way the ajax results are rendered yet preserves default processing for non-AJAX requests.
 * This class was cloned from one provided at https://jira.spring.io/browse/SWF-1220.
 * 
 */

public class FlowAjaxTiles21View extends FlowAjaxTilesView {
	private AjaxHandler ajaxHandler = new SpringJavascriptAjaxHandler();

	/**
	 * Render the tiles. If its an ajax request then handle that using the
	 * custom code for tiles 2.1 and later versions. Otherwise just do the default rendering.
	 */
	@SuppressWarnings("unchecked")
	protected void renderMergedOutputModel(Map model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ServletContext servletContext = getServletContext();
		if (ajaxHandler.isAjaxRequest(request, response)) {
			handleAjaxRequest(model, request, response, servletContext);
		} else {
			super.renderMergedOutputModel(model, request, response);
		}
	}

	/**
	 * Handle the Ajax request; this is custom code for Tiles 2.1 and later versions
	 * interface.
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param servletContext
	 * @throws Exception
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private void handleAjaxRequest(Map model, HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext)
			throws Exception, ServletException, IOException {
		String[] attrNames = getRenderFragments(model, request, response);
		if (attrNames.length == 0) {
			logger.warn("An Ajax request was detected, but no fragments were specified to be re-rendered. "
					+ "Falling back to full page render. This can cause unpredictable results when processing "
					+ "the ajax response on the client.");
			super.renderMergedOutputModel(model, request, response);
			return;
		}
		BasicTilesContainer container = (BasicTilesContainer) ServletUtil
				.getContainer(servletContext);
		if (container == null) {
			throw new ServletException(
					"Tiles container is not initialized. "
							+ "Have you added a TilesConfigurer to your web application context?");
		}
		exposeModelAsRequestAttributes(model, request);
		JstlUtils.exposeLocalizationContext(new RequestContext(request,
				servletContext));
		ServletTilesRequestContext tilesRequestContext = new ServletTilesRequestContext(
				container.getApplicationContext(), request, response);
		Definition compositeDefinition = container.getDefinitionsFactory()
				.getDefinition(getUrl(), tilesRequestContext);
		Map flattenedAttributeMap = new HashMap();
		flattenAttributeMap(container, tilesRequestContext,
				flattenedAttributeMap, compositeDefinition, request, response);
		Object obj = flattenedAttributeMap.get("results");
		// initialize the session before rendering any fragments. Otherwise
		// views that require the session which has
		// not otherwise been initialized will fail to render
		request.getSession();
		response.flushBuffer();
		for (String attr : attrNames) {
			Attribute attrib = compositeDefinition.getAttribute(attr);
			if (attrib==null) {
				attrib = (Attribute)flattenedAttributeMap.get(attr);
			}
			container.render(attrib, new Object[] { request, response });
		}
	}
}