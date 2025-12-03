package com.example.test_lab_week_12

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.test_lab_week_12.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import com.example.test_lab_week_12.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import java.util.Calendar



class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    init {
        fetchPopularMovies()
    }
    // define the LiveData
//    val popularMovies: LiveData<List<Movie>>
//        get() = movieRepository.movies
//    val error: LiveData<String>
//        get() = movieRepository.error
//    // fetch movies from the API
//    private fun fetchPopularMovies() {
//// launch a coroutine in viewModelScope
//// Dispatchers.IO means that this coroutine will run on a shared pool of threads
//
//        viewModelScope.launch(Dispatchers.IO) {
//            movieRepository.fetchMovies()
//        }
//    }

    private val _popularMovies = MutableStateFlow(
        emptyList<Movie>()
    )
    val popularMovies: StateFlow<List<Movie>> = _popularMovies
    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error
    // fetch movies from the API
    private fun fetchPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.fetchMovies()
                .catch { e ->
                    // Menangkap exception dari Flow
                    _error.value = "An exception occurred: ${e.message}"
                }.collect { movies ->
                    val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()

                    val filtered = movies
                        .filter { movie -> movie.releaseDate?.startsWith(currentYear) == true }
                        .sortedByDescending { it.popularity }

                    _popularMovies.value = filtered
                }


        }
        }
    }
