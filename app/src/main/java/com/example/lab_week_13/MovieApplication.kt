package com.example.lab_week_13

import android.app.Application
import androidx.work.*
import com.example.lab_week_13.database.MovieDatabase
import com.example.lab_week_13.model.MovieWorker
import java.util.concurrent.TimeUnit
import com.example.lab_week_13.api.MovieService

class MovieApplication : Application() {

    lateinit var movieRepository: MovieRepository

    override fun onCreate() {
        super.onCreate()

        // 🔑 INIT REPOSITORY (WAJIB)
        val movieService = MovieService.getInstance()
        val movieDatabase = MovieDatabase.getInstance(applicationContext)

        movieRepository = MovieRepository(
            movieService,
            movieDatabase
        )

        // 🔑 WORKMANAGER SCHEDULING (COMMIT 3)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequest.Builder(
            MovieWorker::class.java,
            1,
            TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .addTag("movie-work")
            .build()

        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork(
                "movie-work",
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
    }
}
