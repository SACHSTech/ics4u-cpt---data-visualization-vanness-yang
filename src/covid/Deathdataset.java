package covid;

import covid.*;
import java.util.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.chart.BarChart;


public class Deathdataset {

    private ArrayList<Deaths> deathData;

    public Deathdataset(){
        deathData = new ArrayList<Deaths>();
    }

    public void addDeathData(Deaths newDeaths){
        deathData.add(newDeaths);
    }

    public ArrayList<Deaths> getDeathObject(){
        return deathData;
    }

    public void printResults(){
        for(int i = 0; i < deathData.size(); i++){
            System.out.println(deathData.get(i).toString());
        }
    }

    public void mergeSort(String type){       
        divide(0, deathData.size() - 1, type);
    }

    public void divide(int startIndex, int endIndex, String type){
        
        //Divide till you breakdown your list to single element
        if (startIndex < endIndex && (endIndex - startIndex) >= 1) {
            int mid = (endIndex + startIndex) / 2;
            divide(startIndex, mid, type);
            divide(mid + 1, endIndex, type);        
            
            //merging Sorted array produce above into one sorted array
            merger(startIndex, mid, endIndex, type);            
        }       
    }   

    public void merger(int startIndex, int midIndex, int endIndex, String type){
        
        //Below is the mergedarray that will be sorted array Array[i-midIndex] , Array[(midIndex+1)-endIndex]
        ArrayList<Deaths> mergedSortedArray = new ArrayList<Deaths>();
        
        int leftIndex = startIndex;
        int rightIndex = midIndex + 1;
        
        while(leftIndex <= midIndex && rightIndex <= endIndex){
            if(type.equalsIgnoreCase("Death Toll")){
                if((deathData.get(leftIndex).getDeathPerMillion()) <= (deathData.get(rightIndex).getDeathPerMillion())){
                    mergedSortedArray.add(deathData.get(leftIndex));
                    leftIndex++;
                }else{
                    mergedSortedArray.add(deathData.get(rightIndex));
                    rightIndex++;
                }
            }else if(type.equalsIgnoreCase("Alphabetical")){
                int comparison; 
                comparison = deathData.get(leftIndex).getCountryName().compareTo(deathData.get(rightIndex).getCountryName());
                if(comparison < 0){
                    mergedSortedArray.add(deathData.get(leftIndex));
                    leftIndex++;
                }else if(comparison > 0){
                    mergedSortedArray.add(deathData.get(rightIndex));
                    rightIndex++;
                }
            }
            
        }       
        
        //Either of below while loop will execute
        while(leftIndex <= midIndex){
            mergedSortedArray.add(deathData.get(leftIndex));
            leftIndex++;
        }
        
        while(rightIndex <= endIndex){
            mergedSortedArray.add(deathData.get(rightIndex));
            rightIndex++;
        }
        
        int i = 0;
        int j = startIndex;
        //Setting sorted array to original one
        while(i < mergedSortedArray.size()){
            deathData.set(j, mergedSortedArray.get(i++));
            j++;
        }
    }

    public ObservableList<Deaths> linearSearch(String key){
        
        String element1;
        String element2;
        String element3;

        ArrayList<Deaths> searchResults = new ArrayList<Deaths>();

        for(int i = 0; i < deathData.size(); i++){
           element1 = deathData.get(i).getCountryName();
           element2 = deathData.get(i).getCountryCode();  
           element3 = deathData.get(i).getCountryContinent();

            if(key.equals("")){
                for(i = 0; i < deathData.size(); i++){
                    searchResults.add(deathData.get(i));
                }   
            }else{
                if(element1.equalsIgnoreCase(key)){
                    searchResults.add(deathData.get(i));       
                }else if(element2.equalsIgnoreCase(key)){
                    searchResults.add(deathData.get(i));
                }else if(element3.equalsIgnoreCase(key)){
                    searchResults.add(deathData.get(i));
                }
            }  
       }
       
       return FXCollections.observableList(searchResults);
       
    }

    public ObservableList<Deaths> getSortedObservableList(String sort){
        this.mergeSort(sort);

        return FXCollections.observableList(deathData);

    }

    public ObservableList<Deaths> filter(String filter){
        
        String element;
        int i;

        ArrayList<Deaths> filtered = new ArrayList<Deaths>();

        if(filter.equals("Default")){
            for(i = 0; i < deathData.size(); i++){
                filtered.add(deathData.get(i));
            }
        }else{
            for(i = 0; i < deathData.size(); i++){
            
                element = deathData.get(i).getCountryContinent();
    
                if(element.equalsIgnoreCase(filter)){
                   filtered.add(deathData.get(i));       
                }
            }
        }
        
        return FXCollections.observableList(filtered);
       
    }

    public double max(){

        double max = 0;
        int i;
        
        for(i = 0; i < deathData.size(); i++){
            // Finding the Max
            if(deathData.get(i).getDeathPerMillion() >= max){
                max = Math.round(deathData.get(i).getDeathPerMillion() * 100.0) / 100.0;
            }
        }

        return max;

    }

    public double min(){
        int i;
        double min = 10000;

        for(i = 0; i < deathData.size(); i++){
            if(deathData.get(i).getDeathPerMillion() <= min){
                min = Math.round(deathData.get(i).getDeathPerMillion() * 100.0) / 100.0;
            }
        }

        return min;
    }

    public double average(){
        int i; 
        double total = 0;
        double average;

        for(i = 0; i < deathData.size(); i++){
            // Finding the total 
            total = total + deathData.get(i).getDeathPerMillion();
        }

        // Finding the average
        average = Math.round((total / deathData.size()) * 100.0) / 100.0;

        return average;

    }

    public double median(){

        return Math.round(deathData.get(deathData.size() / 2).getDeathPerMillion() * 100.0) / 100.0;
        
    }

    public double count(){

        return deathData.size();
    }

    public double deviation(){
        double mean;
        double squared;
        double sum = 0;
        double meanSum;
        double deviation;
        int i;

        mean = this.average();
        
        for(i = 0; i < deathData.size(); i++){
            squared = Math.pow((deathData.get(i).getDeathPerMillion() - mean), 2);
            sum = sum + squared;
            
        
        }

        meanSum = sum / deathData.size();
        deviation = Math.round(Math.sqrt(meanSum) * 100.0) / 100.0;

        return deviation;

    }

    public ObservableList<String> getCountryObservableList(){

        ArrayList<String> temp = new ArrayList<String>();

        for(int i = 0; i < deathData.size(); i++){
            temp.add(deathData.get(i).getCountryName());
        }

        return FXCollections.observableList(temp);
    }

    public BarChart.Series getBarObjects(){

        BarChart.Series<String, Integer> series = new BarChart.Series<>();

        for(int i = 0; i < deathData.size(); i++){
            series.getData().add(new BarChart.Data(deathData.get(i).getCountryName(), deathData.get(i).getDeathPerMillion()));
        }


        return series;
    }
    
    
}
