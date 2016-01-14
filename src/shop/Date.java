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
        //return date.getDay().toString() + "." + date.getMonth().toString() + "." + date.getYear().toString();
        return date.getYear().toString() + "-" + date.getMonth().toString() + "-" + date.getDay().toString();
    }

    public static Date stringToDate(String text)
    {
        //2015-04-01
        Integer day = Integer.parseInt(text.substring(8,10));
        Integer month = Integer.parseInt(text.substring(5,7));
        Integer year = Integer.parseInt(text.substring(0,4));
            
       //System.out.println("->"+text.substring(0,4)+"<-"); 
       // Date test = new Date(2, 12, 2012);
        
        return new Date(day, month, year);
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
