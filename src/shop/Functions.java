/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import javax.swing.JTable;

/**
 *
 * @author Kamil
 */
public class Functions {

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

}
