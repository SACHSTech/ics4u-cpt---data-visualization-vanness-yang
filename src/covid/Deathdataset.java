package covid;

import covid.*;
import java.util.*;

public class Deathdataset {

    private ArrayList<Deaths> deathData;

    public Deathdataset(){
        deathData = new ArrayList<Deaths>();
    }

    public void addDeathData(Deaths newDeaths){
        deathData.add(newDeaths);
    }

    

}
