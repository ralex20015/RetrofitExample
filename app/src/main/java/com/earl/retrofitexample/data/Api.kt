package com.earl.retrofitexample.data

import com.earl.retrofitexample.data.model.Dog
import retrofit2.http.GET

interface Api {
    //this is like a sub directory on our url for example if we had https://google.com/subscribe (subscribe will be on the get)

    @GET("breeds/image/random")
    suspend fun getDog():Dog

    //querys are values we can change for each user for example a api id like "https://dummyjson.com/products?api_key=12345"
    //api_key would be our query we need to pass to our function that query ej.
    /*@GET("products/{type}") the {type} on the GET annotation means we change that parameter through a variable anotated with Path
    * suspend fun getProductsList(
    *       @Path("type") type: String,
    *       @Query("page") page: Int,
    *       @Query("api_key") apiKey: String
    *   ): Products
    *
    * */
    companion object {
        const val BASE_URL = "https://dog.ceo/api/"
    }
}