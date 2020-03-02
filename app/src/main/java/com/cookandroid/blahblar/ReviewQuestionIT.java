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

public class ReviewQuestionIT extends AppCompatActivity {
    private SwipeRefreshLayout refreshLayout = null;
    private static final int REQUEST_CODE = 1234;
    ImageView btn_toolbar_back;
    Button word_3, btn_stt;
    TextView q, a, a_3;
    CheckBox cb;
    ArrayList<String> matches_text;
    int position = 0;

    String [] kor = {
            "난 그저 내 삶에 난 구멍을 채우고 싶어요",
            "벤 휘태커인데 오스틴 대표님과 면담 있어요",
            "그녀는 인턴 만나시기로 했는데요",
            "연세가 어떻게 되시죠?",
            "70이요, 그 쪽은?",
            "24살이요. 일하느라 폭삭 늙었죠",
            "벤입니다. 새 인턴이죠",
            "정장 안 입으셔도 돼요",
            "난 정장이 편한데 괜찮겠죠?",
            "눈에도 잘 띄고 좋죠",
            "옷으로 튀실 필요는 없어요",
            "닫을까요? 열어둘까요?",
            "상관없어요",
            "열어두세요",
            "곧 제 변덕에 익숙해지실 거예요",
            "기대하죠",
            "새 인턴은 항상 바쁘네",
            "완전 인기남이야. 다들 좋아해",
            "베키, 안녕? 오늘 너무 예쁘다",
            "여자가 화나면 얼마나 가죠?",
            "뭘 잘못했는데? 사과는 했어?",
            "이메일을 보냈죠",
            "진심이 담긴 장문의 이메일이었는데 제목도 '진짜 미아아아아아아안해'고 아주 슬픈 표정의 이모티콘도 곁들였죠",
            "투자가들은 노련한 CEO가 경영에 참여하길 원해",
            "이런 날이 올 줄 몰랐어",
            "줄리 대표님은 회사와 가족 모두를 위해 최선을 다하죠",
            "분명 스트레스가 심할거예요",
            "1년 반 전에 혼자 창업해서 직원 220명의 회사로 키운 게 나예요",
            "그걸 잊지 말아요",
            "덕분에 차분해지고 자신감도 생겨요",
            "피오나예요. 사내 마사지사죠",
            "같은 연배를 만나 너무 좋네요",
            "어때요 벤?",
            "아직 한창이시네요!"};


    String [] eng = {
           "I just know there’s a hole in my life and I need to fill it.",
           "I’m Ben Whittaker. I have an appointment with Ms. Ostin.",
           "I thought she was meeting with her new intern.",
           "How old are you?",
           "Seventy. You?",
           "I’m 24. I know, I look older. It’s the job.",
           "I’m Ben, your new intern.",
           "Don’t feel like you have to dress up.",
           "I’m comfortable in a suit, if it’s okay.",
           "At least, I’ll stand out.",
           "I don’t think you need a suit to do that.",
           "Want the door open or closed?",
           "Doesn’t matter.",
           "Open, actually.",
           "You’ll get used to me.",
           "Look forward to it.",
           "My intern sure keeps busy.",
           "Mr. Congeniality. Everybody loves him.",
           "Hey, Beck. What’s up? You look really nice and…",
           "How long can a woman be mad at you for?",
           "I assume you talked to her, apologized.",
           "I e-mailed her.",
           "Subject line I wrote, I’m sorry with like a ton of “O’s”. So it was like, “I’m sorry,”with a sad emoticon where he’s crying.",
           "Our investors just think a seasoned CEO could take some things off your plate.",
           "I did not see that coming.",
           "Jules is tryin’ to do right by everybody, the company, the family.",
           "Pressure is unbelievable.",
           "You started this business allby yourself a year and a half ago, and now you have a staff of 220 people.",
           "Remember who did that.",
           "Something about you makes me feel calm or more centered or something.",
           "I’m Fiona, the house masseuse.",
           "There’s another oldie but goodie here.",
           "How’s that, Ben?",
           "You’re not as old as I thought you were."};

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

        /*//x축 라벨 (예시)
        ArrayList<String> barlabels_3 = new ArrayList<String>();
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
                Intent intent = new Intent(ReviewQuestionIT.this, ReviewWordIT.class);
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

