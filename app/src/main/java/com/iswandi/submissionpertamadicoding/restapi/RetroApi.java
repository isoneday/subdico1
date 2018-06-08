package com.iswandi.submissionpertamadicoding.restapi;

import com.iswandi.submissionpertamadicoding.model.ModelMovies;
import com.iswandi.submissionpertamadicoding.model.ModelSearchMovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by iswandisaputra on 3/1/18.
 */

public interface RetroApi {
    //top rated
    @GET("movie/top_rated")
    Call<ModelMovies> getTopRatedMovie(
            @Query("api_key") String apikey);
    //popular
    @GET("movie/popular")
    Call<ModelMovies> getPopularMovie(
            @Query("api_key") String apikey);

    @GET("search/movie")
    Call<ModelSearchMovies> getSearchMovies(
            @Query("api_key") String apikey,
            @Query("language") String language,
            @Query("query")String query);


}
