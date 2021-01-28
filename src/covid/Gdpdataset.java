package covid;

import covid.*;
import java.util.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.chart.BarChart;

/**
* Data visualization CPT (Gdpdataset class)
* @author: V. Yang
* 
*/
public class Gdpdataset {

    // Attributes 
    private ArrayList<Gdp> gdpData;

    /**
    * Constructor - creates a new Gdpdataset instance 
    *
    * @author: V. Yang
    */
    public Gdpdataset() {
        gdpData = new ArrayList<Gdp>();
    }

    /**
    * Adding a new gdp instance 
    *
    * @param newGdp - a new gdp instance  
    * @author: V. Yang
    */
    public void addGdpData(Gdp newGdp) {
        gdpData.add(newGdp);
    }

    /**
    * Printing the results to the screen for testing purposes
    *
    * @author: V. Yang
    */
    public void printResults() {
        for (int i = 0; i < gdpData.size(); i++) {
            System.out.println(gdpData.get(i).toString());
        }
    }

    /**
    * Getting a gdp instance
    *
    * @return a gdp instance 
    * @author: V. Yang
    */
    public ArrayList<Gdp> getGdpObject() {
        return gdpData;
    }

    /**
    * Returning a the gdp 
    *
    * @return the gdp 
    * @author: V. Yang
    */
    public double getGdpElement(int i) {
        return gdpData.get(i).getGdpPerCapita();
    }

   /**
    * mergeSort method callin the divide method 
    *
    * @param type - how to sort the data 
    * @author: V. Yang
    */
    public void mergeSort(String type) {       
        divide(0, gdpData.size() - 1, type);
    }

    /**
    * Dividing the data for mergeSort 
    *
    * @param startIndex - the starting index
    * @param endIndex - the ending index 
    * @param type - how to sort the data 
    * @author: V. Yang
    */
    public void divide(int startIndex, int endIndex, String type) {
        
        //Divide till you breakdown your list to single element
        if (startIndex < endIndex && (endIndex - startIndex) >= 1) {
            int mid = (endIndex + startIndex) / 2;
            divide(startIndex, mid, type);
            divide(mid + 1, endIndex, type);        
            
            //merging Sorted array produce above into one sorted array
            merger(startIndex, mid, endIndex, type);            
        }       
    }   

    /**
    * Merging the divided data 
    *
    * @param startIndex - the starting index 
    * @param midIndex - the middle index
    * @param endIndex - the end index 
    * @param type - how to sort the data  
    * @author: V. Yang
    */
    public void merger(int startIndex, int midIndex, int endIndex, String type) {
        
        //Below is the mergedarray that will be sorted array Array[i-midIndex] , Array[(midIndex+1)-endIndex]
        ArrayList<Gdp> mergedSortedArray = new ArrayList<Gdp>();
        
        int leftIndex = startIndex;
        int rightIndex = midIndex + 1;
        int comparison; 
        int i = 0;
        int j = startIndex;
        
        while (leftIndex <= midIndex && rightIndex <= endIndex) {
            // Sort by increasing GDP 
            if (type.equalsIgnoreCase("GDP")) {
                if((gdpData.get(leftIndex).getGdpPerCapita()) <= (gdpData.get(rightIndex).getGdpPerCapita())){
                    mergedSortedArray.add(gdpData.get(leftIndex));
                    leftIndex++;
                } else {
                    mergedSortedArray.add(gdpData.get(rightIndex));
                    rightIndex++;
                }
            // Sort in alphabetical order 
            } else if(type.equalsIgnoreCase("Alphabetical")) {
                comparison = gdpData.get(leftIndex).getCountryName().compareTo(gdpData.get(rightIndex).getCountryName());
                if (comparison < 0) {
                    mergedSortedArray.add(gdpData.get(leftIndex));
                    leftIndex++;
                } else if (comparison > 0) {
                    mergedSortedArray.add(gdpData.get(rightIndex));
                    rightIndex++;
                }
            }
            
        }       
        
        while (leftIndex <= midIndex) {
            mergedSortedArray.add(gdpData.get(leftIndex));
            leftIndex++;
        }
        
        while (rightIndex <= endIndex) {
            mergedSortedArray.add(gdpData.get(rightIndex));
            rightIndex++;
        }
        
        //Setting sorted array to original one
        while (i < mergedSortedArray.size()) {
            gdpData.set(j, mergedSortedArray.get(i++));
            j++;
        }
    }

    /**
    * linearSearch method 
    *
    * @param key - whatever is searched for 
    * @return an ObservableList of the searched items 
    * @author: V. Yang
    */
    public ObservableList<Gdp> linearSearch(String key) {
        
        String element1;
        String element2;
        String element3;
        String element4;
        String element5;
        int i;

        ArrayList<Gdp> searchResults = new ArrayList<Gdp>();

        // Dataset is the same if nothing is typed into the TextField 
        if (key.equals("")) {
            for (i = 0; i < gdpData.size(); i++) {
                searchResults.add(gdpData.get(i));
            }
        // Searching for whatever is typed in the TextField 
        } else {
            for (i = 0; i < gdpData.size(); i++) {
                element1 = gdpData.get(i).getCountryName();
                element2 = gdpData.get(i).getCountryCode();  
                element3 = gdpData.get(i).getCountryContinent();
                element4 = gdpData.get(i).getGdpYear();
                element5 = String.valueOf(gdpData.get(i).getGdpPerCapita());
                
                // Adding the searched into the ArrayList
                if (element1.equalsIgnoreCase(key)) {
                    searchResults.add(gdpData.get(i));       
                } else if (element2.equalsIgnoreCase(key)) {
                    searchResults.add(gdpData.get(i));
                } else if (element3.equalsIgnoreCase(key)) {
                    searchResults.add(gdpData.get(i));
                } else if (element4.equalsIgnoreCase(key)) {
                    searchResults.add(gdpData.get(i));
                } else if (element5.equalsIgnoreCase(key)) { 
                    searchResults.add(gdpData.get(i));
                }
            }
        }
       
       return FXCollections.observableList(searchResults);
       
    }

    /**
    * Getting an ObservableList of the sorted data 
    *
    * @param sort - how to sort the data 
    * @return an ObservableList of the sorted data 
    * @author: V. Yang
    */
    public ObservableList<Gdp> getSortedObservableList(String sort) {
        this.mergeSort(sort);

        return FXCollections.observableList(gdpData);

    }

    /**
    * Filtering the data by continent 
    *
    * @param filter - the continent to filter the data 
    * @return an ObservableList of the filtered data 
    * @author: V. Yang
    */
    public ObservableList<Gdp> filter(String filter) {
        
        String element;
        int i;

        ArrayList<Gdp> filtered = new ArrayList<Gdp>();

        // Default filter it the normal filter (nothing changes)
        if (filter.equals("Default")) {
            for (i = 0; i < gdpData.size(); i++) {
                filtered.add(gdpData.get(i));
            }
        // Filtering by continent 
        } else {
            for (i = 0; i < gdpData.size(); i++) {
                element = gdpData.get(i).getCountryContinent();
     
                if (element.equalsIgnoreCase(filter)) {
                    filtered.add(gdpData.get(i));       
                }
            }
        }
        
       return FXCollections.observableList(filtered);
       
    }

    /**
    * Finding the max gdp  
    *
    * @return the maximum gdp
    * @author: V. Yang
    */
    public double max() {

        double max = 0;
        int i;
        
        for (i = 0; i < gdpData.size(); i++) {
            // Finding the Max
            if (gdpData.get(i).getGdpPerCapita() >= max) {
                max = Math.round(gdpData.get(i).getGdpPerCapita() * 100.0) / 100.0;
            }
        }

        return max;

    }

    /**
    * Finding the min gdp 
    *
    * @return the min gdp 
    * @author: V. Yang
    */
    public double min() {
        
        int i;
        double min = 200000;

        for (i = 0; i < gdpData.size(); i++) {
            // Finding the min 
            if (gdpData.get(i).getGdpPerCapita() <= min) {
                min = Math.round(gdpData.get(i).getGdpPerCapita() * 100.0) / 100.0;
            }
        }

        return min;
    }

    /**
    * Finding the average gdp 
    *
    * @return the average gdp 
    * @author: V. Yang
    */
    public double average() {
        int i; 
        double total = 0;
        double average;

        for (i = 0; i < gdpData.size(); i++) {
            // Finding the total 
            total = total + gdpData.get(i).getGdpPerCapita();
        }

        // Finding the average
        average = Math.round((total / gdpData.size()) * 100.0) / 100.0;

        return average;

    }

    /**
    * Finding the median of the gdp data  
    *
    * @return the median of the gdp data 
    * @author: V. Yang
    */
    public double median() {

        return Math.round(gdpData.get(gdpData.size() / 2).getGdpPerCapita() * 100.0) / 100.0;
        
    }

    /**
    * Finding the standard deviation 
    *
    * @return the standard deviation of the gdp data 
    * @author: V. Yang
    */
    public double deviation() {
        double mean;
        double squared;
        double sum = 0;
        double meanSum;
        double deviation;
        int i;

        // Finding the average 
        mean = this.average();
        
        for (i = 0; i < gdpData.size(); i++) {
            // Finding the difference between the data point and the average and squaring it 
            squared = Math.pow((gdpData.get(i).getGdpPerCapita() - mean), 2);
            
            // Finding the sum of the squared differences 
            sum = sum + squared;
        }

        // Finding the average of the sum
        meanSum = sum / gdpData.size();

        // Square rooting the average of the sum
        deviation = Math.round(Math.sqrt(meanSum) * 100.0) / 100.0;

        return deviation;

    }

    /**
    * Finding the count of the gdp data  
    *
    * @return the count of the gdp data 
    * @author: V. Yang
    */
    public double count() {

        return gdpData.size();
    }

    /**
    * Getting an ObservableList of all the countries
    *
    * @return an ObservableList of all the countries
    * @author: V. Yang
    */
    public ObservableList<String> getCountryObservableList(){

        ArrayList<String> temp = new ArrayList<String>();

        for (int i = 0; i < gdpData.size(); i++) {
            temp.add(gdpData.get(i).getCountryName());
        }

        return FXCollections.observableList(temp);
    }

    /**
    * Getting a series for the BarChart 
    *
    * @return a series of the BarChart data (Country VS GDP)
    * @author: V. Yang
    */
    public BarChart.Series getBarObjects() {

        BarChart.Series<String, Integer> series = new BarChart.Series<>();

        // Creating a series for the BarChart
        for (int i = 0; i < gdpData.size(); i++) {
            series.getData().add(new BarChart.Data(gdpData.get(i).getCountryName(), gdpData.get(i).getGdpPerCapita()));
        }

        return series;
    }

    /**
    * Getting an ObservableList of all the gdps
    *
    * @return an ObservableList of all the gdps
    * @author: V. Yang
    */
    public ObservableList<Double> getGdpObservableList() {

        ArrayList<Double> temp = new ArrayList<Double>();

        for(int i = 0; i < gdpData.size(); i++){
            temp.add(gdpData.get(i).getGdpPerCapita());
        }

        return FXCollections.observableList(temp);
    }


}
