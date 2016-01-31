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
    SpyMessage sm;
    /**
     * method to do multiple operations on the spy list
     * @param message string containing the operation and targeting spy
     * @return return the result of different operation
     */
    @WebMethod(operationName = "doSpyOperation")
    public String doSpyOperation(@WebParam(name = "message") String message) {
        sm = new SpyMessage(message);
        String operation = sm.getOperation();
        Spy spy = sm.getSpy();
        String result = "";
        switch(operation) {
            case "addSpy": 
                result = addSpy(spy.getName(),spy.getTitle(),spy.getLocation(),spy.getPassword());
                break;
            case "updateSpy": 
                result = updateSpy(spy.getName(),spy.getTitle(),spy.getLocation(),spy.getPassword());
                break;
            case "getSpy": 
                result = getSpy(spy.getName());
                break;
            case "deleteSpy": 
                result = deleteSpy(spy.getName());
                break;
            case "getList": 
                result = getList();
                break;
            case "getListAsXML": 
                result = getListAsXML();
                break;
            
            
        }
        return result;
    }
    
    SpyList myList = SpyList.getInstance();
    /**
     * method to add a new spy to the spy list
     * @param name spy's name
     * @param title spy's title
     * @param location spy's location
     * @param password spy's password
     * @return a simple String representation of the spy
     */
    private String addSpy( String name, 
            String title,
            String location,
            String password){
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
    
    private String updateSpy(String name,
            String title,
            String location,
            String password){
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
    private String getSpy( String name) {
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
    private String deleteSpy(String name){
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
    private String getList(){
        return myList.toString();
    }
    /**
     * method to get spies in the list as xml
     * @return An XML simple stringthat contains all of the spy data on the list.
     */
    private String getListAsXML(){
        return myList.toXML();
    }


}
