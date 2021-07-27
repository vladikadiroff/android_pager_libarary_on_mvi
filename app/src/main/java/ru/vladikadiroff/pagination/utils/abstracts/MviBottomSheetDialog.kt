package ru.vladikadiroff.pagination.utils.abstracts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.onEach
import ru.vladikadiroff.pagination.R
import ru.vladikadiroff.pagination.utils.extensions.launchWhenStarted
import ru.vladikadiroff.pagination.utils.helpers.onSingleEvent

abstract class MviBottomSheetDialog <VB : ViewBinding, VS, VA, VE, VM : MviViewModel<VS, VA, VE>> :
    BottomSheetDialogFragment() {

    protected abstract val viewModel: VM

    private var _binding : ViewBinding? = null
    protected abstract val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = viewBindingInflater(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDialog()
        viewModel.obtainViewState.onEach(::render).launchWhenStarted(lifecycle, lifecycleScope)
        viewModel.obtainViewAction.onSingleEvent(::renderAction)
            .launchWhenStarted(lifecycle, lifecycleScope)
    }

    open fun initDialog() {}

    protected fun postEvent(event: VE) = viewModel.obtainEvent(event)

    protected abstract fun render(state: VS)

    protected abstract fun renderAction(action: VA)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}