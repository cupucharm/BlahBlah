package com.cookandroid.blahblar;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ReviewQuestionGP extends AppCompatActivity {
    private SwipeRefreshLayout refreshLayout = null;
    private static final int REQUEST_CODE = 1234;
    ImageView btn_toolbar_back;
    Button word_3, btn_stt;
    TextView q, a, a_3;
    CheckBox cb;
    ArrayList<String> matches_text;
    int position = 0;

    String [] kor = {
            "안녕하세요. 오늘 기분 어떠세요?",
            "궁금한게 있는데요",
            "여긴 어디고 당신은 누구죠? 어떻게 된 거예요?",
            "엘리너 셸스트롭 씨 당신은 죽었어요.",
            "사후 세계에는 좋은 곳과 나쁜 곳이 있습니다.",
            "당신은 좋은 곳에 오셨어요.",
            "우리가 생전에 한 일엔 착한 일과 나쁜 일이 있죠.",
            "점수가 아주 높은 사람만이 좋은 곳에 올 수 있어요.",
            "난 치디 아나곤예예요.",
            "당신의 소울메이트죠.",
            "당신에게 상처가 될 말이나 행동은 절대 하지 않겠어요.",
            "나 원래 여기 오면 안 돼요.",
            "이 사람들이 착한 사람들일지는 몰라도 꼭 나보다 나은 건 아닐 수도 있잖아요.",
            "후딱 위층에 올라가서 금으로 된 물건을 훔쳐 올게요.",
            "다 당신 때문에 이렇게 됐어요.",
            "세상을 부숴버렸다고요.",
            "칭찬 아니에요.",
            "나한테 기회를 줘요.",
            "여기 어울리는 사람이 될게요",
            "착한 사람이 되려고 노력하겠다는 거군요.",
            "시험도 보고 숙제도 낼 거예요.",
            "엄청 신나겠죠?",
            "이래서 나한테 뭐가 좋은데요?",
            "지옥에서 영원히 썩고 싶어요?"};


    String [] eng = {
          "Hi, Eleanor. How are you today?",
          "One question",
          "Where am I? Who are you? And what's going on?",
          "You, Eleanor Shellstro, are dead.",
          "In the afterlife, there's a Good Place, and there's a Bad Place.",
          "You're in the Good Place.",
          "Everything you did ultimately created some amount of good or bad.",
          "Only the people with the very highest scores get to come here.",
          "I'm Chidi Anagonye.",
          "And you are my soulmate.",
          " I swear that I will never say or do anything to cause you any harm.",
          "I'm not supposed to be here",
          "These people might be good, but are they really that much better than me?",
          "I just have to go upstairs real quick and steal a bunch of gold stuff.",
          "This is all happening because of you.",
          "You broke the world.",
          "It's not a compliment.",
          "Give me a chance.",
          "Let me earn my place here.",
          "You're trying to learn how to be a good person.",
          "We're gonna have quizzes and papers.",
          "It's gonna be so much fun.",
          "Remind me what I'm getting out of this again?",
          "You get to avoid eternal damnation."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        permissionCheck();

        int random = (int)(Math.random()*(eng.length-1));

        q = (TextView) findViewById(R.id.q_3);
        a = (TextView) findViewById(R.id.aTitle_3);
        a.setVisibility(View.INVISIBLE);
        q.setText(kor[random]);
        a.setText(eng[random]);
        a_3 = (TextView) findViewById(R.id.a_3);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);

        btn_stt = (Button) findViewById(R.id.btn_stt);


        btn_stt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //promptSpeechInput();

                if(isConnected()){
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");  // 언어 영어
                    intent.putExtra("android.speech.extra.GET_AUDIO_FORMAT", "audio/mp4");
                    intent.putExtra("android.speech.extra.GET_AUDIO", true);
                    startActivityForResult(intent, REQUEST_CODE);


                }
                else{
                    Toast.makeText(getApplicationContext(), "Plese Connect to Internet", Toast.LENGTH_LONG).show();
                }
            }
        });


        cb = (CheckBox) findViewById(R.id.cb);
        cb.setOnClickListener(new CheckBox.OnClickListener(){
            @Override
            public void onClick(View v){
                if(((CheckBox)v).isChecked()){
                    cb.setText("정답 가리기");
                    a.setVisibility(View.VISIBLE);
                } else {
                    cb.setText("정답 보기");
                    a.setVisibility(View.INVISIBLE);
                }
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //새로고침 소스
                int random = (int)(Math.random()*(eng.length-1));

                a.setVisibility(View.INVISIBLE);
                q.setText(kor[random]);
                a.setText(eng[random]);

                cb.setChecked(false);
                a_3.setText("내 정답");

                refreshLayout.setRefreshing(false);
            }
        });

        btn_toolbar_back = (ImageView) findViewById(R.id.btn_toolbar_back_r);
        btn_toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //x축 라벨 (예시)
       /* ArrayList<String> barlabels_3 = new ArrayList<String>();
        barlabels_3.add("발음");  //a
        barlabels_3.add("문법");  //b
        barlabels_3.add("어휘");  //c
        barlabels_3.add("억양");  //d
        barlabels_3.add("유창성"); //e
        barlabels_3.add("평균");  //avg

        int a=2, b=7, c=5, d=3, e=8, avg;
        avg = (a+b+c+d+e)/5;

        // 표시할 데이터 (예시)
        ArrayList<BarEntry> barentries_3 = new ArrayList<>();
        barentries_3.add(new BarEntry(a, 0));
        barentries_3.add(new BarEntry(b, 1));
        barentries_3.add(new BarEntry(c, 2));
        barentries_3.add(new BarEntry(d, 3));
        barentries_3.add(new BarEntry(e, 4));
        barentries_3.add(new BarEntry(avg, 5));

        //데이터 셋 설정
        int Colors[] = {Color.GREEN,Color.GREEN,Color.GREEN,Color.GREEN,Color.GREEN,Color.YELLOW};  //막대 색깔
        BarChart barChart = (BarChart)findViewById(R.id.Barchart_3);
        BarDataSet barDataSet = new BarDataSet(barentries_3,"평가 그래프");
        barDataSet.setColors(Colors);

        BarData barData = new BarData(barlabels_3, barDataSet);
        barChart.setData(barData);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(15);

        barChart.setDoubleTapToZoomEnabled(false);
        barChart.setDrawGridBackground(false);
        barChart.setDescription("");
        barChart.animateY(2000, Easing.EasingOption.EaseInCubic);
        barChart.invalidate();
*/
        // 단어장
        word_3 = (Button)findViewById(R.id.word_3);

        word_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReviewQuestionGP.this, ReviewWordGP.class);
                startActivity(intent);
            }
        });
    }
    public  boolean isConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();
        if (net!=null && net.isAvailable() && net.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public void permissionCheck(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            matches_text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            a_3.setText(matches_text.get(position));
            if(a_3.getText().toString()!=null){

            }
        }
    }
}

