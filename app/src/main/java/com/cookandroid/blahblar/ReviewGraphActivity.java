package com.cookandroid.blahblar;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import java.util.ArrayList;

public class ReviewGraphActivity extends AppCompatActivity {
    ImageView btn_toolbar_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        btn_toolbar_back = (ImageView) findViewById(R.id.btn_toolbar_back_r);
        btn_toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //x축 라벨 (예시)
        ArrayList<String> linelabels_3 = new ArrayList<String>();
        linelabels_3.add("1/1");
        linelabels_3.add("1/2");
        linelabels_3.add("1/3");
        linelabels_3.add("1/4");
        linelabels_3.add("1/5");
        linelabels_3.add("1/6");

        // 표시할 데이터 (예시)
        ArrayList<Entry> lineentries_3 = new ArrayList<>();
        lineentries_3.add(new Entry(1, 0));
        lineentries_3.add(new Entry(2, 1));
        lineentries_3.add(new Entry(3, 2));
        lineentries_3.add(new Entry(8, 3));
        lineentries_3.add(new Entry(5, 4));
        lineentries_3.add(new Entry(6, 5));

        //데이터 셋 설정
        LineChart lineChart = (LineChart) findViewById(R.id.Linechart_3);
        LineDataSet lineDataSet = new LineDataSet(lineentries_3, "학습능력 변화");
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleColorHole(Color.BLUE);
        lineDataSet.setColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawValues(true);

        LineData lineData = new LineData(linelabels_3, lineDataSet);
        lineChart.setData(lineData); // set the data and list of lables into chart

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.enableGridDashedLine(8, 24, 0);

        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);
        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription("");
        lineChart.animateY(2000, Easing.EasingOption.EaseInCubic);
        lineChart.invalidate();
    }
}