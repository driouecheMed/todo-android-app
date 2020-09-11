package com.mvvm.todoapp.room.entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Date;
import java.sql.Time;

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "title_note")
    @NonNull
    private String title;
    @ColumnInfo(name = "date_note")
    @Nullable
    private Date date;
    @ColumnInfo(name = "time_note")
    @Nullable
    private Time time;
    @ColumnInfo(name = "detail_note")
    @Nullable
    private String detail;

    /*
    @Ignore
    public Note(String title) {
        this.title = title;
    }
    @Ignore
    public Note(String title, Date date, Time time) {
        this.title = title;
        this.title = title;
        this.time = time;
    }
     */

    @Ignore
    public Note(@NonNull String title, @Nullable Date date, @Nullable Time time, @Nullable String detail) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.detail = detail;
    }

    public Note(@NonNull String title, @Nullable String detail) {
        this.title = title;
        this.detail = detail;
        this.time = null;
        this.date = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @Nullable
    public Date getDate() {
        return date;
    }

    @Nullable
    public void setDate(@Nullable Date date) {
        this.date = date;
    }

    @Nullable
    public Time getTime() {
        return time;
    }

    @Nullable
    public void setTime(@Nullable Time time) {
        this.time = time;
    }

    public String getDetail() {
        return detail;
    }

    @Nullable
    public void setDetail(@Nullable String detail) {
        this.detail = detail;
    }
}

