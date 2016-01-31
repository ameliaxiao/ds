/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2task3;

import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;
import static project2task3.UDPServerWithDoubleArithmetic.byteArrayToLong;
/**
 *
 * @author Amelia
 */
public class UDPClientWithDoubleArithmetic {
    /**
     * 
     * main method to do double arithmetic sum
     */
    public static void main(String[] args){
        System.out.println("java UDPClientWithDoubleArithmetic");
        try{
            
            double ans = 1.25;
            //calculate sum value by calling add method
            for(int i = 2; i <= 100; i++){
                ans = add(ans, i+0.25);
            }
            //print out the answer
            System.out.println("answer is " + ans);
            
            
        } catch (Exception e){
            System.out.println(e);
        }
    }
    /**
     * method to calculate sum value of two double by using sending request to udpserver
     * @param x double to calculate sum from 
     * @param y double to calculate sum from 
     * @return double representing the sum value
     */
    public static double add(double x,double y){
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket();
            
            InetAddress aHost = InetAddress.getByName("localhost");
            int serverPort = 6789;
            //convert the numbers to byte array
            long xl = Double.doubleToLongBits(x);
            long yl = Double.doubleToLongBits(y);
            byte[] xBytes = ByteBuffer.allocate(8).putLong(xl).array();
            byte[] yBytes = ByteBuffer.allocate(8).putLong(yl).array();
            //send three separate request to the server
            DatagramPacket request
                    = new DatagramPacket(xBytes, xBytes.length, aHost, serverPort);
            aSocket.send(request);
            request = new DatagramPacket(yBytes, yBytes.length, aHost, serverPort);
            aSocket.send(request);
            request = new DatagramPacket("+".getBytes(), "+".getBytes().length, aHost, serverPort);
            aSocket.send(request);
            byte[] buffer = new byte[1000];
            //get the answer from server
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);

            aSocket.receive(reply);
            //convert byte array to double and return the answer
            byte[] answer = Arrays.copyOf(reply.getData(), reply.getLength());
            double sum = Double.longBitsToDouble(byteArrayToLong(answer));
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
