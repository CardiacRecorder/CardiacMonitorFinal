package com.example.cardiacmonitor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


@RunWith(RobolectricTestRunner.class)
public class MyDatabaseHelperTest {
    /**
     * this method test if a value is inserted in database properly
     */
   @Test
    public void testInsertData()
   {
     MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper((RuntimeEnvironment.application));

     String sys = "120";
     String dias = "60";
     String pressure_stat = "normal";
     String pul = "65";
     String pul_stat = "normal";
     Calendar calendar = Calendar.getInstance();

     String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
     SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
     String time = dateFormat.format(calendar.getTime());

     long i = myDatabaseHelper.insertData(sys,dias,pressure_stat,pul,pul_stat,date,time,"sitting");
       assertTrue(myDatabaseHelper.checkDataExistsOrNot(i));

      myDatabaseHelper.close();


   }

    /**
     * this method is to test to test the if the record is updated properly
     */
    @Test
    public void testUpdateData() {
        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(RuntimeEnvironment.application);

        String sys = "120";
        String dias = "60";
        String pressure_stat = "normal";
        String pul = "65";
        String pul_stat = "normal";
        Calendar calendar = Calendar.getInstance();

        String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        String time = dateFormat.format(calendar.getTime());
        long i = myDatabaseHelper.insertData(sys,dias,pressure_stat,pul,pul_stat,date,time,"sitting");


        myDatabaseHelper.updateData(Long.toString(myDatabaseHelper.insertData(sys,dias,pressure_stat,pul,pul_stat,date,time,"sitting")),"100","65","abnormal","65","normal",date,time,"lying");



        assertFalse(myDatabaseHelper.checkContent(Long.toString(myDatabaseHelper.insertData(sys,dias,pressure_stat,pul,pul_stat,date,time,"sitting")),"100","65","abnormal","65","normal",date,time,"lying"));

        myDatabaseHelper.close();
    }

    /**
     * this method is to test to test the if the record is deleted properly from database
     */
    @Test
    public void DeleteData()
    {
        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(RuntimeEnvironment.application);

        String sys = "120";
        String dias = "60";
        String pressure_stat = "normal";
        String pul = "65";
        String pul_stat = "normal";
        Calendar calendar = Calendar.getInstance();

        String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        String time = dateFormat.format(calendar.getTime());
        long i = myDatabaseHelper.insertData(sys,dias,pressure_stat,pul,pul_stat,date,time,"sitting");

        myDatabaseHelper.deleteData(Long.toString(i));
        assertFalse(myDatabaseHelper.checkDataExistsOrNot(i));
        myDatabaseHelper.close();
    }



}