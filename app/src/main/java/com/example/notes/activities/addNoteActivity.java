package com.example.notes.activities;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.notes.R;
import com.example.notes.models.Model;
import com.example.notes.recyclerview.RecyclerViewAdapter;
import com.example.notes.sqlite.DatabaseHandler;

import java.util.List;
import java.util.Objects;


public class addNoteActivity extends AppCompatActivity {
    private ImageView backButton;
    private EditText title;
    private EditText note;


    private static final String TAG = "addNoteActivity";

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
        MainActivity.showNoteOnRecyclerView(addNoteActivity.this);
        finish();
    }

    public void saveNote() {
        try {
            //puts notes,title data from the UI elements to the model class
            if (title.getText().length() > 0 || note.getText().length() > 0) {
                Model model = new Model(title.getText().toString(), note.getText().toString());

                //saves the model data to the database
                DatabaseHandler databaseHandler = new DatabaseHandler(addNoteActivity.this);
                databaseHandler.insertData(model);
                Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        saveNote();
        MainActivity.showNoteOnRecyclerView(addNoteActivity.this);
        finish();
    }
}