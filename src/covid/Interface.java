package covid;

import java.io.*;
import java.util.*;
import covid.*;
import javafx.*;

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
import javafx.scene.layout.Priority;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.application.Application; 
import javafx.scene.Scene; 
import javafx.scene.control.*; 
import javafx.scene.layout.*; 
import javafx.event.ActionEvent; 
import javafx.event.EventHandler; 
import javafx.collections.*; 
import javafx.beans.value.*; 
import javafx.stage.Stage; 

import javafx.scene.control.ChoiceBox;
 
import java.io.InputStream;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class Interface extends Application{

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

    public Parent createContent() throws IOException{

        // ChoiceBox for Filters
        ChoiceBox filters = new ChoiceBox();
        filters.getItems().addAll("Default", "Africa", "Asia", "Australia", "Europe", "North America", "South America");
        filters.getSelectionModel().selectFirst();
        
        // Death Toll Data TableView
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

        // Listener for choicebox for deathData
        final ChangeListener<String> deathBoxListener =
            (ObservableValue<? extends String> observable,
             String oldValue, String newValue) -> {
                String change = (String) filters.getValue();
                final ObservableList<Deaths> newDeathTable = FXCollections.observableArrayList(deathData.filter(change));
                deathTableView.setItems(newDeathTable);
            };
        filters.valueProperty().addListener(deathBoxListener);

        // MergeSort ChoiceBox for deathData
        ChoiceBox deathSortBox = new ChoiceBox();
        deathSortBox.getItems().addAll("Default", "Alphabetical", "Death Toll");
        deathSortBox.getSelectionModel().selectFirst();
        final ChangeListener<String> deathSortBoxListener =
            (ObservableValue<? extends String> observable,
             String oldValue, String newValue) -> {
                String change = (String) deathSortBox.getValue();
                if(!change.equals("Default")){
                    final ObservableList<Deaths> newDeathTable = deathData.getSortedObservableList(change);
                    deathTableView.setItems(newDeathTable);
                }
            };
        deathSortBox.valueProperty().addListener(deathSortBoxListener);

        // GDP Data TableView
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

        // Listener for choicebox for gdpData
        final ChangeListener<String> gdpBoxListener =
            (ObservableValue<? extends String> observable,
             String oldValue, String newValue) -> {
                String change = (String) filters.getValue();
                final ObservableList<Gdp> newGdpTable = FXCollections.observableArrayList(gdpData.filter(change));
                gdpTableView.setItems(newGdpTable);
            };
        filters.valueProperty().addListener(gdpBoxListener);

        

        // TabPane for TableViews
        TabPane tabPane = new TabPane();
        tabPane.setPrefSize(550, 650);
        tabPane.setMinSize(TabPane.USE_PREF_SIZE, TabPane.USE_PREF_SIZE);
        tabPane.setMaxSize(TabPane.USE_PREF_SIZE, TabPane.USE_PREF_SIZE);
        
        Tab deathview = new Tab();
        Tab gdpview = new Tab();
 
        tabPane.setRotateGraphic(false);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
        tabPane.setSide(Side.TOP);
        
        deathview.setText("Death Toll Chart");
        deathview.setContent(deathTableView);
        tabPane.getTabs().add(deathview);

        tabPane.setRotateGraphic(false);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
        tabPane.setSide(Side.TOP);
        
        gdpview.setText("GDP Chart");
        gdpview.setContent(gdpTableView);
        tabPane.getTabs().add(gdpview);

        // Creating Hbox
        HBox hbox = new HBox(10);
        hbox.setPrefWidth(1250);
        hbox.setPrefHeight(660);
        hbox.getChildren().addAll(tabPane, filters, deathSortBox, gdpSortBox);
        hbox.setAlignment(Pos.TOP_LEFT);
        return hbox;
    }

    @Override public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    
    
}