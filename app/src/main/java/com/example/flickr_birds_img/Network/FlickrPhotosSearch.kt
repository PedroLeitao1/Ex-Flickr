package com.example.flickr_birds_img.Network

import com.example.flickr_birds_img.Constants.Constants
import com.example.flickr_birds_img.Models.PhotoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrPhotosSearch {
    @GET("?method=flickr.photos.search&api_key=${Constants.APP_ID}&tags=bird&nojsoncallback=1&format=json")
    fun getInfo() : Call<PhotoResponse>
}