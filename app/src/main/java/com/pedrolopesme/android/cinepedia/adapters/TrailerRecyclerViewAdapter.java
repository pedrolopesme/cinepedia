package com.pedrolopesme.android.cinepedia.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pedrolopesme.android.cinepedia.R;
import com.pedrolopesme.android.cinepedia.clickListeners.TrailerItemClickListener;
import com.pedrolopesme.android.cinepedia.domain.Trailer;

import java.util.List;

/**
 * Recycler View Adapter responsible to load new MoviesDao to the
 * main grid
 */
public class TrailerRecyclerViewAdapter extends RecyclerView.Adapter<TrailerRecyclerViewAdapter.TrailerViewHolder> {

    // Log tag description
    private final static String LOG_TAG = TrailerRecyclerViewAdapter.class.getSimpleName();

    // List of items
    private List<Trailer> trailers = null;

    // Click Listener
    private final TrailerItemClickListener mOnClickListener;

    // Activity Context
    private final Context context;

    /**
     * TrailerRecyclerViewAdapter
     *
     * @param context  Activity Context
     * @param listener TrailerItemClickListener
     */
    public TrailerRecyclerViewAdapter(final Context context, final TrailerItemClickListener listener) {
        this.context = context;
        mOnClickListener = listener;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        Log.d(LOG_TAG, "Creating view holder ...");
        Context context = parent.getContext();
        int trailerItemLayoutId = R.layout.trailer_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(trailerItemLayoutId, parent, false);
        TrailerViewHolder viewHolder = new TrailerViewHolder(view);
        Log.d(LOG_TAG, "onCreateViewHolder created!");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        Log.i(LOG_TAG, trailers.get(position).toString());
        holder.render(position);
    }

    @Override
    public int getItemCount() {
        return trailers == null ? 0 : trailers.size();
    }

    public void setTrailers(final List<Trailer> trailers) {
        Log.d(LOG_TAG, "Refreshing trailers list");
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    /**
     * Caches children views
     */
    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Log tag description
        final String LOG_TAG = this.getClass().getSimpleName();

//        final TextView mTrailerName;

        TrailerViewHolder(final View itemView) {
            super(itemView);
//            mTrailerName = (TextView) itemView.findViewById(R.id.tv_trailer_name);
            itemView.setOnClickListener(this);
            Log.d(LOG_TAG, "View Holder Created");
        }

        @Override
        public void onClick(final View v) {
            if (trailers != null) {
                Trailer trailer = trailers.get(getAdapterPosition());
                if (trailer != null) {
                    mOnClickListener.onTrailerItemClick(trailer);
                    Log.d(LOG_TAG, "View Holder Clicked on trailer " + trailer);
                }
            }
        }

        void render(int position) {
//            mTrailerName.setText("Trailer #" + position);
        }

    }

}
