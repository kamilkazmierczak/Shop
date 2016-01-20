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
         //db.close();

        //Date data = Date.stringToDate("2014-04-02");
        
        

        //select2

//
//        String condition = "liczba_sztuk > 5";
//        ArrayList<ArrayList<Object>> data2d = db.select2("id_towaru,nazwa,liczba_sztuk,cena,opis", "towar", condition,
//                new ArrayList<SelectTypes>(Arrays.asList(SelectTypes.INT,
//                        SelectTypes.STRING,
//                        SelectTypes.INT,
//                        SelectTypes.FLOAT,
//                        SelectTypes.STRING)));
//
//        for (ArrayList<Object> row : data2d) {
//            for (Object cell : row) {
//                //cell
//            }
//            System.out.println(row);
//        }
//        db.close();

        
        
        
        
//        Database db = Database.getDatabase();
//        db.connect();      
//        ///* SELECT
//        ArrayList<ArrayList<Object>> data2d = db.select2("nazwa,opis","towar",null,
//                new ArrayList<SelectTypes>(Arrays.asList(SelectTypes.STRING,SelectTypes.STRING)));  
//        
//        for (ArrayList<Object> row : data2d) {
//            for (Object cell : row) {
//                System.out.println(cell);
//            }      
//        }      
//        db.close();
//2D arr
//        ArrayList<ArrayList<Object>> my2DList = new ArrayList<ArrayList<Object>>();
//        
//        ArrayList<Object> newRow;
//        
//        newRow= new ArrayList<Object>();
//        newRow.add("1 kolumna w 1 wierszu");
//        newRow.add("2 kolumna w 1 wierszu");
//        my2DList.add(newRow); //new row
//        
//        newRow = new ArrayList<Object>();
//        newRow.add("1 kolumna w 2 wierszu");
//        newRow.add(32);
//        my2DList.add(newRow);
//        
//
//        for (ArrayList<Object> row : my2DList) {
//            for (Object cell : row) {
//                System.out.println(cell);
//            }      
//        }
//        my2DList.add(new ArrayList<Object>(Arrays.asList("1 kolumna w 2 wierszu", 13)));
//        
//        a = my2DList.get(1).get(0).toString();
//        Integer c = (Integer)my2DList.get(1).get(1);
//      
//        System.out.println(a+"\n"+c);
//end of 2D arr
//        Database db = Database.getDatabase();
//        db.connect();      
//        ///* SELECT
//        ArrayList<Object> data = db.select("nazwa","towar",null,SelectTypes.STRING,"id_towaru");     
//        for (Object result : data) {
//            System.out.println(result);
//        }
//       
//        db.close(); 
        // Database db = Database.getDatabase();
        //db.connect();      
//        ///* SELECT
//        ArrayList<Object> data = db.select("nazwa","towar",null,SelectTypes.STRING);     
//        for (Object result : data) {
//            System.out.println(result);
//        }
//        //*/
//  
//        //db.insert("towar", "nazwa,placa_min", "'THREE',33");
////        db.update("etaty", "placa_min = 0.4*placa_min", "nazwa = 'THREE'");
////        db.delete("etaty", "NAZWA = 'TWO'");
//        
        //if (db.insert("uzytkownik", "imie,nazwisko,login,haslo,znizka,typ_konta", "'roger','gornecki','gorasta','ola',0,'user'")) {
        //    System.err.println("fine");
        //}
        // db.insert("uzytkownik", "imie,nazwisko,login,haslo,znizka,typ_konta", "'roger','gornecki','gorasta','ola',0,'user'");
        // db.close();
        /*
        Notes:
        -jesli liczba sztuk towaru = 0 to nie dodawaj go do koszyka (jesli nie zrobisz bedzie powodowac bledy w weryfikacji)
        -jak sie usunie adres to zablokuj zeby go sie nie dalo zmodyfikowac (i na odwrot) (bledy)
        -obsluga bledow userinput jest tylko w zarzadzaniu towarem
       
     
           
       -czy jak mam w tabelach ddane recznie jakies id (np kod znizki a sekwencja jest na nizszej wartosci to sie skrzaczy?)
        @wywali sie -przelec po nim i tyle potem bd ok (w sensie dodaj wiele razy raz blad potem nie)
       
        
        -jakis komunikat jesli nie uda sie wstawic towaru ktory juz ma taka sama naze ->latwo o ten blad bo jak sie zrobi dodaj i kliknie na jakis item to nazwa ta sama i wysztko wskooczy
        cout wywali ze naruszona wizey uniaktowe
        
        
        -db.close() mozna dac wczesniej bo zazwyczaj go daje po dodaniu wartosci do tabeli a nie przed
        
        >opisow bledow szukaj jako ERROR
        >opisow szukaj jako INFO
       
        
       
       ISTOTNE
       idiotyczna jest funckja discountCodeToPercent bo trzeba sie polaczyc do bazy przed jej uzyciem i rozlaczyc po uzyciu, sama w sobie tego nie robi
       bo zajmuje to wieki
         */
    }

}
