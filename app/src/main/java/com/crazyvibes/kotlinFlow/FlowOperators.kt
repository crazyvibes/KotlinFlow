package com.crazyvibes.kotlinFlow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.crazyvibes.KotlinFlow.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis
import kotlin.time.measureTime

class FlowOperators : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow_operators)

        GlobalScope.launch {
            val data: Flow<Int> = producer()
            data.onStart {
                //when flow get start
                Log.d("Data", "starting out")
                //we can emit some extra value on onStart and onCompletion
                emit(-5)
            }.onCompletion {
                //when flow gets complete
                Log.d("Data", "completed")
                //we can emit some extra value on onStart and onCompletion
                emit(-4)
            }.onEach {
                //to operate on each item
                Log.d("Data", "about to emit $it")
            }.collect {
                //flow data gets collect
                Log.d("Data", it.toString())
            }
        }


        GlobalScope.launch {
            val data: Flow<Int> = producer()

            //terminal operators
            val result = producer().first() //will pick the first item
            val result2 = producer().toList() //convert items into list and give item


            producer()
                //non-terminal operators
                .map {
                    //convert or operate with each item of data
                    it * 2
                }
                .filter {
                    // filter the data
                    it < 8 //include only those item which is less than 8
                }
                .collect {
                    //collect is a terminal operators
                }

            //suppose consumer is slow to consume the data
            val time = measureTimeMillis {
                producer()
                    .buffer(3)  //to store 3 items in buffer
                    .collect{
                        delay(1500)
                        Log.d("Data:", it.toString())
                    }
            }
            Log.d("Time:", time.toString())
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

    //There are two types of operators in flow:
    //1. Terminal - starts the flow //declared with suspend// a. first(), b. toList, c. collect()
    //2. Non-Terminal// a. map(), b. filter()
}