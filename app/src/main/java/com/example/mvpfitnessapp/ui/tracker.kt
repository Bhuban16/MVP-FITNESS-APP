package com.example.mvpfitnessapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.mvpfitnessapp.R
import com.example.mvpfitnessapp.callback.stepsCallback
import com.example.mvpfitnessapp.helper.GeneralHelper
import com.example.mvpfitnessapp.helper.PrefsHelper
import com.example.mvpfitnessapp.service.StepDetectorService
import kotlinx.android.synthetic.main.activity_tracker.*


class tracker : AppCompatActivity(), stepsCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracker)

        val intent = Intent(this, StepDetectorService::class.java)
        startService(intent)

        StepDetectorService.subscribe.register(this)

        textReset.setOnClickListener{
            PrefsHelper.putString("TodayDate" , "")
            PrefsHelper.putInt("Steps" , 0)
            PrefsHelper.putInt("FSteps" , 0)
            subscribeSteps(0)
        }
    }

    override fun subscribeSteps(steps: Int) {
        TV_STEPS.setText(steps.toString())
        TV_CALORIES.setText(GeneralHelper.getCalories(steps))
        TV_DISTANCE.setText(GeneralHelper.getDistanceCovered(steps))
    }
}