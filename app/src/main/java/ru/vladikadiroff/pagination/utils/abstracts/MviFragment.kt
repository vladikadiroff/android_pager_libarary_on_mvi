package ru.vladikadiroff.pagination.utils.abstracts

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.onEach
import ru.vladikadiroff.pagination.utils.extensions.launchWhenStarted
import ru.vladikadiroff.pagination.utils.helpers.onSingleEvent

abstract class MviFragment<VB : ViewBinding, VS, VA, VE, VM : MviViewModel<VS, VA, VE>> :
    ViewBindingFragment<VB>() {

    protected abstract val viewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initFragment()
        viewModel.obtainViewState.onEach(::render).launchWhenStarted(lifecycle, lifecycleScope)
        viewModel.obtainViewAction.onSingleEvent(::renderAction)
            .launchWhenStarted(lifecycle, lifecycleScope)
    }

    protected fun postEvent(event: VE) = viewModel.obtainEvent(event)

    protected abstract fun render(state: VS)

    protected abstract fun renderAction(action: VA)

}