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

public class ReviewQuestionBS extends AppCompatActivity {
    private SwipeRefreshLayout refreshLayout = null;
    private static final int REQUEST_CODE = 1234;
    ImageView btn_toolbar_back;
    Button word_3, btn_stt;
    TextView q, a, a_3;
    CheckBox cb;
    ArrayList<String> matches_text;
    int position = 0;

    String [] kor = {
            "마이클, 어떻게 지내나?",
            "진짜 흥미로운걸 알아냈어요.",
            "주택시장 전체를",
            "이 부실대출이 받쳐주고 있어요.",
            "이건 무너질 겁니다.",
            "주택시장은 바위처럼 단단해.",
            "이건 시한폭탄이에요.",
            "그니까 마이크 버리,",
            "이발은 '슈퍼컷츠'에서 하고 신발을 안 신는 친구가",
            "알랜 그린스펀보다 잘 안다?",
            "닥터 마이크 버리요.",
            "네, 그렇습니다.",
            "이거 알아? 나 화났어.",
            "미국민들이 대형 은행들에게 속고 있어.",
            "난 점점 더 화가 나는 거야.",
            "믿기지가 않는군.",
            "그러자 이놈이 내 사무실에 들어와 하는 말이...",
            "뭔가 미심쩍은 기운이 일고 있어.",
            "모든 은행들이 오랫동안 성대한 파티를 벌였지.",
            "몇몇 아웃사이더들이 아무도 못 본 걸 보았어.",
            "전세계 경제가 붕괴될지 몰라.",
            "전세계 은행들은",
            "탐욕보단 더 큰 목적으로 움직인다고 전 확신하지요.",
            "틀렸습니다.",
            "아무도 주의를 안 기울여.",
            "은행들은 탐욕스러워졌어.",
            "우린 저들의 어리석음을 통해 이득을 볼 수 있어.",
            "은행을 상대로 내기를 아겠다고?",
            "우리가 약을 했거나 뇌졸중에 걸린 줄 알겠지.",
            "그런대로 기발하군.",
            "사기는 한 번도 잘된 적이 없습니다.",
            "결국에는",
            "모든게 틀어지죠.",
            "우린 대체 언제 이를 까먹은 겁니까?",
            "은행들이 어떻게 가만 있겠어?",
            "이건 어리석음을 원동력으로 하니까.",
            "이건 어리석은게 아니라 사기야",
            "어리석음과 불법의 차이를 말해봐.",
            "내 처남 좀 구속시키게.",
            "내가 뭘 했는진 알아?",
            "넌 미국 경제를 상대로 내기를 한거야.",
            "자네가 틀리면 자넨 다 잃을 수 있어",
            "은행들은 미국민을 상대로 사기를 쳤어",
            "이제 놈들 치아에 킥을 먹인다.",
            "좋아, 시작이다.",
            "스트리퍼들을 노리겠다?",
            "부실대출을 한?",
            "걔들은 다 현금 부자지.",
            "넌 재융자 못하게 될거야.",
            "제 대출금 다요?",
            "대출금 다라니?",
            "전 집이 다섯채에",
            "콘도가 하나에요."};


    String [] eng = {
            "Michael how are you?",
            "I found something really interesting.",
            "Whole housing market",
            "is propped up on these bad loans.",
            "They will fail.",
            "The housing market is rock solid.",
            "It's a time off.",
            "So Mike burry,",
            "Who gets his haircut Supercuts and dosen't wear shoes",
            "knows more than Alan Greenspan?",
            "Dr.Mike Burry",
            "Yes, he dose.",
            "You know what? I'm pissed off.",
            "American people are getting screwed by the big banks.",
            "And I am getting madder and madder.",
            "It's unbelievable.",
            "Then this guy walks into my office and says...",
            "There's some shady stuff going down.",
            "While the banks we're having a big old party.",
            "A few outsiders saw where no one else could.",
            "Whole world economy might collapse.",
            "I'm sure the world's banks",
            "have more descent of the greed.",
            "You're wrong.",
            "No one's paying attention.",
            "The bank's got greedy",
            "and we can profit off of their stupidity.",
            "You want to bet against the bank's?",
            "To think we're either high or having a stroke.",
            "Kind or brilliant.",
            "Fraud has never ever worked",
            "Eventually",
            "things go south.",
            "When the hell did we forget all that?",
            "How can the banks let this happen?",
            "It's freled by stupidity.",
            "But that's not stupidity. It's fraud.",
            "Tell me the difference between stupid and illegal",
            "and I'll have my wife's brother arrested.",
            "You have any idea what you did?",
            "You just against the American economy.",
            "If you're wrong you can lose it all.",
            "The banks defrauded of the American people.",
            "Now we can kick aridity.",
            "Okay, here we go.",
            "Retard of strippers?",
            "It's bad loans?",
            "We have no cash shred",
            "Not going to be able to refinance on",
            "On all my loans?",
            "What do you mean all your loan?",
            "What do you mean all your loan?",
            "in a condo."};

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
                Intent intent = new Intent(ReviewQuestionBS.this, ReviewWordBS.class);
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

