package com.earl.retrofitexample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.earl.retrofitexample.data.DogRepositoryImpl
import com.earl.retrofitexample.presentation.ProductsViewModel
import com.earl.retrofitexample.ui.theme.RetrofitExampleTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {


    //viewModel Factory
    private val viewModel by viewModels<ProductsViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProductsViewModel(DogRepositoryImpl(RetrofitInstance.api))
                        as T
            }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrofitExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val context = LocalContext.current
                    val dog by viewModel.dog.collectAsState()

                    LaunchedEffect(key1 = viewModel.showErrorToastChannel) {
                        viewModel.showErrorToastChannel.collectLatest { show ->
                            if (show) {
                                Toast.makeText(
                                    context,
                                    "Error",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    val imageState = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current).data(dog.message)
                            .size(Size.ORIGINAL).build()
                    ).state

                    val coroutineScope = rememberCoroutineScope()
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            if (imageState is AsyncImagePainter.State.Success) {
                                Image(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp),
                                    painter = imageState.painter,
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                if (viewModel.getShowText() == "") {
                                    Text(
                                        text = "Cick the button to see a dog image!!",
                                        style = MaterialTheme.typography.headlineMedium
                                    )
                                } else {
                                    CircularProgressIndicator()
                                }
                            }
                            Spacer(modifier = Modifier.height(32.dp))
                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        viewModel.getRandomDog()
                                        viewModel.setShowText()
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(text = "Get Random Dog Image")
                            }
                        }
                    }
                }
            }
        }
    }
}