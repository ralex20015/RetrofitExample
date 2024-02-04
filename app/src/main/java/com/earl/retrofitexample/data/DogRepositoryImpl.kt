package com.earl.retrofitexample.data

import com.earl.retrofitexample.data.model.Dog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class DogRepositoryImpl(
    private val api: Api
): DogRepository {

    override suspend fun getDog(): Flow<Result<Dog>> {
       return flow {
        val dog = try {
            api.getDog()
        }catch (e: IOException){
            e.printStackTrace()
            emit(Result.Error(message = "Error loading dog"))
            return@flow
        }catch (e: HttpException){
            e.printStackTrace()
            emit(Result.Error(message = "Error loading dog"))
            return@flow
        }catch (e: Exception){
            e.printStackTrace()
            emit(Result.Error(message = "Error loading dog"))
            return@flow
        }
        emit(Result.Success(dog))
       }
    }
}