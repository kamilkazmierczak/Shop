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
import java.util.Objects;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Kamil
 */
public class EditAddresses extends javax.swing.JPanel {

    /**
     * Creates new form EditAddresses
     */
    private JFrame _frame;
    private ArrayList<Address> _addressess = new ArrayList<Address>();
    private Object[][] _tableContent;

    public EditAddresses(JFrame frame) {
        initComponents();
        initAddresses();
        _frame = frame;
        jScrollPane1.getViewport().setView(jTableAddresses);
        Functions.disableTableEdit(jTableAddresses);
        initListener();
        jLabelStatus.setVisible(false);

    }

    private void selectAddress(Integer row) {
        jTextFieldCity.setText(_addressess.get(row).getMiejscowosc());
        jTextFieldAddress.setText(_addressess.get(row).getUlica());
        jTextFieldHomeNumber.setText((_addressess.get(row).getNr_domu()).toString());
        jTextFieldZipCode.setText(_addressess.get(row).getKod_pocztowy());

        if (_addressess.get(row).getNr_telefonu() == 0) {
            jTextFieldPhone.setText("");
        } else {
            jTextFieldPhone.setText((_addressess.get(row).getNr_telefonu()).toString());
        }
    }

    private boolean dataCorrect() {
        boolean correctData = true;

        Integer nr_telefonu = 0;
        if (jTextFieldPhone.getText().length() > 0) {

            if (!Functions.correctPhoneNumber(jTextFieldPhone.getText())) {
                correctData = false;
            }

        }

        if (!Functions.correctZipCode(jTextFieldZipCode.getText())) {
            correctData = false;
        }

        if (!Functions.correctHomeNumber(jTextFieldHomeNumber.getText())) {
            correctData = false;
        }

        if (!Functions.correctString(jTextFieldAddress.getText()) || !Functions.correctString(jTextFieldCity.getText())) {
            correctData = false;
        }

        
        if (!correctData) {
            jLabelStatus.setVisible(true);
        }else
            jLabelStatus.setVisible(false);
        
        return correctData;
    }

    private void initAddresses() {

        Database db = Database.getDatabase();
       
        String condition = "uzytkownik = " + db.getUserID();
        ArrayList<ArrayList<Object>> data2d = db.select2("id,ulica,miejscowosc,kod_pocztowy,nr_telefonu,nr_domu", "adres", condition,
                new ArrayList<SelectTypes>(Arrays.asList(
                        SelectTypes.INT,
                        SelectTypes.STRING,
                        SelectTypes.STRING,
                        SelectTypes.STRING,
                        SelectTypes.INT,
                        SelectTypes.INT)));

        for (ArrayList<Object> row : data2d) {
            _addressess.add(new Address(
                    (Integer) row.get(0),
                    (Integer) row.get(4),
                    (Integer) row.get(5),
                    (String) row.get(3),
                    (String) row.get(2),
                    (String) row.get(1)));

        }


        DefaultTableModel model = new DefaultTableModel();
        jTableAddresses = new JTable(model);
        model.addColumn("Miejscowość");
        model.addColumn("Ulica");
        model.addColumn("Nr domu");
        model.addColumn("Kod pocztowy");
        model.addColumn("Nr telefonu");

        for (Address addr : _addressess) {
            model.addRow(addr.getAddress());
        }

    }

    private void initListener() {
        jTableAddresses.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                JTable table = (JTable) me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                if (me.getClickCount() == 1) {
                    selectAddress(row);
                }
            }
        });
    }

    private void deleteAddress(int tableRow) {

        DefaultTableModel model = (DefaultTableModel) jTableAddresses.getModel();

        //Integer id = (Integer) jTableAddresses.getModel().getValueAt(tableRow, 0);
        Integer nr_domu = (Integer) jTableAddresses.getModel().getValueAt(tableRow, 2);
        String miejscowosc = (String) jTableAddresses.getModel().getValueAt(tableRow, 0);

        for (int i = 0; i < _addressess.size(); i++) {
            if (_addressess.get(i).getNr_domu() == nr_domu && _addressess.get(i).getMiejscowosc() == miejscowosc) {

                //_addressess.get(i).getID - >sql modify wgere id == getID
                Database db = Database.getDatabase();
                //db.connect();
                Integer id = _addressess.get(i).getId();
                String condition = "id = " + id;
                db.delete("adres", condition);
                //db.close();

                _addressess.remove(i);

            }
        }

        clearAddressesTable(model);
        for (Address addr : _addressess) {
            //model.addRow(new Object[]{addr.getMiejscowosc(),addr.getUlica(),addr.getNr_domu(),addr.getKod_pocztowy(),addr.getNr_telefonu()});
            model.addRow(addr.getAddress());
        }
    }

    private void modifyAddress(int tableRow) {

        DefaultTableModel model = (DefaultTableModel) jTableAddresses.getModel();

        //Integer id = (Integer) jTableAddresses.getModel().getValueAt(tableRow, 0);
        Integer nr_domu = (Integer) jTableAddresses.getModel().getValueAt(tableRow, 2);
        String miejscowosc = (String) jTableAddresses.getModel().getValueAt(tableRow, 0);

        for (int i = 0; i < _addressess.size(); i++) {
            if (_addressess.get(i).getNr_domu() == nr_domu && _addressess.get(i).getMiejscowosc() == miejscowosc) {

                setNewData(_addressess.get(i));

                Database db = Database.getDatabase();
                //db.connect();
                Integer id = _addressess.get(i).getId();
                String condition = "id = " + id;

                String ulica_ = Functions.addApostrophes(_addressess.get(i).getUlica());
                String miejscowosc_ = Functions.addApostrophes(_addressess.get(i).getMiejscowosc());
                String kod_pocztowy_ = Functions.addApostrophes(_addressess.get(i).getKod_pocztowy());
                Integer nr_telefonu_ = _addressess.get(i).getNr_telefonu();
                Integer nr_domu_ = _addressess.get(i).getNr_domu();

                db.update("adres", "ulica = " + ulica_, condition);
                db.update("adres", "miejscowosc = " + miejscowosc_, condition);
                db.update("adres", "kod_pocztowy = " + kod_pocztowy_, condition);
                db.update("adres", "nr_telefonu = " + nr_telefonu_, condition);
                db.update("adres", "nr_domu = " + nr_domu_, condition);

                //db.close();
            }
        }

        clearAddressesTable(model);
        for (Address addr : _addressess) {
            //model.addRow(new Object[]{addr.getMiejscowosc(),addr.getUlica(),addr.getNr_domu(),addr.getKod_pocztowy(),addr.getNr_telefonu()});
            model.addRow(addr.getAddress());
        }
    }

    private void setNewData(Address addr) {
        addr.setMiejscowosc(jTextFieldCity.getText());
        addr.setKod_pocztowy(jTextFieldZipCode.getText());
        addr.setNr_domu(Integer.parseInt(jTextFieldHomeNumber.getText()));
        addr.setNr_telefonu(Integer.parseInt(jTextFieldPhone.getText()));
        addr.setUlica(jTextFieldAddress.getText());

    }

    public void clearAddressesTable(final DefaultTableModel model) {
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        model.setColumnCount(0);
        model.addColumn("Miejscowość");
        model.addColumn("Ulica");
        model.addColumn("Nr domu");
        model.addColumn("Kod pocztowy");
        model.addColumn("Nr telefonu");

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableAddresses = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jTextFieldPhone = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldHomeNumber = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldZipCode = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldCity = new javax.swing.JTextField();
        jTextFieldAddress = new javax.swing.JTextField();
        jButtonDelete = new javax.swing.JButton();
        jButtonModify = new javax.swing.JButton();
        jLabelStatus = new javax.swing.JLabel();

        jLabel2.setFont(new java.awt.Font("Sitka Small", 0, 48)); // NOI18N
        jLabel2.setText("Zarządzaj adresami");

        jTableAddresses.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableAddresses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableAddressesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableAddresses);

        jButton1.setText("Powrót");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setText("Miejscowość:");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setText("Nr domu:");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setText("Kod pocztowy:");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setText("Ulica:");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setText("Nr telefonu:");
        jLabel7.setToolTipText("");

        jButtonDelete.setText("Usuń");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        jButtonModify.setText("Zmodyfikuj");
        jButtonModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModifyActionPerformed(evt);
            }
        });

        jLabelStatus.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabelStatus.setText("Błędne dane");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(185, 185, 185)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldZipCode, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldCity, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldHomeNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabelStatus)
                                    .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jButtonModify, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextFieldCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextFieldAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextFieldZipCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextFieldHomeNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTextFieldPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelStatus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonModify, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        _frame.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTableAddressesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableAddressesMouseClicked

    }//GEN-LAST:event_jTableAddressesMouseClicked

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        if (dataCorrect()) {
            deleteAddress(jTableAddresses.getSelectedRow());
        }

    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jButtonModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModifyActionPerformed
        if (dataCorrect()) {
            modifyAddress(jTableAddresses.getSelectedRow());
        }

    }//GEN-LAST:event_jButtonModifyActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonModify;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableAddresses;
    private javax.swing.JTextField jTextFieldAddress;
    private javax.swing.JTextField jTextFieldCity;
    private javax.swing.JTextField jTextFieldHomeNumber;
    private javax.swing.JTextField jTextFieldPhone;
    private javax.swing.JTextField jTextFieldZipCode;
    // End of variables declaration//GEN-END:variables
}
