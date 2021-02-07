package ru.vladikadiroff.pagination.ui.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import ru.vladikadiroff.pagination.ui.adapters.viewholders.LoadingFooterViewHolder

class LoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadingFooterViewHolder>() {

    override fun onBindViewHolder(holder: LoadingFooterViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
            LoadingFooterViewHolder(parent, retry)

}