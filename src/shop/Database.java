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

/**
 *
 * @author Kamil
 */
public class Database {

    private static String _currentUserLogin;
    private static Integer _userID;

    private static Database _database = null;
    private static Connection _conn = null;
    private static Properties _connectionProps = null;

    private Database() {
        _connectionProps = new Properties();
        _connectionProps.put("user", "inf117293");
        _connectionProps.put("password", "czita007");

    }

    public static void setUser(String login) {
        _currentUserLogin = login;
    }

    public static String getUser() {
        return _currentUserLogin;
    }

    public static void setUserID(Integer login) {
        _userID = login;
    }

    public static Integer getUserID() {
        return _userID;
    }

    public static void connect() {
        try {
            _conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@//admlab2-main.cs.put.poznan.pl:1521/dblab01.cs.put.poznan.pl",
                    _connectionProps);
            //System.out.println("Połączono z bazą danych");
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE,
                    "nie udało się połączyć z bazą danych", ex);
            System.exit(-1);
        }
    }

    public static void close() {
        try {
            _conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("Odłączono od bazy danych");
    }

    public static void disableAutoCommit() {
        try {
            _conn.setAutoCommit(false);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public static void enableAutoCommit() {
        try {
            _conn.setAutoCommit(true);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public static void commit() {

        try {
            _conn.commit();
        } catch (SQLException ex) {
            System.out.println("Bład wykonania polecenia" + ex.toString());
        }

    }

    public static void lockTable(String table) {
        Statement stmt = null;

        try {
            stmt = _conn.createStatement();
            stmt.execute("LOCK TABLE " + table + " IN EXCLUSIVE MODE");
        } catch (SQLException ex) {
            System.out.println("Bład wykonania polecenia" + ex.toString());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    /* kod obsługi */ }
            }
        }
    }

    public static ArrayList<ArrayList<Object>> getHistory() {
        ArrayList<ArrayList<Object>> myHistory = new ArrayList<ArrayList<Object>>();

        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = _conn.createStatement();

            ArrayList<SelectTypes> types = new ArrayList<SelectTypes>(Arrays.asList(
                    SelectTypes.INT,
                    SelectTypes.STRING,
                    SelectTypes.STRING,
                    SelectTypes.FLOAT,
                    SelectTypes.INT,
                    SelectTypes.DATE,
                    SelectTypes.DATE,
                    SelectTypes.FLOAT));

            rs = stmt.executeQuery("SELECT id_zamowienia,nazwa,status,p.cena,p.liczba_sztuk,data_zamowienia,data_dostawy,koszt FROM "
                    + "((towar t JOIN przydzial p ON t.id_towaru = p.towar) JOIN zamowienie z ON z.id_zamowienia = p.zamowienie) WHERE z.uzytkownik = " + _userID);

            ArrayList<Object> newRow;
            while (rs.next()) {
                newRow = new ArrayList<Object>();
                for (int i = 0; i < types.size(); i++) {

                    switch (types.get(i)) {
                        case STRING:
                            newRow.add(rs.getString(i + 1));
                            break;

                        case INT:
                            newRow.add(rs.getInt(i + 1));
                            break;

                        case FLOAT:
                            newRow.add(rs.getFloat(i + 1));
                            break;

                        case DATE:
                            newRow.add(rs.getDate(i + 1));
                            break;

                        default:
                            System.out.println("Nieznany typ danych");
                    }
                }
                myHistory.add(newRow); //new row
            }

        } catch (SQLException ex) {
            System.out.println("Bład wykonania polecenia" + ex.toString());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    /* kod obsługi */ }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    /* kod obsługi */ }
            }
        }
        return myHistory;

    }

    public static ArrayList<ArrayList<Object>> select2(String what, String table, String condition, ArrayList<SelectTypes> types) {
        //ArrayList<Object> results = new ArrayList<Object>();

        ArrayList<ArrayList<Object>> my2DList = new ArrayList<ArrayList<Object>>();

        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = _conn.createStatement();

            if (condition != null) {
                rs = stmt.executeQuery("select " + what + " from " + table + " where " + condition);
            } else {
                rs = stmt.executeQuery("select " + what + " from " + table);
            }

            ArrayList<Object> newRow;
            while (rs.next()) {
                newRow = new ArrayList<Object>();
                for (int i = 0; i < types.size(); i++) {

                    switch (types.get(i)) {
                        case STRING:
                            newRow.add(rs.getString(i + 1));
                            break;

                        case INT:
                            newRow.add(rs.getInt(i + 1));
                            break;

                        case FLOAT:
                            newRow.add(rs.getFloat(i + 1));
                            break;

                        case DATE:
                            newRow.add(rs.getString(i + 1));
                            break;

                        default:
                            System.out.println("Nieznany typ danych");
                    }
                }
                my2DList.add(newRow); //new row
            }

        } catch (SQLException ex) {
            System.out.println("Bład wykonania polecenia" + ex.toString());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    /* kod obsługi */ }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    /* kod obsługi */ }
            }
        }
        return my2DList;
    }

    public static ArrayList<Object> select(String what, String table, String condition, SelectTypes type, String order) {
        ArrayList<Object> results = new ArrayList<Object>();

        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = _conn.createStatement();

            if (condition != null) {
                rs = stmt.executeQuery("select " + what + " from " + table + " where " + condition + " order by " + order);
            } else {
                rs = stmt.executeQuery("select " + what + " from " + table + " order by " + order);
            }

            switch (type) {
                case STRING:
                    while (rs.next()) {
                        results.add(rs.getString(1));
                    }
                    break;
                case INT:
                    while (rs.next()) {
                        results.add(rs.getInt(1));
                    }
                    break;
                case FLOAT:
                    while (rs.next()) {
                        results.add(rs.getFloat(1));
                    }
                    break;
                default:
                    System.out.println("Nieznany typ danych");
            }

        } catch (SQLException ex) {
            System.out.println("Bład wykonania polecenia" + ex.toString());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    /* kod obsługi */ }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    /* kod obsługi */ }
            }
        }
        return results;
    }

    public static boolean insert(String table, String what, String values) {
        int changes;
        //String result = null;
        boolean status = false;
        Statement stmt = null;

        try {
            stmt = _conn.createStatement();

            changes = stmt.executeUpdate("INSERT INTO " + table + "(" + what + ") " + "VALUES(" + values + ")");
            //result = "Wstawiono " + changes + " krotek.";
            //System.out.println("Wstawiono " + changes + " krotek.");
            status = true;

        } catch (SQLException ex) {
            System.out.println("Bład wykonania polecenia" + ex.toString());
            status = false;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    /* kod obsługi */ }
            }
        }

        return status;
    }

    public static String update(String table, String what, String condition) {
        int changes;
        String result = null;
        Statement stmt = null;

        try {
            stmt = _conn.createStatement();

            if (condition != null) {
                changes = stmt.executeUpdate("UPDATE " + table + " SET " + what + " WHERE " + condition);
            } else {
                changes = stmt.executeUpdate("UPDATE " + table + " SET " + what);
            }

            result = "Zmodyfikowano " + changes + " krotek.";

        } catch (SQLException ex) {
            System.out.println("Bład wykonania polecenia" + ex.toString());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    /* kod obsługi */ }
            }
        }

        return result;
    }

    public static String delete(String table, String condition) {
        int changes;
        String result = null;
        Statement stmt = null;

        try {
            stmt = _conn.createStatement();

            changes = stmt.executeUpdate("DELETE FROM " + table + " WHERE " + condition);
            result = "Usunieto " + changes + " krotek.";

        } catch (SQLException ex) {
            System.out.println("Bład wykonania polecenia" + ex.toString());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    /* kod obsługi */ }
            }
        }

        return result;
    }

    public static Database getDatabase() {
        if (_database == null) {
            _database = new Database();
        }
        return _database;
    }

    public static void test() {
        System.out.println("Singleton works fine!");
    }

}
