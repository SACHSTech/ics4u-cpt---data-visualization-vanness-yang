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
        filters.getItems().addAll("Continent Filter", "Default", "Africa", "Asia", "Australia", "Europe", "North America", "South America");
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
                final ObservableList<Deaths> newDeathTable = deathData.filter(change);
                deathTableView.setItems(newDeathTable);
            };
        filters.valueProperty().addListener(deathBoxListener);

        // MergeSort ChoiceBox for deathData
        ChoiceBox deathSortBox = new ChoiceBox();
        deathSortBox.getItems().addAll("Death Data Sort", "Default", "Alphabetical", "Death Toll");
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
                final ObservableList<Gdp> newGdpTable = gdpData.filter(change);
                gdpTableView.setItems(newGdpTable);
            };
        filters.valueProperty().addListener(gdpBoxListener);

        // MergeSort ChoiceBox for gdpData
        ChoiceBox gdpSortBox = new ChoiceBox();
        gdpSortBox.getItems().addAll("GDP Data Sort", "Default", "Alphabetical", "GDP");
        gdpSortBox.getSelectionModel().selectFirst();
        final ChangeListener<String> gdpSortBoxListener =
            (ObservableValue<? extends String> observable,
             String oldValue, String newValue) -> {
                String change = (String) gdpSortBox.getValue();
                if(!change.equals("Default")){
                    final ObservableList<Gdp> newGdpTable = gdpData.getSortedObservableList(change);
                    gdpTableView.setItems(newGdpTable);
                }
            };
        gdpSortBox.valueProperty().addListener(gdpSortBoxListener);

        // TabPane for TableViews
        TabPane tabPane = new TabPane();
        tabPane.setPrefSize(525, 650);
        tabPane.setMinSize(TabPane.USE_PREF_SIZE, TabPane.USE_PREF_SIZE);
        tabPane.setMaxSize(TabPane.USE_PREF_SIZE, TabPane.USE_PREF_SIZE);
        
        Tab deathview = new Tab();
        Tab gdpview = new Tab();
 
        tabPane.setRotateGraphic(false);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
        tabPane.setSide(Side.TOP);
        
        deathview.setText("Death Toll Table");
        deathview.setContent(deathTableView);
        tabPane.getTabs().add(deathview);

        tabPane.setRotateGraphic(false);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
        tabPane.setSide(Side.TOP);
        
        gdpview.setText("GDP Table");
        gdpview.setContent(gdpTableView);
        tabPane.getTabs().add(gdpview);

        // Textfield for search box 
        TextField text = new TextField("");
        text.setMaxSize(140, TextField.USE_COMPUTED_SIZE);
        final ChangeListener<String> searchListener =
            (ObservableValue<? extends String> observable,
             String oldValue, String newValue) -> {
                String change = (String) text.getText();
                
                final ObservableList<Deaths> newDeathTable = deathData.linearSearch(change);
                deathTableView.setItems(newDeathTable);
                final ObservableList<Gdp> newGdpTable = gdpData.linearSearch(change);
                gdpTableView.setItems(newGdpTable);
            };
        text.textProperty().addListener(searchListener);

        // Labels for my Summary Points
        final String insetTextCss = getClass().getResource("LabelDesign.css").toExternalForm();
        final Label count = new Label("Count: " + deathData.count());
        final Label deathMax = new Label("Max Deaths: " + deathData.max());
        final Label deathMin = new Label("Min Deaths: " + deathData.min());
        final Label deathAverage = new Label("Average Deaths: " + deathData.min());
        final Label deathMedian = new Label("Median Deaths: " + deathData.min());
        final Label deathDeviation = new Label("Standard Deviation of Deaths: " + deathData.deviation());

        final Label gdpMax = new Label("Max GDP: " + gdpData.max());
        final Label gdpMin = new Label("Min GDP: " + gdpData.min());
        final Label gdpAverage = new Label("Average GDP: " + gdpData.min());
        final Label gdpMedian = new Label("Median GDP: " + gdpData.min());
        final Label gdpDeviation = new Label("Standard Deviation of GDP: " + gdpData.deviation());
        
        count.setId("label1");
        count.getStylesheets().add(insetTextCss);
        deathMax.setId("label1");
        deathMax.getStylesheets().add(insetTextCss);
        deathMin.setId("label1");
        deathMin.getStylesheets().add(insetTextCss);
        deathAverage.setId("label1");
        deathAverage.getStylesheets().add(insetTextCss);
        deathMedian.setId("label1");
        deathMedian.getStylesheets().add(insetTextCss);
        deathDeviation.setId("label1");
        deathDeviation.getStylesheets().add(insetTextCss);

        gdpMax.setId("label1");
        gdpMax.getStylesheets().add(insetTextCss);
        gdpMin.setId("label1");
        gdpMin.getStylesheets().add(insetTextCss);
        gdpAverage.setId("label1");
        gdpAverage.getStylesheets().add(insetTextCss);
        gdpMedian.setId("label1");
        gdpMedian.getStylesheets().add(insetTextCss);
        gdpDeviation.setId("label1");
        gdpDeviation.getStylesheets().add(insetTextCss);

        
        // Creating Hbox
        HBox hbox = new HBox(10);
        hbox.setPrefWidth(1250);
        hbox.setPrefHeight(660);
        hbox.getChildren().addAll(tabPane, filters, deathSortBox, gdpSortBox, text, deathMax, deathMin);
        hbox.setAlignment(Pos.TOP_LEFT);
        return hbox;
    }

    @Override public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

}
