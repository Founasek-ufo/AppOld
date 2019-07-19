package com.example.homesensors.services

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.homesensors.entities.temperatureSensor

class AllSensorsService {

    private val TAG = "OneSensorService"
    private val allSensors: MutableLiveData<List<temperatureSensor>> = MutableLiveData<List<temperatureSensor>>()
    val trySensor1: temperatureSensor = temperatureSensor(1.0, false, "temp")
    val trySensor2: temperatureSensor = temperatureSensor(19.0, false, "humd")
    val trySensor3: temperatureSensor = temperatureSensor(89.0, false, "humd")

    init {
        val sensoreValue: MutableList<temperatureSensor> = ArrayList<temperatureSensor>();
        sensoreValue.add(trySensor1)
        sensoreValue.add(trySensor2)
        sensoreValue.add(trySensor3)
        this@AllSensorsService.allSensors.value = sensoreValue
    }

    fun getAllSensors(): LiveData<List<temperatureSensor>> {
        return allSensors
    }

    fun registrationListener() {
        Log.d(TAG, "registrationListener")
//        startCountDownTimer()
    }

    fun unRegistrationListener() {
        Log.d(TAG, "unRegistrationListener")
    }
}


