package ru.vladikadiroff.pagination.ui.adapters

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import ru.vladikadiroff.pagination.R
import ru.vladikadiroff.pagination.databinding.ItemLoadingFooterBinding
import ru.vladikadiroff.pagination.utils.abstracts.BaseViewHolder
import ru.vladikadiroff.pagination.ui.adapters.LoadStateAdapter.LoadingFooterViewHolder

class LoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadingFooterViewHolder>() {

    override fun onBindViewHolder(holder: LoadingFooterViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        LoadingFooterViewHolder(parent, retry)

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

}