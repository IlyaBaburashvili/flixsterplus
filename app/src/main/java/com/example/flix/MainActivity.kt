package com.example.flix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.flix.databinding.ActivityMainBinding
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONException

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

private const val TAG = "MainActivity/"
private const val SEARCH_API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"
private const val ACTOR_SEARCH_URL =
    "https://api.themoviedb.org/3/person/popular\n?api_key=${SEARCH_API_KEY}"


class MainActivity : AppCompatActivity() {

    private val people = mutableListOf<Actor>()
    private lateinit var personRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        personRecyclerView = findViewById(R.id.list)
        val articleAdapter = ActorAdapter(this, people)
        personRecyclerView.adapter = articleAdapter

        personRecyclerView.layoutManager = GridLayoutManager(this, 2)

        val client = AsyncHttpClient()
        client.get(ACTOR_SEARCH_URL , object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch movies: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched movies: $json")

                try {
                    val resultsJson = json.jsonObject.getJSONArray("results")
                    people.addAll(Actor.parceJson(resultsJson))
                    articleAdapter.notifyDataSetChanged()


                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }

        })

    }
}