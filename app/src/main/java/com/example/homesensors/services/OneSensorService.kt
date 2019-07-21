package com.example.homesensors.services

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.homesensors.entities.temperatureSensor
import kotlin.math.round

class OneSensorService {

    private val TAG = "OneSensorService"
    private var oneSensor: MutableLiveData<temperatureSensor> = MutableLiveData()
    val trySensor: temperatureSensor = temperatureSensor(12.0, false, "ddd")

    init {
        oneSensor.value = trySensor
    }

    //TODO pouze pro prezentaci
    private fun startCountDownTimer() {
        val mCountDownTimer: CountDownTimer = object : CountDownTimer(50000, 1000) {
            override fun onTick(mills: Long) {

                if ((mills > 40000 && mills < 45000) || (mills > 20000 && mills < 30000)) {
                    trySensor.setSynsState(false)
                } else {
                    trySensor.setSynsState(true)
                    trySensor.setCurrentTime()
                    trySensor.setValue(round(mills.toDouble() / 1000))
                }


                this@OneSensorService.oneSensor.value = trySensor
                Log.d(TAG, "onTick time: " + mills.toDouble() / 1000 + " state: " + trySensor.getSynsState())
            }

            override fun onFinish() {
            }

        }

        mCountDownTimer.start()
    }

    fun getOneSensors(): LiveData<temperatureSensor> {
        return oneSensor
    }

    fun registrationListener() {
        Log.d(TAG, "registrationListener")
        startCountDownTimer()
    }

    fun unRegistrationListener() {
        Log.d(TAG, "unRegistrationListener")
    }
}