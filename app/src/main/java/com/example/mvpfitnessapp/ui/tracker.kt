package com.example.mvpfitnessapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.mvpfitnessapp.R
import com.example.mvpfitnessapp.SecondActivity
import com.example.mvpfitnessapp.StepHistory
import com.example.mvpfitnessapp.UploadStep
import com.example.mvpfitnessapp.callback.stepsCallback
import com.example.mvpfitnessapp.helper.GeneralHelper
import com.example.mvpfitnessapp.helper.PrefsHelper
import com.example.mvpfitnessapp.service.StepDetectorService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_tracker.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class tracker : AppCompatActivity(), stepsCallback {
   private var message1 : String ?= null
    private var message2 : String ?= null
    private var message3 : String ?= null
    private var Date : String ?= null
    var upStep: UploadStep? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracker)
        upStep = UploadStep()
        val intent = Intent(this, StepDetectorService::class.java)
        startService(intent)

        StepDetectorService.subscribe.register(this)

        textReset.setOnClickListener{

            fun getDateToday(): String {
                val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
                val date = Date()
                return dateFormat.format(date)
            }



           message1 = TV_STEPS.text.toString()
            message2 = TV_CALORIES.text.toString()
            message3 = TV_DISTANCE.text.toString()

            val  firebaseAuth = FirebaseAuth.getInstance()
            val firebaseDatabase =
                FirebaseDatabase.getInstance("https://mvp-fitness-default-rtdb.asia-southeast1.firebasedatabase.app")
            val myRef = firebaseDatabase.getReference("User Step Record").child(firebaseAuth.uid!!)
            upStep!!.setUserSteps(message1!!)
            upStep!!.setUserCalories(message2!!)
            upStep!!.setUserDistance(message3!!)
            upStep!!.setUserDate(getDateToday())

            myRef.push().key?.let { it1 -> myRef.child(it1).setValue(upStep) }

            PrefsHelper.putString("TodayDate" , "")
            PrefsHelper.putInt("Steps" , 0)
            PrefsHelper.putInt("FSteps" , 0)
            subscribeSteps(0)


        }

        stop.setOnClickListener{
            val intent1 = Intent(this, StepDetectorService::class.java)
            stopService(intent1)
            startActivity(Intent(this, SecondActivity::class.java))

        }
    }


    override fun subscribeSteps(steps: Int) {
        TV_STEPS.setText(steps.toString())
        TV_CALORIES.setText(GeneralHelper.getCalories(steps))
        TV_DISTANCE.setText(GeneralHelper.getDistanceCovered(steps))
    }

}