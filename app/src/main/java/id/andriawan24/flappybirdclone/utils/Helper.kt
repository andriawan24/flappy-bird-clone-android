package id.andriawan24.flappybirdclone.utils

import android.app.Activity
import android.content.res.Resources
import android.util.Log
import android.view.View

object LogHelper {
    fun log(tag: String = "FlappyBirdClone", message: String) {
        Log.d(tag, message)
    }
}

object DensityUtil {
    fun dxToDp(resources: Resources, px: Int): Int =
        (px / resources.displayMetrics.density + 0.5f).toInt()
}

fun Activity.findRootView(): View {
    return window.decorView.findViewById(android.R.id.content)
}
