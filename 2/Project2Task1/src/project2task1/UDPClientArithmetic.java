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
public class UDPClientArithmetic {
    /**
     * 
     * main method to start a UDPClient
     */
    public static void main(String args[]) {
        DatagramSocket aSocket = null;
        try {
            //print out the instruction lines
            System.out.println("java UDPClient");
            System.out.println("Enter simple postfix expression to be computed by the server:");
            Scanner sc = new Scanner(System.in);
            //read the integers and operator
            String message = sc.nextLine(); 
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
            System.out.println(answer);
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (aSocket != null) {
                aSocket.close();
            }
        }
    }
}
