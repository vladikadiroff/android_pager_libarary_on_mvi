package ru.vladikadiroff.pagination.utils.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun <T> Flow<T>.launchWhenStarted(lifecycle: Lifecycle, lifecycleScope: LifecycleCoroutineScope) =
    lifecycleScope.launch {
        flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
        collect()
    }
