package com.mvvm.todoapp.room.database;


import androidx.room.TypeConverter;

import java.sql.Date;
import java.sql.Time;

//This Class is created for the using of customs data type (Date & Time)
public class Converters {

    @TypeConverter
    public static Date dateFromTimestamp(String value) {
        return value == null ? null : Date.valueOf(value);
    }

    @TypeConverter
    public static String dateToTimestamp(Date date) {
        return date == null ? null : date.toString();
    }

    @TypeConverter
    public static Time timeFromTimestamp(String value) {
        return value == null ? null : Time.valueOf(value);
    }

    @TypeConverter
    public static String timeToTimestamp(Time time) {
        return time == null ? null : time.toString();
    }

}
