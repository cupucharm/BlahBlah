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

public class ShadowingBS extends AppCompatActivity {
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
                Intent intent = new Intent(getApplicationContext(), VideoLearnBS.class);

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
        File file_b=new File(sdcard, "./BS/");
        boolean success=false;
        if(!file_b.exists()){
            success=file_b.mkdir();
        }
        File file = new File(sdcard, "./BS/"+id+"_"+today+"_"+title+".mp4");

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

        Uri video = Uri.parse("android.resource://"+getPackageName()+"/raw/bigshort");
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
