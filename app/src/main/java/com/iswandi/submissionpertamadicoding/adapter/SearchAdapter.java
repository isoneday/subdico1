package com.iswandi.submissionpertamadicoding.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iswandi.submissionpertamadicoding.MainActivity;
import com.iswandi.submissionpertamadicoding.R;
import com.iswandi.submissionpertamadicoding.helper.MyConstant;
import com.iswandi.submissionpertamadicoding.model.DataSearchMovie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by iswandisaputra on 3/1/18.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder>{
    Context c;
    List<DataSearchMovie> searchMovies;
    ListItemClick mOnClickListener;

    public SearchAdapter(MainActivity mainActivity, List<DataSearchMovie> dataSearchMovies) {
    c=mainActivity;
    searchMovies=dataSearchMovies;
    }

    public interface ListItemClick{
        void onListClick(int clickedItemIndex, DataSearchMovie searchMovie);
    }
    public void setOnSearchClick(ListItemClick onClick){
        mOnClickListener=onClick;
    }

    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.listmovie, parent, false);
        return new SearchAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.MyViewHolder holder, final int position) {
        final DataSearchMovie dataSearchMovie = searchMovies.get(position);
        holder.txttitlemovie.setText(dataSearchMovie.getOriginalTitle());
        Picasso.with(c).load(MyConstant.IMAGE_URL + dataSearchMovie.getPosterPath()).placeholder(R.drawable.noimage).fit().centerCrop().into(holder.imgmovie);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickListener.onListClick(position, dataSearchMovie);

            }
        });
    }

    @Override
    public int getItemCount() {
        return searchMovies.size();
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
