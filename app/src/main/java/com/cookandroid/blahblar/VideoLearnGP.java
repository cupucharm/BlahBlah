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

public class VideoLearnGP extends AppCompatActivity {
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
    double[] stop_time = {9, 32, 49, 59, 86};


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

                Intent intent = new Intent(getApplicationContext(), ShadowingGP.class);
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

        Uri video = Uri.parse("android.resource://"+getPackageName()+"/raw/gp");
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
                            if(vv.getCurrentPosition()<2000) eng.setText(" Hi, Eleanor. How are you today?");
                            else if(vv.getCurrentPosition()<3000) eng.setText("One question");
                            else if(vv.getCurrentPosition()<5000) eng.setText("Where am I? Who are you?");
                            else if(vv.getCurrentPosition()<6000) eng.setText("And what's going on?");
                            else if(vv.getCurrentPosition()<9000) eng.setText("Right.");
                            else if(vv.getCurrentPosition()<11000) eng.setText("You, Eleanor Shellstro, are dead.");
                            else if(vv.getCurrentPosition()<14000) eng.setText("Surprise!");
                            else if(vv.getCurrentPosition()<18000) eng.setText("In the afterlife, there's a Good Place, and there's a Bad Place.");
                            else if(vv.getCurrentPosition()<21000) eng.setText("You're in the Good Place.");
                            else if(vv.getCurrentPosition()<26000) eng.setText("Everything you did ultimately created some amount of good or bad.");
                            else if(vv.getCurrentPosition()<30000) eng.setText("Only the people with the very highest scores get to come here.");
                            else if(vv.getCurrentPosition()<32000) eng.setText("I'm Chidi Anagonye.");
                            else if(vv.getCurrentPosition()<36000) eng.setText("And you are my soulmate.");
                            else if(vv.getCurrentPosition()<42000) eng.setText(" I swear that I will never say or do anything to cause you any harm.");
                            else if(vv.getCurrentPosition()<44000) eng.setText("Good.");
                            else if(vv.getCurrentPosition()<46000) eng.setText("I'm not supposed to be here..");
                            else if(vv.getCurrentPosition()<48000) eng.setText("Wait, what?");
                            else if(vv.getCurrentPosition()<50000) eng.setText("These people might be \"good,\"");
                            else if(vv.getCurrentPosition()<52000) eng.setText("but are they really that much better than me?");
                            else if(vv.getCurrentPosition()<54000) eng.setText("I just have to go upstairs real quick");
                            else if(vv.getCurrentPosition()<56000) eng.setText("and steal a bunch of gold stuff.");
                            else if(vv.getCurrentPosition()<59000) eng.setText("Okay, don't do that.");
                            else if(vv.getCurrentPosition()<63000) eng.setText("Don't do... No. Eleanor. Eleanor...");
                            else if(vv.getCurrentPosition()<65000) eng.setText("This is all happening because of you.");
                            else if(vv.getCurrentPosition()<68000) eng.setText("You broke the world.");
                            else if(vv.getCurrentPosition()<70000) eng.setText("It's not a compliment.");
                            else if(vv.getCurrentPosition()<71000) eng.setText("Give me a chance.");
                            else if(vv.getCurrentPosition()<73000) eng.setText("Let me earn my place here.");
                            else if(vv.getCurrentPosition()<76000) eng.setText("You're trying to learn how to be a good person.");
                            else if(vv.getCurrentPosition()<79000) eng.setText("We're gonna have quizzes and papers.");
                            else if(vv.getCurrentPosition()<81000) eng.setText("It's gonna be so much fun.");
                            else if(vv.getCurrentPosition()<83000) eng.setText("Remind me what I'm getting out of this again?");
                            else if(vv.getCurrentPosition()<85000) eng.setText("You get to avoid eternal damnation.");
                            else eng.setText("Oh, yeah.");

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
                            if(vv.getCurrentPosition()<2000) kor.setText("안녕하세요. 오늘 기분 어떠세요?");
                            else if(vv.getCurrentPosition()<3000) kor.setText("궁금한게 있는데요");
                            else if(vv.getCurrentPosition()<5000) kor.setText("여긴 어디고 당신은 누구죠?");
                            else if(vv.getCurrentPosition()<6000) kor.setText("어떻게 된 거예요?");
                            else if(vv.getCurrentPosition()<9000) kor.setText("그게 궁금하시군요");
                            else if(vv.getCurrentPosition()<11000) kor.setText("엘리너 셸스트롭 씨 당신은 죽었어요.");
                            else if(vv.getCurrentPosition()<14000) kor.setText("놀라셨죠!");
                            else if(vv.getCurrentPosition()<18000) kor.setText("사후 세계에는 좋은 곳과 나쁜 곳이 있습니다.");
                            else if(vv.getCurrentPosition()<21000) kor.setText("당신은 좋은 곳에 오셨어요.");
                            else if(vv.getCurrentPosition()<26000) kor.setText("우리가 생전에 한 일엔 착한 일과 나쁜 일이 있죠.");
                            else if(vv.getCurrentPosition()<30000) kor.setText("점수가 아주 높은 사람만이 좋은 곳에 올 수 있어요.");
                            else if(vv.getCurrentPosition()<32000) kor.setText("난 치디 아나곤예예요.");
                            else if(vv.getCurrentPosition()<36000) kor.setText("당신의 소울메이트죠.");
                            else if(vv.getCurrentPosition()<42000) kor.setText("당신에게 상처가 될 말이나 행동은 절대 하지 않겠어요.");
                            else if(vv.getCurrentPosition()<44000) kor.setText("좋아요");
                            else if(vv.getCurrentPosition()<46000) kor.setText("나 원래 여기 오면 안 돼요.");
                            else if(vv.getCurrentPosition()<48000) kor.setText("뭐라고요?");
                            else if(vv.getCurrentPosition()<50000) kor.setText("이 사람들이 착한 사람들일지는 몰라도");
                            else if(vv.getCurrentPosition()<52000) kor.setText("꼭 나보다 나은 건 아닐 수도 있잖아요.");
                            else if(vv.getCurrentPosition()<54000) kor.setText("후딱 위층에 올라가서");
                            else if(vv.getCurrentPosition()<56000) kor.setText("금으로 된 물건을 훔쳐 올게요.");
                            else if(vv.getCurrentPosition()<59000) kor.setText("그러지 마요");
                            else if(vv.getCurrentPosition()<63000) kor.setText("그러지..엘리너..");
                            else if(vv.getCurrentPosition()<65000) kor.setText("다 당신 때문에 이렇게 됐어요.");
                            else if(vv.getCurrentPosition()<68000) kor.setText("세상을 부숴버렸다고요.");
                            else if(vv.getCurrentPosition()<70000) kor.setText("칭찬 아니에요.");
                            else if(vv.getCurrentPosition()<71000) kor.setText("나한테 기회를 줘요.");
                            else if(vv.getCurrentPosition()<73000) kor.setText("여기 어울리는 사람이 될게요");
                            else if(vv.getCurrentPosition()<76000) kor.setText("착한 사람이 되려고 노력하겠다는 거군요.");
                            else if(vv.getCurrentPosition()<79000) kor.setText("시험도 보고 숙제도 낼 거예요.");
                            else if(vv.getCurrentPosition()<81000) kor.setText("엄청 신나겠죠?");
                            else if(vv.getCurrentPosition()<83000) kor.setText("이래서 나한테 뭐가 좋은데요?");
                            else if(vv.getCurrentPosition()<85000) kor.setText("지옥에서 영원히 썩고 싶어요?");
                            else kor.setText("맞다");

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
