package com.example.notes.activities;

import androidx.appcompat.app.AppCompatActivity;

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


    private static final String TAG = "AddNoteActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        backButton = findViewById(R.id.back_button);
        title = findViewById(R.id.titleEditText);
        note = findViewById(R.id.note_EditText);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateBack();
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

    @Override
    public void onBackPressed() {
        saveNote();
        MainActivity.showNoteOnRecyclerView(AddNoteActivity.this);
        finish();
    }
}