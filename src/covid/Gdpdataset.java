package covid;

import covid.*;
import java.util.*;

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
            if(type.equalsIgnoreCase("gdp")){
                if((gdpData.get(leftIndex).getGdpPerCapita()) <= (gdpData.get(rightIndex).getGdpPerCapita())){
                    mergedSortedArray.add(gdpData.get(leftIndex));
                    leftIndex++;
                }else{
                    mergedSortedArray.add(gdpData.get(rightIndex));
                    rightIndex++;
                }
            }else if(type.equalsIgnoreCase("name")){
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

}
