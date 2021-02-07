package ru.vladikadiroff.pagination.utils.extensions

import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.transition.Transition
import androidx.transition.TransitionListenerAdapter
import androidx.transition.Visibility
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import ru.vladikadiroff.pagination.R
import ru.vladikadiroff.pagination.utils.helpers.ShimmerWithLifecycleHandler

fun Drawable.startVectorAnimation() {
    when (this) {
        is AnimatedVectorDrawable -> start()
        is AnimatedVectorDrawableCompat -> start()
        else -> return
    }
}



fun View.setVisibleWithFadeAnimation(visibility: Boolean = true) {
    resources.getInteger(R.integer.reply_motion_fade_in)
    if (visibility) {
        isVisible = visibility
        animate()
            .alpha(1f)
            .setDuration(resources.getInteger(R.integer.reply_motion_fade_in).toLong())
            .setInterpolator(DecelerateInterpolator())
            .start()
    } else animate()
            .alpha(0f)
            .setDuration(resources.getInteger(R.integer.reply_motion_fade_in).toLong())
            .setInterpolator(AccelerateInterpolator())
            .withEndAction { isVisible = visibility }
            .start()
}





