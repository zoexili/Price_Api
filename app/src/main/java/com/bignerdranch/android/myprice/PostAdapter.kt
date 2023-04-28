package com.bignerdranch.android.myprice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.myprice.databinding.CardPostBinding
import retrofit2.Response

class PostAdapter(val apiResponse: MutableList<Response<ApiResponse>>) : RecyclerView.Adapter<PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        return holder.bindView(apiResponse[position])
    }

    override fun getItemCount(): Int {
        return apiResponse.size
    }
}

class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private lateinit var binding: CardPostBinding

    fun bindView(apiResponse: Response<ApiResponse>) {
        binding.url.text = apiResponse.body()?.requestMetadata?.amazonUrl
        binding.product.text = apiResponse.body()?.product?.title
        binding.price.text =
            apiResponse.body()?.product?.buyboxWinner?.subscribeAndSave?.basePrice?.raw
//        val variants = apiResponse.body()?.product?.variants
//        if (variants != null) {
//            binding.image.text = variants[0].mainImage
//        }
    }
}