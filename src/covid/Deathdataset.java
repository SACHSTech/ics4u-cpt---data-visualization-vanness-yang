package covid;

import covid.*;
import java.util.*;

public class Deathdataset {

    private ArrayList<Deaths> deathData;

    public Deathdataset(){
        deathData = new ArrayList<Deaths>();
    }

    public void addDeathData(Deaths newDeaths){
        deathData.add(newDeaths);
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
            if(type.equalsIgnoreCase("deaths")){
                if((deathData.get(leftIndex).getDeathPerMillion()) <= (deathData.get(rightIndex).getDeathPerMillion())){
                    mergedSortedArray.add(deathData.get(leftIndex));
                    leftIndex++;
                }else{
                    mergedSortedArray.add(deathData.get(rightIndex));
                    rightIndex++;
                }
            }else if(type.equalsIgnoreCase("name")){
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

    public ArrayList<Deaths> linearSearch(String key){
        
        String element1;
        String element2;
        String element3;

        ArrayList<Deaths> searchResults = new ArrayList<Deaths>();

        for(int i = 0; i < deathData.size(); i++){
           element1 = deathData.get(i).getCountryName();
           element2 = deathData.get(i).getCountryCode();  
           element3 = deathData.get(i).getCountryContinent();

           if(element1.equalsIgnoreCase(key)){
               searchResults.add(deathData.get(i));       
           }else if(element2.equalsIgnoreCase(key)){
               searchResults.add(deathData.get(i));
           }else if(element3.equalsIgnoreCase(key)){
               searchResults.add(deathData.get(i));
           }
       }
       
       return searchResults;
       
    }

    public ArrayList<Deaths> filter(String filter){
        
        String element;

        ArrayList<Deaths> filtered = new ArrayList<Deaths>();

        for(int i = 0; i < deathData.size(); i++){
           element = deathData.get(i).getCountryContinent();

           if(element.equalsIgnoreCase(filter)){
               filtered.add(deathData.get(i));       
           }
       }
       
       return filtered;
       
    }

}
