package de.drunkenapps.buttongame;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

public class ActivityMain extends AppCompatActivity implements View.OnClickListener {

    Button[] buttons;
    Button playAgain;
    Button skipRed;
    TextView scoreView;
    TextView triesView;
    TextView timeTaken;
    int score;
    int triesLeft;
    Date startDate;
    Date endDate;
    boolean red;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreView = findViewById(R.id.scoreView);
        triesView = findViewById(R.id.triesView);
        timeTaken = findViewById(R.id.timeTaken);
        skipRed = findViewById(R.id.skipRed);
        playAgain = findViewById(R.id.playAgain);

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setup();
            }
        });

        buttons = new Button[16];

        buttons[0] = findViewById(R.id.button0);
        buttons[1] = findViewById(R.id.button1);
        buttons[2] = findViewById(R.id.button2);
        buttons[3] = findViewById(R.id.button3);
        buttons[4] = findViewById(R.id.button4);
        buttons[5] = findViewById(R.id.button5);
        buttons[6] = findViewById(R.id.button6);
        buttons[7] = findViewById(R.id.button7);
        buttons[8] = findViewById(R.id.button8);
        buttons[9] = findViewById(R.id.button9);
        buttons[10] = findViewById(R.id.button10);
        buttons[11] = findViewById(R.id.button11);
        buttons[12] = findViewById(R.id.button12);
        buttons[13] = findViewById(R.id.button13);
        buttons[14] = findViewById(R.id.button14);
        buttons[15] = findViewById(R.id.button15);

        skipRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((ColorDrawable)getActiveButton().getBackground()).getColor() == Color.RED){
                    makeMove(getActiveButton());
                }
                triesLeft--;
                updateScoreAndTries();
                checkGame();
            }
        });

        setup();

    }

    void setup(){

        red = false;

        score = 0;
        triesLeft = 10;

        timeTaken.setVisibility(View.INVISIBLE);

        updateScoreAndTries();

        for (Button button : buttons) {
            button.setVisibility(View.INVISIBLE);
            button.setBackgroundColor(Color.GREEN);
            button.setOnClickListener(this);
        }

        for (int i = 0; i < 5; i++) {
            buttons[getRand()].setBackgroundColor(Color.RED);
        }

        playAgain.setVisibility(View.INVISIBLE);
        playAgain.setClickable(false);

        skipRed.setClickable(true);

        buttons[getRand()].setVisibility(View.VISIBLE);
        while (((ColorDrawable) (getActiveButton().getBackground())).getColor() == Color.RED){
            getActiveButton().setVisibility(View.INVISIBLE);
            buttons[getRand()].setVisibility(View.VISIBLE);
        }

        startDate = new Date();
    }

    @Override
    public void onClick(View v) {
        if (v.getVisibility() == View.VISIBLE){
            ColorDrawable colorDrawable = (ColorDrawable) v.getBackground();
            if (colorDrawable.getColor() == Color.RED){
                score -= 3;
            }
            triesLeft--;
            makeMove(v);
        }

        checkGame();
    }

    void makeMove(View v){
        score++;
        v.setVisibility(View.INVISIBLE);
        buttons[getRand()].setVisibility(View.VISIBLE);
        updateScoreAndTries();
    }

    boolean checkGame(){
        if (triesLeft <= 0) {
            endGame();
            return false;
        }
        return true;
    }

    void endGame(){
        endDate = new Date();
        for (Button button : buttons) {
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(null);
        }

        timeTaken.setText("Time: " + (endDate.getTime() - startDate.getTime()));
        timeTaken.setVisibility(View.VISIBLE);

        playAgain.setVisibility(View.VISIBLE);
        playAgain.setClickable(true);

        skipRed.setClickable(false);
    }

    int getRand(){
        return (int) (Math.random() * 16 );
    }

    Button getActiveButton(){
        for (Button button :
                buttons) {
            if (button.getVisibility() == View.VISIBLE){
                return button;
            }
        }
        return null;
    }

    void updateScoreAndTries(){
        scoreView.setText("Score: " + score);
        triesView.setText("Clicks left: " + triesLeft);
    }
}
