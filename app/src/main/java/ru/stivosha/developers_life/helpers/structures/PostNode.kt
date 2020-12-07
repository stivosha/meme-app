package ru.stivosha.developers_life.helpers.structures

import ru.stivosha.developers_life.entity.Post

class PostNode(var value: Post) {
    var nextNode: PostNode? = null
    var prevNode: PostNode? = null
}