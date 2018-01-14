package com.internshala.trainings.attendancecalculator;
/*
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
*/








import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private String finalDate;
    private TextView mDisplayDate;
    private String m_Text = "";
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
            getSubject();
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


    public void addSubjectClicked(View v){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                addSubject(m_Text);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();






    }

    public void addSubject(final String subjectName){
        final MyDBHandler dbhandler1=new MyDBHandler(HomeActivity.this,null,null,1);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        dbhandler1.addSubjectdb(subjectName);
                        Toast.makeText(HomeActivity.this,"Subject added successfully",Toast.LENGTH_LONG).show();
                        getSubject();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                        builder.setMessage("Login Failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        addSubjectRequest addsubjectRequest = new addSubjectRequest(dbhandler1.getUserName()+"_"+dbhandler1.getUserId(), subjectName, responseListener);
        RequestQueue queue = Volley.newRequestQueue(HomeActivity.this);
        queue.add(addsubjectRequest);
    }


    public void getSubject(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    boolean success = jsonResponse.getBoolean("success");
                   JSONArray subjectName=jsonResponse.getJSONArray("subject");
                    ArrayList<Model> subject__name=new ArrayList <Model>();

                    if (success) {
                        int count = subjectName.length();
                        if(count>4){
                        for(int i = 4;  i< count; i++)
                        { Model newmodel =new Model();
                            try {
                                //JSONObject jsonObject = subjectName.getJSONObject(i);
                                newmodel.setSubName(subjectName.getString(i));
                                subject__name.add(newmodel);
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                       ListAdapter customListAdapter = new CustomAdapter(HomeActivity.this,subject__name);// Pass the food arrary to the constructor.
                        ListView customListView = (ListView) findViewById(R.id.custom_ListView);
                        customListView.setAdapter(customListAdapter);
                        Toast.makeText(HomeActivity.this,"List updated successfully",Toast.LENGTH_LONG).show();}
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                        builder.setMessage("Loading Failed")
                                .setNegativeButton("Retry",null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        MyDBHandler dbhandler1=new MyDBHandler(HomeActivity.this,null,null,1);
        getSubjectListRequest getsubjectRequest = new getSubjectListRequest(dbhandler1.getUserName()+"_"+dbhandler1.getUserId(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(HomeActivity.this);
        queue.add(getsubjectRequest);
    }



    public class addSubjectRequest extends StringRequest {
        private static final String LOGIN_REQUEST_URL = "http://alekhkumar226.000webhostapp.com/addSubject.php";
        private Map<String, String> params;

        public addSubjectRequest(String table_name, String subject_name, Response.Listener<String> listener) {
            super(Method.POST, LOGIN_REQUEST_URL, listener, null);
            params = new HashMap<>();
            params.put("table_name", table_name);
            params.put("subject_name", subject_name);
        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }}



    public class getSubjectListRequest extends StringRequest {
        private static final String LOGIN_REQUEST_URL = "http://alekhkumar226.000webhostapp.com/getSubjectList.php";
        private Map<String, String> params;

        public getSubjectListRequest(String table_name, Response.Listener<String> listener) {
            super(Method.POST, LOGIN_REQUEST_URL, listener, null);
            params = new HashMap<>();
            params.put("table_name", table_name);

        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }}


    }
