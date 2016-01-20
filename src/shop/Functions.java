/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Kamil
 */
public class Functions {

    
    public static boolean correctString(String text)
    {
        boolean status = true;
        
        if(text.matches(".*\\d+.*"))
            status = false;
        
        
        return status;
    }
    
    
    public static boolean correctPhoneNumber(String number) {
        boolean status = true;

        if (number.length() != 9) {
            status = false;
        }

        try {
            Integer.parseInt(number);
        } catch (Exception e) {
            status = false;
        }

        return status;
    }

    public static boolean correctHomeNumber(String number) {
        boolean status = true;
        
        try {
            Integer.parseInt(number);
        } catch (Exception e) {
            status = false;
        }
        
        
        return status;
    }
    
    
    
    public static boolean correctZipCode(String number) {
        boolean status = true;
        String pre = "";
        String dash = "";
        String post = "";

        if (number.length() != 6) {
            status = false;
        } else {

            pre = number.substring(0, 2);
            dash = number.substring(2, 3);
            post = number.substring(3, 6);

        }

        try {
            Integer.parseInt(pre);
            Integer.parseInt(post);
        } catch (Exception e) {
            status = false;
        }

        if (!dash.equals("-")) {
            status = false;
        }

        return status;
    }

    public static String addApostrophes(String text) {
        return "'" + text + "'";
    }

    public static Float calculateDeliverCost(DeliverType typ) {
        Float koszt = (float) 0;

        switch (typ) {

            case KURIER:
                koszt = (float) 15;
                break;

            case POCZTA:
                koszt = (float) 7;
                break;

            case OSOBISCIE:
                koszt = (float) 0;
                break;

            default:
                System.out.println("Nieznany sposob dostawy");
                break;
        }

        return koszt;
    }

    public static void clearTable(final DefaultTableModel model, ArrayList<String> columns) {
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        model.setColumnCount(0);
        for (String col : columns) {
            model.addColumn(col);
        }
    }

    public static void disableTableEdit(JTable table) {
        for (int c = 0; c < table.getColumnCount(); c++) {
            Class<?> col_class = table.getColumnClass(c);
            table.setDefaultEditor(col_class, null);// remove editor
        }
    }

    public static String statusToString(StatusTypes status) {
        switch (status) {

            case PACKING:
                return "pakowany";

            case SENT:
                return "wysłany";

            case CANCELLED:
                return "anulowany";

            case RECEIVED:
                return "odebrany";

            default:
                return "nieznany status";
        }
    }

    public static Float discountCodeToPercent(Integer code) {
        /*
        SQL
         */
        Float discount = (float) 0;
        Database db = Database.getDatabase();
      

        ArrayList<Object> data;

        String condition = "kod_znizki = " + code;

        data = db.select("ile", "znizka", condition, SelectTypes.FLOAT, "kod_znizki");
        for (Object result : data) {
            discount = (float) result;
        }

        return discount;
    }

}
