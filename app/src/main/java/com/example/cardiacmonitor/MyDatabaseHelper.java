package com.example.cardiacmonitor;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME ="cardiac.db";
    private static final String TABLE_NAME ="cardiac_details";
    private static final String  ID ="_id";
    private static final String SYSTOLIC ="systolic";
    private static final String DIASTOLIC ="diastolic";
    private static final String PRESURE_STATUS ="pressure_status";
    private static final String PULSE ="pulse";
    private static final String PULSE_STATUS ="pulse_status";
    private static final String DATE ="date";
    private static final String TIME ="time";
    private static final String COMMENTS ="comments";
    private static final String CREATE_TABLE ="CREATE TABLE "+TABLE_NAME+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+SYSTOLIC+" varchar,"+DIASTOLIC+" varchar,"+PRESURE_STATUS+" varchar,"+PULSE+" varchar,"+PULSE_STATUS+","+DATE+" varchar,"+TIME+" varchar,"+COMMENTS+" varchar(255));";
    private static final String SELECT_ALL = "SELECT * FROM cardiac_details WHERE _id = ?";
    private static final String UPDATE_DATA = "SELECT * FROM "+TABLE_NAME;
    private static final int VERSION_NUMBER = 1;
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;


    private Context context;


    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try {
            sqLiteDatabase.execSQL(CREATE_TABLE);
        }catch (Exception e)
        {
            Toast.makeText(context,"Exception : "+e,Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        try{
            Toast.makeText(context,"onUpgrade is Called",Toast.LENGTH_SHORT).show();
            sqLiteDatabase.execSQL(DROP_TABLE);
            onCreate(sqLiteDatabase);

        }catch (Exception e)
        {
            Toast.makeText(context,"Exception : "+e,Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * this method is called whenever we try to add a new record
     * @param systolic
     * @param diastolic
     * @param pre_stat
     * @param pulse
     * @param pul_stat
     * @param date
     * @param time
     * @param comments
     * @return id
     */
    public long insertData(String systolic,String diastolic,String pre_stat,String pulse,String pul_stat,String date,String time,String comments)
    {
        SQLiteDatabase sqLiteDatabase =  this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        /*the values are inserted on corresponding columns
         */
        contentValues.put(SYSTOLIC,systolic);
        contentValues.put(DIASTOLIC,diastolic);
        contentValues.put(PRESURE_STATUS,pre_stat);
        contentValues.put(PULSE,pulse);
        contentValues.put(PULSE_STATUS,pul_stat);
        contentValues.put(DATE,"Date: "+date);
        contentValues.put(TIME,"Time: "+time);
        contentValues.put(COMMENTS,"Comments: "+comments);
        /*id represents if data is inserted properly in database*/
        long id = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);

        return id;
    }

    /**
     * this method is called whenever we try to update an existing record
     * @param id
     * @param systolic
     * @param diastolic
     * @param pre_stat
     * @param pulse
     * @param pul_stat
     * @param date
     * @param time
     * @param comments
     * @return bool
     */
    public Boolean updateData(String id,String systolic,String diastolic,String pre_stat,String pulse,String pul_stat,String date,String time,String comments)
    {

       // Toast.makeText(context,"id: "+id,Toast.LENGTH_SHORT).show();
        SQLiteDatabase sqLiteDatabase =  this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        /*the values are inserted on corresponding columns
         */
        contentValues.put(ID,id);
        contentValues.put(SYSTOLIC,systolic);
        contentValues.put(DIASTOLIC,diastolic);
        contentValues.put(PRESURE_STATUS,pre_stat);
        contentValues.put(PULSE,pulse);
        contentValues.put(PULSE_STATUS,pul_stat);
        contentValues.put(DATE,"Date: "+date);
        contentValues.put(TIME,"Time: "+time);
        contentValues.put(COMMENTS,"Comments: "+comments);

        sqLiteDatabase.update(TABLE_NAME, contentValues, "_id = ?", new String[]{id});

       return true;
    }

    /**
     * this method is called whenever we try to delete an existing record
     * @param id
     * @return status of executed query
     */
    public long deleteData(String id)
    {
        SQLiteDatabase sqLiteDatabase =  this.getWritableDatabase();
        return  sqLiteDatabase.delete(TABLE_NAME,ID+" = ?",new String[]{id});/*returns null or zero if data is not deleted from database*/
    }

    /**
     * this method returns all the records from database
     * @return simpleCursorAdaptor
     */
    public SimpleCursorAdapter populateListViewFromDB() {

        SQLiteDatabase sqLiteDatabase =  this.getWritableDatabase();
        String columns[] = {MyDatabaseHelper.ID,MyDatabaseHelper.SYSTOLIC,MyDatabaseHelper.DIASTOLIC,MyDatabaseHelper.PRESURE_STATUS,MyDatabaseHelper.PULSE,MyDatabaseHelper.PULSE_STATUS,MyDatabaseHelper.DATE,MyDatabaseHelper.TIME,MyDatabaseHelper.COMMENTS};

        Cursor cursor = sqLiteDatabase.query(MyDatabaseHelper.TABLE_NAME,columns,null,null,null,null,null);

        String[] fromFieldNames = new String[]{
                MyDatabaseHelper.ID,MyDatabaseHelper.SYSTOLIC,MyDatabaseHelper.DIASTOLIC,MyDatabaseHelper.PRESURE_STATUS,MyDatabaseHelper.PULSE,MyDatabaseHelper.PULSE_STATUS,MyDatabaseHelper.DATE,MyDatabaseHelper.TIME,MyDatabaseHelper.COMMENTS
        };

        int[] toViewId = new int[]{
               R.id.item_id, R.id.systol, R.id.diastol, R.id.pressure_stat, R.id.pulse, R.id.pulse_status, R.id.date, R.id.time, R.id.comments
        };
        SimpleCursorAdapter contactAdapter = new SimpleCursorAdapter(context, R.layout.sample_list,cursor,fromFieldNames,toViewId);
        return contactAdapter;

    }

    /**
     * this method is for database unit test
     * this method checks if a data exists or not in the database by using unique id
     * @param id
     * @return bool
     */
    public boolean checkDataExistsOrNot(Long id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String Query = "Select * from " + TABLE_NAME + " where " + ID + " = " + Long.toString(id);
        Cursor cursor = sqLiteDatabase.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    /**
     * this method is to check all the column values of a particular record used for database unit testing
     * @param id
     * @param sys
     * @param dias
     * @param pressure_status
     * @param pulse
     * @param pulse_status
     * @param date
     * @param time
     * @param comments
     * @return bool
     */
    public boolean checkContent(String id, String sys, String dias, String pressure_status, String pulse, String pulse_status, String date, String time, String comments) {
        SQLiteDatabase sqLiteDatabase =  this.getWritableDatabase();
        String[] columns = {MyDatabaseHelper.SYSTOLIC, MyDatabaseHelper.DIASTOLIC, MyDatabaseHelper.PRESURE_STATUS, MyDatabaseHelper.PULSE, MyDatabaseHelper.PULSE_STATUS, MyDatabaseHelper.DATE, MyDatabaseHelper.TIME, MyDatabaseHelper.COMMENTS};
        Cursor cursor = sqLiteDatabase.query(MyDatabaseHelper.TABLE_NAME, columns, MyDatabaseHelper.ID+" = '"+id+"'", null, null, null, null);
        while (cursor.moveToNext()) {
            int i1 = cursor.getColumnIndex(MyDatabaseHelper.SYSTOLIC);
            int i2 = cursor.getColumnIndex(MyDatabaseHelper.DIASTOLIC);
            int i3 = cursor.getColumnIndex(MyDatabaseHelper.PRESURE_STATUS);
            int i4 = cursor.getColumnIndex(MyDatabaseHelper.PULSE);
            int i5 = cursor.getColumnIndex(MyDatabaseHelper.PULSE_STATUS);
            int i6 = cursor.getColumnIndex(MyDatabaseHelper.DATE);
            int i7 = cursor.getColumnIndex(MyDatabaseHelper.TIME);
            int i8 = cursor.getColumnIndex(MyDatabaseHelper.COMMENTS);

            String sys1 = cursor.getString(i1);
            String dia1 = cursor.getString(i2);
            String bp_sta1 = cursor.getString(i3);
            String pulse1 = cursor.getString(i4);
            String pulse_sta1 = cursor.getString(i5);
            String date1 = cursor.getString(i6);
            String time1 = cursor.getString(i7);
            String comm1 = cursor.getString(i8);

            if (sys != sys1 || dias != dia1 || pressure_status != bp_sta1 || pulse != pulse1 || pulse_status != pulse1 || date != date1 || time1 != time || comments != comm1) {
                cursor.close();
                return false;
            }
        }
        cursor.close();
        return true;
    }
}
