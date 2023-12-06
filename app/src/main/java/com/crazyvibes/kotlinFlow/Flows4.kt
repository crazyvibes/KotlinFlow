package com.crazyvibes.kotlinFlow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.crazyvibes.KotlinFlow.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class Flows4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flows)

        //consumer
        GlobalScope.launch {
            val data: Flow<Int> = producer()
            delay(2500) //every consumer get data from starting.
            // if we comment below consumer block than producer will not produce any data.
            data.collect {
                Log.d("Data", it.toString())
            }
        }


        // a producer can have multiple consumers.
        //consumer2
       val job =  GlobalScope.launch {
            val data: Flow<Int> = producer()
            data.collect {
                Log.d("Data2", it.toString())
            }
        }

        GlobalScope.launch {
            delay(3500)  // once time over coroutine will be canceled as well flow will be also canceled.
            job.cancel()
        }

    }

    //producer
    private fun producer() = flow<Int> {
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        list.forEach {
            delay(1000)
            emit(it) //passing data in stream one by one
        }
    }

}

//* In cold stream every consumers is independent and get data from starting.
//* In hot stream once data lost and produced, will not get again to consumer who joins late.
