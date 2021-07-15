package com.example.notes.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.models.Model;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private List<Model> modelList;

    public RecyclerViewAdapter(List<Model> modelList) {
        this.modelList = modelList;
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
        holder.Notes.setText(modelList.get(position).getNote());
        holder.Title.setText(modelList.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
