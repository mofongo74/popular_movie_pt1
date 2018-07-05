package com.example.madga.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madga.popularmovies.utils.JSONUtils;
import com.example.madga.popularmovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.madga.popularmovies.R.string.base_url;

// TODO ? extends appcompatactivity
public class MainActivity extends AppCompatActivity {

    List<String> movies;
    GridView gridView;
    ImageView imageView;

    private final String TAG = MainActivity.class.getSimpleName();

    private ProgressBar mLoadingIndicator;
    private GridView mGridView;

    // COMPLETED (12) Create a variable to store a reference to the error message TextView
    private TextView mErrorMessageDisplay;
    // Remember the favorite movies
    private String favoriteMoviesKey = String.valueOf(R.string.fav_list);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // COMPLETED (13) Get a reference to the error TextView using findViewById
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);

        // COMPLETED (25) Get a reference to the ProgressBar using findViewById
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        showLoading();
        //TODO refer to isOnline check to avoid timeout
        isOnline();

        // TODO using findViewById to get a reference to gridview
        mGridView = findViewById(R.id.gridview);
        showMovieDataView();

    }

//    private void makeTMDSearch() {
//        String tmdSearchBy = mSearchBoxEditText.getText().toString();
//        URL tmdSearchUrl = NetworkUtils.buildUrl(tmdSearchBy);
//        mUrlDisplayTextView.setText(tmdSearchUrl.toString());
//        new TMDTask().execute(tmdSearchUrl);
//    }


    // This is for the Menu.xml
    // TODO (8) Override onCreateOptionsMenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO (9) Within onCreateOptionsMenu, use getMenuInflater().inflate to inflate the menu
        getMenuInflater().inflate(R.menu.main, menu);
        // TODO (10) Return true to display your menu
        return true;
    }

    // TODO (11) Override onOptionsItemSelected
    // TODO (12) Within onOptionsItemSelected, get the ID of the item that was selected
    // TODO (13) If the item's ID is R.id.action_search_by_top or action_search_by_pop, show a Toast and return true to tell Android that you've handled this menu click
    // TODO (14) Don't forgot to call .show() on your Toast
    // TODO (15) If you do NOT handle the menu click, return super.onOptionsItemSelected to let Android handle the menu click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search_by_top) {
            userSortOrder(R.string.top_rated);
            //Display Main Activity based on user menu selection most_pop or top_rated
            requestData(new StringBuilder().append(base_url + R.string.http_top_rated).append("?api_key=").append(R.string.api_key_v3_auth).toString());
        } else if (itemThatWasClickedId == R.id.action_search_by_pop) {
            userSortOrder(R.string.most_pop);
            //Display Main Activity based on user menu selection most_pop or top_rated
            requestData(new StringBuilder().append(base_url + R.string.http_popular).append("?api_key=").append(R.string.api_key_v3_auth).toString());
        }
        return super.onOptionsItemSelected(item);
    }

    public void requestData(String url){
        TMDTask task = new TMDTask();
        task.execute(url);
    }

    public class TMDTask extends AsyncTask<URL, Void, String> {

        // COMPLETED (26) Override onPreExecute to set the loading indicator to visible
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String searchResults = null;
            try {
                searchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return searchResults;
        }

        @Override
        protected void onPostExecute(String searchResults) {

            // COMPLETED (27) As soon as the loading is complete, hide the loading indicator
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (searchResults != null && !searchResults.equals("")) {
                //movies = JSONUtils.parseMovieJson(json);
                mGridView.setAdapter(new MovieAdapter(MainActivity.this, JSONUtils.urlALS));
                super.onPostExecute(searchResults);
                // COMPLETED (17) Call showJsonDataView if we have valid, non-null results
                //showJsonDataView();
                //mSearchResultsTextView.setText(searchResults);
            } else {
                // COMPLETED (16) Call showErrorMessage if the result is null in onPostExecute
                closeOnError();
            }
        }

        public void execute(String url) {
        }
    }


    /***
     * Movie Adapter to load the info into the grid view from the movie data source
     */
    public class MovieAdapter extends BaseAdapter {
        private final Context mContext;
        private ArrayList<String> images;
        ImageView imageView;

        // Remember the favorite movies
        private final String favoritedMoviesKey = String.valueOf(R.string.fav_list);


        // Instantiate the adapter
        public MovieAdapter(Context context, ArrayList<String> images) {
            this.mContext = context;
            this.images = images;
        }

        // return count
        @Override
        public int getCount() {
            return images.size();
        }

        // req'd
        @Override
        public long getItemId(int position) {
            return position;
        }

        // req'd
        @Override
        public Object getItem(int position) {
            return images.get(position);
        }

        // put this view in our activity MAIN layout
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //populate this movie in this position

            // inflate the view
            if (convertView == null) {
                LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                convertView = layoutInflater.inflate(R.layout.activity_main, parent, false);
                //TODO ?: populate ImageView in activity main by ID
                ImageView imageView = convertView.findViewById(R.id.posterThumb_iv);
            } else {
                //reference the views
                ImageView imageView = (ImageView) convertView;
            }

            /* The Movie Poster imageView url should appear as such
             * http://image.tmdb.org/t/p/w185/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg
             * */
            Picasso.with(this.mContext)
                    .load(images.get(position))
                    .into(imageView);

            // Toggle the star
            //imageViewFavorite.setImageResource(
            //movie.getIsFavorite() ? R.mipmap.star_enabled : R.mipmap.star_disabled);

            return convertView;
        }
    }


//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        final ArrayList<Integer> favoritedMovieNames = new ArrayList<>();
//        for (Movie movie : movies) {
//            if (movie.getIsFavorite()){
//                favoritedMovieNames.add(movie.getName());
//            }
//        }
//        outState.putIntegerArrayList(favoriteMoviesKey, favoritedMovieNames);
//    }


//    /***
//     * Restores selections when activity is recreated
//     * @param savedInstanceState
//     */


//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//
//        /* get our previously saved list of favorited movies*/
//        final ArrayList<Integer> favoritedMovieNames =
//                savedInstanceState.getIntegerArrayList(favoriteMoviesKey);
//
//        /* look at all of your movies and figure out which are the favorites*/
//        for (int bookName : favoritedMovieNames) {
//            for (Movie movie : movies) {
//                if (movie.getName() == ) {
//                    movie.setIsFavorite(true);
//                    break;
//                }
//            }
//        }
//    }


    /***
     * TODO 3: Allow user to sort order via a setting by most popular or top rated
     *
     */
    private void userSortOrder(int sortBy) {
        if (sortBy == R.string.top_rated) {
            Toast.makeText(this, R.string.top_rated, Toast.LENGTH_SHORT).show();
        } else if (sortBy == R.string.most_pop) {
            Toast.makeText(this, R.string.most_pop, Toast.LENGTH_SHORT).show();
        } else {
            Log.i(TAG,"Something is wonky with user Sort Order.");
            closeOnError();
        }
    }

    /**
     * This method will make the View for the movie data visible and hide the loading indicator.
     *
     */
    private void showMovieDataView() {
        // First, make sure the error is invisible
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        // First, hide the loading indicator
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        // Then, make sure the JSON data is visible
        mGridView.setVisibility(View.VISIBLE);
    }
    /**
     * This method will make the loading indicator visible and hide the movie View
     *
     */
    private void showLoading() {
        /* Then, hide the weather data */
        mGridView.setVisibility(View.INVISIBLE);
        /* Finally, show the loading indicator */
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }


    /***
     * TODO 5a: Prepare error checking
     */
    private void closeOnError() {
        // First, hide the visible area
        mGridView.setVisibility(View.INVISIBLE);
        // Then, show the error
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        finish();
        Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show();
    }


    // TODO : "make sure your app does not crash when there is no network connection"
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
