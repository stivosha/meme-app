package ru.stivosha.developers_life.service

import ru.stivosha.developers_life.entity.Post

interface PostRepository {
    suspend fun getRandomPost() : Post
}