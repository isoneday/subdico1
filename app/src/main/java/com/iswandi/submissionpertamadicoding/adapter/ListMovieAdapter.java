package com.iswandi.submissionpertamadicoding.adapter;

import android.content.Context;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iswandi.submissionpertamadicoding.MainActivity;
import com.iswandi.submissionpertamadicoding.R;
import com.iswandi.submissionpertamadicoding.helper.MyConstant;
import com.iswandi.submissionpertamadicoding.model.DataMovies;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by iswandisaputra on 3/1/18.
 */

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.MyViewHolder> {
    Context c;
    List<DataMovies> movies;
    ListMovieClickListener OnClickListener;
    private static final String TAG = ListMovieAdapter.class.getSimpleName();

    public ListMovieAdapter(MainActivity mainActivity, List<DataMovies> dataMovies) {
        movies = dataMovies;
        c = mainActivity;
    }

    @Override
    public ListMovieAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.listmovie, parent, false);
        return new MyViewHolder(v);
    }

    public interface ListMovieClickListener {
        void onListItemClick(int clickedItemIndex, DataMovies movie);
    }

    public void setOnClick(ListMovieClickListener onClick){
        OnClickListener=onClick;
    }
    @Override
    public void onBindViewHolder(ListMovieAdapter.MyViewHolder holder, final int position) {
        holder.txttitlemovie.setText(movies.get(position).getOriginalTitle());
        Picasso.with(c).load(MyConstant.IMAGE_URL + movies.get(position).getPosterPath()).placeholder(R.drawable.noimage).fit().centerCrop().error(R.drawable.noimage).into(holder.imgmovie);

        final DataMovies dataMovies = movies.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickListener.onListItemClick(position, dataMovies);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgmovie;
        TextView txttitlemovie;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgmovie = (ImageView) itemView.findViewById(R.id.imgmovie);
            txttitlemovie = (TextView) itemView.findViewById(R.id.txttitle);

        }
    }
}
