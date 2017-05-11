package com.example.myfirstapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Calendar;
import java.util.Date;

import static com.example.myfirstapp.MainActivity.REMINDER_INDEX;
import static com.example.myfirstapp.MainActivity.REMINDER_NOTIFICATION;
import static com.example.myfirstapp.MainActivity.REMINDER_UPDATE;


public class BuildReminderActivity extends AppCompatActivity {

    public static final String REMINDER_OBJECT = "object";
    EditText time;
    EditText date;
    Calendar dateSet = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_reminder);

        Intent intent = getIntent();
        if (intent.hasExtra(REMINDER_UPDATE)){
            Gson gson = new GsonBuilder().create();
            Reminder reminder = gson.fromJson(intent.getStringExtra(REMINDER_UPDATE), Reminder.class);
            Calendar cal = reminder.getDateDue();
            EditText title = (EditText) findViewById(R.id.title);
            EditText description = (EditText) findViewById(R.id.description);
            time = (EditText) findViewById(R.id.time);
            date = (EditText) findViewById(R.id.date);
            title.setText(reminder.getTitle());
            description.setText(reminder.getDescription());
            dateSet = reminder.getDateDue();
            time.setText(dateSet.get(Calendar.HOUR) + ":" + dateSet.get(Calendar.MINUTE));
            date.setText(String.format("%d/%d/%d", dateSet.get(Calendar.DAY_OF_MONTH),
                    dateSet.get(Calendar.MONTH), dateSet.get(Calendar.YEAR)));
        }

        //Date Dialog
        date = (EditText) findViewById(R.id.date);
        date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Calendar currentTime = Calendar.getInstance();
                int year = currentTime.get(Calendar.YEAR);
                int month = currentTime.get(Calendar.MONTH);
                int day = currentTime.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog;
                datePickerDialog = new DatePickerDialog(BuildReminderActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                        dateSet.set(year, monthOfYear, dayOfMonth);
                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });

        //Time Dialog
        time = (EditText) findViewById(R.id.time);
        time.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR);
                int minute = currentTime.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(BuildReminderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int sHour, int sMinute) {
                        time.setText(String.format("%02d:%02d",sHour, sMinute));
                        dateSet.set(Calendar.HOUR, sHour);
                        dateSet.set(Calendar.MINUTE, sMinute);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });
    }

    public void deleteReminder(View view){
        Intent mainIntent = getIntent();
        Intent intent = new Intent(this, MainActivity.class);
        if (mainIntent.hasExtra(REMINDER_NOTIFICATION)){
            intent.putExtra(REMINDER_NOTIFICATION, mainIntent.getIntExtra(REMINDER_NOTIFICATION, -2));
        }
        startActivity(intent);
    }

    public void addReminder(View view){
        //instantiations
        Intent mainIntent = getIntent();
        Intent intent = new Intent(this, MainActivity.class);
        Gson gson = new GsonBuilder().create();
        Reminder reminder = new Reminder();
        //text from title and description
        EditText titleText = (EditText) findViewById(R.id.title);
        EditText descriptionText = (EditText) findViewById(R.id.description);
        //filling the reminder with all the info
        reminder.setTitle(titleText.getText().toString());
        reminder.setDescription(descriptionText.getText().toString());
        reminder.setDateDue(dateSet);
        //turning the reminder into a json
        String obj = gson.toJson(reminder);
        intent.putExtra(REMINDER_OBJECT, obj);
        if (mainIntent.hasExtra(REMINDER_NOTIFICATION)){
            intent.putExtra(REMINDER_NOTIFICATION, mainIntent.getIntExtra(REMINDER_NOTIFICATION, -1));
        }
        startActivity(intent);
    }
}
