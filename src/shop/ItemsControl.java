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
    private ArrayList<String> _tableColumns = new ArrayList<String>(Arrays.asList("Id", "Nazwa", "Liczba sztuk", "Cena","Opis"));
    
    
    public ItemsControl(JFrame frame) {
        initComponents();
        initItems();
        _frame = frame;
        jScrollPane1.getViewport().setView(jTableItems);
        Functions.disableTableEdit(jTableItems);
        initListener();
        jSpinnerNumberofItems.setValue(1);
       // jFormattedPriceField = new JFormattedTextField(new Float(3.14));
        
        
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

        //Integer discountCode = _discounts.get(jComboBox1.getSelectedIndex()).getKod_znizki();
        //_users.get(row).setKod_znizki(discountCode);
        setNewData(_items.get(row));
        
        Functions.clearTable(model, _tableColumns);
        //clearItemsTable(model);
        for (Item item : _items) {
            model.addRow(item.getItem());
        }
    }
    
    private void setNewData(Item item)
    {
        if (checkUserInput()) {
            item.setNazwa(jTextFieldName.getText());
            item.setLiczba_sztuk((Integer)jSpinnerNumberofItems.getValue());
            item.setCena(Float.parseFloat(jTextFieldPrice.getText())); 
            if (jTextFieldDescription.getText().length()>0) {
                item.setOpis(jTextFieldDescription.getText());
            }else
            {
                item.setOpis("");
            }
        } 
    }
    
    private boolean checkUserInput()
    {
        boolean flag = true;
        
       Float price = (float)0;
       try{
           price = Float.parseFloat(jTextFieldPrice.getText());
           if (price < 0 || price > 1000000) {
                flag = false;
           }
       }catch(NumberFormatException ex)
       {
           flag = false;
       }
       
        if (jTextFieldName.getText().length() < 1) {
            flag = false;
        }
        
        //opis moze byc pusty
        
        return flag;
    }
    

    private void initItems() {

        /*
        SQL
         */
        _items.add(new Item(1, "pendrive", 34, (float) 12.5, "Bardzo szybki"));
        _items.add(new Item(2, "monitor", 2, (float) 2500, "Znakomity"));
        _items.add(new Item(3, "tv", 4, (float) 5000, "Duzy"));

        DefaultTableModel model = new DefaultTableModel();
        jTableItems = new JTable(model);
        
        for(String col : _tableColumns)
        {
            model.addColumn(col);
        }
        
//        model.addColumn("Id");
//        model.addColumn("Nazwa");
//        model.addColumn("Liczba sztuk");
//        model.addColumn("Cena");
//        model.addColumn("Opis");

        for (Item item : _items) {
            model.addRow(item.getItem());
        }

    }
    
    
//    public void clearItemsTable(final DefaultTableModel model) {
//        for (int i = model.getRowCount() - 1; i >= 0; i--) {
//            model.removeRow(i);
//        }
//        model.setColumnCount(0);
//        model.addColumn("Id");
//        model.addColumn("Nazwa");
//        model.addColumn("Liczba sztuk");
//        model.addColumn("Cena");
//        model.addColumn("Opis");
//    }

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
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

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

        jButton2.setText("Zmodyfikuj");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Usuń");

        jButton4.setText("jButton4");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
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
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(342, 342, 342)
                        .addComponent(jButton4))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                                .addGap(60, 60, 60)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(170, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
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
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        _frame.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        modifyItem(_row);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed


//
//        if (checkUserInput()) {
//            System.out.println("dobre dane");
//        }else
//        {
//            System.out.println("zle dane");
//        }
//        
//        System.out.println("opis " + jTextFieldDescription.getText());
//        
//        if (jTextFieldDescription.getText().length() <= 0) {
//            System.out.println("super");
//        }
        
//       Float price = (float)0;
//       try{
//           price = Float.parseFloat(jTextFieldPrice.getText());
//           if (price < 0 || price > 1000000) {
//                System.out.println("Nieprawidlowa cena");
//           }
//       }catch(NumberFormatException ex)
//       {
//           System.out.println("Nieprawidlowa cena");
//       }
//        System.out.println("mamy "+price);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jSpinnerNumberofItemsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinnerNumberofItemsStateChanged
       if ((Integer)jSpinnerNumberofItems.getValue() < 1) {
            jSpinnerNumberofItems.setValue(1);
        }
    }//GEN-LAST:event_jSpinnerNumberofItemsStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinnerNumberofItems;
    private javax.swing.JTable jTableItems;
    private javax.swing.JTextField jTextFieldDescription;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTextField jTextFieldPrice;
    // End of variables declaration//GEN-END:variables
}
