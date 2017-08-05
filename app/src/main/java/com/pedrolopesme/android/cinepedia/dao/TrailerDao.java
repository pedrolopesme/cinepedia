package com.pedrolopesme.android.cinepedia.dao;

import com.pedrolopesme.android.cinepedia.domain.Trailer;

import java.util.List;

public interface TrailerDao {

    List<Trailer> get(int movieId);

}
