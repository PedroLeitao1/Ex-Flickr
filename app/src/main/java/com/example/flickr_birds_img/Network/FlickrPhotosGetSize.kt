package com.example.flickr_birds_img.Network

import com.example.flickr_birds_img.Constants.Constants
import com.example.flickr_birds_img.Models.SizeResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface FlickrPhotosGetSize {
    @GET("?method=flickr.photos.getSizes&api_key=${Constants.APP_ID}&format=json&nojsoncallback=1")
    fun getInfo(
        @Query("photo_id") photo_id: String,
    ): retrofit2.Call<SizeResponse>
}