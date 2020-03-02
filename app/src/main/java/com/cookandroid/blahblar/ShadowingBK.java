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

public class ShadowingBK extends AppCompatActivity {
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
                Intent intent = new Intent(getApplicationContext(), VideoLearnBK.class);

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
        File file_b=new File(sdcard, "./BK/");
        boolean success=false;
        if(!file_b.exists()){
            success=file_b.mkdir();
        }
        File file = new File(sdcard, "./BK/"+id+"_"+today+"_"+title+".mp4");
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

        Uri video = Uri.parse("android.resource://"+getPackageName()+"/raw/brooklyn");
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
                            if(vv.getCurrentPosition()<12000) eng.setText("");
                            else if(vv.getCurrentPosition()<14000) eng.setText(" Step over this way, please.");
                            else if(vv.getCurrentPosition()<15000) eng.setText("Get out of the line.");
                            else if(vv.getCurrentPosition()<16000) eng.setText("Next.");
                            else if(vv.getCurrentPosition()<18000) eng.setText("Passport, please.");
                            else if(vv.getCurrentPosition()<20000) eng.setText("Welcome to the United States, Ma'am.");
                            else if(vv.getCurrentPosition()<24000) eng.setText("");
                            else if(vv.getCurrentPosition()<26000) eng.setText("Dear Rose.");
                            else if(vv.getCurrentPosition()<29000) eng.setText("I miss you and Mother and think about you every day.");
                            else if(vv.getCurrentPosition()<33000) eng.setText("The most important news is that I have a job and I'm in a boarding house.");
                            else if(vv.getCurrentPosition()<40000) eng.setText("I was glad to see you finally got some letters from home today.");
                            else if(vv.getCurrentPosition()<44000) eng.setText("I wish that I could stop feeling that I want to be an Irish girl in Ireland.");
                            else if(vv.getCurrentPosition()<46000) eng.setText("Homesickness is like most sicknesses.");
                            else if(vv.getCurrentPosition()<48000) eng.setText("It will pass.");
                            else if(vv.getCurrentPosition()<50000) eng.setText("Would you dance with me?");
                            else if(vv.getCurrentPosition()<51000) eng.setText("I'm not Irish.");
                            else if(vv.getCurrentPosition()<53000) eng.setText("So what were you doing at an Irish dance?");
                            else if(vv.getCurrentPosition()<56000) eng.setText("I really like Irish girls.");
                            else if(vv.getCurrentPosition()<59000) eng.setText("I met somebody and Italian fella.");
                            else if(vv.getCurrentPosition()<62000) eng.setText("We're going to Coney Island at the weekend.");
                            else if(vv.getCurrentPosition()<63000) eng.setText("But do you have a bathing costume?");
                            else if(vv.getCurrentPosition()<65000) eng.setText("Why didn't you tell me to put my costume on underneath my clothes?");
                            else if(vv.getCurrentPosition()<68000) eng.setText("I thought you'd know.");
                            else if(vv.getCurrentPosition()<70000) eng.setText("Tony!");
                            else if(vv.getCurrentPosition()<71000) eng.setText("I wanna ask you something.");
                            else if(vv.getCurrentPosition()<75000) eng.setText("And you're gonna say \"Oh, it's too soon.\"");
                            else if(vv.getCurrentPosition()<76000) eng.setText("Will you come for dinner and meet my family?");
                            else if(vv.getCurrentPosition()<78000) eng.setText("I'm gonna say splash any time I see problems.");
                            else if(vv.getCurrentPosition()<79000) eng.setText("You like Italian food?");
                            else if(vv.getCurrentPosition()<81000) eng.setText("I'm gonna say splash any time I see problems.");
                            else if(vv.getCurrentPosition()<82000) eng.setText("Good idea.");
                            else if(vv.getCurrentPosition()<83000) eng.setText("Splash!");
                            else if(vv.getCurrentPosition()<87000) eng.setText("You just splashed his mother, his father, and the walls.");
                            else if(vv.getCurrentPosition()<88000) eng.setText("Let's go again.");
                            else if(vv.getCurrentPosition()<91000) eng.setText("Ready?");
                            else if(vv.getCurrentPosition()<93000) eng.setText("I should say that we don't like Irish people.");
                            else if(vv.getCurrentPosition()<94000) eng.setText("Hey, hey.");
                            else if(vv.getCurrentPosition()<95000) eng.setText("What?.");
                            else if(vv.getCurrentPosition()<96000) eng.setText("We don't.");
                            else if(vv.getCurrentPosition()<97000) eng.setText("That is a well-known fact.");
                            else if(vv.getCurrentPosition()<103000) eng.setText("Up.");
                            else if(vv.getCurrentPosition()<105000) eng.setText("I'm sorry.");
                            else if(vv.getCurrentPosition()<107000) eng.setText("When will they bury her?");
                            else if(vv.getCurrentPosition()<109000) eng.setText("You wanna go home, I guess.");
                            else if(vv.getCurrentPosition()<111000) eng.setText("How would it be for you if I did go home?");
                            else if(vv.getCurrentPosition()<113000) eng.setText("I'd be afraid.");
                            else if(vv.getCurrentPosition()<114000) eng.setText("Afraid that I wouldn't come back?");
                            else if(vv.getCurrentPosition()<116000) eng.setText("Yeah.");
                            else if(vv.getCurrentPosition()<121000) eng.setText("Home is home.");
                            else if(vv.getCurrentPosition()<124000) eng.setText("Ireland must seem very backward to you now.");
                            else if(vv.getCurrentPosition()<125000) eng.setText("Is that Jim Farrell I saw?");
                            else if(vv.getCurrentPosition()<127000) eng.setText("He's a catch for someone.");
                            else if(vv.getCurrentPosition()<129000) eng.setText("I have a life halfway across the sea.");
                            else if(vv.getCurrentPosition()<131000) eng.setText("Your life here could be just as good.");
                            else if(vv.getCurrentPosition()<134000) eng.setText("If you go back, I have nobody.");
                            else if(vv.getCurrentPosition()<137000) eng.setText("I want you to stay here, with me.");
                            else if(vv.getCurrentPosition()<143000) eng.setText("");
                            else eng.setText("Home is home.");
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
                            if(vv.getCurrentPosition()<12000) kor.setText("");
                            else if(vv.getCurrentPosition()<14000) kor.setText("이쪽으로 와주세요.");
                            else if(vv.getCurrentPosition()<15000) kor.setText("거기 줄요.");
                            else if(vv.getCurrentPosition()<16000) kor.setText("다음요.");
                            else if(vv.getCurrentPosition()<18000) kor.setText("여권 주세요.");
                            else if(vv.getCurrentPosition()<20000) kor.setText("미국에 오신걸 환영합니다. 아가씨.");
                            else if(vv.getCurrentPosition()<24000) kor.setText("");
                            else if(vv.getCurrentPosition()<26000) kor.setText("로즈에게");
                            else if(vv.getCurrentPosition()<29000) kor.setText("언니랑 엄마가 보고 싶어. 매일같이 가족 생각을 해.");
                            else if(vv.getCurrentPosition()<33000) kor.setText("제일 중요한 소식은 내가 취직을 했고 하숙을 한다는거야.");
                            else if(vv.getCurrentPosition()<40000) kor.setText("오늘 드디어 고향집 편지를 받았다니 잘됐구나.");
                            else if(vv.getCurrentPosition()<44000) kor.setText("아일랜드에서 아일랜드 여자로 남았다면 하는 생각이 안들었으면 좋겠어요.");
                            else if(vv.getCurrentPosition()<46000) kor.setText("향수병은 대개의 병들과 똑같지.");
                            else if(vv.getCurrentPosition()<48000) kor.setText("곧 사라질거야.");
                            else if(vv.getCurrentPosition()<50000) kor.setText("저랑 춤추실래요?");
                            else if(vv.getCurrentPosition()<51000) kor.setText("전 아일랜드인이 아니에요.");
                            else if(vv.getCurrentPosition()<53000) kor.setText("그럼 아일랜드인 무도회엔 왜 왔어요?");
                            else if(vv.getCurrentPosition()<56000) kor.setText("전 아일랜드 여자들이 정말 좋아요.");
                            else if(vv.getCurrentPosition()<59000) kor.setText("누굴 만났는데 이탈리아 남자예요.");
                            else if(vv.getCurrentPosition()<62000) kor.setText("우린 주말에 '코니 아일랜드'에 갈 거예요.");
                            else if(vv.getCurrentPosition()<63000) kor.setText("수영복은 있어?");
                            else if(vv.getCurrentPosition()<65000) kor.setText("옷 속에다 수영복 입으라고 왜 말 안 했어?");
                            else if(vv.getCurrentPosition()<68000) kor.setText("아는 줄 알았지.");
                            else if(vv.getCurrentPosition()<70000) kor.setText("토니!");
                            else if(vv.getCurrentPosition()<71000) kor.setText("네게 할 말이 있는데, ");
                            else if(vv.getCurrentPosition()<75000) kor.setText("넌 이러겠지만 \"어머, 그건 너무 일러.\"");
                            else if(vv.getCurrentPosition()<76000) kor.setText("저녁 먹을 겸 우리 가족 보러 안 올래?");
                            else if(vv.getCurrentPosition()<78000) kor.setText("그러고 말고");
                            else if(vv.getCurrentPosition()<79000) kor.setText("이탈리아 요리 좋아해?");
                            else if(vv.getCurrentPosition()<81000) kor.setText("문제가 보이면 튀었다고 할게.");
                            else if(vv.getCurrentPosition()<82000) kor.setText("생각 좋네.");
                            else if(vv.getCurrentPosition()<83000) kor.setText("튀었어!");
                            else if(vv.getCurrentPosition()<87000) kor.setText("걔네 부모님이랑 벽에 튀었어.");
                            else if(vv.getCurrentPosition()<88000) kor.setText("다시 해.");
                            else if(vv.getCurrentPosition()<91000) kor.setText("준비 됐어?");
                            else if(vv.getCurrentPosition()<93000) kor.setText("우린 아일랜드인을 안 좋아해요.\n");
                            else if(vv.getCurrentPosition()<94000) kor.setText("야");
                            else if(vv.getCurrentPosition()<95000) kor.setText("왜요?");
                            else if(vv.getCurrentPosition()<96000) kor.setText("안 좋아하잖아요!");
                            else if(vv.getCurrentPosition()<97000) kor.setText("잘 알려진 사실인데요!");
                            else if(vv.getCurrentPosition()<103000) kor.setText("일어나.");
                            else if(vv.getCurrentPosition()<105000) kor.setText("유감이네.");
                            else if(vv.getCurrentPosition()<107000) kor.setText("장례는 언제 치르죠?");
                            else if(vv.getCurrentPosition()<109000) kor.setText("집에 가고 싶은가봐?");
                            else if(vv.getCurrentPosition()<111000) kor.setText("내가 집에 가면 넌 어떨 것 같아?");
                            else if(vv.getCurrentPosition()<113000) kor.setText("염려될 거야.");
                            else if(vv.getCurrentPosition()<114000) kor.setText("내가 안 돌아올까봐?");
                            else if(vv.getCurrentPosition()<116000) kor.setText("응.");
                            else if(vv.getCurrentPosition()<121000) kor.setText("집은 집이잖아.");
                            else if(vv.getCurrentPosition()<124000) kor.setText("아일랜드는 이제 너무 낙후되어 보이겠네요.");
                            else if(vv.getCurrentPosition()<125000) kor.setText("그 남자 짐 패럴 아니니?");
                            else if(vv.getCurrentPosition()<127000) kor.setText("잘 어울리는 상대구나.");
                            else if(vv.getCurrentPosition()<129000) kor.setText("제가 살 곳은 바다 건너에 있어요.");
                            else if(vv.getCurrentPosition()<131000) kor.setText("여기서의 삶도 괜찮을지 모르죠.");
                            else if(vv.getCurrentPosition()<134000) kor.setText("네가 돌아가면, 난 아무도 없어.");
                            else if(vv.getCurrentPosition()<137000) kor.setText("여기 있어주면 좋겠어요. 저랑요.");
                            else if(vv.getCurrentPosition()<143000) kor.setText("");
                            else kor.setText("집은 집이잖아.");

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

