package com.example.notes.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.notes.R;
import com.example.notes.models.NoteModel;
import com.example.notes.recyclerview.RecyclerViewAdapter;
import com.example.notes.recyclerview.RecyclerViewItemTouchHelper;
import com.example.notes.recyclerview.RecyclerViewOnClickListener;
import com.example.notes.sqlite.DatabaseHandler;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected static RecyclerView recyclerView;
    public static RecyclerViewAdapter recyclerViewAdapter;
    public static List<NoteModel> dataArrayList;
    public static DatabaseHandler databaseHandler;
    private Context context = MainActivity.this;


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
                new ItemTouchHelper(new RecyclerViewItemTouchHelper(MainActivity.this, recyclerView));
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
        databaseHandler = new DatabaseHandler(context);
        dataArrayList = databaseHandler.getAllData();
        //specifying the recyclerViewAdapter
        recyclerViewAdapter = new RecyclerViewAdapter(dataArrayList);

        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerViewAdapter.setRecyclerViewOnClickListener(new RecyclerViewOnClickListener() {
            @Override
            public void onItemClick(int Position) {
                //gets the data of the item that was clicked
                NoteModel data = dataArrayList.get(Position);

                //Sends the data of the item clicked to EditNoteActivity
                Intent intent = new Intent(context, EditNoteActivity.class);
                intent.putExtra("id",data.getId());
                intent.putExtra("title",data.getTitle());
                intent.putExtra("note",data.getNote());

                context.startActivity(intent);
            }

            @Override
            public void onLongItemClick(int Position) {

            }
        });

    }


    public void switchtoAddnoteActivity() {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

}