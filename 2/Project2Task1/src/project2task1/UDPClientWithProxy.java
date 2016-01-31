/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2task1;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author Amelia
 */
public class UDPClientWithProxy {
    /**
     * 
     * main method to get user's k and calculate the sum 
     */
    public static void main(String[] args){
        //print out the instruction
        System.out.println("java UDPClientWithProxy");
        System.out.println("Enter an integer >= 1");
        try{
            Scanner sc = new Scanner(System.in);
            int k = sc.nextInt();
            //return if k is equal to 1
            if(k == 1){
                System.out.println("1 = 1");
                return;
            }
            String answer = "1";
            int ans = 1;
            //calculate sum value by calling add method
            for(int i = 2; i <= k; i++){
                answer += " + " +i;
                ans = add(ans, i);
            }
            answer += " = " + ans;
            //print out the answer
            System.out.println(answer);
            
            
        } catch (Exception e){
            System.out.println(e);
        }
    }
    /**
     * method to calculate sum value of two integer by using sending request to udpserver
     * @param x integer to calculate sum form 
     * @param y integer to calculate sum form 
     * @return integer representing the sum value
     */
    public static int add(int x,int y){
        DatagramSocket aSocket = null;
        try {

            String message = x + " " + y + " " + "+"; 
            aSocket = new DatagramSocket();
            
            InetAddress aHost = InetAddress.getByName("localhost");
            int serverPort = 6789;
            //build request and send
            DatagramPacket request
                    = new DatagramPacket(message.getBytes(), message.getBytes().length, aHost, serverPort);
            aSocket.send(request);
            byte[] buffer = new byte[1000];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            //get the answer and print it out
            aSocket.receive(reply);
            String answer = new String(Arrays.copyOf(reply.getData(), reply.getLength()));
            int sum = Integer.parseInt(answer.substring(answer.lastIndexOf(" ") + 1));
            return sum;
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (aSocket != null) {
                aSocket.close();
            }
        }
        return 0;
    }
}
