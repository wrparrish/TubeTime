package com.ruthlessprogramming.tubetime.features.linedetails.epoxy

import android.content.res.Resources
import com.airbnb.epoxy.TypedEpoxyController
import com.ruthlessprogramming.data.datasources.dummyLat
import com.ruthlessprogramming.data.datasources.dummyLong
import com.ruthlessprogramming.domain.Location
import com.ruthlessprogramming.tubetime.R
import com.ruthlessprogramming.tubetime.features.linedetails.state.LineWithLocation
import com.ruthlessprogramming.tubetime.features.stations.epoxy.epoxyError
import com.ruthlessprogramming.tubetime.features.stations.epoxy.epoxyLoading
import java.lang.StrictMath.*

class LineController(private val resources: Resources) : TypedEpoxyController<LineWithLocation>() {
    override fun buildModels(data: LineWithLocation) {
        when {
            data.lineDetailsState.isLoading -> {
                epoxyLoading {
                    id("loadingModel")
                        loadingMessage(resources.getString(R.string.loading_line_route))
                }
                return
            }

            data.lineDetailsState.isInErrorState -> {
                epoxyError {
                    id("errorModel")
                        .errorMessage(data.lineDetailsState.errorMessage)
                }
                return
            }
        }

        val details = data.lineDetailsState.lineDetails
        val selectedStop = data.lineDetailsState.passedStopPoint
        val userLocation = data.appState.location ?: Location(dummyLat, dummyLong)

        val sequencedStopPoints = details?.stopPointSequences?.first()?.stopPoints

        val closestStation = sequencedStopPoints?.sortedBy {
            haversine(
                userLocation.lat,
                userLocation.lon,
                it.lat,
                it.lon
            )
        }
            ?.first()


        sequencedStopPoints?.map { stop ->
            epoxyLineStation {
                id(stop.id)
                selectedStation(stop.stationId == selectedStop?.stationNaptan)
                closestStation(stop.id == closestStation?.id)
                stop(stop)
            }
        }


    }


    val r = 6372.8 // in kilometers
    fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val λ1 = toRadians(lat1)
        val λ2 = toRadians(lat2)
        val Δλ = toRadians(lat2 - lat1)
        val Δφ = toRadians(lon2 - lon1)
        return 2 * r * asin(sqrt(pow(sin(Δλ / 2), 2.0) + pow(sin(Δφ / 2), 2.0) * cos(λ1) * cos(λ2)))
    }


}