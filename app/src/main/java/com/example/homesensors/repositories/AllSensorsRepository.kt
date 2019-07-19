package com.example.homesensors.repositories

import androidx.lifecycle.LiveData
import com.example.homesensors.entities.temperatureSensor
import com.example.homesensors.services.AllSensorsService

class AllSensorsRepository {

    private var allSensorsValue: LiveData<List<temperatureSensor>>

    private var allSensorsService: AllSensorsService = AllSensorsService()

    init {
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