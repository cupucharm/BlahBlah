package com.cookandroid.blahblar;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AppComponentFactory;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import static android.speech.tts.TextToSpeech.ERROR;

public class ScriptPK  extends AppCompatActivity {
    ListView listViewMP3;
    Button btnReplay, btnStop, btnSTT, btnTTS;
    ImageView btn_toolbar_back;
    String name, id, phone, today, title;
    TextView textView;
    private TextToSpeech myTTS;
    ArrayList<String> pkList;
    String selectedPK;

    String PKPath= Environment.getExternalStorageDirectory().getPath()+"/PK/";
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_script);

        btn_toolbar_back = (ImageView) findViewById(R.id.btn_toolbar_back_sv);
        btn_toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        name = intent.getExtras().getString("name");
        id = intent.getExtras().getString("id");
        phone = intent.getExtras().getString("phone");
        today = intent.getExtras().getString("today");
        title = intent.getExtras().getString("title");

        final ArrayList<String> midList1_3 = new ArrayList<String>();  //오답목록 저장
        final ArrayAdapter<String> adapter1_3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, midList1_3);

        listViewMP3 = (ListView) findViewById(R.id.listscript);
        listViewMP3.setAdapter(adapter1_3);
        textView=(TextView)findViewById(R.id.scrtv);
        btnReplay=(Button)findViewById(R.id.replay);
        btnStop=(Button)findViewById(R.id.stop);
        btnSTT=(Button)findViewById(R.id.stt);
        btnTTS=(Button)findViewById(R.id.tts);

        ActivityCompat.requestPermissions(this,new String[] {android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);

        pkList=new ArrayList<String>();

        File[] listFiles=new File(PKPath).listFiles();
        String fileName, extName, idName;
        int num;
        for(File file: listFiles){
            fileName=file.getName();
            extName= fileName.substring(fileName.length()-3);
            num=fileName.indexOf('_');
            idName=fileName.substring(0,num);

            if((extName.equals((String) "mp4")||extName.equals((String) "mp3"))&&id.equals(idName))
                pkList.add(fileName);
        }

        listViewMP3=(ListView)findViewById(R.id.listscript);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, pkList);
        listViewMP3.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listViewMP3.setAdapter(adapter);
        listViewMP3.setItemChecked(0,true);

        listViewMP3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                selectedPK = pkList.get(arg2);
            }
        });

        selectedPK=pkList.get(0);

        btnReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    mediaPlayer=new MediaPlayer();
                    mediaPlayer.setDataSource(PKPath+selectedPK);
                    Log.d("대체뭐가재생",PKPath+selectedPK);
                    mediaPlayer.prepare();;
                    mediaPlayer.start();
                    btnReplay.setClickable(false);
                    btnStop.setClickable(true);
                }catch (IOException e){
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                btnReplay.setClickable(true);
                btnStop.setClickable(false);
            }
        });

        btnSTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /*btnTTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status != ERROR) {  // 언어를 선택한다.
                            tts.setLanguage(Locale.ENGLISH);
                        }
                    }
                });
                tts.speak(textView.getText().toString(),TextToSpeech.QUEUE_FLUSH, null);
            }
        });*/

    }

    public void onInit(int status) {
        String myText1 = textView.getText().toString();
        //String myText2 = "말하는 스피치 입니다.";
        myTTS.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);
        //myTTS.speak(myText2, TextToSpeech.QUEUE_ADD, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myTTS.shutdown();
    }

}
