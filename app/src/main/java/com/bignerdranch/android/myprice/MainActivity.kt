package com.bignerdranch.android.myprice

import android.app.Service
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.bignerdranch.android.myprice.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.asin

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.myRecyclerView

//        val asinList = listOf("B08ZFPQGK5", "B0B2N5GHMG", "B076Z6SDGR", "B0B52731W9",
//            "B00OO77BL6", "B07K77SH7F", "B0829QQHCS")
        val asinList = listOf("B08ZFPQGK5", "B076Z6SDGR")
        val myasin1 = "B08ZFPQGK5"
        val myasin2 = "B076Z6SDGR"


        val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
        val myapi = "7E7D26AB23B04E929FEEE6151E907080"
        val mytype = "product"
        val mydomain = "amazon.com"

        val apiResponseList = mutableListOf<Response<ApiResponse>>()
        for (myasin in asinList) {
            val call = serviceGenerator.getPosts(myapi, mytype, mydomain, myasin)
            call.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(
                    call: Call<ApiResponse>,
                    response: Response<ApiResponse>
                ) {
                    if (response.isSuccessful) {
                        apiResponseList.add(response)
                    }

                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = PostAdapter(apiResponseList)
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    t.printStackTrace()
                    Log.e("Failure", t.message.toString())
                }
            })
        }

    }
}
