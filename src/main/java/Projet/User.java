/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Projet;

import static Projet.KNN1.eval;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aurel
 */
public class User {
    
    private String Name;
    
    public String [] classes = {"Information", "Reservation", "Neutre"};

    public User() {
    }
    
    public String Analyse_message(final String message){
        
        System.out.println(message);
        
        String response;
        int cl = 0;
        
        //Detection Debut (nom) et FIN
        if (message.length()>4 && message.substring(0, 4).equals("Nom:")){
            this.Name = message.substring(4);
            response = "Bienvenue " + this.Name;
        }else if(message.equals("FIN"))
        {
            response = "Au revoir " + this.Name;
            
        //Si pas debut ni fin alors on l'analyse
        }else{
            //Vord2vec du message
            double [] res = Serveur.transfo.word2vec(message);
            
            //KNN
            try {
                cl = Serveur.KNN.eval(res);
            } catch (Exception ex) {
                System.out.println("ERREUR User line 44");
            }
            
            //Info/Reservation + Reponse
            System.out.println(cl);
            response = "Classe : " + this.classes[cl];
            synchronized(this){
                if (cl == 0)
                {
                    response += " => Le prochain creneau disponible est : ";
                    response += Serveur.cal.prochain_creneau();
                }
                else if (cl == 1)
                {
                    response += " => Vous avez reserve le creneau : ";
                    response += Serveur.cal.prochain_creneau();
                    response += " Etat : ";
                    response += Serveur.cal.reservation_creneau(this.Name);
                }else{
                    response += " => Desole je n'ai pas compris";
                }
            }
        }
        return response;
    }
}
