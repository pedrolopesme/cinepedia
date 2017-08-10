package com.pedrolopesme.android.cinepedia.dao;

import com.pedrolopesme.android.cinepedia.domain.Review;

import java.util.List;

public interface ReviewDao {

    List<Review> get(long movieId);

}
