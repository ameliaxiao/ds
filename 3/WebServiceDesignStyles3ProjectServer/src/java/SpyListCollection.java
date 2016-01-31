/**
 * SpyListCollection.java
 * Tianyue Xiao
 * tianyuex@andrew.cmu.edu
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Amelia
 */
@WebServlet(name = "SpyListCollection", urlPatterns = {"/SpyListCollection/*"})
public class SpyListCollection extends HttpServlet {

    SpyList myList;
    /**
     * method to get spylist instance
     */
    @Override
    public void init() {
        myList = SpyList.getInstance();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        //get the whole list
        if (name == null) {
            response.setStatus(200);
            if (request.getHeader("Accept").equals("text/plain")) {
                response.getWriter().write(myList.toString());
                response.getWriter().flush();
                response.getWriter().close();

            } else {
                response.getWriter().write(myList.toXML());
                response.getWriter().flush();
                response.getWriter().close();
            }
        } else {//get a specific spy
            if (myList.exist(name)) {
                response.setStatus(200);
                Spy curSpy = myList.get(name);
                if (request.getHeader("Accept").equals("text/plain")) {
                    response.getWriter().write(curSpy.toString());
                    response.getWriter().flush();
                    response.getWriter().close();
                } else {
                    response.getWriter().write(curSpy.toXML());
                    response.getWriter().flush();
                    response.getWriter().close();
                }
            } else {
                response.setStatus(404);
                response.getWriter().write("no such spy");
                response.getWriter().flush();
                response.getWriter().close();
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //get the body

        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String body = sb.toString();
        // do add
        SpyMessage message = new SpyMessage(body);
        Spy curSpy = message.getSpy();
        if(!myList.exist(curSpy)){//spy is not on the list
            response.setStatus(404);
        } else {
            response.setStatus(200);
            myList.add(curSpy);
        }
        
    }

    /**
     * Handles the HTTP <code>PUT</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //get the body

        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String body = sb.toString();
        // do add
        SpyMessage message = new SpyMessage(body);
        Spy curSpy = message.getSpy();
        boolean result = addSpy(curSpy.getName(), curSpy.getTitle(), curSpy.getLocation(), curSpy.getPassword());
        //set status
        if (result) {
            response.setStatus(201);
        } else {
            response.setStatus(405);
        }
    }

    /**
     * Handles the HTTP <code>DELETE</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        if(!myList.exist(name)){
            response.setStatus(404);
        } else {
            response.setStatus(200);
            Spy curSpy = myList.get(name);
            myList.delete(curSpy);
        }
    }

    /**
     * method to add a new spy to the spy list
     *
     * @param name spy's name
     * @param title spy's title
     * @param location spy's location
     * @param password spy's password
     * @return a boolean indicate successfully added a spy or not
     */
    private boolean addSpy(String name,
            String title,
            String location,
            String password) {
        Spy curSpy = new Spy(name, title, location, password);
        if (myList.exist(curSpy)) {//already exists
            return false;

        } else {
            myList.add(curSpy);
            return true;
        }
    }
}