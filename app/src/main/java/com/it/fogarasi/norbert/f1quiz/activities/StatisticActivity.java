package com.it.fogarasi.norbert.f1quiz.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.it.fogarasi.norbert.f1quiz.CustomAdapter;
import com.it.fogarasi.norbert.f1quiz.ListRow;
import com.it.fogarasi.norbert.f1quiz.R;
import com.it.fogarasi.norbert.f1quiz.StatisticContract;
import com.it.fogarasi.norbert.f1quiz.StatisticDbHelper;
import java.util.ArrayList;

public class StatisticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_statistic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ListView lv = (ListView) findViewById(R.id.listview);
        ArrayList<ListRow> arrayList = new ArrayList<>();

        StatisticDbHelper mDbHelper = new StatisticDbHelper(getApplicationContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] proiection = {
                StatisticContract.StatisticEntry._ID,
                StatisticContract.StatisticEntry.COLUMN_NAME_NUMBER_OF_QUESTIONS,
                StatisticContract.StatisticEntry.COLUMN_NAME_TIME,
                StatisticContract.StatisticEntry.COLUMN_NAME_CORRECT,
                StatisticContract.StatisticEntry.COLUMN_NAME_INCORRECT,
                StatisticContract.StatisticEntry.COLUMN_NAME_PERCENTAGE
        };

        Cursor c = db.query(
                StatisticContract.StatisticEntry.TABLE_NAME,
                proiection, null, null, null, null, null);

        c.moveToFirst();

        while (!c.isAfterLast()) {
            ListRow row = new ListRow();
            row.set_id(c.getString(c.getColumnIndexOrThrow(StatisticContract.StatisticEntry._ID)));
            row.setQuestions(c.getString(c.getColumnIndexOrThrow(StatisticContract.StatisticEntry.COLUMN_NAME_NUMBER_OF_QUESTIONS)));
            row.setTime(c.getString(c.getColumnIndexOrThrow(StatisticContract.StatisticEntry.COLUMN_NAME_TIME)));
            row.setCorrect(c.getString(c.getColumnIndexOrThrow(StatisticContract.StatisticEntry.COLUMN_NAME_CORRECT)));
            row.setIncorrect(c.getString(c.getColumnIndexOrThrow(StatisticContract.StatisticEntry.COLUMN_NAME_INCORRECT)));
            row.setPercentage(c.getString(c.getColumnIndexOrThrow(StatisticContract.StatisticEntry.COLUMN_NAME_PERCENTAGE)));
            arrayList.add(row);
            c.moveToNext();
        }
            CustomAdapter adapter = new CustomAdapter(this,arrayList);
            lv.setAdapter(adapter);

        if(adapter.isEmpty()){
            TextView mTextView = new TextView(this);
            mTextView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mTextView.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
            mTextView.setText(getResources().getString(R.string.tv_no_game));
        }
        c.close();
    }
}
