package com.example.cardiacmonitor;

/**
 * a class to represent a record
 */
public class Record implements Comparable<Record>{

    private String systolic;
    private String diastolic;
    private String pressure_status;
    private String pulse;
    private String pulse_status;
    private String date;
    private String time;
    private String comments;

    /**
     * this is a constructor of record class to initialize values
     * @param systolic
     * @param diastolic
     * @param pressure_status
     * @param pulse
     * @param pulse_status
     * @param date
     * @param time
     * @param comments
     */
    Record(String systolic, String diastolic, String pressure_status, String pulse, String pulse_status, String date, String time, String comments) {
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.pressure_status = pressure_status;
        this.pulse = pulse;
        this.pulse_status = pulse_status;
        this.date = date;
        this.time = time;
        this.comments = comments;
    }

    /**
     * getter of systolic
     * @return string
     */
    public String getSystolic() {
        return systolic;
    }
    /**
     * setter of systolic
     * @return void
     */
    public void setSystolic(String systolic) {
        this.systolic = systolic;
    }
    /**
     * getter of diastolic
     * @return string
     */
    public String getDiastolic() {
        return diastolic;
    }
    /**
     * setter of systolic
     * @return void
     */
    public void setDiastolic(String diastolic) {
        this.diastolic = diastolic;
    }
    /**
     * getter of pressure_status
     * @return string
     */
    public String getPressure_status() {
        return pressure_status;
    }
    /**
     * setter of pressure_status
     * @return void
     */
    public void setPressure_status(String pressure_status) {
        this.pressure_status = pressure_status;
    }
    /**
     * getter of pulse
     * @return string
     */
    public String getPulse() {
        return pulse;
    }
    /**
     * setter of pulse
     * @return void
     */
    public void setPulse(String pulse) {
        this.pulse = pulse;
    }
    /**
     * setter of pulse
     * @return string
     */
    public String getPulse_status() {
        return pulse_status;
    }
    /**
     * setter of pulse_status
     * @return void
     */
    public void setPulse_status(String pulse_status) {
        this.pulse_status = pulse_status;
    }
    /**
     * getter of date
     * @return string
     */
    public String getDate() {
        return date;
    }
    /**
     * setter of date
     * @return void
     */
    public void setDate(String date) {
        this.date = date;
    }
    /**
     * setter of time
     * @return string
     */
    public String getTime() {
        return time;
    }
    /**
     * setter of time
     * @return void
     */
    public void setTime(String time) {
        this.time = time;
    }
    /**
     * getter of comment
     * @return string
     */
    public String getComments() {
        return comments;
    }
    /**
     * setter of comment
     * @return void
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * this method is used to compare two records using their systolic value
     * @param record
     * @return int
     */
    @Override
    public int compareTo(Record record) {
        return this.systolic.compareTo(record.systolic);
    }
}
