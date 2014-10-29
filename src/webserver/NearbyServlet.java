package Webserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import DatabaseAccess.ExtDBAccess;
import GeneralClasses.AutocompleteHelper;
import GeneralClasses.Tag;

/**
 * Servlet implementation class NearbyServlet
 */
@WebServlet("/NearbyServlet")
public class NearbyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NearbyServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");

		List<String> nearby = ExtDBAccess.getInstance().findNearbyRestaurants(Double.parseDouble(longitude), Double.parseDouble(latitude), 10);

		Gson gson = new Gson();

		List<String> tagStrings = new LinkedList<String>();

		for (String tag : nearby){
			Tag t = gson.fromJson(tag, Tag.class);
			tagStrings.add(t.toString());
		}


		String arr = AutocompleteHelper.toJSONArray(tagStrings);

		out.write(arr);


		//		// THIS IS DUMMY DATA UNTIL WE HOOK IT UP TO THE DATABASE
		//
		//		String r1 = "Chipotle (3811 Walnut St, Philadelphia PA 19104) **id:1**";
		//		String r2 = "Bobby's Burger Palace (3800 Walnut St, Philadelphia PA 19104) **id:5**";
		//		String r3 = "Jimmy John's (4302 Walnut St, Philadelphia PA 19104) **id:6**";
		//
		//		String dummyData = "[\"" + r1 + "\",\"" + r2 + "\",\"" + r3 + "\"]";
		//		out.println(dummyData);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
