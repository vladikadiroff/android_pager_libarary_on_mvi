package ru.vladikadiroff.pagination.utils.extensions

import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment

fun View.doOnApplyWindowInsets(block: (View, WindowInsetsCompat, Rect) -> WindowInsetsCompat) {
    val initialPadding = recordInitialPaddingForView(this)
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        block(v, insets, initialPadding)
    }
    requestApplyInsetsWhenAttached()
}

fun Fragment.hideSystemUI() {
    hideSystemUI(requireActivity().window)
}

fun Fragment.showSystemUI() {
    showSystemUI(requireActivity().window, requireView())
}

fun Fragment.setLightStatusBar(isLightStatusBar: Boolean) {
    setLightStatusBar(isLightStatusBar, requireActivity().window)
}

fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        requestApplyInsets()
    } else {
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                v.requestApplyInsets()
            }
            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}

private fun recordInitialPaddingForView(view: View) =
    Rect(view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom)


private fun showSystemUI(window: Window, root: View) {
    WindowCompat.setDecorFitsSystemWindows(window, true)
    WindowInsetsControllerCompat(window, root).show(WindowInsetsCompat.Type.statusBars())
    WindowInsetsControllerCompat(window, root).show(WindowInsetsCompat.Type.navigationBars())
}

private fun hideSystemUI(window: Window) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.setDecorFitsSystemWindows(false)
        window.insetsController?.let { controller ->
            controller.hide(WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars())
            controller.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    } else {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
    }
}

@Suppress("DEPRECATION")
private fun setLightStatusBar(isLightStatusBar: Boolean, window: Window) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val systemUiAppearance =
            if (isLightStatusBar) WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS else 0
        window.insetsController?.setSystemBarsAppearance(
            systemUiAppearance,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
    } else {
        val systemUiVisibilityFlags = if (isLightStatusBar)
            window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        else window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        window.decorView.systemUiVisibility = systemUiVisibilityFlags
    }
}