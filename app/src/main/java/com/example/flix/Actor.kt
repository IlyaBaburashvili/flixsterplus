package com.example.flix

import kotlinx.serialization.Serializable
import org.json.JSONArray


@Serializable
data class Actor(
    val movieId: Int?,
    val profilePath: String?,
    val name: String?,
    val knownFor: String,

    ) : java.io.Serializable {
    val profileImageUrl = "https://image.tmdb.org/t/p/w342/$profilePath"

    companion object {
        fun fromJasonArray(movieJsonArray: JSONArray): List<Actor> {
            val people = mutableListOf<Actor>()
            for(i in 0 until movieJsonArray.length()){
                val peopleJson = movieJsonArray.getJSONObject(i)
                people.add(
                    Actor(
                        peopleJson.getInt("id"),
                        peopleJson.getString("profile_path"),
                        peopleJson.getString("name"),
                        peopleJson.getString("known_for")
                        )
                )
            }
            return people
        }
    }
}
