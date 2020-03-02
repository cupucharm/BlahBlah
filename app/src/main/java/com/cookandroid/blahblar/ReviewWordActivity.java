package com.cookandroid.blahblar;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ReviewWordActivity extends AppCompatActivity {
    ImageView btn_toolbar_back;
    ListView list2_3;
    Button add_3, del_3;
    EditText engEdt_3, korEdt_3;
    View dialogView_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        btn_toolbar_back = (ImageView) findViewById(R.id.btn_toolbar_back_b);
        btn_toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final ArrayList<String> eng_3 = new ArrayList<String>();  //영어 저장
        final ArrayList<String> kor_3 = new ArrayList<String>();  //한글 저장
        final ArrayList<String> word_3 = new ArrayList<String>();
        final ArrayAdapter<String> adapter2_3 = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_multiple_choice, word_3);
        //예시
        eng_3.add("celebration");
        kor_3.add("기념[축하] 행사");
        eng_3.add("harmony");
        kor_3.add("조화, 화합");
        eng_3.add("legend");
        kor_3.add("전설");
        eng_3.add("precinct");
        kor_3.add("구역");
        eng_3.add("understand");
        kor_3.add("이해하다");
        eng_3.add("trainer");
        kor_3.add("교육[훈련]시키는 사람, 트레이너");
        eng_3.add("jeez");
        kor_3.add("에이, 이크(화·놀람 등을 나타내는 소리)");
        eng_3.add("stapler");
        kor_3.add("스테이플러");
        eng_3.add("electrocute");
        kor_3.add("감전 사고를 입히다");
        eng_3.add("adorable");
        kor_3.add("사랑스러운");
        eng_3.add("detective");
        kor_3.add("형사, 수사관");
        eng_3.add("pop");
        kor_3.add("아저씨, 아빠");
        eng_3.add("brought");
        kor_3.add("bring(가져오다)의 과거, 과거분사");
        eng_3.add("shove");
        kor_3.add("(거칠게) 밀치다");

        for(int i =0 ; i< eng_3.size(); i++){
            String eng = eng_3.get(i);
            String kor = kor_3.get(i);
            word_3.add(eng+"\t : \t"+kor);
        }
        adapter2_3.notifyDataSetChanged();

        list2_3 = (ListView)findViewById(R.id.listView2_3);
        list2_3.setAdapter(adapter2_3);


        add_3 = (Button)findViewById(R.id.add_3);
        del_3 = (Button)findViewById(R.id.del_3);

        dialogView_3 = (View) View.inflate(ReviewWordActivity.this,R.layout.dialog, null);
        engEdt_3 = (EditText) dialogView_3.findViewById(R.id.engEdt_3);
        korEdt_3 = (EditText) dialogView_3.findViewById(R.id.korEdt_3);

        // 추가 버튼 클릭
        add_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(ReviewWordActivity.this);
                dlg.setTitle("단어 추가");
                dlg.setView(dialogView_3);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"추가하였습니다.", Toast.LENGTH_SHORT).show();
                        String eng = engEdt_3.getText().toString();
                        String kor = korEdt_3.getText().toString();
                        eng_3.add(eng);
                        kor_3.add(kor);
                        word_3.add(eng+"\t : \t"+kor);
                        adapter2_3.notifyDataSetChanged();
                    }
                });
                dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"취소하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
                dlg.show();
            }
        });

        // 삭제 버튼 클릭
        del_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(ReviewWordActivity.this);
                dlg.setTitle("정말 삭제하시겠습니까?");
                // 확인버튼 누르면
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"삭제하였습니다.", Toast.LENGTH_SHORT).show();
                        SparseBooleanArray checkedItems = list2_3.getCheckedItemPositions();
                        int count = adapter2_3.getCount() ;
                        for (int n = count-1; n >= 0; n--) {
                            if (checkedItems.get(n)) {
                                eng_3.remove(n) ;
                                kor_3.remove(n) ;
                                word_3.remove(n);
                            }
                        }
                        // 모든 선택 상태 초기화.
                        list2_3.clearChoices() ;
                        adapter2_3.notifyDataSetChanged();

                    }
                });
                dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"삭제를 취소하였습니다.", Toast.LENGTH_SHORT).show();
                        list2_3.clearChoices() ;
                        adapter2_3.notifyDataSetChanged();
                    }
                });
                dlg.show();
            }
        });
    }
}
