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

public class VideoLearnTS extends AppCompatActivity {
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
    double[] stop_time = {4, 10, 16, 23, 31, 46, 70, 80, 109};


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

                Intent intent = new Intent(getApplicationContext(), ShadowingTS.class);
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

        Uri video = Uri.parse("android.resource://"+getPackageName()+"/raw/toystory");
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
                            if(vv.getCurrentPosition()<3000) eng.setText("Everyone, Bonnie made a friend in class!");
                            else if(vv.getCurrentPosition()<5000) eng.setText("Oh she's already making friends.");
                            else if(vv.getCurrentPosition()<8000) eng.setText("No, no she literally made a new friend.");
                            else if(vv.getCurrentPosition()<10000) eng.setText("I want you to meet ... Forky!");
                            else if(vv.getCurrentPosition()<11000) eng.setText("H-h-h-hi?");
                            else if(vv.getCurrentPosition()<13000) eng.setText("Hello! Hi!");
                            else if(vv.getCurrentPosition()<15000) eng.setText("He's a spork!");
                            else if(vv.getCurrentPosition()<17000) eng.setText("Yes, yeah, I know.");
                            else if(vv.getCurrentPosition()<21000) eng.setText("Forky is the most important toy to Bonnie right now.");
                            else if(vv.getCurrentPosition()<24000) eng.setText("We all have to make sure nothing happens to him.");
                            else if(vv.getCurrentPosition()<26000) eng.setText("Woody! We have a situation.");
                            else if(vv.getCurrentPosition()<27000) eng.setText("I am not a toy.");
                            else if(vv.getCurrentPosition()<32000) eng.setText("I was made for soup, salad, maybe chili, and then the trash!");
                            else if(vv.getCurrentPosition()<33000) eng.setText("Freedom!");
                            else if(vv.getCurrentPosition()<35000) eng.setText("Buzz! We've gotta get Forky!");
                            else if(vv.getCurrentPosition()<39000) eng.setText("Affirmative!");
                            else if(vv.getCurrentPosition()<40000) eng.setText("Why am I alive?");
                            else if(vv.getCurrentPosition()<42000) eng.setText("You're Bonnie's toy.");
                            else if(vv.getCurrentPosition()<47000) eng.setText("You are going to help create happy memories that will last for the rest of her life!");
                            else if(vv.getCurrentPosition()<53000) eng.setText("Huh? Wha?");
                            else if(vv.getCurrentPosition()<55000) eng.setText("Bo? Forky, come on!");
                            else if(vv.getCurrentPosition()<56000) eng.setText("Bo?");
                            else if(vv.getCurrentPosition()<59000) eng.setText("Hi there! My name is Gabby Gabby.");
                            else if(vv.getCurrentPosition()<60000) eng.setText("We can't stay.");
                            else if(vv.getCurrentPosition()<63000) eng.setText("Haha, yes you can. Boys?");
                            else if(vv.getCurrentPosition()<66000) eng.setText("Woody, Behind you!");
                            else if(vv.getCurrentPosition()<68000) eng.setText("Bo! What are you doing here?");
                            else if(vv.getCurrentPosition()<70000) eng.setText("No time to explain. Come with me.");
                            else if(vv.getCurrentPosition()<71000) eng.setText("We need to get back to our kid.");
                            else if(vv.getCurrentPosition()<75000) eng.setText("Aww, Sheriff Woody, always comin'to the rescue.");
                            else if(vv.getCurrentPosition()<76000) eng.setText("Bonnie needs Forky.");
                            else if(vv.getCurrentPosition()<81000) eng.setText("Woody, who needs a kid's room when you can have all of this...?");
                            else if(vv.getCurrentPosition()<82000) eng.setText("Wow.");
                            else if(vv.getCurrentPosition()<86000) eng.setText("Woody, aren't we goin' to Bonnie?");
                            else if(vv.getCurrentPosition()<87000) eng.setText("We have to find them.");
                            else if(vv.getCurrentPosition()<88000) eng.setText("What do we do, Buzz?");
                            else if(vv.getCurrentPosition()<89000) eng.setText("What would Woody do?");
                            else if(vv.getCurrentPosition()<90000) eng.setText("Jump out of a moving vehicle.");
                            else if(vv.getCurrentPosition()<91000) eng.setText("Let's go!");
                            else if(vv.getCurrentPosition()<97000) eng.setText("Hey, if you gotta go, you gotta go.");
                            else if(vv.getCurrentPosition()<101000) eng.setText("You know, you've handled this lost toy life better than I could.");
                            else if(vv.getCurrentPosition()<103000) eng.setText("Open your eyes Woody.");
                            else if(vv.getCurrentPosition()<104000) eng.setText("There's plenty of kids out there.");
                            else if(vv.getCurrentPosition()<106000) eng.setText("Sometimes change can be good.");
                            else if(vv.getCurrentPosition()<108000) eng.setText("You can't teach this old toy new tricks.");
                            else if(vv.getCurrentPosition()<110000) eng.setText("You'd be surprised.");
                            else if(vv.getCurrentPosition()<111000) eng.setText("Bonnie?!");
                            else if(vv.getCurrentPosition()<114000) eng.setText("We're going home, Forky!");
                            else if(vv.getCurrentPosition()<117000) eng.setText("Bonnie, I'm comin'!");
                            else if(vv.getCurrentPosition()<118000) eng.setText("On my way Woody!");
                            else if(vv.getCurrentPosition()<126000) eng.setText("To infinity... and beyond!");
                            else if(vv.getCurrentPosition()<127000) eng.setText("Don't let Woody leave!");
                            else if(vv.getCurrentPosition()<129000) eng.setText("Kids lose their toys every day.");
                            else if(vv.getCurrentPosition()<131000) eng.setText("I was made to help a child.");
                            else if(vv.getCurrentPosition()<134000) eng.setText("I don't remember it being this hard.");
                            else if(vv.getCurrentPosition()<137000) eng.setText("Woody? Somebody's whispering in your ear...");
                            else eng.setText("Everything's gomma be ok.");
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
                            if(vv.getCurrentPosition()<3000) kor.setText("자 주목! 보니가 학교에서 새 친구를 만들었어!");
                            else if(vv.getCurrentPosition()<5000) kor.setText("오 벌써 새 친구를 사귀었구나");
                            else if(vv.getCurrentPosition()<8000) kor.setText("그게 아니야. 말 그대로 친구를 '만들었다고'");
                            else if(vv.getCurrentPosition()<10000) kor.setText("여러분에게 소개할게. 새 친구 포키!");
                            else if(vv.getCurrentPosition()<11000) kor.setText("아...안녕?");
                            else if(vv.getCurrentPosition()<13000) kor.setText("안녕!");
                            else if(vv.getCurrentPosition()<15000) kor.setText("쟤는 숟가락이잖아");
                            else if(vv.getCurrentPosition()<17000) kor.setText("나도 알아");
                            else if(vv.getCurrentPosition()<21000) kor.setText("포키는 보니에게 가장 중요한 장난감이야");
                            else if(vv.getCurrentPosition()<24000) kor.setText("그러니 포키에게 무슨 일이 일어나지 않도록 해야 해");
                            else if(vv.getCurrentPosition()<26000) kor.setText("우디, 안 좋은 상황이 발생했어");
                            else if(vv.getCurrentPosition()<27000) kor.setText("난 장난감이 아니야");
                            else if(vv.getCurrentPosition()<32000) kor.setText("난 그저 수프나 샐러드, 고추를 먹기 위한 도구야 그리고 쓰레기통으로 가야 할 운명이야");
                            else if(vv.getCurrentPosition()<33000) kor.setText("자유를 위하여!");
                            else if(vv.getCurrentPosition()<35000) kor.setText("버즈! 포키를 데리고 와야 해!");
                            else if(vv.getCurrentPosition()<39000) kor.setText("알겠다 오버");
                            else if(vv.getCurrentPosition()<40000) kor.setText("근데 난 왜 살아있어?");
                            else if(vv.getCurrentPosition()<42000) kor.setText("넌 보니의 장난감이니까");
                            else if(vv.getCurrentPosition()<47000) kor.setText("넌 보니가 평생동안 기억할 행복한 추억을 만들어 줘야 해");
                            else if(vv.getCurrentPosition()<53000) kor.setText("응? 뭐라고?");
                            else if(vv.getCurrentPosition()<55000) kor.setText("보? 포키, 얼른 들어와!");
                            else if(vv.getCurrentPosition()<56000) kor.setText("보? 거기 있어?");
                            else if(vv.getCurrentPosition()<59000) kor.setText("안녕 친구들! 내 이름은 '개비 개비'야");
                            else if(vv.getCurrentPosition()<60000) kor.setText("우린 가봐야 해");
                            else if(vv.getCurrentPosition()<63000) kor.setText("너희들은 못가 친구들~");
                            else if(vv.getCurrentPosition()<66000) kor.setText("우디, 뒤쪽이야!");
                            else if(vv.getCurrentPosition()<68000) kor.setText("보! 여기서 뭐 하는거야?");
                            else if(vv.getCurrentPosition()<70000) kor.setText("설명할 시간이 없어. 날 따라와");
                            else if(vv.getCurrentPosition()<71000) kor.setText("우린 친구들에게 돌아가야 해");
                            else if(vv.getCurrentPosition()<75000) kor.setText("역시 보안관 우디 항상 남을 도우려고 하지");
                            else if(vv.getCurrentPosition()<76000) kor.setText("보니에겐 포키가 필요해");
                            else if(vv.getCurrentPosition()<81000) kor.setText("우디, 이 모든걸 가졌을 때 과연 보니 방으로 돌아갈 필요가 있을까?");
                            else if(vv.getCurrentPosition()<82000) kor.setText("우와.");
                            else if(vv.getCurrentPosition()<86000) kor.setText("우디, 보니에게 돌아갈거야?");
                            else if(vv.getCurrentPosition()<87000) kor.setText("우디랑 포키를 찾아야 해!");
                            else if(vv.getCurrentPosition()<88000) kor.setText("하지만 어떻게 찾지, 버즈?");
                            else if(vv.getCurrentPosition()<89000) kor.setText("어떻게 찾지?");
                            else if(vv.getCurrentPosition()<90000) kor.setText("일단 차에서 나가야지");
                            else if(vv.getCurrentPosition()<91000) kor.setText("가보자!");
                            else if(vv.getCurrentPosition()<97000) kor.setText("그래 가보자 가보자");
                            else if(vv.getCurrentPosition()<101000) kor.setText("있잖아 보, 너는 버려진 장난감들에게 새로운 삶을 주었어");
                            else if(vv.getCurrentPosition()<103000) kor.setText("눈 크게 뜨고 봐봐 우디");
                            else if(vv.getCurrentPosition()<104000) kor.setText("저 밖엔 많은 아이들이 있어");
                            else if(vv.getCurrentPosition()<106000) kor.setText("떄론 변화도 괜찮은 방법이야");
                            else if(vv.getCurrentPosition()<108000) kor.setText("하지만 장난감들이 새 주인을 만나지는 않을거야");
                            else if(vv.getCurrentPosition()<110000) kor.setText("보면 놀랄껄");
                            else if(vv.getCurrentPosition()<111000) kor.setText("보니잖아?!");
                            else if(vv.getCurrentPosition()<114000) kor.setText("포키, 우린 집에 갈 수 있어!");
                            else if(vv.getCurrentPosition()<117000) kor.setText("차가 오고 있어!");
                            else if(vv.getCurrentPosition()<118000) kor.setText("우디, 내가 간다!");
                            else if(vv.getCurrentPosition()<126000) kor.setText("무한한 공간 저 넘어로!");
                            else if(vv.getCurrentPosition()<127000) kor.setText("우디가 못 떠나게 막아!");
                            else if(vv.getCurrentPosition()<129000) kor.setText("아이들은 매일 장난감 곁을 떠나고 있어");
                            else if(vv.getCurrentPosition()<131000) kor.setText("난 아이들과 함께 하기 위해 만들어졌어");
                            else if(vv.getCurrentPosition()<134000) kor.setText("이걸 계속 기억하려고 노력하고 있고");
                            else if(vv.getCurrentPosition()<137000) kor.setText("우디, 누군가 너에게 속삭이고 있어");
                            else kor.setText("전부 다 괜찮아질거야");

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