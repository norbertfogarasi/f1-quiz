package com.it.fogarasi.norbert.f1quiz.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.it.fogarasi.norbert.f1quiz.R;

public class GameOverDialog extends AppCompatActivity {

    TextView tvNumberOfQuestions, tvGameOverTime, tvCorrect, tvIncorrect;
    String gameOverTime;
    StringBuilder sNumberOfQuestions, sCorrect, sIncorrect;
    int numberOfQuestions, correctAnswers, incorrectAnswers;
    int defaultValue = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        this.requestWindowFeature(Window.FEATURE_LEFT_ICON);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.requestWindowFeature(Window.FEATURE_RIGHT_ICON);
        this.setTitle(getResources().getString(R.string.tv_game_over));
        setContentView(R.layout.gameover_dialog);
        initializeViews();
        setFinishOnTouchOutside(false);
    }

    private void initializeViews() {

        sNumberOfQuestions = new StringBuilder();
        sNumberOfQuestions.append(getResources().getString(R.string.tv_number_of_questions));

        sCorrect = new StringBuilder();
        sCorrect.append(getResources().getString(R.string.tv_correct_answers));

        sIncorrect = new StringBuilder();
        sIncorrect.append(getResources().getString(R.string.tv_incorrect_answers));

        tvNumberOfQuestions = (TextView) findViewById(R.id.tv_number_of_questions);
        tvCorrect = (TextView) findViewById(R.id.tv_correct_answers);
        tvIncorrect = (TextView) findViewById(R.id.tv_incorrect_answers);
        tvGameOverTime = (TextView) findViewById(R.id.tv_gameover_time);

        tvNumberOfQuestions.setText(sNumberOfQuestions);
        tvCorrect.setText(sCorrect);
        tvIncorrect.setText(sIncorrect);

        numberOfQuestions = getIntent().getIntExtra(GameActivity.SEND_NUMBER_OF_QUESTIONS, defaultValue);
        correctAnswers = getIntent().getIntExtra(GameActivity.SEND_SCORE_CORRECT, defaultValue);
        incorrectAnswers = getIntent().getIntExtra(GameActivity.SEND_SCORE_INCORRECT, defaultValue);
        gameOverTime = getIntent().getStringExtra(GameActivity.SEND_TIME);

        sNumberOfQuestions.append(" ");
        sNumberOfQuestions.append(numberOfQuestions);

        sCorrect.append(" ");
        sCorrect.append(correctAnswers);

        sIncorrect.append(" ");
        sIncorrect.append(incorrectAnswers);

        try {
            tvNumberOfQuestions.setText(sNumberOfQuestions);
        } catch (NullPointerException e) {
            Log.d("FNorbert", "numberOfQuestions");
        }
        try {
            tvCorrect.setText(sCorrect);
        } catch (NullPointerException e) {
            Log.d("FNorbert", "correctAnswers");
        }
        try {
            tvIncorrect.setText(sIncorrect);
        } catch (NullPointerException e) {
            Log.d("FNorbert", "incorrectAnswers");
        }
        try {
            tvGameOverTime.setText(String.valueOf(gameOverTime));
        } catch (NullPointerException e) {
            Log.d("FNorbert", "gameOverTime");
        }
    }

    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.btn_another_game:
                i = new Intent(this, GameActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.btn_back_to_main_menu:
                i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
        return super.onKeyDown(keyCode, event);
    }
}