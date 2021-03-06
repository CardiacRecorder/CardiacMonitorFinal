package com.example.cardiacmonitor;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.function.ThrowingRunnable;

public class RecordsActivityTest {
    /**
     * this method is to adding a mockrecord in the record list which will be used for testing
     * @return RecordList
     */
    private RecordList mockRecordList()
    {
        RecordList recordList = new RecordList();
        recordList.addRecords(mockRecord());
        return recordList;
    }

    /**
     * for creating a mock record
     * @return Record;
     */
    private Record mockRecord()
    {
        return new Record("120","80","normal","60","normal","Thursday,7 July 2022","05:01 pm","sitting");
    }

    /**
     * this method is for testing if a mockrecord is added properly in record list
     */
    @Test
    public void testAddRecord()
    {
        RecordList recordList = mockRecordList();

        assertEquals(1,recordList.getRecords().size());


        Record record = new Record("160","60","high","50","exceptional","Thursday,7 July 2022","05:49 pm","resting");

        recordList.addRecords(record);
        assertEquals(2,recordList.getRecords().size());
        assertTrue(recordList.getRecords().contains(record));
    }

    /**
     * exception will occur if a duplicate record is added
     * this method is to check if this exception is thrown properly
     */
    @Test
    public void testAddException()
    {
        RecordList recordList = new RecordList();
        Record record = mockRecord();
        recordList.addRecords(record);

        assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                recordList.addRecords(record);
            }
        });
    }

    /**
     * this method is to test if a record can be fetcehd properly
     */
    @Test
    public void testGetRecords()
    {
        RecordList recordList = mockRecordList();

        assertEquals(0,mockRecord().compareTo(recordList.getRecords().get(0)));

        Record record1 = new Record("190","60","high","50","exceptional","Thursday,7 July 2022","05:49 pm","resting");
        recordList.addRecords(record1);
        Record record2 = new Record("90","60","high","50","exceptional","Thursday,7 July 2022","05:49 pm","resting");
        recordList.addRecords(record2);


        assertEquals(0,record1.compareTo(recordList.getRecords().get(1)));
        assertEquals(0,mockRecord().compareTo(recordList.getRecords().get(0)));
        assertEquals(0,record2.compareTo(recordList.getRecords().get(2)));

    }

    /**
     * this method is to test if a record is deleted from record list properly
     */
    @Test
    public void testdelete()
    {
        RecordList recordList = new RecordList();
        Record record1 = new Record("190","60","high","50","exceptional","Thursday,7 July 2022","05:49 pm","resting");
        recordList.addRecords(record1);
        Record record2 = new Record("90","60","high","50","exceptional","Thursday,7 July 2022","05:49 pm","resting");
        recordList.addRecords(record2);
        recordList.delete(record1);
        assertTrue(!recordList.getRecords().contains(record1));

    }
    /**
     * exception will occur if a nonexistent record is being tried to delete
     * this method is to check if this exception is thrown properly
     */
    @Test
    public void testDeleteException() {
        /*creating and initializing objects for citylist and city class*/
        RecordList recordList = new RecordList();
        Record record1 = new Record("190","60","high","50","exceptional","Thursday,7 July 2022","05:49 pm","resting");
        recordList.addRecords(record1);
        Record record2 = new Record("90","60","high","50","exceptional","Thursday,7 July 2022","05:49 pm","resting");
        recordList.addRecords(record2);
        recordList.delete(record1);
        recordList.delete(record2);
        /*trying to delete an object which is not in the list*/
        assertThrows(IllegalArgumentException.class, () -> {
            recordList.delete(record1);
        });
    }

    /**
     * to test the rowcount of record list
     */
    @Test
    public void testcount(){
        /*creating and initializing objects for citylist and city class*/
        RecordList recordList = new RecordList();
        Record record1 = new Record("190","60","high","50","exceptional","Thursday,7 July 2022","05:49 pm","resting");
        recordList.addRecords(record1);
        Record record2 = new Record("90","60","high","50","exceptional","Thursday,7 July 2022","05:49 pm","resting");
        recordList.addRecords(record2);
        assertEquals(2,recordList.count());
        recordList.delete(record1);
        assertEquals(1,recordList.count());
        recordList.delete(record2);
    }




}