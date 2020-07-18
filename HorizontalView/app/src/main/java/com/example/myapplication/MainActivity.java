package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private ListView lv_one;
    private ListView lv_two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_one = findViewById(R.id.lv_one);
        lv_two = findViewById(R.id.lv_two);
        String[] str1 = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.list_item1, str1);
        lv_one.setAdapter(adapter1);
        String[] str2 = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.list_item1, str2);
        lv_two.setAdapter(adapter2);
    }
}
