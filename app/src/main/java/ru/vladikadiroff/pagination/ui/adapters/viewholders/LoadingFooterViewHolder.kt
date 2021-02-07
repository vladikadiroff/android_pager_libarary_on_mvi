package ru.vladikadiroff.pagination.ui.adapters.viewholders

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import ru.vladikadiroff.pagination.R
import ru.vladikadiroff.pagination.databinding.ItemLoadingFooterBinding
import ru.vladikadiroff.pagination.utils.abstracts.BaseViewHolder

class LoadingFooterViewHolder(parent: ViewGroup, retry: () -> Unit) :
    BaseViewHolder(R.layout.item_loading_footer, parent) {

    private val binding = ItemLoadingFooterBinding.bind(itemView)
    private val footerLoading = binding.footerLoading
    private val footerError = binding.footerError.also { it.setOnClickListener { retry() } }

    fun bind(loadState: LoadState) {
        footerError.isVisible = loadState is LoadState.Error
        footerLoading.isVisible = loadState is LoadState.Loading
    }

}