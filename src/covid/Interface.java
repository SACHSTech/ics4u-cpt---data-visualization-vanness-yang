package covid;

import java.io.*;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ChoiceBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.GridPane;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.geometry.Insets;

/**
* Data visualization CPT (Interface)
* @author: V. Yang
* 
*/
public class Interface extends Application {

    // Class variables 
    static Deathdataset deathData;
    static Gdpdataset gdpData;

    /**
    * Main method to test the program and call the methods for gui 
    *
    * @author: V. Yang
    */
    public static void main(String args[]) throws IOException {
        // Creates the death and gdp datasets and objects 
        CountryCreator();

        // Launches the gui 
        launch(args);
    }

    /**
    * A method to create all the gdp and death objects 
    *
    * @author: V. Yang
    */
    public static void CountryCreator() throws IOException {
        // Preparing the csv files for reading 
        BufferedReader deathfile = new BufferedReader(new FileReader("src/covid/newdeaths.csv"));
        BufferedReader gdpfile = new BufferedReader(new FileReader("src/covid/newgdp.csv"));

        // Varaibles 
        String line;
        String countryName;
        String countryCode;
        String countryContinent;
        String deathDate;
        String deathCount;
        String gdpDate;
        String gdpNumber;
        int count;

        // Deathdataset has deathData
        deathData = new Deathdataset();

        // For loop to read the newdeaths.csv file 
        for (count = 0; count < 166; count++) {
            line = deathfile.readLine();
            
            // Splitting up the line and putting each element into an array 
            String[] splitLine = line.split(",");
            countryName = splitLine[0];
            countryCode = splitLine[1];
            deathDate = splitLine[2];
            countryContinent = splitLine[3];
            deathCount = splitLine[4];

            // Creating death objects 
            Deaths countryDeath = new Deaths(countryName, countryCode, countryContinent, deathDate, deathCount);
            deathData.addDeathData(countryDeath);
            
        }

        deathfile.close();

        // Gdpdataset has gdpData
        gdpData = new Gdpdataset();

        // For loop to read the newgdp.csv file 
        for (count = 0; count < 166; count++) {
            line = gdpfile.readLine();

            // Splitting up the line and putting each element into an array 
            String[] splitLine = line.split(",");
            countryName = splitLine[0];
            countryCode = splitLine[1];
            gdpDate = splitLine[2];
            countryContinent = splitLine[3];
            gdpNumber = splitLine[4];

            // Creating gdp objects 
            Gdp countryGdp = new Gdp(countryName, countryCode, countryContinent, gdpDate, gdpNumber);
            gdpData.addGdpData(countryGdp);
            
        }

        gdpfile.close();

    }

    /**
    * A method that creates all the content in the gui 
    *
    * @author: V. Yang
    */
    public Parent createContent() throws IOException{

        // ChoiceBox for continent filters
        ChoiceBox filters = new ChoiceBox();
        filters.getItems().addAll("Continent Filter       ", "Default", "Africa", "Asia", "Australia", "Europe", "North America", "South America");
        filters.getSelectionModel().selectFirst();
        
        // Death Toll Data TableView
        final ObservableList<Deaths> deathTable = FXCollections.observableArrayList(deathData.getDeathObject());

        // Creating columns for the death TableView
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

        // Creating the TableView
        final TableView deathTableView = new TableView();
        deathTableView.setItems(deathTable);
        deathTableView.getColumns().addAll(name, code, continent, date, toll);

        // Listener for ChoiceBox for deathData filters 
        final ChangeListener<String> deathBoxListener =
            (ObservableValue<? extends String> observable,
             String oldValue, String newValue) -> {
                String change = (String) filters.getValue();

                // Updating the TableView
                final ObservableList<Deaths> newDeathTable = deathData.filter(change);
                deathTableView.setItems(newDeathTable);
            };
        
        // Adding the listener to the ChoiceBox
        filters.valueProperty().addListener(deathBoxListener);

        // MergeSort ChoiceBox for deathData
        ChoiceBox deathSortBox = new ChoiceBox();
        deathSortBox.getItems().addAll("Death Data Sort                           ", "Default", "Alphabetical", "Death Toll");
        deathSortBox.getSelectionModel().selectFirst();

        // Listener for ChoiceBox for deathData sorting 
        final ChangeListener<String> deathSortBoxListener =
            (ObservableValue<? extends String> observable,
             String oldValue, String newValue) -> {
                String change = (String) deathSortBox.getValue();
                
                if (!change.equals("Default")) {
                    // Updating the TableView 
                    final ObservableList<Deaths> newDeathTable = deathData.getSortedObservableList(change);
                    deathTableView.setItems(newDeathTable);
                }
            };
        
        // Adding the listener to the ChoiceBox 
        deathSortBox.valueProperty().addListener(deathSortBoxListener);

        // GDP Data TableView
        final ObservableList<Gdp> gdpTable = FXCollections.observableArrayList(gdpData.getGdpObject());
        
        // Creating columns 
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

        // Creating the TableView for gdpData 
        final TableView gdpTableView = new TableView();
        gdpTableView.setItems(gdpTable);
        gdpTableView.getColumns().addAll(name2, code2, continent2, year, number);

        // Listener for choicebox for gdpData filters
        final ChangeListener<String> gdpBoxListener =
            (ObservableValue<? extends String> observable,
             String oldValue, String newValue) -> {
                String change = (String) filters.getValue();

                // Updating the TableView
                final ObservableList<Gdp> newGdpTable = gdpData.filter(change);
                gdpTableView.setItems(newGdpTable);
            };

        // Adding the listener to the ChoiceBox 
        filters.valueProperty().addListener(gdpBoxListener);

        // MergeSort ChoiceBox for gdpData
        ChoiceBox gdpSortBox = new ChoiceBox();
        gdpSortBox.getItems().addAll("GDP Data Sort      ", "Default", "Alphabetical", "GDP");
        gdpSortBox.getSelectionModel().selectFirst();

        // Listener for the ChoiceBox for sorting gdpData 
        final ChangeListener<String> gdpSortBoxListener =
            (ObservableValue<? extends String> observable,
             String oldValue, String newValue) -> {
                String change = (String) gdpSortBox.getValue();
                
                if(!change.equals("Default")){
                    // Updating the TableView
                    final ObservableList<Gdp> newGdpTable = gdpData.getSortedObservableList(change);
                    gdpTableView.setItems(newGdpTable);
                }
            };

        // Adding the listener to the ChoiceBox
        gdpSortBox.valueProperty().addListener(gdpSortBoxListener);

        // TabPane for TableViews
        TabPane tabPane = new TabPane();
        tabPane.setPrefSize(525, 650);
        tabPane.setMinSize(TabPane.USE_PREF_SIZE, TabPane.USE_PREF_SIZE);
        tabPane.setMaxSize(TabPane.USE_PREF_SIZE, TabPane.USE_PREF_SIZE);
        
        // Tab variables 
        Tab deathview = new Tab();
        Tab gdpview = new Tab();
 
        tabPane.setRotateGraphic(false);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
        tabPane.setSide(Side.TOP);
        
        // Creating tabs 
        deathview.setText("Death Toll Table");
        deathview.setContent(deathTableView);
        tabPane.getTabs().add(deathview);

        tabPane.setRotateGraphic(false);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
        tabPane.setSide(Side.TOP);
        
        gdpview.setText("GDP Table");
        gdpview.setContent(gdpTableView);
        tabPane.getTabs().add(gdpview);

        // TextField for searching 
        TextField text = new TextField("");
        text.setPrefWidth(200);

        // Listener for TextField
        final ChangeListener<String> searchListener =
            (ObservableValue<? extends String> observable,
             String oldValue, String newValue) -> {
                String change = (String) text.getText();
                
                // Updating both TableViews
                final ObservableList<Deaths> newDeathTable = deathData.linearSearch(change);
                deathTableView.setItems(newDeathTable);
                final ObservableList<Gdp> newGdpTable = gdpData.linearSearch(change);
                gdpTableView.setItems(newGdpTable);
            };

        // Adding the listener to the TextField 
        text.textProperty().addListener(searchListener);

        // Labels for my death summary points 
        final String insetTextCss = getClass().getResource("LabelDesign.css").toExternalForm();
        final Label deathCount = new Label("Death Data Count: " + deathData.count());
        final Label deathMax = new Label("Max Deaths: " + deathData.max());
        final Label deathMin = new Label("Min Deaths: " + deathData.min());
        final Label deathAverage = new Label("Average Deaths: " + deathData.average());
        final Label deathMedian = new Label("Median Deaths: " + deathData.median());
        final Label deathDeviation = new Label("Standard Deviation of Deaths: " + deathData.deviation());
        
        // Labels for my gdp summary points 
        final Label gdpCount = new Label("GDP Data Count: " + gdpData.count());
        final Label gdpMax = new Label("Max GDP: " + gdpData.max());
        final Label gdpMin = new Label("Min GDP: " + gdpData.min());
        final Label gdpAverage = new Label("Average GDP: " + gdpData.average());
        final Label gdpMedian = new Label("Median GDP: " + gdpData.median());
        final Label gdpDeviation = new Label("Standard Deviation of GDP: " + gdpData.deviation());
        
        // Using css file to create a desing for the death labels 
        deathCount.setId("label1");
        deathCount.getStylesheets().add(insetTextCss);
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
        
        // Using css file to create a desing for the gdp labels 
        gdpCount.setId("label1");
        gdpCount.getStylesheets().add(insetTextCss);
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
        
        // Bar Chart for Country VS Deaths
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategories(deathData.getCountryObservableList());
        NumberAxis yAxis = new NumberAxis("Deaths Per Million", 0.0d, 2000.0d, 250.0d);

        ObservableList<BarChart.Series> deathBarchart = FXCollections.observableArrayList(deathData.getBarObjects());
        BarChart deathChart = new BarChart(xAxis, yAxis, deathBarchart, 10.0d);
        deathChart.setLegendVisible(false);

        // Bar Chart for Country VS GDP 
        CategoryAxis xAxis2 = new CategoryAxis();
        xAxis2.setCategories(gdpData.getCountryObservableList());
        NumberAxis yAxis2 = new NumberAxis("GDP", 0.0d, 120000.0d, 10000.0d);

        ObservableList<BarChart.Series> gdpBarchart = FXCollections.observableArrayList(gdpData.getBarObjects());
        BarChart gdpChart = new BarChart(xAxis2, yAxis2, gdpBarchart, 10.0d);
        gdpChart.setLegendVisible(false);

        // Scatter Plot for GDP VS Deaths 
        ScatterChart scatterChart;
        NumberAxis xAxis3;
        NumberAxis yAxis3;

        xAxis3 = new NumberAxis("GDP", 0.0d, 120000.0d, 10000.0d);
        yAxis3 = new NumberAxis("Deaths Per Million", 0.0d, 2000.0d, 250.0d);

        final Series<Number, Number> series = new Series<>();
        series.setName("Series 1");
        scatterChart = new ScatterChart(xAxis3, yAxis3);
        scatterChart.getData().add(this.getScatterObjects());
        scatterChart.setLegendVisible(false);

        // TabPane for Charts
        TabPane chartTabPane = new TabPane();
        chartTabPane.setPrefSize(700, 500);
        chartTabPane.setMinSize(TabPane.USE_PREF_SIZE, TabPane.USE_PREF_SIZE);
        chartTabPane.setMaxSize(TabPane.USE_PREF_SIZE, TabPane.USE_PREF_SIZE);
        
        // Tab variables
        Tab deathChartTab = new Tab();
        Tab gdpChartTab = new Tab();
        Tab totalChartTab = new Tab();
 
        chartTabPane.setRotateGraphic(false);
        chartTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
        chartTabPane.setSide(Side.TOP);
        
        // Creating tabs and setting its contents
        deathChartTab.setText("Country VS. Deaths");
        deathChartTab.setContent(deathChart);
        gdpChartTab.setText("Country VS. GDP");
        gdpChartTab.setContent(gdpChart);
        totalChartTab.setText("GDP VS. Deaths");
        totalChartTab.setContent(scatterChart);
        
        // Adding the tabs to the TabPane 
        chartTabPane.getTabs().add(deathChartTab);
        chartTabPane.getTabs().add(gdpChartTab);
        chartTabPane.getTabs().add(totalChartTab);
        
        // Label to view individual record
        final Label viewRecord = new Label();

        // Listener for the death TableView 
        deathTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                if (deathTableView.getSelectionModel().getSelectedItem() != null) {    
                    // Setting the text of the Label 
                    Deaths record = (Deaths) deathTableView.getSelectionModel().getSelectedItem();
                    viewRecord.setText("     COUNTRY: " + record.getCountryName() + "     CODE: " + record.getCountryCode() + "     CONTINENT: " + record.getCountryContinent() + "     DATE: " + record.getDeathDate() + "     DEATHS: " + record.getDeathPerMillion());
                }
            }
        });
        
        // Listener for the gdp TableView
        gdpTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (gdpTableView.getSelectionModel().getSelectedItem() != null) { 
                    // Setting the text of the Label    
                    Gdp record = (Gdp) gdpTableView.getSelectionModel().getSelectedItem();
                    viewRecord.setText("     COUNTRY: " + record.getCountryName() + "     CODE: " + record.getCountryCode() + "     CONTINENT: " + record.getCountryContinent() + "     YEAR: " + record.getGdpYear() + "     GDP: " + record.getGdpPerCapita());
                }
            }
        });

        // Creating a GridPane 
        GridPane gridPane = new GridPane();

        gridPane.add(tabPane, 0, 0, 1, 1);
        gridPane.add(filters, 1, 0, 1, 1);
        gridPane.add(deathSortBox, 2, 0, 1, 1);
        gridPane.add(gdpSortBox, 3, 0, 1, 1);
        gridPane.add(text, 4, 0, 1, 1);
        gridPane.add(deathCount, 1, 3, 1, 1);
        gridPane.add(deathMax, 1, 1, 1, 1);
        gridPane.add(deathMin, 2, 1, 1, 1);
        gridPane.add(deathAverage, 3, 1, 1, 1);
        gridPane.add(deathMedian, 4, 1, 1, 1);
        gridPane.add(deathDeviation, 2, 3);
        gridPane.add(gdpCount, 3, 3, 1, 1);
        gridPane.add(gdpMax, 1, 2, 1, 1);
        gridPane.add(gdpMin, 2, 2, 1, 1);
        gridPane.add(gdpAverage, 3, 2, 1, 1);
        gridPane.add(gdpMedian, 4, 2, 1, 1);
        gridPane.add(gdpDeviation, 4, 3);
        gridPane.add(chartTabPane, 1, 5, 5, 1);
        gridPane.add(viewRecord, 1, 6, 6, 1);

        // Setting the gap between each element in the GridPane 
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        // Creating Hbox
        HBox hbox = new HBox(10);
        hbox.setPrefWidth(1250);
        hbox.setPrefHeight(660);
        hbox.getChildren().addAll(tabPane, gridPane);
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.setAlignment(Pos.TOP_LEFT);
        return hbox;
        
    }

    /**
    * Showing the scene and displaying the content for the gui 
    *
    * @author: V. Yang
    */
    @Override public void start(Stage primaryStage) throws IOException {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    /**
    * Creating a series for the ScatterChart (GDP VS Deaths)
    *
    * @return a series for the ScatterChart 
    * @author: V. Yang
    */
    public XYChart.Series getScatterObjects(){

        XYChart.Series<Double, Double> series = new ScatterChart.Series<>();

        for (int i = 0; i < 166; i++) {
            series.getData().add(new XYChart.Data(gdpData.getGdpElement(i), deathData.getDeathElement(i)));
        }

        return series;
    }

}
