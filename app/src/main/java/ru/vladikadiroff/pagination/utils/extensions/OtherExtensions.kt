package ru.vladikadiroff.pagination.utils.extensions

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.transition.Transition
import androidx.transition.TransitionListenerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.transition.MaterialContainerTransform
import ru.vladikadiroff.pagination.utils.helpers.ShimmerWithLifecycleHandler

fun Fragment.showToast(msg: String) = Toast.makeText(this.context, msg, Toast.LENGTH_LONG).show()

fun ImageView.withGlide(url: String, onLoad: (() -> Unit)? = null, onError: (() -> Unit)? = null) =
    Glide.with(this)
        .load(url)
        .listener(setGlideRequestListener(onLoad, onError))
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)

private fun setGlideRequestListener(onLoad: (() -> Unit)?, onError: (() -> Unit)?) =
    object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            onError?.invoke()
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            onLoad?.invoke()
            return false
        }
    }


fun ShimmerFrameLayout.withLifecycleHandler(lifecycleOwner: LifecycleOwner):
        ShimmerWithLifecycleHandler {
    val shimmer = ShimmerWithLifecycleHandler(this)
    lifecycleOwner.lifecycle.addObserver(shimmer)
    return shimmer
}

fun MaterialContainerTransform.doOnTransitionAnimationEnd(action: () -> Unit) {
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
