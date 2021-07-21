package com.example.notes.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.notes.R;
import com.example.notes.models.NoteModel;
import com.example.notes.sqlite.DatabaseHandler;


public class AddNoteActivity extends AppCompatActivity {
    private ImageView backButton;
    private EditText title;
    private EditText note;
    private ImageView clearNote;


    private static final String TAG = "AddNoteActivity";

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
                clearNoteText();
            }
        });


    }


    public void navigateBack() {
        saveNote();
        MainActivity.showNoteOnRecyclerView(AddNoteActivity.this);
        finish();
    }

    public void saveNote() {
        try {
            //puts notes,title data from the UI elements to the model class
            if (title.getText().length() > 0 || note.getText().length() > 0) {
                NoteModel noteModel = new NoteModel(title.getText().toString(), note.getText().toString());

                //saves the noteModel data to the database
                DatabaseHandler databaseHandler = new DatabaseHandler(AddNoteActivity.this);
                databaseHandler.insertData(noteModel);
                Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void clearNoteText(){
        if (note.getText().length() > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to clear Note text");

            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    note.getText().clear();
                }
            });

            builder.setNegativeButton(R.string.cancel, null);

            builder.create()
                    .show();
        }

    }

    @Override
    public void onBackPressed() {
        saveNote();
        MainActivity.showNoteOnRecyclerView(AddNoteActivity.this);
        finish();
    }
}