/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Projet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author aurelien
 */

public class my_word2vec {
    private String [] mots = new String [155561];
    private double [][] coordonnees = new double[155561][200];
    
    //Lecture du Dico
    public void file_reader() {
        String line;
        
        int index_line = 0;
        
        try {
            File myObj = new File("Dictionnaire.txt");
            Scanner myReader = new Scanner(myObj);
            
            while (myReader.hasNextLine()) {
                line = myReader.nextLine();
                
                String [] line_tab = line.split(" ");
                this.mots[index_line] = line_tab[0];
                
                for(int i=1; i<201; i++)
                {
                    this.coordonnees[index_line][i-1] = Double.parseDouble(line_tab[i]);
                }
                index_line++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public my_word2vec() {
    }
    
    public double[] word2vec(String phrase){
        //Suppression des majuscules, virgules et des points
        char [] phrase_char = phrase.toCharArray();
        for (int i=0; i<phrase.length(); i++){
            if (phrase_char[i]>64 && phrase_char[i]<91){
                phrase_char[i] = (char)((int)(phrase_char[i])+32);
            }
            if (phrase_char[i] == 44 || phrase_char[i] == 46){
                phrase_char[i] = (char) 0;
            }
        }
        phrase = String.valueOf(phrase_char);
        System.out.println(phrase);
        
        //Separation des mots
        String [] mots = phrase.split(" ");
        
        double[] res = new double [200];
        
        //Recherche des mot du dico dans la phrase
        for(int i=0; i<mots.length; i++){
            for(int j=0; j<this.mots.length; j++){
                if(mots[i].equals(this.mots[j])){
                    for (int k=0;k<200; k++)
                    {
                        res[k] += this.coordonnees[j][k];
                    }
                }
            }
        }
        
        return(res);
    }
    
    //Exemple
    /*public static void main(String[] args) {
        my_word2vec transfo = new my_word2vec();
        transfo.file_reader();
        double [] res = transfo.word2vec("Je veux un rendez-vous");
        for (int k=0;k<200; k++)
        {
            System.out.println(res[k]);
        }
    }*/
}
