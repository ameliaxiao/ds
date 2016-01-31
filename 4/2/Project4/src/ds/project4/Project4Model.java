/**
 * Tianyue Xiao tianyuex@andrew.cmu.edu
 */
package ds.project4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
/**
 * model class to do search on Odata
 * @author Amelia
 *
 */
public class Project4Model {
	/**
	 * method to do search on OData
	 * @param id product id to search
	 * @return
	 */
	public String doSearch(String id) {

		try {
			// open url and get response
			URL url = new URL(
					"http://services.odata.org/Northwind/Northwind.svc/Products("
							+ id + ")/Order_Details?$format=json");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			in.close();
			// parse json
			String json = sb.toString();
			JSONObject obj = new JSONObject(json);
			JSONArray arr = obj.getJSONArray("value");
			StringBuilder result = new StringBuilder();
			result.append(" \"result\":\"");
			result.append("There are " + arr.length()
					+ " orders containing product " + id + " as listed below: ");
			for (int i = 0; i < arr.length(); i++) {
				JSONObject cur = arr.getJSONObject(i);

				result.append(cur.getString("OrderID") + ". ");
			}
			result.append("\"");
			return result.toString();
		} catch (Exception e) {

		}
		return null;

	}

}
