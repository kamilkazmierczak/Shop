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
import java.util.Objects;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Kamil
 */
public class Products extends javax.swing.JPanel {

    /**
     * Creates new form Products
     */
    private JFrame _frame;
    private ArrayList _items = new ArrayList();
    private ArrayList _cart = new ArrayList();

    private Object[][] _tableContent;
    private ArrayList<Item> _cartContent = new ArrayList<Item>();

    public Products(JFrame frame) {
        initComponents();
        initCart();
        this._frame = frame;
        addItems();
        jScrollPane1.getViewport().setView(jTable1);
        jScrollPane2.getViewport().setView(CartTable);
        initListeners();
        infoLabel.setVisible(false);
        Functions.disableTableEdit(jTable1);
        Functions.disableTableEdit(CartTable);
    }

    private void addItems() {
        _items.add(new Item(1, "pendrive", 34, (float) 12.5, "Bardzo szybki"));
        _items.add(new Item(2, "monitor", 2, (float) 2500, "Znakomity"));
        _items.add(new Item(3, "tv", 4, (float) 5000, "Duzy"));

        _tableContent = new Object[_items.size()][];
        for (int i = 0; i < _items.size(); i++) {
            _tableContent[i] = ((Item) _items.get(i)).getItem();
        }

        String[] ColumnNames = {"Id", "Nazwa", "Liczba sztuk", "Cena", "Opis"};
        jTable1 = new JTable(_tableContent, ColumnNames);
        jTable1.removeColumn(jTable1.getColumnModel().getColumn(0));
    }

    private void initCart() {
        DefaultTableModel model = new DefaultTableModel();
        CartTable = new JTable(model);
        model.addColumn("Id");
        model.addColumn("Nazwa");
        model.addColumn("Cena");
        CartTable.removeColumn(CartTable.getColumnModel().getColumn(0));

    }

    private void addItemToCart(int tableRow) {

        DefaultTableModel model = (DefaultTableModel) CartTable.getModel();

        Integer id = (Integer) jTable1.getModel().getValueAt(tableRow, 0);
        //Integer id = (Integer) jTable1.getValueAt(tableRow, 0);

        for (int i = 0; i < _items.size(); i++) {
            if (((Item) _items.get(i)).getId_towaru() == id) {

                boolean already = false;
                for (int j = 0; j < _cartContent.size(); j++) {
                    if (_cartContent.get(j).getId_towaru() == id) {
                        already = true;
                    }
                }

                if (!already) {
                    //System.out.println("WTF"+((Item)_items.get(i)).getNazwa());
                    _cartContent.add((Item) _items.get(i));
                    //System.out.println("WTF COUNT "+ _cartContent.size());
                    model.addRow(new Object[]{_cartContent.get(_cartContent.size() - 1).getId_towaru(), _cartContent.get(_cartContent.size() - 1).getNazwa(), _cartContent.get(_cartContent.size() - 1).getCena()});
                    //model.addRow(new Object[]{((Item) _items.get(i)).getId_towaru(),((Item) _items.get(i)).getNazwa(), ((Item) _items.get(i)).getCena()});
                }
            }
        }
    }

    public void clearCartTable(final DefaultTableModel model) {
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        model.setColumnCount(0);
        model.addColumn("Id");
        model.addColumn("Nazwa");
        model.addColumn("Cena");
        CartTable.removeColumn(CartTable.getColumnModel().getColumn(0));
    }

    private void deleteItemFromCart(int tableRow) {

        DefaultTableModel model = (DefaultTableModel) CartTable.getModel();

        Integer id = (Integer) CartTable.getModel().getValueAt(tableRow, 0);

        for (int i = 0; i < _cartContent.size(); i++) {
            if (Objects.equals(((Item) _cartContent.get(i)).getId_towaru(), id)) {
                _cartContent.remove(i);
            }
        }

        clearCartTable(model);
        for (Item item : _cartContent) {
            model.addRow(new Object[]{item.getId_towaru(), item.getNazwa(), item.getCena()});
            System.out.println("left " + item.getId_towaru() + " " + item.getNazwa() + " " + item.getCena());
        }

        System.out.println("id" + id);

    }

    private void initListeners() {
        jTable1.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                JTable table = (JTable) me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                if (me.getClickCount() == 2) {
                    //row = jTable1.convertRowIndexToModel(row);
                    addItemToCart(row);
                }
            }
        });

        CartTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                JTable table = (JTable) me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                if (me.getClickCount() == 2) {
                    //row = CartTable.convertRowIndexToModel(row);
                    deleteItemFromCart(row);
                }
            }
        });
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        CartTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        infoLabel = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Sitka Small", 0, 48)); // NOI18N
        jLabel1.setText("Produkty");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        jLabel2.setFont(new java.awt.Font("Sitka Small", 0, 48)); // NOI18N
        jLabel2.setText("Koszyk");

        CartTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Title 1"
            }
        ));
        jScrollPane2.setViewportView(CartTable);

        jButton1.setText("Realizuj zamówienie");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Powrót do menu");
        jButton2.setToolTipText("");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("jButton3");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setText("jButton5");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        infoLabel.setText("Information Label (invisilbe)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(132, 132, 132)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(101, 101, 101)
                                .addComponent(jButton3)
                                .addGap(91, 91, 91)
                                .addComponent(jButton5)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(jLabel2))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(infoLabel)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(infoLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton5))
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        _frame.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        System.out.println("Zawartość koszyka:");
        for (int i = 0; i < _cartContent.size(); i++) {

            System.out.println(_cartContent.get(i).getId_towaru() + " " + _cartContent.get(i).getNazwa());

        }
        System.out.println("To juz wszysko:");


    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        for (Item item : _cartContent) {
            System.out.println(item.getNazwa());
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if (_cartContent.size() > 0) {
            JFrame frame = new JFrame("Potwierdzenie");
            frame.setContentPane(new OrderConfirmation(frame, _cartContent));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            infoLabel.setVisible(false);
        } else {
            infoLabel.setText("Koszyk jest pusty");
            infoLabel.setVisible(true);
        }


    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable CartTable;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
