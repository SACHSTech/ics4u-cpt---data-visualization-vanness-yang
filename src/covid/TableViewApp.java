package covid;

import java.io.*;
import java.util.*;
import covid.*;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class TableViewApp extends Application{

    static Deathdataset deathData;
    static Gdpdataset gdpData;

    public static void main(String args[]) throws IOException{
        CountryCreator();
        launch(args);
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

    public Parent createContent() {
        //Controls to be added to the HBox
        //Label label = new Label("Test:");
        //TextField tb = new TextField();
        //Button button = new Button("Button");

        final ObservableList<Deaths> deathTable = FXCollections.observableArrayList(deathData.getDeathObject());

        TableColumn name = new TableColumn();
        name.setText("Country");
        name.setCellValueFactory(new PropertyValueFactory("countryName"));

        TableColumn code = new TableColumn();
        code.setText("Country Code");
        code.setCellValueFactory(new PropertyValueFactory("countryCode"));

        TableColumn continent = new TableColumn();
        continent.setText("Continent");
        continent.setCellValueFactory(new PropertyValueFactory("countryContinent"));

        TableColumn date = new TableColumn();
        date.setText("Death Date");
        date.setCellValueFactory(new PropertyValueFactory("deathDate"));

        TableColumn toll = new TableColumn();
        toll.setText("Deaths Per Million");
        toll.setCellValueFactory(new PropertyValueFactory("deathPerMillion"));

        final TableView deathTableView = new TableView();
        deathTableView.setItems(deathTable);
        deathTableView.getColumns().addAll(name, code, continent, date, toll);

        final ObservableList<Gdp> gdpTable = FXCollections.observableArrayList(gdpData.getGdpObject());
        
        TableColumn name2 = new TableColumn();
        name2.setText("Country");
        name2.setCellValueFactory(new PropertyValueFactory("countryName"));

        TableColumn code2 = new TableColumn();
        code2.setText("Country Code");
        code2.setCellValueFactory(new PropertyValueFactory("countryCode"));

        TableColumn continent2 = new TableColumn();
        continent2.setText("Continent");
        continent2.setCellValueFactory(new PropertyValueFactory("countryContinent"));

        TableColumn year = new TableColumn();
        year.setText("Year");
        year.setCellValueFactory(new PropertyValueFactory("gdpYear"));

        TableColumn number = new TableColumn();
        number.setText("GDP");
        number.setCellValueFactory(new PropertyValueFactory("gdpPerCapita"));

        final TableView gdpTableView = new TableView();
        gdpTableView.setItems(gdpTable);
        gdpTableView.getColumns().addAll(name2, code2, continent2, year, number);
 
        //HBox with spacing = 5
        HBox hbox = new HBox(250);
        hbox.setPrefWidth(1200);
        hbox.setPrefHeight(500);
        hbox.getChildren().addAll(deathTableView, gdpTableView);
        //hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

    @Override public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    
    
}
