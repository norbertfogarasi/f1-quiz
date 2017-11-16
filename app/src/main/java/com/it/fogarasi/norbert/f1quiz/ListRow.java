package com.it.fogarasi.norbert.f1quiz;

public class ListRow {

    public ListRow(String _id, String questions, String time, String correct, String incorrect, String percentage) {
        this._id = _id;
        this.questions = questions;
        this.time = time;
        this.correct = correct;
        this.incorrect = incorrect;
        this.percentage = percentage;
    }

    public ListRow() {}

    private String _id,questions,time,correct,incorrect,percentage;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(String incorrect) {
        this.incorrect = incorrect;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

}
