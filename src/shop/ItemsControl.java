/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Kamil
 */
public class ItemsControl extends javax.swing.JPanel {

    /**
     * Creates new form ItemsControl
     */
    private Integer _row;
    private JFrame _frame;
    private ArrayList<Item> _items = new ArrayList<Item>();
    private ArrayList<String> _tableColumns = new ArrayList<String>(Arrays.asList("Id", "Nazwa", "Liczba sztuk", "Cena", "Opis"));

    public ItemsControl(JFrame frame) {
        initComponents();
        initItems(true);
        _frame = frame;
        jScrollPane1.getViewport().setView(jTableItems);
        Functions.disableTableEdit(jTableItems);
        initListener();
        jSpinnerNumberofItems.setValue(1);
        // jFormattedPriceField = new JFormattedTextField(new Float(3.14));
        jLabelInfo.setVisible(false);


    }

    private void initListener() {
        jTableItems.addMouseListener(new MouseAdapter() {
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

        jTextFieldName.setText(_items.get(row).getNazwa());
        jTextFieldDescription.setText(_items.get(row).getOpis());
        jTextFieldPrice.setText(_items.get(row).getCena().toString());
        jSpinnerNumberofItems.setValue(_items.get(row).getLiczba_sztuk());

    }

    private void modifyItem(int row) {
        DefaultTableModel model = (DefaultTableModel) jTableItems.getModel();
        setNewData(_items.get(row));

        //
        Integer id = _items.get(row).getId_towaru();
        Database db = Database.getDatabase();
        //db.connect();
        String condition = "id_towaru = " + id;

        String nazwa_ = Functions.addApostrophes(_items.get(row).getNazwa());
        Integer liczba_sztuk_ = _items.get(row).getLiczba_sztuk();
        Float cena_ = _items.get(row).getCena();
        String opis_ = Functions.addApostrophes(_items.get(row).getOpis());

        db.update("towar", "nazwa = " + nazwa_, condition);
        db.update("towar", "liczba_sztuk = " + liczba_sztuk_, condition);
        db.update("towar", "cena = " + cena_, condition);
        db.update("towar", "opis = " + opis_, condition);

        //db.update("towar", "nr_domu = "+nr_domu_, condition);
        //db.insert("adres", "ulica,miejscowosc,kod_pocztowy,nr_domu,uzytkownik", value);
        //db.close();
        Functions.clearTable(model, _tableColumns);

        for (Item item : _items) {
            model.addRow(item.getItem());
        }
    }

    private void addItem() {
        DefaultTableModel model = (DefaultTableModel) jTableItems.getModel();

        if (checkUserInput()) {
            Item newItem = new Item();
            setNewData(newItem);

            
            Database db = Database.getDatabase();
            

            String nazwa_ = Functions.addApostrophes(newItem.getNazwa());
            Integer liczba_sztuk_ = newItem.getLiczba_sztuk();
            Float cena_ = newItem.getCena();
            String opis_ = Functions.addApostrophes(newItem.getOpis());


            String value = nazwa_ + "," + liczba_sztuk_ + "," + cena_ + "," + opis_;
            boolean state = false;
            state = db.insert("towar", "nazwa,liczba_sztuk,cena,opis", value);

            if (state) {

                jLabelInfo.setVisible(false);
                
                
                _items.clear();
                initItems(false);
                Functions.clearTable(model, _tableColumns);
                for (Item item : _items) {
                    model.addRow(item.getItem());
                }

            }else
            {
                jLabelInfo.setText("Taki przedmiot istnieje");
                jLabelInfo.setVisible(true);
                
            }
            
        }

    }

    private void setNewData(Item item) {
        item.setNazwa(jTextFieldName.getText());
        item.setLiczba_sztuk((Integer) jSpinnerNumberofItems.getValue());
        item.setCena(Float.parseFloat(jTextFieldPrice.getText()));
        if (jTextFieldDescription.getText().length() > 0) {
            item.setOpis(jTextFieldDescription.getText());
        } else {
            item.setOpis("");
        }

    }

    private boolean checkUserInput() {
        boolean flag = true;

        Float price = (float) 0;
        try {
            price = Float.parseFloat(jTextFieldPrice.getText());
            if (price < 0 || price > 1000000) {
                flag = false;
            }
        } catch (NumberFormatException ex) {
            flag = false;
        }

        if (jTextFieldName.getText().length() < 1) {
            flag = false;
        }

        if (!flag) {
            jLabelInfo.setText("Błędne dane");
            jLabelInfo.setVisible(true);
        }

        //opis moze byc pusty
        return flag;
    }

    private void initItems(boolean init) {

        /*
        SQL
         */
        Database db = Database.getDatabase();
        //db.connect();
        ArrayList<ArrayList<Object>> data2d = db.select2("id_towaru,nazwa,liczba_sztuk,cena,opis", "towar", null,
                new ArrayList<SelectTypes>(Arrays.asList(
                        SelectTypes.INT,
                        SelectTypes.STRING,
                        SelectTypes.INT,
                        SelectTypes.FLOAT,
                        SelectTypes.STRING)));

        for (ArrayList<Object> row : data2d) {
            _items.add(new Item(
                    (Integer) row.get(0),
                    (String) row.get(1),
                    (Integer) row.get(2),
                    (float) row.get(3),
                    (String) row.get(4)
            ));

        }


        if (init) {
            DefaultTableModel model = new DefaultTableModel();
            jTableItems = new JTable(model);

            for (String col : _tableColumns) {
                model.addColumn(col);
            }

            for (Item item : _items) {
                model.addRow(item.getItem());
            }
        }

    }

    private void deleteItem(int tableRow) {
        DefaultTableModel model = (DefaultTableModel) jTableItems.getModel();
        boolean status = false;

        Integer id = _items.get(tableRow).getId_towaru();
        Database db = Database.getDatabase();
        //db.connect();
        String condition = "id_towaru = " + id;
        status = db.delete("towar", condition);
        //db.close();
        if (status) {
            _items.remove(tableRow);
            Functions.clearTable(model, _tableColumns);
            for (Item item : _items) {
                model.addRow(item.getItem());
            }
            jLabelInfo.setVisible(false);
        }else
        {
            jLabelInfo.setVisible(true);
            jLabelInfo.setText("Towar jest w zamówieniu");
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableItems = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jTextFieldPrice = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldDescription = new javax.swing.JTextField();
        jSpinnerNumberofItems = new javax.swing.JSpinner();
        jLabelInfo = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jButtonDelete = new javax.swing.JButton();
        jButtonModAdd = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Sitka Small", 0, 48)); // NOI18N
        jLabel1.setText("Zarządzaj towarem");

        jButton1.setText("Powrót");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTableItems.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableItems);

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setText("Opis:");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setText("Cena:");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setText("Liczba Sztuk:");
        jLabel6.setToolTipText("");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setText("Nazwa:");

        jSpinnerNumberofItems.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinnerNumberofItemsStateChanged(evt);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel6))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextFieldDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jSpinnerNumberofItems, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextFieldPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(58, 58, 58)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabelInfo)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButtonModAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jSpinnerNumberofItems, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextFieldPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextFieldDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelInfo)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonModAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        _frame.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButtonModAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModAddActionPerformed

        //System.out.println("Index od combobox ->" + jComboBox1.getSelectedIndex());
        if (jComboBox1.getSelectedIndex() == 0) {
            //modeyfikuj

            if (checkUserInput()) {
                modifyItem(_row);
            }

            //System.out.println("Modyfikacja");
        } else {
            addItem();
            //System.out.println("Dodawanie");
        }


    }//GEN-LAST:event_jButtonModAddActionPerformed

    private void jSpinnerNumberofItemsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinnerNumberofItemsStateChanged
        if ((Integer) jSpinnerNumberofItems.getValue() < 1) {
            jSpinnerNumberofItems.setValue(1);
        }
    }//GEN-LAST:event_jSpinnerNumberofItemsStateChanged

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        deleteItem(_row);
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        if (jComboBox1.getSelectedIndex() == 0) {
            //modifikuj
            jButtonDelete.setVisible(true);
            jButtonModAdd.setText("Modyfikuj");
        } else {
            jSpinnerNumberofItems.setValue(1);
            jTextFieldDescription.setText("");
            jTextFieldName.setText("");
            jTextFieldPrice.setText("");

            jButtonModAdd.setText("Dodaj");
            jButtonDelete.setVisible(false);
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonModAdd;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelInfo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinnerNumberofItems;
    private javax.swing.JTable jTableItems;
    private javax.swing.JTextField jTextFieldDescription;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTextField jTextFieldPrice;
    // End of variables declaration//GEN-END:variables
}
