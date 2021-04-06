package ru.vladikadiroff.pagination.utils.helpers

import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.facebook.shimmer.ShimmerFrameLayout

class ShimmerWithLifecycleHandler(private val shimmer: ShimmerFrameLayout) :
    LifecycleObserver {

    var isVisible: Boolean
        get() = shimmer.isVisible
        set(visibitity) {
            shimmer.isVisible = visibitity
            if (visibitity) startAnimation()
            else stopAnimation()
        }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun stopAnimation() {
        shimmer.stopShimmerAnimation()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun startAnimation() {
        if (isVisible) shimmer.startShimmerAnimation()
    }

}

fun ShimmerFrameLayout.withLifecycleHandler(lifecycleOwner: LifecycleOwner):
        ShimmerWithLifecycleHandler {
    val shimmer = ShimmerWithLifecycleHandler(this)
    lifecycleOwner.lifecycle.addObserver(shimmer)
    return shimmer
}
