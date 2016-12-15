package com.example.login;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by LENOVO on 17-12-2015.
 */
public class CustomAdapter extends ArrayAdapter {
    private LayoutInflater inflater;

    public CustomAdapter(Context context, List<model> modelList) {
        super(context, R.layout.activity_model, R.id.textView, modelList);
        //Cache the LayoutInflate to avoid asking for a new one each time.
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        model model = (model) this.getItem(position);
        CheckBox checkBox;
        TextView textView;

        // Create a new row view
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_model, null);

            textView = (TextView) convertView.findViewById(R.id.textView);
            checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);

            convertView.setTag(new modelViewHolder(textView, checkBox));

            // If CheckBox is toggled, update the planet it is tagged with.
            checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    model models = (model) cb.getTag();
                    models.setChecked(cb.isChecked());
                }
            });
        }
        // Re-use existing row view
        else {

            modelViewHolder viewHolder = (modelViewHolder) convertView
                    .getTag();
            checkBox = viewHolder.getCheckBox();
            textView = viewHolder.getTextView();
        }

        checkBox.setTag(model);

        // Display planet data
        checkBox.setChecked(model.isChecked());
        textView.setText(model.getName());

        return convertView;
    }
}
