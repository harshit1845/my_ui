package com.example.makesurest.adapter;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makesurest.R;

import java.util.List;

public class TestAdapter extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "quiz_database";
    private static final String TABLE_QUESTIONS = "questions";
    private static final String KEY_ID = "id";
    private static final String KEY_QUESTION = "question";
    private static final String KEY_OPTION1 = "option1";
    private static final String KEY_OPTION2 = "option2";
    private static final String KEY_OPTION3 = "option3";
    private static final String KEY_CORRECT_OPTION = "correct_option";


    public TestAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE = " CREATE TABLE " +TABLE_QUESTIONS+"("
                +KEY_ID+"PRIMARY_KEY,"
                +KEY_QUESTION+"TEXT,"
                +KEY_OPTION1+"TEXT,"
                +KEY_OPTION2+"TEXT"+")";

        sqLiteDatabase.execSQL(CREATE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
