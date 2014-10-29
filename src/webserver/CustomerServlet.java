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

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import DatabaseAccess.ExtDBAccess;
import GeneralClasses.AutocompleteHelper;

/**
 * Servlet implementation class Customer
 */
@WebServlet("/Customer")
public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CustomerServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		Configuration cfg = new Configuration();

		cfg.setDirectoryForTemplateLoading(new File("./templates"));

		Template template = cfg.getTemplate("customer.ftl");

		Map<String, Object> input = new HashMap<String, Object>();
		input.put("title", "Customer");
		input.put("activeTab", "customer");
		input.put("thankYou", 0);

		try {
			template.process(input, out);
		} catch (TemplateException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("in customer post.");

		PrintWriter out = response.getWriter();

		try {

			String businessId = request.getParameter("BusinessID");
			String dateTime = request.getParameter("DateTime");
			String waitTime = request.getParameter("WaitTime");

			String bId = AutocompleteHelper.extractBusinessID(businessId);

			DateTime d = GeneralClasses.DateTimeHelper.parseJQuery(dateTime);
			ExtDBAccess.getInstance().addWaitTimeData(bId, d, Integer.parseInt(waitTime));


			Configuration cfg = new Configuration();

			cfg.setDirectoryForTemplateLoading(new File("./templates"));

			Template template = cfg.getTemplate("customer.ftl");

			Map<String, Object> input = new HashMap<String, Object>();
			input.put("title", "Customer");
			input.put("activeTab", "customer");
			input.put("thankYou", 1);

			try {
				template.process(input, out);
			} catch (TemplateException e) {
				e.printStackTrace();
			}
		} catch (Exception e){
			out.println("Sorry, the format of the data you entered is invalid.");
		}



	}

}
