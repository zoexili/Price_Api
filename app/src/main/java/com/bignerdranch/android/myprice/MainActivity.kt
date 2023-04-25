package com.bignerdranch.android.myprice

import android.app.Service
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bignerdranch.android.myprice.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
        val myapi = "7E7D26AB23B04E929FEEE6151E907080"
        val mytype = "product"
        val mydomain = "amazon.com"
        val myasin = "B073JYC4XM"

        binding.submitButton.setOnClickListener {
            Log.e("submit_button", "submit button clicked")
            val call = serviceGenerator.getPosts(myapi, mytype, mydomain, myasin)
            call.enqueue(object : Callback<MutableList<PostModel>> {
                override fun onResponse(
                    call: Call<MutableList<PostModel>>,
                    response: Response<MutableList<PostModel>>
                ) {
                    if (response.isSuccessful) {
                        Log.e("Success", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<MutableList<PostModel>>, t: Throwable) {
                    t.printStackTrace()
                    Log.e("Failure", t.message.toString())
                }
            })
        }
    }

    fun showPrice() {
        binding.submitButton.setOnClickListener{ view: View ->
            if (binding.apple.text.equals("Apple")) {
                binding.price.text = "5"
            }
        }
    }


}