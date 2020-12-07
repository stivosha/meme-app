package ru.stivosha.developers_life.entity

import com.google.gson.annotations.SerializedName

data class Post (
    @SerializedName("description")
    val title : String?,

    @SerializedName("gifURL")
    val gifURL : String?,

    var isNotCorrectData: Boolean = false
)
