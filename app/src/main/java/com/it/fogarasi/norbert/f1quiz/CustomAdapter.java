package com.it.fogarasi.norbert.f1quiz;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.it.fogarasi.norbert.f1quiz.activities.StatisticActivity;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<ListRow> {
    public CustomAdapter(Context context, ArrayList<ListRow> rows) {
        super(context,0,rows);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ListRow row = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_row, parent, false);
        }
        // Lookup view for data population
        TextView tvId = (TextView) convertView.findViewById(R.id.row_id);
        TextView tvQuestions = (TextView) convertView.findViewById(R.id.row_questions);
        TextView tvTime = (TextView)convertView.findViewById(R.id.row_time);
        TextView tvCorrect = (TextView)convertView.findViewById(R.id.row_correct);
        TextView tvIncorrect = (TextView)convertView.findViewById(R.id.row_incorrect);
        TextView tvPercentage = (TextView)convertView.findViewById(R.id.row_percentage);

        //Set the background
        tvId.setBackground(ContextCompat.getDrawable(getContext(),R.color.oszlop_1_statisztika));
        tvQuestions.setBackground(ContextCompat.getDrawable(getContext(),R.color.oszlop_2_statisztika));
        tvTime.setBackground(ContextCompat.getDrawable(getContext(),R.color.oszlop_1_statisztika));
        tvCorrect.setBackground(ContextCompat.getDrawable(getContext(),R.color.oszlop_2_statisztika));
        tvIncorrect.setBackground(ContextCompat.getDrawable(getContext(),R.color.oszlop_1_statisztika));
        tvPercentage.setBackground(ContextCompat.getDrawable(getContext(),R.color.oszlop_2_statisztika));

        // Populate the data into the template view using the data object
        tvId.setText(row.get_id() + ".");
        tvQuestions.setText(row.getQuestions());
        tvTime.setText(row.getTime());
        tvCorrect.setText(row.getCorrect());
        tvIncorrect.setText(row.getIncorrect());
        tvPercentage.setText(row.getPercentage() + "%");
        // Return the completed view to render on screen
        return convertView;
    }
}
