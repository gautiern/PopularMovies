package com.example.garbu.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.garbu.popularmovies.model.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by garbu on 6/13/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private List<Review> mReviews = new ArrayList<>();

    public class ViewHolder extends RecyclerView.ViewHolder {
          public TextView author;
          public TextView content;


        public ViewHolder(View itemView) {
            super(itemView);
                author = itemView.findViewById(R.id.authorTV);
                content = itemView.findViewById(R.id.contentTV);
        }
    }
    public  ReviewAdapter(List <Review> reviews){
        mReviews = reviews;

    }
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.review_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ViewHolder holder, int position) {

        Review review = mReviews.get(position);
        holder.author.setText(review.getAuthor());
        holder.content.setText(review.getContent());

    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }
}
