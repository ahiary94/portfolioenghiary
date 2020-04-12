package com.engfirstapp.abeer.mysecretportfolio;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.engfirstapp.abeer.mysecretportfolio.models.HomeModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TrashCanAdapter extends RecyclerView.Adapter<TrashCanAdapter.TrashCanViewHolder> {

    private List<HomeModel> list;
    private TrashCanActivity trashCanActivity;

    public TrashCanAdapter(List<HomeModel> list, TrashCanActivity trashCanActivity) {
        this.list = list;
        this.trashCanActivity = trashCanActivity;
        showLog("", "list", "" + list.size());


    }

    @NonNull
    @Override
    public TrashCanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trash_can_raw, parent, false);
        return new TrashCanViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull TrashCanViewHolder holder, final int position) {
        showLog("onBindViewHolder", "list", "" + list.size());

        holder.title.setText(list.get(position).getTitle());
        holder.note.setText(list.get(position).getNote());
        holder.note.setBackgroundResource(list.get(position).getColor());
        holder.date.setText(list.get(position).getDate());

        holder.note.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trashCanActivity.deleteTheNote(list.get(position).getId());

            }
        });

        holder.undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trashCanActivity.undoTheNote(list.get(position).getId());

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    void showLog(String method, String key, String value) {
        Log.e("TrashCanAdapter", method + "/" + key + "/" + value);
    }

    public class TrashCanViewHolder extends RecyclerView.ViewHolder {

        TextView note, title, date, delete, undo;
        public TrashCanViewHolder(View itemView) {
            super(itemView);
            note = itemView.findViewById(R.id.trashCanRaw_note);
            title = itemView.findViewById(R.id.trashCanRaw_title);
            date = itemView.findViewById(R.id.trashCanRaw_date);
            delete = itemView.findViewById(R.id.trashCanRaw_delete);
            undo = itemView.findViewById(R.id.trashCanRaw_undo);

        }
    }
}
