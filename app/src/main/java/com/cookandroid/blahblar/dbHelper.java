package com.cookandroid.blahblar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;
import static java.lang.annotation.RetentionPolicy.CLASS;

public class dbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "blahData.db";
    private static final int DATABASE_VERSION =1;


    /*
     *먼저 SQLiteOpenHelper클래스를 상속받은 dbHelper클래스가 정의 되어 있다. 데이터베이스 파일 이름은 "mycontacts.db"가되고,
     *데이터베이스 버전은 1로 되어있다. 만약 데이터베이스가 요청되었는데 데이터베이스가 없으면 onCreate()를 호출하여 데이터베이스
     *파일을 생성해준다.
     */

    public dbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("CREATE TABLE user (id TEXT PRIMARY KEY, name TEXT, passwd TEXT, phone TEXT);");
        db.execSQL("CREATE TABLE learninfo (\n" +
                "  dno     INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "  id     TEXT PRIMARY KEY, \n" +
                "  level   INTEGER, \n" +
                "  time TEXT,\n" +
                "  score INTEGER,\n" +
                "  dscore INTEGER, \n" +
                "  FOREIGN KEY(id) REFERENCES user(id)\n" +
                ");");
        db.execSQL("CREATE TABLE voca (\n" +
                "  id     TEXT PRIMARY KEY, \n" +
                "  word   TEXT, \n" +
                "  mean TEXT,\n" +
                "  FOREIGN KEY(id) REFERENCES user(id)\n" +
                ");");
        db.execSQL("CREATE TABLE videoinfo (\n" +
                "  title     TEXT PRIMARY KEY, \n" +
                "  tag   TEXT, \n" +
                "  level INTEGER,\n" +
                "  runtime TEXT\n" +
                ");");
        db.execSQL("DROP TABLE IF EXISTS review;");
        db.execSQL("CREATE TABLE review (\n" +
                "  id     TEXT PRIMARY KEY, \n" +
                "  title   TEXT, \n" +
                //"  todaytime   TEXT, \n" +  // 현재날짜시간
                "  wsent   TEXT, \n" +
                "  FOREIGN KEY(id) REFERENCES user(id)\n" +
                ");");

        db.execSQL("CREATE TABLE videolearn (\n" +
                "  id     TEXT PRIMARY KEY, \n" +
                "  vtime   TEXT, \n" +
                "  title TEXT,\n" +
                "  FOREIGN KEY(id) REFERENCES user(id),\n" +
                "  FOREIGN KEY(title) REFERENCES videoinfo(title)\n" +
                ");");
        db.execSQL("CREATE TABLE commu (\n" +
                "  id     TEXT, \n" +
                "  ctime   TEXT, \n" +
                "  board TEXT,\n" +
                "  contents TEXT,\n" +
                "  PRIMARY KEY(id, ctime),\n" +
                "  FOREIGN KEY(id) REFERENCES user(id)\n" +
                ");");
        db.execSQL("CREATE TABLE comments (\n" +
                "  id     TEXT, \n" +
                "  ctime   TEXT, \n" +
                "  cctime   TEXT, \n" +
                "  comconts   TEXT, \n" +
                "  board TEXT,\n" +
                "  contents TEXT,\n" +
                "  PRIMARY KEY(id, cctime),\n" +
                "  FOREIGN KEY(id) REFERENCES user(id), \n" +
                "  FOREIGN KEY(ctime) REFERENCES commu(ctime), \n" +
                "  FOREIGN KEY(board) REFERENCES commu(board), \n" +"" +
                "  FOREIGN KEY(contents) REFERENCES commu(contents) \n" +
                ");");
        db.execSQL("CREATE TABLE recomm (\n" +
                "  id     TEXT, \n" +
                "  contents   TEXT, \n" +
                "  rec  BOOLEAN, \n" +
                "  PRIMARY KEY(id, contents),\n" +
                "  FOREIGN KEY(id) REFERENCES user(id), \n" +
                "  FOREIGN KEY(contents) REFERENCES commu(contents) \n" +
                ");");
        db.execSQL("CREATE TABLE recommexpre (\n" +
                "  title     TEXT, \n" +
                "  stpoint   TEXT, \n" +
                "  expr     TEXT, \n" +
                "  PRIMARY KEY(title, stpoint),\n" +
                "  FOREIGN KEY(title) REFERENCES videoinfo(title)\n" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1 :
                try {
                    db.beginTransaction();
                    db.execSQL("CREATE TABLE user (id TEXT PRIMARY KEY, name TEXT, passwd TEXT, phone TEXT);");
                    db.execSQL("CREATE TABLE learninfo (\n" +
                            "  dno     INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                            "  id     TEXT PRIMARY KEY, \n" +
                            "  level   INTEGER, \n" +
                            "  time TEXT,\n" +
                            "  score INTEGER,\n" +
                            "  dscore INTEGER, \n" +
                            "  FOREIGN KEY(id) REFERENCES user(id)\n" +
                            ");");
                    db.execSQL("CREATE TABLE voca (\n" +
                            "  id     TEXT PRIMARY KEY, \n" +
                            "  word   TEXT, \n" +
                            "  mean TEXT,\n" +
                            "  FOREIGN KEY(id) REFERENCES user(id)\n" +
                            ");");
                    db.execSQL("CREATE TABLE videoinfo (\n" +
                            "  title     TEXT PRIMARY KEY, \n" +
                            "  tag   TEXT, \n" +
                            "  level INTEGER,\n" +
                            "  runtime TEXT\n" +
                            ");");
                    db.execSQL("DROP TABLE IF EXISTS review;");
                    db.execSQL("CREATE TABLE review (\n" +
                            "  id     TEXT, \n" +
                            "  title   TEXT, \n" +
                            //"  todaytime   TEXT, \n" +  // 현재날짜시간
                            "  wsent   TEXT, \n" +
                            "  FOREIGN KEY(id) REFERENCES user(id)\n" +
                            ");");

                    db.execSQL("CREATE TABLE videolearn (\n" +
                            "  id     TEXT PRIMARY KEY, \n" +
                            "  vtime   TEXT, \n" +
                            "  title TEXT,\n" +
                            "  FOREIGN KEY(id) REFERENCES user(id),\n" +
                            "  FOREIGN KEY(title) REFERENCES videoinfo(title)\n" +
                            ");");
                    db.execSQL("CREATE TABLE commu (\n" +
                            "  id     TEXT, \n" +
                            "  ctime   TEXT, \n" +
                            "  board TEXT,\n" +
                            "  contents TEXT,\n" +
                            "  PRIMARY KEY(id, ctime),\n" +
                            "  FOREIGN KEY(id) REFERENCES user(id)\n" +
                            ");");
                    db.execSQL("CREATE TABLE comments (\n" +
                            "  id     TEXT, \n" +
                            "  ctime   TEXT, \n" +
                            "  cctime   TEXT, \n" +
                            "  comconts   TEXT, \n" +
                            "  board TEXT,\n" +
                            "  contents TEXT,\n" +
                            "  PRIMARY KEY(id, cctime),\n" +
                            "  FOREIGN KEY(id) REFERENCES user(id), \n" +
                            "  FOREIGN KEY(ctime) REFERENCES commu(ctime), \n" +
                            "  FOREIGN KEY(board) REFERENCES commu(board), \n" +"" +
                            "  FOREIGN KEY(contents) REFERENCES commu(contents) \n" +
                            ");");
                    db.execSQL("CREATE TABLE recomm (\n" +
                            "  id     TEXT, \n" +
                            "  contents   TEXT, \n" +
                            "  rec  BOOLEAN, \n" +
                            "  PRIMARY KEY(id, contents),\n" +
                            "  FOREIGN KEY(id) REFERENCES user(id), \n" +
                            "  FOREIGN KEY(contents) REFERENCES commu(contents) \n" +
                            ");");
                    db.execSQL("CREATE TABLE recommexpre (\n" +
                            "  title     TEXT, \n" +
                            "  stpoint   TEXT, \n" +
                            "  expr     TEXT, \n" +
                            "  PRIMARY KEY(title, stpoint),\n" +
                            "  FOREIGN KEY(title) REFERENCES videoinfo(title)\n" +
                            ");");

                    db.execSQL("ALTER TABLE review ADD COLUMN dno  INTEGER PRIMARY KEY AUTOINCREMENT;");
                    db.execSQL("ALTER TABLE review ADD COLUMN todaytime TEXT");
                    //"  todaytime   TEXT, \n" +  // 현재날짜시간
                    //ALTER TABLE PLAYER  ADD (ADDRESS VARCHAR2(80));

                    db.setTransactionSuccessful();
                } catch (IllegalStateException e) {
                    //HLog.e(TAG, CLASS, e);
                } finally {
                    db.endTransaction();
                };
                break;
        }

        db.execSQL("DROP TABLE IF EXISTS review;");
        //db.execSQL("drop table sample;");
        Log.d("DEBUG", "Table Upgrade Drop ");
        onCreate(db);
    }
}
