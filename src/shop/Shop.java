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
        
        
        //Object[] res = db.select("nazwisko","pracownicy",SelectTypes.STRING);
        ArrayList<Object> data = db.select("placa_pod","pracownicy",SelectTypes.FLOAT);
        
        for (Object result : data) {
            System.out.println(result);
        }
        
        //System.out.println(res[0]);
        
        
        db.close();

 

        
       
    }
    
}
