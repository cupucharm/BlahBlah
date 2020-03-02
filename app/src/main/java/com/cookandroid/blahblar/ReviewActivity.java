package com.cookandroid.blahblar;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.cookandroid.blahblar.R;
import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {
    ImageView btn_toolbar_back;
    ListView list1_3;
    Button graph_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        btn_toolbar_back = (ImageView) findViewById(R.id.btn_toolbar_back_r);
        btn_toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final ArrayList<String> midList1_3 = new ArrayList<String>();  //오답목록 저장
        final ArrayAdapter<String> adapter1_3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, midList1_3);


        list1_3 = (ListView) findViewById(R.id.listView1_3);
        list1_3.setAdapter(adapter1_3);

        //예시
        midList1_3.add("굿 플레이스");
        midList1_3.add("상사에 대처하는 로맨틱한 자세");
        midList1_3.add("명탐정 피카츄");
        midList1_3.add("인턴");
        midList1_3.add("브루클린의 멋진 주말");
        midList1_3.add("미드나잇 인 파리");
        midList1_3.add("빅 쇼트");
        midList1_3.add("토이스토리4");
        adapter1_3.notifyDataSetChanged();

        //리스트 클릭 : 문항별 보기
        list1_3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int p, long l) {
                if(p==0) { // 굿 플레이스
                    Intent intent = new Intent(ReviewActivity.this, ReviewQuestionGP.class);
                    startActivity(intent);
                } else if(p==1) { // 상사에 대처하는 로맨틱한 자세
                    Intent intent = new Intent(ReviewActivity.this, ReviewQuestionSS.class);
                    startActivity(intent);
                } else if(p==2) { // 명탐정 피카츄
                    Intent intent = new Intent(ReviewActivity.this, ReviewQuestionActivity.class);
                    startActivity(intent);
                } else if(p==3) { // 인턴
                    Intent intent = new Intent(ReviewActivity.this, ReviewQuestionIT.class);
                    startActivity(intent);
                }  else if(p==4) { // 브루클린의 멋진 주말
                    Intent intent = new Intent(ReviewActivity.this, ReviewQuestionBK.class);
                    startActivity(intent);
                } else if(p==5) { // 미드나잇 인 파리
                    Intent intent = new Intent(ReviewActivity.this, ReviewQuestionMP.class);
                    startActivity(intent);
                } else if(p==6) { // 빅 쇼트
                    Intent intent = new Intent(ReviewActivity.this, ReviewQuestionBS.class);
                    startActivity(intent);
                } else if(p==7) { // 토이스토리4
                    Intent intent = new Intent(ReviewActivity.this, ReviewQuestionTS.class);
                    startActivity(intent);
                }
            }
        });

    }
}
