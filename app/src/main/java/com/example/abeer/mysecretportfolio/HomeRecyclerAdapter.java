package com.example.abeer.mysecretportfolio;

import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.abeer.mysecretportfolio.models.HomeModel;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder> {

    private List<HomeModel> list;
    private HomeModel modelForEditNote = new HomeModel();
    private AddNoteDatabase addNoteDatabase;
    private MainActivity mainActivity;
    private MediaPlayer mediaPlayer;
    private MediaRecorder mediaRecorder;
    private int length = 0;
    private Runnable runnable;
    private Handler handler;

    public HomeRecyclerAdapter(List<HomeModel> list, MainActivity mainActivity, AddNoteDatabase addNoteDatabase) {
        this.list = list;
        this.mainActivity = mainActivity;
        this.addNoteDatabase = addNoteDatabase;
//        mediaPlayer = new MediaPlayer();

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
            holder.background.setBackgroundResource(list.get(position).getColor());
            holder.textView.setText(list.get(position).getNote());
            holder.date.setText(list.get(position).getDate());
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
            handler = new Handler();
            holder.noteLayout.setVisibility(View.GONE);
            holder.voiceLayout.setVisibility(View.VISIBLE);
            holder.voiceTitle.setText(list.get(position).getTitle());
            holder.voiceDate.setText(list.get(position).getDate());

            holder.play.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
//                    holder.play.setBackgroundTintList(ColorStateList.valueOf(mainActivity.getResources().getColor(R.color.red_dark)));
                    if (mediaPlayer != null)
                        mediaPlayer.stop();
                    mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource(list.get(position).getNote());
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.start();
//                    }

                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            holder.seekBar.setMax(mediaPlayer.getDuration());
                            mediaPlayer.start();
                            mainActivity.changeAdapterSeekbar(holder.seekBar, mediaPlayer, runnable, handler);

                        }
                    });
//
                    holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if (fromUser) {
                                mediaPlayer.seekTo(progress);
                            }
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });

                }
            });

            holder.pause.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
//                    holder.play.setBackgroundTintList(ColorStateList.valueOf(mainActivity.getResources().getColor(R.color.white)));
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
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
        LinearLayout background;
        TextView textView;
        TextView title, date;

        LinearLayout voiceLayout;
        Button play, pause, delete;
        TextView voiceTitle, voiceDate;
        SeekBar seekBar;

        public HomeViewHolder(View itemView) {
            super(itemView);

            noteLayout = itemView.findViewById(R.id.home_container_note);
            textView = itemView.findViewById(R.id.home_add_new_note);
            title = itemView.findViewById(R.id.textView_favorite_title);
            date = itemView.findViewById(R.id.textView_favorite_date);
            background = itemView.findViewById(R.id.home_background);

            voiceLayout = itemView.findViewById(R.id.home_container_voice);
            seekBar = itemView.findViewById(R.id.home_voiceMessage_seekbar);
            play = itemView.findViewById(R.id.home_voiceMessage_play);
            pause = itemView.findViewById(R.id.home_voiceMessage_pause);
            delete = itemView.findViewById(R.id.home_voiceMessage_delete);
            voiceTitle = itemView.findViewById(R.id.home_voiceMessage_title);
            voiceDate =itemView.findViewById(R.id.home_voiceMessage_date);

        }


    }
}
