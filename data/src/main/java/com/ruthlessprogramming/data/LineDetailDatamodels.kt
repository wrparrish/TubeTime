package com.ruthlessprogramming.data

import com.google.gson.annotations.SerializedName
import com.ruthlessprogramming.domain.LineDetails
import com.ruthlessprogramming.domain.MatchedStop
import com.ruthlessprogramming.domain.OrderedLineRoute
import com.ruthlessprogramming.domain.Station
import com.ruthlessprogramming.domain.StopPointSequence


data class LineDetailsDataModel(
    @SerializedName("'$'type") val type: String = "",
    @SerializedName("lineId") val lineId: String = "",
    @SerializedName("lineName") val lineName: String = "",
    @SerializedName("mode") val mode: String = "",
    @SerializedName("lineStrings") val lineStrings: List<String> = listOf(),
    @SerializedName("stations") val stations: List<StationDataModel> = listOf(),
    @SerializedName("stopPointSequences") val stopPointSequences: List<StopPointSequenceDataModel> = listOf(),
    @SerializedName("orderedLineRoutes") val orderedLineRoutes: List<OrderedLineRouteDataModel> = listOf()
)

fun LineDetailsDataModel.toDomain(): LineDetails {
    return LineDetails(
        type = this.type,
        lineId = this.lineId,
        lineName = this.lineName,
        mode = this.mode,
        lineStrings = this.lineStrings,
        stations = this.stations.map { it.toDomain() },
        stopPointSequences = this.stopPointSequences.map { it.toDomain() },
        orderedLineRoutes = this.orderedLineRoutes.map { it.toDomain() }

    )
}

data class StopPointSequenceDataModel(
    @SerializedName("'$'type") val type: String = "",
    @SerializedName("lineId") val lineId: String = "",
    @SerializedName("lineName") val lineName: String = "",
    @SerializedName("direction") val direction: String = "",
    @SerializedName("branchId") val branchId: Int = 0,
    @SerializedName("nextBranchIds") val nextBranchIds: List<Any> = listOf(),
    @SerializedName("prevBranchIds") val prevBranchIds: List<Any> = listOf(),
    @SerializedName("stopPoint") val stopPoint: List<MatchedStopDataModel> = listOf(),
    @SerializedName("serviceType") val serviceType: String = ""
)

fun StopPointSequenceDataModel.toDomain(): StopPointSequence {
    return StopPointSequence(
        type = this.type,
        lineId = this.lineId,
        lineName = this.lineName,
        direction = this.direction,
        branchId = this.branchId,
        nextBranchIds = this.nextBranchIds,
        prevBranchIds = this.prevBranchIds,
        stopPoints = this.stopPoint.map { it.toDomain() },
        serviceType = this.serviceType
    )
}


data class OrderedLineRouteDataModel(
    @SerializedName("'$'type") val type: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("naptanIds") val naptanIds: List<String> = listOf(),
    @SerializedName("serviceType") val serviceType: String = ""
)

fun OrderedLineRouteDataModel.toDomain(): OrderedLineRoute {
    return OrderedLineRoute(
        type = this.type,
        name = this.name,
        naptanIds = this.naptanIds,
        serviceType = this.serviceType
    )

}


data class StationDataModel(
    @SerializedName("'$'type") val type: String = "",
    @SerializedName("stationId") val stationId: String = "",
    @SerializedName("icsId") val icsId: String = "",
    @SerializedName("topMostParentId") val topMostParentId: String = "",
    @SerializedName("modes") val modes: List<String> = listOf(),
    @SerializedName("stopType") val stopType: String = "",
    @SerializedName("zone") val zone: String = "",
    @SerializedName("status") val status: Boolean = false,
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("lat") val lat: Double = 0.0,
    @SerializedName("lon") val lon: Double = 0.0
)

fun StationDataModel.toDomain(): Station {
    return Station(
        type = this.type,
        stationId = this.stationId,
        icsId = this.icsId,
        topMostParentId = this.topMostParentId,
        modes = this.modes,
        stopType = this.stopType,
        zone = this.zone,
        status = this.status,
        id = this.id,
        name = this.name,
        lat = this.lat,
        lon = this.lon
    )
}


data class MatchedStopDataModel(
    @SerializedName("'$'type") val type: String = "",
    @SerializedName("stationId") val stationId: String = "",
    @SerializedName("icsId") val icsId: String = "",
    @SerializedName("topMostParentId") val topMostParentId: String = "",
    @SerializedName("modes") val modes: List<String> = listOf(),
    @SerializedName("stopType") val stopType: String = "",
    @SerializedName("zone") val zone: String = "",
    @SerializedName("hasDisruption") val hasDisruption: Boolean = false,
    @SerializedName("lines") val lines: List<LineDataModel> = listOf(),
    @SerializedName("status") val status: Boolean = false,
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("lat") val lat: Double = 0.0,
    @SerializedName("lon") val lon: Double = 0.0
)

fun MatchedStopDataModel.toDomain(): MatchedStop {
    return MatchedStop(
        type = this.type,
        stationId = this.stationId,
        icsId = this.icsId,
        topMostParentId = this.topMostParentId,
        modes = this.modes,
        stopType = this.stopType,
        zone = this.zone,
        hasDisruption = this.hasDisruption,
        lines = this.lines.map { it.toDomain() },
        status = this.status,
        id = this.id,
        name = this.name,
        lat = this.lat,
        lon = this.lon
    )
}

