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
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        
        
        Database db = Database.getDatabase();
        db.connect();      
        
        
        /* SELECT
        ArrayList<Object> data = db.select("placa_pod","pracownicy","placa_pod>1700",SelectTypes.FLOAT);     
        for (Object result : data) {
            System.out.println(result);
        }
        */
  
        //db.insert("etaty", "nazwa,placa_min", "'THREE',33");
        db.update("etaty", "placa_min = 1*placa_min", null);
        
        
        db.close();

 

        
       
    }
    
}
