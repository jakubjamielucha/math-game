package com.example.mathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class Game extends AppCompatActivity {

    TextView score, lives, time, question;
    EditText answer;
    Button send, next;
    Random random = new Random();
    int number1, number2, userAnswer, correctAnswer, userScore = 0, userLives = 3;

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
                if(userAnswer==correctAnswer){
                    userScore+=10;
                    score.setText(""+userScore);
                    question.setText("Congrats! Your answer is correct!");

                } else {
                    userLives-=1;
                    lives.setText(""+userLives);
                    question.setText("Sorry, your anser is incorrect.");
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer.setText("");
                gameQuestion();
            }
        });

    }

    public void gameQuestion(){
        number1 = random.nextInt(100);
        number2 = random.nextInt(100);

        correctAnswer = number1 + number2;
        question.setText(number1 + " + " + number2);


    }
}