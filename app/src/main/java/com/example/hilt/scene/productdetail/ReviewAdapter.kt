package com.example.hilt.scene.productdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hilt.databinding.ItemNoReviewBinding
import com.example.hilt.databinding.ItemRatingBinding
import com.example.hilt.internal.ext.addStar
import com.example.hilt.scene.productdetail.ReviewAdapter.TYPE.*
import com.example.hilt.scene.uimodel.BaseReviewUIModel
import com.example.hilt.scene.uimodel.NoReviewUIModel
import com.example.hilt.scene.uimodel.ReviewUIModel

class ReviewAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var reviewList: List<BaseReviewUIModel>? = null

    override fun getItemViewType(position: Int): Int {
       return when(reviewList?.get(position)){
            is ReviewUIModel -> REVIEW.ordinal
            else -> EMPTY.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val type = values().find { it.ordinal == viewType }
        return when (type) {
            REVIEW -> ReviewViewHolder(
                ItemRatingBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )
            else -> NoReviewFoundHolder(ItemNoReviewBinding.inflate(layoutInflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val uiModel = reviewList?.get(position)
        when (holder) {
            is ReviewViewHolder -> holder.bind(uiModel as ReviewUIModel)
            is NoReviewFoundHolder -> holder.bind(uiModel as NoReviewUIModel)
        }
    }

    override fun getItemCount(): Int = reviewList?.size ?: 0

    fun updateProductList(uiModelList: List<BaseReviewUIModel>) {
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


    inner class NoReviewFoundHolder(private val binding: ItemNoReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(uiModel: NoReviewUIModel) {
            with(binding) {
                textViewNoReview.text = root.context.getString(uiModel.textId)
            }
        }
    }

    enum class TYPE {
        REVIEW,
        EMPTY
    }
}