package net.esc20.txeis.EmployeeAccess.web.mvc;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.tiles.AttributeContext;
import org.apache.tiles.context.TilesRequestContext;
import org.apache.tiles.preparer.PreparerException;
import org.apache.tiles.preparer.ViewPreparer;

public class ErrorViewPreparer implements ViewPreparer {

	public void execute(TilesRequestContext requestContext, AttributeContext attributeContext)
			throws PreparerException {
		
		try {
			((HttpServletResponse)requestContext.getResponse()).getOutputStream().print("<p>This is from the output stream!</p>");
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
