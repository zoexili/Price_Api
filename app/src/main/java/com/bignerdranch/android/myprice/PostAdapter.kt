package com.bignerdranch.android.myprice

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.myprice.databinding.CardPostBinding
import com.squareup.picasso.Picasso
import retrofit2.Response

class PostAdapter(private val apiResponseList: MutableList<Response<ApiResponse>>) : RecyclerView.Adapter<PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardPostBinding.inflate(inflater, parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        return holder.bind(apiResponseList[position])
    }

    override fun getItemCount(): Int {
        return apiResponseList.size
    }
}

class PostViewHolder(private val binding: CardPostBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(apiResponse: Response<ApiResponse>) {
        val myUrl = apiResponse.body()?.requestMetadata?.amazonUrl
        binding.product.text = apiResponse.body()?.product?.title
        binding.price.text =
            apiResponse.body()?.product?.buyboxWinner?.subscribeAndSave?.basePrice?.raw
        val variants = apiResponse.body()?.product?.variants
        if (variants != null) {
            Picasso.get().load(variants[0].mainImage).resize(600, 600).into(binding.imageView)
        }
        binding.urlButton.setOnClickListener {
//            val openURL = Intent(Intent.ACTION_VIEW)
//            openURL.data = Uri.parse(myUrl.toString())
//
//            binding.root.context.startActivity(openURL)
            val openURL = Intent(binding.root.context, WebviewActivity::class.java)
            openURL.putExtra("url", myUrl.toString())
            binding.root.context.startActivity(openURL)

        }
    }
}