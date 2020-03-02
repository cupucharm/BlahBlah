package com.cookandroid.blahblar;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ReviewWordGP extends AppCompatActivity {
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
        eng_3.add("harm");
        kor_3.add("해, 해를 끼치다");
        eng_3.add("a bunch of");
        kor_3.add("다수의");
        eng_3.add("stuff");
        kor_3.add("물건, 물질");
        eng_3.add("damnation");
        kor_3.add("지옥살이");

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

        dialogView_3 = (View) View.inflate(ReviewWordGP.this,R.layout.dialog, null);
        engEdt_3 = (EditText) dialogView_3.findViewById(R.id.engEdt_3);
        korEdt_3 = (EditText) dialogView_3.findViewById(R.id.korEdt_3);

        // 추가 버튼 클릭
        add_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(ReviewWordGP.this);
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
                AlertDialog.Builder dlg = new AlertDialog.Builder(ReviewWordGP.this);
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
