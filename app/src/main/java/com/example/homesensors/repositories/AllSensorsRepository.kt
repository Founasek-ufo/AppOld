package com.example.homesensors.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.homesensors.`interface`.onDataReadeListener
import com.example.homesensors.entities.temperatureSensor
import com.example.homesensors.services.AllSensorsService

class AllSensorsRepository(application: Application, sensorID: String, dataReadyListener: onDataReadeListener) {

    private var allSensorsValue: LiveData<List<temperatureSensor>>

    private var allSensorsService: AllSensorsService

    var application: Application
    var sensorID: String
    var dataReadyListener: onDataReadeListener

    init {
        this.application = application
        this.sensorID = sensorID
        this.dataReadyListener = dataReadyListener

        allSensorsService = AllSensorsService(application, sensorID, dataReadyListener)

        allSensorsValue = allSensorsService.getAllSensors()
    }

    fun getAllSenosrs(): LiveData<List<temperatureSensor>>{
        return allSensorsValue
    }

    fun registrationListener(){
        allSensorsService.registrationListener()
    }

    fun unRegistrationListener(){
        allSensorsService.unRegistrationListener()
    }
}