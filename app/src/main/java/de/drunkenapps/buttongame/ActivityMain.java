package de.drunkenapps.buttongame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityMain extends AppCompatActivity implements View.OnClickListener {

    Button[] buttons;
    Button playAgain;
    TextView scoreView;
    TextView timeView;
    int score;
    int timeLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreView = findViewById(R.id.scoreView);
        timeView = findViewById(R.id.timeView);
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
        timeLeft = 10000;

        updateScore();

        for (Button button : buttons) {
            button.setVisibility(View.INVISIBLE);
            button.setOnClickListener(this);
        }

        playAgain.setVisibility(View.INVISIBLE);
        playAgain.setClickable(false);

        buttons[getRand()].setVisibility(View.VISIBLE);

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        if (timeLeft <= 0){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    endGame();
                                }
                            });
                            return;
                        }

                        Thread.sleep(100);
                        timeLeft -= 100;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                timeView.setText("Time left: " + timeLeft/1000 + "." + timeLeft%1000);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    @Override
    public void onClick(View v) {
        if (v.getVisibility() == View.VISIBLE){
            score++;
            v.setVisibility(View.INVISIBLE);
            buttons[getRand()].setVisibility(View.VISIBLE);
            updateScore();
        }
    }

    void endGame(){
        for (Button button : buttons) {
//            button.setClickable(false);
//            button.setEnabled(false);
//            button.setBackgroundColor(Color.BLACK);
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(null);
        }
        playAgain.setVisibility(View.VISIBLE);
        playAgain.setClickable(true);
    }

    int getRand(){
        return (int) (Math.random() * 16 );
    }

    void updateScore(){
        scoreView.setText("Score: " + score);
    }
}
