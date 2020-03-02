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

public class ShadowingTS extends AppCompatActivity {
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
                Intent intent = new Intent(getApplicationContext(), VideoLearnTS.class);

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
        File file_b=new File(sdcard, "./TS/");
        boolean success=false;
        if(!file_b.exists()){
            success=file_b.mkdir();
        }
        File file = new File(sdcard, "./TS/"+id+"_"+today+"_"+title+".mp4");
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

        Uri video = Uri.parse("android.resource://"+getPackageName()+"/raw/toystory");
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
