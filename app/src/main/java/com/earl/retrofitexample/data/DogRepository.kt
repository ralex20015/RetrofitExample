package com.earl.retrofitexample.data

import com.earl.retrofitexample.data.model.Dog
import kotlinx.coroutines.flow.Flow

//We create this abstraction in case of we want to use another API, then we can change the implementation
interface DogRepository {
    suspend fun getDog(): Flow<Result<Dog>>
}