package com.company.dmart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.os.Handler;

import com.company.dmart.R;
import com.company.dmart.TopRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.topRecycle);
        List<String> list;
        list = new ArrayList<>();
        list.add("https://m.media-amazon.com/images/I/71lvwF036oL._SL1265_.jpg");
        list.add("https://m.media-amazon.com/images/I/71YdE55GwjL._SL1500_.jpg");
        list.add("https://m.media-amazon.com/images/I/61Lz4JjcWOL._UL1302_.jpg");
        list.add("https://m.media-amazon.com/images/I/51nVtdGuB-L._SL1024_.jpg");
        list.add("https://m.media-amazon.com/images/I/61SxCMSA3GL._UL1440_.jpg");
        list.add("https://m.media-amazon.com/images/I/61KtXVslwDL._SL1500_.jpg");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        TopRecyclerViewAdapter topRecyclerViewAdapter = new TopRecyclerViewAdapter(list);
        recyclerView.setAdapter(topRecyclerViewAdapter);

        }
}
