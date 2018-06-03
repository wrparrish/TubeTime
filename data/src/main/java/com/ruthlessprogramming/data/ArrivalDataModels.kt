package com.ruthlessprogramming.data

import com.google.gson.annotations.SerializedName
import com.ruthlessprogramming.domain.Arrival


data class ArrivalDataModel(
    @SerializedName("'$'type") val type: String = "",
    @SerializedName("id") val id: String = "",
    @SerializedName("operationType") val operationType: Int = 0,
    @SerializedName("vehicleId") val vehicleId: String = "",
    @SerializedName("naptanId") val naptanId: String = "",
    @SerializedName("stationName") val stationName: String = "",
    @SerializedName("lineId") val lineId: String = "",
    @SerializedName("lineName") val lineName: String = "",
    @SerializedName("platformName") val platformName: String = "",
    @SerializedName("direction") val direction: String = "",
    @SerializedName("bearing") val bearing: String = "",
    @SerializedName("destinationNaptanId") val destinationNaptanId: String = "",
    @SerializedName("destinationName") val destinationName: String = "",
    @SerializedName("timestamp") val timestamp: String = "",
    @SerializedName("timeToStation") val timeToStation: Int = 0,
    @SerializedName("currentLocation") val currentLocation: String = "",
    @SerializedName("towards") val towards: String = "",
    @SerializedName("expectedArrival") val expectedArrival: String = "",
    @SerializedName("timeToLive") val timeToLive: String = "",
    @SerializedName("modeName") val modeName: String = ""
)

fun ArrivalDataModel.toDomain(): Arrival {
    return Arrival(
        type = this.type,
        id = this.id,
        operationType = this.operationType,
        vehicleId = this.vehicleId,
        naptanId = this.naptanId,
        stationName = this.stationName,
        lineId = this.lineId,
        lineName = this.lineName,
        platformName = this.platformName,
        direction = this.direction,
        bearing = this.bearing,
        destinationNaptanId = this.destinationNaptanId,
        destinationName = this.destinationName,
        timestamp = this.timestamp,
        timeToStation = this.timeToStation,
        currentLocation = this.currentLocation,
        towards = this.towards,
        expectedArrival = this.expectedArrival,
        timeToLive = this.timeToLive,
        modeName = this.modeName
    )

}