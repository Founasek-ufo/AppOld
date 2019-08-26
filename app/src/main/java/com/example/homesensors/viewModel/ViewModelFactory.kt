package com.example.homesensors.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.homesensors.`interface`.onDataReadeListener
import com.example.homesensors.activities.TemperatureActivity

class ViewModelFactory(application: Application, sensorID: String, dataReadyListener: onDataReadeListener) : ViewModelProvider.Factory {

    var application: Application
    var sensorID: String
    var dataReadyListener: onDataReadeListener

    init {
        this.application = application
        this.sensorID = sensorID
        this.dataReadyListener = dataReadyListener
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == TemperatureActivity::class.java) {
            return OneSensorViewModel(application, sensorID,dataReadyListener) as T
        } else {
            return AllSensorsViewModel(application, sensorID, dataReadyListener) as T

        }
    }
}