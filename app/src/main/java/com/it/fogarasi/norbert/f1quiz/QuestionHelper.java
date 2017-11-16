package com.it.fogarasi.norbert.f1quiz;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class QuestionHelper {

    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private Question q;

    public QuestionHelper(Question q,JSONObject jsonObject,JSONArray jsonArray){
        this.q = q;
        this.jsonObject = jsonObject;
        this.jsonArray = jsonArray;
    }

    public void generateQuestion(Question q,int index) throws JSONException{
        JSONObject data = jsonArray.getJSONObject(index);
        q.setQuestion(data.getString("question"));
        q.setAnswA(data.getString("answA"));
        q.setAnswB(data.getString("answB"));
        q.setAnswC(data.getString("answC"));
        q.setAnswD(data.getString("answD"));
        q.setCorrectAnswer(data.getString("correctAnsw"));
    }

    public void displayQuestion(Question q,TextView tv,Button b1,Button b2,Button b3,Button b4){
        tv.setText(q.getQuestion());
        b1.setText(q.getAnswA());
        b2.setText(q.getAnswB());
        b3.setText(q.getAnswC());
        b4.setText(q.getAnswD());
    }

    public boolean verifyQuestion(Question q,String answer){
        return q.getCorrectAnswer().equals(answer);
    }
}
