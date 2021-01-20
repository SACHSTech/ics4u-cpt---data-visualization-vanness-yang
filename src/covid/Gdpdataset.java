package covid;

import covid.*;
import java.util.*;

public class Gdpdataset {

    private ArrayList<Gdp> gdpData;

    public Gdpdataset(){
        gdpData = new ArrayList<Gdp>();
    }

    public void addGdpData(Gdp newGdp){
        gdpData.add(newGdp);
    }

}
