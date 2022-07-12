package com.example.cardiacmonitor;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * this class mainly implemets the user interface of recordsactivity
 * this class also works as an inteface between records and database helper
 * this class uses instance of record and record list classes
 */
public class RecordsActivity extends AppCompatActivity {

    MyDatabaseHelper myDatabaseHelper;
    SimpleCursorAdapter simpleCursorAdapter;

    ListView listView;
    TextView no_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        myDatabaseHelper = new MyDatabaseHelper(RecordsActivity.this);//calling constructor for mydatabase helper class
        SQLiteDatabase sqLiteDatabase =  myDatabaseHelper.getWritableDatabase();//this opens a sqlite database that will be used for reading and writing records datas

        listView = findViewById(R.id.list_view);
        no_text = findViewById(R.id.no_text);

        loadData();//This is used to read rows from a recordlist into database table at a very high speed
        /**
         * this implemets a setOnItemClickListener if we click on any item in record list
         * we can either delete a record or update it
         */
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

               Cursor cursor = (Cursor) simpleCursorAdapter.getItem(i);
               String _id = cursor.getString(0);

               // Toast.makeText(RecordsActivity.this,"id: "+_id,Toast.LENGTH_SHORT).show();


               AlertDialog.Builder builder = new AlertDialog.Builder(RecordsActivity.this);//this creates a builder for an alert dialog that uses the recordactivity alert dialog theme.
               /**
                * an action popup will appear containing an update and delete button
                */
               View view2 = getLayoutInflater().inflate(R.layout.action_popup_dialog, null);

               Button update, delete;

               update = view2.findViewById(R.id.update);
               delete = view2.findViewById(R.id.delete);

               builder.setView(view2);
               AlertDialog alertDialog = builder.create();//this returns an AlertDialog object with the arguments supplied to it
               alertDialog.setCanceledOnTouchOutside(false);//if we touch anything outside the alertdialogue it will cancel out
               /**
                * this implements setOnClickListener for update button
                */
               update.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

                       AlertDialog.Builder builder = new AlertDialog.Builder(RecordsActivity.this);

                       View view2 = getLayoutInflater().inflate(R.layout.popup_dialog, null);//a popup dialouge will appear with all of record fields

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

                       AlertDialog alertDialog1 = builder.create();
                       alertDialog1.setCanceledOnTouchOutside(false);/*if we touch anything outside of alertdialog popup,it will close*/
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

                               String pulse_status = "", pressure_status = "";

                               sys.setText("");
                               dias.setText("");
                               pulse.setText("");
                               date.setText("");
                               time.setText("");
                               comment.setText("");

                               if (TextUtils.isEmpty(sys_v))/*this checks if systol field is empty*/ {
                                   sys.setError("Required");
                                   return;
                               } else if (TextUtils.isEmpty(dias_v))/*this checks if diastol field is empty*/ {
                                   dias.setError("Required");
                                   return;
                               } else if (TextUtils.isEmpty(comments_v)) /*this checks if comment field is empty*/{
                                   comment.setError("Required");
                                   return;
                               } else if (TextUtils.isEmpty(pulse_v))/*this checks if pulse field is empty*/ {
                                   pulse.setError("Required");
                                   return;
                               }
                               /*all these conditions are used for indicating the flag based on ones's pulse rate*/
                               if (Integer.parseInt(pulse_v) >= 60 && Integer.parseInt(pulse_v) <= 80) {
                                   pulse_status += "normal";
                               } else {
                                   pulse_status += "exceptional";
                               }
                               /*all these conditions are used for indicating the flag based on ones's blood rate*/
                               int x = Integer.parseInt(sys_v);
                               int y = Integer.parseInt(dias_v);

                               if ((x >= 90 && x <= 140) && (y >= 60 && y <= 90)) {
                                   pressure_status += "normal";
                               } else if (x > 140 || y > 90) {
                                   pressure_status += "high";
                               } else if (x < 90 || y < 60) {
                                   pressure_status += "low";
                               }

                               /*invoking insertdata method of  myDatabaseHelper class which is used to insert an entry into database*/
                               boolean id = myDatabaseHelper.updateData(_id, sys_v, dias_v, pressure_status, pulse_v, pulse_status, date_v, time_v, comments_v);

                               /*if successfully inserted data in databse the method will return true*/
                               if (id) {
                                   Toast.makeText(RecordsActivity.this, "data is updated", Toast.LENGTH_SHORT).show();

                               } else {
                                   Toast.makeText(RecordsActivity.this, "data is not updated", Toast.LENGTH_SHORT).show();
                               }

                               loadData();

                               alertDialog.dismiss();
                               alertDialog1.dismiss();


                           }
                       });
                       /**
                        * if we click on no button ,alert dialog popup will be dismissed
                        */
                       no.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               //Toast.makeText(getActivity(),"no",Toast.LENGTH_SHORT).show();
                               alertDialog1.dismiss();
                           }
                       });

                       alertDialog1.show();

                   }
               });
               /**
                * this implements setOnClickListener for update button
                */
               delete.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                         long f = myDatabaseHelper.deleteData(_id);
                         /*
                           if record is successfully deleted from database daletedata method will return 1,else 0
                          */
                         if(f>0)
                         {
                             Toast.makeText(RecordsActivity.this,"Data is deleted",Toast.LENGTH_SHORT).show();
                         }
                         else
                         {
                             Toast.makeText(RecordsActivity.this,"Data is not deleted",Toast.LENGTH_SHORT).show();

                         }

                         alertDialog.dismiss();
                         loadData();
                   }
               });

               alertDialog.show();

           }
       });



    }

    /**
     * this method fetch all records from database show them in record list
     */
    public void loadData()
    {
        simpleCursorAdapter = myDatabaseHelper.populateListViewFromDB();
        listView.setAdapter(simpleCursorAdapter);

       // Toast.makeText(RecordsActivity.this,"::"+listView.getCount(),Toast.LENGTH_SHORT).show();
        /*if there is no record ,then no records will be shown in screen,
        else list view will be shown
         */
        if(listView.getCount()<1)
        {
            no_text.setText("No Records");
            no_text.setVisibility(View.VISIBLE);//no record text will be shown
            listView.setVisibility(View.GONE);//list will be hidden
        }
        else
        {
            no_text.setVisibility(View.GONE);//no record text will be hidden
            listView.setVisibility(View.VISIBLE);//list will be shown
        }
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