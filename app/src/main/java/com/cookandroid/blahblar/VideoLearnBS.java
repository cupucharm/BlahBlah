package com.cookandroid.blahblar;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import org.w3c.dom.Text;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.lang.Object;
import android.speech.RecognitionListener;

public class VideoLearnBS extends AppCompatActivity {
    private static final int REQUEST_CODE = 1234;
    public static final String RESULTS_RECOGNITION="DEFAULT";
    MediaPlayer player;
    MediaRecorder recorder;
    VideoView vv;
    TextView txtSpeechInput;
    Button btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    String id, name, phone, today, title;

    Dialog match_text_dialog;
    ListView textlist;
    ImageView btn_toolbar_back;
    ImageView btn_toolbar_v;
    ArrayList<String> matches_text;

    TextView eng;
    TextView kor;

    Button eng_btn;
    Button kor_btn;
    boolean eng_s=true;
    boolean kor_s=true;

    String filename;
    int position = 0;
    double[] stop_time = {15, 24, 36, 52, 62, 80, 93, 110, 130, 148};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videolearn);

        vv = (VideoView) findViewById(R.id.videoView);
        eng = (TextView) findViewById(R.id.english);
        kor = (TextView) findViewById(R.id.korea);
        eng_btn = (Button) findViewById(R.id.eng_btn);
        kor_btn = (Button) findViewById(R.id.kor_btn);
        Long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        today = sdf.format(date);

        Intent intent = getIntent();
        name = intent.getExtras().getString("name");
        id = intent.getExtras().getString("id");
        phone = intent.getExtras().getString("phone");
        //today = intent.getExtras().getString("today");
        title = intent.getExtras().getString("title");
        Log.d("비디오굿플..", id+ " " + name+" "+phone+" "+today);
        //Toast.makeText(this, id + " " + name + " " + phone + " " + today, Toast.LENGTH_LONG).show();
        //Toast.makeText(this, title, Toast.LENGTH_SHORT).show();


        btn_toolbar_back = (ImageView) findViewById(R.id.btn_toolbar_back_v);
        btn_toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_toolbar_v = (ImageView) findViewById(R.id.btn_toolbar_v);
        btn_toolbar_v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ShadowingBS.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("today", today);
                intent.putExtra("title", title);

                finish();
                startActivity(intent);  //이걸해야 intent에 넘어감
            }
        });

        eng_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                while(true) {
                    if (eng_s == true) {
                        eng.setVisibility(View.INVISIBLE);
                        eng_btn.setText("영어자막 켜기");
                        eng_s = false;
                        break;
                    }
                    if (eng_s == false) {
                        eng.setVisibility(View.VISIBLE);
                        eng_btn.setText("영어자막 끄기");
                        eng_s = true;
                        break;
                    }
                }
            }
        });

        kor_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                while (true) {
                    if (kor_s == true) {
                        kor.setVisibility(View.INVISIBLE);
                        kor_btn.setText("한글자막 켜기");
                        kor_s = false;
                        break;
                    }
                    if (kor_s == false) {
                        kor.setVisibility(View.VISIBLE);
                        kor_btn.setText("한글자막 끄기");
                        kor_s = true;
                        break;
                    }
                }
            }
        });

        MediaController mc = new MediaController(this);
        mc.setAnchorView(vv);

        Uri video = Uri.parse("android.resource://"+getPackageName()+"/raw/bigshort");
        vv.setMediaController(mc);
        vv.setVideoURI(video);
        vv.requestFocus();
        vv.start();

        new Thread() {
            @Override
            public void run() {
                while(true) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for(int i=0; i<stop_time.length; i++)
                                if (vv.getCurrentPosition() > (stop_time[i] * 1000 - 200) && vv.getCurrentPosition() < (stop_time[i] * 1000)) {
                                    vv.pause(); //일시정지
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            vv.start();
                                        }
                                    }, 3000);   //3초 후 다시 실행
                                }

                        }
                    });
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();




        new Thread() {
            @Override
            public void run() {
                while(true) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(vv.getCurrentPosition()<11000) eng.setText("");
                            else if(vv.getCurrentPosition()<13000) eng.setText("Michael how are you?");
                            else if(vv.getCurrentPosition()<17000) eng.setText("I found something really interesting.");
                            else if(vv.getCurrentPosition()<18000) eng.setText("Whole housing market");
                            else if(vv.getCurrentPosition()<21000) eng.setText("is propped up on these bad loans.");
                            else if(vv.getCurrentPosition()<23000) eng.setText("They will fail.");
                            else if(vv.getCurrentPosition()<27000) eng.setText("The housing market is rock solid.");
                            else if(vv.getCurrentPosition()<29000) eng.setText("It's a time off.");
                            else if(vv.getCurrentPosition()<30000) eng.setText("So Mike burry,");
                            else if(vv.getCurrentPosition()<32000) eng.setText("Who gets his haircut Supercuts and dosen't wear shoes");
                            else if(vv.getCurrentPosition()<34000) eng.setText("knows more than Alan Greenspan?");
                            else if(vv.getCurrentPosition()<35000) eng.setText("Dr.Mike Burry");
                            else if(vv.getCurrentPosition()<39000) eng.setText("Yes, he dose.");
                            else if(vv.getCurrentPosition()<41000) eng.setText("You know what? I'm pissed off.");
                            else if(vv.getCurrentPosition()<44000) eng.setText("American people are getting screwed by the big banks.");
                            else if(vv.getCurrentPosition()<46000) eng.setText("And I am getting madder and madder.");
                            else if(vv.getCurrentPosition()<47000) eng.setText("It's unbelievable.");
                            else if(vv.getCurrentPosition()<50000) eng.setText("Then this guy walks into my office and says...");
                            else if(vv.getCurrentPosition()<55000) eng.setText("There's some shady stuff going down.");
                            else if(vv.getCurrentPosition()<58000) eng.setText("While the banks we're having a big old party.");
                            else if(vv.getCurrentPosition()<61000) eng.setText("A few outsiders saw where no one else could.");
                            else if(vv.getCurrentPosition()<65000) eng.setText("Whole world economy might collapse.");
                            else if(vv.getCurrentPosition()<67000) eng.setText("I'm sure the world's banks");
                            else if(vv.getCurrentPosition()<70000) eng.setText("have more descent of the greed.");
                            else if(vv.getCurrentPosition()<72000) eng.setText("You're wrong.");
                            else if(vv.getCurrentPosition()<73000) eng.setText("No one's paying attention.");
                            else if(vv.getCurrentPosition()<75000) eng.setText("The bank's got greedy");
                            else if(vv.getCurrentPosition()<77000) eng.setText("and we can profit off of their stupidity.");
                            else if(vv.getCurrentPosition()<79000) eng.setText("You want to bet against the bank's?");
                            else if(vv.getCurrentPosition()<83000) eng.setText("To think we're either high or having a stroke.");
                            else if(vv.getCurrentPosition()<86000) eng.setText("Kind or brilliant.");
                            else if(vv.getCurrentPosition()<88000) eng.setText("Fraud has never ever worked ");
                            else if(vv.getCurrentPosition()<89000) eng.setText("eventually");
                            else if(vv.getCurrentPosition()<91000) eng.setText("things go south.");
                            else if(vv.getCurrentPosition()<100000) eng.setText("When the hell did we forget all that?");
                            else if(vv.getCurrentPosition()<101000) eng.setText("How can the banks let this happen?");
                            else if(vv.getCurrentPosition()<102000) eng.setText("It's freled by stupidity.");
                            else if(vv.getCurrentPosition()<105000) eng.setText("But that's not stupidity. It's fraud.");
                            else if(vv.getCurrentPosition()<106000) eng.setText("Tell me the difference between stupid and illegal");
                            else if(vv.getCurrentPosition()<107000) eng.setText("and I'll have my wife's brother arrested.");
                            else if(vv.getCurrentPosition()<119000) eng.setText("You have any idea what you did?");
                            else if(vv.getCurrentPosition()<123000) eng.setText("You just against the American economy.");
                            else if(vv.getCurrentPosition()<126000) eng.setText("If you're wrong you can lose it all.");
                            else if(vv.getCurrentPosition()<129000) eng.setText("The banks defrauded of the American people.");
                            else if(vv.getCurrentPosition()<130000) eng.setText("Now we can kick aridity.");
                            else if(vv.getCurrentPosition()<136000) eng.setText("Okay, here we go.");
                            else if(vv.getCurrentPosition()<137000) eng.setText("Retard of strippers?");
                            else if(vv.getCurrentPosition()<138000) eng.setText("It's bad loans?");
                            else if(vv.getCurrentPosition()<140000) eng.setText("We have no cash shred");
                            else if(vv.getCurrentPosition()<141000) eng.setText("Not going to be able to refinance on");
                            else if(vv.getCurrentPosition()<143000) eng.setText("On all my loans?");
                            else if(vv.getCurrentPosition()<144000) eng.setText("What do you mean all your loan?");
                            else if(vv.getCurrentPosition()<146000) eng.setText("I have five houses");
                            else kor.setText("in a condo.");


                        }
                    });
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();


        new Thread() {
            @Override
            public void run() {
                while(true) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(vv.getCurrentPosition()<11000) kor.setText("");
                            else if(vv.getCurrentPosition()<13000) kor.setText("마이클, 어떻게 지내나?");
                            else if(vv.getCurrentPosition()<17000) kor.setText("진짜 흥미로운걸 알아냈어요.");
                            else if(vv.getCurrentPosition()<18000) kor.setText("주택시장 전체를");
                            else if(vv.getCurrentPosition()<21000) kor.setText("이 부실대출이 받쳐주고 있어요.");
                            else if(vv.getCurrentPosition()<23000) kor.setText("이건 무너질 겁니다.");
                            else if(vv.getCurrentPosition()<27000) kor.setText("주택시장은 바위처럼 단단해.");
                            else if(vv.getCurrentPosition()<29000) kor.setText("이건 시한폭탄이에요.");
                            else if(vv.getCurrentPosition()<30000) kor.setText("그니까 마이크 버리,");
                            else if(vv.getCurrentPosition()<32000) kor.setText("이발은 '슈퍼컷츠'에서 하고 신발을 안 신는 친구가");
                            else if(vv.getCurrentPosition()<34000) kor.setText("알랜 그린스펀보다 잘 안다?");
                            else if(vv.getCurrentPosition()<35000) kor.setText("닥터 마이크 버리요.");
                            else if(vv.getCurrentPosition()<39000) kor.setText("네, 그렇습니다.");
                            else if(vv.getCurrentPosition()<41000) kor.setText("이거 알아? 나 빡쳤어.");
                            else if(vv.getCurrentPosition()<44000) kor.setText("미국민들이 대형 은행들에게 속고 있어.");
                            else if(vv.getCurrentPosition()<46000) kor.setText("난 점점 더 화가 나는 거야.");
                            else if(vv.getCurrentPosition()<47000) kor.setText("믿기지가 않는군.");
                            else if(vv.getCurrentPosition()<50000) kor.setText("그러자 이놈이 내 사무실에 들어와 하는 말이...");
                            else if(vv.getCurrentPosition()<55000) kor.setText("뭔가 미심쩍은 기운이 일고 있어.");
                            else if(vv.getCurrentPosition()<58000) kor.setText("모든 은행들이 오랫동안 성대한 파티를 벌였지.");
                            else if(vv.getCurrentPosition()<61000) kor.setText("몇몇 아웃사이더들이 아무도 못 본 걸 보았어.");
                            else if(vv.getCurrentPosition()<65000) kor.setText("전세계 경제가 붕괴될지 몰라.");
                            else if(vv.getCurrentPosition()<67000) kor.setText("전세계 은행들은");
                            else if(vv.getCurrentPosition()<70000) kor.setText("탐욕보단 더 큰 목적으로 움직인다고 전 확신하지요.");
                            else if(vv.getCurrentPosition()<72000) kor.setText("틀렸습니다.");
                            else if(vv.getCurrentPosition()<73000) kor.setText("아무도 주의를 안 기울여.");
                            else if(vv.getCurrentPosition()<75000) kor.setText("은행들은 탐욕스러워졌어.");
                            else if(vv.getCurrentPosition()<77000) kor.setText("우린 저들의 어리석음을 통해 이득을 볼 수 있어.");
                            else if(vv.getCurrentPosition()<79000) kor.setText("은행을 상대로 내기를 아겠다고?");
                            else if(vv.getCurrentPosition()<83000) kor.setText("우리가 약을 했거나 뇌졸중에 걸린 줄 알겠지.");
                            else if(vv.getCurrentPosition()<86000) kor.setText("그런대로 기발하군.");
                            else if(vv.getCurrentPosition()<88000) kor.setText("사기는 한 번도 잘된 적이 없습니다.");
                            else if(vv.getCurrentPosition()<89000) kor.setText("결국에는");
                            else if(vv.getCurrentPosition()<91000) kor.setText("모든게 틀어지죠.");
                            else if(vv.getCurrentPosition()<100000) kor.setText("우린 대체 언제 이를 까먹은 겁니까?");
                            else if(vv.getCurrentPosition()<101000) kor.setText("은행들이 어떻게 가만 있겠어?");
                            else if(vv.getCurrentPosition()<102000) kor.setText("이건 어리석음을 원동력으로 하니까.");
                            else if(vv.getCurrentPosition()<105000) kor.setText("이건 어리석은게 아니라 사기야");
                            else if(vv.getCurrentPosition()<106000) kor.setText("어리석음과 불법의 차이를 말해봐.");
                            else if(vv.getCurrentPosition()<107000) kor.setText("내 처남 좀 구속시키게.");
                            else if(vv.getCurrentPosition()<119000) kor.setText("내가 뭘 했는진 알아?");
                            else if(vv.getCurrentPosition()<123000) kor.setText("넌 미국 경제를 상대로 내기를 한거야.");
                            else if(vv.getCurrentPosition()<126000) kor.setText("자네가 틀리면 자넨 다 잃을 수 있어");
                            else if(vv.getCurrentPosition()<129000) kor.setText("은행들은 미국민을 상대로 사기를 쳤어");
                            else if(vv.getCurrentPosition()<130000) kor.setText("이제 놈들 치아에 킥을 먹인다.");
                            else if(vv.getCurrentPosition()<136000) kor.setText("좋아, 시작이다.");
                            else if(vv.getCurrentPosition()<137000) kor.setText("스트리퍼들을 노리겠다?");
                            else if(vv.getCurrentPosition()<138000) kor.setText("부실대출을 한?");
                            else if(vv.getCurrentPosition()<140000) kor.setText("걔들은 다 현금 부자지.");
                            else if(vv.getCurrentPosition()<141000) kor.setText("넌 재융자 못하게 될거야.");
                            else if(vv.getCurrentPosition()<143000) kor.setText("제 대출금 다요?");
                            else if(vv.getCurrentPosition()<144000) kor.setText("대출금 다라니?");
                            else if(vv.getCurrentPosition()<146000) kor.setText("전 집이 다섯채에");
                            else kor.setText("콘도가 하나에요.");
                        }
                    });
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        permissionCheck();

        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, "recorded.mp4");
        filename = file.getAbsolutePath();
        Log.d("MainActivity", "저장할 파일 명 : " + filename);

        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        btnSpeak = (Button) findViewById(R.id.btnSpeak);


        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vv.pause();  //음성 인식 시 영상 정지

                if(isConnected()){
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
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
    }


    public void permissionCheck(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //menu1에 있는 내용으로 만들겠다go
        super.onCreateOptionsMenu(menu);
        MenuInflater mInflater = getMenuInflater(); //MenuInflater가 메뉴를 누르면 목록이 뜸
        mInflater.inflate(R.menu.activity_video_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            //이 안에서 id가 같으면 안됨
            case R.id.shadow:
                Intent intent = new Intent(getApplicationContext(), ShadowingActivity.class);
                startActivity(intent);  //이걸해야 intent에 넘어감
        }
        return false;
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            matches_text = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            txtSpeechInput.setText(matches_text.get(position));
            vv.start(); //영상 다시 시작

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public class SpeechRecognizer extends Object{

    }
}