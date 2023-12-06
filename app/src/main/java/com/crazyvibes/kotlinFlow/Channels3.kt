package com.crazyvibes.kotlinFlow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.crazyvibes.KotlinFlow.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class Channels3 : AppCompatActivity() {
    val channel = Channel<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channells)

        producer()
        consumer()
    }

    private fun producer() {
        CoroutineScope(Dispatchers.Main).launch {
            channel.send(1)
            channel.send(2)
            channel.send(3)
        }
    }

    private fun consumer() {
        CoroutineScope(Dispatchers.Main).launch {
            channel.receive()
            channel.receive()
            channel.receive()
        }
    }

}