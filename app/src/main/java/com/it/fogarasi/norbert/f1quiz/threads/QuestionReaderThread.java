package com.it.fogarasi.norbert.f1quiz.threads;

import android.content.Context;
import android.util.Log;

import com.it.fogarasi.norbert.f1quiz.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QuestionReaderThread extends Thread implements Runnable {

    private static final String LOG_TAG = "FNorbert";
    private Question q;
    private Context context;
    private JSONObject jsonObject;
    private JSONArray jsonArray;

    public QuestionReaderThread(Context context, Question q) {
        this.context = context;
        this.q = q;
    }

    @Override
    public void run() {
        try {
            jsonObject = new JSONObject(q.readQuestions(context));
            jsonArray = jsonObject.getJSONArray("questions");
        }
        catch (JSONException e){
            Log.d(LOG_TAG,"Failed to read the questions!");
        }
    }

    public JSONObject getJsonObject(){
        return jsonObject;
    }

    public JSONArray getJsonArray(){
        return jsonArray;
    }
}
