package com.mvvm.todoapp.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mvvm.todoapp.R;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.mvvm.todoapp.view.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.mvvm.todoapp.view.EXTRA_TITLE";
    public static final String EXTRA_SWITCH = "com.mvvm.todoapp.view.EXTRA_SWITCH";
    public static final String EXTRA_DATE = "com.mvvm.todoapp.view.EXTRA_DATE";
    public static final String EXTRA_TIME = "com.mvvm.todoapp.view.EXTRA_TIME";
    public static final String EXTRA_DESCRIPTION = "com.mvvm.todoapp.view.EXTRA_DESCRIPTION";
    public static final String EXTRA_NOTIFICATION_ID = "com.mvvm.todoapp.view.EXTRA_NOTIFICATION_ID";

    private EditText editTextTitle;
    private Switch aSwitch;
    private EditText editTextDate;
    private EditText editTextTime;
    private EditText editTextDescription;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle = findViewById(R.id.titleTask);
        aSwitch = findViewById(R.id.aswitch);
        editTextDate = findViewById(R.id.dateTask);
        editTextTime = findViewById(R.id.hourTask);
        editTextDescription = findViewById(R.id.detailTask);
        saveButton = findViewById(R.id.savebutton);

        // To distinguish between add and edit Task
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Task");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            if (Boolean.parseBoolean(intent.getStringExtra(AddEditNoteActivity.EXTRA_SWITCH))) {
                aSwitch.setChecked(true);
                editTextDate.setEnabled(true);
                editTextTime.setEnabled(true);
            } else {
                aSwitch.setChecked(false);
            }
            editTextDate.setText(intent.getStringExtra(EXTRA_DATE));
            editTextTime.setText(intent.getStringExtra(EXTRA_TIME));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));

        } else {
            setTitle("Add Task");
            editTextDate.setText(LocalDate.now().toString());
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            String formattedTime = sdf.format(now);
            formattedTime = formattedTime + ":00";
            editTextTime.setText(formattedTime);
        }

        // Switch
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editTextDate.setEnabled(true);
                    editTextTime.setEnabled(true);
                } else {
                    editTextDate.setEnabled(false);
                    editTextTime.setEnabled(false);

                }
            }
        });
        //Pickers Line
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddEditNoteActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month++;    //Cuz month starts from 0
                        String text = year + "-" + month + "-" + dayOfMonth;
                        editTextDate.setText(text);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker = new TimePickerDialog(AddEditNoteActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String text = selectedHour + ":" + selectedMinute + ":00";
                        editTextTime.setText(text);
                    }
                }, Calendar.getInstance().get(Calendar.HOUR), Calendar.getInstance().get(Calendar.MINUTE), true);
                timePicker.show();
            }
        });
        //End Pickers Line

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    public void saveNote() {

        String titleValue = editTextTitle.getText().toString();
        String detailValue = editTextDescription.getText().toString();
        String SwitchValue = Boolean.toString(aSwitch.isChecked());
        String dateValue = editTextDate.getText().toString();
        String hourValue = editTextTime.getText().toString();

        // Tests
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        String formattedTime = sdf.format(now);
        if (java.sql.Date.valueOf(dateValue).before(java.sql.Date.valueOf(LocalDate.now().toString())) ||
                (java.sql.Date.valueOf(dateValue).equals(java.sql.Date.valueOf(LocalDate.now().toString())) &&
                        Time.valueOf(hourValue).before(Time.valueOf(formattedTime + ":00")))) {
            Toast.makeText(getApplicationContext(), "Can't set a task for the past, \n " +
                    "please update the date or the time.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (titleValue.trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Title field is required", Toast.LENGTH_SHORT).show();
            return;
        }
        //End of tests

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, titleValue);
        data.putExtra(EXTRA_SWITCH, SwitchValue);
        data.putExtra(EXTRA_DATE, dateValue);
        data.putExtra(EXTRA_TIME, hourValue);
        data.putExtra(EXTRA_DESCRIPTION, detailValue);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

}
