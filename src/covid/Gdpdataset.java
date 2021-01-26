package covid;

import covid.*;
import java.util.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class Gdpdataset {

    private ArrayList<Gdp> gdpData;

    public Gdpdataset(){
        gdpData = new ArrayList<Gdp>();
    }

    public void addGdpData(Gdp newGdp){
        gdpData.add(newGdp);
    }

    public void printResults(){
        for(int i = 0; i < gdpData.size(); i++){
            System.out.println(gdpData.get(i).toString());
        }
    }

    public ArrayList<Gdp> getGdpObject(){
        return gdpData;
    }

    public void mergeSort(String type){       
        divide(0, gdpData.size() - 1, type);
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
        ArrayList<Gdp> mergedSortedArray = new ArrayList<Gdp>();
        
        int leftIndex = startIndex;
        int rightIndex = midIndex + 1;
        
        while(leftIndex <= midIndex && rightIndex <= endIndex){
            if(type.equalsIgnoreCase("GDP")){
                if((gdpData.get(leftIndex).getGdpPerCapita()) <= (gdpData.get(rightIndex).getGdpPerCapita())){
                    mergedSortedArray.add(gdpData.get(leftIndex));
                    leftIndex++;
                }else{
                    mergedSortedArray.add(gdpData.get(rightIndex));
                    rightIndex++;
                }
            }else if(type.equalsIgnoreCase("Alphabetical")){
                int comparison; 
                comparison = gdpData.get(leftIndex).getCountryName().compareTo(gdpData.get(rightIndex).getCountryName());
                if(comparison < 0){
                    mergedSortedArray.add(gdpData.get(leftIndex));
                    leftIndex++;
                }else if(comparison > 0){
                    mergedSortedArray.add(gdpData.get(rightIndex));
                    rightIndex++;
                }
            }
            
        }       
        
        //Either of below while loop will execute
        while(leftIndex <= midIndex){
            mergedSortedArray.add(gdpData.get(leftIndex));
            leftIndex++;
        }
        
        while(rightIndex <= endIndex){
            mergedSortedArray.add(gdpData.get(rightIndex));
            rightIndex++;
        }
        
        int i = 0;
        int j = startIndex;
        //Setting sorted array to original one
        while(i < mergedSortedArray.size()){
            gdpData.set(j, mergedSortedArray.get(i++));
            j++;
        }
    }

    public ObservableList<Gdp> linearSearch(String key){
        
        String element1;
        String element2;
        String element3;
        int i;

        ArrayList<Gdp> searchResults = new ArrayList<Gdp>();

        if(key.equals("")){
            for(i = 0; i < gdpData.size(); i++){
                searchResults.add(gdpData.get(i));
            }
        }else{
            for(i = 0; i < gdpData.size(); i++){
                element1 = gdpData.get(i).getCountryName();
                element2 = gdpData.get(i).getCountryCode();  
                element3 = gdpData.get(i).getCountryContinent();
     
                if(element1.equalsIgnoreCase(key)){
                    searchResults.add(gdpData.get(i));       
                }else if(element2.equalsIgnoreCase(key)){
                    searchResults.add(gdpData.get(i));
                }else if(element3.equalsIgnoreCase(key)){
                    searchResults.add(gdpData.get(i));
                }
            }
        }
       
       return FXCollections.observableList(searchResults);
       
    }

    public ObservableList<Gdp> getSortedObservableList(String sort){
        this.mergeSort(sort);

        return FXCollections.observableList(gdpData);

    }

    public ObservableList<Gdp> filter(String filter){
        
        String element;
        int i;

        ArrayList<Gdp> filtered = new ArrayList<Gdp>();

        if(filter.equals("Default")){
            for(i = 0; i < gdpData.size(); i++){
                filtered.add(gdpData.get(i));
            }
        }else{
            for(i = 0; i < gdpData.size(); i++){
                element = gdpData.get(i).getCountryContinent();
     
                if(element.equalsIgnoreCase(filter)){
                    filtered.add(gdpData.get(i));       
                }
            }
        }
        
       
       return FXCollections.observableList(filtered);
       
    }

    public double max(){

        double max = 0;
        int i;
        
        for(i = 0; i < gdpData.size(); i++){
            // Finding the Max
            if(gdpData.get(i).getGdpPerCapita() >= max){
                max = Math.round(gdpData.get(i).getGdpPerCapita() * 100.0) / 100.0;
            }
        }

        return max;

    }

    public double min(){
        int i;
        double min = 200000;

        for(i = 0; i < gdpData.size(); i++){
            if(gdpData.get(i).getGdpPerCapita() <= min){
                min = Math.round(gdpData.get(i).getGdpPerCapita() * 100.0) / 100.0;
            }
        }

        return min;
    }

    public double average(){
        int i; 
        double total = 0;
        double average;

        for(i = 0; i < gdpData.size(); i++){
            // Finding the total 
            total = total + gdpData.get(i).getGdpPerCapita();
        }

        // Finding the average
        average = Math.round((total / gdpData.size()) * 100.0) / 100.0;

        return average;

    }

    public double median(){

        return Math.round(gdpData.get(gdpData.size() / 2).getGdpPerCapita() * 100.0) / 100.0;
        
    }

    public double deviation(){
        double mean;
        double squared;
        double sum = 0;
        double meanSum;
        double deviation;
        int i;

        mean = this.average();
        
        for(i = 0; i < gdpData.size(); i++){
            squared = Math.pow((gdpData.get(i).getGdpPerCapita() - mean), 2);
            sum = sum + squared;
            
        
        }

        meanSum = sum / gdpData.size();
        deviation = Math.round(Math.sqrt(meanSum) * 100.0) / 100.0;

        return deviation;

    }

    public double count(){

        return gdpData.size();
    }


}
