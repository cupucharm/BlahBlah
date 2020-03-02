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

public class ReviewQuestionSS extends AppCompatActivity {
    private SwipeRefreshLayout refreshLayout = null;
    private static final int REQUEST_CODE = 1234;
    ImageView btn_toolbar_back;
    Button word_3, btn_stt;
    TextView q, a, a_3;
    CheckBox cb;
    ArrayList<String> matches_text;
    int position = 0;

    String [] kor = {
            "내 재킷 환불했어?",
            "판매원과 아주 진지한 대화를 나누고 둘 다 눈물을 흘린 후에 전액 환불 받았어요.",
            "내 비밀번호가 안 먹혀",
            "그건 제 노트북이에요",
            "노트북 가져와",
            "자정에 깨워줘",
            "하지만 놀라게 하지 마",
            "자장가를 틀고 천천히 볼륨을 높여",
            "여태 안 가고 뭐 해요?",
            "난 늘 마지막에 퇴근해요",
            "둘이 재미 볼 때 우린 자유예요",
            "두 사람이 사랑에 빠지게 잠깐 엘레베이터를 정지시켰으면 해요",
            "이 남자는 누구죠?",
            "얼른 나가요",
            "시작해보죠!",
            "맙소사, 내 악몽은 늘 이렇게 시작해",
            "뭐하는 거예요?",
            "그러지 마요, 당장 멈춰요",
            "이걸 벗어버려야 해요",
            "우린 둘의 모든 걸 알아요. 뭘 좋아하는지, 싫어하는지",
            "우리가 배후자예요",
            "릭한테 좋은 양키스 티켓 있어요",
            "아뇨, 처량하게 외야석에서 봐요. 당연히 있죠",
            "이 두 좌석에 앉은 사람들이 키스하게 해줘요",
            "이 세상은 우리 거예요!",
            "자유다!",
            "오후엔 쉴게",
            "내일 늦을 거야",
            "우리가 해냈어요, 불행한 두 사람을 행복하게 만들었어요.",
            "우리가 자랑스러워요.",
            "둘이 어떻게 아는 사이죠?",
            "친구예요",
            "왜 그런 식으로 말해?",
            "왜 목소리 톤이 높아져?",
            "그 사람이 나한테 세상의 이치를 가르치려는 꼴은 못 봐",
            "애쓰지 말아요",
            "부모를 재결합시키려는 자식들 같아요",
            "그렇지 않아요",
            "린제이 로한의 옛날 영화를 하도 많이 봐서 이게 그 상황이라는 건 확실히 알아요",
            "내가 28살일 때도 비서라면 너무 비참할 거예요",
            "난 28살이에요",
            "맙소사 참 딱하네요 ",
            "당신이 너무 불쌍해요"};


    String [] eng = {
           "Did you return my jacket?",
           "And after a conversation with their sales woman that ended with us in tears, I got you a full refund",
           "This computer isn't taking my password",
           "That's my laptop",
           "Bring me my laptop",
           "Wake me up at midnight",
           "But don't startle me",
           "Play a lullaby, Then slowly increase the volume",
           "What are you doing here?",
           "I'm always the last one",
           "When they're boning, we're free",
           "We wanted to shut down the elevator so two people could fall in love",
           "Whoa, who's this guy?",
           "You need to go",
           "It's go time!",
           "Oh,god. This is how it starts in my nightmare",
           "What are you doing?",
           "Stop doing that, Stop right now",
           "I need to be free of this",
           "We know everything what they like, don't like",
           "We are the men behind the curtain",
           "Does Rick have good Yankees tickets?",
           "No, He watches from the bleachers like a peasant. Of course he does",
           "We need the people in these two seats to make out",
           "The world's our oyster",
           "We’re free.",
           "Taking the rest of the day.",
           "I’ll be late.",
           "We did it, We made two miserable people happy",
           "I'm proud of us",
           "Do you guys know each other?",
           "We're friends",
           "Why'd you say it like that?",
           "Why'd your voice go up?",
           "I’m not gonna pretend to let him teach me about how the world works.",
           " Save yourself.",
           "We’re full-on parent trapping.",
           "No, we’re not.",
           "I’ve seen the Lindsay Lohan classic enough times to know that we’re full-on parent trapping.",
           "All I care about is that I'm not still an assistant when I'm 28",
           "I'm 28",
           "Oh, my God. I'm so sorry",
           "For you. That's very sad for you"};

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
                Intent intent = new Intent(ReviewQuestionSS.this, ReviewWordSS.class);
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

