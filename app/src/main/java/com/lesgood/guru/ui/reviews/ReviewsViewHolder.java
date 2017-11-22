package com.lesgood.guru.ui.reviews;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lesgood.guru.R;
import com.lesgood.guru.data.model.Reviews;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ikun on 22/11/17.
 */

public class ReviewsViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.txt_review)
    TextView txtReviews;

    @Bind(R.id.txt_rating)
    TextView txtRating;

    @Bind(R.id.rating_bar)
    RatingBar ratingBar;

    @Bind(R.id.txt_isi)
    TextView txtisi;

    private View itemView;

    public ReviewsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemView = itemView;
    }

    public void bind(Reviews item) {
        txtReviews.setText(item.getReviewer());
        float rating = item.getRating();
        txtRating.setText(String.valueOf(rating));
        float ratings = item.getRating();
        ratingBar.setRating(ratings);
        txtisi.setText(item.getTitle());
    }
}
