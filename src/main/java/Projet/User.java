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
    
    public String [] classes = {"Information", "Reservation"};

    public User() {
    }
    
    public String Analyse_message(final String message){
        
        System.out.println(message);
        
        String response;
        int cl = 0;
        
        if (message.length()>4 && message.substring(0, 4).equals("Nom:")){
            this.Name = message.substring(4);
            response = "Bienvenue " + this.Name;
        }else if(message.equals("FIN"))
        {
            response = "Au revoir " + this.Name;
        }else{
            double [] res = Serveur.transfo.word2vec(message);
            
            try {
                cl = (int)KNN1.eval(res);
            } catch (Exception ex) {
                Logger.getLogger(KNN1.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.out.println(cl);
            response = "Classe : " + this.classes[cl];
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
            }
        }
        return response;
    }
}
