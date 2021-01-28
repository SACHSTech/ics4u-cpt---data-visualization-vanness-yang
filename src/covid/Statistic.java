package covid;

/**
* Data visualization CPT (Statistic superclass)
* @author: V. Yang
* 
*/
public class Statistic {

    // Instance variables 
    private String countryName;
    private String countryCode;
    private String countryContinent;

    /**
    * Constructor - creates a new statistic instance
    *
    * @param name - country name 
    * @param code - country code
    * @param continent - country continent 
    * @author: V. Yang
    */
    public Statistic(String name, String code, String continent){
        this.countryName = name;
        this.countryCode = code;
        this.countryContinent = continent;
    }

    /**
    * Getter for the country name 
    *
    * @return the country name 
    * @author: V. Yang
    */
    public String getCountryName(){
        return countryName;
    }

    /**
    * Getter for the country code
    *
    * @return the country code
    * @author: V. Yang
    */
    public String getCountryCode(){
        return countryCode;
    }

    /**
    * Getter for the country tontinent
    *
    * @return the country continent 
    * @author: V. Yang
    */
    public String getCountryContinent(){
        return countryContinent;
    }

}
