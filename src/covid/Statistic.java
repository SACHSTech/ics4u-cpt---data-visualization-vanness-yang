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

    public void setCountryName(String name){
        countryName = name;
    }

    public String getCountryCode(){
        return countryCode;
    }

    public void setCountryCode(String code){
        countryCode = code;
    }

    public String getCountryContinent(){
        return countryContinent;
    }

    public void setCountryContinent(String continent){
        countryContinent = continent;
    }

}
