package com.example.lab_week_13.model

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.lab_week_13.MovieApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {

        val repository =
            (applicationContext as MovieApplication).movieRepository

        CoroutineScope(Dispatchers.IO).launch {
            repository.fetchMoviesFromNetwork()
        }

        return Result.success()
    }
}