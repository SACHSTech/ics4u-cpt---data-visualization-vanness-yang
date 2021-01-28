package covid;

import covid.*;
import java.util.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.chart.BarChart;

/**
* Data visualization CPT (Deathdataset class)
* @author: V. Yang
* 
*/
public class Deathdataset {

    // Attributes 
    private ArrayList<Deaths> deathData;

    /**
    * Constructor - creates a new Deathdataset instance 
    *
    * @author: V. Yang
    */
    public Deathdataset() {
        deathData = new ArrayList<Deaths>();
    }

    /**
    * Adding a new death instance 
    *
    * @param newDeaths - a death object 
    * @author: V. Yang
    */
    public void addDeathData(Deaths newDeaths) {
        deathData.add(newDeaths);
    }

    /**
    * Returning a death instance 
    *
    * @return a death instance 
    * @author: V. Yang
    */
    public ArrayList<Deaths> getDeathObject() {
        return deathData;
    }

    /**
    * Printing a result to the screen for testing purposes 
    *
    * @author: V. Yang
    */
    public void printResults() {
        for (int i = 0; i < deathData.size(); i++) {
            System.out.println(deathData.get(i).toString());
        }
    }

    /**
    * mergeSort method callin the divide method 
    *
    * @param type - how to sort the data 
    * @author: V. Yang
    */
    public void mergeSort(String type) {       
        divide(0, deathData.size() - 1, type);
    }

    /**
    * Dividing the data for mergeSort 
    *
    * @param startIndex - the starting index
    * @param endIndex - the ending index 
    * @param type - how to sort the data 
    * @author: V. Yang
    */
    public void divide (int startIndex, int endIndex, String type) {
        
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
        ArrayList<Deaths> mergedSortedArray = new ArrayList<Deaths>();
        
        int leftIndex = startIndex;
        int rightIndex = midIndex + 1;
        int comparison; 
        int i = 0;
        int j = startIndex;
        
        while (leftIndex <= midIndex && rightIndex <= endIndex) {
            // Sort by increasing death toll 
            if (type.equalsIgnoreCase("Death Toll")) {
                if ((deathData.get(leftIndex).getDeathPerMillion()) <= (deathData.get(rightIndex).getDeathPerMillion())) {
                    mergedSortedArray.add(deathData.get(leftIndex));
                    leftIndex++;
                } else {
                    mergedSortedArray.add(deathData.get(rightIndex));
                    rightIndex++;
                }
            // Sort by alpabetical order 
            } else if (type.equalsIgnoreCase("Alphabetical")) {
                comparison = deathData.get(leftIndex).getCountryName().compareTo(deathData.get(rightIndex).getCountryName());
                if (comparison < 0) {
                    mergedSortedArray.add(deathData.get(leftIndex));
                    leftIndex++;
                } else if (comparison > 0 ){
                    mergedSortedArray.add(deathData.get(rightIndex));
                    rightIndex++;
                }
            }
            
        }       
        
        while (leftIndex <= midIndex) {
            mergedSortedArray.add(deathData.get(leftIndex));
            leftIndex++;
        }
        
        while (rightIndex <= endIndex) {
            mergedSortedArray.add(deathData.get(rightIndex));
            rightIndex++;
        }
        
        //Setting sorted array to original one
        while (i < mergedSortedArray.size()) {
            deathData.set(j, mergedSortedArray.get(i++));
            j++;
        }
    }

    /**
    * linearSearch method  
    *
    * @param key - what to search for 
    * @return an ObservableList of the data searched for 
    * @author: V. Yang
    */
    public ObservableList<Deaths> linearSearch(String key) {
        
        String element1;
        String element2;
        String element3;
        String element4;
        String element5;

        ArrayList<Deaths> searchResults = new ArrayList<Deaths>();

        for (int i = 0; i < deathData.size(); i++) {
           element1 = deathData.get(i).getCountryName();
           element2 = deathData.get(i).getCountryCode();  
           element3 = deathData.get(i).getCountryContinent();
           element4 = deathData.get(i).getDeathDate();
           element5 = String.valueOf(deathData.get(i).getDeathPerMillion());

           // deathData wil be the same as original if nothing is typed in the TextField 
            if (key.equals("")) {
                for (i = 0; i < deathData.size(); i++) {
                    searchResults.add(deathData.get(i));
                }   
            // Adding search results to the ArrayList
            } else {
                if (element1.equalsIgnoreCase(key)) {
                    searchResults.add(deathData.get(i));       
                } else if (element2.equalsIgnoreCase(key)) {
                    searchResults.add(deathData.get(i));
                } else if (element3.equalsIgnoreCase(key)) {
                    searchResults.add(deathData.get(i));
                } else if (element4.equalsIgnoreCase(key)) {
                    searchResults.add(deathData.get(i));
                } else if (element5.equalsIgnoreCase(key)) { 
                    searchResults.add(deathData.get(i));
                }
            }  
       }
       
       return FXCollections.observableList(searchResults);
       
    }

    /**
    * Getting the sorted ObservableList
    *
    * @param sort - how to sort the data 
    * @return an ObservableList of the sorted data 
    * @author: V. Yang
    */
    public ObservableList<Deaths> getSortedObservableList(String sort) {
        this.mergeSort(sort);

        return FXCollections.observableList(deathData);

    }

    /**
    * Method for filtering the data 
    *
    * @param filter - how to filter the data 
    * @return an ObservableList of the filtered data 
    * @author: V. Yang
    */
    public ObservableList<Deaths> filter(String filter) {
        
        String element;
        int i;

        ArrayList<Deaths> filtered = new ArrayList<Deaths>();

        // Default ArrayList 
        if (filter.equals("Default")) {
            for (i = 0; i < deathData.size(); i++) {
                filtered.add(deathData.get(i));
            }
        // Filtered ArrayList
        } else {
            for (i = 0; i < deathData.size(); i++) {
            
                element = deathData.get(i).getCountryContinent();
    
                if (element.equalsIgnoreCase(filter)) {
                   filtered.add(deathData.get(i));       
                }
            }
        }
        
        return FXCollections.observableList(filtered);
       
    }

    /**
    * Finding the maximum deaths 
    *
    * @return the maximum deaths 
    * @author: V. Yang
    */
    public double max() {

        double max = 0;
        int i;
        
        for (i = 0; i < deathData.size(); i++) {
            // Finding the Max
            if (deathData.get(i).getDeathPerMillion() >= max) {
                max = Math.round(deathData.get(i).getDeathPerMillion() * 100.0) / 100.0;
            }
        }

        return max;

    }

    /**
    * Finding the minimum deaths
    *
    * @return the minimum deaths 
    * @author: V. Yang
    */
    public double min() {
        int i;
        double min = 10000;

        for (i = 0; i < deathData.size(); i++) {
            if (deathData.get(i).getDeathPerMillion() <= min) {
                min = Math.round(deathData.get(i).getDeathPerMillion() * 100.0) / 100.0;
            }
        }

        return min;
    }

    /**
    * Finding the average deaths 
    *
    * @return the average deaths 
    * @author: V. Yang
    */
    public double average() {
        int i; 
        double total = 0;
        double average;

        for (i = 0; i < deathData.size(); i++) {
            // Finding the total 
            total = total + deathData.get(i).getDeathPerMillion();
        }

        // Finding the average
        average = Math.round((total / deathData.size()) * 100.0) / 100.0;

        return average;

    }

    /**
    * Finding the median of the deaths 
    *
    * @return the median of the deaths 
    * @author: V. Yang
    */
    public double median() {

        return Math.round(deathData.get(deathData.size() / 2).getDeathPerMillion() * 100.0) / 100.0;
        
    }

    /**
    * Finding the count of the data set 
    *
    * @return the count of the data set 
    * @author: V. Yang
    */
    public double count() {

        return deathData.size();
    }

    /**
    * Finding the standard deviation of the death data  
    *
    * @return the standard deviation
    * @author: V. Yang
    */
    public double deviation() {
        double mean;
        double squared;
        double sum = 0;
        double meanSum;
        double deviation;
        int i;

        // Getting the average of the data 
        mean = this.average();
        
        for (i = 0; i < deathData.size(); i++) {
            // Squaring the difference between the data point and the average 
            squared = Math.pow((deathData.get(i).getDeathPerMillion() - mean), 2);
            
            // Getting the sum of those squared differences
            sum = sum + squared;
        }

        // Finding the average of the sum 
        meanSum = sum / deathData.size();

        // Square rooting the average of the sum 
        deviation = Math.round(Math.sqrt(meanSum) * 100.0) / 100.0;

        return deviation;

    }

    /**
    * Getting an ObservableList of all the countries 
    *
    * @return an ObservableList of all the countries 
    * @author: V. Yang
    */
    public ObservableList<String> getCountryObservableList() {

        ArrayList<String> temp = new ArrayList<String>();

        for (int i = 0; i < deathData.size(); i++) {
            temp.add(deathData.get(i).getCountryName());
        }

        return FXCollections.observableList(temp);
    }

    /**
    * Creating a series for the BarChart (Country VS Deaths)
    *
    * @return the series created for the BarChart 
    * @author: V. Yang
    */
    public BarChart.Series getBarObjects() {

        BarChart.Series<String, Integer> series = new BarChart.Series<>();

        for (int i = 0; i < deathData.size(); i++) {
            series.getData().add(new BarChart.Data(deathData.get(i).getCountryName(), deathData.get(i).getDeathPerMillion()));
        }

        return series;
    }

    /**
    * Getting an ObservableList of the death tolls
    *
    * @return an ObservableList of the death tolls
    * @author: V. Yang
    */
    public ObservableList<Double> getDeathObservableList() {

        ArrayList<Double> temp = new ArrayList<Double>();

        for (int i = 0; i < deathData.size(); i++) {
            temp.add(deathData.get(i).getDeathPerMillion());
        }

        return FXCollections.observableList(temp);
    }
    
    /**
    * Getting the death toll in each object 
    *
    * @param i - a counter variable 
    * @return the death toll in each object
    * @author: V. Yang
    */
    public double getDeathElement(int i) {
        return deathData.get(i).getDeathPerMillion();
    }
    
}
