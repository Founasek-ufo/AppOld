package com.example.homesensors.services

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.homesensors.entities.temperatureSensor
import com.example.homesensors.handlers.DatabaseHandler

class OneSensorService {

    private val dbHandler: DatabaseHandler = DatabaseHandler()

    private val TAG = "OneSensorService"
    private var oneSensor: MutableLiveData<temperatureSensor> = MutableLiveData()
    private val trySensor: temperatureSensor = temperatureSensor(12.0, false, "ddd")

    init {
        oneSensor.value = trySensor
    }

    fun getOneSensors(): LiveData<temperatureSensor> {
        return oneSensor
    }

    fun registrationListener() {
        Log.d(TAG, "registrationListener")
    }

    fun unRegistrationListener() {
        Log.d(TAG, "unRegistrationListener")
    }
}