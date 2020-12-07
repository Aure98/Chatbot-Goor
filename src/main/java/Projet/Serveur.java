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
public class Serveur extends Thread{
    
    private Socket socketClient;
    protected static calendrier cal = new calendrier();
    protected static my_word2vec transfo = new my_word2vec();
    
    //Constructeur
    public Serveur(Socket sock) {
        this.socketClient = sock;
        this.transfo.file_reader();
    }
    
    //Thread
    @Override
    public void run(){
        try{
            String message = "Pas Fin";
            String message_retour;
            User utilisateur = new User();

            while(!message.equals("FIN")){
                BufferedReader in = new BufferedReader (new InputStreamReader(this.socketClient.getInputStream(), Charset.forName("UTF8")));
                Writer out = new OutputStreamWriter(this.socketClient.getOutputStream(), Charset.forName("UTF8"));

                message = in.readLine();
                message_retour = utilisateur.Analyse_message(message);
                out.write(message_retour + "\n");
                out.flush();
            }
            this.socketClient.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args){
        Inet4Address IP = (Inet4Address) Inet4Address.getLoopbackAddress();
        int port = 12800;
        
        try{
            ServerSocket serv = new ServerSocket(port);
            
            System.out.println("Serveur IP : " + IP);
            System.out.println("Serveur port : " + port);
            
            while(true){
                Socket socketClient = serv.accept();
                Serveur T = new Serveur(socketClient);
                T.start();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
