package com.cookandroid.blahblar;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ReviewQuestionActivity extends AppCompatActivity {
    private SwipeRefreshLayout refreshLayout = null;
    private static final int REQUEST_CODE = 1234;
    ImageView btn_toolbar_back;
    Button word_3, btn_stt;
    TextView q, answer, answer3;
    CheckBox cb;
    ArrayList<String> matches_text;
    int position = 0;

    String [] kor = {
            "라임시티에 잘 오셨어요.",
            "인간과 포켓몬이 공존하는 도시죠.",
            "네 아빠는 전설적인 분이셨어.",
            "어릴 때 포켓몬 트레이너 되고 싶어했지?",
            "네, 잘 안 풀렸죠.",
            "나 이거 쓸 줄 알아.",
            "스테이플러 내려놔",
            "아님 전기로 쏴 버린다.",
            "너 지금 말했어?",
            "너 지금 알아 들었어?",
            "맙소사 내 말을 알아 듣다니!",
            "나 넘나 외로웠어!",
            "다들 나랑 말 하고 싶어도 내 답은 '피카 피카'로 들린대.",
            "말하는 거 들리죠?",
            "귀여워라!",
            "못 알아듣는다니까",
            "안들려요?!",
            "난 포켓몬 필요없어",
            "세계적인 명탐정이라면?",
            "같이 하는 거지 너랑 나랑.",
            "우릴 만나게 한 게 마법인가 봐. 희망이라는 마법.",
            "말 안하면 마임 시킨다.",
            "아는 거 다 불어.",
            "내가 사람들을 밀어내고는 날 떠났다고 사람들을 원망한대.",
            "집어치우래.",
            "뭐? 집어치우라고?",
            "그럼 이제 방법 바꿔야겠다.",
            "난 나쁜 형사, 넌 좋은 형사.",
            "우리 형사 아냐.",
            "이러려던 게 아닌데."};


    String [] eng = {
            "Welcome to Ryme City.",
            "A celebration of the harmony between humans and Pokemon.",
            "your dad was a legend in this precinct.",
            "I remember you wanted to be a Pokemon trainer when you were young.",
            "Yeah, that didn’t really work out.",
            "I know how to use this.",
            "put down the stapler",
            "or I will electrocute you.",
            "Did you just talk?",
            "Did you just understand me?",
            "Oh, my God! You can understand me!",
            "I have been so lonely!",
            "They try to talk to me all the time. All they hear is “Pika-Pika.”",
            "You can hear him, right?",
            "You’re adorable!",
            "They can’t understand me, kid.",
            "Can no one else hear him?!",
            "I don’t need a Pokemon. Period",
            "Then what about a world-class detective?",
            "We’re gonna do this, you and me.",
            "There’s magic. It brought us together and that magic is called hope.",
            "Listen up, we got ways to make you talk, or mime…",
            "So tell us what we wanna know.",
            "problem is that I push people away and then hate them for leaving.",
            "He’s saying you can shove it.",
            "What? I can shove it?",
            "We’re switching roles.",
            "I’m bad cop, you’re good cop.",
            "No, we’re not cops.",
            "In my head, I saw that differently."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        //stt 사용허가
        permissionCheck();

        int random = (int)(Math.random()*(eng.length-1));

        q = (TextView) findViewById(R.id.q_3);
        answer = (TextView) findViewById(R.id.aTitle_3);
        answer.setVisibility(View.INVISIBLE);
        q.setText(kor[random]);
        answer.setText(eng[random]);
        answer3 = (TextView) findViewById(R.id.a_3);

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
                    answer.setVisibility(View.VISIBLE);
                } else {
                    cb.setText("정답 보기");
                    answer.setVisibility(View.INVISIBLE);
                }
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //새로고침 소스
                int random = (int)(Math.random()*(eng.length-1));

                answer.setVisibility(View.INVISIBLE);
                q.setText(kor[random]);
                answer.setText(eng[random]);

                cb.setChecked(false);
                answer3.setText("내 정답");

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


       /* //단어 채점 알고리즘
        String[] answer_sp = answer.toString().split(" ");
        String[] answer3_sp = answer3.toString().split(" ");

        int word_checher = 0;

        for(int i=0; i<answer_sp.length; i++){
            for(int j=0; j<answer3_sp.length; j++){
                if(answer_sp[i].equals(answer3_sp[j]))
                    word_checher++;
            }
        }*/

        //x축 라벨 (예시)
        ArrayList<String> barlabels_3 = new ArrayList<String>();
        barlabels_3.add("발음");  //a
       /* barlabels_3.add("문법");  //b
        barlabels_3.add("어휘");  //c
        barlabels_3.add("억양");  //d
        barlabels_3.add("유창성"); //e
        barlabels_3.add("평균");  //avg
*/
        int a=2, b=7, c=5, d=3, e=8, avg;
        avg = (a+b+c+d+e)/5;

        //a = 100/answer_sp.length * word_checher;  // 어휘 점수

        // 표시할 데이터 (예시)
        ArrayList<BarEntry> barentries_3 = new ArrayList<>();
        barentries_3.add(new BarEntry(a, 0));
       /* barentries_3.add(new BarEntry(b, 1));
        barentries_3.add(new BarEntry(c, 2));
        barentries_3.add(new BarEntry(d, 3));
        barentries_3.add(new BarEntry(e, 4));
        barentries_3.add(new BarEntry(avg, 5));

        //데이터 셋 설정
        int Colors[] = {Color.GREEN,Color.GREEN,Color.GREEN,Color.GREEN,Color.GREEN,Color.YELLOW};  //막대 색깔
        BarChart barChart = (BarChart)findViewById(R.id.Barchart_3);
        BarDataSet barDataSet = new BarDataSet(barentries_3,"평가 그래프");
        barDataSet.setColor(Color.GREEN);//Colors

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
                Intent intent = new Intent(ReviewQuestionActivity.this, ReviewWordActivity.class);
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

            answer3.setText(matches_text.get(position));
            if(answer3.getText().toString()!=null){

            }
        }
    }
}

