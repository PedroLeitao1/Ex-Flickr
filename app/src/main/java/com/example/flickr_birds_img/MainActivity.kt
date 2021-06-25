package com.example.flickr_birds_img


import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.flickr_birds_img.Adapter.ItemAdapter
import com.example.flickr_birds_img.Constants.Constants
import com.example.flickr_birds_img.Models.PhotoResponse
import com.example.flickr_birds_img.Models.SizeResponse

import com.example.flickr_birds_img.Network.FlickrPhotosGetSize
import com.example.flickr_birds_img.Network.FlickrPhotosSearch

import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    var photoSearchList : PhotoResponse? = null
    var photoSizesList : SizeResponse? = null

    val list = ArrayList<String>()

    private val itemAdapter = ItemAdapter(this, list)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR

        getPhotoSearch()

        // Set the LayoutManager that this RecyclerView will use.
        ItemsList.layoutManager = GridLayoutManager(this, 2)
        // Adapter class is initialized and list is passed in the param.
        // adapter instance is set to the recyclerview to inflate the items.
        ItemsList.adapter = itemAdapter
    }

    private fun getPhotoSearch(){
        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service : FlickrPhotosSearch = retrofit.create<FlickrPhotosSearch>(FlickrPhotosSearch::class.java)

        val listCall : Call<PhotoResponse> = service.getInfo()

        listCall.enqueue(object : Callback<PhotoResponse> {
            override fun onResponse(call: Call<PhotoResponse>, response: Response<PhotoResponse>) {
                if(response!!.isSuccessful){
                    photoSearchList = response.body()
                    Log.i("WWT", "$photoSearchList")
                    getSizesSearch()
                } else {
                    Log.i("WWT", response.code().toString())
                }
            }

            override fun onFailure(call: Call<PhotoResponse>, t: Throwable) {
                Log.i("WWT", t!!.message.toString())
            }

        })
    }

    private fun getSizesSearch(){


        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service : FlickrPhotosGetSize = retrofit.create<FlickrPhotosGetSize>(FlickrPhotosGetSize::class.java)

        for(item in photoSearchList!!.photos.photo){

            val listCall : Call<SizeResponse> = service.getInfo(item.id)

            listCall.enqueue(object : Callback<SizeResponse> {
                override fun onResponse(call: Call<SizeResponse>, response: Response<SizeResponse>) {
                    if(response!!.isSuccessful){
                        photoSizesList = response.body()
                        list.add(photoSizesList!!.sizes.size[1].source)
                        itemAdapter.notifyDataSetChanged()
                    } else {
                        Log.i("WWTe", response.code().toString())
                    }
                }

                override fun onFailure(call: Call<SizeResponse>, t: Throwable) {
                    Log.i("WWTd", t!!.message.toString())
                }

            })
        }
    }
}