package com.example.cardiacmonitor;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.animation.ValueAnimator;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.cardiacmonitor.databinding.ActivityHomeBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A class created for home screen activity
 */
public class HomeActivity extends AppCompatActivity {


    ActivityHomeBinding binding;
    MyDatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(view);
        myDatabaseHelper = new MyDatabaseHelper(HomeActivity.this);
        SQLiteDatabase sqLiteDatabase =  myDatabaseHelper.getWritableDatabase();


        binding.progressCounter.setText("0");
        binding.status.setText("");

        /**
         *this method is used to add clicklistener event on add button
         */
        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);/*after clicking add button an alertdialog will pop up to make a new entry*/

                View view2 = getLayoutInflater().inflate(R.layout.popup_dialog,null);/*this reads popup_dialog xml to translate them in java code*/

                EditText sys = view2.findViewById(R.id.systolic);
                EditText dias = view2.findViewById(R.id.diastolic);
                EditText date = (EditText) view2.findViewById(R.id.date);
                EditText time = (EditText) view2.findViewById(R.id.time);
                EditText comment = (EditText) view2.findViewById(R.id.comments);
                EditText pulse = (EditText) view2.findViewById(R.id.pulse_rate);

                Button yes = (Button) view2.findViewById(R.id.yes_btn);
                Button no = (Button) view2.findViewById(R.id.no_btn);

                Calendar calendar = Calendar.getInstance();/* used to get a calendar using the current time zone and locale of the system*/

                Date currentDate = calendar.getTime();/*this returns a Date object that represents this Calendar's time value.*/
                String date_v = DateFormat.getDateInstance(DateFormat.FULL).format(currentDate);/*to get the date in given format*/
                date.setText(date_v);/*automatic filling up date in a record from system*/

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
                String time_v = simpleDateFormat.format(calendar.getTime());/*to get the time in given format*/
                time.setText(time_v);/*automatic filling up time in a record from system*/

                builder.setView(view2);

                AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);/*if we touch anything outside of alertdialog popup,it will close*/
                /**
                 * this click listener is used for action if we click on yes button
                 */
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String sys_v = sys.getText().toString();
                        String dias_v = dias.getText().toString();
                        String comments_v = comment.getText().toString();
                        String pulse_v = pulse.getText().toString();

                        String pulse_status="",pressure_status="";

                        sys.setText("");
                        dias.setText("");
                        pulse.setText("");
                        date.setText("");
                        time.setText("");
                        comment.setText("");

                        if(TextUtils.isEmpty(sys_v))/*this checks if systol field is empty*/
                        {
                            sys.setError("Required");
                            return;
                        }
                        else if(TextUtils.isEmpty(dias_v))/*this checks if diastol field is empty*/
                        {
                            dias.setError("Required");
                            return;
                        }
                        else if(TextUtils.isEmpty(comments_v))/*this checks if comment field is empty*/
                        {
                            comment.setError("Required");
                            return;
                        }
                        else if(TextUtils.isEmpty(pulse_v))/*this checks if pulse field is empty*/
                        {
                            pulse.setError("Required");
                            return;
                        }
                        /*all these conditions are used for indicating the flag based on ones's pulse rate*/
                        if(Integer.parseInt(pulse_v)>=60 && Integer.parseInt(pulse_v)<=80)
                        {
                            pulse_status+= "normal";
                        }
                        else
                        {
                            pulse_status+= "exceptional";
                        }
                        /*all these conditions are used for indicating the flag based on ones's blood pressure*/
                        int x = Integer.parseInt(sys_v);
                        int y = Integer.parseInt(dias_v);

                        if((x>=90 && x<=140) && (y>=60 && y<=90))
                        {
                            pressure_status+="normal";
                        }
                        else if(x>140 || y>90)
                        {
                            pressure_status+="high";
                        }
                        else if(x<90 || y<60)
                        {
                            pressure_status+="low";
                        }

                        /*invoking insertdata method of  myDatabaseHelper class which is used to insert an entry into database*/
                        long id =  myDatabaseHelper.insertData(sys_v,dias_v,pressure_status,pulse_v,pulse_status,date_v,time_v,comments_v);

                        /*if not successfully inserted data in databse the method will return -1*/
                        if(id==-1)
                        {
                            Toast.makeText(HomeActivity.this,"data is not saved",Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Toast.makeText(HomeActivity.this,"data saved",Toast.LENGTH_SHORT).show();
                        }

                        alertDialog.dismiss();

                        int v = Integer.parseInt(pulse_v);

                        if(v>150)/*this is used as limit of progress bar is 150*/
                        {
                            v=150;
                        }
                        /* progress bar is changed from 0 to given pulse rate*/
                        startAnimatedCounter(0,v);
                        /*all these conditions are used for indicating the flag based on ones's pulse rate*/
                        if(v>=60 && v<=80)
                        {
                            binding.status.setText("Normal");
                            YoYo.with(Techniques.Shake).duration(1000).repeat(1).playOn(binding.status);

                        }
                        else
                        {
                            YoYo.with(Techniques.Shake).duration(1000).repeat(1).playOn(binding.status);
                            binding.status.setText("Exceptional");
                        }


                    }
                });
                /**
                 * if we click on no button ,alert dialog popup will be dismissed
                 */
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(getActivity(),"no",Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();


            }
        });
        /**
         * this method implemets OnClickListener for record button
         */
        binding.records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,RecordsActivity.class));/*switch screen from home to records*/
            }
        });


    }

    /**
     *implement the progress bar to show pulse rate
     * @param s
     * @param e
     * @return void
     */
    public void startAnimatedCounter(int s,int e)
    {
        ValueAnimator animator = ValueAnimator.ofInt(s,e);
        animator.setDuration(2000);/* duration of progress bar transition*/
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                binding.progressCounter.setText(animator.getAnimatedValue().toString()+"");
                binding.progress.setProgress(Integer.parseInt(animator.getAnimatedValue().toString()));
            }
        });
        animator.start();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}