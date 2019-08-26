package com.example.homesensors.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.homesensors.`interface`.onDataReadeListener
import com.example.homesensors.entities.temperatureSensor
import com.example.homesensors.repositories.OneSensorRepository

class OneSensorViewModel(application: Application, sensorID:String,dataReadyListener: onDataReadeListener) : ViewModel() {

    private val oneSensorRepository: OneSensorRepository
    private var oneValue: LiveData<temperatureSensor>
    var dataReadyListener: onDataReadeListener


    var application:Application
    var sensorID: String

    init {
        this.application = application
        this.sensorID = sensorID
        this.dataReadyListener = dataReadyListener


        oneSensorRepository = OneSensorRepository(application,sensorID,dataReadyListener)

        registrationListener()

        oneValue = this.oneSensorRepository.getOneSenosr()
    }

    fun getOneValue(): LiveData<temperatureSensor>{
        return oneValue
    }

    private fun registrationListener(){
        oneSensorRepository.registrationListener()
    }

    override fun onCleared() {
        super.onCleared()
        oneSensorRepository.unRegistrationListener()
    }
}