package org.stagemonitor.web.monitor.widget;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.stagemonitor.core.Stagemonitor;
import org.stagemonitor.core.configuration.Configuration;

public class WidgetServlet extends HttpServlet {

	private final Configuration configuration;
	private final StagemonitorWidgetHtmlInjector widgetHtmlInjector;

	public WidgetServlet() {
		this(Stagemonitor.getConfiguration(), new StagemonitorWidgetHtmlInjector(true));
	}

	public WidgetServlet(Configuration configuration, StagemonitorWidgetHtmlInjector widgetHtmlInjector) {
		this.configuration = configuration;
		this.widgetHtmlInjector = widgetHtmlInjector;
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		widgetHtmlInjector.init(configuration, config.getServletContext());
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		final PrintWriter w = resp.getWriter();
		w.println("<!DOCTYPE html>");
		w.println("<html>");
		w.println("<body>");
		w.println(widgetHtmlInjector.getContentToInjectBeforeClosingBody(null));
		w.println("</body>");
		w.println("</html>");
	}
}
