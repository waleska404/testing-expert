package com.devexperto.testingexpert

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MoviesRemoteDataSourceStub: MoviesRemoteDataSource {
    override fun findPopularMovies(): List<Movie> = listOf(
        Movie(1, "Movie 1"),
        Movie(2, "Movie 2")
    )
}

class MoviesLocalDataSourceSpy: MoviesLocalDataSource {
    private val movies = mutableListOf<Movie>()
    var saveAllCalled = false
    override fun isEmpty(): Boolean = movies.isEmpty()
    override fun findAll(): List<Movie> = movies
    override fun saveAll(movies: List<Movie>) {
        this.movies.addAll(movies)
        saveAllCalled = true
    }
}

class SampleTest {
    @Test
    fun `getMovies() returns a list of local movies if local data source is not empty`() {
        val moviesLocalDataSource = MoviesLocalDataSourceSpy()
        val moviesRemoteDataSource = MoviesRemoteDataSourceStub()
        val moviesRepository = MoviesRepository(moviesLocalDataSource, moviesRemoteDataSource)

        val movies = moviesRepository.findAll()

        assertEquals(1, movies[0].id)
    }

    @Test
    fun `findAll() returns a list of remote movies if local data source is empty`() {
        val moviesLocalDataSource = MoviesLocalDataSourceSpy()
        val moviesRemoteDataSource = MoviesRemoteDataSourceStub()
        val moviesRepository = MoviesRepository(moviesLocalDataSource, moviesRemoteDataSource)

        val movies = moviesRepository.findAll()

        assertEquals(1, movies[0].id)
        assertEquals(2, movies[1].id)
    }

    @Test
    fun `When local data source is empty, movies are saved from remote`() {
        val moviesLocalDataSource = MoviesLocalDataSourceSpy()
        val moviesRemoteDataSource = MoviesRemoteDataSourceStub()
        val moviesRepository = MoviesRepository(moviesLocalDataSource, moviesRemoteDataSource)

        moviesRepository.findAll()

        assertTrue(moviesLocalDataSource.saveAllCalled)
    }
}