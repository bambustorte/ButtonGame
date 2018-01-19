package de.drunkenapps.buttongame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

public class ActivityMain extends AppCompatActivity implements View.OnClickListener {

    Button[] buttons;
    Button playAgain;
    TextView scoreView;
    TextView triesView;
    TextView timeTaken;
    int score;
    int triesLeft;
    Date startDate;
    Date endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreView = findViewById(R.id.scoreView);
        triesView = findViewById(R.id.triesView);
        timeTaken = findViewById(R.id.timeTaken);
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

        setup();

    }

    void setup(){

        score = 0;
        triesLeft = 10;

        timeTaken.setVisibility(View.INVISIBLE);

        updateScoreAndTries();

        for (Button button : buttons) {
            button.setVisibility(View.INVISIBLE);
            button.setOnClickListener(this);
        }

        playAgain.setVisibility(View.INVISIBLE);
        playAgain.setClickable(false);

        buttons[getRand()].setVisibility(View.VISIBLE);

        startDate = new Date();
    }

    @Override
    public void onClick(View v) {
        if (v.getVisibility() == View.VISIBLE){
            score++;
            triesLeft--;
            v.setVisibility(View.INVISIBLE);
            buttons[getRand()].setVisibility(View.VISIBLE);
            updateScoreAndTries();
        }

        if (triesLeft <= 0) {
            endGame();
            return;
        }
    }

    void endGame(){
        endDate = new Date();
        for (Button button : buttons) {
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(null);
        }

        timeTaken.setText("millis: " + (endDate.getTime() - startDate.getTime()));
        timeTaken.setVisibility(View.VISIBLE);

        playAgain.setVisibility(View.VISIBLE);
        playAgain.setClickable(true);
    }

    int getRand(){
        return (int) (Math.random() * 16 );
    }

    void updateScoreAndTries(){
        scoreView.setText("Score: " + score);
        triesView.setText("Tries left: " + triesLeft);
    }
}
