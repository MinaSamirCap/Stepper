package com.beshoy.myapplication.widget

import android.view.View
import android.view.ViewTreeObserver
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.imageview.ShapeableImageView

inline fun View.afterMeasured(crossinline afterMeasureCallback: View.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                afterMeasureCallback()
            }
        }
    })
}

@BindingAdapter("image_src")
fun setImgToIv(iv: ShapeableImageView, @DrawableRes res: Int) {
    iv.setImageResource(res)
}

@BindingAdapter("view_bg")
fun setViewBg(view: View, @ColorRes res: Int) {
    view.setBackgroundColor(ContextCompat.getColor(view.context, res))
}