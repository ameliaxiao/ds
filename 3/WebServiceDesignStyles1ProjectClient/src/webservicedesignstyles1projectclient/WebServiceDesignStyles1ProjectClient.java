/**
 * WebServiceDesignStyles1ProjectClient.java
 * Tianyue Xiao
 * tianyuex@andrew.cmu.edu
 */
package webservicedesignstyles1projectclient;

/**
 *
 * @author Amelia
 */
public class WebServiceDesignStyles1ProjectClient {

    /**
     * main method to test the web server
     * @param args 
     */
    public static void main(String[] args) {
        addSpy("Tianyue", "Spy", "Pittsburgh", "mypassword");//include my name
        System.out.println(getList());
        System.out.println(getListAsXML());
        addSpy("mikem", "spy", "Pittsburgh", "sesame");
        addSpy("joem", "spy", "North Hills", "xyz");
        addSpy("seanb", "spy commander", "South Hills", "abcdefg");
        addSpy("jamesb", "spy", "Adelaide", "sydney");
        addSpy("adekunle", "spy", "Pittsburgh", "secret");
        System.out.println(getList());
        System.out.println(getListAsXML());
        updateSpy("mikem", "super spy", "Pittsburgh", "sesame");
        System.out.println(getListAsXML());
        String result = getSpy("jamesb");
        System.out.println(result);
        deleteSpy("jamesb");
        result = getSpy("jamesb");
        System.out.println(result);
    }

    private static String addSpy(java.lang.String name, java.lang.String title, java.lang.String location, java.lang.String password) {
        amelia.CoolService_Service service = new amelia.CoolService_Service();
        amelia.CoolService port = service.getCoolServicePort();
        return port.addSpy(name, title, location, password);
    }

    private static String deleteSpy(java.lang.String name) {
        amelia.CoolService_Service service = new amelia.CoolService_Service();
        amelia.CoolService port = service.getCoolServicePort();
        return port.deleteSpy(name);
    }

    private static String getList() {
        amelia.CoolService_Service service = new amelia.CoolService_Service();
        amelia.CoolService port = service.getCoolServicePort();
        return port.getList();
    }

    private static String getListAsXML() {
        amelia.CoolService_Service service = new amelia.CoolService_Service();
        amelia.CoolService port = service.getCoolServicePort();
        return port.getListAsXML();
    }

    private static String getSpy(java.lang.String name) {
        amelia.CoolService_Service service = new amelia.CoolService_Service();
        amelia.CoolService port = service.getCoolServicePort();
        return port.getSpy(name);
    }

    private static String updateSpy(java.lang.String name, java.lang.String title, java.lang.String location, java.lang.String password) {
        amelia.CoolService_Service service = new amelia.CoolService_Service();
        amelia.CoolService port = service.getCoolServicePort();
        return port.updateSpy(name, title, location, password);
    }

}
