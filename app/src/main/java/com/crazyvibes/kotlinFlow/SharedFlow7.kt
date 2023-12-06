package com.crazyvibes.kotlinFlow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.crazyvibes.KotlinFlow.R
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class SharedFlow7 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_flow)

        //consumer1
        GlobalScope.launch {
            val data: Flow<Int> = producer()
            data.collect {
                Log.d("Data1", it.toString())
            }
        }

        //consumer2
        GlobalScope.launch {
            val data: Flow<Int> = producer()
            data.collect {
                delay(2500)
                Log.d("Data2", it.toString())
            }
        }
    }


    //producer - value produces on shared flow
    private fun producer(): Flow<Int> {

       // val mutableSharedFlow = MutableSharedFlow<Int>()
        val mutableSharedFlow = MutableSharedFlow<Int>(replay = 1)
        GlobalScope.launch {
            val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            list.forEach {
                mutableSharedFlow.emit(it) //passing data in stream one by one
                delay(1000)
            }
        }

        return mutableSharedFlow
    }
}

//- Shared flows are are hot flows those can have multiple consumers
//- Every consumers get same data.
//- there are two types fo shared flow: Mutable and Immutable(read only)
//- In the above code both consumers will get same data.
//- reply is basically used for if any consumer joins later then we can store and pass some recent values.