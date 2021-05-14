package com.example.hilt.scene.productdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hilt.databinding.ItemRatingBinding
import com.example.hilt.internal.ext.addStar

class ReviewAdapter :
    RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    private var reviewList: List<ReviewUIModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ReviewViewHolder(ItemRatingBinding.inflate(layoutInflater))
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val uiModel = reviewList?.get(position)
        holder.bind(uiModel ?: return)
    }

    override fun getItemCount(): Int = reviewList?.size ?: 0

    fun updateProductList(uiModelList: List<ReviewUIModel>) {
        reviewList = uiModelList
        notifyDataSetChanged()
    }

    inner class ReviewViewHolder(private val binding: ItemRatingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(uiModel: ReviewUIModel) {
            with(binding) {
                textViewComment.text = uiModel.comment
                linearLayoutStars.addStar(uiModel.rating)
            }
        }
    }
}