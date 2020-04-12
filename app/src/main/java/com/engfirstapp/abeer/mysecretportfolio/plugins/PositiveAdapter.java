package com.engfirstapp.abeer.mysecretportfolio.plugins;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.engfirstapp.abeer.mysecretportfolio.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class PositiveAdapter extends RecyclerView.Adapter<PositiveAdapter.ViewHolderLocal> {

    private Context mContext;
    private List<Bitmap> bitmapList;

    public PositiveAdapter(Context mContext, List<Bitmap> bitmapList) {//}Context context, ArrayList<String> names, ArrayList<String> imageUrls) {
        this.bitmapList = bitmapList;
        this.mContext = mContext;
        setHasStableIds(true);

    }

    @Override
    public PositiveAdapter.ViewHolderLocal onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recycler_row_possitive_quotes, parent, false);
        return new PositiveAdapter.ViewHolderLocal(view);
    }

    @Override
    public void onBindViewHolder(PositiveAdapter.ViewHolderLocal holder, final int position) {

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.brown_wallpaper);

        Glide.with(mContext)
                .load(bitmapList.get(position))
                .apply(requestOptions)
                .into(holder.image);

//        holder.name.setText(mNames.get(position));

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d(TAG, "onClick: clicked on: " + mNames.get(position));
//                Toast.makeText(mContext, mNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return bitmapList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolderLocal extends RecyclerView.ViewHolder {

        ImageView image;
//        TextView name;


        public ViewHolderLocal(android.view.View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.textView_possitive_quotes);
//            this.name = itemView.findViewById(R.id.name_widget);
        }
    }
}
