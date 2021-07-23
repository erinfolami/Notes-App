package com.example.notes.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.notes.R;
import com.example.notes.models.NoteModel;
import com.example.notes.sqlite.DatabaseHandler;
import com.example.notes.utils.NoteHandler;


public class AddNoteActivity extends AppCompatActivity {
    private ImageView backButton;
    private EditText title;
    private EditText note;
    private ImageView clearNote;


    private static final String TAG = "AddNoteActivity";

    private boolean added = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        backButton = findViewById(R.id.addnote_back_button);
        title = findViewById(R.id.addTitle_EditText);
        note = findViewById(R.id.addNote_EditText);
        clearNote = findViewById(R.id.clear_all_addNote);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateBack();
            }
        });

        clearNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteHandler.clearNoteText(note, AddNoteActivity.this);
            }
        });


    }


    public void navigateBack() {
        finish();
    }


    @Override
    protected void onStop() {
        // call the superclass method first
        super.onStop();

        //The 'added' flag ensures the note is saved only once
        if (added == false) {
            added = true;
            // save the note's current draft, because the activity is stopping
            // and we want to be sure the current note progress isn't lost.
            NoteHandler.saveNote(title, note, this);
            MainActivity.showNoteOnRecyclerView(AddNoteActivity.this);
        }
        // If the flag becomes true then changes  in the note will only be updated
        else {
            //get the last item in the dataArrayList( to get the id of the item that was saved lastly)
            NoteModel data = MainActivity.dataArrayList.get(MainActivity.dataArrayList.size() - 1);
            int selectedId = data.getId();

//            updates the changes in the current note
            NoteHandler.updateNote(title, note, selectedId, this);
            MainActivity.showNoteOnRecyclerView(AddNoteActivity.this);
        }
    }
}