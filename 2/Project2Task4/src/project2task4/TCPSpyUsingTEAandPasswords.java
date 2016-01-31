
package project2task4;
import java.net.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author Amelia
 */
public class TCPSpyUsingTEAandPasswords {
    /**
     * main method to start this program on spy's side
     * @param args 
     */
    	public static void main (String args[]) {
		// arguments supply message and hostname
		Socket s = null;
		try{
			int serverPort = 7896;
			s = new Socket("localhost", serverPort);    
			DataInputStream in = new DataInputStream( s.getInputStream());
			DataOutputStream out =new DataOutputStream( s.getOutputStream());
                        Scanner sc = new Scanner(System.in);
                        System.out.println("java TCPSpyUsingTEAandPasswords");
                        //ask spy to enter the symmetric key
                        System.out.println("Enter symmetric key for TEA (taking first sixteen bytes):");
                        String myKey = sc.nextLine();
			//ask spy to enter other information
                        System.out.print("Enter your ID:");
                        String myID = sc.nextLine();
                        System.out.print("Enter your Password:");
                        String myPassword = sc.nextLine();
                        System.out.print("Enter your location:");
                        String myLoc = sc.nextLine();
                        //get tea instance with the key entered by client and encrypt the information
                        TEA myTea = new TEA(myKey.getBytes());
                        byte[] keyConfirm = myTea.encrypt("confirm".getBytes());
                        byte[] encryptedID = myTea.encrypt(myID.getBytes());
                        byte[] encryptedPassword = myTea.encrypt(myPassword.getBytes());
                        byte[] encryptedLoc = myTea.encrypt(myLoc.getBytes());
                        //send information to server
                        out.write(keyConfirm);//use to confirm if the key is correct
                        out.flush();
                        in.read();
                        out.write(encryptedID);
                        out.flush();
                        in.read();
                        out.write(encryptedPassword);
                        out.flush();
                        in.read();
                        out.write(encryptedLoc);
                        out.flush();
                        //get the message sent from server
                        int length = 0;
                        byte[] readin = new byte[1000];
                        length = in.read(readin);
                        byte[] recieveMessage = Arrays.copyOf(readin, length);
                        recieveMessage = myTea.decrypt(recieveMessage);
                        String  recieveMessages= new String(recieveMessage);
                        //print out the message
                        System.out.println(recieveMessages);
                        
                        
			
		}catch (UnknownHostException e){System.out.println("Socket:"+e.getMessage());
		}catch (EOFException e){System.out.println("EOF:"+e.getMessage());
		}catch (IOException e){System.out.println("readline:"+e.getMessage());
		}finally {if(s!=null) try {s.close();}catch (IOException e){System.out.println("close:"+e.getMessage());}}
     }
}
