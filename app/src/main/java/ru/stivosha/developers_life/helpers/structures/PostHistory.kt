package ru.stivosha.developers_life.helpers.structures

import ru.stivosha.developers_life.entity.Post

class PostHistory {
    var currentNode: PostNode? = null
    var size: Int = 0
    var currentIndex = -1;

    fun add(post: Post){
        if(currentNode == null){
            currentNode = PostNode(post)
        }else{
            val newPost = PostNode(post)
            newPost.value = post
            newPost.prevNode = currentNode
            currentNode!!.nextNode = newPost
            currentNode = newPost
        }
        size += 1
        currentIndex += 1
    }

    fun next(): Boolean {
        if (currentNode?.nextNode == null)
            return false
        currentNode = currentNode!!.nextNode!!
        currentIndex += 1
        return true
    }

    fun prev(): Boolean {
        if (currentNode!!.prevNode == null)
            return false
        currentNode = currentNode!!.prevNode!!
        currentIndex -= 1
        return true
    }
}