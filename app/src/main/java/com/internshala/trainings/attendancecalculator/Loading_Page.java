package com.internshala.trainings.attendancecalculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
/*public class Loading_Page extends  AppCompatActivity  {
   @Override
   public void onCreate(Bundle savedInstanceState)
   {
       super.onCreate(savedInstanceState);
       requestWindowFeature(Window.FEATURE_NO_TITLE);
       setContentView(R.layout.splash);


       new CountDownTimer(5000,1000){
           @Override
           public void onTick(long millisUntilFinished){}

           @Override
           public void onFinish(){
               //set the new Content of your activity
               Loading_Page.this.setContentView(R.layout.activity_loading__page);
           }
       }.start();
       setContentView(R.layout.activity_loading__page);
       final EditText etUsername = (EditText) findViewById(R.id.userName);
       final EditText etPassword = (EditText) findViewById(R.id.password);
       final Button bLogin = (Button) findViewById(R.id.logInb);

       bLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final String user_name = etUsername.getText().toString();
               final String password = etPassword.getText().toString();

               // Response received from the server
               Response.Listener<String> responseListener = new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       try {
                           JSONObject jsonResponse = new JSONObject(response);
                           boolean success = jsonResponse.getBoolean("success");

                           if (success) {
                               String name = jsonResponse.getString("user_name");
                               Toast.makeText(getApplicationContext(),"welcome "+name,Toast.LENGTH_LONG).show();
                           } else {
                               AlertDialog.Builder builder = new AlertDialog.Builder(Loading_Page.this);
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

               LoginRequest loginRequest = new LoginRequest(user_name, password, responseListener);
               RequestQueue queue = Volley.newRequestQueue(Loading_Page.this);
               queue.add(loginRequest);
           }
       });

   }

    public void NewUserClicked(View v){
       Intent i=new Intent(Loading_Page.this,register.class);
        Loading_Page.this.startActivity(i);

    }

    public void ForgetPasswordClicked(View v){
        Intent i=new Intent(Loading_Page.this,forgetPassword.class);
        Loading_Page.this.startActivity(i);
    }
}*/


public class Loading_Page extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading__page);

        final EditText etUsername = (EditText) findViewById(R.id.userName);
        final EditText etPassword = (EditText) findViewById(R.id.password);
        final Button bLogin = (Button) findViewById(R.id.logInb);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user_name = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            String email=jsonResponse.getString("email");
                            int user_id=jsonResponse.getInt("user_id");


                            if (success) {
                                Intent ie=new Intent(Loading_Page.this,HomeActivity.class);
                                Loading_Page.this.startActivity(ie);
                                Model model1=new Model();

                                MyDBHandler dbhandler =new MyDBHandler(Loading_Page.this,null,null,1);
                                model1.setFlag(1);
                                model1.setEmail(email);
                                model1.setUserID(user_id);
                                model1.setUserName(user_name);
                                dbhandler.addMember(model1);
                               /* AlertDialog.Builder builder = new AlertDialog.Builder(Loading_Page.this);
                                builder.setMessage("Login successful")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();*/
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Loading_Page.this);
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

                LoginRequest loginRequest = new LoginRequest(user_name, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Loading_Page.this);
                queue.add(loginRequest);
            }
        });
    }

    public void NewUserClicked(View v){
        Intent i=new Intent(Loading_Page.this,register.class);
        Loading_Page.this.startActivity(i);

    }

    public void ForgetPasswordClicked(View v){
        Intent i=new Intent(Loading_Page.this,ForgetEmailActivity.class);
        Loading_Page.this.startActivity(i);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("no", null).show();
    }
}
