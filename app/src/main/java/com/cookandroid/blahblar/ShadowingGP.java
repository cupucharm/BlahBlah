package com.cookandroid.blahblar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShadowingGP extends AppCompatActivity {
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
        //Toast.makeText(this, "쉐도잉굿플... "+id + " " + name + " " + phone + " " + today+" "+title, Toast.LENGTH_LONG).show();
        Log.d("비디오쉐도잉..", id+ " " + name+" "+phone+" "+today);


        btn_toolbar_back = (ImageView) findViewById(R.id.btn_toolbar_back_s);
        btn_toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                Intent intent = new Intent(getApplicationContext(), VideoLearnGP.class);

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
        startSD=(Button)findViewById(R.id.startSD);

        permissionCheck();

        Long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        today = sdf.format(date);

        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, "./GP/"+id+"_"+today+"_"+title+".mp4");
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

        Uri video = Uri.parse("android.resource://"+getPackageName()+"/raw/gp");
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
                            else kor.setText("Oh, yeah.");

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


        startSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    vv.start();
                    startbtn=false;
                    recordAudio();


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
