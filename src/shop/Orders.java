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
public class Orders extends javax.swing.JPanel {

    /**
     * Creates new form Orders
     */
    private JFrame _frame;
    private ArrayList<Order> _orders = new ArrayList<Order>();

    public Orders(JFrame frame) {
        initComponents();
        initOrders();
        _frame = frame;
        jScrollPane1.getViewport().setView(jTableOrders);
        Functions.disableTableEdit(jTableOrders);
        initListener();
    }

    public void initOrders() {
        /*
        SQL
         */
        
        Database db = Database.getDatabase();
        db.connect();
        ArrayList<ArrayList<Object>> data2d = db.select2("id_zamowienia,data_zamowienia,data_dostawy,koszt_transportu,sposob_transportu,sposob_platnosci,status,koszt", "zamowienie", null,
                new ArrayList<SelectTypes>(Arrays.asList(
                        SelectTypes.INT,
                        SelectTypes.DATE,
                        SelectTypes.DATE,
                        SelectTypes.FLOAT,
                        SelectTypes.STRING,
                        SelectTypes.STRING,
                        SelectTypes.STRING,
                        SelectTypes.FLOAT)));
         db.close();
        
        for (ArrayList<Object> row : data2d) {
            _orders.add(new Order(
                    (Integer) row.get(0),
                    Date.stringToDate(row.get(1).toString()),
                    Date.stringToDate(row.get(2).toString()),
                    (float) row.get(3),
                    (String) row.get(4),
                    (String) row.get(5),
                    (String) row.get(6),
                    (float) row.get(7)
            ));
        }

       
        
        
        //_orders.add(new Order(33, new Date(1, 3, 2013), new Date(13, 5, 2015), (float) 12.4, "Kurier", "PayPal", "pakowany", (float) 160.9));
       // _orders.add(new Order(82, new Date(23, 1, 2016), (float) 15.5, "Kurier", "Przelew", "anulowany", (float) 50.12));
        
        

        DefaultTableModel model = new DefaultTableModel();
        jTableOrders = new JTable(model);
        model.addColumn("Id");
        model.addColumn("Data zamowienia");
        model.addColumn("Data dostawy");
        model.addColumn("Koszt transporu");
        model.addColumn("Sposob transportu");
        model.addColumn("Sposob płatności");
        model.addColumn("Status");
        model.addColumn("Koszt");

        for (Order order : _orders) {
            model.addRow(order.getOrder());
        }
    }

    private void initListener() {
        jTableOrders.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                JTable table = (JTable) me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                if (me.getClickCount() == 1) {
                    selectStatus(row);
                }
            }
        });
    }

    private void selectStatus(Integer row) {
        String status = (String) jTableOrders.getModel().getValueAt(row, 6);
        //System.out.println("status to "+status);
        StatusTypes statusType = StatusTypes.UNKNOWN;

        switch (status) {

            case "pakowany":
                statusType = StatusTypes.PACKING;
                break;

            case "wysłany":
                statusType = StatusTypes.SENT;
                break;

            case "anulowany":
                statusType = StatusTypes.CANCELLED;
                break;

            case "odebrany":
                statusType = StatusTypes.RECEIVED;
                break;

            default:
                System.out.println("Nieznany status x");

        }

        setProperRadioButton(statusType);

    }

    private void setProperRadioButton(StatusTypes status) {
        switch (status) {

            case PACKING:
                jRadioPacking.setSelected(true);
                break;

            case SENT:
                jRadioSent.setSelected(true);
                break;

            case CANCELLED:
                jRadioCancelled.setSelected(true);
                break;

            case RECEIVED:
                jRadioReceived.setSelected(true);
                break;

            default:
                System.out.println("Nie udalo sie wybrac buttona");
        }
    }

    private void modifyOrderStatus(int tableRow, String status) {

        DefaultTableModel model = (DefaultTableModel) jTableOrders.getModel();

        Integer id = (Integer) jTableOrders.getModel().getValueAt(tableRow, 0);

        for (int i = 0; i < _orders.size(); i++) {
            if (_orders.get(i).getId_zamowienia() == id) {
                
                System.out.println("status :"+status);
                String condition = "id_zamowienia = "+id;
                Database db = Database.getDatabase();
                db.connect();
                db.update("zamowienie", "status = '" + status + "' ", condition);
                db.close();
                            
                _orders.get(i).setStatus(status);
            }
        }

        clearOrdersTable(model);
        for (Order order : _orders) {
            model.addRow(order.getOrder());
        }
    }

    public void clearOrdersTable(final DefaultTableModel model) {
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        model.setColumnCount(0);
        model.addColumn("Id");
        model.addColumn("Data zamowienia");
        model.addColumn("Data dostawy");
        model.addColumn("Koszt transporu");
        model.addColumn("Sposob transportu");
        model.addColumn("Sposob płatności");
        model.addColumn("Status");
        model.addColumn("Koszt");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableOrders = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jRadioPacking = new javax.swing.JRadioButton();
        jRadioCancelled = new javax.swing.JRadioButton();
        jRadioSent = new javax.swing.JRadioButton();
        jRadioReceived = new javax.swing.JRadioButton();
        jButton2 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Sitka Small", 0, 48)); // NOI18N
        jLabel1.setText("Zamówienia");

        jTableOrders.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableOrders);

        jButton1.setText("Powrót");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Sitka Small", 0, 36)); // NOI18N
        jLabel2.setText("Status");

        buttonGroup1.add(jRadioPacking);
        jRadioPacking.setText("Pakowany");

        buttonGroup1.add(jRadioCancelled);
        jRadioCancelled.setText("Anulowany");

        buttonGroup1.add(jRadioSent);
        jRadioSent.setText("Wysłany");
        jRadioSent.setToolTipText("");

        buttonGroup1.add(jRadioReceived);
        jRadioReceived.setText("Odebrany");

        jButton2.setText("Zapisz");
        jButton2.setToolTipText("");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 637, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel2)
                                .addComponent(jRadioPacking, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jRadioReceived, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jRadioCancelled, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jRadioSent, javax.swing.GroupLayout.Alignment.LEADING)))
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(191, 191, 191)
                        .addComponent(jLabel1)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioPacking)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioCancelled)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioSent)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioReceived)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        _frame.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        if (jRadioCancelled.isSelected()) {
            modifyOrderStatus(jTableOrders.getSelectedRow(), Functions.statusToString(StatusTypes.CANCELLED));
        } else if (jRadioPacking.isSelected()) {
            modifyOrderStatus(jTableOrders.getSelectedRow(), Functions.statusToString(StatusTypes.PACKING));
        } else if (jRadioReceived.isSelected()) {
            modifyOrderStatus(jTableOrders.getSelectedRow(), Functions.statusToString(StatusTypes.RECEIVED));
        } else {
            modifyOrderStatus(jTableOrders.getSelectedRow(), Functions.statusToString(StatusTypes.SENT));
        }


    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JRadioButton jRadioCancelled;
    private javax.swing.JRadioButton jRadioPacking;
    private javax.swing.JRadioButton jRadioReceived;
    private javax.swing.JRadioButton jRadioSent;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableOrders;
    // End of variables declaration//GEN-END:variables
}
