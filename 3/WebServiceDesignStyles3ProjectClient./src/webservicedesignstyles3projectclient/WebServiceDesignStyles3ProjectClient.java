/**
 * WebServiceDesignStyles3ProjectClient.java
 * Tianyue Xiao
 * tianyuex@andrew.cmu.edu
 */
package webservicedesignstyles3projectclient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Amelia
 */
public class WebServiceDesignStyles3ProjectClient {

    /** main method to test the server
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Begin main");
        Spy spy1 = new Spy("mikem", "spy", "Pittsburgh", "sesame");
        Spy spy2 = new Spy("joem", "spy", "Philadelphia", "obama");
        Spy spy3 = new Spy("seanb", "spy commander", "Adelaide", "pirates");
        Spy spy4 = new Spy("jamesb", "007", "Boston", "queen");
        System.out.println(doPut(spy1)); // 200
        System.out.println(doPut(spy2)); // 200
        System.out.println(doPut(spy3)); // 200
        System.out.println(doPut(spy4)); // 200
        System.out.println(doDelete("joem")); // 200
        spy1.setPassword("Doris");
        System.out.println(doPost(spy1)); // 200
        System.out.println(doGetListAsXML()); // display xml
        System.out.println(doGetListAsText()); // display text
        System.out.println(doGetSpyAsXML("mikem")); // display xml
        System.out.println(doGetSpyAsText("joem")); // 404
        System.out.println(doGetSpyAsXML("mikem")); // display xml
        System.out.println(doPut(spy2)); // 200
        System.out.println(doGetSpyAsText("joem")); // display text
        System.out.println("End main");
    }

    /**
     * method to add new spy to server
     * @param curSpy the spy to add
     * @return return 200 if success, 405 if not
     */
    private static int doPut(Spy curSpy) {
        String body = curSpy.toXML();
        try {
            URL url = new URL("http://localhost:8080/WebServiceDesignStyles3ProjectServer/SpyListCollection?name=" + curSpy.getName());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Accept", "text/plain");
            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.write(body.getBytes());
            out.flush();

            int responseCode = con.getResponseCode();
            out.close();
            if (responseCode == 201) {
                return 200;
            }
            return responseCode;

        } catch (Exception e) {
            System.out.print(e);
        }
        return 0;
    }

    /**
     * method to get the xml representation of the spy list
     * @return a string of the xml representation of the spy list
     */
    public static String doGetListAsXML() {
        try {
            URL url = new URL("http://localhost:8080/WebServiceDesignStyles3ProjectServer/SpyListCollection");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "text/xml");
            //get response
            int responseCode = con.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            return sb.toString();

        } catch (Exception e) {
            System.out.print(e);
        }
        return "";
    }

    /**
     * method to get the text representation of the spy list
     * @return a string of the text representation of the spy list
     */
    public static String doGetListAsText() {
        try {
            URL url = new URL("http://localhost:8080/WebServiceDesignStyles3ProjectServer/SpyListCollection");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "text/plain");
            //get response
            int responseCode = con.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            return sb.toString();

        } catch (Exception e) {
            System.out.print(e);
        }
        return "";
    }

    /**
     * method to get the xml representation of a specific spy
     * @param name the name of the spy
     * @return a string a the xml representation of a specific spy
     */
    private static String doGetSpyAsXML(String name) {
        try {
            URL url = new URL("http://localhost:8080/WebServiceDesignStyles3ProjectServer/SpyListCollection?name=" + name);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "text/xml");
            //get response
            int responseCode = con.getResponseCode();
            //not found
            if (responseCode == 404) {
                return "404";
            }
            //found
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            return sb.toString();

        } catch (Exception e) {
            System.out.print(e);
        }
        return "";
    }

    /**
     * method to get the text representation of a specific spy
     * @param name the name of the spy
     * @return a string a the text representation of a specific spy
     */
    private static String doGetSpyAsText(String name) {
        try {
            URL url = new URL("http://localhost:8080/WebServiceDesignStyles3ProjectServer/SpyListCollection?name=" + name);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "text/plain");
            //get response
            int responseCode = con.getResponseCode();
            //not found
            if (responseCode == 404) {
                return "404";
            }
            //found
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            return sb.toString();

        } catch (Exception e) {
            System.out.print(e);
        }
        return "";
    }

    /**
     * method to delete a spy from the list
     * @param name the name of the spy to delete
     * @return response code indicate the deletion is successful or not
     */
    private static int doDelete(String name) {
        try {
            URL url = new URL("http://localhost:8080/WebServiceDesignStyles3ProjectServer/SpyListCollection?name=" + name);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("DELETE");
            con.setRequestProperty("Accept", "text/plain");
            //get response
            int responseCode = con.getResponseCode();
            return responseCode;

        } catch (Exception e) {
            System.out.print(e);
        }
        return 0;
    }

    /**
     * method to change the information of spy in the list
     * @param curSpy the spy to change
     * @return response code indicate the change is successful or not
     */
    private static int doPost(Spy curSpy) {
        String body = curSpy.toXML();
        try {
            URL url = new URL("http://localhost:8080/WebServiceDesignStyles3ProjectServer/SpyListCollection");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept", "text/plain");
            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.write(body.getBytes());
            out.flush();

            int responseCode = con.getResponseCode();
            out.close();
            return responseCode;

        } catch (Exception e) {
            System.out.print(e);
        }
        return 0;
    }
}
