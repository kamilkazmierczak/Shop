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
public class Item {
    private Integer _id_towaru;
    private String _nazwa;
    private Integer _liczba_sztuk;
    private Float _cena;
    private String _opis;

    public Item(Integer _id_towaru, String _nazwa, Integer _liczba_sztuk, Float _cena, String _opis) {
        this._id_towaru = _id_towaru;
        this._nazwa = _nazwa;
        this._liczba_sztuk = _liczba_sztuk;
        this._cena = _cena;
        this._opis = _opis;
    }

    public Integer getId_towaru() {
        return _id_towaru;
    }

    public void setId_towaru(Integer _id_towaru) {
        this._id_towaru = _id_towaru;
    }

    public String getNazwa() {
        return _nazwa;
    }

    public void setNazwa(String _nazwa) {
        this._nazwa = _nazwa;
    }

    public Integer getLiczba_sztuk() {
        return _liczba_sztuk;
    }

    public void setLiczba_sztuk(Integer _liczba_sztuk) {
        this._liczba_sztuk = _liczba_sztuk;
    }

    public Float getCena() {
        return _cena;
    }

    public void setCena(Float _cena) {
        this._cena = _cena;
    }

    public String getOpis() {
        return _opis;
    }

    public void setOpis(String _opis) {
        this._opis = _opis;
    }
    
    public Object[] getItem(){
        Object[] item = {this._nazwa,this._liczba_sztuk,this._cena,this._opis};
        return item;
    }
    
    
    
}
