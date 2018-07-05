package com.example.madga.popularmovies.utils;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.madga.popularmovies.MainActivity;
import com.example.madga.popularmovies.R;
import com.example.madga.popularmovies.model.Movies;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/***
 * The Network Utils is used to communicate with The Movie Data Base Repository
 */
public class NetworkUtils {

    /** Tag for the log messages */
    private static final String TAG = NetworkUtils.class.getSimpleName();

    //private static final String DYNAMIC_MOVIE_URL =
    //        "http://api.themoviedb.org/3";
    private static final String TMD_BASE_URL =
            "http://api.themoviedb.org/3";

    /*
     * The sort field. Users can sort by most Popular or by Top Charts
     * Default: results are sorted by most popular if no field is specified.
     */
    //final static String SORT_PARAM = "@string/http_top_rated";
    final static String SORT_PARAM = String.valueOf(R.string.http_popular);

    final static String apiKey = String.valueOf(R.string.api_key_v3_auth);

/* The JSON for popular movies is this:
* http://api.themoviedb.org/3/movie/popular?api_key=a9f7e70ba759b199817dae93d9645b21
* */
    /**
     * Builds the URL used to query TheMovieDatabase.
     *
     * @param tmdSearchBy The keyword used for queries.
     * @return The URL used.
     */
    public static URL buildUrl(String tmdSearchBy) {
        Uri builtUri = Uri.parse(TMD_BASE_URL).buildUpon()
                .appendQueryParameter(SORT_PARAM, tmdSearchBy)
                .appendPath(apiKey)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.d(TAG, String.valueOf(builtUri));
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
        }catch (Exception error) {
            error.printStackTrace();
            return null;
        }finally {
            urlConnection.disconnect();
        }
        return null;
    }
}
