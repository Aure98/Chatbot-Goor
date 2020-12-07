package Projet;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.IOException;

public class calendrier {
    
    private final String [] date_txt = new String[6];
    private Date [] liste_date = new Date[date_txt.length];
    private String [] reservation = new String[date_txt.length];
    
    //Format de la date dd/mm/yy hh:mm
    private DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
    
    
    //Constructeur creation du calendrier
    public calendrier(){
        for (int i=0; i<this.date_txt.length; i++){
            this.reservation[i] = "";
        }
        
        file_reader("rendez-vous.txt");
        
        try{
            for (int i=0; i<this.date_txt.length; i++){
                this.liste_date[i] = this.shortDateFormat.parse(date_txt[i]);
            }
        } catch (ParseException e) {
          e.printStackTrace();
        }
    }
    
    public void file_reader(String File_name) {
        String line;
        
        int index_line = 0;
        
        try {
            File myObj = new File(File_name);
            Scanner myReader = new Scanner(myObj);
            
            while (myReader.hasNextLine()) {
                line = myReader.nextLine();
                String [] line_tab = line.split(";");
                
                this.date_txt[index_line] = line_tab[0];
                if(line_tab.length>1)
                {
                    this.reservation[index_line] = line_tab[1];
                }
                
                index_line++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    public void save(String File_name) 
    {
        try {
            FileWriter myWriter = new FileWriter(File_name);
            for(int i=0;i<date_txt.length;i++)
            {
                myWriter.write(this.date_txt[i] + ";" + this.reservation[i] + "\n");
            }
            myWriter.close();
            
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }
    
    
    public String prochain_creneau(){
        int i=0;
        String res;
        while(this.reservation[i]!="" && i<this.date_txt.length){
            i++;
        }
        
        if (i == this.date_txt.length){
            res = "Plus de creneau disponible";
        }else{
            res = this.liste_date[i].toString();
        }
        
        return res;
    }
    
    public boolean reservation_creneau(String user){
        int i=0;
        boolean res;
        while(this.reservation[i]!="" && i<this.date_txt.length){
            i++;
        }
        
        if (i == this.date_txt.length){
            res = false;
        }else{
            this.reservation[i]=user;
            save("rendez-vous.txt");
            res = true;
        }
        
        return res;
    }
    
    public String dispo_creneau(String d){
        Date date;
        try{
            date= this.shortDateFormat.parse(d);
        } catch (ParseException e) {
            return "Prb de date";
        }
        
        int index=-1;
        for (int i=0; i<this.liste_date.length; i++){
            if (liste_date[i].equals(date)){
                index = i;
            }
        }
        
        if (index == -1){
            return "Creneau inexistant";
        }else if(reservation[index] == ""){
            return "Creneau dispo";
        }else{
            return reservation[index];
        }
    }
    
    //Exemple
    public static void main(String[] args) {
        calendrier cal = new calendrier();
        System.out.println(cal.reservation_creneau("Aure"));
        System.out.println(cal.prochain_creneau());
        System.out.println(cal.dispo_creneau("4/12/2020 16:00:00"));
    }
}