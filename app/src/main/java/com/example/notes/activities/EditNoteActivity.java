package com.example.notes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.notes.R;
import com.example.notes.models.NoteModel;
import com.example.notes.sqlite.DatabaseHandler;

public class EditNoteActivity extends AppCompatActivity {

    private int selectedId;
    private String selectedTitle;
    private String selectedNote;

    private ImageView backButton;
    private EditText title;
    private EditText note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        backButton = findViewById(R.id.editnote_back_button);
        title = findViewById(R.id.editTitleEditText);
        note = findViewById(R.id.editNote_EditText);

        //gets the intent extra (data) from the Mainactivity
        Intent receivedIntent = getIntent();

        //gets the Item data that was passed as an extra in the Mainactivity
        selectedId = receivedIntent.getIntExtra("id", -1); //Note -1 is just a default value incase the id value doesnt exist
        selectedTitle = receivedIntent.getStringExtra("title");
        selectedNote = receivedIntent.getStringExtra("note");

        //sets the text of the current selected item to the EditText
        setSelectedItemToEditText();

        //

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateBack();
            }
        });

    }

    public void setSelectedItemToEditText() {
        title.setText(selectedTitle);
        note.setText(selectedNote);
    }


    public void navigateBack() {
        updateNote();
        MainActivity.showNoteOnRecyclerView(EditNoteActivity.this);
        finish();
    }

    public void updateNote() {
        try {
            //puts notes,title data from the UI elements to the model class and updates it in the database
            if (title.getText().length() > 0 || note.getText().length() > 0) {
                NoteModel noteModel = new NoteModel(title.getText().toString(), note.getText().toString());
                noteModel.setId(selectedId);
//               puts the updated data to the database
                DatabaseHandler databaseHandler = new DatabaseHandler(EditNoteActivity.this);
                databaseHandler.updateData(noteModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        updateNote();
        MainActivity.showNoteOnRecyclerView(EditNoteActivity.this);
        finish();
    }

}