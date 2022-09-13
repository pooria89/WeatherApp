package com.app.utils.ext

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Fragment.safeNavigate(@IdRes resId: Int) = view?.post {
    if (!isAdded) return@post
    val navController = findNavController()
    val destination = navController.currentDestination?.getAction(resId)?.destinationId
        ?: return@post
    navController.navigate(resId = destination)
}
