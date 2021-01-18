package covid;

public class Statistic {
    private String countryName;
    private String countryCode;
    private String countryContinent;

    public Statistic(String name, String code, String continent){
        this.countryName = name;
        this.countryCode = code;
        this.countryContinent = continent;
    }

    public String getCountryName(){
        return countryName;
    }

    public String getCountryCode(){
        return countryCode;
    }

    public String getCountryContinent(){
        return countryContinent;
    }

}
