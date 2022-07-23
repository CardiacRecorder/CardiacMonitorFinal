package com.example.cardiacmonitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/*
 this class represents a list of records
 */
public class RecordList {

    public List<Record> records = new ArrayList<>();//a list of type "record" is declared

    /**
     * this method is used to add any new record
     * if record already exists,it will throw an exception
     * @param record a new record
     */
    public void addRecords(Record record)
    {
        if(records.contains(record))
        {
            throw new IllegalArgumentException();
        }
        records.add(record);
    }

    /**
     * this returns an instance of sorted record list
     * sort is based on first attribute by default
     * @return list<record> a list of records
     */
    public List<Record>getRecords()
    {
        List<Record>recordList = records;
        Collections.sort(recordList);
        return recordList;
    }

    /**
     * this returns an instance of sorted record list
     * @param x
     * @return list<record>
     */
    public List<Record> getRecords(int x)
    {
        List<Record>recordList = records;
        return recordList;
    }

    /**
     * this method is used for deleting a particular record
     * if the record doesnt exist,it will throw an exception
     * @param record
     */
    public void delete(Record record)
    {
        List<Record>recordList = records;
        if(recordList.contains(record)){
            records.remove(record);
        }//list name is cities
        else
        {
            throw new IllegalArgumentException();
        }
    }

    /**
     * this method returns the size of list
     * @return int
     */
    public int count()
    {
        return records.size();
    }



}
