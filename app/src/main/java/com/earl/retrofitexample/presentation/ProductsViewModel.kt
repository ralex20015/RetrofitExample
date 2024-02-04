package com.earl.retrofitexample.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.earl.retrofitexample.data.DogRepository
import com.earl.retrofitexample.data.Result
import com.earl.retrofitexample.data.model.Dog
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val productsRepository: DogRepository
) : ViewModel() {
    private val _dog = MutableStateFlow(Dog())
    val dog = _dog.asStateFlow()

    private val showText = mutableStateOf("")

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    suspend fun getRandomDog(){
        viewModelScope.launch {
            productsRepository.getDog().collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        _showErrorToastChannel.send(true)
                    }

                    is Result.Success -> {
                        result.data?.let { dog ->
                            _dog.update { dog }
                        }
                    }
                }
            }
        }
    }

    fun getShowText(): String{
        return showText.value
    }

    fun setShowText(){
        showText.value = "Image"
    }
}
