package com.example.madga.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madga.popularmovies.model.Movies;
import com.example.madga.popularmovies.utils.JSONUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

// TODO (?) Create a new Activity called DetailActivity
public class DetailActivity extends AppCompatActivity {


    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        assert intent != null;
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        /*in sandwich club this comes from a static array list in Strings.xml */
        //String[] movies = getResources().getStringArray(R.array.movie_names);

        //String json = movies[position];
        ArrayList<String> movies = null;
/*
        //TODO 2: handle exception with parsing sandwich
        try {
            movies = JSONUtils.parseMovieJson(json).toArray(new String[0]);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (movies == null) {
            // Movie data is unavailable
            closeOnError();
            return;
        }

        //TODO ?: populate TextViews & ImageView in activity detail by ID
        ImageView posterThumb = findViewById(R.id.posterThumb_iv);

        TextView originalTitle = findViewById(R.id.originalTitle_tv);
        TextView releaseDate = findViewById(R.id.releaseDate_tv);
        TextView movieLength = findViewById(R.id.movieLength_tv);
        //TextView popularity = findViewById(R.id.popularity);
        TextView userRating = findViewById(R.id.userRating_tv);
        TextView overview = findViewById(R.id.overview_tv);

        Picasso.with(this)
                .load(movies.getPosterThumb())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(posterThumb);

        // set the name of this page
        setTitle(movies.getName());

        //TODO 4: get/set TextViews to populate UI
        //from sandwich club detailActivity.java
        originalTitle.setText(movies.getName());
        releaseDate.setText(movies.getReleaseDate());
        movieLength.setText(movies.getMovieLength());
        //popularity.setText(movies.getPopularity());
        userRating.setText(movies.getUserRating());
        overview.setText(movies.getOverView());
 */   }


    /***
     * TODO 5b: Prepare error checking
     */
    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show();
    }
}
