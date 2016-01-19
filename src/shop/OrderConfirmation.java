/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import java.util.Date;

/**
 *
 * @author Kamil
 */
public class OrderConfirmation extends javax.swing.JPanel {

    private JFrame _frame;
    private ArrayList<Item> _cartContent;
    private Integer _spinnerValue;
    private Float _discount;
    Map<Integer, Integer> _item = new HashMap<Integer, Integer>();
    private ArrayList<Address> _addressess = new ArrayList<Address>();

    /**
     * Creates new form OrderConfirmation
     */
    public OrderConfirmation(JFrame frame, ArrayList<Item> cartContent) {
        initComponents();
        _frame = frame;
        _cartContent = cartContent;
        initComboAndMap();
        initAddress();
        initDiscount();
        disabelSpinnerEdit();
        jLabelKoszt.setText(calculateOrderCost().toString());
    }

    private void disabelSpinnerEdit() {
        JFormattedTextField tf = ((JSpinner.DefaultEditor) jSpinner1.getEditor()).getTextField();
        tf.setEditable(false);
    }

    private void initAddress() {
        jComboAddress.setModel(new javax.swing.DefaultComboBoxModel<>());

        Database db = Database.getDatabase();
        db.connect();
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
        
        
        
        

        db.close();

        for (Address addr : _addressess) {
            jComboAddress.addItem(addr.getMiejscowosc());
        }

    }
    
    private void initDiscount()
    {
        Database db = Database.getDatabase();
        db.connect();
        
        String condition = "id_uzytkownika = "+db.getUserID();
        ArrayList<ArrayList<Object>> data2d = db.select2("znizka", "uzytkownik", condition,
                new ArrayList<SelectTypes>(Arrays.asList(SelectTypes.INT)));
        
        Float procent_znizki =(float)0; 
        for (ArrayList<Object> row : data2d) {
            procent_znizki = Functions.discountCodeToPercent((Integer)row.get(0));
        }
        db.close();
        _discount = procent_znizki;
        System.out.println(procent_znizki);
    }

    private void initComboAndMap() {
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>());
        for (Item item : _cartContent) {
            _item.put(item.getId_towaru(), 1);
            jComboBox1.addItem(item.getNazwa());

        }

    }
    
    
    private Float calculateOrderCost()
    {
        Integer ilosc = 0;
        Float wartosc_zamowienia = (float) 0;

        try {
            for (Item item : _cartContent) {
                ilosc = _item.get(item.getId_towaru());

                if (ilosc == null) {
                    ilosc = 1;
                    wartosc_zamowienia += (item.getCena() - (item.getCena() * (_discount / 100))) * ilosc;
                } else {
                    wartosc_zamowienia += (item.getCena() - (item.getCena() * (_discount / 100))) * ilosc;
                }
            }
        } catch (NullPointerException e) {
            return (float) -1;
        }

        wartosc_zamowienia = (float) ((float) Math.round(wartosc_zamowienia * 100d) / 100d);

        return wartosc_zamowienia;
    }

    private void confirmOrder() {

        DeliverType dostawaTyp = DeliverType.KURIER;
        switch (jComboDeliver.getSelectedIndex()) {
            case 0:
                dostawaTyp = DeliverType.KURIER;
                break;
            case 1:
                dostawaTyp = DeliverType.POCZTA;
                break;
                        
            case 2:
                dostawaTyp = DeliverType.OSOBISCIE;
                break;

            default:
                System.out.println("blad");
        }
        
        Float koszt_transportu = Functions.calculateDeliverCost(dostawaTyp);       
        String sposob_platnosci = Functions.addApostrophes(jComboPayment.getSelectedItem().toString());   
        String sposob_transportu = Functions.addApostrophes(jComboDeliver.getSelectedItem().toString());
        Integer nr_domu = _addressess.get(jComboAddress.getSelectedIndex()).getNr_domu();
        String miejscowosc = Functions.addApostrophes(_addressess.get(jComboAddress.getSelectedIndex()).getMiejscowosc());
        String adres = Functions.addApostrophes(_addressess.get(jComboAddress.getSelectedIndex()).getUlica());
        String kod_pocztowy = Functions.addApostrophes(_addressess.get(jComboAddress.getSelectedIndex()).getKod_pocztowy());
        Integer nr_telefonu = _addressess.get(jComboAddress.getSelectedIndex()).getNr_telefonu();  
        Integer adresID = _addressess.get(jComboAddress.getSelectedIndex()).getId();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String data = Functions.addApostrophes(dateFormat.format(new Date()).toString());
        Float koszt = calculateOrderCost();
 
        Database db = Database.getDatabase();
        db.connect();
 
        System.out.println("data->"+data);
        
        String value = "to_date("+data+",'YYYY-MM-DD')"+","+koszt_transportu+","+sposob_transportu+","+sposob_platnosci+","+"'pakowany'"+","+koszt+","+db.getUserID()+","+adresID;
        //dla tabeli zamowienie
        db.insert("zamowienie", "data_zamowienia,koszt_transportu,sposob_transportu,sposob_platnosci,status,koszt,uzytkownik,adres", value);
        
        
    
        ArrayList<ArrayList<Object>> data2d = db.select2("ZAMOWIENIE_SEQ.CURRVAL", "dual", null,
                new ArrayList<SelectTypes>(Arrays.asList(
                        SelectTypes.INT)));

        Integer id_zamowienia = 0;
        for (ArrayList<Object> row : data2d) {         
                   id_zamowienia = (Integer)row.get(0); 
        }
             
        //dla tabeli przydzial
        Integer ilosc = 0;
        String condition ="";
        for (Item item : _cartContent) {
            ilosc = _item.get(item.getId_towaru());

            
            value = item.getId_towaru() + "," + id_zamowienia + "," + ilosc + "," + item.getCena();
            db.insert("przydzial", "towar,zamowienie,liczba_sztuk,cena", value);
            
            condition = "id_towaru = "+item.getId_towaru();
            db.update("towar","liczba_sztuk = liczba_sztuk - "+ilosc,condition);

        }



    db.close();
    }

    private boolean checkForAddItemNumber() {
        boolean status = false; //false jesli nie wolno dodac wiecej sztuk produktu
        Integer liczbaDostepnychSztuk = 0;

        //wersja ze sprawdzeniem bazy danych
//       Database db = Database.getDatabase();
//       db.connect();      
//
//        String  selectedItem = _cartContent.get(jComboBox1.getSelectedIndex()).getNazwa();
//        String condition = "nazwa = '"+selectedItem+"'";
//        ArrayList<Object> data = db.select("liczba_sztuk","towar",condition,SelectTypes.INT);     
//        for (Object result : data) {
//            liczbaDostepnychSztuk = (Integer)result;
//        }
//        db.close();
        liczbaDostepnychSztuk = _cartContent.get(jComboBox1.getSelectedIndex()).getLiczba_sztuk();

        if (liczbaDostepnychSztuk + 1 == (Integer) jSpinner1.getValue()) {
            status = false;
        } else {
            status = true;
        }

        return status;
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
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jComboPayment = new javax.swing.JComboBox<>();
        jComboAddress = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabelCity = new javax.swing.JLabel();
        jLabelAddress = new javax.swing.JLabel();
        jLabelZipCode = new javax.swing.JLabel();
        jLabelPhone = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboDeliver = new javax.swing.JComboBox<>();
        jLabelNrDomu = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabelKoszt = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Sitka Small", 0, 36)); // NOI18N
        jLabel1.setText(" zamówienia");

        jLabel2.setFont(new java.awt.Font("Sitka Small", 0, 36)); // NOI18N
        jLabel2.setText("Potwierdzenie");

        jButton1.setText("Potwierdź");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Zrezygnuj");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("Sposób płatności:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "..." }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Przedmiot:");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("Ilość:");

        jSpinner1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner1StateChanged(evt);
            }
        });
        jSpinner1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jSpinner1MouseExited(evt);
            }
        });
        jSpinner1.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jSpinner1InputMethodTextChanged(evt);
            }
        });
        jSpinner1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jSpinner1PropertyChange(evt);
            }
        });

        jComboPayment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Przy odbiorze", "Przelew", "PayPal" }));
        jComboPayment.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboPaymentItemStateChanged(evt);
            }
        });
        jComboPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboPaymentActionPerformed(evt);
            }
        });

        jComboAddress.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboAddressActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setText("Adres:");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setText("Twoje dane:");

        jLabelCity.setText("City");

        jLabelAddress.setText("Address");

        jLabelZipCode.setText("ZipCode");

        jLabelPhone.setText("Phone");

        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setText("Sposób transportu:");

        jComboDeliver.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kurier", "Poczta", "Odbiór osobisty" }));
        jComboDeliver.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboDeliverItemStateChanged(evt);
            }
        });
        jComboDeliver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboDeliverActionPerformed(evt);
            }
        });

        jLabelNrDomu.setText("nr domu");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setText("Koszt:");

        jLabelKoszt.setText("koszt");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(32, 32, 32)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel6))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jComboAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabelCity)
                                        .addComponent(jLabelAddress)
                                        .addComponent(jLabelZipCode)
                                        .addComponent(jLabelPhone)
                                        .addComponent(jLabelNrDomu)))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(38, 38, 38)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel4))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jLabel1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboDeliver, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 39, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabelKoszt)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(34, 34, 34)
                    .addComponent(jLabel2)
                    .addContainerGap(22, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboDeliver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelCity)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelAddress)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelZipCode)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelPhone)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelNrDomu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabelKoszt))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(21, 21, 21)
                    .addComponent(jLabel2)
                    .addContainerGap(407, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        _frame.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        confirmOrder();
        _frame.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed

        Integer id_towaru = _cartContent.get(jComboBox1.getSelectedIndex()).getId_towaru();
        jSpinner1.setValue(_item.get(id_towaru));


    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        //performer dziala lepiej
// TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jSpinner1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner1StateChanged

        //System.out.println("zmiana stanu");
        if ((Integer) jSpinner1.getValue() < 1) {
            jSpinner1.setValue(1);
            _spinnerValue = (Integer) jSpinner1.getValue();
        } else if (checkForAddItemNumber()) {
            _item.put(_cartContent.get(jComboBox1.getSelectedIndex()).getId_towaru(), (Integer) jSpinner1.getValue());
            _spinnerValue = (Integer) jSpinner1.getValue();
            
            
            jLabelKoszt.setText(calculateOrderCost().toString());
            
            
        } else {
            jSpinner1.setValue(_spinnerValue);
        }
        
       

        // TODO add your handling code here:
    }//GEN-LAST:event_jSpinner1StateChanged

    private void jSpinner1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSpinner1MouseExited

        // TODO add your handling code here:
    }//GEN-LAST:event_jSpinner1MouseExited

    private void jComboPaymentItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboPaymentItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboPaymentItemStateChanged

    private void jComboPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboPaymentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboPaymentActionPerformed

    private void jComboDeliverItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboDeliverItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboDeliverItemStateChanged

    private void jComboDeliverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboDeliverActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboDeliverActionPerformed

    private void jSpinner1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jSpinner1PropertyChange
        // TODO add your handling code here:
        System.out.println("property changed");
    }//GEN-LAST:event_jSpinner1PropertyChange

    private void jSpinner1InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jSpinner1InputMethodTextChanged
        // TODO add your handling code here:
        System.out.println("method changed");
    }//GEN-LAST:event_jSpinner1InputMethodTextChanged

    private void jComboAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboAddressActionPerformed
        // TODO add your handling code here:

        Integer nr_domu = _addressess.get(jComboAddress.getSelectedIndex()).getNr_domu();
        String miejscowosc = _addressess.get(jComboAddress.getSelectedIndex()).getMiejscowosc();
        String adres = _addressess.get(jComboAddress.getSelectedIndex()).getUlica();
        String kod_pocztowy = _addressess.get(jComboAddress.getSelectedIndex()).getKod_pocztowy();
        Integer nr_telefonu = _addressess.get(jComboAddress.getSelectedIndex()).getNr_telefonu();

        //System.out.println(nr_domu);
        jLabelAddress.setText(adres + " (ulica)");
        jLabelCity.setText(miejscowosc + " (miejscowość)");
        jLabelPhone.setText(nr_telefonu.toString() + " (telefon)");
        jLabelZipCode.setText(kod_pocztowy + " (kod pocztowy)");
        jLabelNrDomu.setText(nr_domu.toString() + " (nr domu)");

        //addr.getMiejscowosc()

    }//GEN-LAST:event_jComboAddressActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboAddress;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboDeliver;
    private javax.swing.JComboBox<String> jComboPayment;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelAddress;
    private javax.swing.JLabel jLabelCity;
    private javax.swing.JLabel jLabelKoszt;
    private javax.swing.JLabel jLabelNrDomu;
    private javax.swing.JLabel jLabelPhone;
    private javax.swing.JLabel jLabelZipCode;
    private javax.swing.JSpinner jSpinner1;
    // End of variables declaration//GEN-END:variables
}
