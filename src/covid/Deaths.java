package covid;

import covid.*;

public class Deaths extends Statistic{
    
    private String deathDate;
    private double deathPerMillion;

    public Deaths(String name, String code, String continent, String date, String dead){
        super(name, code, continent);
        this.deathDate = date;
        this.deathPerMillion = Double.parseDouble(dead);
    }

    public String getDeathDate(){
        return deathDate;
    }

    public double getDeathPerMillion(){
        return deathPerMillion;
    }

    public String toString(){
        return this.getCountryName() + "," + this.getCountryCode() + "," + this.getCountryContinent() + "," + deathDate + "," + deathPerMillion;
    }
}
