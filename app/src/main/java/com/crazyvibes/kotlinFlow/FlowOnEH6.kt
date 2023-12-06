package com.crazyvibes.kotlinFlow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.crazyvibes.KotlinFlow.R
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class FlowOnEH6 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow_on_eh)

        //consumer
        GlobalScope.launch(Dispatchers.Main) {
            val data: Flow<Int> = producer()
            data
                .map {
                    Log.d("Data", "Collector thread - ${Thread.currentThread().name}")
                    it * 2
                }
                .flowOn(Dispatchers.IO) //the above code after data var will be execute on IO thread (e.g. map operator). And below code will be execute on Main thread (e.g. collect).
                .filter {
                    Log.d("Data", "Collector thread - ${Thread.currentThread().name}")
                    it < 8
                }
                .flowOn(Dispatchers.Default) // we can add multiple flowOn
                .collect {
                Log.d("Data", "Collector thread - ${Thread.currentThread().name}") //main thread
            }
        }


        //consumer2
        GlobalScope.launch(Dispatchers.Main) {
            val data: Flow<Int> = producer()
            try {
                data
                    .collect {
                        Log.d("Data", "Collector thread - ${Thread.currentThread().name}") //main thread
                    }
            }catch (e:Exception){
                e.printStackTrace()
            }

        }
    }

    //producer
    private fun producer() : Flow<Int> {
        return flow<Int>{
            withContext(Dispatchers.IO) {
                val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                list.forEach {
                    delay(1000)
                    emit(it) //passing data in stream one by one
                    Log.d("Data", "Producer thread - ${Thread.currentThread().name}") //main thread
                }
            }
        }.catch {
            //to through exception
            Log.d("Producer", "Emitter catch- ${it.message}")
            //we can also emit some additional value here
            emit(-1)
        }

    }

    //-coroutine context or thread will be same for both producer coroutine what we declare for consumer-
    //but there is a requirement to collect data on main thread and produce data on background thread
    //to change thread for producer we use withContext.
    //-for changing context or thread that is collect on main thread or produce on another thread-
    //we need to tell flow by using flowOn otherwise it assumes by default same context/thread for both and through error.


    //Exception Handling: there are two places where we can get exception, in consumer or in producer.

}