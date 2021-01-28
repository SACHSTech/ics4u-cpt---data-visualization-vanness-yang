package covid;

import covid.*;

/**
* Data visualization CPT (Gdp subclass)
* @author: V. Yang
* 
*/
public class Gdp extends Statistic{

    // Instance variables 
    private String gdpYear;
    private double gdpPerCapita;

    /**
    * Constructor - creates a new gdp instance
    *
    * @param name - country name 
    * @param code - country code
    * @param continent - country continent 
    * @param year - the year of the gdp 
    * @param perCapita - the gdp 
    * @author: V. Yang
    */
    public Gdp(String name, String code, String continent, String year, String perCapita) {
        super(name, code, continent);
        this.gdpYear = year;
        this.gdpPerCapita = Double.parseDouble(perCapita);
    }

    /**
    * Getter for gdp year  
    * 
    * @return the gdp year
    * @author: V. Yang
    */
    public String getGdpYear() {
        return gdpYear;
    }

    /**
    * Getter for gdp   
    * 
    * @return the gdp
    * @author: V. Yang
    */
    public double getGdpPerCapita() {
        return gdpPerCapita;
    }

    /**
    * toString to return the values of the variables 
    * 
    * @return a gdp datapoint 
    * @author: V. Yang
    */
    public String toString() {
        return this.getCountryName() + "," + this.getCountryCode() + "," + this.getCountryContinent() + "," + gdpYear + "," + gdpPerCapita;
    }
}