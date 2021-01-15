package covid;

import java.util.*;
import java.io.*;

public class cvsreader {
    public static void main(String[] args) throws IOException{
        BufferedReader deaths = new BufferedReader(new FileReader("src/covid/deaths.csv"));
        PrintWriter newdeaths = new PrintWriter(new FileWriter("src/covid/newdeaths.csv"));
        PrintWriter newgdp = new PrintWriter(new FileWriter("src/covid/newgdp.csv"));
        
        String line;

        line = deaths.readLine();

        while(line != null){   
            ArrayList<String> data = new ArrayList<>(Arrays.asList(line.split(",")));
            data.add(line);
            System.out.println(data.get(5));
            if(data.get(2).equalsIgnoreCase("2017")){
                newgdp.println(data.get(0) + "," + data.get(1) + "," + data.get(2) + "," +  data.get(3) + "," + data.get(4));
            }

            if(data.get(5).equalsIgnoreCase("2021-01-13")){
                newdeaths.println(data.get(0) + "," + data.get(1) + "," + data.get(5) + "," +  data.get(6));
            }
            line = deaths.readLine();

        }

        newgdp.close();

        newdeaths.close();
        
        deaths.close();

    }
    
}
