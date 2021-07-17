package com.example.notes.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.models.NoteModel;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private List<NoteModel> noteModelList;

    public RecyclerViewAdapter(List<NoteModel> noteModelList) {
        this.noteModelList = noteModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_cardview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Notes.setText(noteModelList.get(position).getNote());
        holder.Title.setText(noteModelList.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return noteModelList.size();
    }
}
