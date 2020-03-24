package com.example.abeer.mysecretportfolio;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.abeer.mysecretportfolio.models.HomeModel;

import java.io.IOException;
import java.util.List;


public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder> {

    private List<HomeModel> list;
    private HomeModel modelForEditNote = new HomeModel();
    private AddNoteDatabase addNoteDatabase;
    private MainActivity mainActivity;
    private MediaPlayer mediaPlayer;
    private MediaRecorder mediaRecorder;

    public HomeRecyclerAdapter(List<HomeModel> list, MainActivity mainActivity, AddNoteDatabase addNoteDatabase) {
        this.list = list;
        this.mainActivity = mainActivity;
        this.addNoteDatabase = addNoteDatabase;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_layout, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeViewHolder holder, final int position) {
        Log.e("flag", "" + list.get(position).getNoteFlag() + (list.get(position).getNoteFlag() == 1));
        if (list.get(position).getNoteFlag() == 0) {
            holder.noteLayout.setVisibility(View.VISIBLE);
            holder.voiceLayout.setVisibility(View.GONE);
            holder.title.setText(list.get(position).getTitle());
            holder.textView.setText(list.get(position).getNote());
            holder.textView.setBackgroundResource(list.get(position).getColor());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = list.get(position).getId();
                    modelForEditNote.setId(id);
                    modelForEditNote.setTitle(list.get(position).getTitle());
                    modelForEditNote.setNote(list.get(position).getNote());
                    modelForEditNote.setColor(list.get(position).getColor());
                    Cursor cursor = addNoteDatabase.selectSpecificPluginsContent(id);
                    if (cursor.moveToFirst()) {
                        do {
                            modelForEditNote.setPluginId(cursor.getInt(0));
                            modelForEditNote.setFavorite(cursor.getInt(1));
//                        Log.e("fav adapter", "" + cursor.getInt(1));
                            modelForEditNote.setPinToTaskbar(cursor.getInt(2));
                            modelForEditNote.setSecret(cursor.getInt(3));

                        } while (cursor.moveToNext());
                    }
                    mainActivity.goToAddNotePageForEditting(modelForEditNote);
                }
            });
        } else if (list.get(position).getNoteFlag() == 1) {
            mediaPlayer = new MediaPlayer();
            holder.noteLayout.setVisibility(View.GONE);
            holder.voiceLayout.setVisibility(View.VISIBLE);
            holder.voiceTitle.setText(list.get(position).getTitle());
            holder.play.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    try {
                        holder.play.setBackgroundTintList(ColorStateList.valueOf(mainActivity.getResources().getColor(R.color.red_dark)));
                        mediaPlayer.setDataSource(list.get(position).getNote());
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.start();
                }
            });

            holder.pause.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    if (mediaPlayer != null) {
                        holder.play.setBackgroundTintList(ColorStateList.valueOf(mainActivity.getResources().getColor(R.color.white)));
                        mediaPlayer.stop();
                        mediaPlayer.release();
//                        mediaRecorder = new MediaRecorder();
//                        mediaRecorder.setAudioSource(MIC);
//                        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//                        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//                        mediaRecorder.setOutputFile(list.get(position).getNote());
                    }
                }
            });

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.deleteTheNote(list.get(position).getId());
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout noteLayout;
        TextView textView;
        TextView title;

        LinearLayout voiceLayout;
        Button play, pause, delete;
        TextView voiceTitle;

        public HomeViewHolder(View itemView) {
            super(itemView);

            noteLayout = itemView.findViewById(R.id.home_container_note);
            textView = itemView.findViewById(R.id.home_add_new_note);
            title = itemView.findViewById(R.id.textView_favorite_title);

            voiceLayout = itemView.findViewById(R.id.home_container_voice);
//            close = itemView.findViewById(R.id.home_voiceMessage_close);
            play = itemView.findViewById(R.id.home_voiceMessage_play);
            pause = itemView.findViewById(R.id.home_voiceMessage_pause);
            delete = itemView.findViewById(R.id.home_voiceMessage_delete);
            voiceTitle = itemView.findViewById(R.id.home_voiceMessage_title);

        }
    }
}
