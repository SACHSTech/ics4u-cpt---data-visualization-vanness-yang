package covid;

import covid.*;

public class Deaths extends Statistic{
    
    private String deathDate;
    private String deathPerMillion;

    public Deaths(String name, String code, String continent, String date, String dead){
        super(name, code, continent);
        this.deathDate = date;
        this.deathPerMillion = dead;
    }

    public String getDeathDate(){
        return deathDate;
    }

    public void setDeathDate(String date){
        deathDate = date;
    }

    public String getDeathPerMillion(){
        return deathPerMillion;
    }

    public void setDeathPerMillion(String dead){
        deathPerMillion = dead;
    }

    public String toString(){
        return this.getCountryName() + "," + this.getCountryCode() + "," + this.getCountryContinent() + "," + deathDate + "," + deathPerMillion;
    }
}
