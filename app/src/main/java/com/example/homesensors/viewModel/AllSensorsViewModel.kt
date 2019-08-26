package com.example.homesensors.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.homesensors.`interface`.onDataReadeListener
import com.example.homesensors.entities.temperatureSensor
import com.example.homesensors.repositories.AllSensorsRepository
import com.example.homesensors.repositories.OneSensorRepository

class AllSensorsViewModel(application: Application, sensorID:String, dataReadyListener: onDataReadeListener): ViewModel() {

    private val allSensorsRepository: AllSensorsRepository
    private var allSensorsValue: LiveData<List<temperatureSensor>>
    var dataReadyListener: onDataReadeListener

    var application:Application
    var sensorID: String

    init {
        this.application = application
        this.sensorID = sensorID
        this.dataReadyListener = dataReadyListener

        allSensorsRepository = AllSensorsRepository(application,sensorID,dataReadyListener)

        allSensorsValue = allSensorsRepository.getAllSenosrs()
        registrationListener()
    }

    fun getAllValues(): LiveData<List<temperatureSensor>> {
        return allSensorsValue
    }

    private fun registrationListener() {
        allSensorsRepository.registrationListener()
    }

    override fun onCleared() {
        super.onCleared()
        allSensorsRepository.unRegistrationListener()
    }
}