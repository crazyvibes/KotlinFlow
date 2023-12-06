package com.crazyvibes.kotlinFlow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.crazyvibes.KotlinFlow.R

class MainActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


}
//## Basic:
//1. Coroutines helps to implement asynchronous, non blocking code.
//2. For this we use suspend function.
//3. Either you fire and forget using launch or wait for data(i.e single object) using async.
// ex:  suspend fun deleteUser(){
//        CoroutineScope(Dispatchers.IO).launch {
//            //network call
//       }
//   }

// ex:  suspend fun deleteUser():User{
//        val user = CoroutineScope(Dispatchers.IO).async {
//            //network call
//        }
//       return user.await()
//    }

//4. Suspend function always returns a single objects.
//5. Normal application of suspend function:
//   Storing some value in db.
//   Network calls.
//   Doing task then returns single value.

//# But there are scenarios where you have streams of data. such as
//  Video streaming
//  FM radio
//  Mobile sending audio signal to bluetooth speakers.
//  Trade data
//  GPS data


//## Kotlin has asynchronous data stream support using channels and flows.
// a. Channels (Send and Receive).
// b. Flows (Emit and Collect) // is a better way to handle the stream of data asynchronously that executes sequentially.

//- Channels are hot. // Continuously produced data either data is consumed or not by consumer. //ex. radio channels, movie theater.
//- Flows are mostly cold. //produce data only if data is consumed. //ex. Netflix

//* Cold streams are preferred over hot streams.
//* Cold stream should have at least one consumer.
//* Hot streams waste resources.
//* Hot streams needs to be manual close.

//USE CASE:
//- It might be possible producer can be faster than consumer, that is overwhelming of data.
//- bottleneck comes for consumer.
//- To solve this problem we use buffer.
//- And vice-versa

//-Old approach, if producer produce the data fast and consumer unable to consume then block producer thread.
//-Similarly, If consumer consumes the data fast and producer unable to produce the data then block the consumer thread.

//New approach for above case, we do not block the thread in kotlin, we use coroutine and suspend the producer or consumer coroutine.

//# To implement streams, we follow below flows APIs:
// 1. Produces Consumers
// 2. Bottleneck (there is no bottleneck in producer and consumer)
// 3. Asynchronous (data should produce asynchronously)
// 4. Cold (data stream is cold)