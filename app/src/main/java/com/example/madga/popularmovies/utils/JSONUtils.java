package com.example.madga.popularmovies.utils;

import android.os.AsyncTask;

import com.example.madga.popularmovies.MainActivity;
import com.example.madga.popularmovies.R;
import com.example.madga.popularmovies.model.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONUtils {

    private static final String TAG = "JsonUtils";
    public static ArrayList<String> urlALS = new ArrayList<>();

    /***
     *  This is intended to parse the JSON data and return something for
     *  our Main Activity home page
     * @param json
     * @return Movie
     */

    public static ArrayList<String> parseMovieJson(String json) {

        final String baseURL = String.valueOf(R.string.image_url);

        try {
            //intitialize a new JSONObject
            JSONObject arrayObject = new JSONObject(json);
            //declare a new JSONArray
            JSONArray mArrayObject = arrayObject.getJSONArray("results");
            //make list to hold movie items
            List<Movies> mMovies = new ArrayList<>();
            urlALS.clear();

            //loop through array
            for(int i=0; i< mArrayObject.length(); i++){
                JSONObject obj = mArrayObject.getJSONObject(i);
                String moviePoster = obj.getString("poster_path");
                String URL = baseURL + moviePoster;
                Movies movie = new Movies();

                /* return what has been found in the json parsing for this Movies object*/
                //return new Movies(id,release_date,original_title, overview, popularity, title, poster_path, vote_count);

                /*
                movie.setId(obj.getInt("id"));
                movie.setRelease_date(obj.getString("release_date"));
                movie.setOriginal_title(obj.getString("original_title"));
                movie.setOverview(obj.getString("overview"));
                movie.setPopularity(obj.getDouble("popularity"));
                movie.setTitle(obj.getString("title"));
                movie.setPoster_path(obj.getString("poster_path"));
                movie.setVote_count(obj.getInt("vote_count"));
                */

                //After we've set each property for the current object, we will need to add it to our mMovies list
                mMovies.add(movie);
                //Each time the loop goes around, it will add another movie poster URL to the ArrayList
                urlALS.add(URL);
            }

            //After each object has been added to the list, we will return that list for our application to use
        return urlALS;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
