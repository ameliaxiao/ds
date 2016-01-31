/**
 * CoolService.java
 * Tianyue Xiao
 * tianyuex@andrew.cmu.edu
 */
package Amelia;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Amelia
 */
@WebService(serviceName = "CoolService")
public class CoolService {

    SpyList myList = SpyList.getInstance();
    /**
     * method to add a new spy to the spy list
     * @param name spy's name
     * @param title spy's title
     * @param location spy's location
     * @param password spy's password
     * @return a simple String representation of the spy
     */
    @WebMethod(operationName = "addSpy")
    public String addSpy(@WebParam(name = "name") String name, 
            @WebParam(name = "title") String title,
            @WebParam(name = "location") String location,
            @WebParam(name = "password") String password){
        Spy curSpy = new Spy(name,title,location,password);
        if(myList.exist(curSpy)){//already exists
            return "This spy already exists in the spy list. No change is made to the spy list";
            
        } else {
            myList.add(curSpy);
            return curSpy.toString();
        }
    }
    /**
     * method to update a spy's info in the list
     * @param name spy's name
     * @param title spy's title
     * @param location spy's location
     * @param password spy's password
     * @return a simple String representation of the spy
     */
    @WebMethod(operationName = "updateSpy")
    public String updateSpy(@WebParam(name = "name") String name,
            @WebParam(name = "title") String title,
            @WebParam(name = "location") String location,
            @WebParam(name = "password") String password){
        Spy curSpy = new Spy(name,title,location,password);
        if(myList.exist(curSpy)){//already exists
            myList.add(curSpy);
            return curSpy.toString();
            
        } else {
            return "This spy does not exist in the spy list. no update was made";
        }
    }
    /**
     * method to get a spy by name
     * @param name spy's name
     * @return an XML string representing the spy
     */
    @WebMethod(operationName = "getSpy")
    public String getSpy(@WebParam(name = "name") String name) {
        if(myList.exist(name)){
            return myList.get(name).toXML();
        } else {
            return "No such spy";
        }
        
    }
    /**
     * method to delete a spy from the list
     * @param name spy's name
     * @return string indicating the deletion is successful or not
     */
    @WebMethod(operationName = "deleteSpy")
    public String deleteSpy(@WebParam(name = "name") String name){
        if(myList.exist(name)){
            Spy curSpy = myList.get(name);
            myList.delete(curSpy);
            return "Spy "+curSpy.getName()+" was deleted from the list.";
        } else {
            return "No spy with name "+name+" exists in the list.";
        }
    }
    /**
     * method to get spies in the list as a simple string
     * @return A simple string that contains all of the spy data on the list
     */
    @WebMethod(operationName = "getList")
    public String getList(){
        return myList.toString();
    }
    /**
     * method to get spies in the list as xml
     * @return An XML simple stringthat contains all of the spy data on the list.
     */
    @WebMethod(operationName = "getListAsXML")
    public String getListAsXML(){
        return myList.toXML();
    }
}
