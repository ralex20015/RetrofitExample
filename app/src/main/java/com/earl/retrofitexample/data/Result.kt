package com.earl.retrofitexample.data


//With this class we get the response of the api, like success, error, etc, the result would be of type cause we want to
// pass some data in this case is of type product or error
sealed class Result<T>(
    val data: T? = null,
    val message: String? = null
){
    class Success<T> (data: T?): Result<T>(data)
    class Error<T> (data: T? = null, message: String): Result<T>(data, message)

}