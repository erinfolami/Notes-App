package com.example.notes.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.notes.R;
import com.example.notes.models.Model;
import com.example.notes.recyclerview.RecyclerViewAdapter;
import com.example.notes.recyclerview.RecyclerViewItemTouchHelper;
import com.example.notes.sqlite.DatabaseHandler;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected static RecyclerView recyclerView;
    protected static RecyclerViewAdapter recyclerViewAdapter;
    protected static List<Model> dataArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.notes_recycler_view);
        ImageButton addNoteButton = findViewById(R.id.addNotebutton);

        showNoteOnRecyclerView(this);


        //passing the RecyclerViewItemTouchHelper callback to the ItemTouchHelper constrctor,
        // to receives events when user performs these actions in the callback
        ItemTouchHelper recyclerViewItemTouchHelper =
                new ItemTouchHelper(new RecyclerViewItemTouchHelper(MainActivity.this, dataArrayList, recyclerViewAdapter, recyclerView));
        // attaches the touch helper to recycler view
        recyclerViewItemTouchHelper.attachToRecyclerView(recyclerView);


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

        //data that will be displayed in the recyclerViewAdapter from sqlite database
        DatabaseHandler databaseHandler = new DatabaseHandler(context);
        dataArrayList = databaseHandler.getAllData();
        //specifying the recyclerViewAdapter
        recyclerViewAdapter = new RecyclerViewAdapter(dataArrayList);

        recyclerView.setAdapter(recyclerViewAdapter);
    }


    public void switchtoAddnoteActivity() {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }


}