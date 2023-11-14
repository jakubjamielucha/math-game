package com.example.mathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

public class Game extends AppCompatActivity {

    TextView score, lives, time, question;
    EditText answer;
    Button send, next;
    Random random = new Random();
    int number1, number2, userAnswer, correctAnswer, userScore = 0, userLives = 3;
    CountDownTimer timer;

    private static final long START_TIMER = 10000;
    Boolean timerRunning;
    long timeLeft = START_TIMER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        score = findViewById(R.id.textViewScore);
        lives = findViewById(R.id.textViewLives);
        time = findViewById(R.id.textViewTime);
        question = findViewById(R.id.textViewQuestion);

        answer = findViewById(R.id.editTextAnswer);

        send = findViewById(R.id.buttonSend);
        next = findViewById(R.id.buttonNext);

        gameQuestion();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userAnswer = Integer.valueOf(answer.getText().toString());

                pauseTimer();

                if(userAnswer==correctAnswer){
                    userScore+=10;
                    score.setText(""+userScore);
                    question.setText("Congrats! Your answer is correct!");
                    resetTimer();

                } else {
                    userLives-=1;
                    lives.setText(""+userLives);
                    question.setText("Sorry, your answer is incorrect.");
                    resetTimer();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer.setText("");
                resetTimer();

                if(userLives <= 0){
                    Toast.makeText(getApplicationContext(), "GAME OVER", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Game.this, Result.class);
                    intent.putExtra("score", userScore);
                    startActivity(intent);
                    finish();
                } else {
                    gameQuestion();
                }
            }
        });

    }

    public void gameQuestion(){
        number1 = random.nextInt(100);
        number2 = random.nextInt(100);

        correctAnswer = number1 + number2;
        question.setText(number1 + " + " + number2);

        startTimer();


    }

    public void startTimer(){
        timer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long l) {
                timeLeft = l;
                updateText();
            }

            @Override
            public void onFinish() {

                timerRunning = false;
                pauseTimer();
                resetTimer();
                updateText();
                userLives -= 1;
                lives.setText(""+userLives);
                question.setText("Sorry, the time is up!");

            }
        }.start();

        timerRunning = true;
    }

    public void updateText(){

        int second = (int) (timeLeft/1000) % 60;
        String timeLeft = String.format(Locale.getDefault(), "%02d", second);
        time.setText(timeLeft);
    }

    public void pauseTimer(){
        timer.cancel();
        timerRunning=false;
    }

    public void resetTimer(){
        timeLeft = START_TIMER;
    }
}