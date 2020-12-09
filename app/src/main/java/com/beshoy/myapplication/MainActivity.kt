package com.beshoy.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.beshoy.myapplication.widget.StepperWidget
import com.google.android.material.button.MaterialButton
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var stepperWidget: StepperWidget
    private var selectedIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupProgress()
        setupClicks()
    }

    private fun setupProgress() {
        stepperWidget = findViewById(R.id.stepper_widget)
        stepperWidget.setStepsCount(10)
    }

    private fun setupClicks() {
        findViewById<MaterialButton>(R.id.plus).setOnClickListener {
            selectedIndex++
            stepperWidget.setActiveStep(selectedIndex)
        }

        findViewById<MaterialButton>(R.id.minus).setOnClickListener {
            selectedIndex--
            stepperWidget.setActiveStep(selectedIndex)
        }
    }
}