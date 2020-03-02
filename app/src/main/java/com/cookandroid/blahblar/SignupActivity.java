package com.cookandroid.blahblar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    ImageView btn_toolbar_back;

    EditText edtSignupName3, edtSignupID3, edtSignupPW3, edtSignupPW_check3, edtSignupNum3;
    Button btnSignupSubmit3, btnSignupID_check3;
    //ImageView btn_toolbar_back3;
    String idno[];
    Boolean flag=false, flag2=true;

    dbHelper helper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btn_toolbar_back = (ImageView) findViewById(R.id.btn_toolbar_back_sign);
        btn_toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        edtSignupName3 = (EditText)findViewById(R.id.edtSignupName3);
        edtSignupID3 = (EditText)findViewById(R.id.edtSignupID3);
        edtSignupPW3 = (EditText)findViewById(R.id.edtSignupPW3);
        edtSignupPW_check3 = (EditText)findViewById(R.id.edtSignupPW_check3);
        edtSignupNum3 = (EditText)findViewById(R.id.edtSignupNum3);
        btnSignupSubmit3 = (Button)findViewById(R.id.btnSignupSubmit3);
        btnSignupID_check3 = (Button)findViewById(R.id.btnSignupID_check3);

        helper = new dbHelper(this);
        try {
            db = helper.getWritableDatabase();
            //데이터베이스 객체를 얻기 위하여 getWritableDatabse()를 호출
        } catch (SQLiteException e) {
            db = helper.getReadableDatabase();
        }
        btnSignupID_check3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag2=true;
                db = helper.getReadableDatabase();
                final String ckId2 = edtSignupID3.getText().toString();;

                StringBuffer sbCk = new StringBuffer();
                sbCk.append("SELECT * FROM user");

                String queryCk = sbCk.toString();
                Cursor cursor;
                cursor = db.rawQuery(queryCk, null);
                idno = new String[cursor.getCount()];
                while (cursor.moveToNext()){ //moveToNext는 다음값이 없으면 null로 while문 빠져나와
                    for(int i=0; i<cursor.getCount();i++){
                        idno[i] = cursor.getString(0);
                        if(idno[i].equals(ckId2)){
                            edtSignupID3.setText(null);
                            Toast.makeText(SignupActivity.this,"중복되는 아이디 입니다.",Toast.LENGTH_SHORT).show();
                            flag2=false;
                        }else{
                            Toast.makeText(SignupActivity.this,"사용가능한 아이디 입니다.",Toast.LENGTH_SHORT).show();
                        }
                        cursor.moveToNext();
                    }
                }

                cursor.close();
                if(edtSignupID3.getText()!=null){
                    flag=true;
                }
            }
        });
        edtSignupName3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(edtSignupName3.hasFocus())
                    edtSignupName3.setBackgroundResource(R.drawable.primary_border);
                else
                    edtSignupName3.setBackgroundResource(R.drawable.orange_border);
            }
        });
        edtSignupID3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(edtSignupID3.hasFocus())
                    edtSignupID3.setBackgroundResource(R.drawable.primary_border);
                else
                    edtSignupID3.setBackgroundResource(R.drawable.orange_border);
            }
        });
        edtSignupPW3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(edtSignupPW3.hasFocus())
                    edtSignupPW3.setBackgroundResource(R.drawable.primary_border);
                else
                    edtSignupPW3.setBackgroundResource(R.drawable.orange_border);
            }
        });
        edtSignupPW_check3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(edtSignupPW_check3.hasFocus())
                    edtSignupPW_check3.setBackgroundResource(R.drawable.primary_border);
                else
                    edtSignupPW_check3.setBackgroundResource(R.drawable.orange_border);
            }
        });
        edtSignupNum3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(edtSignupNum3.hasFocus())
                    edtSignupNum3.setBackgroundResource(R.drawable.primary_border);
                else
                    edtSignupNum3.setBackgroundResource(R.drawable.orange_border);
            }
        });

        btnSignupSubmit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtSignupName3.getText().toString().isEmpty())
                    edtSignupName3.requestFocus();
                else if(edtSignupID3.getText().toString().isEmpty())
                    edtSignupID3.requestFocus();
                else if(edtSignupPW3.getText().toString().isEmpty())
                    edtSignupPW3.requestFocus();
                else if(edtSignupPW_check3.getText().toString().isEmpty())
                    edtSignupPW_check3.requestFocus();
                else if(edtSignupNum3.getText().toString().isEmpty())
                    edtSignupNum3.requestFocus();
                else if(flag==false){
                    Toast.makeText(getApplicationContext(),"아이디 중복확인 해주세요",Toast.LENGTH_SHORT).show();
                }
                else{
                    UserVO userInfo = new UserVO();

                    String name = edtSignupName3.getText().toString();
                    String id = edtSignupID3.getText().toString();
                    String pw = edtSignupPW3.getText().toString();
                    String checkPw = edtSignupPW_check3.getText().toString();
                    String phone = edtSignupNum3.getText().toString();


                    if(name.equals("") || id.equals("") || pw.equals("") || checkPw.equals("") || phone.equals("")){
                        Toast.makeText(getApplicationContext(),"공백없이 입력하세요.",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    userInfo.setName(name);
                    userInfo.setId(id);
                    userInfo.setPasswd(pw);
                    userInfo.setPhone(phone);

                    if (pw.equals(checkPw)){
                        StringBuffer sb = new StringBuffer();
                        sb.append("INSERT INTO user (");
                        sb.append(" id, name, passwd, phone )");
                        sb.append(" VALUES (?, ?, ?, ?)");

                        db.execSQL(sb.toString(), new Object[]{ userInfo.getId(), userInfo.getName(), userInfo.getPasswd(), userInfo.getPhone()});

                        db.close();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),"비밀번호가 같지 않습니다", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
    }
}
