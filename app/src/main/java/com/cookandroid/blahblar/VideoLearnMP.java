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

public class VideoLearnMP extends AppCompatActivity {
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

                Intent intent = new Intent(getApplicationContext(), ShadowingMP.class);
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

        Uri video = Uri.parse("android.resource://"+getPackageName()+"/raw/paris");
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
                            if(vv.getCurrentPosition()<12000) eng.setText("Because this is unbelievable. There's no city like this in the world.");
                            else if(vv.getCurrentPosition()<14000) eng.setText("You're in love with a fantasy.");
                            else if(vv.getCurrentPosition()<20000) eng.setText("I'm in love with you.");
                            else if(vv.getCurrentPosition()<22000) eng.setText("What are you doing here?");
                            else if(vv.getCurrentPosition()<27000) eng.setText("Dad's on business and we just decided to free-loaded along.");
                            else if(vv.getCurrentPosition()<29000) eng.setText("That's great. We can spend some time together.");
                            else if(vv.getCurrentPosition()<32000) eng.setText("Well, I think we have a lot of commitments, but I’m sure it’s...");
                            else if(vv.getCurrentPosition()<36000) eng.setText("what?");
                            else if(vv.getCurrentPosition()<39000) eng.setText("If I’m not mistaken, Rodin’s work was influenced by his wife, Camille.");
                            else if(vv.getCurrentPosition()<41000) eng.setText("Rose was the wife.");
                            else if(vv.getCurrentPosition()<42000) eng.setText("No, he was never married to Rose.");
                            else if(vv.getCurrentPosition()<44000) eng.setText("I hope you're not going to be as anti-social tomorrow.");
                            else if(vv.getCurrentPosition()<47000) eng.setText("I'm not quite as taken with him as you are.");
                            else if(vv.getCurrentPosition()<49000) eng.setText("He's a pseudo-intellectual.");
                            else if(vv.getCurrentPosition()<55000) eng.setText("slightly more tannic than the 59, and I prefer a smoky feeling.");
                            else if(vv.getCurrentPosition()<58000) eng.setText("Carol and I are gonna go dancing.");
                            else if(vv.getCurrentPosition()<60000) eng.setText("we heard of a great place. Interested?");
                            else if(vv.getCurrentPosition()<61000) eng.setText("Sure, yes");
                            else if(vv.getCurrentPosition()<81000) eng.setText("No, no. I don’t want to be a killjoy, but I need to get a little fresh air.");
                            else if(vv.getCurrentPosition()<82000) eng.setText("What time did you get in last night?");
                            else if(vv.getCurrentPosition()<83000) eng.setText("Not that late.");
                            else if(vv.getCurrentPosition()<87000) eng.setText("I'll find up going on another little hike tonight.");
                            else if(vv.getCurrentPosition()<89000) eng.setText("Where did Gil run off to?");
                            else if(vv.getCurrentPosition()<91000) eng.setText("he likes to walk around Paris.");
                            else if(vv.getCurrentPosition()<92000) eng.setText("What do you think Gil goes every night?");
                            else if(vv.getCurrentPosition()<96000) eng.setText("He walks and gets ideas.");
                            else if(vv.getCurrentPosition()<97000) eng.setText("Why are you so dressed up?");
                            else if(vv.getCurrentPosition()<98000) eng.setText("No, I was just doing a writing.");
                            else if(vv.getCurrentPosition()<101000) eng.setText("You dress up and put on cologne to write?");
                            else if(vv.getCurrentPosition()<107000) eng.setText("You know how I think better in the shower, and I get the positive ions going in there.");
                            else if(vv.getCurrentPosition()<108000) eng.setText("I had a private detective follow him.");
                            else if(vv.getCurrentPosition()<109000) eng.setText("What happened?");
                            else if(vv.getCurrentPosition()<116000) eng.setText("I don't know. The detective agency says the detective is missing.");
                            else eng.setText("I'm in very perplexing situation.");
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
                            if(vv.getCurrentPosition()<12000) kor.setText("정말 기가 막히다. 세계 어디에도 이런 도시는 없어.");
                            else if(vv.getCurrentPosition()<14000) kor.setText("자긴 환상에 빠졌어.");
                            else if(vv.getCurrentPosition()<20000) kor.setText("자기한테 빠졌지.");
                            else if(vv.getCurrentPosition()<22000) kor.setText("파리엔 어쩐 일이야?");
                            else if(vv.getCurrentPosition()<27000) kor.setText("부모님한테 묻어서 왔어.");
                            else if(vv.getCurrentPosition()<29000) kor.setText("잘 됐네. 같이 여행하자.");
                            else if(vv.getCurrentPosition()<32000) kor.setText("우린 따로 할 일이...");
                            else if(vv.getCurrentPosition()<36000) kor.setText("무슨 할 일?");
                            else if(vv.getCurrentPosition()<39000) kor.setText("내가 알기론 로댕 작품 다수는 아내 까미유 영향을 받았지.");
                            else if(vv.getCurrentPosition()<41000) kor.setText("부인은 로즈였죠.");
                            else if(vv.getCurrentPosition()<42000) kor.setText("아냐, 내가 확실해.");
                            else if(vv.getCurrentPosition()<44000) kor.setText("자기 질투해?");
                            else if(vv.getCurrentPosition()<47000) kor.setText("난 그 친구가 싫어.");
                            else if(vv.getCurrentPosition()<49000) kor.setText("사이비 지식인 같아.");
                            else if(vv.getCurrentPosition()<55000) kor.setText("59년산보다 탄닌이 살짝 많네. 와인은 스모키 향이지.");
                            else if(vv.getCurrentPosition()<58000) kor.setText("춤추러 가자.");
                            else if(vv.getCurrentPosition()<60000) kor.setText("내가 좋은데 알아.");
                            else if(vv.getCurrentPosition()<61000) kor.setText("난 갈래!");
                            else if(vv.getCurrentPosition()<81000) kor.setText("아니, 아뇨... 난 산책 좀 해야 겠어.");
                            else if(vv.getCurrentPosition()<82000) kor.setText("어제 몇시에 들어왔어?");
                            else if(vv.getCurrentPosition()<83000) kor.setText("별로 안 늦었어.");
                            else if(vv.getCurrentPosition()<87000) kor.setText("오늘 밤도 좀 걷다 올까봐.");
                            else if(vv.getCurrentPosition()<89000) kor.setText("길은 어디 갔니?");
                            else if(vv.getCurrentPosition()<91000) kor.setText("파리 거리를 헤매요.");
                            else if(vv.getCurrentPosition()<92000) kor.setText("매일 밤 어딜 헤매?");
                            else if(vv.getCurrentPosition()<96000) kor.setText("걸으며 영감을 얻는데나 뭐래나.");
                            else if(vv.getCurrentPosition()<97000) kor.setText("차려입고 어디가?");
                            else if(vv.getCurrentPosition()<98000) kor.setText("글쓰려던 참이야.");
                            else if(vv.getCurrentPosition()<101000) kor.setText("글 쓰는데 향수까지?");
                            else if(vv.getCurrentPosition()<107000) kor.setText("잠깐 샤워하고 나왔어. 샤워해야 머리가 맑아져서.");
                            else if(vv.getCurrentPosition()<108000) kor.setText("사립탐정을 붙여봤어.");
                            else if(vv.getCurrentPosition()<109000) kor.setText("근데요?");
                            else if(vv.getCurrentPosition()<116000) kor.setText("나도 몰라. 그 탐정이 실종됐대.");
                            else kor.setText("저도 지금 황당한 상황이라서요.");


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