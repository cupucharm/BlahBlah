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

public class VideoLearnIT extends AppCompatActivity {
    private static final int REQUEST_CODE = 1234;
    public static final String RESULTS_RECOGNITION="DEFAULT";
    MediaPlayer player;
    MediaRecorder recorder;
    VideoView vv;
    TextView txtSpeechInput;
    Button btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    Dialog match_text_dialog;
    String id, name, phone, today, title;


    ListView textlist;
    ImageView btn_toolbar_back;
    ImageView btn_toolbar_v;
    ArrayList<String> matches_text;

    int ttt;

    TextView eng;
    TextView kor;

    Button eng_btn;
    Button kor_btn;
    boolean eng_s=true;
    boolean kor_s=true;

    String filename;
    int position = 0;
    double[] stop_time = {10, 22, 40, 47, 60, 72, 92, 99, 138};

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
        Log.d("비디오인턴..", id+ " " + name+" "+phone+" "+today);
        //Toast.makeText(this, "비디오인턴... "+id + " " + name + " " + phone + " " + today+" "+title, Toast.LENGTH_LONG).show();



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

                Intent intent = new Intent(getApplicationContext(), ShadowingIT.class);
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

        Uri video = Uri.parse("android.resource://"+getPackageName()+"/raw/it");
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
                            if(vv.getCurrentPosition()<4000) eng.setText("Retirement? That is an ongoing, relentless effort in creativity.");
                            else if(vv.getCurrentPosition()<8000) eng.setText("Tried yoga, learned to cook, bought some plants, too classes in Mandarin. ");
                            else if(vv.getCurrentPosition()<10000) eng.setText("Believe me, I’ve tried everything.");
                            else if(vv.getCurrentPosition()<13000) eng.setText("I just know there’s a hole in my life and I need to fill it.");
                            else if(vv.getCurrentPosition()<17000) eng.setText("Soon.");
                            else if(vv.getCurrentPosition()<20000) eng.setText("I’m Ben Whittaker. I have an appointment with Ms. Ostin.");
                            else if(vv.getCurrentPosition()<21000) eng.setText("I thought she was meeting with her new intern.");
                            else if(vv.getCurrentPosition()<23000) eng.setText("That’s me.");
                            else if(vv.getCurrentPosition()<25000) eng.setText("How old are you?");
                            else if(vv.getCurrentPosition()<26000) eng.setText("Seventy. You?");
                            else if(vv.getCurrentPosition()<29000) eng.setText("I’m 24. I know, I look older. It’s the job.");
                            else if(vv.getCurrentPosition()<31000) eng.setText("It ages you. Which won’t be great in your case.");
                            else if(vv.getCurrentPosition()<34000) eng.setText("Sorry.");
                            else if(vv.getCurrentPosition()<35000) eng.setText("Hi, Jules?");
                            else if(vv.getCurrentPosition()<37000) eng.setText("I’m Ben, your new intern.");
                            else if(vv.getCurrentPosition()<38000) eng.setText("I’m glad you also see the humor in this.");
                            else if(vv.getCurrentPosition()<41000) eng.setText("Be hard not to.");
                            else if(vv.getCurrentPosition()<43000) eng.setText("Don’t feel like you have to dress up.");
                            else if(vv.getCurrentPosition()<44000) eng.setText("I’m comfortable in a suit, if it’s okay.");
                            else if(vv.getCurrentPosition()<45000) eng.setText("Old-school.");
                            else if(vv.getCurrentPosition()<46000) eng.setText("At least, I’ll stand out.");
                            else if(vv.getCurrentPosition()<49000) eng.setText("I don’t think you need a suit to do that.");
                            else if(vv.getCurrentPosition()<50000) eng.setText("Want the door open or closed?");
                            else if(vv.getCurrentPosition()<52000) eng.setText("Doesn’t matter.");
                            else if(vv.getCurrentPosition()<55000) eng.setText("Open, actually.");
                            else if(vv.getCurrentPosition()<56000) eng.setText("You’ll get used to me.");
                            else if(vv.getCurrentPosition()<58000) eng.setText("Look forward to it.");
                            else if(vv.getCurrentPosition()<59000) eng.setText("My intern sure keeps busy.");
                            else if(vv.getCurrentPosition()<61000) eng.setText("Mr. Congeniality. Everybody loves him.");
                            else if(vv.getCurrentPosition()<62000) eng.setText("Here she comes.");
                            else if(vv.getCurrentPosition()<64000) eng.setText("Hey, Beck. What’s up? You look really nice and…");
                            else if(vv.getCurrentPosition()<66000) eng.setText("How long can a woman be mad at you for?");
                            else if(vv.getCurrentPosition()<68000) eng.setText("I assume you talked to her, apologized.");
                            else if(vv.getCurrentPosition()<69000) eng.setText("I e-mailed her.");
                            else if(vv.getCurrentPosition()<71000) eng.setText("Subject line I wrote, I’m sorry with like a ton of “O’s”.");
                            else if(vv.getCurrentPosition()<73000) eng.setText("So it was like, “I’m sorry,”");
                            else if(vv.getCurrentPosition()<78000) eng.setText("with a sad emoticon where he’s crying.");
                            else if(vv.getCurrentPosition()<81000) eng.setText("Our investors just think ");
                            else if(vv.getCurrentPosition()<82000) eng.setText("a seasoned CEO could take some things off your plate.");
                            else if(vv.getCurrentPosition()<85000) eng.setText("I did not see that coming.");
                            else if(vv.getCurrentPosition()<89000) eng.setText("Jules is tryin’ to do right by everybody, the company, the family.");
                            else if(vv.getCurrentPosition()<92000) eng.setText("Pressure is unbelievable.");
                            else if(vv.getCurrentPosition()<95000) eng.setText("You started this business all by yourself a year and a half ago,");
                            else if(vv.getCurrentPosition()<98000) eng.setText("and now you have a staff of 220 people.");
                            else if(vv.getCurrentPosition()<104000) eng.setText("Remember who did that.");
                            else if(vv.getCurrentPosition()<105000) eng.setText("Good times!");
                            else if(vv.getCurrentPosition()<106000) eng.setText("The truth is… ");
                            else if(vv.getCurrentPosition()<110000) eng.setText("Something about you makes me feel calm or more centered or something.");
                            else if(vv.getCurrentPosition()<112000) eng.setText("I could use that. Obviously.");
                            else if(vv.getCurrentPosition()<116000) eng.setText("How, in one generation, ");
                            else if(vv.getCurrentPosition()<121000) eng.setText("men gone from guys like Jack Nicholson and Harrison Ford to…");
                            else if(vv.getCurrentPosition()<125000) eng.setText("Oh boy.");
                            else if(vv.getCurrentPosition()<126000) eng.setText("I’m Fiona, the house masseuse.");
                            else if(vv.getCurrentPosition()<129000) eng.setText("There’s another oldie but goodie here.");
                            else if(vv.getCurrentPosition()<130000) eng.setText("How’s that, Ben?");
                            else if(vv.getCurrentPosition()<133000) eng.setText("Here you go.");
                            else kor.setText("You’re not as old as I thought you were.");

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
                            if(vv.getCurrentPosition()<4000) kor.setText("퇴직 후에 오히려 더 바쁜 삶을 보냈어요");
                            else if(vv.getCurrentPosition()<8000) kor.setText("요가, 요리, 화초 재배, 중국어도 배우러 다녔고");
                            else if(vv.getCurrentPosition()<10000) kor.setText("할 수 있는건 뭐든 다 해봤죠");
                            else if(vv.getCurrentPosition()<13000) kor.setText("난 그저 내 삶에 난 구멍을 채우고 싶어요");
                            else if(vv.getCurrentPosition()<17000) kor.setText("최대한 빨리요");
                            else if(vv.getCurrentPosition()<20000) kor.setText("벤 휘태커인데 대표님과 면담 있어요");
                            else if(vv.getCurrentPosition()<21000) kor.setText("인턴 만나시기로 했는데");
                            else if(vv.getCurrentPosition()<23000) kor.setText("그게 나예요");
                            else if(vv.getCurrentPosition()<25000) kor.setText("연세가 어떻게 되시죠?");
                            else if(vv.getCurrentPosition()<26000) kor.setText("70이요, 그 쪽은?");
                            else if(vv.getCurrentPosition()<29000) kor.setText("24살이요. 일하느라 폭삭 늙었죠");
                            else if(vv.getCurrentPosition()<31000) kor.setText("어르신은 그럴 일 없겠네요");
                            else if(vv.getCurrentPosition()<34000) kor.setText("죄송해요");
                            else if(vv.getCurrentPosition()<35000) kor.setText("줄스 대표님?");
                            else if(vv.getCurrentPosition()<37000) kor.setText("벤입니다. 새 인턴이죠");
                            else if(vv.getCurrentPosition()<38000) kor.setText("선생님도 이 상황이 웃기시죠?");
                            else if(vv.getCurrentPosition()<41000) kor.setText("안 웃기면 이상한 거죠");
                            else if(vv.getCurrentPosition()<43000) kor.setText("정장 안 입으셔도 돼요");
                            else if(vv.getCurrentPosition()<44000) kor.setText("난 정장이 편한데 괜찮겠죠?");
                            else if(vv.getCurrentPosition()<45000) kor.setText("그럼요");
                            else if(vv.getCurrentPosition()<46000) kor.setText("눈에도 잘 띄고 좋죠");
                            else if(vv.getCurrentPosition()<49000) kor.setText("옷으로 튀실 필요는 없어요");
                            else if(vv.getCurrentPosition()<50000) kor.setText("닫을까요? 열어둘까요?");
                            else if(vv.getCurrentPosition()<52000) kor.setText("상관없어요");
                            else if(vv.getCurrentPosition()<55000) kor.setText("열어두세요");
                            else if(vv.getCurrentPosition()<56000) kor.setText("곧 제 변덕에 익숙해지실 거예요");
                            else if(vv.getCurrentPosition()<58000) kor.setText("기대하죠");
                            else if(vv.getCurrentPosition()<59000) kor.setText("새 인턴은 항상 바쁘네");
                            else if(vv.getCurrentPosition()<61000) kor.setText("완전 인기남이야. 다들 좋아해");
                            else if(vv.getCurrentPosition()<62000) kor.setText("저기, 베키예요.");
                            else if(vv.getCurrentPosition()<64000) kor.setText("베키, 안녕? 오늘 너무 예쁘다");
                            else if(vv.getCurrentPosition()<66000) kor.setText("여자가 화나면 얼마나 가죠?");
                            else if(vv.getCurrentPosition()<68000) kor.setText("뭘 잘못했는데? 사과는 했어?");
                            else if(vv.getCurrentPosition()<69000) kor.setText("이메일을 보냈죠");
                            else if(vv.getCurrentPosition()<71000) kor.setText("진심이 담긴 장문의 이메일이었는데");
                            else if(vv.getCurrentPosition()<73000) kor.setText("제목도 '진짜 미아아아아아아안해'고");
                            else if(vv.getCurrentPosition()<38000) kor.setText("아주 슬픈 표정의 이모티콘도 곁들였죠");
                            else if(vv.getCurrentPosition()<81000) kor.setText("투자가들은 노련한 CEO가");
                            else if(vv.getCurrentPosition()<82000) kor.setText("경영에 참여하길 원해");
                            else if(vv.getCurrentPosition()<85000) kor.setText("이런 날이 올 줄 몰랐어");
                            else if(vv.getCurrentPosition()<89000) kor.setText("대표님은 회사와 가족 모두를 위해 최선을 다하죠");
                            else if(vv.getCurrentPosition()<92000) kor.setText("분명 스트레스가 심할거예요");
                            else if(vv.getCurrentPosition()<95000) kor.setText("1년 반 전에 혼자 창업해서");
                            else if(vv.getCurrentPosition()<98000) kor.setText("직원 220명의 회사로 키운 게");
                            else if(vv.getCurrentPosition()<104000) kor.setText("누군지 잊지 말아요");
                            else if(vv.getCurrentPosition()<105000) kor.setText("좋을 때야");
                            else if(vv.getCurrentPosition()<106000) kor.setText("솔직히");
                            else if(vv.getCurrentPosition()<110000) kor.setText("덕분에 차분해지고 자신감도 생겨요");
                            else if(vv.getCurrentPosition()<112000) kor.setText("제게 꼭 필요한 거죠 아시겠지만...");
                            else if(vv.getCurrentPosition()<116000) kor.setText("잭 니콜슨, 해리슨 포드나 벤 같은");
                            else if(vv.getCurrentPosition()<121000) kor.setText("옛날 남자들이 훨씬 멋진 거 같아");
                            else if(vv.getCurrentPosition()<125000) kor.setText("맙소사");
                            else if(vv.getCurrentPosition()<126000) kor.setText("피오나예요. 사내 마사지사죠");
                            else if(vv.getCurrentPosition()<129000) kor.setText("같은 연배를 만나 너무 좋네요");
                            else if(vv.getCurrentPosition()<130000) kor.setText("어때요?");
                            else if(vv.getCurrentPosition()<133000) kor.setText("이런");
                            else if(vv.getCurrentPosition()<136000) kor.setText("이걸로 가려요");
                            else kor.setText("아직 한창이시네요!");

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