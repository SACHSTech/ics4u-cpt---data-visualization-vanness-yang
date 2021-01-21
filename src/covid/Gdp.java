package covid;

import covid.*;

public class Gdp extends Statistic{

    private String gdpYear;
    private double gdpPerCapita;

    public Gdp(String name, String code, String continent, String year, String perCapita){
        super(name, code, continent);
        this.gdpYear = year;
        this.gdpPerCapita = Double.parseDouble(perCapita);
    }

    public String getGdpYear(){
        return gdpYear;
    }

    public double getGdpPerCapita(){
        return gdpPerCapita;
    }

    public String toString(){
        return this.getCountryName() + "," + this.getCountryCode() + "," + this.getCountryContinent() + "," + gdpYear + "," + gdpPerCapita;
    }
}