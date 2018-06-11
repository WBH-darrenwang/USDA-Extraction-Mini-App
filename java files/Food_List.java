package com.wang.myandroidfirstapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Overview:
 *
 * Creates a new thread to access the http link separate from the graphic interfaces (prevent freezing)
 * Creates buttons for each searched food item with OnClickListener that opens a new nutrition intent
 */

public class Food_List extends AsyncTask<Void, Void, Void>{
    private String API_KEY = "6l5HlpQdoRU0FzsijrozdHm4h0mOAa8pfzPYFlYV", food, food_ground;
    private static ArrayList<Food_Item_Object> food_list2;
    public static String current_food;

    public Food_List(String search1, String food_ground1){
        food = search1;
        food_ground = food_ground1;
    }


    @Override
    protected Void doInBackground(Void... Void) {

        /**
         * Extracts the information with the parameters of the food search and food ground
         * Reads from an online JSON file and adds the foods found into this thread's array
         */

        food_list2 = new ArrayList<Food_Item_Object>();
        String text = "", name ="", ndbno = "";
        URL url = null;

        /**
         * tries to connect to the http link with given parameters
         * catches any network problems
         * @food the searched string in the search bar
         * @food_ground the food group number
         * @API_KEY this is a generated key under Darren's name
         */

        try {
            url = new URL("https://api.nal.usda.gov/ndb/search/?format=json&q="+food+"&fg="+food_ground+
                    "&sort=n&max=25&offset=0&api_key="+API_KEY);
            URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(is));

            /**
             * Extracts every food item that came up in the search and makes it in into a Food_Item_Object object
             * Adds the new object to this thread's arraylist
             */

            while ((text = read.readLine()) != null) {
                String temp = text;

                if (text.indexOf("name")!=-1) {
                    if (text.indexOf("UPC")!=-1) {
                        temp = text.substring(text.indexOf("name")+8,text.length()-1);
                        name = temp.substring(0,temp.indexOf("UPC")-2);
                    }
                    else if (text.indexOf("UPC")==-1){
                        name = text.substring(text.indexOf("name")+8,text.length()-2);
                    }
                }
                else if (text.indexOf("ndbno")!=-1){
                    ndbno = text.substring(text.indexOf("ndbno")+9,text.length()-2);
                }
                if (text.indexOf("ndbno")!=-1 && !name.equals("") && !ndbno.equals("")) {
                    food_list2.add(new Food_Item_Object(name,ndbno));
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds foods found in this secondary thread onto the public static main arraylist from the main thread *
     */

    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);

        /**
         * Clears the static master arraylist from Present_Nutrition class
         */

        Present_Nutrition.food_list.clear();

        /**
         * Copies the food items inside this thread's arraylist to Present_Nutrition class's arraylist
         */

        for(int i = 0; i<food_list2.size(); i++){
            Present_Nutrition.food_list.add(food_list2.get(i));
        }

        /**
         * Removes every previous button in Present_Nutrition's linear layout
         */

        Present_Nutrition.ll.removeAllViews();

        /**
         * Creates a button for every food item within the arraylist
         * Adds the OnClickListener to each button
         */

        for(int i=0; i<Present_Nutrition.food_list.size(); i++){
            Present_Nutrition.but = new Button(Present_Nutrition.context);
            Present_Nutrition.but.setText(Present_Nutrition.food_list.get(i).getname());
            Present_Nutrition.but.setId(Integer.parseInt(Present_Nutrition.food_list.get(i).getndbno()));
            Present_Nutrition.ll.addView(Present_Nutrition.but);
            Present_Nutrition.but.setOnClickListener(click(Present_Nutrition.but.getId(),Present_Nutrition.food_list.get(i).getname()));
        }
    }

    /**
     * @param num the ndbno number that corresponds to the food item id
     * @return the OnClickListener that opens a initiates the ndbno number in the new intent and opens the new intent
     */

    public static View.OnClickListener click(final int num, final String name){
        View.OnClickListener onclick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nutrition_Intent.number = "" + num;
                current_food = name;
                Intent intent = new Intent(Present_Nutrition.context, Nutrition_Intent.class);
                Present_Nutrition.context.startActivity(intent);
            }
        };
        return onclick;
    }
}
