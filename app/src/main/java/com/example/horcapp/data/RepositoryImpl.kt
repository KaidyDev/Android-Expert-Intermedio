package com.example.horcapp.data

import android.util.Log
import com.example.horcapp.data.network.HoroscopeApiService
import com.example.horcapp.domain.Model.PredictionModel
import com.example.horcapp.domain.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: HoroscopeApiService) : Repository {

    override suspend fun getPrediction(sign: String): PredictionModel? {
        runCatching { apiService.getHoroscope(sign) }
            .onSuccess { return it.toDomain() }
            .onFailure { Log.i("aris", "Ha ocurrido un error ${it.message}") }
        return null
    }
}