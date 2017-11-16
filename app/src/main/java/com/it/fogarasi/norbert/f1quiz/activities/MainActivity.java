package com.it.fogarasi.norbert.f1quiz.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.it.fogarasi.norbert.f1quiz.AboutText;
import com.it.fogarasi.norbert.f1quiz.Question;
import com.it.fogarasi.norbert.f1quiz.R;
import com.it.fogarasi.norbert.f1quiz.threads.QuestionReaderThread;

public class MainActivity extends AppCompatActivity {

    boolean doubleOnBackPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button btnStartGame = (Button) findViewById(R.id.btn_new_game);
        ImageButton imgBtnSettings = (ImageButton) findViewById(R.id.im_btn_settings);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutText aboutText = new AboutText();
                String message = aboutText.aboutText;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle(getResources().getString(R.string.dialog_title));
                builder.setNeutralButton(getResources().getString(R.string.dialog_neutralbutton), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setMessage(message);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.btn_new_game:
                i = new Intent(this, GameActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.im_btn_settings:
                i  = new Intent(this,SettingsActivity.class);
                startActivity(i);
                break;
            case R.id.btn_statistic:
                i = new Intent(this,StatisticActivity.class);
                startActivity(i);
                break;
            case R.id.btn_rules:
                i = new Intent(this,RulesActivity.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleOnBackPressed) {
            super.onBackPressed();
        }
        else {
            doubleOnBackPressed = true;
            Toast.makeText(this,"Press again to exit",Toast.LENGTH_SHORT).show();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleOnBackPressed = false;
            }
        }, 2000);
    }
}


