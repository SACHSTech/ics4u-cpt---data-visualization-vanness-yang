package covid;

import covid.*;

/**
* Data visualization CPT (Death subclass)
* @author: V. Yang
* 
*/
public class Deaths extends Statistic {
    
    // Instance variables 
    private String deathDate;
    private double deathPerMillion;

    /**
    * Constructor - creates a new death instance
    *
    * @param name - country name 
    * @param code - country code
    * @param continent - country continent 
    * @param date - the date of the death count  
    * @param dead - the death toll
    * @author: V. Yang
    */
    public Deaths(String name, String code, String continent, String date, String dead) {
        super(name, code, continent);
        this.deathDate = date;
        this.deathPerMillion = Double.parseDouble(dead);
    }

    /**
    * Getter for death date   
    * 
    * @return the death date 
    * @author: V. Yang
    */
    public String getDeathDate() {
        return deathDate;
    }

    /**
    * Getter for death toll
    * 
    * @return the death toll 
    * @author: V. Yang
    */
    public double getDeathPerMillion() {
        return deathPerMillion;
    }

    /**
    * toString to return the values of the variables 
    * 
    * @return a death datapoint 
    * @author: V. Yang
    */
    public String toString() {
        return this.getCountryName() + "," + this.getCountryCode() + "," + this.getCountryContinent() + "," + deathDate + "," + deathPerMillion;
    }
}
