package com.wang.myandroidfirstapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.prefs.Preferences;

/**
 * Overview:
 *
 * Creates the layout for the nutrition intent
 * Displays the nutrition extracted and prints it onto the screen
 * Has a back and add button
 *    -> back button brings the intent back to the Present_Nutrition intent (search and display food item screen)
 *    -> add button adds the nutrition into ___ intent
 */
public class Nutrition_Intent extends AppCompatActivity {

    public static TextView nutrition_text;
    public static Context context;
    public static String number;

    private Button back_button;

    /**
     * Creates a TextView with nutritions on it
     * Creates a add button
     * Creates a back button
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition__intent);

        context = this;
        nutrition_text = (TextView) findViewById(R.id.nutrition_text_intent);
        nutrition_text.append(Food_List.current_food+"\n\n");
        /**
         * Creates an USDA_Nutrition_Extraction class to retrieve nutrition data in a separate thread
         */

        final USDA_Nutrition_Extraction usda = new USDA_Nutrition_Extraction(number);
        usda.execute();


        /**
         * Initiates back button to return back to the search intent
         */

        back_button = (Button) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Present_Nutrition.context, Present_Nutrition.class);
                Present_Nutrition.searchbar.setText(Present_Nutrition.search);
                Present_Nutrition.food_spinner.setSelection(Present_Nutrition.spinner_pos);
                Present_Nutrition.enter_button.performClick();
                Present_Nutrition.scrollView.setScrollY(Present_Nutrition.scroll_pos);
                startActivity(intent);
            }
        });
    }
}

