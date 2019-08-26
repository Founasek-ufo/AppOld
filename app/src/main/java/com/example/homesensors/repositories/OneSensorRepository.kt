package com.example.homesensors.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.homesensors.`interface`.onDataReadeListener
import com.example.homesensors.entities.temperatureSensor
import com.example.homesensors.services.OneSensorService

class OneSensorRepository(application: Application, sensorID: String, dataReadyListener: onDataReadeListener) {

    private var oneValue: LiveData<temperatureSensor>

    private var oneSensorService: OneSensorService

    var application: Application
    var sensorID: String
    var dataReadyListener: onDataReadeListener


    init {
        this.application = application
        this.sensorID = sensorID
        this.dataReadyListener = dataReadyListener


        oneSensorService = OneSensorService(application, sensorID, dataReadyListener)

        oneValue = oneSensorService.getOneSensors()
    }

    fun getOneSenosr(): LiveData<temperatureSensor> {
        return oneValue
    }

    fun registrationListener() {
        oneSensorService.registrationListener()
    }

    fun unRegistrationListener() {
        oneSensorService.unRegistrationListener()
    }
}