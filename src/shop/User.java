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
public class User {
    
    private Integer _id_uzytkownika;
    private String _imie;
    private String _nazwisko;
    private String _login;
    private String _haslo;
    private Integer _kod_znizki;
    private Float _procentZnizki;
    
    public User(Integer _id_uzytkownika, String _imie, String _nazwisko, String _login, String _haslo, Integer _kod_znizki) {
        this._id_uzytkownika = _id_uzytkownika;
        this._imie = _imie;
        this._nazwisko = _nazwisko;
        this._login = _login;
        this._haslo = _haslo;
        this._kod_znizki = _kod_znizki;
    }
    
    public User(Integer _id_uzytkownika, String _imie, String _nazwisko, String _login, String _haslo) {
        this._id_uzytkownika = _id_uzytkownika;
        this._imie = _imie;
        this._nazwisko = _nazwisko;
        this._login = _login;
        this._haslo = _haslo;
        this._kod_znizki = 0;
    }

    public Float getProcentZnizki() {
        return _procentZnizki;
    }

    public void setProcentZnizki(Float _procentZnizki) {
        this._procentZnizki = _procentZnizki;
    }
    

    public Integer getId_uzytkownika() {
        return _id_uzytkownika;
    }

    public void setId_uzytkownika(Integer _id_uzytkownika) {
        this._id_uzytkownika = _id_uzytkownika;
    }

    public String getImie() {
        return _imie;
    }

    public void setImie(String _imie) {
        this._imie = _imie;
    }

    public String getNazwisko() {
        return _nazwisko;
    }

    public void setNazwisko(String _nazwisko) {
        this._nazwisko = _nazwisko;
    }

    public String getLogin() {
        return _login;
    }

    public void setLogin(String _login) {
        this._login = _login;
    }

    public String getHaslo() {
        return _haslo;
    }

    public void setHaslo(String _haslo) {
        this._haslo = _haslo;
    }

    public Integer getKod_znizki() {
        return _kod_znizki;
    }

    public void setKod_znizki(Integer _kod_znizki) {
        this._kod_znizki = _kod_znizki;
    }
    
    public Object[] getUser(){
        Float procentZnizki = Functions.discountCodeToPercent(this._kod_znizki);
        this._procentZnizki = procentZnizki;
        
        Object[] user = {this._id_uzytkownika,this._imie,this._nazwisko,this._login,this._haslo,procentZnizki};
        
        return user;
    }
    
    public Object[] getUserWithPercent(){
        Object[] user = {this._id_uzytkownika,this._imie,this._nazwisko,this._login,this._haslo,this._procentZnizki};
        return user;
    }
    
}
