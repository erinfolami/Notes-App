package com.example.notes.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.notes.R;
import com.example.notes.models.Model;
import com.example.notes.recyclerview.RecyclerViewAdapter;
import com.example.notes.sqlite.DatabaseHandler;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected static RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.notes_recycler_view);
        ImageButton addNoteButton = findViewById(R.id.addNotebutton);

        showNoteOnRecyclerView(this);

        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchtoAddnoteActivity();
            }
        });

    }

    public static void showNoteOnRecyclerView(Context context) {

        //using a Linear Layout Manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        //to reverse the recyclerView and show latest item on top
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);

        //data that will be displayed in the adapter from sqlite database
        DatabaseHandler databaseHandler = new DatabaseHandler(context);
        List<Model> data = databaseHandler.getAllData();
        //specifying the adapter
        RecyclerView.Adapter adapter = new RecyclerViewAdapter(data,context);

        recyclerView.setAdapter(adapter);
    }

    public void switchtoAddnoteActivity() {
        Intent intent = new Intent(this, addNoteActivity.class);
        startActivity(intent);
    }


}