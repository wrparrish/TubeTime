package com.ruthlessprogramming.tubetime.features.stations.state

import com.ruthlessprogramming.domain.Arrival
import com.ruthlessprogramming.domain.StopPoint
import org.threeten.bp.Instant

data class StationState(
    val isLoading: Boolean = false,
    val hasFetchedStations: Boolean = false,
    val refreshTime: Instant = Instant.now(),
    val isInErrorState: Boolean = false,
    val errorMessage: String = "",
    val stops: List<StopPoint> = listOf(),
    val arrivals: MutableMap<StopPoint, MutableList<Arrival>>? = null
)