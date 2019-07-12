package com.example.homesensors.repositories

import androidx.lifecycle.LiveData
import com.example.homesensors.entities.temperatureSensor
import com.example.homesensors.services.OneSensorService

class OneSensorRepository {

    private var oneValue: LiveData<temperatureSensor>

    private var oneSensorService: OneSensorService = OneSensorService()

    init {
        oneValue = oneSensorService.getOneSensors()
    }

    fun getOneSenosr(): LiveData<temperatureSensor>{
        return oneValue
    }

    fun registrationListener(){
        oneSensorService.registrationListener()
    }

    fun unRegistrationListener(){
        oneSensorService.unRegistrationListener()
    }
}