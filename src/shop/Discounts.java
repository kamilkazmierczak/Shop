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
public class Discounts extends javax.swing.JPanel {

    /**
     * Creates new form Discounts
     */
    private JFrame _frame;
    private Integer _row;
    private ArrayList<Discount> _discounts = new ArrayList<Discount>();
    private ArrayList<String> _tableColumns = new ArrayList<String>(Arrays.asList("Kod zniżki", "Ile [%]"));

    public Discounts(JFrame frame) {
        initComponents();
        initDiscounts(true);
        _frame = frame;
        jScrollPane1.getViewport().setView(jTableDiscounts);
        Functions.disableTableEdit(jTableDiscounts);
        initListener();
        jLabelInfo.setVisible(false);
    }

    private void initDiscounts(boolean init) {

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

        // _discounts.add(new Discount(999, (float) 60.2));
        //_discounts.add(new Discount(888, (float) 14));
        if (init) {
            DefaultTableModel model = new DefaultTableModel();
            jTableDiscounts = new JTable(model);
            for (String col : _tableColumns) {
                model.addColumn(col);
            }

            for (Discount disc : _discounts) {
                model.addRow(disc.getDiscount());
            }
        }

    }

    private void initListener() {
        jTableDiscounts.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                JTable table = (JTable) me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                if (me.getClickCount() == 1) {
                    _row = row;
                    selectItems(_row);
                }
            }
        });
    }

    private void selectItems(Integer row) {
        jTextFieldDiscountValue.setText(_discounts.get(row).getIle().toString());
    }

    private void modifyItem(int row) {
        DefaultTableModel model = (DefaultTableModel) jTableDiscounts.getModel();
        setNewData(_discounts.get(row));

        Database db = Database.getDatabase();
        //db.connect();
        Integer kod_znizki = _discounts.get(row).getKod_znizki();
        String condition = "kod_znizki = " + kod_znizki;
        Float ile_ = _discounts.get(row).getIle();
        db.update("znizka", "ile = " + ile_, condition);

        Functions.clearTable(model, _tableColumns);
        for (Discount disc : _discounts) {
            model.addRow(disc.getDiscount());
        }
    }

    private void setNewData(Discount disc) {
        disc.setIle(Float.parseFloat(jTextFieldDiscountValue.getText()));
    }

    private boolean checkUserInput() {
        boolean flag = true;

        Float value = (float) 0;
        try {
            value = Float.parseFloat(jTextFieldDiscountValue.getText());
            if (value < 0 || value > 100) {
                flag = false;
            }
        } catch (NumberFormatException ex) {
            flag = false;
        }

        if (!flag) {
            jLabelInfo.setText("Błędne dane");
            jLabelInfo.setVisible(true);
        }

        return flag;
    }

    private void deleteItem(int tableRow) {
        DefaultTableModel model = (DefaultTableModel) jTableDiscounts.getModel();

        Database db = Database.getDatabase();
        //db.connect();
        Integer id = _discounts.get(tableRow).getKod_znizki();
        String condition = "kod_znizki = " + id;
        
       

        if (id == 0) {
            System.out.println("id->"+id);
            jLabelInfo.setText("Nie można usunąć tej zniżki");
            jLabelInfo.setVisible(true);
            
     
        } else {
            jLabelInfo.setVisible(false);
            String cond = "znizka = "+id;
            db.update("uzytkownik", "znizka = 0",cond);
            
            
            db.delete("znizka", condition);
            _discounts.remove(tableRow);
            Functions.clearTable(model, _tableColumns);
            for (Discount disc : _discounts) {
                model.addRow(disc.getDiscount());
            }
        }
    //db.close();
    }

    private void addItem() {
        DefaultTableModel model = (DefaultTableModel) jTableDiscounts.getModel();

        //setNewData(_items.get(row));
        //Item item = new Item(14, "myszka", 78, (float) 140.67, "Zwinna");
        if (checkUserInput()) {
            Discount newDiscount = new Discount();
            setNewData(newDiscount);
            _discounts.add(newDiscount);

            Database db = Database.getDatabase();
            //db.connect();
            db.insert("znizka", "ile", newDiscount.getIle().toString());
            //close();

            //Functions.clearTable(model, _tableColumns);
            _discounts.clear();
            initDiscounts(false);

            /*
        ERROR
        TU BEDA BLEDY!!!
        bo to mi nie dodaje nowego id, nie robie nowej funkcji setNewData(..)
        bo nie wiem jak chce to realizowac, albo jakos z sbd, albo
        wziac id ostatniego elementu i nadac mu kolejny,
        domyslnie jak go nie ma to jest to null
             */
            Functions.clearTable(model, _tableColumns);
            for (Discount disc : _discounts) {
                model.addRow(disc.getDiscount());
            }
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabelInfo = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButtonDelete = new javax.swing.JButton();
        jButtonModAdd = new javax.swing.JButton();
        jTextFieldDiscountValue = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDiscounts = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Sitka Small", 0, 48)); // NOI18N
        jLabel1.setText("Zarządzaj zniżkami");

        jButton1.setText("Powrót");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabelInfo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelInfo.setText("Infolabel");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Modyfikuj", "Dodaj" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Sitka Small", 0, 18)); // NOI18N
        jLabel2.setText("Operacja");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setText("Wartość: [%]");

        jButtonDelete.setText("Usuń");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        jButtonModAdd.setText("Zmodyfikuj");
        jButtonModAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModAddActionPerformed(evt);
            }
        });

        jTableDiscounts.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableDiscounts);

        jButton2.setText("Zapisz zmiany");
        jButton2.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldDiscountValue, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButtonModAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabelInfo)))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextFieldDiscountValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addComponent(jLabelInfo)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonModAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        _frame.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        if (jComboBox1.getSelectedIndex() == 0) {
            //modifikuj
            jButtonDelete.setVisible(true);
            jButtonModAdd.setText("Modyfikuj");
        } else {
            jTextFieldDiscountValue.setText("");
            jButtonModAdd.setText("Dodaj");
            jButtonDelete.setVisible(false);
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        deleteItem(_row);
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jButtonModAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModAddActionPerformed

        if (jComboBox1.getSelectedIndex() == 0) {
            //modeyfikuj
            modifyItem(_row);
        } else {
            addItem();
        }

    }//GEN-LAST:event_jButtonModAddActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonModAdd;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelInfo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableDiscounts;
    private javax.swing.JTextField jTextFieldDiscountValue;
    // End of variables declaration//GEN-END:variables
}
