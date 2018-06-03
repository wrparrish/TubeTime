package com.ruthlessprogramming.tubetime.features.stations.state

import com.ruthlessprogramming.domain.Arrival
import com.ruthlessprogramming.domain.StopPoint
import com.ruthlessprogramming.tubetime.common.StationActions
import zendesk.suas.Action
import zendesk.suas.Reducer
import javax.inject.Inject

class StationReducer @Inject constructor() : Reducer<StationState>() {
    override fun getInitialState(): StationState = StationState()


    override fun reduce(state: StationState, action: Action<*>): StationState? {
        return when (action) {

            is StationActions.SetLoading -> {
                state.copy(
                    isLoading = action.isLoading,
                    isInErrorState = if (action.isLoading) false else state.isInErrorState
                )
            }

            is StationActions.SetError -> state.copy(
                isInErrorState = state.arrivals?.values?.all { it.isEmpty() } == true,
                hasFetchedStations = true,
                errorMessage = action.throwable.toString()
            )

            is StationActions.SetStops -> state.copy(
                stops = action.stops.sortedBy { it.distance },
                hasFetchedStations = true,
                arrivals = action.stops.associate { stop -> stop to arrayListOf<Arrival>() }.toMutableMap()
            )

            is StationActions.SetArrivals -> {
                if (action.arrivalList.isEmpty()) return state

                val arrivalMap = state.arrivals
                val stops = arrivalMap?.keys
                val matchingStop: StopPoint? =
                    stops?.find { it.stationNaptan == action.arrivalList.first().naptanId }

                matchingStop?.let {
                    arrivalMap[it] =  action.arrivalList.sortedBy { it.timeToStation }.toMutableList()
                    return state.copy(arrivals = arrivalMap)
                }

                state
            }


            is StationActions.ClockTick -> state.copy(
                refreshTime = action.refreshInstant
            )


            else -> state

        }
    }
}