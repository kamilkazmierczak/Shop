/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Kamil
 */
public class Shop {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        JFrame frame = new JFrame("Menu główne");
        //frame.setSize(1300, 1600);
        frame.setContentPane(new MainMenu(frame));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


         Database db = Database.getDatabase();
         db.connect();
         
         

        /*
        Notes:
        -jesli liczba sztuk towaru = 0 to nie dodawaj go do koszyka (jesli nie zrobisz bedzie powodowac bledy w weryfikacji)
        -jak sie usunie adres to zablokuj zeby go sie nie dalo zmodyfikowac (i na odwrot) (bledy)
        -obsluga bledow userinput jest tylko w zarzadzaniu towarem
       
     

       
        
        -jakis komunikat jesli nie uda sie wstawic towaru ktory juz ma taka sama naze ->latwo o ten blad bo jak sie zrobi dodaj i kliknie na jakis item to nazwa ta sama i wysztko wskooczy
        cout wywali ze naruszona wizey uniaktowe
        
        

        
        >opisow bledow szukaj jako ERROR
        >opisow szukaj jako INFO
       
        
       
      
         */
    }

}
