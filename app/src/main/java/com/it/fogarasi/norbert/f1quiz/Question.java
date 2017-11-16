package com.it.fogarasi.norbert.f1quiz;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class Question {

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswA() {
        return answA;
    }

    public void setAnswA(String answA) {
        this.answA = answA;
    }

    public String getAnswB() {
        return answB;
    }

    public void setAnswB(String answB) {
        this.answB = answB;
    }

    public String getAnswC() {
        return answC;
    }

    public void setAnswC(String answC) {
        this.answC = answC;
    }

    public String getAnswD() {
        return answD;
    }

    public void setAnswD(String answD) {
        this.answD = answD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    private String question;
    private String answA;
    private String answB;
    private String answC;
    private String answD;
    private String correctAnswer;

    public Question(){
    }

    public Question(String question,String answA,String answB,String answC,String answD,String correctAnswer){
        this.question = question;
        this.answA = answA;
        this.answB = answB;
        this.answC = answC;
        this.answD = answD;
        this.correctAnswer = correctAnswer;
    }

    public String readQuestions(Context context) {
        String json;
        try {
            InputStream is = context.getAssets().open("myQuestions.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}