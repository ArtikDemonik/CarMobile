package com.artikdemonik.carmobile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

data class Car(val name: String, val type: String, val brand: String, val power: String)

class MainVM: ViewModel() {
    val carList = MutableLiveData<List<Car>>()
    private suspend fun connect(): Socket {
        return aSocket(
            ActorSelectorManager(
                Executors.newCachedThreadPool()
                    .asCoroutineDispatcher()
            )
        )
            .tcp()
            .connect(InetSocketAddress("192.168.1.130", 8003))
    }
    fun getCarList() {
        viewModelScope.launch {
            val socket = connect()
            socket.openWriteChannel(autoFlush = true).writeStringUtf8("getCars\n")
            val reader = socket.openReadChannel()
            val answer = reader.readUTF8Line().toString()
            //Log.d("qwe", answer)
            for (car in Gson().fromJson(answer, Answer::class.java).MapAttributes){
                carList.value = carList.value?.plus(Car(car.value[0], car.value[1], car.value[2], car.value[3]))
            }
        }
    }


}