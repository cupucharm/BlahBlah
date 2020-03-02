package com.cookandroid.blahblar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.blahblar.MApp;
import com.cookandroid.blahblar.MenuActivity;
import com.cookandroid.blahblar.R;
import com.cookandroid.blahblar.SignupActivity;
import com.cookandroid.blahblar.UserVO;
import com.cookandroid.blahblar.dbHelper;

import java.io.File;

public class LoginActivity extends AppCompatActivity {
    EditText edtLoginID2, edtLoginPW2;
    Button btnLoginSubmit2, btn_kakao_login;
    TextView txtLoginSignup2, txtPassChange2;
    CheckBox ckSaveId2;
    ImageView img_naver2, img_twt2, img_fb2;
    MApp appDel;

    dbHelper helper;
    SQLiteDatabase db;

    String saveId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtLoginID2 = (EditText) findViewById(R.id.edtLoginID_00);
        edtLoginPW2 = (EditText) findViewById(R.id.edtLoginPW_00);
        btnLoginSubmit2 = (Button) findViewById(R.id.btnLoginSubmit_00);
        txtLoginSignup2 = (TextView) findViewById(R.id.txtLoginSignup_00);
        txtPassChange2 = (TextView) findViewById(R.id.txtPassChange_00);
        ckSaveId2 = (CheckBox) findViewById(R.id.ckSaveId_00);

        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        saveId = auto.getString("inputId", null);


        if (saveId != null) {
            edtLoginID2.setText(saveId);
        }

        edtLoginID2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (edtLoginID2.hasFocus())
                    edtLoginID2.setBackgroundResource(R.drawable.primary_border);
                else
                    edtLoginID2.setBackgroundResource(R.drawable.orange_border);
            }
        });
        edtLoginPW2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (edtLoginPW2.hasFocus())
                    edtLoginPW2.setBackgroundResource(R.drawable.primary_border);
                else
                    edtLoginPW2.setBackgroundResource(R.drawable.orange_border);
            }
        });

        btnLoginSubmit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtLoginID2.getText().toString().isEmpty())
                    edtLoginID2.requestFocus();
                else if (edtLoginPW2.getText().toString().isEmpty())
                    edtLoginPW2.requestFocus();
                else {
                    try {
                        helper = new dbHelper(LoginActivity.this);
                        db = helper.getReadableDatabase();
                    } catch (SQLiteException e) {
                    }

                    String id = edtLoginID2.getText().toString();
                    String pw = edtLoginPW2.getText().toString();

                    UserVO userInfo = new UserVO();
                    userInfo.setId(id);
                    userInfo.setPasswd(pw);

                    StringBuffer sb = new StringBuffer();
                    sb.append("SELECT * FROM user WHERE id = #id# AND passwd = #passwd#");

                    String query = sb.toString();
                    query = query.replace("#id#", "'" + userInfo.getId() + "'");
                    query = query.replace("#passwd#", "'" + userInfo.getPasswd() + "'");

                    //
                    Cursor cursor;
                    cursor = db.rawQuery(query, null);

                    if (cursor.moveToNext()) {

                        String strId = cursor.getString(0);
                        String strName = cursor.getString(1);
                        String strPhone = cursor.getString(3);

                        // 여기 기능이 뭔기 알아내서 해결해야함
                        /*appDel = (MApp) getApplication();
                        appDel.setUserId(strId);
                        appDel.setUserName(strName);
                        appDel.setUserPhone(strPhone);*/

                        if (strId != null) {
                            if (ckSaveId2.isChecked() == true) {
                                SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor autoLogin = auto.edit();
                                autoLogin.putString("inputId", strId);
                                autoLogin.commit();
                                startMain(strId, strName, strPhone);
                            } else {
                                SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor autoLogin = auto.edit();
                                autoLogin.putString("inputId", null);
                                autoLogin.commit();
                                startMain(strId, strName, strPhone);
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "입력정보를 확인하세요", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    cursor.close();
                    db.close();
                }
            }
        });

        txtLoginSignup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });


        String str = Environment.getExternalStorageState();
        if ( str.equals(Environment.MEDIA_MOUNTED)) {

            String dirPath = "/sdcard/PK";
            File file = new File(dirPath);
            if( !file.exists() )  // 원하는 경로에 폴더가 있는지 확인
                file.mkdirs();

            dirPath = "/sdcard/SS";
            file = new File(dirPath);
            if( !file.exists() )  // 원하는 경로에 폴더가 있는지 확인
                file.mkdirs();

            dirPath = "/sdcard/IT";
            file = new File(dirPath);
            if( !file.exists() )  // 원하는 경로에 폴더가 있는지 확인
                file.mkdirs();

            dirPath = "/sdcard/GP";
            file = new File(dirPath);
            if( !file.exists() )  // 원하는 경로에 폴더가 있는지 확인
                file.mkdirs();
        }
        else
            Toast.makeText(LoginActivity.this, "SD Card 인식 실패", Toast.LENGTH_SHORT).show();

    }

    private void startMain(String id, String name, String phone) {

        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        intent.putExtra("phone", phone);
        startActivity(intent);
    }
}
