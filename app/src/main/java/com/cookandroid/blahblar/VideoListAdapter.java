package com.cookandroid.blahblar;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListViewHolder> {

    String id, name, phone, today, title;
    String dataname;

    private ArrayList<VideoListData> verticalDatas;

    public void setData(ArrayList<VideoListData> list, String dn){
        verticalDatas = list;
        dataname = dn;
    }

    @Override
    public VideoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


// 사용할 아이템의 뷰를 생성해준다.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_videolist_item, parent, false);

        VideoListViewHolder holder = new VideoListViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(VideoListViewHolder holder, final int position) {
        final VideoListData data = verticalDatas.get(position);

        holder.name.setText(data.getText());

        holder.img.setImageResource(data.getImg());
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dataname=="d1" && position==0) {
                    title="The Good Place";

                    Intent intent1 = new Intent(view.getContext(), VideoLearnGP.class);
                    intent1.putExtra("id", id);
                    intent1.putExtra("name", name);
                    intent1.putExtra("phone", phone);
                    intent1.putExtra("today", today);
                    intent1.putExtra("title", title);
                    Log.d("비디오리스트어답터..", id + " " + name + " " + phone + " " + today+" "+title);
                    view.getContext().startActivity(intent1);
                }
                else if(dataname=="d1" && position==1) {
                    title="Set It Up";

                    Intent intent2 = new Intent(view.getContext(), VideoLearnSS.class);
                    intent2.putExtra("id", id);
                    intent2.putExtra("name", name);
                    intent2.putExtra("phone", phone);
                    intent2.putExtra("today", today);
                    intent2.putExtra("title", title);
                    Log.d("비디오리스트어답터..", id + " " + name + " " + phone + " " + today+" "+title);
                    view.getContext().startActivity(intent2);
                }
                else if(dataname=="d1" && position==2) {
                    title="Ruth & Alex";

                    Intent intent2 = new Intent(view.getContext(), VideoLearnBK.class);
                    intent2.putExtra("id", id);
                    intent2.putExtra("name", name);
                    intent2.putExtra("phone", phone);
                    intent2.putExtra("today", today);
                    intent2.putExtra("title", title);
                    Log.d("비디오리스트어답터..", id + " " + name + " " + phone + " " + today+" "+title);
                    view.getContext().startActivity(intent2);
                }
                else if(dataname=="d2" && position==0) {
                    title="Pokemon Detective Pikachu";

                    Intent intent3 = new Intent(view.getContext(), VideoLearnActivity.class);
                    intent3.putExtra("id", id);
                    intent3.putExtra("name", name);
                    intent3.putExtra("phone", phone);
                    intent3.putExtra("today", today);
                    intent3.putExtra("title", title);
                    Log.d("비디오리스트어답터..", id + " " + name + " " + phone + " " + today+" "+title);
                    view.getContext().startActivity(intent3);
                }
                else if(dataname=="d2" && position==1) {
                    title="Midnight in Paris";

                    Intent intent3 = new Intent(view.getContext(), VideoLearnMP.class);
                    intent3.putExtra("id", id);
                    intent3.putExtra("name", name);
                    intent3.putExtra("phone", phone);
                    intent3.putExtra("today", today);
                    intent3.putExtra("title", title);
                    Log.d("비디오리스트어답터..", id + " " + name + " " + phone + " " + today+" "+title);
                    view.getContext().startActivity(intent3);
                }
                else if(dataname=="d3" && position==0) {
                    title="Intern";

                    Intent intent4 = new Intent(view.getContext(), VideoLearnIT.class);
                    intent4.putExtra("id", id);
                    intent4.putExtra("name", name);
                    intent4.putExtra("phone", phone);
                    intent4.putExtra("today", today);
                    intent4.putExtra("title", title);
                    Log.d("비디오리스트어답터..", id + " " + name + " " + phone + " " + today+" "+title);
                    view.getContext().startActivity(intent4);
                }
                else if(dataname=="d3" && position==1) {
                    title="The Big Short";

                    Intent intent4 = new Intent(view.getContext(), VideoLearnBS.class);
                    intent4.putExtra("id", id);
                    intent4.putExtra("name", name);
                    intent4.putExtra("phone", phone);
                    intent4.putExtra("today", today);
                    intent4.putExtra("title", title);
                    Log.d("비디오리스트어답터..", id + " " + name + " " + phone + " " + today+" "+title);
                    view.getContext().startActivity(intent4);
                }
                else if(dataname=="d4" && position==0) {
                    title="Toy Story4";

                    Intent intent4 = new Intent(view.getContext(), VideoLearnTS.class);
                    intent4.putExtra("id", id);
                    intent4.putExtra("name", name);
                    intent4.putExtra("phone", phone);
                    intent4.putExtra("today", today);
                    intent4.putExtra("title", title);
                    Log.d("비디오리스트어답터..", id + " " + name + " " + phone + " " + today+" "+title);
                    view.getContext().startActivity(intent4);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return verticalDatas.size();
    }


    public void getData(String id, String name, String phone, String today) {
        this.id=id;
        this.name=name;
        this.phone=phone;
        this.today=today;
    }
}