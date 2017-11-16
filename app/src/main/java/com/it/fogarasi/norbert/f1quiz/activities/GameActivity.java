package com.it.fogarasi.norbert.f1quiz.activities;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.it.fogarasi.norbert.f1quiz.StatisticContract;
import com.it.fogarasi.norbert.f1quiz.StatisticDbHelper;
import com.it.fogarasi.norbert.f1quiz.TouchHandler;
import com.it.fogarasi.norbert.f1quiz.threads.GeneratorThread;
import com.it.fogarasi.norbert.f1quiz.Question;
import com.it.fogarasi.norbert.f1quiz.QuestionHelper;
import com.it.fogarasi.norbert.f1quiz.threads.QuestionReaderThread;
import com.it.fogarasi.norbert.f1quiz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class GameActivity extends AppCompatActivity {

    private static final String LOG_TAG = "FNorbert";
    public static final String SEND_SCORE_CORRECT = "SEND_SCORE_CORRECT";
    public static final String SEND_SCORE_INCORRECT = "SEND_SCORE_INCORRECT";
    public static final String SEND_NUMBER_OF_QUESTIONS = "SEND_NUMBER_OF_QUESTIONS";
    public static final String SEND_TIME = "SEND_TIME";

    private final String soundKey = "switchSound";
    private final String vibrateKey = "switchVibrate";
    private final String numberPickerKey = "numberPicker";

    private static final int COUNT_BACK = 0;
    private static final int BLOCK_BUTTONS = 1;
    private static final int UNLOCK_BUTTONS = 2;
    private static final int PLAY_RIGHT_SONG = 3;
    private static final int PLAY_WRONG_SONG = 4;
    private static final int CHANGE_BUTTON_COLOR = 5;

    private Question q;
    private QuestionHelper questionHelper;
    private static TextView tvGame, tvTimer, tvCounterBack, tvQuestionNumber;
    private static Button b1, b2, b3, b4;
    private static GeneratorThread generatorThread;
    private static MediaPlayer mMediaPlayerRight, mMediaPlayerWrong;
    private static LinearLayout mLinearLayout;
    private SharedPreferences sharedPreferences;
    private TouchHandler mTouchHandler;

    private int[] numbers;
    private static int length, currentQuestion;
    private int scoreCorrect,scoreIncorrect,numberOfQuestions,selectedNumberOfQuestions;

    private static long startTime = 0;

    // -- Timer --------------------------------------------------------------
    final Handler handler = new Handler();
    final MyOwnHandler myOwnHandler = new MyOwnHandler(GameActivity.this, handler);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // -- Initialize --------------------------------------------
        initializeViews();
        initializeComponents();

        HandleQuiz handleQuiz = new HandleQuiz(myOwnHandler);
        new Thread(handleQuiz).start();

        // -- Read the questions ------------------------------------
        QuestionReaderThread questionReaderThread = new QuestionReaderThread(this, q);
        questionReaderThread.start();
        try {
            questionReaderThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = questionReaderThread.getJsonObject();
        JSONArray jsonArray = questionReaderThread.getJsonArray();
        questionHelper = new QuestionHelper(q, jsonObject, jsonArray);

        length = jsonArray.length();
        Log.d(LOG_TAG, String.valueOf(length));
        numbers = new int[length];

        try {
            questionReaderThread.join();
        } catch (InterruptedException e) {
            Log.e(LOG_TAG, "Failed to initialize the questions!");
        }
        //-----------------------------------------------------------
        currentQuestion = 1;
        updateCurrentQuestion(tvQuestionNumber, currentQuestion, numberOfQuestions);

        // -- Display a new question --------------------------------
        displayNewQuestion();
        //------------------------------------------------------------
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
            builder.setCancelable(true);
            builder.setMessage(getResources().getString(R.string.alertdialog_message));
            builder.setPositiveButton(getResources().getString(R.string.alertdialog_button_positive), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }
            });

            builder.setNegativeButton(getResources().getString(R.string.alertdialog_button_negative), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //counterHandler.removeCallbacks(timerRunnable);
        startTime = System.currentTimeMillis();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //counterHandler.post(timerRunnable);
    }

    public void onClick(View view) {
        final int Correct = 1;
        final int Incorrect = 0;
        final int btn1 = 1;
        final int btn2 = 2;
        final int btn3 = 3;
        final int btn4 = 4;
        final boolean DEFAULT_SOUND = true;
        final boolean DEFAULT_VIBRATE = true;
        switch (view.getId()) {
            case R.id.btn_1:
                if (questionHelper.verifyQuestion(q, "A")) {
                    if(sharedPreferences.getBoolean(soundKey, DEFAULT_SOUND))
                    playRightSong();
                    scoreCorrect++;
                    waitAndDisplay(Correct, btn1);
                } else {
                    if(sharedPreferences.getBoolean(soundKey, DEFAULT_SOUND))
                    playWrongSong();
                    if(sharedPreferences.getBoolean(vibrateKey,DEFAULT_VIBRATE))
                    vibrate(getApplicationContext());
                    scoreIncorrect++;
                    waitAndDisplay(Incorrect, btn1);
                }
                b2.setEnabled(true);
                b3.setEnabled(true);
                b4.setEnabled(true);
                break;
            case R.id.btn_2:
                if (questionHelper.verifyQuestion(q, "B")) {
                    if(sharedPreferences.getBoolean(soundKey, DEFAULT_SOUND))
                    playRightSong();
                    scoreCorrect++;
                    waitAndDisplay(Correct, btn2);
                } else {
                    if(sharedPreferences.getBoolean(soundKey, DEFAULT_SOUND))
                    playWrongSong();
                    if(sharedPreferences.getBoolean(vibrateKey,DEFAULT_VIBRATE))
                    vibrate(getApplicationContext());
                    scoreIncorrect++;
                    waitAndDisplay(Incorrect, btn2);
                }
                b1.setEnabled(true);
                b3.setEnabled(true);
                b4.setEnabled(true);
                break;
            case R.id.btn_3:
                if (questionHelper.verifyQuestion(q, "C")) {
                    if(sharedPreferences.getBoolean(soundKey, DEFAULT_SOUND))
                    playRightSong();
                    scoreCorrect++;
                    waitAndDisplay(Correct, btn3);
                } else {
                    if(sharedPreferences.getBoolean(soundKey, DEFAULT_SOUND))
                    playWrongSong();
                    if(sharedPreferences.getBoolean(vibrateKey,DEFAULT_VIBRATE))
                    vibrate(getApplicationContext());
                    scoreIncorrect++;
                    waitAndDisplay(Incorrect, btn3);
                }
                b1.setEnabled(true);
                b2.setEnabled(true);
                b4.setEnabled(true);
                break;
            case R.id.btn_4:
                if (questionHelper.verifyQuestion(q, "D")) {
                    if(sharedPreferences.getBoolean(soundKey, DEFAULT_SOUND))
                    playRightSong();
                    scoreCorrect++;
                    waitAndDisplay(Correct, btn4);
                } else {
                    if(sharedPreferences.getBoolean(soundKey, DEFAULT_SOUND))
                    playWrongSong();
                    if(sharedPreferences.getBoolean(vibrateKey,DEFAULT_VIBRATE))
                    vibrate(getApplicationContext());
                    scoreIncorrect++;
                    waitAndDisplay(Incorrect, btn4);
                }
                b1.setEnabled(true);
                b2.setEnabled(true);
                b3.setEnabled(true);
                break;
        }
    }

    private void initializeViews() {
        mLinearLayout = (LinearLayout) findViewById(R.id.gameLayout);
        tvGame = (TextView) findViewById(R.id.tv_game);
        tvTimer = (TextView) findViewById(R.id.tv_timer);
        tvCounterBack = (TextView) findViewById(R.id.tv_counter_back);
        tvQuestionNumber = (TextView) findViewById(R.id.tv_question_number);
        try {
            tvGame.setAlpha(0.8F);
        }
        catch (NullPointerException e){
            Log.d(LOG_TAG,"Failed to set alpha!");
        }
//        try {
//            tvTimer.setAlpha(0.4F);
//        }
//        catch (NullPointerException e){
//            Log.d(LOG_TAG,"Failed to set alpha!");
//        }
//        try {
//            tvQuestionNumber.setAlpha(0.4F);
//        }
//        catch (NullPointerException e){
//            Log.d(LOG_TAG,"Failed to set alpha!");
//        }

        b1 = (Button) findViewById(R.id.btn_1);
        b2 = (Button) findViewById(R.id.btn_2);
        b3 = (Button) findViewById(R.id.btn_3);
        b4 = (Button) findViewById(R.id.btn_4);
        setRoboto(tvGame, tvCounterBack, b1, b2, b3, b4);
    }

    private void initializeComponents(){
        q = new Question();

        mTouchHandler = new TouchHandler(b1,b2,b3,b4);
        b1.setOnTouchListener(mTouchHandler);
        b2.setOnTouchListener(mTouchHandler);
        b3.setOnTouchListener(mTouchHandler);
        b4.setOnTouchListener(mTouchHandler);

        scoreCorrect = 0;
        scoreIncorrect = 0;

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        mMediaPlayerRight = MediaPlayer.create(this, R.raw.correct_answer);
        mMediaPlayerWrong = MediaPlayer.create(this, R.raw.incorrect_answer);

        numberOfQuestions = sharedPreferences.getInt(numberPickerKey,length) * 5 + 5;
        selectedNumberOfQuestions = sharedPreferences.getInt(numberPickerKey,length) * 5 + 5;
        startTime = System.currentTimeMillis() + 3000;
    }

    private void generateRandomNumber() {
        generatorThread = new GeneratorThread(this, length, numberOfQuestions--,numbers);
        generatorThread.start();
        try {
            generatorThread.join();
        } catch (InterruptedException e) {
            Log.e(LOG_TAG, "Failed to generate a random number");
        }
    }

    private void displayNewQuestion() {
        generateRandomNumber();

        // -- Set the questionIndex with the generated number --------
        int questionIndex = generatorThread.getGeneratedNumber();
        Log.e(LOG_TAG, String.valueOf(questionIndex));
        //------------------------------------------------------------

        //if questionIndex = -1 -> no more question ----------------------
        if (questionIndex != -1) {
            // -- Generate that question ---------------------------------
            try {
                questionHelper.generateQuestion(q, questionIndex);
                Log.d(LOG_TAG, String.valueOf(questionIndex));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //------------------------------------------------------------

            // -- Displaying that question -------------------------------
            Message msg;
            msg = myOwnHandler.obtainMessage(UNLOCK_BUTTONS);
            handler.sendMessage(msg);
            questionHelper.displayQuestion(q, tvGame, b1, b2, b3, b4);
            updateCurrentQuestion(tvQuestionNumber, currentQuestion++, selectedNumberOfQuestions);
            //------------------------------------------------------------
        } else {
            Intent i = new Intent(this, GameOverDialog.class);
            i.putExtra(SEND_SCORE_CORRECT, scoreCorrect);
            i.putExtra(SEND_SCORE_INCORRECT, scoreIncorrect);
            i.putExtra(SEND_TIME, tvTimer.getText().toString());
            i.putExtra(SEND_NUMBER_OF_QUESTIONS,selectedNumberOfQuestions);

            try{
                mLinearLayout.setAlpha(0.5F);
            }catch (NullPointerException e){
                Log.e(LOG_TAG,"Alpha animation cannot be applied!");
            }

            StatisticDbHelper mDbHelper = new StatisticDbHelper(getApplicationContext());
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            double percentage;
            percentage = ((double)scoreCorrect / selectedNumberOfQuestions) * 100;

            ContentValues values = new ContentValues();
            values.put(StatisticContract.StatisticEntry.COLUMN_NAME_NUMBER_OF_QUESTIONS,selectedNumberOfQuestions);
            values.put(StatisticContract.StatisticEntry.COLUMN_NAME_TIME,String.valueOf(tvTimer.getText().toString().substring(4)));
            values.put(StatisticContract.StatisticEntry.COLUMN_NAME_CORRECT,scoreCorrect);
            values.put(StatisticContract.StatisticEntry.COLUMN_NAME_INCORRECT,scoreIncorrect);
            values.put(StatisticContract.StatisticEntry.COLUMN_NAME_PERCENTAGE,new DecimalFormat("#.##").format(percentage));

            db.insert(
                    StatisticContract.StatisticEntry.TABLE_NAME,
                    null,
                    values);

            startActivity(i);
        }
    }

    private void playRightSong() {
        Message msg;
        msg = handler.obtainMessage(PLAY_RIGHT_SONG);
        myOwnHandler.sendMessage(msg);
    }

    private void playWrongSong() {
        Message msg;
        msg = handler.obtainMessage(PLAY_WRONG_SONG);
        myOwnHandler.sendMessage(msg);
    }

    private void waitAndDisplay(int isCorrect, int btnIndex) {
        //Block buttons
        Message msg;
        msg = myOwnHandler.obtainMessage(BLOCK_BUTTONS);
        myOwnHandler.sendMessage(msg);
        msg = null;

        //Change button color
        msg = myOwnHandler.obtainMessage(CHANGE_BUTTON_COLOR);
        msg.arg1 = isCorrect;
        msg.arg2 = btnIndex;
        myOwnHandler.sendMessage(msg);

        msg = myOwnHandler.obtainMessage(CHANGE_BUTTON_COLOR);
        msg.arg1 = 3;
        msg.arg2 = btnIndex;
        myOwnHandler.sendMessageDelayed(msg, 1000);

        msg = myOwnHandler.obtainMessage(UNLOCK_BUTTONS);
        myOwnHandler.sendMessageDelayed(msg, 1000);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                displayNewQuestion();
            }
        }, 1000);
    }

    private void setRoboto(TextView tv1, TextView tv2, Button b1, Button b2, Button b3, Button b4) {
        Typeface Roboto = Typeface.createFromAsset(getApplicationContext().getAssets(), "RobotoCondensed-Bold.ttf");
        Typeface RobotoLight = Typeface.createFromAsset(getApplicationContext().getAssets(), "Roboto-Light.ttf");

        b1.setTypeface(RobotoLight);
        b2.setTypeface(RobotoLight);
        b3.setTypeface(RobotoLight);
        b4.setTypeface(RobotoLight);
        tv1.setTypeface(Roboto);
        tv2.setTypeface(RobotoLight);
    }

    public void sleepForOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class HandleQuiz implements Runnable {
        final Handler handler;

        private HandleQuiz(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            Message msg;
            for (int i = 3; i >= 0; i--) {
                msg = handler.obtainMessage(COUNT_BACK);
                msg.arg1 = i;
                handler.sendMessage(msg);
                sleepForOneSecond();
            }
        }
    }

    private static class MyOwnHandler extends Handler {

        Context context;
        Handler handler;

        private MyOwnHandler(Context context, Handler handler) {
            this.context = context;
            this.handler = handler;
        }

        @Override
        public void handleMessage(Message msg) {
            Runnable timerRunnable = new Runnable() {

                @Override
                public void run() {
                    long millis = System.currentTimeMillis() - startTime;
                    int seconds = (int) (millis / 1000);
                    int minutes = seconds / 60;
                    seconds = seconds % 60;

                    StringBuilder s = new StringBuilder();
                    s.append(context.getResources().getString(R.string.tv_timer));
                    s.append(" ");
                    s.append(String.format("%d:%02d", minutes, seconds));
                    tvTimer.setText(s);
                    handler.post(this);
                }
            };

            switch (msg.what) {
                case COUNT_BACK:
                    if (msg.arg1 == 0) {
                        int padding = convertToPixels(context, 16);
                        mLinearLayout.setPadding(padding, padding, padding, padding);
                        tvCounterBack.setVisibility(View.GONE);
                        handler.post(timerRunnable);
                    } else
                        tvCounterBack.setText(String.valueOf(msg.arg1));
                    break;
                case BLOCK_BUTTONS:
                    b1.setEnabled(false);
                    b2.setEnabled(false);
                    b3.setEnabled(false);
                    b4.setEnabled(false);
                    break;
                case UNLOCK_BUTTONS:
                    b1.setEnabled(true);
                    b2.setEnabled(true);
                    b3.setEnabled(true);
                    b4.setEnabled(true);
                    break;
                case PLAY_RIGHT_SONG:
                    mMediaPlayerRight.start();
                    break;
                case PLAY_WRONG_SONG:
                    mMediaPlayerWrong.start();
                    break;
                case CHANGE_BUTTON_COLOR:
                    if (msg.arg1 == 0) {
                        switch (msg.arg2) {
                            case 1:
                                b1.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_incorrect_answer));
                                break;
                            case 2:
                                b2.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_incorrect_answer));
                                break;
                            case 3:
                                b3.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_incorrect_answer));
                                break;
                            case 4:
                                b4.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_incorrect_answer));
                                break;
                        }

                    } else if (msg.arg1 == 1) {
                        switch (msg.arg2) {
                            case 1:
                                b1.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_correct_answer));
                                break;
                            case 2:
                                b2.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_correct_answer));
                                break;
                            case 3:
                                b3.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_correct_answer));
                                break;
                            case 4:
                                b4.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_correct_answer));
                                break;
                        }
                    } else {
                        switch (msg.arg2) {
                            case 1:
                                b1.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_game_color));
                                break;
                            case 2:
                                b2.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_game_color));
                                break;
                            case 3:
                                b3.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_game_color));
                                break;
                            case 4:
                                b4.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_game_color));
                                break;
                        }
                    }
            }
        }
    }

    public static int convertToPixels(Context context, int dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static void vibrate(Context context){
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, 300, 100, 300};
        vibrator.vibrate(pattern,-1);
    }

    private static void updateCurrentQuestion(TextView tv, int index, int selectedNumberOfQuestions) {
        if(!(index > selectedNumberOfQuestions)) {
            StringBuilder s = new StringBuilder();
            s.append(String.valueOf(index));
            s.append(" / ");
            s.append(String.valueOf(selectedNumberOfQuestions));
            tv.setText(s);
        }
    }
}