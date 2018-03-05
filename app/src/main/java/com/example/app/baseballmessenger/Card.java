package com.example.app.baseballmessenger;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by pr4h6n on 3/2/18.
 */

/*
* Local Database classes
*   AppDatabase - local database
*   Card - 'CARD' table
*   CardDAO - used to interact with database
*/

@Entity(tableName = "CARDS")
public class Card {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "NAME")
    private String name;

    @ColumnInfo(name = "CONDITION")
    private String condition;

    @ColumnInfo(name = "NUMBER")
    private String number;

    @ColumnInfo(name = "ROLE")
    private String role;

    @ColumnInfo(name = "TEAM")
    private String team;

    @ColumnInfo(name = "VALUE")
    private double value;

    @ColumnInfo(name = "YEAR")
    private String year;

    @ColumnInfo(name = "DATE_ACQUIRED")
    private String dateAcquired;


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDateAcquired() {
        return dateAcquired;
    }

    public void setDateAcquired(String dateAcquired) {
        this.dateAcquired = dateAcquired;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

}