package covid;

import covid.*;

public class Country {

    private String deathData;
    private String gdpData;

    public Country(String dead, String gdp){
        this.deathData = dead;
        this.gdpData = gdp;
    }

    public String getDeathData(){
        return deathData;
    }

    public String getGdpData(){
        return gdpData;
    }

}
