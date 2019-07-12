package com.example.homesensors.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.homesensors.entities.temperatureSensor
import com.example.homesensors.repositories.OneSensorRepository

class OneSensorViewModel : ViewModel() {

    private val oneSensorRepository: OneSensorRepository = OneSensorRepository()
    private var oneValue: LiveData<temperatureSensor>

    init {
        oneValue = this.oneSensorRepository.getOneSenosr()
        registrationListener()
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