package posledni;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author adammar
 */
public class DBEditor {
    
    static class Person {
        public String jmeno;
        public String prijmeni;
        public String narozeni;
        public String bydliste;
        int id;
        
        public Person(String in) {
             String[] split = in.split(";");
             this.id = Integer.parseInt(split[0]);
             this.jmeno = split[1];
             this.prijmeni = split[2];
             this.narozeni = split[3];
             this.bydliste = split[4];
       }
        

    }
    
    public static void createAndShowGUI() {
        
    }

    public static ArrayList persons = new ArrayList();
    public static void main(String[] args) {
        
        if (args.length < 1) {
                    System.err.println("give me tha FILE!!!");
                    System.exit(1);
            }
            String line = "";
            String file = args[0];
            try(BufferedReader input = new BufferedReader(new FileReader(file))){
                    while ((line = input.readLine()) != null) {
                        persons.add(new Person(line));
                    }
            } catch (IOException e) {
                    System.err.println(e.toString());
                    System.exit(1);
            }



            SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                            createAndShowGUI();
                    }
            });
        
    }
    
    
}
