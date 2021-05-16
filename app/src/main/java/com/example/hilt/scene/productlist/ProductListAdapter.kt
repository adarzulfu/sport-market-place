package com.example.hilt.scene.productlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hilt.databinding.ItemProductBinding
import com.example.hilt.internal.ext.loadImage
import com.example.hilt.scene.uimodel.ProductUIModel

class ProductListAdapter(val clickListener: ProductItemClickListener) :
    RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    private var productList: List<ProductUIModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductViewHolder(ItemProductBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val uiModel = productList?.get(position)
        holder.bind(uiModel ?: return)
    }

    override fun getItemCount(): Int = productList?.size ?: 0

    fun updateProductList(uiModelList: List<ProductUIModel>) {
        productList = uiModelList
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(uiModel: ProductUIModel) {
            with(binding) {
                imageViewPreview.loadImage(uiModel.imageUrl)
                textViewProductDescription.text = uiModel.description
                textViewProductName.text = uiModel.productName
                textViewProductPrice.text = uiModel.price
                imageViewPreview.transitionName = uiModel.id
                root.setOnClickListener {
                    clickListener.onItemSelected(imageViewPreview, uiModel)
                }
            }
        }
    }
}