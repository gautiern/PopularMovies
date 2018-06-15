package com.example.garbu.popularmovies.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.garbu.popularmovies.R;
import com.example.garbu.popularmovies.model.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by garbu on 6/13/2018.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {
    private List<Video> mVideos = new ArrayList<>();

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView trailerTitle;


        public ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.playIconIV);
            trailerTitle = itemView.findViewById(R.id.titleTV);
        }
    }
    public  TrailerAdapter(List<Video> videos) {
        //mVideos = videos;
        //loop to only add Trailers
 //       for (int i = 0; i < videos.size(); i++) {
  //          Video video = videos.get(i);
  //          if (!video.getType().equals("Trailer")) {
  //              videos.remove(i);
  //          }

  //      }
        for(Video video : videos){
            if (video.getType().equals("Trailer")) {
            mVideos.add(video);
            }
        }
    }
    @Override
    public TrailerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.trailer_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.ViewHolder holder, int position) {

        Video video = mVideos.get(position);
        //holder.icon.setImageResource("");
        holder.trailerTitle.setText(video.getName());

    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }
}
