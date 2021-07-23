package com.example.notes.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.notes.R;
import com.example.notes.activities.EditNoteActivity;
import com.example.notes.models.NoteModel;
import com.example.notes.sqlite.DatabaseHandler;

public class NoteHandler {


    public static void saveNote(EditText title, EditText note, Context context) {
        try {
            //puts notes,title data from the UI elements to the model class
            if (title.getText().length() > 0 || note.getText().length() > 0) {
                NoteModel noteModel = new NoteModel(title.getText().toString(), note.getText().toString());

                //saves the noteModel data to the database
                DatabaseHandler databaseHandler = new DatabaseHandler(context);
                databaseHandler.insertData(noteModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void updateNote(EditText title, EditText note, int selectedId, Context context) {
        try {
            //puts notes,title data from the UI elements to the model class and updates it in the database
            if (title.getText().length() > 0 || note.getText().length() > 0) {
                NoteModel noteModel = new NoteModel(title.getText().toString(), note.getText().toString());
                noteModel.setId(selectedId);
//               puts the updated data to the database
                DatabaseHandler databaseHandler = new DatabaseHandler(context);
                databaseHandler.updateData(noteModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void clearNoteText(EditText note, Context context) {
        if (note.getText().length() > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
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


}
