package com.ruthlessprogramming.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.ruthlessprogramming.domain.Line
import com.ruthlessprogramming.domain.Location
import com.ruthlessprogramming.domain.StopPoint
import kotlinx.android.parcel.Parcelize


data class StopsByRadiusResponse(
    @SerializedName("'$'type") val type: String = "",
    @SerializedName("centrePoint") val centrePoint: List<Double> = listOf(),
    @SerializedName("stopPoints") val stopPoints: List<StopPointDataModel> = listOf(),
    @SerializedName("pageSize") val pageSize: Int = 0,
    @SerializedName("total") val total: Int = 0,
    @SerializedName("page") val page: Int = 0
)




@Parcelize
data class StopPointDataModel(
    @SerializedName("'$'type") val type: String = "",
    @SerializedName("naptanId") val naptanId: String = "",
    @SerializedName("modes") val modes: List<String> = listOf(),
    @SerializedName("icsCode") val icsCode: String = "",
    @SerializedName("stopType") val stopType: String = "",
    @SerializedName("stationNaptan") val stationNaptan: String = "",
    @SerializedName("lines") val lines: List<LineDataModel> = listOf(),
    @SerializedName("status") val status: Boolean = false,
    @SerializedName("id") val id: String = "",
    @SerializedName("commonName") val commonName: String = "",
    @SerializedName("distance") val distance: Double = 0.0,
    @SerializedName("placeType") val placeType: String = "",
    @SerializedName("lat") val lat: Double = 0.0,
    @SerializedName("lon") val lon: Double = 0.0
) : Parcelable

fun StopPointDataModel.toDomain(): StopPoint {
    return StopPoint(
        type = this.type,
        naptanId = this.naptanId,
        modes = this.modes,
        icsCode = this.icsCode,
        stopType = this.stopType,
        stationNaptan = this.stationNaptan,
        lines = this.lines.map { it.toDomain() },
        status = this.status,
        id = this.id,
        commonName = this.commonName,
        distance = this.distance,
        placeType = this.placeType,
        location = Location(
            this.lat, this.lon
        )
    )
}

fun StopPoint.toDataModel(): StopPointDataModel {
    return StopPointDataModel(
        type = this.type,
        naptanId = this.naptanId,
        modes = this.modes,
        icsCode = this.icsCode,
        stopType = this.stopType,
        stationNaptan = this.stationNaptan,
        lines = this.lines.map { it.toDataModel() },
        status = this.status,
        id = this.id,
        commonName = this.commonName,
        distance = this.distance,
        placeType = this.placeType,
        lat = this.location.lat,
        lon = this.location.lon
    )
}

@Parcelize
data class LineDataModel(
    @SerializedName("'$'type") val entityType: String = "",
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("uri") val uri: String = "",
    @SerializedName("type") val lineType: String = ""
): Parcelable

fun LineDataModel.toDomain(): Line {
    return Line(
        entityType = this.entityType,
        id = this.id,
        name = this.name,
        uri = this.uri,
        lineType = this.lineType
    )
}

fun Line.toDataModel(): LineDataModel {
    return LineDataModel(
        entityType = this.entityType,
        id = this.id,
        name = this.name,
        uri = this.uri,
        lineType = this.lineType
    )
}










