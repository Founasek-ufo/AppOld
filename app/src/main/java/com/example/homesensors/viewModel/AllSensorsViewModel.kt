package com.example.homesensors.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.homesensors.entities.temperatureSensor
import com.example.homesensors.repositories.AllSensorsRepository

class AllSensorsViewModel : ViewModel() {

    private val allSensorsRepository: AllSensorsRepository = AllSensorsRepository()
    private var allSensorsValue: LiveData<List<temperatureSensor>>

    init {
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