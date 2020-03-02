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

public class ReviewQuestionTS extends AppCompatActivity {
    private SwipeRefreshLayout refreshLayout = null;
    private static final int REQUEST_CODE = 1234;
    ImageView btn_toolbar_back;
    Button word_3, btn_stt;
    TextView q, a, a_3;
    CheckBox cb;
    ArrayList<String> matches_text;
    int position = 0;

    String [] kor = {
            "자 주목! 보니가 학교에서 새 친구를 만들었어!",
            "오 벌써 새 친구를 사귀었구나",
            "그게 아니야. 말 그대로 친구를 '만들었다고'",
            "여러분에게 소개할게. 새 친구 포키!",
            "아...안녕?",
            "안녕!",
            "쟤는 숟가락이잖아",
            "나도 알아",
            "포키는 보니에게 가장 중요한 장난감이야",
            "그러니 포키에게 무슨 일이 일어나지 않도록 해야 해",
            "우디, 안 좋은 상황이 발생했어",
            "난 장난감이 아니야",
            "난 그저 수프나 샐러드, 고추를 먹기 위한 도구야 그리고 쓰레기통으로 가야 할 운명이야",
            "자유를 위하여!",
            "버즈! 포키를 데리고 와야 해!",
            "알겠다 오버",
            "근데 난 왜 살아있어?",
            "넌 보니의 장난감이니까",
            "넌 보니가 평생동안 기억할 행복한 추억을 만들어 줘야 해",
            "응? 뭐라고?",
            "보? 포키, 얼른 들어와!",
            "보? 거기 있어?",
            "안녕 친구들! 내 이름은 '개비 개비'야",
            "우린 가봐야 해",
            "너희들은 못가 친구들~",
            "우디, 뒤쪽이야!",
            "보! 여기서 뭐 하는거야?",
            "설명할 시간이 없어. 날 따라와",
            "우린 친구들에게 돌아가야 해",
            "역시 보안관 우디 항상 남을 도우려고 하지",
            "보니에겐 포키가 필요해",
            "우디, 이 모든걸 가졌을 때 과연 보니 방으로 돌아갈 필요가 있을까?",
            "우와.",
            "우디, 보니에게 돌아갈거야?",
            "우디랑 포키를 찾아야 해!",
            "하지만 어떻게 찾지, 버즈?",
            "어떻게 찾지?",
            "일단 차에서 나가야지",
            "가보자!",
            "그래 가보자 가보자",
            "있잖아 보, 너는 버려진 장난감들에게 새로운 삶을 주었어",
            "눈 크게 뜨고 봐봐 우디",
            "저 밖엔 많은 아이들이 있어",
            "떄론 변화도 괜찮은 방법이야",
            "하지만 장난감들이 새 주인을 만나지는 않을거야",
            "보면 놀랄껄",
            "보니잖아?!",
            "포키, 우린 집에 갈 수 있어!",
            "차가 오고 있어!",
            "우디, 내가 간다!",
            "무한한 공간 저 넘어로!",
            "우디가 못 떠나게 막아!",
            "아이들은 매일 장난감 곁을 떠나고 있어",
            "난 아이들과 함께 하기 위해 만들어졌어",
            "이걸 계속 기억하려고 노력하고 있고",
            "우디, 누군가 너에게 속삭이고 있어",
            "전부 다 괜찮아질거야"};


    String [] eng = {
            "Everyone, Bonnie made a friend in class!",
            "Oh she's already making friends.",
            "No, no she literally made a new friend.",
            "I want you to meet ... Forky!",
            "H-h-h-hi?",
            "Hello! Hi!",
            "He's a spork!",
            "Yes, yeah, I know.",
            "Forky is the most important toy to Bonnie right now.",
            "We all have to make sure nothing happens to him.",
            "Woody! We have a situation.",
            "I am not a toy.",
            "I was made for soup, salad, maybe chili, and then the trash!",
            "Freedom!",
            "Buzz! We've gotta get Forky!",
            "Affirmative!",
            "Why am I alive?",
            "You're Bonnie's toy.",
            "You are going to help create happy memories that will last for the rest of her life!",
            "Huh? Wha?",
            "Bo? Forky, come on!",
            "Bo?",
            "Hi there! My name is Gabby Gabby.",
            "We can't stay.",
            "Haha, yes you can. Boys?",
            "Woody, Behind you!",
            "Bo! What are you doing here?",
            "No time to explain. Come with me.",
            "We need to get back to our kid.",
            "Aww, Sheriff Woody, always comin'to the rescue.",
            "Bonnie needs Forky.",
            "Woody, who needs a kid's room when you can have all of this...?",
            "Wow.",
            "Woody, aren't we goin' to Bonnie?",
            "We have to find them.",
            "What do we do, Buzz?",
            "What would Woody do?",
            "Jump out of a moving vehicle.",
            "Let's go!",
            "Hey, if you gotta go, you gotta go.",
            "You know, you've handled this lost toy life better than I could.",
            "Open your eyes Woody.",
            "There's plenty of kids out there.",
            "Sometimes change can be good.",
            "You can't teach this old toy new tricks.",
            "You'd be surprised.",
            "Bonnie?!",
            "We're going home, Forky!",
            "Bonnie, I'm comin'!",
            "On my way Woody!",
            "To infinity... and beyond!",
            "Don't let Woody leave!",
            "Kids lose their toys every day.",
            "I was made to help a child.",
            "I don't remember it being this hard.",
            "Woody? Somebody's whispering in your ear...",
            "Everything's gomma be ok."};

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
                Intent intent = new Intent(ReviewQuestionTS.this, ReviewWordTS.class);
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

