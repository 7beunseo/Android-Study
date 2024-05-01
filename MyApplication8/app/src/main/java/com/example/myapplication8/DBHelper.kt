package com.example.myapplication8

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context, "testdb", null, 1) { // testdb 데이터베이스 생성
    override fun onCreate(db: SQLiteDatabase?) { //db 변수를 통해서 sqlite에서 하고싶은 명령어를 이용하면 됨
        db?.execSQL("create table todo_tb (_id integer primary key autoincrement, todo not null)")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}