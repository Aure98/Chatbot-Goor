/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Projet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aurel
 */
public class Client2 {
    public static void go() throws IOException{
        
        String IP = ConsoleFDB.entreeString("adresse IP du serveur : ");
        int port  = ConsoleFDB.entreeInt("port : ");
        
        Socket sock = new Socket(IP, port);

        try{
            String message = "PASFIN";
            
            while (!message.equals("FIN")) {
                BufferedReader in = new BufferedReader (new InputStreamReader(sock.getInputStream(), Charset.forName("UTF8")));
                Writer out = new OutputStreamWriter(sock.getOutputStream(), Charset.forName("UTF8"));
                
                message = ConsoleFDB.entreeString("message : ");
                out.write(message + "\n");
                out.flush();
                
                String line;
                line = in.readLine();
                System.out.println(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(Client2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) throws IOException{
        go();
    }
}
