package covid;

import java.util.*;
import java.io.*;

public class cvsreader {
    public static void main(String[] args) throws IOException{
        //firstFilter();
        //secondFilter();
    }

    // Filters only most recent death toll and most recent GDP
    public static void firstFilter() throws IOException{
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
    
    // Writes only the country name 
    public static void secondFilter() throws IOException{
        BufferedReader dead = new BufferedReader(new FileReader("src/covid/newdeaths.csv"));
        BufferedReader domestic = new BufferedReader(new FileReader("src/covid/newgdp.csv"));
        PrintWriter filtered1 = new PrintWriter(new FileWriter("src/covid/filtered1.csv"));
        PrintWriter filtered2 = new PrintWriter(new FileWriter("src/covid/filtered2.csv"));

        String line1;
        String line2;

        line1 = dead.readLine();
        line2 = domestic.readLine();

        while(line1 != null){
            ArrayList<String> deadarr = new ArrayList<>(Arrays.asList(line1.split(",")));

            deadarr.add(line1);
            
            filtered1.println(deadarr.get(0));           

            line1 = dead.readLine();
        }

        while(line2 != null){
            ArrayList<String> domesticarr = new ArrayList<>(Arrays.asList(line2.split(",")));

            domesticarr.add(line2);
            
            filtered2.println(domesticarr.get(0));            

            line2 = domestic.readLine();
        }

        dead.close();
        domestic.close();
        filtered1.close();
        filtered2.close();

    }
        
}
