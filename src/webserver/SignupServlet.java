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

import DatabaseAccess.ExtDBAccess;
import DatabaseAccess.ExtDBAccessI;
import GeneralClasses.AutocompleteHelper;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignupServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
		Configuration cfg = new Configuration();

		cfg.setDirectoryForTemplateLoading(new File("./templates"));

		Template template = cfg.getTemplate("signup.ftl");

		Map<String, Object> input = new HashMap<String, Object>();
		input.put("title", "Sign Up");
		input.put("activeTab", "signup");
		
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
		// commit new user to database

		PrintWriter out = response.getWriter();

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String businessId = request.getParameter("businessID");
		
		String bId = AutocompleteHelper.extractBusinessID(businessId);
		
		ExtDBAccessI db = ExtDBAccess.getInstance();
		boolean success = db.registerOwner(username, password, bId);
		if (success) {
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
		} else{
			out.print("Sign up failed.  Either the username you entered is already taken, or your username contains an invalid character.");
		}
	}

}
