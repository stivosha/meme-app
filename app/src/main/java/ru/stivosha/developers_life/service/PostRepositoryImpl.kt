package ru.stivosha.developers_life.service

class PostRepositoryImpl : PostRepository{
    var client: PostService = RetrofitClient.postService

    override suspend fun getRandomPost() = client.getRandomPost()
}