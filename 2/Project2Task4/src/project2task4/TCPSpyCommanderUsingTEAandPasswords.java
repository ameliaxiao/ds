
package project2task4;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author Amelia
 */
public class TCPSpyCommanderUsingTEAandPasswords {
    //global variables
    static HashMap<String, Spy> spies = new HashMap();//use to store information fot the three spies
    static TEA myTea = null;//tea instance on commander side
    static int count = 1;//count it is the ith visitor
/**
 * main method to start this program on commander's side
 * @param args 
 */
    public static void main(String args[]) {
        initialize();
        //ask the user to type in the key and get tea instance
        System.out.println("java TCPSpyCommanderUsingTEAandPasswords");
        System.out.println("Enter symmetric key for TEA (taking first sixteen bytes):");
        Scanner sc = new Scanner(System.in);
        String myKey = sc.nextLine();
        //create tea instance
        myTea = new TEA(myKey.getBytes());
        try {
            int serverPort = 7896; // the server port
            ServerSocket listenSocket = new ServerSocket(serverPort);
            System.out.println("Waiting for spies to visit…");
            while (true) {
                Socket clientSocket = listenSocket.accept();
                Connection c = new Connection(clientSocket);
            }
        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        }
    }

    /**
     * method to initialize the server, set the original information of the
     * three spies, create kml file
     */
    public static void initialize() {
        spies.put("jamesb", new Spy("James","jamesb", "james"));
        spies.put("joem", new Spy("Joe","joem", "joe"));
        spies.put("mikem", new Spy("Mike","mikem", "mike"));
        update();

    }

    /**
     * method used to update the kml file
     */
    public static void update() {
        try {
            PrintWriter writer = new PrintWriter("SecretAgents.kml", "UTF-8");
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
                    + "<kml xmlns=\"http://earth.google.com/kml/2.2\""
                    + "><Document>"
                    + "<Style id=\"style1\">"
                    + "<IconStyle>"
                    + "<Icon>"
                    + "<href>http://maps.gstatic.com/intl/en_ALL/mapfiles/ms/micons/bluedot.png"
                    + "</href>"
                    + "</Icon>"
                    + "</IconStyle>"
                    + "</Style><Placemark>"
                    + "<name>jamesb</name>"
                    + "<description>Spy</description>"
                    + "<styleUrl>#style1</styleUrl>"
                    + "<Point>"
                    + "<coordinates>");
            writer.println(spies.get("jamesb").getLoc());
            writer.println("</coordinates>");
            writer.println("</Point>"
                    + "</Placemark>"
                    + "<Placemark>"
                    + "<name>joem</name>"
                    + "<description>Spy</description>"
                    + "<styleUrl>#style1</styleUrl>"
                    + "<Point>"
                    + "<coordinates>");
            writer.println(spies.get("joem").getLoc());
            writer.println("</coordinates>");
            writer.println("</Point>"
                    + "</Placemark>"
                    + "<Placemark>"
                    + "<name>mikem</name>"
                    + "<description>Spy</description>"
                    + "<styleUrl>#style1</styleUrl>"
                    + "<Point>"
                    + "<coordinates>");
            writer.println(spies.get("mikem").getLoc());
            writer.println("</coordinates>");
            writer.println("</Point>"
                    + "</Placemark>"
                    + "</Document>"
                    + "</kml>");
            writer.close();
        } catch (Exception e) {

        }

    }
}

class Connection extends Thread {

    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
/**
 * constructor to create a connection
 * @param aClientSocket the socket that this connection is based on
 */
    public Connection(Socket aClientSocket) {

        try {
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            System.out.print("Got visit "+TCPSpyCommanderUsingTEAandPasswords.count);
            TCPSpyCommanderUsingTEAandPasswords.count++;
            int length;
            byte[] readin = new byte[1000];
            //get symmetric key confirmation
            length = in.read(readin);
            byte[] keyConfirm = Arrays.copyOf(readin, length);
            keyConfirm = TCPSpyCommanderUsingTEAandPasswords.myTea.decrypt(keyConfirm);
            
            String confirm = new String(keyConfirm);
            //get the user id
            out.write(1);//inform client to go on 
            readin = new byte[1000];
            length = in.read(readin);
            byte[] curIDb = Arrays.copyOf(readin, length);
            curIDb = TCPSpyCommanderUsingTEAandPasswords.myTea.decrypt(curIDb);
            String curID = new String(curIDb);
            
            //get the password
            out.write(1);
            out.flush();
            readin = new byte[1000];
            length = in.read(readin);
            //System.out.println(length);
            byte[] curPassb = Arrays.copyOf(readin, length);
            curPassb = TCPSpyCommanderUsingTEAandPasswords.myTea.decrypt(curPassb);
            String curPass = new String(curPassb);
            
            //get the location
            out.write(1);
            out.flush();
            readin = new byte[1000];
            
            length = in.read(readin);
            byte[] curLocb = Arrays.copyOf(readin, length);
            curLocb = TCPSpyCommanderUsingTEAandPasswords.myTea.decrypt(curLocb);
            String curLoc = new String(curLocb);
            
            //if the symmetric key is wrong
            if(!confirm.equals("confirm")){
                System.out.println("illegal symmetric key used.");
                return;
            }
            
            
            //if the user id is wrong
            if(!TCPSpyCommanderUsingTEAandPasswords.spies.containsKey(curID)){
                System.out.println(" from unknown user");
                out.write(TCPSpyCommanderUsingTEAandPasswords.myTea.encrypt("Not a valid user-id or password.".getBytes()));
                out.flush();
                return;
            }
            //get the current spy
            Spy curSpy = TCPSpyCommanderUsingTEAandPasswords.spies.get(curID);
            
            //check the password
            if(!curSpy.checkPassword(curPass)){//password is wrong
                System.out.println(" from " + curSpy.getName() + ". Illegal Password attempt.");
                out.write(TCPSpyCommanderUsingTEAandPasswords.myTea.encrypt("Not a valid user-id or password.".getBytes()));
                out.flush();
                return;
            }
            
            curSpy.changeLocation(curLoc);
            //update the kml
            TCPSpyCommanderUsingTEAandPasswords.update();
            System.out.println(" from " + curSpy.getName());
            out.write(TCPSpyCommanderUsingTEAandPasswords.myTea.encrypt("Thank you. Your location was securely transmitted to Intelligence Headquarters.".getBytes()));
            out.flush();
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {/*close failed*/

            }
        }

    }

}
