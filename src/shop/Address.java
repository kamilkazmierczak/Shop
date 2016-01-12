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
public class Address {

    private Integer _nr_telefonu;
    private Integer _nr_domu;
    private String _kod_pocztowy;
    private String _miejscowosc;
    private String _ulica;
    private Integer _id;

    public Address(Integer id, Integer _nr_telefonu, Integer _nr_domu, String _kod_pocztowy, String _miejscowosc, String _ulica) {
        this._nr_telefonu = _nr_telefonu;
        this._nr_domu = _nr_domu;
        this._kod_pocztowy = _kod_pocztowy;
        this._miejscowosc = _miejscowosc;
        this._ulica = _ulica;
        this._id = id;
    }

    public Address(Integer _nr_telefonu, Integer _nr_domu, String _kod_pocztowy, String _miejscowosc, String _ulica) {
        this._nr_telefonu = _nr_telefonu;
        this._nr_domu = _nr_domu;
        this._kod_pocztowy = _kod_pocztowy;
        this._miejscowosc = _miejscowosc;
        this._ulica = _ulica;
        this._id = null;
    }

    public Address(Integer _nr_domu, String _kod_pocztowy, String _miejscowosc, String _ulica) {
        this._nr_telefonu = 0;
        this._nr_domu = _nr_domu;
        this._kod_pocztowy = _kod_pocztowy;
        this._miejscowosc = _miejscowosc;
        this._ulica = _ulica;
        this._id = null;
    }

    public Integer getId() {
        return _id;
    }

    public void setId(Integer _id) {
        this._id = _id;
    }

    public Integer getNr_telefonu() {
        return _nr_telefonu;
    }

    public void setNr_telefonu(Integer _nr_telefonu) {
        this._nr_telefonu = _nr_telefonu;
    }

    public Integer getNr_domu() {
        return _nr_domu;
    }

    public void setNr_domu(Integer _nr_domu) {
        this._nr_domu = _nr_domu;
    }

    public String getKod_pocztowy() {
        return _kod_pocztowy;
    }

    public void setKod_pocztowy(String _kod_pocztowy) {
        this._kod_pocztowy = _kod_pocztowy;
    }

    public String getMiejscowosc() {
        return _miejscowosc;
    }

    public void setMiejscowosc(String _miejscowosc) {
        this._miejscowosc = _miejscowosc;
    }

    public String getUlica() {
        return _ulica;
    }

    public void setUlica(String _ulica) {
        this._ulica = _ulica;
    }

    public Object[] getAddress() {
        if (this._nr_telefonu == 0) {
        Object[] address = {this._miejscowosc, this._ulica, this._nr_domu, this._kod_pocztowy, ""};
        return address; 
        }else
        {
        Object[] address = {this._miejscowosc, this._ulica, this._nr_domu, this._kod_pocztowy, this._nr_telefonu};
        return address;  
        }
        
       
    }

}
