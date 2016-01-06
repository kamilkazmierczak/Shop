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
public class Discount {
 
    private Integer _kod_znizki;
    private Float _ile;

    
    public Discount() {
        this._kod_znizki = null;
        this._ile = null;
    }
    
    public Discount(Integer _kod_znizki, Float _ile) {
        this._kod_znizki = _kod_znizki;
        this._ile = _ile;
    }
    
    

    public Integer getKod_znizki() {
        return _kod_znizki;
    }

    public void setKod_znizki(Integer _kod_znizki) {
        this._kod_znizki = _kod_znizki;
    }

    public Float getIle() {
        return _ile;
    }

    public void setIle(Float _ile) {
        this._ile = _ile;
    }
    
    public Object[] getDiscount(){
        Object[] user = {this._kod_znizki,this._ile};
        return user;
    }
    
    
}
