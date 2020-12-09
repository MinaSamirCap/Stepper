package com.beshoy.myapplication.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import com.beshoy.myapplication.R
import com.beshoy.myapplication.databinding.WidgetStepperBinding

class StepperWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var stepsNumber = 0
    private lateinit var stepsList: MutableList<StepperUiModel>
    private lateinit var binding: WidgetStepperBinding
    private lateinit var adapter: StepperAdapter

    init {
        initView(context)
        obtainStyledAttributes(context, attrs, defStyleAttr)
    }

    private fun obtainStyledAttributes(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        context.withStyledAttributes(
            set = attrs,
            attrs = R.styleable.StepperView,
            defStyleAttr = defStyleAttr
        ) {
            getString(R.styleable.StepperView_progressHint)?.let { hint ->
                setProgressHint(hint)
            }
        }
    }

    private fun initView(context: Context) {
        val layoutInflater = LayoutInflater.from(context)
        binding = WidgetStepperBinding.inflate(layoutInflater, this, true)

        adapter = StepperAdapter()
        binding.recyclerView.adapter = adapter
    }

    private fun calculateItemWidth(screenWidth: Int): Int {
        return screenWidth / stepsList.size
    }

    fun setStepperAsCompleted() {
        stepsList.forEach {
            it.isCompleted = true
        }
        adapter.notifyDataSetChanged()
        setCompleteHint()
    }

    fun setStepsCount(numbers: Int) {
        stepsNumber = numbers
        createStepsList()
    }

    fun setActiveStep(activePosition: Int) {
        val steps = stepsList.mapIndexed { index, stepperUiModel ->
            stepperUiModel.copy(isCompleted = index <= activePosition)
        }
        stepsList = steps.toMutableList()
        adapter.submitList(steps)
        setCompleteHint()
        setProgress(adapter.itemWidth.times(activePosition + 1))
    }

    private fun setProgress(progress: Int) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            binding.progress.setProgress(progress, true)
        } else binding.progress.progress = progress
    }

    fun setProgressHint(hint: String) {
        binding.stepperHintLabel.text = hint
    }

    private fun setCompleteHint() {
        binding.stepperCompleteHintLabel.text = context.getString(
            R.string.plan_complete_hint,
            stepsList.filter { it.isCompleted }.size,
            stepsList.size
        )
    }

    private fun createStepsList() {
        stepsList = mutableListOf()

        for (position in 0 until stepsNumber) {
            stepsList.add(
                StepperUiModel(
                    stepNum = (position + 1).toString(),
                    isCompleted = false
                )
            )
        }

        afterMeasured {
            val screenWidth = binding.recyclerView.measuredWidth
            binding.progress.max = screenWidth
            val itemWidth = calculateItemWidth(screenWidth)
            adapter.setItemWidth(itemWidth, stepsList.size)
            adapter.submitList(stepsList)
        }
    }

}