package com.saltside.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.saltside.R;
import com.saltside.model.SaltSideModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by E6430 on 8/18/2016.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    List<SaltSideModel> saltSideModelList;
    Context context;

    public CardAdapter(Context context,List<SaltSideModel> saltSideModelList) {
        this.saltSideModelList = saltSideModelList;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.saltsidelistview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            if (saltSideModelList.size() > 0) {
                Log.v("VMA", "OnBindView title " + (saltSideModelList.get(position).getTitle()));
                Log.v("VMA", "OnBindView description " + (saltSideModelList.get(position).getDescription()));
                Log.v("VMA", "OnBindView imageUrl " + (saltSideModelList.get(position).getImageUrl()));
                holder.title.setText(saltSideModelList.get(position).getTitle());
                holder.description.setText(saltSideModelList.get(position).getDescription());
                Picasso.with(context).load(saltSideModelList.get(position).getImageUrl()).into(holder.imageView);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return saltSideModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ImageView imageView;


        ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_view_list_view);
            title = (TextView) itemView.findViewById(R.id.title_list_view);
            description = (TextView) itemView.findViewById(R.id.description_list_view);
            description.setMovementMethod(new ScrollingMovementMethod());
            title.setMovementMethod(new ScrollingMovementMethod());
        }
    }


}
