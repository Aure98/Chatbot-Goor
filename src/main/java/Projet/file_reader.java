/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Projet;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

/**
 *
 * @author aurel
 */
public class file_reader {
    
    public static void main(String[] args) {
        String line;
        String [] mots = new String [18];
        double [][] coordonnees = new double[18][200];
        int index_line = 0;
        
        try {
            File myObj = new File("essai.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                line = myReader.nextLine();
                System.out.println(line);
                
                String [] line_tab = line.split(" ");
                mots[index_line] = line_tab[0];
                
                for(int i=1; i<201; i++)
                {
                    coordonnees[index_line][i-1] = Double.parseDouble(line_tab[i]);
                }
                
                index_line++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
        System.out.println(mots[0]);
        for(int i=1; i<200; i++)
            {
                System.out.println(coordonnees[0][i]);
            }
        
    }
}
