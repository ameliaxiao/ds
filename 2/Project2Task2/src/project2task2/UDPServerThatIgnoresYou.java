/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2task2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Amelia
 */
public class UDPServerThatIgnoresYou {

    /**
     *
     * main method to start a UDPServer that can calculate arithmetic
     */
    public static void main(String args[]) {
        DatagramSocket aSocket = null;
        Random rnd = new Random();
        try {
            aSocket = new DatagramSocket(6789);
            // create socket at agreed port
            byte[] buffer = new byte[1000];
            //print out the first line
            System.out.println("java UDPServer");
            while (true) {

                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);
                //get the request string
                String message = new String(Arrays.copyOf(request.getData(), request.getLength()));
                //ignore or reply to the request
                if (rnd.nextInt(10) < 8) {
                    System.out.println("Got request "
                            + message
                            + " but ignoring it.");
                    continue;
                } else {
                    System.out.println("Got request"
                            + message);
                    System.out.println("And making a reply");
                }

                //get the integers and operator
                int a = Integer.parseInt(message.substring(0, message.indexOf(" ")));
                int b = Integer.parseInt(message.substring(message.indexOf(" ") + 1, message.lastIndexOf(" ")));
                char oper = message.substring(message.lastIndexOf(" ") + 1).charAt(0);
                int ans = 0;
                //calculate the answer
                String answer = "";
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
                    case '^':
                        ans = (int) Math.pow(a, b);
                        break;

                }
                answer = message + " = " + ans;

                //return the answer to client
                DatagramPacket reply = new DatagramPacket(answer.getBytes(), answer.getBytes().length,
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
}
