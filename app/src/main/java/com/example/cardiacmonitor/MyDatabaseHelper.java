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
     * @param systolic value for systol
     * @param diastolic value for diastol
     * @param pre_stat value for pressure_status
     * @param pulse value for pulserate
     * @param pul_stat value for pulse_status
     * @param date value for date
     * @param time value for date
     * @param comments value for commnets
     * @return id corresponding unique id of newly created row
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
     * @param id value for unique id of the row
     * @param systolic value for systol
     * @param diastolic value for diastol
     * @param pre_stat value for pressure_status
     * @param pulse value for pulserate
     * @param pul_stat value for pulse_status
     * @param date value for date
     * @param time value for date
     * @param comments value for commnets
     * @return bool indicating record has been updated
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
     * @param id pf the corresponding row which will be deleted
     * @return status of executed query
     */
    public long deleteData(String id)
    {
        SQLiteDatabase sqLiteDatabase =  this.getWritableDatabase();
        return  sqLiteDatabase.delete(TABLE_NAME,ID+" = ?",new String[]{id});/*returns null or zero if data is not deleted from database*/
    }

    /*
      this method returns all the records from database
      @return simpleCursorAdaptor that is all the rows from database
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
}
