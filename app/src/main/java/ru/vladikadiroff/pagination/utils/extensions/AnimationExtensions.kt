package ru.vladikadiroff.pagination.utils.extensions

import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.view.isVisible
import androidx.transition.Transition
import androidx.transition.TransitionListenerAdapter
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.google.android.material.transition.MaterialContainerTransform
import ru.vladikadiroff.pagination.R

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

inline fun MaterialContainerTransform.doOnTransitionAnimationEnd(crossinline action: () -> Unit) {
    addListener(object : TransitionListenerAdapter() {
        override fun onTransitionEnd(transition: Transition) {
            try {
                action.invoke()
            } catch (e: Exception) {
                Log.e("Crutch onTransitionEnd", e.toString())
            }
        }
    })
}




