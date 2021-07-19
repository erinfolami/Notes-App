package com.example.notes.recyclerview;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.activities.MainActivity;


public class MyViewHolder extends RecyclerView.ViewHolder {

    /**
     * Provide a reference to the type of views that you are using
     * (Custom ViewHolder).
     */

    protected TextView Title;
    protected TextView Notes;

    public MyViewHolder(View view, RecyclerViewOnClickListener recyclerViewOnClickListener) {
        super(view);

        this.Title = (TextView) view.findViewById(R.id.title_textView);
        this.Notes = (TextView) view.findViewById(R.id.note_textView);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Passes the Item clicked position to the RecyclerViewOnClickListener Interface
                if(recyclerViewOnClickListener != null) {
                    int position = getAbsoluteAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        recyclerViewOnClickListener.onItemClick(getAbsoluteAdapterPosition());
                    }
                }
            }
        });
    }
}
