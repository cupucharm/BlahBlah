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
public class ReviewQuestionBK extends AppCompatActivity {
    private SwipeRefreshLayout refreshLayout = null;
    private static final int REQUEST_CODE = 1234;
    ImageView btn_toolbar_back;
    Button word_3, btn_stt;
    TextView q, a, a_3;
    CheckBox cb;
    ArrayList<String> matches_text;
    int position = 0;

    String [] kor = {
            "이쪽으로 와주세요.",
            "거기 줄요.",
            "다음요.",
            "여권 주세요.",
            "미국에 오신걸 환영합니다. 아가씨.",
            "로즈에게",
            "언니랑 엄마가 보고 싶어. 매일같이 가족 생각을 해.",
            "제일 중요한 소식은 내가 취직을 했고 하숙을 한다는거야.",
            "오늘 드디어 고향집 편지를 받았다니 잘됐구나.",
            "아일랜드에서 아일랜드 여자로 남았다면 하는 생각이 안들었으면 좋겠어요.",
            "향수병은 대개의 병들과 똑같지.",
            "곧 사라질거야.",
            "저랑 춤추실래요?",
            "전 아일랜드인이 아니에요.",
            "그럼 아일랜드인 무도회엔 왜 왔어요?",
            "전 아일랜드 여자들이 정말 좋아요.",
            "누굴 만났는데 이탈리아 남자예요.",
            "우린 주말에 '코니 아일랜드'에 갈 거예요.",
            "수영복은 있어?",
            "옷 속에다 수영복 입으라고 왜 말 안 했어?",
            "아는 줄 알았지.",
            "토니!",
            "네게 할 말이 있는데, ",
            "넌 이러겠지만 \"어머, 그건 너무 일러.\"",
            "저녁 먹을 겸 우리 가족 보러 안 올래?",
            "그러고 말고",
            "이탈리아 요리 좋아해?",
            "문제가 보이면 튀었다고 할게.",
            "생각 좋네.",
            "튀었어!",
            "걔네 부모님이랑 벽에 튀었어.",
            "다시 해.",
            "준비 됐어?",
            "우린 아일랜드인을 안 좋아해요",
            "야!.",
            "왜요?",
            "안 좋아하잖아요!",
            "잘 알려진 사실인데요!",
            "일어나.",
            "유감이네.",
            "장례는 언제 치르죠?",
            "집에 가고 싶은가봐?",
            "내가 집에 가면 넌 어떨 것 같아?",
            "염려될 거야.",
            "내가 안 돌아올까봐?",
            "응. ",
            "집은 집이잖아.",
            "아일랜드는 이제 너무 낙후되어 보이겠네요.",
            "그 남자 짐 패럴 아니니?",
            "잘 어울리는 상대구나.",
            "제가 살 곳은 바다 건너에 있어요.",
            "여기서의 삶도 괜찮을지 모르죠.",
            "네가 돌아가면, 난 아무도 없어.",
            "여기 있어주면 좋겠어요. 저랑요.",
            "집은 집이잖아."};


    String [] eng = {
            "Step over this way, please.",
            "Get out of the line.",
            "Next.",
            "Passport, please.",
            "Welcome to the United States, Ma'am.",
            "Dear Rose.",
            "I miss you and Mother and think about you every day.",
            "The most important news is that I have a job and I'm in a boarding house.",
            "I was glad to see you finally got some letters from home today.",
            "I wish that I could stop feeling that I want to be an Irish girl in Ireland.",
            "Homesickness is like most sicknesses.",
            "It will pass.",
            "Would you dance with me?",
            "I'm not Irish.",
            "So what were you doing at an Irish dance?",
            "I really like Irish girls.",
            "I met somebody and Italian fella.",
            "We're going to Coney Island at the weekend.",
            "But do you have a bathing costume?",
            "Why didn't you tell me to put my costume on underneath my clothes?",
            "I thought you'd know.",
            "Tony!",
            "I wanna ask you something.",
            "And you're gonna say \"Oh, it's too soon.",
            "Will you come for dinner and meet my family?",
            "I'm gonna say splash any time I see problems.",
            "You like Italian food?",
            "I'm gonna say splash any time I see problems.",
            "Good idea.",
            "Splash!",
            "You just splashed his mother, his father, and the walls.",
            "Let's go again.",
            "Ready?",
            "I should say that we don't like Irish people.",
            "Hey, hey.",
            "What?",
            "We don't.",
            "That is a well-known fact.",
            "Up.",
            "I'm sorry.",
            "When will they bury her?",
            "You wanna go home, I guess.",
            "How would it be for you if I did go home?",
            "I'd be afraid.",
            "Afraid that I wouldn't come back?",
            "Yeah.",
            "Home is home.",
            "Ireland must seem very backward to you now.",
            "Is that Jim Farrell I saw?",
            "He's a catch for someone.",
            "I have a life halfway across the sea.",
            "Your life here could be just as good.",
            "If you go back, I have nobody.",
            "I want you to stay here, with me.",
            "Home is home."};

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
                Intent intent = new Intent(ReviewQuestionBK.this, ReviewWordBK.class);
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


