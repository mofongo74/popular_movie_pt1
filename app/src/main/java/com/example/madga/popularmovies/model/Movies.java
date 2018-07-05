package com.example.madga.popularmovies.model;

/***
 * Movie class has constructor to build individual profile for each movie
 */
public class Movies {

    // Initiate variables
    private int id;
    private String originalTitle;
    private String posterThumb;
    private String releaseDate;
    private String movieLength;
    private String userRating;
    private double popularity;
    private boolean isFavorite = false;
    private String overView;

    // TODO ? establish setters, make an empty contructor first.
    public Movies() {
    }
    public Movies(int id, String originalTitle, String posterThumb, String releaseDate, String movieLength, String userRating, Double popularity, String overView) {
        this.id = id;
        this.originalTitle = originalTitle;
        this.posterThumb = posterThumb;
        this.releaseDate = releaseDate;
        this.movieLength = movieLength;
        this.userRating = userRating;
        this.popularity = popularity;
        this.overView = overView;
    }

    // TODO ? establish getters for the API to plop our data into.
    public int getId() { return id; }
    public String getName() {
        return originalTitle;
    }
    public String getPosterThumb() {return posterThumb;  }
    public String getReleaseDate() {
        return releaseDate;
    }
    public String getMovieLength() {
        return movieLength;
    }
    public String getUserRating() {
        return userRating;
    }
    public Double getPopularity() { return popularity; }
    public String getOverView() {
        return overView;
    }



    // TODO ? set favorite info for future toggle
    public boolean getIsFavorite() {
        return isFavorite;
    }
    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }
    public void toggleFavorite() {
        isFavorite = !isFavorite;
    }
}
