package com.example.kim.newhigherlower;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HigherLower extends AppCompatActivity {

    private ImageView imageView;
    private int[] mDiceImage;
    private int currentThrow;
    private int previousThrow;
    private int currentScore = 0;
    private int highScore = 0;

    List<Integer> diceIndexList = new ArrayList<>();
    List<String> historyStringList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_higher_lower);

        //initializes imageView and Buttons
        imageView = findViewById(R.id.imageView);
        FloatingActionButton higherButton = findViewById(R.id.higherButton);
        FloatingActionButton lowerButton = findViewById(R.id.lowerButton);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyStringList);

        //array holding giving the images a number (0-5)
        mDiceImage = new int[] {R.drawable.d1, R.drawable.d2, R.drawable.d3, R.drawable.d4, R.drawable.d5, R.drawable.d6};

        //Makes the diceIndexList not empty
        diceIndexList.add(0);

        //Triggers PressedButton function when higher button is pressed
        higherButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PressedButton(true);
            }
        });

        //Triggers PressedButton function when lower button is pressed
        lowerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PressedButton(false);
            }
        });

        ListView listView = (ListView) findViewById(R.id.viewHistory);
        listView.setAdapter(arrayAdapter);
    }

    //Rolls dice and sets image
    private int DiceRoll (){
        int randomNumber;
        randomNumber = (int) (Math.random() * 6);

        if (randomNumber == diceIndexList.get(diceIndexList.size() - 1)) {
            randomNumber = DiceRoll();
        }

        imageView.setImageResource(mDiceImage[randomNumber]);
        return randomNumber;
    }

    //Checks if guess is correct
    private boolean GuessCorrect (boolean higherButton, int currentThrow, int previousThrow){
        if((currentThrow > previousThrow) && (higherButton == true)){
            return true;
        } else if ((currentThrow < previousThrow) && (higherButton == false)){
            return true;
        } else {
            return false;
        }
    }

    //Gives output for when the guess is correct or not
    private void PressedButton (boolean higherButton) {
        diceIndexList.add(DiceRoll());
        currentThrow = diceIndexList.get(diceIndexList.size() - 1);
        previousThrow = diceIndexList.get(diceIndexList.size() - 2);

        if (GuessCorrect(higherButton, currentThrow, previousThrow) == true) {
            currentScore++;
            if (currentScore > highScore) {
                highScore = currentScore;
            }
        } else {
            currentScore = 0;
            historyStringList.clear();
        }

        TextView viewScore = findViewById(R.id.viewScore);
        TextView viewHighScore = findViewById(R.id.viewHighScore);
        viewScore.setText("Score: " + currentScore);
        viewHighScore.setText("Highscore: " + highScore);

        historyStringList.add("Throw is " + (currentThrow + 1));
        arrayAdapter.notifyDataSetChanged();


    }
}
