package ru.vladikadiroff.pagination.utils.abstracts

import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding

abstract class MviFragment<VB : ViewBinding, VS, VA, VE, VM : MviViewModel<VS, VA, VE>> :
    ViewBindingFragment<VB>() {

    protected abstract val viewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.attachToNewInstance()
        viewModel.obtainViewState.observe(viewLifecycleOwner, ::render)
        viewModel.obtainViewAction.observe(viewLifecycleOwner, ::renderAction)
        initFragment()
    }

    protected fun postEvent(event: VE) = viewModel.obtainEvent(event)

    protected abstract fun render(state: VS)

    protected abstract fun renderAction(action: VA)

}