package com.cookandroid.blahblar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.os.Handler;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShadowingActivity extends AppCompatActivity {
    ImageView btn_toolbar_back;
    ImageView btn_toolbar_s;
    TextView eng, kor;
    MediaPlayer player;
    MediaRecorder recorder;
    String id, name, phone, today, title;
    Button kor_btn, eng_btn, startSD;
    Boolean kor_s=true, eng_s=true, record=true, flag=true, startbtn=true;
    VideoView vv;
    String filename;
    int position = 0;
    int count = 0;


    double[] stop_time = {4.5, 9, 17, 19, 22, 27, 29, 34, 40,
            45, 46, 47, 49, 51, 56, 59, 60, 62,
            63, 64, 66, 67, 68, 69, 71, 72, 74, 76,
            78, 81, 83, 85, 88, 90, 102, 107,
            110, 111, 114, 115, 118, 120, 121, 122, 123, 127, 129};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shadowing);

        Intent intent = getIntent();
        name = intent.getExtras().getString("name");
        id = intent.getExtras().getString("id");
        phone = intent.getExtras().getString("phone");
        today = intent.getExtras().getString("today");
        title = intent.getExtras().getString("title");
        Log.d("비디오쉐도잉..", id+ " " + name+" "+phone+" "+today);
        //Toast.makeText(this, "쉐도잉피카... "+id + " " + name + " " + phone + " " + today+" "+title, Toast.LENGTH_LONG).show();

        btn_toolbar_back = (ImageView) findViewById(R.id.btn_toolbar_back_s);
        btn_toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {   // 나가면 녹음 중지 및 저장
                if(startbtn==false){
                    stopRecording();
                }
                finish();
            }
        });

        btn_toolbar_s = (ImageView) findViewById(R.id.btn_toolbar_s);
        btn_toolbar_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), VideoLearnActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("today", today);
                intent.putExtra("title", title);
                finish();
                startActivity(intent);  //이걸해야 intent에 넘어감
            }
        });

        vv = (VideoView) findViewById(R.id.videoView);

        eng_btn=(Button)findViewById(R.id.eng_btn);
        kor_btn=(Button)findViewById(R.id.kor_btn);
        kor = (TextView) findViewById(R.id.korea);
        eng = (TextView) findViewById(R.id.english);
        startSD = (Button) findViewById(R.id.startSD);

        permissionCheck();

        Long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        today = sdf.format(date);

        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, "./PK/"+id+"_"+today+"_"+title+".mp3");
        filename = file.getAbsolutePath();
        Log.d("ShadowingActivity", "저장할 파일 명 : " + filename);


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

        Uri video = Uri.parse("android.resource://"+getPackageName()+"/raw/pica");
        vv.setMediaController(mc);
        vv.setVideoURI(video);
        vv.requestFocus();

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

                            if(vv.getCurrentPosition()<5000) eng.setText("Welcome to Ryme City.");
                            else if(vv.getCurrentPosition()<9000) eng.setText("A celebration of the harmony between humans and Pokemon.");
                            else if(vv.getCurrentPosition()<13000) eng.setText("♪♪");
                            else if(vv.getCurrentPosition()<17000) eng.setText("Tim, your dad was a legend in this precinct.");
                            else if(vv.getCurrentPosition()<19000) eng.setText("If you are anything like your dad...");
                            else if(vv.getCurrentPosition()<23000) eng.setText("I’m not.");
                            else if(vv.getCurrentPosition()<25000) eng.setText("I remember you wanted to be a Pokemon trainer");
                            else if(vv.getCurrentPosition()<29000) eng.setText("when you were young.");
                            else if(vv.getCurrentPosition()<36000) eng.setText("Yeah, that didn’t really work out.");
                            else if(vv.getCurrentPosition()<40000) eng.setText("Someone there?");
                            else if(vv.getCurrentPosition()<45000) eng.setText("Whoever you are, I know how to use this.");
                            else if(vv.getCurrentPosition()<46000) eng.setText("Aw, jeez.");
                            else if(vv.getCurrentPosition()<47000) eng.setText("Here we go.");
                            else if(vv.getCurrentPosition()<49000) eng.setText("I know you can’t understand me.");
                            else if(vv.getCurrentPosition()<51000) eng.setText("But put down the stapler…");
                            else if(vv.getCurrentPosition()<56000) eng.setText("… or I will electrocute you.");
                            else if(vv.getCurrentPosition()<58000) eng.setText("♪♪");
                            else if(vv.getCurrentPosition()<59000) eng.setText("Did you just talk?");
                            else if(vv.getCurrentPosition()<60000) eng.setText("Whoa.");
                            else if(vv.getCurrentPosition()<62000) eng.setText("Did you just understand me?");
                            else if(vv.getCurrentPosition()<63000) eng.setText("Oh, my God! You can understand me!");
                            else if(vv.getCurrentPosition()<64000) eng.setText("Stop!");
                            else if(vv.getCurrentPosition()<66000) eng.setText("I have been so lonely!");
                            else if(vv.getCurrentPosition()<67000) eng.setText("They try to talk to me all the time.");
                            else if(vv.getCurrentPosition()<68000) eng.setText("All they hear is “Pika-Pika.”");
                            else if(vv.getCurrentPosition()<69000) eng.setText("You can hear him, right?");
                            else if(vv.getCurrentPosition()<71000) eng.setText("Pika-Pika.");
                            else if(vv.getCurrentPosition()<72000) eng.setText("Yeah. Pika-Pika-Pika.");
                            else if(vv.getCurrentPosition()<74000) eng.setText("You’re adorable!");
                            else if(vv.getCurrentPosition()<76000) eng.setText("They can’t understand me, kid.");
                            else if(vv.getCurrentPosition()<78000) eng.setText("Can no one else hear him?!");
                            else if(vv.getCurrentPosition()<79000) eng.setText("♪♪");
                            else if(vv.getCurrentPosition()<81000) eng.setText("I don’t need a Pokemon. Period");
                            else if(vv.getCurrentPosition()<83000) eng.setText("Then what about a world-class detective?");
                            else if(vv.getCurrentPosition()<85000) eng.setText("Becase if you wanna find your pops…");
                            else if(vv.getCurrentPosition()<88000) eng.setText("… I’m your best bet.");
                            else if(vv.getCurrentPosition()<90000) eng.setText("We’re gonna do this, you and me.");
                            else if(vv.getCurrentPosition()<99000) eng.setText("♪♪");
                            else if(vv.getCurrentPosition()<102000) eng.setText("There’s magic. It brought us together.");
                            else if(vv.getCurrentPosition()<107000) eng.setText("And that magic is called hope.");
                            else if(vv.getCurrentPosition()<110000) eng.setText("Listen up, we got ways to make you talk, or mime…");
                            else if(vv.getCurrentPosition()<111000) eng.setText("…So tell us what we wanna know.");
                            else if(vv.getCurrentPosition()<114000) eng.setText("Pipe. Yes. Okay. A can.");
                            else if(vv.getCurrentPosition()<115000) eng.setText("Shoving? Pushing.");
                            else if(vv.getCurrentPosition()<118000) eng.setText("problem is that I push people away and then hate them for leaving.");
                            else if(vv.getCurrentPosition()<120000) eng.setText("He’s saying you can shove it.");
                            else if(vv.getCurrentPosition()<121000) eng.setText("What? I can shove it?");
                            else if(vv.getCurrentPosition()<122000) eng.setText("Okay, that’s it. No. We’re switching roles.");
                            else if(vv.getCurrentPosition()<123000) eng.setText("I’m bad cop, you’re good cop.");
                            else if(vv.getCurrentPosition()<127000) eng.setText("No, we’re not cops.");
                            else if(vv.getCurrentPosition()<129000) eng.setText("In my head, I saw that differently.");
                            else eng.setText(" ");

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

                            if(vv.getCurrentPosition()<5000) kor.setText("라임시티에 잘 오셨어요.");
                            else if(vv.getCurrentPosition()<9000) kor.setText("인간과 포켓몬이 공존하는 도시죠.");
                            else if(vv.getCurrentPosition()<13000) kor.setText("♪♪");
                            else if(vv.getCurrentPosition()<17000) kor.setText("네 아빠는 전설적인 분이셨어.");
                            else if(vv.getCurrentPosition()<19000) kor.setText("아빠를 조금만 닮았...");
                            else if(vv.getCurrentPosition()<23000) kor.setText("아니잖아요.");
                            else if(vv.getCurrentPosition()<25000) kor.setText("어릴 때 포켓몬 트레이너 되고 싶어했지?");
                            else if(vv.getCurrentPosition()<36000) kor.setText("네, 잘 안 풀렸죠.");
                            else if(vv.getCurrentPosition()<40000) kor.setText("누구야?");
                            else if(vv.getCurrentPosition()<45000) kor.setText("나 이거 쓸 줄 알아.");
                            else if(vv.getCurrentPosition()<46000) kor.setText("맙소사.");
                            else if(vv.getCurrentPosition()<47000) kor.setText("시작이네.");
                            else if(vv.getCurrentPosition()<49000) kor.setText("내 말 못 알아듣겠지만");
                            else if(vv.getCurrentPosition()<51000) kor.setText("스테이플러 내려놔");
                            else if(vv.getCurrentPosition()<56000) kor.setText("아님 전기로 쏴 버린다.");
                            else if(vv.getCurrentPosition()<58000) kor.setText("♪♪");
                            else if(vv.getCurrentPosition()<59000) kor.setText("너 지금 말했어?");
                            else if(vv.getCurrentPosition()<60000) kor.setText("헐.");
                            else if(vv.getCurrentPosition()<62000) kor.setText("너 지금 알아 들었어?");
                            else if(vv.getCurrentPosition()<63000) kor.setText("맙소사 내 말을 알아 듣다니!");
                            else if(vv.getCurrentPosition()<64000) kor.setText("그만!");
                            else if(vv.getCurrentPosition()<66000) kor.setText("나 넘나 외로웠어!");
                            else if(vv.getCurrentPosition()<67000) kor.setText("다들 나랑 말 하고 싶어도");
                            else if(vv.getCurrentPosition()<68000) kor.setText("내 답은 '피카 피카'로 들린대.");
                            else if(vv.getCurrentPosition()<69000) kor.setText("말하는 거 들리죠?");
                            else if(vv.getCurrentPosition()<71000) kor.setText("피카 피카.");
                            else if(vv.getCurrentPosition()<72000) kor.setText("그래, 피카 피카 피카.");
                            else if(vv.getCurrentPosition()<74000) kor.setText("귀여워라!");
                            else if(vv.getCurrentPosition()<75000) kor.setText("당신이 귀엽지");
                            else if(vv.getCurrentPosition()<76000) kor.setText("못 알아듣는다니까");
                            else if(vv.getCurrentPosition()<78000) kor.setText("안들려요?!");
                            else if(vv.getCurrentPosition()<79000) kor.setText("♪♪");
                            else if(vv.getCurrentPosition()<81000) kor.setText("난 포켓몬 필요없어");
                            else if(vv.getCurrentPosition()<83000) kor.setText("세계적인 명탐정이라면?");
                            else if(vv.getCurrentPosition()<85000) kor.setText("아빠를 찾으려면");
                            else if(vv.getCurrentPosition()<88000) kor.setText("내가 있어야 할 걸?");
                            else if(vv.getCurrentPosition()<90000) kor.setText("같이 하는 거지 너랑 나랑.");
                            else if(vv.getCurrentPosition()<99000) kor.setText("♪♪");
                            else if(vv.getCurrentPosition()<102000) kor.setText("우릴 만나게 한 게 마법인가 봐.");
                            else if(vv.getCurrentPosition()<107000) kor.setText("희망이라는 마법.");
                            else if(vv.getCurrentPosition()<110000) kor.setText("말 안하면 마임 시킨다.");
                            else if(vv.getCurrentPosition()<111000) kor.setText("아는 거 다 불어.");
                            else if(vv.getCurrentPosition()<114000) kor.setText("파이프... 깡통...");
                            else if(vv.getCurrentPosition()<115000) kor.setText("민다고? 민대.");
                            else if(vv.getCurrentPosition()<118000) kor.setText("내가 사람들을 밀어내고는 날 떠났다고 사람들을 원망한대.");
                            else if(vv.getCurrentPosition()<120000) kor.setText("집어치우래.");
                            else if(vv.getCurrentPosition()<121000) kor.setText("뭐? 집어치우라고?");
                            else if(vv.getCurrentPosition()<122000) kor.setText("그럼 이제 방법 바꿔야겠다.");
                            else if(vv.getCurrentPosition()<123000) kor.setText("난 나쁜 형사, 넌 좋은 형사.");
                            else if(vv.getCurrentPosition()<127000) kor.setText("우리 형사 아냐.");
                            else if(vv.getCurrentPosition()<129000) kor.setText("이러려던 게 아닌데.");
                            else kor.setText(" ");


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


        startSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count++;


                if(count % 2 == 0){
                    startSD.setText("SHADOWING START!");
                    vv.pause();
                    stopRecording();
                }

                else {
                    startSD.setText("SHADOWING STOP!");
                    vv.start();
                    startbtn = false;
                    recordAudio();
                }

                    vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            new Handler().postDelayed(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    //recorder.stop();
                                    stopRecording();
                                }
                            }, 5000);
                        }
                    });

            }
        });
    }


    private void recordAudio() {
        recorder = new MediaRecorder();

        /* 그대로 저장하면 용량이 크다.
         * 프레임 : 한 순간의 음성이 들어오면, 음성을 바이트 단위로 전부 저장하는 것
         * 초당 15프레임 이라면 보통 8K(8000바이트) 정도가 한순간에 저장됨
         * 따라서 용량이 크므로, 압축할 필요가 있음 */
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 어디에서 음성 데이터를 받을 것인지
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4); // 압축 형식 설정
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

        recorder.setOutputFile(filename);

        try {
            recorder.prepare();
            recorder.start();

            Toast.makeText(this, "녹음 시작됨.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
            Toast.makeText(this, "녹음 중지됨.", Toast.LENGTH_SHORT).show();
        }
    }


    public void closePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    public void permissionCheck(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1);
        }
    }
}