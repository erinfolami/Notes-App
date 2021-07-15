package com.example.notes.recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.activities.MainActivity;
import com.example.notes.models.Model;
import com.example.notes.sqlite.DatabaseHandler;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

//This utility class  adds swipe to dismiss(delete,archive etc) and drag & drop support to RecyclerView.
//This is a Callback class, which configures what type of interactions are enabled,

public class RecyclerViewItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    protected List<Model> dataArrayList;
    protected RecyclerViewAdapter recyclerViewAdapter;
    protected RecyclerView recyclerView;
    protected Context context;


    public RecyclerViewItemTouchHelper(Context context, RecyclerView recyclerView) {
        super(0, ItemTouchHelper.LEFT);
        this.recyclerView = recyclerView;
        this.context = context;

    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // removes notes from RecyclerView

        Model deletedNote;

        //position of the ListItem that was swiped
        int position = viewHolder.getAbsoluteAdapterPosition();

//        storing the note that will be deleted in a "deletedNote" variable before deletion
//        in case a user Un-do's the delete
        dataArrayList = MainActivity.dataArrayList;
        deletedNote = dataArrayList.get(position);

        //Sets the swiped ListItem position to the ID variable in Model class
        Model model = new Model();
        model.setId(dataArrayList.get(position).getId());

        //removes the item from the List
        dataArrayList.remove(position);
        //notifies the recyclerViewAdapter an item has been removed
        recyclerViewAdapter = MainActivity.recyclerViewAdapter;
        recyclerViewAdapter.notifyItemRemoved(position);

        //Creating a Snackbar to give an option to undo delete
        int duration = Snackbar.LENGTH_SHORT;
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
                    MainActivity.databaseHandler.deleteNote(model);

                }
            }
        });
        snackbar.show();


    }

}



