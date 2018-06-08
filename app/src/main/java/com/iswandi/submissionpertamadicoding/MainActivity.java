package com.iswandi.submissionpertamadicoding;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.iswandi.submissionpertamadicoding.activity.DetailMovieActivity;
import com.iswandi.submissionpertamadicoding.adapter.ListMovieAdapter;
import com.iswandi.submissionpertamadicoding.adapter.SearchAdapter;
import com.iswandi.submissionpertamadicoding.helper.MyConstant;
import com.iswandi.submissionpertamadicoding.model.DataMovies;
import com.iswandi.submissionpertamadicoding.model.DataSearchMovie;
import com.iswandi.submissionpertamadicoding.model.ModelMovies;
import com.iswandi.submissionpertamadicoding.model.ModelSearchMovies;
import com.iswandi.submissionpertamadicoding.restapi.RetroApi;
import com.iswandi.submissionpertamadicoding.restapi.RetroConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ListMovieAdapter.ListMovieClickListener,SearchAdapter.ListItemClick {

    @BindView(R.id.progbar)
    ProgressBar progbar;
    @BindView(R.id.edtcari)
    EditText edtcari;
    @BindView(R.id.btnsearch)
    Button btnsearch;
    @BindView(R.id.listmovie)
    RecyclerView listmovie;
    private static final String TAG = MainActivity.class.getSimpleName();
    List<DataMovies> dataMovies;
    List<DataSearchMovie> dataSearchMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        dataMovies = new ArrayList<>();
        dataSearchMovies = new ArrayList<>();

        rotasidevice();
        progbar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getPopular();
            }
        }, 4000);

    }

    private void getPopular() {
        RetroApi api = RetroConfig.getInstaceRetrofit();
        Call<ModelMovies> modelMoviesCall =api.getPopularMovie(MyConstant.API_KEY);
        modelMoviesCall.enqueue(new Callback<ModelMovies>() {
            @Override
            public void onResponse(Call<ModelMovies> call, Response<ModelMovies> response) {
                if (response.isSuccessful()){
                    ModelMovies movies = response.body();
                    dataMovies = movies.getResults();
                    ListMovieAdapter adapter = new ListMovieAdapter(MainActivity.this,dataMovies);
                    progbar.setVisibility(View.GONE);
                    listmovie.setAdapter(adapter);
                    adapter.setOnClick(MainActivity.this);
                }
            }

            @Override
            public void onFailure(Call<ModelMovies> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error"+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.toString());

            }
        });
    }

    private void rotasidevice() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            listmovie.setLayoutManager(new GridLayoutManager(this, 3));
        } else {
            listmovie.setLayoutManager(new GridLayoutManager(this, 2));
        }
    }

    @OnClick(R.id.btnsearch)
    public void onViewClicked() {
        String pencarian = edtcari.getText().toString();
        if (TextUtils.isEmpty(pencarian)) {
            edtcari.setError("harus di isi !");
            edtcari.requestFocus();
        } else {
            RetroApi api = RetroConfig.getInstaceRetrofit();
            Call<ModelSearchMovies> searchMoviesCall = api.getSearchMovies(
                    MyConstant.API_KEY, MyConstant.language, pencarian
            );
            searchMoviesCall.enqueue(new Callback<ModelSearchMovies>() {
                @Override
                public void onResponse(Call<ModelSearchMovies> call, Response<ModelSearchMovies> response) {
                    if (response.isSuccessful()) {
                        ModelSearchMovies searchMovies = response.body();
                        dataSearchMovies = searchMovies.getResults();
                        progbar.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progbar.setVisibility(View.GONE);
                                SearchAdapter adapter = new SearchAdapter(MainActivity.this, dataSearchMovies);
                                listmovie.setAdapter(adapter);
                                adapter.setOnSearchClick(MainActivity.this);
                            }
                        }, 2000);

                    }
                }

                @Override
                public void onFailure(Call<ModelSearchMovies> call, Throwable t) {

                }
            });

        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex, DataMovies movie) {
        Intent i = new Intent(MainActivity.this, DetailMovieActivity.class);
        i.putExtra(MyConstant.TITLE, movie.getOriginalTitle());
        i.putExtra(MyConstant.RELEASE, movie.getReleaseDate());
        i.putExtra(MyConstant.RATE, movie.getVoteAverage());
        i.putExtra(MyConstant.POSTER, movie.getBackdropPath());
        i.putExtra(MyConstant.SYSNOPSIS, movie.getOverview());
        startActivity(i);
    }

    @Override
    public void onListClick(int clickedItemIndex, DataSearchMovie searchMovie) {
        Intent i = new Intent(MainActivity.this, DetailMovieActivity.class);
        i.putExtra(MyConstant.TITLE, searchMovie.getOriginalTitle());
        i.putExtra(MyConstant.RELEASE, searchMovie.getReleaseDate());
        i.putExtra(MyConstant.RATE, searchMovie.getVoteAverage());
        i.putExtra(MyConstant.POSTER, searchMovie.getBackdropPath());
        i.putExtra(MyConstant.SYSNOPSIS, searchMovie.getOverview());
        startActivity(i);
    }
}
