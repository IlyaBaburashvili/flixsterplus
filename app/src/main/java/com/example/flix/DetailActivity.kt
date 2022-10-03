package com.example.flix

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val TAG = "DetailActivity"

class DetailActivity : AppCompatActivity() {
    private lateinit var movie: Movie
    private lateinit var actorImageView: ImageView
    private lateinit var actorNameTextView: TextView
    private lateinit var movieImageView: ImageView
    private lateinit var movieTitleTextView: TextView
    private lateinit var movieDescriptionTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        actorImageView = findViewById(R.id.actorPhoto)
        actorNameTextView = findViewById(R.id.name)
        movieImageView = findViewById(R.id.movieImage)
        movieTitleTextView = findViewById(R.id.movie)
        movieDescriptionTextView = findViewById(R.id.movieDescription)

        val actor = intent.getSerializableExtra(ACTOR_EXTRA) as Actor

        actorNameTextView.text = actor.name

        val knownForString=  actor.knownFor

        val gson = Gson()
        val arrayMovieType = object : TypeToken<List<Movie>>() {}.type
        val models : List<Movie> = gson.fromJson(knownForString, arrayMovieType)

        movie = models[0]
        movieTitleTextView.text = movie.title
        movieDescriptionTextView.text = movie.description

        Glide.with(this)
            .load(actor.profileImageUrl)
            .into(actorImageView)

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500/" + movie.movieImageUrl)
            .into(movieImageView)
    }
}