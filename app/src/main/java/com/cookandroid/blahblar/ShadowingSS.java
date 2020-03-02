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

public class ShadowingSS extends AppCompatActivity {
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
        Log.d("비디오쉐도잉..", id + " " + name + " " + phone + " " + today);
        //Toast.makeText(this, "쉐도잉상사... " + id + " " + name + " " + phone + " " + today + " " + title, Toast.LENGTH_LONG).show();

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
                Intent intent = new Intent(getApplicationContext(), VideoLearnSS.class);
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

        eng_btn = (Button) findViewById(R.id.eng_btn);
        kor_btn = (Button) findViewById(R.id.kor_btn);
        kor = (TextView) findViewById(R.id.korea);
        eng = (TextView) findViewById(R.id.english);
        startSD=(Button)findViewById(R.id.startSD);

        permissionCheck();

        Long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        today = sdf.format(date);

        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, "./SS/" + id + "_" + today + "_" + title + ".mp4");
        filename = file.getAbsolutePath();
        Log.d("ShadowingActivity", "저장할 파일 명 : " + filename);

        eng_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                while (true) {
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

        Uri video = Uri.parse("android.resource://" + getPackageName() + "/raw/ss");
        vv.setMediaController(mc);
        vv.setVideoURI(video);
        vv.requestFocus();


        new Thread() {
            @Override
            public void run() {
                while (true) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (vv.getCurrentPosition() < 2000)
                                eng.setText("Did you return my jacket?");
                            else if (vv.getCurrentPosition() < 3000) eng.setText("Yes");
                            else if (vv.getCurrentPosition() < 7000)
                                eng.setText("And after a conversation with their sales woman that ended with us in tears, I got you a full refund");
                            else if (vv.getCurrentPosition() < 8000) eng.setText("I want it back");
                            else if (vv.getCurrentPosition() < 10000) eng.setText("Absolutely");
                            else if (vv.getCurrentPosition() < 12000)
                                eng.setText("This computer isn't taking my password");
                            else if (vv.getCurrentPosition() < 13000)
                                eng.setText("Oh, no, no, that's--");
                            else if (vv.getCurrentPosition() < 14000)
                                eng.setText("[grunting, then yells]");
                            else if (vv.getCurrentPosition() < 15000)
                                eng.setText("That's my laptop");
                            else if (vv.getCurrentPosition() < 17000)
                                eng.setText("Bring me my laptop");
                            else if (vv.getCurrentPosition() < 18000)
                                eng.setText("Wake me up at midnight");
                            else if (vv.getCurrentPosition() < 19000)
                                eng.setText("But don't startle me");
                            else if (vv.getCurrentPosition() < 20000) eng.setText("Play a lullaby");
                            else if (vv.getCurrentPosition() < 22000)
                                eng.setText("Then slowly increase the volume");
                            else if (vv.getCurrentPosition() < 23000)
                                eng.setText("What are you doing here?");
                            else if (vv.getCurrentPosition() < 24000)
                                eng.setText("I'm always the last one");
                            else if (vv.getCurrentPosition() < 26000)
                                eng.setText("I'm always the last one");
                            else if (vv.getCurrentPosition() < 28000)
                                eng.setText("They are always in this office");
                            else if (vv.getCurrentPosition() < 30000)
                                eng.setText("Let's lock them in a room so they can have sex");
                            else if (vv.getCurrentPosition() < 33000)
                                eng.setText("When they're boning, we're free");
                            else if (vv.getCurrentPosition() < 35000)
                                eng.setText("'My Type' playing]");
                            else if (vv.getCurrentPosition() < 39000)
                                eng.setText("We wanted to shut down the elevator so two people could fall in love");
                            else if (vv.getCurrentPosition() < 40000)
                                eng.setText("Whoa, who's this guy?");
                            else if (vv.getCurrentPosition() < 41000) eng.setText("You need to go");
                            else if (vv.getCurrentPosition() < 42000) eng.setText("It's go time!");
                            else if (vv.getCurrentPosition() < 45000)
                                eng.setText("Oh,god. This is how it starts in my nightmare");
                            else if (vv.getCurrentPosition() < 46000)
                                eng.setText("What are you doing?");
                            else if (vv.getCurrentPosition() < 47000)
                                eng.setText("What is he doing?");
                            else if (vv.getCurrentPosition() < 48000)
                                eng.setText("Stop doing that, Stop right now");
                            else if (vv.getCurrentPosition() < 49000)
                                eng.setText("I need to be free of this");
                            else if (vv.getCurrentPosition() < 50000)
                                eng.setText("♪ Take a look… ♪");
                            else if (vv.getCurrentPosition() < 51000)
                                eng.setText("We know everything");
                            else if (vv.getCurrentPosition() < 52000)
                                eng.setText("What they like, don't like");
                            else if (vv.getCurrentPosition() < 55000)
                                eng.setText("We are the men behind the curtain");
                            else if (vv.getCurrentPosition() < 56000)
                                eng.setText("Does Rick have good Yankees tickets?");
                            else if (vv.getCurrentPosition() < 60000)
                                eng.setText("No, He watches from the bleachers like a peasant. Of course he does");
                            else if (vv.getCurrentPosition() < 63000)
                                eng.setText("We need the people in these two seats to make out");
                            else if (vv.getCurrentPosition() < 67000)
                                eng.setText("[crowd chanting] Kiss, Kiss, Kiss");
                            else if (vv.getCurrentPosition() < 68000) eng.setText("[cheering]");
                            else if (vv.getCurrentPosition() < 69000)
                                eng.setText("[crowd cheering]");
                            else if (vv.getCurrentPosition() < 70000)
                                eng.setText("The world's our oyster");
                            else if (vv.getCurrentPosition() < 71000) eng.setText("We’re free.");
                            else if (vv.getCurrentPosition() < 72000)
                                eng.setText("♪ when there’s lovin’… ♪");
                            else if (vv.getCurrentPosition() < 73000)
                                eng.setText("Taking the rest of the day.");
                            else if (vv.getCurrentPosition() < 74000) eng.setText("I’ll be late.");
                            else if (vv.getCurrentPosition() < 75000) eng.setText("We did it");
                            else if (vv.getCurrentPosition() < 77000)
                                eng.setText("We made two miserable people happy");
                            else if (vv.getCurrentPosition() < 78000)
                                eng.setText("I'm proud of us");
                            else if (vv.getCurrentPosition() < 79000) eng.setText("Yes!");
                            else if (vv.getCurrentPosition() < 80000)
                                eng.setText("[Harper shrieks]");
                            else if (vv.getCurrentPosition() < 81000) eng.setText("Yes!");
                            else if (vv.getCurrentPosition() < 84000)
                                eng.setText("Do you guys know each other?");
                            else if (vv.getCurrentPosition() < 85000)
                                eng.setText("We're friends   -That's nice");
                            else if (vv.getCurrentPosition() < 87000)
                                eng.setText("Why'd you say it like that?");
                            else if (vv.getCurrentPosition() < 91000)
                                eng.setText("Why'd your voice go up?");
                            else if (vv.getCurrentPosition() < 95000)
                                eng.setText("♪ You’re, you’re, You’re just my type ♪");
                            else if (vv.getCurrentPosition() < 98000)
                                eng.setText("♪ Oh, you got a pulse And you are breathing ♪");
                            else if (vv.getCurrentPosition() < 99000) eng.setText("[yells]");
                            else if (vv.getCurrentPosition() < 100000)
                                eng.setText("Code Red, Code Black.");
                            else if (vv.getCurrentPosition() < 103000)
                                eng.setText("I’m not gonna pretend to let him teach me about how the world works.");
                            else if (vv.getCurrentPosition() < 105000)
                                eng.setText("♪ You’re just my type ♪");
                            else if (vv.getCurrentPosition() < 106000)
                                eng.setText(" Save yourself.");
                            else if (vv.getCurrentPosition() < 107000)
                                eng.setText("♪ Oh, l think it’s time… ♪");
                            else if (vv.getCurrentPosition() < 108000)
                                eng.setText("We’re full-on parent trapping.");
                            else if (vv.getCurrentPosition() < 110000)
                                eng.setText("No, we’re not.");
                            else if (vv.getCurrentPosition() < 114000)
                                eng.setText("I’ve seen the Lindsay Lohan classic enough times to know that we’re full-on parent trapping.");
                            else if (vv.getCurrentPosition() < 116000) eng.setText("[laughs]");
                            else if (vv.getCurrentPosition() < 119000)
                                eng.setText("All I care about is that I'm not still an assistant when I'm 28");
                            else if (vv.getCurrentPosition() < 120000) eng.setText("I'm 28");
                            else if (vv.getCurrentPosition() < 122000)
                                eng.setText("Oh, my God. I'm so sorry");
                            else eng.setText("For you. That's very sad for you");

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
                while (true) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (vv.getCurrentPosition() < 2000) kor.setText("내 재킷 환불했어?");
                            else if (vv.getCurrentPosition() < 3000) kor.setText("네");
                            else if (vv.getCurrentPosition() < 4000)
                                kor.setText("판매원과 아주 진지한 대화를 나누고 둘 다 눈물을 흘린 후에 전액 환불 받았어요.");
                            else if (vv.getCurrentPosition() < 8000) kor.setText("다시 사와");
                            else if (vv.getCurrentPosition() < 10000) kor.setText("그러죠");
                            else if (vv.getCurrentPosition() < 12000) kor.setText("내 비밀번호가 안 먹혀");
                            else if (vv.getCurrentPosition() < 14000) kor.setText("아니, 그건 제...");
                            else if (vv.getCurrentPosition() < 15000) kor.setText("그건 제 노트북이에요");
                            else if (vv.getCurrentPosition() < 17000) kor.setText("노트북 가져와");
                            else if (vv.getCurrentPosition() < 18000) kor.setText("자정에 깨워줘");
                            else if (vv.getCurrentPosition() < 19000) kor.setText("하지만 놀라게 하지 마");
                            else if (vv.getCurrentPosition() < 22000)
                                kor.setText("자장가를 틀고 천천히 볼륨을 높여");
                            else if (vv.getCurrentPosition() < 23000) kor.setText("여태 안 가고 뭐 해요?");
                            else if (vv.getCurrentPosition() < 24000) kor.setText("난 늘 마지막에 퇴근해요");
                            else if (vv.getCurrentPosition() < 28000)
                                kor.setText("마지막에 퇴근하는 건 나예요");
                            else if (vv.getCurrentPosition() < 30000)
                                kor.setText("둘 다 항상 사무실에 있으니까 한 방에 가둬두고 섹스하게 하면 어때요?");
                            else if (vv.getCurrentPosition() < 35000)
                                kor.setText("둘이 재미 볼 때 우린 자유예요");
                            else if (vv.getCurrentPosition() < 39000)
                                kor.setText("두 사람이 사랑에 빠지게 잠깐 엘레베이터를 정지시켰으면 해요");
                            else if (vv.getCurrentPosition() < 40000) kor.setText("이 남자는 누구죠?");
                            else if (vv.getCurrentPosition() < 41000) kor.setText("얼른 나가요");
                            else if (vv.getCurrentPosition() < 42000) kor.setText("시작해보죠!");
                            else if (vv.getCurrentPosition() < 45000)
                                kor.setText("맙소사, 내 악몽은 늘 이렇게 시작해");
                            else if (vv.getCurrentPosition() < 46000) kor.setText("뭐하는 거예요?");
                            else if (vv.getCurrentPosition() < 47000) kor.setText("뭐 하는 거죠?");
                            else if (vv.getCurrentPosition() < 48000) kor.setText("그러지 마요, 당장 멈춰요");
                            else if (vv.getCurrentPosition() < 50000) kor.setText("이걸 벗어버려야 해요");
                            else if (vv.getCurrentPosition() < 52000)
                                kor.setText("우린 둘의 모든 걸 알아요. 뭘 좋아하는지, 싫어하는지");
                            else if (vv.getCurrentPosition() < 55000) kor.setText("우리가 배후자예요");
                            else if (vv.getCurrentPosition() < 56000)
                                kor.setText("릭한테 좋은 양키스 티켓 있어요");
                            else if (vv.getCurrentPosition() < 60000)
                                kor.setText("아뇨, 처량하게 외야석에서 봐요. 당연히 있죠");
                            else if (vv.getCurrentPosition() < 63000)
                                kor.setText("이 두 좌석에 앉은 사람들이 키스하게 해줘요");
                            else if (vv.getCurrentPosition() < 69000) kor.setText("키스!");
                            else if (vv.getCurrentPosition() < 70000) kor.setText("이 세상은 우리 거예요!");
                            else if (vv.getCurrentPosition() < 72000) kor.setText("자유다!");
                            else if (vv.getCurrentPosition() < 73000) kor.setText("오후엔 쉴게");
                            else if (vv.getCurrentPosition() < 74000) kor.setText("내일 늦을 거야");
                            else if (vv.getCurrentPosition() < 77000)
                                kor.setText("우리가 해냈어요,불행한 두 사람을 행복하게 만들었어요.");
                            else if (vv.getCurrentPosition() < 78000) kor.setText("우리가 자랑스러워요.");
                            else if (vv.getCurrentPosition() < 80000) kor.setText("좋았어!");
                            else if (vv.getCurrentPosition() < 81000) kor.setText("멋져!");
                            else if (vv.getCurrentPosition() < 84000) kor.setText("둘이 어떻게 아는 사이죠?");
                            else if (vv.getCurrentPosition() < 8500) kor.setText("친구예요");
                            else if (vv.getCurrentPosition() < 85500) kor.setText("멋지네");
                            else if (vv.getCurrentPosition() < 87000) kor.setText("왜 그런 식으로 말해?");
                            else if (vv.getCurrentPosition() < 99000) kor.setText("왜 목소리 톤이 높아져?");
                            else if (vv.getCurrentPosition() < 100000) kor.setText("코드 레드,코드 블랙");
                            else if (vv.getCurrentPosition() < 105000)
                                kor.setText("그 사람이 나한테 세상의 이치를 가르치려는 꼴은 못 봐");
                            else if (vv.getCurrentPosition() < 107000) kor.setText("애쓰지 말아요");
                            else if (vv.getCurrentPosition() < 108000)
                                kor.setText("부모를 재결합시키려는 자식들 같아요");
                            else if (vv.getCurrentPosition() < 110000) kor.setText("그렇지 않아요");
                            else if (vv.getCurrentPosition() < 116000)
                                kor.setText("린제이 로한의 옛날 영화를 하도 많이 봐서 이게 그 상황이라는 건 확실히 알아요");
                            else if (vv.getCurrentPosition() < 119000)
                                kor.setText("내가 28살일 때도 비서라면 너무 비참할 거예요");
                            else if (vv.getCurrentPosition() < 120000) kor.setText("난 28살이에요");
                            else if (vv.getCurrentPosition() < 122000) kor.setText("맙소사 참 딱하네요 ");
                            else kor.setText("당신이 너무 불쌍해요");

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
                    startbtn = false;
                    recordAudio();


                    vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
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