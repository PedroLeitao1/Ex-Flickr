package com.example.flickr_birds_img.Models

import java.io.Serializable

data class SizeResponse (
    val sizes: Sizes,
    val stat: String
) : Serializable {
    data class Sizes(
        val canblog: Int,
        val candownload: Int,
        val canprint: Int,
        val size: List<Size>
    ) : Serializable{
        data class Size(
            val height: Int,
            val label: String,
            val media: String,
            val source: String,
            val url: String,
            val width: Int
        ) : Serializable
    }
}