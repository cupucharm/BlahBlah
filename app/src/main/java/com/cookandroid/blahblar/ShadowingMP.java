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

public class ShadowingMP extends AppCompatActivity {
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
                Intent intent = new Intent(getApplicationContext(), VideoLearnMP.class);

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
        File file_b=new File(sdcard, "./MP/");
        boolean success=false;
        if(!file_b.exists()){
            success=file_b.mkdir();
        }
        File file = new File(sdcard, "./MP/"+id+"_"+today+"_"+title+".mp4");
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

        Uri video = Uri.parse("android.resource://"+getPackageName()+"/raw/paris");
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
                            if(vv.getCurrentPosition()<2100) eng.setText("Because this is unbelievable. There's no city like this in the world.");
                            else if(vv.getCurrentPosition()<2500) eng.setText("You're in love with a fantasy.");
                            else if(vv.getCurrentPosition()<2800) eng.setText("I'm in love with you.");
                            else if(vv.getCurrentPosition()<3100) eng.setText("What are you doing here?");
                            else if(vv.getCurrentPosition()<2500) eng.setText("Dad's on business and we just decided to free-loaded along.");
                            else if(vv.getCurrentPosition()<2800) eng.setText("That's great. We can spend some time together.");
                            else if(vv.getCurrentPosition()<3100) eng.setText("Well, I think we have a lot of commitments, but I’m sure it’s...");
                            else if(vv.getCurrentPosition()<9000) eng.setText("what?");
                            else if(vv.getCurrentPosition()<11000) eng.setText("If I’m not mistaken, Rodin’s work was influenced by his wife, Camille.");
                            else if(vv.getCurrentPosition()<14000) eng.setText("Rose was the wife.");
                            else if(vv.getCurrentPosition()<18000) eng.setText("No, he was never married to Rose.");
                            else if(vv.getCurrentPosition()<21000) eng.setText("I hope you're not going to be as anti-social tomorrow.");
                            else if(vv.getCurrentPosition()<26000) eng.setText("I'm not quite as taken with him as you are.");
                            else if(vv.getCurrentPosition()<30000) eng.setText("He's a pseudo-intellectual.");
                            else if(vv.getCurrentPosition()<32000) eng.setText("slightly more tannic than the 59, and I prefer a smoky feeling.");
                            else if(vv.getCurrentPosition()<36000) eng.setText("Carol and I are gonna go dancing.");
                            else if(vv.getCurrentPosition()<42000) eng.setText("we heard of a great place. Interested?");
                            else if(vv.getCurrentPosition()<44000) eng.setText("Sure, yes");
                            else if(vv.getCurrentPosition()<46000) eng.setText("No, no. I don’t want to be a killjoy, but I need to get a little fresh air.");
                            else if(vv.getCurrentPosition()<48000) eng.setText("What time did you get in last night?");
                            else if(vv.getCurrentPosition()<50000) eng.setText("Not that late.");
                            else if(vv.getCurrentPosition()<52000) eng.setText("I'll find up going on another little hike tonight.");
                            else if(vv.getCurrentPosition()<54000) eng.setText("Where did Gil run off to?");
                            else if(vv.getCurrentPosition()<56000) eng.setText("he likes to walk around Paris.");
                            else if(vv.getCurrentPosition()<59000) eng.setText("What do you think Gil goes every night?");
                            else if(vv.getCurrentPosition()<63000) eng.setText("He walks and gets ideas.");
                            else if(vv.getCurrentPosition()<65000) eng.setText("Why are you so dressed up?");
                            else if(vv.getCurrentPosition()<68000) eng.setText("No, I was just doing a writing.");
                            else if(vv.getCurrentPosition()<70000) eng.setText("You dress up and put on cologne to write?");
                            else if(vv.getCurrentPosition()<71000) eng.setText("You know how I think better in the shower, and I get the positive ions going in there.");
                            else if(vv.getCurrentPosition()<73000) eng.setText("I had a private detective follow him.");
                            else if(vv.getCurrentPosition()<76000) eng.setText("What happened?");
                            else if(vv.getCurrentPosition()<79000) eng.setText("I don't know. The detective agency says the detective is missing.");
                            else if(vv.getCurrentPosition()<81000) eng.setText("I'm in very perplexing situation.");
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
