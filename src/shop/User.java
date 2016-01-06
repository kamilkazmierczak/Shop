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
    private Float _saldo;
    private Integer _kod_znizki;

    public User(Integer _id_uzytkownika, String _imie, String _nazwisko, String _login, String _haslo, Float _saldo, Integer _kod_znizki) {
        this._id_uzytkownika = _id_uzytkownika;
        this._imie = _imie;
        this._nazwisko = _nazwisko;
        this._login = _login;
        this._haslo = _haslo;
        this._saldo = _saldo;
        this._kod_znizki = _kod_znizki;
    }
    
    public User(Integer _id_uzytkownika, String _imie, String _nazwisko, String _login, String _haslo, Float _saldo) {
        this._id_uzytkownika = _id_uzytkownika;
        this._imie = _imie;
        this._nazwisko = _nazwisko;
        this._login = _login;
        this._haslo = _haslo;
        this._saldo = _saldo;
        this._kod_znizki = 0;
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

    public Float getSaldo() {
        return _saldo;
    }

    public void setSaldo(Float _saldo) {
        this._saldo = _saldo;
    }

    public Integer getKod_znizki() {
        return _kod_znizki;
    }

    public void setKod_znizki(Integer _kod_znizki) {
        this._kod_znizki = _kod_znizki;
    }
    
    public Object[] getUser(){
        Object[] user = {this._id_uzytkownika,this._imie,this._nazwisko,this._login,this._haslo,this._saldo,Functions.discountCodeToPercent(this._kod_znizki)};
        return user;
    }
    
}
