/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Projet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.Object;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author aurel
 */
public class KNN1 {
    
    public static BufferedReader readDataFile(String filename) throws Exception{
        BufferedReader inputReader = null;
        
        try {
            inputReader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException ex) {
            System.err.println("File not found: " + filename);
        }
        
        return inputReader;
    }
    
    public static double eval(double [] resultat) throws Exception{
        BufferedReader datafile = readDataFile("ads2.txt");
        
        Instances data = new Instances(datafile);
        data.setClassIndex(data.numAttributes() - 1);
        
        //do not use first and second
        Instance dataRaw = data.instance(0);
        for (int i=0; i<200; i++)
        {
            dataRaw.setValue(i, resultat[i]);
        }
        dataRaw.setValue(200, "N");
        
        data.delete(0);
        
        //System.out.println(data.toString());
        Classifier ibk = new IBk();
        ibk.buildClassifier(data);
        
        double class1 = ibk.classifyInstance(dataRaw);
        
        return class1;
    }
    
    public static void train(){
        //A modifier
        int nb_line = 16;
        
        String line;
        int index_line = 0;
        String [] classes = new String [nb_line];
        String [] phrases = new String [nb_line];
        
        try {
            File myObj = new File("dataset.txt");
            Scanner myReader = new Scanner(myObj);
            
            while (myReader.hasNextLine()) {
                line = myReader.nextLine();
                
                String [] line_tab = line.split(";");
                classes[index_line] = line_tab[0];
                phrases[index_line] = line_tab[1];
                
                index_line++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
            
            
        my_word2vec transfo = new my_word2vec();
        transfo.file_reader();
        double [] res;

        try {
            FileWriter myWriter = new FileWriter("ads2.txt");
            myWriter.write("@relation ads\n");
            myWriter.write("\n");

            for (int i=0;i<200; i++)
            {
                myWriter.write("@attribute A" + i + " numeric\n");
            }
            myWriter.write("@attribute profit {I, R, N}\n");
            myWriter.write("\n");
            myWriter.write("@data\n");
            for (int i=0;i<200; i++)
            {
                myWriter.write("0,");
            }
            myWriter.write("N\n");

            for (int i=0;i<phrases.length; i++)
            {
                System.out.println(phrases[i]);
                res = transfo.word2vec(phrases[i]);
                for (int j=0; j<res.length; j++)
                {
                    myWriter.write(res[j] + ",");
                }
                myWriter.write(classes[i] + "\n");

            }

            myWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    //Exemple
    /*public static void main(String[] args) {
        //train();
        
        my_word2vec transfo = new my_word2vec();
        transfo.file_reader();
        double [] res = transfo.word2vec("je souhaite avoir une information");
        
        try {
            System.out.println((int)eval(res));
        } catch (Exception ex) {
            Logger.getLogger(KNN1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
}
