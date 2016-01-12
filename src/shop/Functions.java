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

    public static String addApostrophes(String text)
    {
        return "'"+text+"'";
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
        Float discount = (float)0;
        Database db = Database.getDatabase();
        db.connect();
   
     
        ArrayList<Object> data;
              
        String condition = "kod_znizki = "+code;
        
        data = db.select("ile", "znizka", condition, SelectTypes.FLOAT);
        for (Object result : data) {
            discount= (float)result;
        }
        
        db.close();

//        if (code == 817) {
//            return (float) 50.0;
//        } else if (code == 0) {
//            return (float) 0;
//        } else if (code == 999) {
//            return (float) 60.2;
//        } else if (code == 888) {
//            return (float) 14;
//        }
//
//        return (float) 4.4;
        
    return discount;
    }

}
