/**
 * Tianyue Xiao tianyuex@andrew.cmu.edu
 */
package ds.project4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.servlet.http.*;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class Project4Servlet extends HttpServlet {
	/**
	 * method to process get request
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// get product id from request
		String id = req.getParameter("id");
		// instantiate a model and do search
		Project4Model model = new Project4Model();
		String result = model.doSearch(id);
		result = "{\"status\": \"success\"," + result + "}";
		// put result in response
		resp.getWriter().write(result);

	}
}
