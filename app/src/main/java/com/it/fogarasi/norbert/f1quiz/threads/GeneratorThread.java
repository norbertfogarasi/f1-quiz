package com.it.fogarasi.norbert.f1quiz.threads;

import android.content.Context;

import java.util.Random;

public class GeneratorThread extends Thread implements Runnable {

    private int generatedNumber;
    private int length,numberOfQuestions;
    int[] numbers = new int[length];
    Random r;
    Context context;

    //Constructor
    public GeneratorThread(Context context, int length, int numberOfQuestions,int[] numbers) {
        this.context = context;
        this.length = length;
        this.numbers = numbers;
        this.numberOfQuestions = numberOfQuestions;
    }

    @Override
    public void run() {
        r = new Random();
        if (ifFull(numbers) || numberOfQuestions == 0) {
            setToZero(numbers);
            setGeneratedNumber(-1);
        } else {
            int i = r.nextInt(length);
            while (numbers[i] != 0)
                i = r.nextInt(length);
            numbers[i]++;
            numberOfQuestions--;
            setGeneratedNumber(i);
        }
    }

    public int getGeneratedNumber() {
        return generatedNumber;
    }

    private void setGeneratedNumber(int number) {
        this.generatedNumber = number;
    }

    private boolean ifFull(int[] numbers) {
        for(int i = 0;i<numbers.length; i++)
            if(numbers[i] == 0) return false;
        return true;
    }

    private void setToZero(int[] numbers) {
        for (int i : numbers) {
            numbers[i] = 0;
        }
    }
}
