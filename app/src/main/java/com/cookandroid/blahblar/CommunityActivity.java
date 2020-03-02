package com.cookandroid.blahblar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class CommunityActivity extends AppCompatActivity{
    ImageView btn_toolbar_back;
    ListView lv;
    String[] items = {"신규영상","인기영상", "Hot게시판",  "학습후기"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        setTitle("");

        btn_toolbar_back = (ImageView) findViewById(R.id.btn_toolbar_back_co);
        btn_toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lv = (ListView)findViewById(R.id.ListView);
        lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Intent intent0 = new Intent (CommunityActivity.this,CommuNewVideo.class);
                    startActivity(intent0);
                }
                if(position == 1){
                    Intent intent1 = new Intent (CommunityActivity.this,CommuHotVideo.class);
                    startActivity(intent1);
                }
                if(position == 2){
                    Intent intent2 = new Intent(CommunityActivity.this, CommuHotboard.class);
                    startActivity(intent2);
                }
                if(position == 3){
                    Intent intent3 = new Intent(CommunityActivity.this, CommuReviews.class);
                    startActivity(intent3);
                }
            }
        });
    }
}
