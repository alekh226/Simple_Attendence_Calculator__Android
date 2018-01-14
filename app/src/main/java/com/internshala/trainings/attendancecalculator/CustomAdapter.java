package com.internshala.trainings.attendancecalculator;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

class CustomAdapter extends ArrayAdapter<String>{
    ArrayList<Model> studentList;
    Context mContext;

    public CustomAdapter(Context context, ArrayList<Model> list) {
        super(context,R.layout.custom_row);
        this.studentList=list;
        this.mContext=context;
    }
    @Override
    public int getCount() {
        return studentList.size();
    }

   @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Model model =studentList.get(position);
        LayoutInflater myCustomInflater = LayoutInflater.from(getContext());
        View customView = myCustomInflater.inflate(R.layout.custom_row, parent, false);

        //String singleFoodItem = getItem(position);
        TextView itemText = (TextView) customView.findViewById(R.id.subjectName);

        itemText.setText(model.getSubName());


       Button presentPlus =(Button) customView.findViewById(R.id.buttonPresentPlus);
       Button presentMinus =(Button) customView.findViewById(R.id.buttonPresentMinus);
       Button absentPlus =(Button) customView.findViewById(R.id.buttonAbsentPlus);
       Button  absentMinus =(Button) customView.findViewById(R.id.buttonAbsentMinus);
        final TextView presentCount = (TextView) customView.findViewById(R.id.presentCount);
       final TextView absentCount = (TextView) customView.findViewById(R.id.absentCount);
        presentPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ik=model.getCountPresent()+1;
                presentCount.setText(""+ik);
                model.setCountPresent(ik);
            }
        });
       presentMinus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               int ik=model.getCountPresent()-1;
               presentCount.setText(""+ik);
               model.setCountPresent(ik);
           }
       });
       absentPlus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               int ik=model.getCountAbsent()+1;
               absentCount.setText(""+ik);
               model.setCountAbsent(ik);
           }
       });
       absentMinus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               int ik=model.getCountAbsent()-1;
               absentCount.setText(""+ik);
               model.setCountAbsent(ik);
           }
       });
        return customView;
    }
}