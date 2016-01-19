/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Kamil
 */
public class Accounts extends javax.swing.JPanel {

    /**
     * Creates new form Accounts
     */
    private JFrame _frame;
    private ArrayList<User> _users = new ArrayList<User>();
    private ArrayList<Discount> _discounts = new ArrayList<Discount>();

    public Accounts(JFrame frame) {
        initComponents();
        _frame = frame;
        initUsers();
        jScrollPane1.getViewport().setView(jTableAccounts);
        Functions.disableTableEdit(jTableAccounts);
        initListener();
        initDiscounts();

    }

    private void initUsers() {

        Database db = Database.getDatabase();
        //db.connect();
        String condition = "typ_konta != 'admin'";
        ArrayList<ArrayList<Object>> data2d = db.select2("id_uzytkownika,imie,nazwisko,login,haslo,znizka", "uzytkownik", condition,
                new ArrayList<SelectTypes>(Arrays.asList(
                        SelectTypes.INT,
                        SelectTypes.STRING,
                        SelectTypes.STRING,
                        SelectTypes.STRING,
                        SelectTypes.STRING,
                        SelectTypes.INT)));

        for (ArrayList<Object> row : data2d) {
            _users.add(new User((Integer) row.get(0), (String) row.get(1), (String) row.get(2), (String) row.get(3), (String) row.get(4), (Integer) row.get(5)));
        }

        //close jest na koncu bo jest jeszcze
        DefaultTableModel model = new DefaultTableModel();
        jTableAccounts = new JTable(model);
        model.addColumn("Id");
        model.addColumn("Imie");
        model.addColumn("Nazwisko");
        model.addColumn("Login");
        model.addColumn("Hasło");
        model.addColumn("Zniżka [%]");

        for (User user : _users) {
            model.addRow(user.getUser());
        }

        //db.close();
    }

    private void initDiscounts() {
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>());
        /*
        SQL
         */


        Database db = Database.getDatabase();
        //db.connect();
        ArrayList<ArrayList<Object>> data2d = db.select2("kod_znizki,ile", "znizka", null,
                new ArrayList<SelectTypes>(Arrays.asList(
                        SelectTypes.INT,
                        SelectTypes.FLOAT)));

        for (ArrayList<Object> row : data2d) {
            _discounts.add(new Discount(
                    (Integer) row.get(0),
                    (float) row.get(1)));
        }

        //db.close();
        
        //_discounts.add(new Discount(999, (float) 60.2));
        // _discounts.add(new Discount(888, (float) 14));
        /*
        INFO
        User ma kod znizki i przy user.getUser() zwraca wartosc [%] (funkcja)
        i ta funkcja potrzebuje SQL zeby sobie to zamienic na razie jest dla testow
        initDiscounts dodaje mi wszystkie znizki do comboboxa
         */
        for (Discount disc : _discounts) {
            jComboBox1.addItem(disc.getIle().toString());
        }

    }

    private void initListener() {
        jTableAccounts.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                JTable table = (JTable) me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                if (me.getClickCount() == 1) {
                    //modifyAccountDiscount(row);
                }
            }
        });
    }

    private void modifyAccountDiscount(int row) {

        DefaultTableModel model = (DefaultTableModel) jTableAccounts.getModel();

        Integer discountCode = _discounts.get(jComboBox1.getSelectedIndex()).getKod_znizki();
        _users.get(row).setKod_znizki(discountCode);

        Float ilezniski = _discounts.get(jComboBox1.getSelectedIndex()).getIle();
        _users.get(row).setProcentZnizki(ilezniski);

        Database db = Database.getDatabase();
        //db.connect();
        Integer id = _users.get(row).getId_uzytkownika();
        String condition = "id_uzytkownika = " + id;
        db.update("uzytkownik", "znizka = " + discountCode, condition);
        //db.close();

        clearAccountTable(model);
        for (User user : _users) {
            model.addRow(user.getUserWithPercent());
        }

    }

    public void clearAccountTable(final DefaultTableModel model) {
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        model.setColumnCount(0);
        model.addColumn("Id");
        model.addColumn("Imie");
        model.addColumn("Nazwisko");
        model.addColumn("Login");
        model.addColumn("Hasło");
        model.addColumn("Zniżka [%]");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableAccounts = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButtonSave = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        jButton1.setText("Powrót");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Sitka Small", 0, 36)); // NOI18N
        jLabel1.setText("Zniżka");

        jTableAccounts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTableAccounts);

        jLabel2.setFont(new java.awt.Font("Sitka Small", 0, 48)); // NOI18N
        jLabel2.setText("Konta");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButtonSave.setText("Zapisz");
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        jLabel3.setText("Info: pokazuje % ale operuje na ID");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButtonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)))
                .addGap(34, 34, 34))
            .addGroup(layout.createSequentialGroup()
                .addGap(199, 199, 199)
                .addComponent(jLabel2)
                .addGap(68, 68, 68)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        _frame.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed

        if (jTableAccounts.getSelectedRow() != -1) {
            modifyAccountDiscount(jTableAccounts.getSelectedRow());
        }

    }//GEN-LAST:event_jButtonSaveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableAccounts;
    // End of variables declaration//GEN-END:variables
}
