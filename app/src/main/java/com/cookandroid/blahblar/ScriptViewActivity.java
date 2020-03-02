package com.cookandroid.blahblar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class ScriptViewActivity extends AppCompatActivity {
    String id, name, phone, today, title;
    ImageView btn_toolbar_back;
    ListView list1_3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scriptview);

        Intent intent = getIntent();
        name = intent.getExtras().getString("name");
        id = intent.getExtras().getString("id");
        phone = intent.getExtras().getString("phone");
        today = intent.getExtras().getString("today");
        title = intent.getExtras().getString("title");

        btn_toolbar_back = (ImageView) findViewById(R.id.btn_toolbar_back_sv);
        btn_toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final ArrayList<String> midList = new ArrayList<String>();  //오답목록 저장
        final ArrayAdapter<String> adapter1_3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, midList);


        list1_3 = (ListView) findViewById(R.id.shadowScript);
        list1_3.setAdapter(adapter1_3);

        //예시
        midList.add("굿 플레이스");
        midList.add("상사에 대처하는 로맨틱한 자세");
        midList.add("명탐정 피카츄");
        midList.add("인턴");
        midList.add("브루클린의 멋진 주말");
        midList.add("미드나잇 인 파리");
        midList.add("빅 쇼트");
        midList.add("토이스토리4");
        adapter1_3.notifyDataSetChanged();


        //리스트 클릭 : 문항별 보기
        list1_3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int p, long l) {
                // 각 녹음파일 띄울 class 만들기
                if(p==0) { // 굿 플레이스
                    String GPPath = Environment.getExternalStorageDirectory().getPath()+"/GP/";
                    File[] listFiles = new File(GPPath).listFiles();

                    if(listFiles.length==0){
                        Toast.makeText(ScriptViewActivity.this, "녹음 학습 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = new Intent(ScriptViewActivity.this, ScriptGP.class);
                        intent.putExtra("id", id);
                        intent.putExtra("name", name);
                        intent.putExtra("phone", phone);
                        intent.putExtra("today", today);
                        intent.putExtra("title", "The Good Place");
                        startActivity(intent);
                    }

                } else if(p==1) { // 상사에 대처하는 로맨틱한 자세
                    String GPPath = Environment.getExternalStorageDirectory().getPath()+"/SS/";
                    File[] listFiles = new File(GPPath).listFiles();

                    if(listFiles.length==0){
                        Toast.makeText(ScriptViewActivity.this, "녹음 학습 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent = new Intent(ScriptViewActivity.this, ScriptSS.class);
                        intent.putExtra("id", id);
                        intent.putExtra("name", name);
                        intent.putExtra("phone", phone);
                        intent.putExtra("today", today);
                        intent.putExtra("title", "Set It Up");
                        startActivity(intent);
                    }
                } else if(p==2) { // 명탐정 피카츄
                    String GPPath = Environment.getExternalStorageDirectory().getPath()+"/PK/";
                    File[] listFiles = new File(GPPath).listFiles();

                    if(listFiles.length==0){
                        Toast.makeText(ScriptViewActivity.this, "녹음 학습 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent = new Intent(ScriptViewActivity.this, ScriptPK.class);
                        intent.putExtra("id", id);
                        intent.putExtra("name", name);
                        intent.putExtra("phone", phone);
                        intent.putExtra("today", today);
                        intent.putExtra("title", "Pokemon Detective Pikachu");
                        startActivity(intent);
                    }
                } else if(p==3) { // 인턴
                    String GPPath = Environment.getExternalStorageDirectory().getPath()+"/IT/";
                    File[] listFiles = new File(GPPath).listFiles();

                    if(listFiles.length==0){
                        Toast.makeText(ScriptViewActivity.this, "녹음 학습 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent = new Intent(ScriptViewActivity.this, ScriptIT.class);
                        intent.putExtra("id", id);
                        intent.putExtra("name", name);
                        intent.putExtra("phone", phone);
                        intent.putExtra("today", today);
                        intent.putExtra("title", "Intern");
                        startActivity(intent);
                    }
                } else if(p==4) { // 브루클린의 멋진 주말
                    String GPPath = Environment.getExternalStorageDirectory().getPath()+"/BK/";
                    File[] listFiles = new File(GPPath).listFiles();

                    if(listFiles.length==0){
                        Toast.makeText(ScriptViewActivity.this, "녹음 학습 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent = new Intent(ScriptViewActivity.this, ScriptBK.class);
                        intent.putExtra("id", id);
                        intent.putExtra("name", name);
                        intent.putExtra("phone", phone);
                        intent.putExtra("today", today);
                        intent.putExtra("title", "Ruth & Alex");
                        startActivity(intent);
                    }
                } else if(p==5) { // 미드나잇 인 파리
                    String GPPath = Environment.getExternalStorageDirectory().getPath()+"/MP/";
                    File[] listFiles = new File(GPPath).listFiles();

                    if(listFiles.length==0){
                        Toast.makeText(ScriptViewActivity.this, "녹음 학습 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent = new Intent(ScriptViewActivity.this, ScriptMP.class);
                        intent.putExtra("id", id);
                        intent.putExtra("name", name);
                        intent.putExtra("phone", phone);
                        intent.putExtra("today", today);
                        intent.putExtra("title", "Midnight in Paris");
                        startActivity(intent);
                    }
                } else if(p==6) { // 빅 쇼트
                    String GPPath = Environment.getExternalStorageDirectory().getPath()+"/BS/";
                    File[] listFiles = new File(GPPath).listFiles();

                    if(listFiles.length==0){
                        Toast.makeText(ScriptViewActivity.this, "녹음 학습 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent = new Intent(ScriptViewActivity.this, ScriptBS.class);
                        intent.putExtra("id", id);
                        intent.putExtra("name", name);
                        intent.putExtra("phone", phone);
                        intent.putExtra("today", today);
                        intent.putExtra("title", "The Big Short");
                        startActivity(intent);
                    }
                } else if(p==7) { // 토이스토리7
                    String GPPath = Environment.getExternalStorageDirectory().getPath()+"/TS/";
                    File folder = new File(Environment.getExternalStorageDirectory().getPath()+"/TS/");
                    boolean success =false;
                    if(!folder.exists()){
                        success=folder.mkdir();
                    }
                    File[] listFiles = new File(GPPath).listFiles();

                    if(listFiles.length==0){
                        Toast.makeText(ScriptViewActivity.this, "녹음 학습 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent = new Intent(ScriptViewActivity.this, ScriptTS.class);
                        intent.putExtra("id", id);
                        intent.putExtra("name", name);
                        intent.putExtra("phone", phone);
                        intent.putExtra("today", today);
                        intent.putExtra("title", "ToyStory7");
                        startActivity(intent);
                    }
                }
            }
        });

    }
}
