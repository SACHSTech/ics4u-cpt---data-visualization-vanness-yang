package covid;

import covid.*;

public class Gdp extends Statistic{

    private String gdpYear;
    private String gdpPerCapita;

    public Gdp(String name, String code, String continent, String year, String perCapita){
        super(name, code, continent);
        this.gdpYear = year;
        this.gdpPerCapita = perCapita;
    }

    public String getGdpYear(){
        return gdpYear;
    }

    public void setGdpYear(String year){
        gdpYear = year;
    }

    public String getGdpPerCapita(){
        return gdpPerCapita;
    }

    public void setGdpPerCapita(String perCapita){
        gdpPerCapita = perCapita;
    }

    public String toString(){
        return this.getCountryName() + "," + this.getCountryCode() + "," + this.getCountryContinent() + "," + gdpYear + "," + gdpPerCapita;
    }
}