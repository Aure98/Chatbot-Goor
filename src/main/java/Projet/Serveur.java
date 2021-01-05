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
    protected static calendrier cal;
    protected static my_word2vec transfo;
    protected static my_KNN KNN;
    
    //Constructeur
    public Serveur(Socket sock, my_word2vec transfo, my_KNN KNN, calendrier cal) {
        this.socketClient = sock;
        this.transfo = transfo;
        this.KNN = KNN;
        this.cal = cal;
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
        //Creation des objets calendrier, word2vec et calendrier
        calendrier cal = new calendrier();
        my_word2vec transfo = new my_word2vec();
        my_KNN KNN = new my_KNN();
        
        //Initialisation des objets word2vec et KNN
        transfo.file_reader();
        KNN.file_reader();
        
        //Reccuperation de l'IP et definition du port
        Inet4Address IP = (Inet4Address) Inet4Address.getLoopbackAddress();
        int port = 12800;
        
        try{
            //Creation de serveur
            ServerSocket serv = new ServerSocket(port);
            
            System.out.println("Serveur IP : " + IP);
            System.out.println("Serveur port : " + port);
            
            //Attente des clients
            while(true){
                Socket socketClient = serv.accept();
                Serveur T = new Serveur(socketClient, transfo, KNN, cal);
                T.start();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
