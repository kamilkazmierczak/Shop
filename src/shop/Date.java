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
public class Date {
    
    private Integer Day;
    private Integer Month;
    private Integer Year;
    
    
    public static String dateToString(Date date)
    {   
        return date.getDay().toString() + "." + date.getMonth().toString() + "." + date.getYear().toString();
    }

    public Date(Integer Day, Integer Month, Integer Year) {
        this.Day = Day;
        this.Month = Month;
        this.Year = Year;
    }

    public Integer getDay() {
        return Day;
    }

    public void setDay(Integer Day) {
        this.Day = Day;
    }

    public Integer getMonth() {
        return Month;
    }

    public void setMonth(Integer Month) {
        this.Month = Month;
    }

    public Integer getYear() {
        return Year;
    }

    public void setYear(Integer Year) {
        this.Year = Year;
    }
    
    
    
    
}
