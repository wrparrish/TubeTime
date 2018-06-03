package com.ruthlessprogramming.domain.usecase

sealed class StopParams {
    class LocationParams(
        val latitude: Double,
        val longitude: Double
    ) : StopParams()

    class IdParams(
        val stopId: String
    ): StopParams()

    class NameParams(
        val stopName: String
    ): StopParams()

}

