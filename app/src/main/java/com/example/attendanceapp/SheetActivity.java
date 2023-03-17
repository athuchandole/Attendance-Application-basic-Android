 package com.example.attendanceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import java.util.Calendar;

 public class SheetActivity extends AppCompatActivity {
     Toolbar toolbar;
     private String className;
     private String subjectName;
     private MyCalendar calendar;
     private TextView subtitle;


     @Override
     protected void onCreate(Bundle savedInstanceState) {

         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_sheet);

         setToolbar();
         showTable();

     }

     private void setToolbar() {
         toolbar = findViewById(R.id.toolbar);
         TextView title = toolbar.findViewById(R.id.title_toolbar);
         subtitle = toolbar.findViewById(R.id.subtitle_toolbar);
         ImageButton back = toolbar.findViewById(R.id.back);
         ImageButton save = toolbar.findViewById(R.id.save);


         title.setText("Catalogue");
         subtitle.setVisibility(View.GONE);
         save.setVisibility(View.INVISIBLE);

         back.setOnClickListener(v -> onBackPressed());

     }



     private void showTable() {
        DbHelper dbHelper = new DbHelper(this);
         TableLayout tableLayout = findViewById(R.id.tableLayout);
         long[] idArray = getIntent().getLongArrayExtra("idArray");
         int[] rollArray = getIntent().getIntArrayExtra("rollArray");
         String[] nameArray = getIntent().getStringArrayExtra("nameArray");
         String month = getIntent().getStringExtra("month");

         int DAY_IN_MONTH = getDayInMonth(month);


         //roe setup
         int rowSize = idArray.length + 1;

         TableRow[] rows = new TableRow[rowSize];
         TextView[] roll_tvs = new TextView[rowSize];
         TextView[] name_tvs = new TextView[rowSize];
         TextView[][] status_tvs = new TextView[rowSize][DAY_IN_MONTH + 1];

         for (int i = 0; i < rowSize; i++) {
             roll_tvs[i] = new TextView(this);
             name_tvs[i] = new TextView(this);
             for (int j = 1; j <= DAY_IN_MONTH; j++) {
                 status_tvs[i][j] = new TextView(this);
             }
         }

         // header
         roll_tvs[0].setText("Roll \nNo.");
         roll_tvs[0].setTypeface(roll_tvs[0].getTypeface(), Typeface.BOLD);
         name_tvs[0].setText("Student \nName");
         name_tvs[0].setTypeface(name_tvs[0].getTypeface(), Typeface.BOLD);
         for (int i = 1; i < DAY_IN_MONTH; i++) {
             status_tvs[0][i].setText(String.valueOf(i));
             status_tvs[0][i].setTypeface(status_tvs[0][i].getTypeface(), Typeface.BOLD);
         }

         for (int i = 1; i < rowSize; i++) {
             roll_tvs[i].setText(String.valueOf(rollArray[i - 1]));
             name_tvs[i].setText(nameArray[i - 1]);

             for (int j = 1; j <= DAY_IN_MONTH; j++) {
                String day = String.valueOf(j);
                if(day.length()==1)day = "0"+day;
                String date = day+"."+month;
                String status = dbHelper.getStatus(idArray[i-1],date);
                status_tvs[i][j].setText(status);
             }
         }

         for (int i = 0; i < rowSize; i++) {
             rows[i] = new TableRow(this);

             if (i % 2==0)
                 rows[i].setBackgroundColor(Color.parseColor("#EEEEEE"));
             else rows[i].setBackgroundColor(Color.parseColor("#E4E4E4"));


             roll_tvs[i].setPadding(16,16,16,16);
             name_tvs[i].setPadding(16,16,16,16);

             rows[i].addView(roll_tvs[i]);
             rows[i].addView(name_tvs[i]);

             for (int j =1; j <= DAY_IN_MONTH; j++){
             status_tvs[i][j].setPadding(16,16,16,16);
                 rows[i].addView(status_tvs[i][j]);
             }

             tableLayout.addView(rows[i]);
        }
         tableLayout.setShowDividers(TableLayout.SHOW_DIVIDER_MIDDLE);
     }
     private int getDayInMonth(String month) {
         int monthIndex = Integer.parseInt(month.substring(0, 2)) -1 ;
         int year = Integer.valueOf(month.substring(3));

         Calendar calendar = Calendar.getInstance();
         calendar.set(Calendar.MONTH,monthIndex);
         calendar.set(Calendar.YEAR,year);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
     }

 }