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
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Owner is attempting to log in
		
				PrintWriter out = response.getWriter();

				String username = request.getParameter("username");
				String password = request.getParameter("password");
				
				ExtDBAccessI db = ExtDBAccess.getInstance();
				String businessId = db.getBusinessForOwner(username, password);
				
				if (businessId == null){
					//user validation failed
					out.print("Login failed.  Either your username or password is incorrect.");
				} else{

					Configuration cfg = new Configuration();

					cfg.setDirectoryForTemplateLoading(new File("./templates"));

					Template template = cfg.getTemplate("owner.ftl");

					Map<String, Object> input = new HashMap<String, Object>();
					input.put("title", "Owner");
					input.put("activeTab", "owner");
					input.put("businessID", businessId);

					try {
						template.process(input, out);
					} catch (TemplateException e) {
						e.printStackTrace();
					}
				}
	}

}
