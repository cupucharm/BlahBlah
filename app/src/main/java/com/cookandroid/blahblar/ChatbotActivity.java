package com.cookandroid.blahblar;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatbotActivity extends AppCompatActivity {
    ImageView btn_toolbar_back;
    TextView bt;
    WebView chatbot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        btn_toolbar_back = (ImageView) findViewById(R.id.btn_toolbar_back_ch);
        btn_toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        chatbot = (WebView) findViewById(R.id.chatbot);
        WebSettings webSettings = chatbot.getSettings();
        webSettings.setJavaScriptEnabled(true);
        chatbot.loadUrl("https://console.dialogflow.com/api-client/demo/embedded/4be424a9-d788-4f24-9602-a0fb48f4c623");

    }
}

