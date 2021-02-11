package com.example.newcomponentstructure.model

import io.reactivex.Single
import retrofit2.http.GET

interface Api {
    @GET("DevTides/DogsApi/master/dogs.json")
    fun getDogs():Single<ArrayList<DogBreed>>
}