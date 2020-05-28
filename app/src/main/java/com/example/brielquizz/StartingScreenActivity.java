package com.example.brielquizz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class StartingScreenActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_QUIZZ = 1;
    public static final String EXTRA_CATEGORY_ID = "extraCategoryID";
    public static final String EXTRA_CATEGORY_Name = "extraCategoryName";

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "KeyHighscore";

    private TextView textViewHighscore;
    private Spinner spinnerCategory;
    private int highscore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_screen);

        textViewHighscore = findViewById(R.id.text_view_highscore);
        spinnerCategory = findViewById(R.id.spinner_category);

        loadCategories();
        loadHighscore();
        Button buttonStartQuizz = findViewById(R.id.button_start_quizz);
        buttonStartQuizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuizz();
            }
        });

    }

    private void startQuizz() {

        Category selectedCategory = (Category) spinnerCategory.getSelectedItem();
        int categoryID = selectedCategory.getId();
        String categoryName = selectedCategory.getName();

        Intent intent = new Intent(StartingScreenActivity.this, QuizzActivity.class);
        intent.putExtra(EXTRA_CATEGORY_ID, categoryID);
        intent.putExtra(EXTRA_CATEGORY_Name, categoryName);
        startActivityForResult(intent, REQUEST_CODE_QUIZZ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_QUIZZ){
            if(resultCode == RESULT_OK){
                int score = data.getIntExtra(QuizzActivity.EXTRA_SCORE, 0);
                if(score > highscore) {
                    updateHightscore(score);
                }
            }
        }
    }

    private void loadCategories() {
        QuizzDbHelper dbHelper = QuizzDbHelper.getInstance(this);
        List<Category> categories = dbHelper.getAllCategories();

        ArrayAdapter<Category> adapterCategories = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapterCategories);
    }

    private void loadHighscore(){
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highscore = prefs.getInt(KEY_HIGHSCORE, 0);
        textViewHighscore.setText(("Highscore : " + highscore));
    }

    private void updateHightscore(int highscoreNew){
        highscore = highscoreNew;
        textViewHighscore.setText(("Highscore : " + highscore));
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE, highscore);
        editor.apply();
    }
}
