package com.example.horcapp.domain

import com.example.horcapp.domain.Model.PredictionModel


interface Repository {
    suspend fun getPrediction(sing: String): PredictionModel?
}