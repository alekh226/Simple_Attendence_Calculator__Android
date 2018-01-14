package com.internshala.trainings.attendancecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;



public class ClenderActivity extends AppCompatActivity {
    private  CalendarView cv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.calender_layout);
        cv =(CalendarView) findViewById(R.id.calendarView);

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
              String date = year +"/" + (month+1) +"/"+dayOfMonth;
                Intent i =new Intent(ClenderActivity.this,HomeActivity.class);
                i.putExtra("datee",date);
                ClenderActivity.this.startActivity(i);
            }
        });

    }
}


/*
package com.internshala.trainings.attendancecalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
         final TextView datee =(TextView)findViewById(R.id.textView222);
         datee.setText(getDateTime());



        datee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this,ClenderActivity.class);
                HomeActivity.this.startActivity(i);
                Intent intent =getIntent();
                 String dateee = intent.getStringExtra("datee");
                datee.setText(dateee);
            }
        });

    }


    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}









import android.app.DatePickerDialog;
        import android.graphics.Color;
        import android.graphics.drawable.ColorDrawable;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.DatePicker;
        import android.widget.TextView;

        import java.text.DateFormat;
        import java.text.Format;
        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.GregorianCalendar;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private String finalDate;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mDisplayDate = (TextView) findViewById(R.id.textView222);

        finalDate=getDateFormat(getDateTime());
        mDisplayDate.setText(getDateFormat(getDateTime()));
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        HomeActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: MM/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                finalDate=getDateFormat(date);
                mDisplayDate.setText(getDateFormat(date));
            }
        };
    }

    private String getDateFormat(String date){
        SimpleDateFormat spf=new SimpleDateFormat("MM/dd/yyyy");
        Date newDate= null;
        try {
            newDate = spf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf= new SimpleDateFormat("dd-MMM-yyyy");
        return spf.format(newDate);
    }
    private String getDateTime() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return ((month +1)+ "/" + day + "/" + year);
    }


    public void plusDateButtonClicked(View v){

        try {
            finalDate=plusminusDate(finalDate,1);
            mDisplayDate.setText(finalDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void minusDateButtonClicked(View v){

        try {
            finalDate=plusminusDate(finalDate,-1);
            mDisplayDate.setText(finalDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public String plusminusDate( String dateNow,int key ) throws ParseException {

        final String sdate = dateNow;
        final SimpleDateFormat df = new SimpleDateFormat( "dd-MMM-yyyy" );
        final Date date = df.parse( sdate ); // conversion from String
        final java.util.Calendar cal = GregorianCalendar.getInstance();
        cal.setTime( date );
        if(key==1)
            cal.add( GregorianCalendar.DATE, 1 );
        else if(key==-1)
            cal.add( GregorianCalendar.DATE, -1 );// date manipulation
        return  df.format( cal.getTime() ); // conversion to String
    }

}

 */