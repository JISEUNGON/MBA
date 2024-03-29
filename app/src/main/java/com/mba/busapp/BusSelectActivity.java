package com.mba.busapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Date;

public class BusSelectActivity extends AppCompatActivity {

    // 다음에 넘길 요일과 학기 String
    String day;
    String semester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_select);

        // 타이틀바 숨기기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // 변수 초기화
        day = "";
        semester = "";

        ImageButton ib01 = (ImageButton)findViewById(R.id.weekend);
        ImageButton ib02 = (ImageButton)findViewById(R.id.weekday);

        ImageButton ib03 = (ImageButton)findViewById(R.id.semester);
        ImageButton ib04 = (ImageButton)findViewById(R.id.vacationstudy);
        ImageButton ib05 = (ImageButton)findViewById(R.id.vacation);

        // 텍스트뷰
        TextView text1 = (TextView)findViewById(R.id.weekend_text);
        TextView text2 = (TextView)findViewById(R.id.weekday_text);
        TextView text3 = (TextView)findViewById(R.id.semester_text);
        TextView text4 = (TextView)findViewById(R.id.vacationstudy_text);
        TextView text5 = (TextView)findViewById(R.id.vacation_text);


        // 요일 리스너
        ib01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ib01.setImageResource(R.drawable.select_weekend_img);
                ib02.setImageResource(R.drawable.unselect_weekday_img);
                day = "주말";
                text1.setTextColor(Color.parseColor("#FF3700B3"));
                text2.setTextColor(Color.parseColor("#808080"));

            }
        });
        ib02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ib01.setImageResource(R.drawable.unselect_weekend_img);
                ib02.setImageResource(R.drawable.select_weekday_img);
                day = "평일";
                text1.setTextColor(Color.parseColor("#808080"));
                text2.setTextColor(Color.parseColor("#FF3700B3"));
            }
        });

        // 학기 리스너
        ib03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ib03.setImageResource(R.drawable.select_semester_img);
                ib04.setImageResource(R.drawable.unselect_vacationstudy_img);
                ib05.setImageResource(R.drawable.unselect_vacation_img);
                semester = "학기";
                text3.setTextColor(Color.parseColor("#FF3700B3"));
                text4.setTextColor(Color.parseColor("#808080"));
                text5.setTextColor(Color.parseColor("#808080"));
            }
        });
        ib04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ib03.setImageResource(R.drawable.unselect_semester_img);
                ib04.setImageResource(R.drawable.select_vacationstudy_img);
                ib05.setImageResource(R.drawable.unselect_vacation_img);
                semester = "계절학기";
                text3.setTextColor(Color.parseColor("#808080"));
                text4.setTextColor(Color.parseColor("#FF3700B3"));
                text5.setTextColor(Color.parseColor("#808080"));
            }
        });
        ib05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ib03.setImageResource(R.drawable.unselect_semester_img);
                ib04.setImageResource(R.drawable.unselect_vacationstudy_img);
                ib05.setImageResource(R.drawable.select_vacation_img);
                semester = "방학";
                text3.setTextColor(Color.parseColor("#808080"));
                text4.setTextColor(Color.parseColor("#808080"));
                text5.setTextColor(Color.parseColor("#FF3700B3"));
            }
        });


        /**
         *  주말 여부
         */
        if (isWeekend()) {
            ib01.performClick();
        } else {
            ib02.performClick();
        }

        /**
         * 계절학기 여부
         */
        if (isSemester()) {
            ib03.performClick();
        } else if (isSeasonalSemester()) {
            ib04.performClick();
        } else {
            ib05.performClick();
        }
    }


    // 버스 노선도 보기 버튼
    public void btn_busRoute(View v) {

        if(day != "" && semester != ""){

            // intent로 값 전달
            Intent intent = new Intent(this, BusRouteActivity.class);

            intent.putExtra("day", day);
            intent.putExtra("semester", semester);

            startActivity(intent);
        }

    }

    public boolean isWeekend() {
        LocalDate today = LocalDate.now();

        DayOfWeek day = DayOfWeek.of(today.get(ChronoField.DAY_OF_WEEK));
        return day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY;
    }

    public boolean isSemester() {
        /**
         * 2022.03.02 ~ 06.14
         * 2022.09.01 ~ 12.13
         */
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date today = dateFormat.parse(String.valueOf(LocalDate.now()));
            Date spring_start = dateFormat.parse("2022-03-02");
            Date spring_end = dateFormat.parse("2022-06-14");
            Date autumn_start = dateFormat.parse("2022-09-01");
            Date autumn_end = dateFormat.parse("2022-12-13");

            return spring_start.before(today) && spring_end.after(today) || autumn_start.before(today) && autumn_end.after(today);

        } catch (Exception e) {

        }
        return true;
    }

    public boolean isSeasonalSemester() {
        /**
         * 2022.06.20 ~ 2022.07.08
         * 2022.12.22 ~ 2023.01.11
         */
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date today = dateFormat.parse(String.valueOf(LocalDate.now()));
            Date summer_start = dateFormat.parse("2022-06-20");
            Date summer_end = dateFormat.parse("2022-07-08");
            Date winter_start = dateFormat.parse("2022-12-22");
            Date winter_end = dateFormat.parse("2023-01-11");
            return summer_start.before(today) && summer_end.after(today) || winter_start.before(today) && winter_end.after(today);
        } catch (Exception e) {

        }

        return true;
    }
}


