package com.app.utils.ext

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible

const val ANIMATION_DURATION = 350L


fun View.fadeIn(
    listener: Animator.AnimatorListener? = null,
    duration: Long = ANIMATION_DURATION
) {
    if (isVisible) return
    isVisible = true
    alpha = 0f
    animate()
        .setDuration(duration)
        .alpha(1f)
        .setListener(listener)
        .start()
}

fun View.fadeOut(
    listener: Animator.AnimatorListener? = null,
    invisibleView: Boolean = false,
    duration: Long = ANIMATION_DURATION
) {
    if (!isVisible) return
    val mListener = object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            if (invisibleView) invisible() else hide()
        }
    }
    alpha = 1f
    animate()
        .setDuration(duration)
        .alpha(0f)
        .setListener(listener ?: mListener)
        .start()
}

fun View.invisible() {
    isInvisible = true
    alpha = 0f
}

fun View.hide() {
    isGone = true
    alpha = 0f
}