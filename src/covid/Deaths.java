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

    public String getDate(){
        return deathDate;
    }

    public String getDeathPerMillion(){
        return deathPerMillion;
    }
}
