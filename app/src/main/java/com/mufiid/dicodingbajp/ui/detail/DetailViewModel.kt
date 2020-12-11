package com.mufiid.dicodingbajp.ui.detail

import androidx.lifecycle.ViewModel
import com.mufiid.dicodingbajp.data.MovieEntity
import com.mufiid.dicodingbajp.utils.DataDummy

class DetailViewModel : ViewModel() {
    private lateinit var movieId: String

    fun setSelectedMovie(movieId: String) {
        this.movieId = movieId
    }

    fun getMovie(): MovieEntity {
        lateinit var movie: MovieEntity
        val movieEntities = DataDummy.generateDummyMovies()
        for (movieEntity in movieEntities) {
            if (movieEntity.movieId == movieId) {
                movie = movieEntity
            }
        }
        return movie
    }
}