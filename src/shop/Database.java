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
public class Database {
    
    private static Database _database = null;  
    private static Connection _conn = null;
    private static Properties _connectionProps = null;
    
    private Database()
    {
        _connectionProps = new Properties(); 
        _connectionProps.put("user", "inf117293");
        _connectionProps.put("password", "czita007");
    }
    
    

    
    
    
    
    public static void connect()
    {
        try
        {
            _conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@//admlab2-main.cs.put.poznan.pl:1521/dblab01.cs.put.poznan.pl",
                    _connectionProps);
            System.out.println("Połączono z bazą danych");
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE,
            "nie udało się połączyć z bazą danych", ex);
            System.exit(-1);
        }    
    }
    
    
     public static void close()
     {
         try { _conn.close(); }
            catch(SQLException ex) { Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex); }
            System.out.println("Odłączono od bazy danych");
     }
    
    
 
     
     
     public static ArrayList<Object> select(String what,String table, SelectTypes type)
     {      
        ArrayList<Object> results = new ArrayList<Object>();
    
            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = _conn.createStatement();
                rs = stmt.executeQuery("select " + what + " from "+table);

                switch(type){
                    case STRING: while (rs.next()){results.add(rs.getString(1));}
                                 break;
                    case INT:    while (rs.next()){results.add(rs.getInt(1));}
                                 break;
                    case FLOAT:  while (rs.next()){results.add(rs.getFloat(1));}
                                 break;              
                }
                
            } 
            catch (SQLException ex) 
            {
                System.out.println("Bład wykonania polecenia" + ex.toString());
            } 
            finally 
            {
                if (rs != null) {
                    try { rs.close();} 
                    catch (SQLException e) { /* kod obsługi */ }
                }
                if (stmt != null) {
                    try { stmt.close(); } 
                    catch (SQLException e) { /* kod obsługi */ }
                }
            }
           
            
            return results;
            
     }
     
    
    
    public static Database getDatabase()
    {
        if (_database == null) {
            _database = new Database();
        }
        return _database;
    }
    
    
    public enum Day {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
    THURSDAY, FRIDAY, SATURDAY 
    }

    
    public static void test()
    {
        System.out.println("Singleton works fine!");
        Day d;
        d = Day.FRIDAY;
    }
    
    
    
}
