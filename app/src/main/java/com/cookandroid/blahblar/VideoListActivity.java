package com.cookandroid.blahblar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;

public class VideoListActivity extends AppCompatActivity {

    private RecyclerView View1, View2, View3, View4;
    private VideoListAdapter mAdapter1, mAdapter2, mAdapter3, mAdapter4;
    private LinearLayoutManager mLayoutManager1, mLayoutManager2, mLayoutManager3,mLayoutManager4;
    String id, name, phone, today;
    ImageView btn_toolbar_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videolist);

        btn_toolbar_back = (ImageView) findViewById(R.id.btn_toolbar_back_vm);
        btn_toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        name = intent.getExtras().getString("name");
        id = intent.getExtras().getString("id");
        phone = intent.getExtras().getString("phone");
        today = intent.getExtras().getString("today");
        Log.d("비디오피카..", id+ " " + name+" "+phone+" "+today);
        //Toast.makeText(this, "비디오리스트... "+id + " " + name + " " + phone + " " + today, Toast.LENGTH_SHORT).show();


        // RecyclerView binding
        View1 = (RecyclerView) findViewById(R.id.listview1);    //일상 리스트뷰
        View2 = (RecyclerView) findViewById(R.id.listview2);    //여행 리스트뷰
        View3 = (RecyclerView) findViewById(R.id.listview3);    //비즈니스 리스트뷰
        View4 = (RecyclerView) findViewById(R.id.listview4);    //키즈 라스트뷰

        // init Data
        ArrayList<VideoListData> data1 = new ArrayList<>();  //일상 데이터
        ArrayList<VideoListData> data2 = new ArrayList<>();  //여행 데이터
        ArrayList<VideoListData> data3 = new ArrayList<>();  //비즈니스 데이터
        ArrayList<VideoListData> data4 = new ArrayList<>();  //키즈 데이터


        data1.add(new VideoListData(R.drawable.goodplace, "굿 플레이스"));
        data1.add(new VideoListData(R.drawable.setitup, "상사에 대처하는 로맨틱한 자세"));
        data1.add(new VideoListData(R.drawable.brooklyn, "브루클린"));

        data2.add(new VideoListData(R.drawable.pika, "명탐정 피카츄"));
        data2.add(new VideoListData(R.drawable.midnight, "미드나잇 인 파리"));

        data3.add(new VideoListData(R.drawable.intern, "인턴"));
        data3.add(new VideoListData(R.drawable.bigshort, "빅쇼트"));

        data4.add(new VideoListData(R.drawable.toystory, "토이스토리"));


        // init LayoutManager
        mLayoutManager1 = new LinearLayoutManager(this);
        mLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL); // 기본값이 VERTICAL
        mLayoutManager2 = new LinearLayoutManager(this);
        mLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL); // 기본값이 VERTICAL
        mLayoutManager3 = new LinearLayoutManager(this);
        mLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL); // 기본값이 VERTICAL
        mLayoutManager4 = new LinearLayoutManager(this);
        mLayoutManager4.setOrientation(LinearLayoutManager.HORIZONTAL); // 기본값이 VERTICAL

        // setLayoutManager
        View1.setLayoutManager(mLayoutManager1);
        View2.setLayoutManager(mLayoutManager2);
        View3.setLayoutManager(mLayoutManager3);
        View4.setLayoutManager(mLayoutManager4);

        // init Adapter
        mAdapter1 = new VideoListAdapter();
        mAdapter2 = new VideoListAdapter();
        mAdapter3 = new VideoListAdapter();
        mAdapter4 = new VideoListAdapter();

        // set Data, set Adapter
        mAdapter1.setData(data1, "d1");
        mAdapter1.getData(id, name, phone, today);
        View1.setAdapter(mAdapter1);
        mAdapter2.setData(data2, "d2");
        mAdapter2.getData(id, name, phone, today);
        View2.setAdapter(mAdapter2);
        mAdapter3.setData(data3, "d3");
        mAdapter3.getData(id, name, phone, today);
        View3.setAdapter(mAdapter3);
        mAdapter4.setData(data4, "d4");
        mAdapter4.getData(id, name, phone, today);
        View4.setAdapter(mAdapter4);
    }
}
