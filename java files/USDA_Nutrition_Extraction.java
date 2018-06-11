package com.wang.myandroidfirstapplication;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Overview:
 *
 * Connects to the http and extracts the nutritional values
 * Sets the TextView in Nutrition_Intent
 */
public class USDA_Nutrition_Extraction extends AsyncTask<Void,Void,Void>{
    private String text, water, calories, protein, sodium, fat, num, API_KEY="6l5HlpQdoRU0FzsijrozdHm4h0mOAa8pfzPYFlYV";
    public USDA_Nutrition_Extraction(String number){
        num = number;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        URL url;
        try {
            url = new URL("https://api.nal.usda.gov/ndb/V2/reports?ndbno="+num+"&type=f&format=json&api_key="+API_KEY);
            URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(is));
            text = read.readLine();
            try{
                water = get_nutr(text,"Water");
                calories = get_nutr(text,"Energy");
                protein = get_nutr(text,"Protein");
                sodium = get_nutr(text,"Sodium");
                fat = get_nutr(text,"Total lipid (fat)");
            }catch (Exception e){
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param text the whole JSON text extracted from the USDA database
     * @param look the nutrition value that is wanted to be extracted
     * @return the nutrition value with the unit
     */

    public String get_nutr(String text, String look) {
        String unit="g", value="g", total = "No data";
        if (text.indexOf(look)!=-1) {
            text = text.substring(text.indexOf(look));

            String temp = text.substring(text.indexOf("unit"));
            unit = temp.substring(7,temp.indexOf(",")-1);

            temp = text.substring(text.indexOf("value"));
            value = temp.substring(7,temp.indexOf(",")-1);
            value = value.replace("\"", "");

            total = value+unit;
        }
        return total;
    }

    public String toString(){
        return "Water: " + water + "\nCalories: " + calories + "\nProtein: " + protein +
                "\nSodium: " + sodium + "\nFat: " + fat;
    }

    /**
     * Sets the TextView in Nutrition_Intent to the nutrition text
     * @param aVoid
     */

    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        Nutrition_Intent.nutrition_text.append(toString());
    }

    public String getText(){
        return text;
    }

    public String getWater(){
        return water;
    }

    public String getCalories(){
        return calories;
    }
    public String getProtein(){
        return  protein;
    }
}
