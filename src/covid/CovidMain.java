package covid;

import java.io.*;
import covid.*;
import java.util.*;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CovidMain {
    
    static Deathdataset deathData;
    static Gdpdataset gdpData;
    public static void main(String[] args) throws IOException{
        CountryCreator();
        //System.out.println(deathData.filter("Asia"));
        //deathData.mergeSort("deaths");
        //deathData.printResults();
        //gdpData.mergeSort("gdp");
        //gdpData.printResults();

        
        //System.out.println(gdpData.filter("Australia"));


    }

    public static void CountryCreator() throws IOException{
        BufferedReader deathfile = new BufferedReader(new FileReader("src/covid/newdeaths.csv"));
        BufferedReader gdpfile = new BufferedReader(new FileReader("src/covid/newgdp.csv"));

        String line;
        String countryName;
        String countryCode;
        String countryContinent;
        String deathDate;
        String deathCount;
        String gdpDate;
        String gdpNumber;
        int count;

        deathData = new Deathdataset();

        for(count = 0; count < 166; count++){
            line = deathfile.readLine();
            String[] splitLine = line.split(",");
            countryName = splitLine[0];
            countryCode = splitLine[1];
            deathDate = splitLine[2];
            countryContinent = splitLine[3];
            deathCount = splitLine[4];

            Deaths countryDeath = new Deaths(countryName, countryCode, countryContinent, deathDate, deathCount);
            deathData.addDeathData(countryDeath);
            
        }

        deathfile.close();

        gdpData = new Gdpdataset();

        for(count = 0; count < 166; count++){
            line = gdpfile.readLine();
            String[] splitLine = line.split(",");
            countryName = splitLine[0];
            countryCode = splitLine[1];
            gdpDate = splitLine[2];
            countryContinent = splitLine[3];
            gdpNumber = splitLine[4];

            Gdp countryGdp = new Gdp(countryName, countryCode, countryContinent, gdpDate, gdpNumber);
            gdpData.addGdpData(countryGdp);
            
        }

        gdpfile.close();

    }

    public void start(Stage stage){

        final ObservableList<Deaths> deathTable = FXCollections.observableArrayList();

        TableColumn name = new TableColumn();
        name.setText("Country");
        name.setCellValueFactory(new PropertyValueFactory("Country"));

        TableColumn code = new TableColumn();
        code.setText("Country Code");
        code.setCellValueFactory(new PropertyValueFactory("Country Code"));

        TableColumn continent = new TableColumn();
        continent.setText("Continent");
        continent.setCellValueFactory(new PropertyValueFactory("Continent"));

        TableColumn date = new TableColumn();
        date.setText("Death Date");
        date.setCellValueFactory(new PropertyValueFactory("Death Date"));

        TableColumn toll = new TableColumn();
        toll.setText("Deaths Per Million");
        toll.setCellValueFactory(new PropertyValueFactory("Deaths Per Million"));

    }
    
}
