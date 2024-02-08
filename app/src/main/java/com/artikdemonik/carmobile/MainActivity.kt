package com.artikdemonik.carmobile


import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val policy = ThreadPolicy.Builder()
            .permitAll().build()
        StrictMode.setThreadPolicy(policy)
        super.onCreate(savedInstanceState)
        val viewModel = MainVM()
        viewModel.getCarList()
        setContent {
            var carList by remember {
                mutableStateOf(emptyList<Car>())
            }
            viewModel.carList.observe(this){
                carList = it
            }
            var text by remember{
                mutableStateOf("")
            }
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.hsv(32f, 0.83f, 0.898f, 1f))){
                Column{
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.075f)
                        .background(color = Color.hsv(189.0f, 0.26f, 0.302f, 1f)))
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.040f)
                        .background(color = Color.hsv(202f, 0.148f, 0.714f, 1f)))
                    Spacer(modifier = Modifier.size(30.dp))
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        TextField(value = text, onValueChange = {t -> text = t}, colors = TextFieldDefaults.textFieldColors(containerColor = Color.White, textColor = Color.Black.copy(alpha = 0.5f)))
                    }
                    Spacer(modifier = Modifier.size(60.dp))
                    LazyColumn{
                        items(carList){
                            CarItem(img = "todo", name = it.name, type = it.brand, price = 10111010)
                        }
                    }
                }
            }

        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CarItem(img: String, name: String, type: String, price: Int){
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Row(modifier = Modifier.padding(2.dp)){
                Box(modifier = Modifier
                    .background(color = Color.Red)
                    .size(50.dp)
                    .padding(5.dp))
                Column() {
                    Text(text = name, fontSize = 22.sp)
                    Text(text = type, fontSize = 11.sp)
                }
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd){
                    Text("$price р")
                }
            }
        }
    }
}

