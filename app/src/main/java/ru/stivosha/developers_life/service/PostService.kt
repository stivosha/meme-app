package ru.stivosha.developers_life.service

import retrofit2.http.GET
import ru.stivosha.developers_life.entity.Post

interface PostService {
    @GET("random?json=true")
    suspend fun getRandomPost(): Post
}