package Webserver;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;

import DatabaseAccess.ExtDBAccess;
import DatabaseAccess.ExtDBAccessI;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Servlet implementation class OwnerServlet
 */
@WebServlet("/OwnerServlet")
public class OwnerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OwnerServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * Displays the owner login
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		Configuration cfg = new Configuration();

		cfg.setDirectoryForTemplateLoading(new File("./templates"));

		Template template = cfg.getTemplate("login.ftl");

		Map<String, Object> input = new HashMap<String, Object>();
		input.put("title", "Owner Login");
		input.put("activeTab", "owner");

		try {
			template.process(input, out);
		} catch (TemplateException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * This is where owner updates the wait time
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		
		String businessId = request.getParameter("businessID");
		String waitEstimate = request.getParameter("WaitTime");

		ExtDBAccessI db = ExtDBAccess.getInstance();
		db.addOwnerWaitTimeData(businessId, DateTime.now(), Integer.parseInt(waitEstimate));
		
		Configuration cfg = new Configuration();

		cfg.setDirectoryForTemplateLoading(new File("./templates"));

		Template template = cfg.getTemplate("ownersuccess.ftl");

		Map<String, Object> input = new HashMap<String, Object>();
		input.put("title", "Owner Success");
		input.put("activeTab", "owner");
		

		try {
			template.process(input, out);
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		
		

	}


}
