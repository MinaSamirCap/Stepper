package com.beshoy.myapplication.widget

import com.beshoy.myapplication.R

data class StepperUiModel(val stepNum: String, var isCompleted: Boolean = false) {
    val backgroundResId
        get() = if (isCompleted) {
            R.drawable.ic_baseline_check_circle_24
        } else {
            R.drawable.ic_baseline_check_circle_outline_24
        }

    val lineColorResId
        get() = if (isCompleted) {
            R.color.purple_700
        } else {
            R.color.grey
        }
}