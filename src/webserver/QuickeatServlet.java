package Webserver;

import DatabaseAccess.ExtDBAccess;
import DatabaseAccess.ExtDBAccessI;
import GeneralClasses.Restaurant;
import GeneralClasses.HTMLHelper;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Servlet implementation class QuickeatServlet
 */
@WebServlet("/QuickeatServlet")
public class QuickeatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QuickeatServlet() {
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

		Template template = cfg.getTemplate("quickeat.ftl");

		Map<String, Object> input = new HashMap<String, Object>();
		input.put("title", "Quickeat");
		input.put("activeTab", "quickeat");

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

		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");

		PrintWriter out = response.getWriter();


		ExtDBAccessI db = ExtDBAccess.getInstance();
		List<String> quickEats = db.getQuickEat(Double.parseDouble(longitude), Double.parseDouble(latitude), 10);

		List<Restaurant> restaurants = new LinkedList<Restaurant>();
		
		Gson gson = new Gson();
		
		for (String r : quickEats){
			Restaurant restaurant = gson.fromJson(r, Restaurant.class);
			restaurants.add(restaurant);
		}

		String htmlTable = HTMLHelper.RestaurantsToHTMLTable(restaurants);

		out.println("<html>");
		out.println("<body>");

		out.println(htmlTable);

		out.println("</body>");
		out.println("</html>");
	}

}
