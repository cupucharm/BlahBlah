package com.cookandroid.blahblar;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;



import java.sql.Date;
import java.text.SimpleDateFormat;

public class MenuActivity extends AppCompatActivity {

    public static String UPDATEIS = "X";

    Button btnwrt_4,btncal_4, btnrd_4, btnTTS_4, btnftn_4;
    ImageView btntool4;
    String id, name, phone, today;

    dbHelper helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnwrt_4 = (Button) findViewById(R.id.btnwrt_4);
        btncal_4=(Button)findViewById(R.id.btncal_4);
        btnTTS_4 = (Button) findViewById(R.id.btnTTS_4);
        btnrd_4 = (Button) findViewById(R.id.btnrd_4);
        //btnftn_4 = (Button) findViewById(R.id.btnftn_4);
        //btntool4 = (ImageView) findViewById(R.id.btntool4);

        Long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        today = sdf.format(date);

        Intent intent = getIntent();
        name = intent.getExtras().getString("name");
        id = intent.getExtras().getString("id");
        phone = intent.getExtras().getString("phone");

        Toast.makeText(this, name + "님 안녕하세요.", Toast.LENGTH_SHORT).show();
        Log.d("메뉴비디오..", id+ " " + name+" "+phone+" "+today);


        // 영상 학습
        btnwrt_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), VideoListActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("today", today);
                startActivity(intent);
            }
        });

        // 챗봇
        btncal_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatbotActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("today", today);
                startActivity(intent);
            }
        });


        // 학습 정보
        btnrd_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("today", today);
                startActivity(intent);
            }
        });

        // Audio 및 TTS
        btnTTS_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ScriptViewActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("today", today);
                startActivity(intent);
            }
        });
    }
}
