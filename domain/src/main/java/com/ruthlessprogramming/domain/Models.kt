package com.ruthlessprogramming.domain

data class StopPoint(
    val type: String,
    val naptanId: String,
    val modes: List<String>,
    val icsCode: String,
    val stopType: String,
    val stationNaptan: String,
    val lines: List<Line>,
    val status: Boolean,
    val id: String,
    val commonName: String,
    val distance: Double,
    val placeType: String,
    val location: Location
)


data class Line(
    val entityType: String,
    val id: String,
    val name: String,
    val uri: String,
    val lineType: String
)

data class Location(
    val lat: Double,
    val lon: Double
)


data class Arrival(
    val type: String,
    val id: String,
    val operationType: Int,
    val vehicleId: String,
    val naptanId: String,
    val stationName: String,
    val lineId: String,
    val lineName: String,
    val platformName: String,
    val direction: String,
    val bearing: String,
    val destinationNaptanId: String,
    val destinationName: String,
    val timestamp: String,
    val timeToStation: Int,
    val currentLocation: String,
    val towards: String,
    val expectedArrival: String,
    val timeToLive: String,
    val modeName: String
)


data class LineDetails(
    val type: String,
    val lineId: String,
    val lineName: String,
    val mode: String,
    val lineStrings: List<String> = listOf(),
    val stations: List<Station> = listOf(),
    val stopPointSequences: List<StopPointSequence>,
    val orderedLineRoutes: List<OrderedLineRoute>
)


data class StopPointSequence(
    val type: String,
    val lineId: String,
    val lineName: String,
    val direction: String,
    val branchId: Int,
    val nextBranchIds: List<Any>,
    val prevBranchIds: List<Any>,
    val stopPoints: List<MatchedStop>,
    val serviceType: String
)


data class Station(
    val type: String,
    val stationId: String,
    val icsId: String,
    val topMostParentId: String,
    val modes: List<String>,
    val stopType: String,
    val zone: String,
    val status: Boolean,
    val id: String,
    val name: String,
    val lat: Double,
    val lon: Double
)

data class MatchedStop(
    val type: String = "",
    val stationId: String,
    val icsId: String,
    val topMostParentId: String,
    val modes: List<String>,
    val stopType: String,
    val zone: String,
    val hasDisruption: Boolean,
    val lines: List<Line>,
    val status: Boolean = false,
    val id: String,
    val name: String,
    val lat: Double,
    val lon: Double
)


data class OrderedLineRoute(
    val type: String,
    val name: String,
    val naptanIds: List<String>,
    val serviceType: String
)





