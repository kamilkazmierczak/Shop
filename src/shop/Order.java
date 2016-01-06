/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

/**
 *
 * @author Kamil
 */
public class Order {
    
    private Integer id_zamowienia;
    private Date data_zamowienia;
    private Date data_dostawy;
    private Float koszt_transportu;
    private String sposob_transportu;
    private String sposob_platnosci;
    private String status;
    private Float calkowity_koszt;

    public Order(Integer id_zamowienia, Date data_zamowienia, Date data_dostawy, Float koszt_transportu, String sposob_transportu, String sposob_platnosci, String status, Float calkowity_koszt) {
        this.id_zamowienia = id_zamowienia;
        this.data_zamowienia = data_zamowienia;
        this.data_dostawy = data_dostawy;
        this.koszt_transportu = koszt_transportu;
        this.sposob_transportu = sposob_transportu;
        this.sposob_platnosci = sposob_platnosci;
        this.status = status;
        this.calkowity_koszt = calkowity_koszt;
    }

    public Integer getId_zamowienia() {
        return id_zamowienia;
    }

    public void setId_zamowienia(Integer id_zamowienia) {
        this.id_zamowienia = id_zamowienia;
    }

    public Date getData_zamowienia() {
        return data_zamowienia;
    }

    public void setData_zamowienia(Date data_zamowienia) {
        this.data_zamowienia = data_zamowienia;
    }

    public Date getData_dostawy() {
        return data_dostawy;
    }

    public void setData_dostawy(Date data_dostawy) {
        this.data_dostawy = data_dostawy;
    }

    public Float getKoszt_transportu() {
        return koszt_transportu;
    }

    public void setKoszt_transportu(Float koszt_transportu) {
        this.koszt_transportu = koszt_transportu;
    }

    public String getSposob_transportu() {
        return sposob_transportu;
    }

    public void setSposob_transportu(String sposob_transportu) {
        this.sposob_transportu = sposob_transportu;
    }

    public String getSposob_platnosci() {
        return sposob_platnosci;
    }

    public void setSposob_platnosci(String sposob_platnosci) {
        this.sposob_platnosci = sposob_platnosci;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Float getCalkowity_koszt() {
        return calkowity_koszt;
    }

    public void setCalkowity_koszt(Float calkowity_koszt) {
        this.calkowity_koszt = calkowity_koszt;
    }
    
    
    
    
}
