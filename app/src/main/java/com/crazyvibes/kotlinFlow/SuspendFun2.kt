package com.crazyvibes.kotlinFlow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.crazyvibes.KotlinFlow.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SuspendFun2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_1)

        //consumer
        CoroutineScope(Dispatchers.Main).launch {
            getUserNames().forEach{
                Log.d("DATA", it)
            }
        }
    }

    //producer
    private suspend fun getUserNames():List<String> {
        val list = mutableListOf<String>()
        list.add(getUser(1))
        list.add(getUser(2))
        list.add(getUser(3))
        return list

        // this code block will be suspend state until list is completed.
    }

    private suspend fun getUser(id:Int): String {
        delay(1000)
        return "User$id"
    }

    // first list will complete and then it will return
}

// But we need to return user1 data when we get, wouldn't wait for all user's data.
// And this can be possible by using stream(channels and flow).