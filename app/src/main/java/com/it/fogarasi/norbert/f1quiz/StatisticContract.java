package com.it.fogarasi.norbert.f1quiz;

import android.provider.BaseColumns;

public final class StatisticContract {
    public StatisticContract() {}

    public static abstract class StatisticEntry implements BaseColumns {
        public static final String TABLE_NAME = "statistic";
        public static final String COLUMN_NAME_NUMBER_OF_QUESTIONS = "questions";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_CORRECT = "correct";
        public static final String COLUMN_NAME_INCORRECT = "incorrect";
        public static final String COLUMN_NAME_PERCENTAGE = "percentage";
    }

}
