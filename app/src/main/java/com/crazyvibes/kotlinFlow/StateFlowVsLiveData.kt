package com.crazyvibes.kotlinFlow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.crazyvibes.KotlinFlow.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StateFlowVsLiveData : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state_flow_vs_live_data)


        //consumer2
        GlobalScope.launch(Dispatchers.Main) {
            val data: StateFlow<Int> = producer()
            val stateValue = data.value //we can get the latest state value
            data.collect {
                delay(6000)
                Log.d("Data2", it.toString())

            }
        }
    }

    //producer - value produces on shared flow
    private fun producer(): StateFlow<Int> {

        // val mutableSharedFlow = MutableSharedFlow<Int>()
        val mutableStateFlow = MutableStateFlow<Int>(10) //pass initial value 10
        GlobalScope.launch {
            val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            list.forEach {
                delay(2000)
                mutableStateFlow.emit(it) //passing data in stream one by one
                Log.d("Producer", "Emitting: -$it")

            }
        }

        return mutableStateFlow
    }
}

//-state flow are hot flow which maintain the state of latest value.
//-In the above code producer continue emits the data and consumer2 consumes after 2500ms.

//-In state flow, there are two types, mutable and normal state flow
//- only last value will be consumed after 6000ms because of state flow otherwise all data will be lost.

//Difference between live data and state flow
//1. In live data, transformations and operators apply on main thread. (disadvantage)
//2. In live data, operators are limited.
//3. Live data is lifeCycle dependent.