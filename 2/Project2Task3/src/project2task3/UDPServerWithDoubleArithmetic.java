/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2task3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 *
 * @author Amelia
 */
public class UDPServerWithDoubleArithmetic {
    /**
     * 
     * main method to start a UDPServer that can calculate double arithmetic
     */
    public static void main(String args[]) {
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket(6789);
            // create socket at agreed port
            byte[] buffer = new byte[1000];
            //print out the first line
            System.out.println("java UDPServer");
            while (true) {
                //create request and use it to get the two numbers and the operator separately
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);
                byte[] double1 = Arrays.copyOf(request.getData(), request.getLength());
                aSocket.receive(request);
                byte[] double2 = Arrays.copyOf(request.getData(), request.getLength());
                aSocket.receive(request);
                char oper = (new String(Arrays.copyOf(request.getData(), request.getLength()))).charAt(0);
                //convert byte array to double
                double a = Double.longBitsToDouble(byteArrayToLong(double1));
                double b = Double.longBitsToDouble(byteArrayToLong(double2));
                String message = "" + a + oper + b;
                //print out current request 
                System.out.println("Received request for " + message);
                
                double ans = 0;
                //calculate the answer

                switch (oper) {
                    case '+':
                        ans = a + b;
                        break;
                    case '-':
                        ans = a - b;
                        break;
                    case 'X':
                        ans = a * b;
                        break;
                    case '/':
                        ans = a / b;
                        break;


                }
                //convert double answer to byte array
                long ansl = Double.doubleToLongBits(ans);
                byte[] ansBytes = ByteBuffer.allocate(8).putLong(ansl).array();
                //return the answer to client
                DatagramPacket reply = new DatagramPacket(ansBytes, ansBytes.length,
                        request.getAddress(), request.getPort());
                aSocket.send(reply);
            }
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
    public static long byteArrayToLong(byte bytes[]) {
        long v = 0;
        for (int i = 0; i < bytes.length; i++)
        { // bytes[i] will be promoted to a long with the byte’s leftmost
        // bit replicated. We need to clear that with 0xff.
        v = (v << 8) + (bytes[i] & 0xff);
        }
        return v;

    }
}
