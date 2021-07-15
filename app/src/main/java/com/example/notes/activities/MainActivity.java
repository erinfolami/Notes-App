package com.example.notes.activities;

import androidx.annotation.NonNull;
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
import com.example.notes.sqlite.DatabaseHandler;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected static RecyclerView recyclerView;
    protected static RecyclerViewAdapter recyclerViewAdapter;
    protected static List<Model> dataArrayList;

    protected Model deletedNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.notes_recycler_view);
        ImageButton addNoteButton = findViewById(R.id.addNotebutton);

        showNoteOnRecyclerView(this);

        deleteNoteOnSwipe();

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
        recyclerViewAdapter = new RecyclerViewAdapter(dataArrayList, context);

        recyclerView.setAdapter(recyclerViewAdapter);
    }

    public void deleteNoteOnSwipe() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //position of the ListItem that was swiped
                int position = viewHolder.getAbsoluteAdapterPosition();


                //storing the note that will be deleted in a "deletedNote" variable before deletion
                //in case a user Un-do's the delete
                deletedNote = dataArrayList.get(position);

                //Sets the swiped ListItem position to the ID variable in Model class
                Model model = new Model();
                model.setId(dataArrayList.get(position).getId());

                //removes the item from the List
                dataArrayList.remove(position);
                //notifies the recyclerViewAdapter an item has been removed
                recyclerViewAdapter.notifyItemRemoved(position);

                //Creating a Snackbar to give an option to undo delete
                int duration = Snackbar.LENGTH_LONG;
                String message = "Note deleted";
                Snackbar snackbar = Snackbar.make(recyclerView, message, duration);
                snackbar.setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //adds the item back to the dataArrayList when Undo is clicked
                        dataArrayList.add(position, deletedNote);
                        //notifies the the recyclerView an item has been inserted in the dataArrayList
                        // and inserts the data back to its position
                        recyclerViewAdapter.notifyItemInserted(position);
                    }
                });
                snackbar.addCallback(new Snackbar.Callback() {
                    @Override
                    public void onShown(Snackbar sb) {
                        super.onShown(sb);
                    }

                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        super.onDismissed(transientBottomBar, event);
                        //checks if stackbar was not dismissed via an action click (Undo)
                        if (event != BaseTransientBottomBar.BaseCallback.DISMISS_EVENT_ACTION) {

//                          //deletes the swiped note from database by using the ID
                            DatabaseHandler databaseHandler = new DatabaseHandler(MainActivity.this);
                            databaseHandler.deleteNote(model);
                        }
                    }
                });
                snackbar.show();

            }
        };


        // attaching the touch helper to recycler view
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    public void switchtoAddnoteActivity() {
        Intent intent = new Intent(this, addNoteActivity.class);
        startActivity(intent);
    }


}