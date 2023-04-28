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

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.myRecyclerView

        val asinList = listOf("B08ZFPQGK5", "B0B2N5GHMG", "")
        val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
        val myapi = "7E7D26AB23B04E929FEEE6151E907080"
        val mytype = "product"
        val mydomain = "amazon.com"
        val myasin = "B08ZFPQGK5"

        val call = serviceGenerator.getPosts(myapi, mytype, mydomain, myasin)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                if (response.isSuccessful) {
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = PostAdapter(response.body())
                    }



                    val url = response.body()?.requestMetadata?.amazonUrl
                    val food = response.body()?.product?.title
                    val price =
                        response.body()?.product?.buyboxWinner?.subscribeAndSave?.basePrice?.raw
                    val variants = response.body()?.product?.variants
                    if (variants != null) {
                        val imageUrl = variants[0].mainImage
                        binding.image.text = imageUrl
                    }
                    binding.product.text = food
                    binding.price.text = price
                    binding.url.text = url
                    Log.e("Success", response.body().toString())
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                t.printStackTrace()
                Log.e("Failure", t.message.toString())
            }
        })
    }
}