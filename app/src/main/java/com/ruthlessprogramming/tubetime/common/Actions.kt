package com.ruthlessprogramming.tubetime.common

import com.ruthlessprogramming.domain.Arrival
import com.ruthlessprogramming.domain.LineDetails
import com.ruthlessprogramming.domain.Location
import com.ruthlessprogramming.domain.StopPoint
import org.threeten.bp.Instant
import zendesk.suas.Action

const val setLoadingActionType = "setLoading"
const val setLineDetailsActionType = "setLineDetails"
const val initLineDetails = "initLineDetails"
const val setStops = "setStops"
const val setArrivals = "setArrivals"
const val setLocation = "setLocation"
const val setError = "setError"
const val setClock = "setClock"

interface AppActions {
    class SetLocation(val location: Location) : Action<Location>(setLocation, location)
}

interface StationActions {

    class SetLoading(val isLoading: Boolean) : Action<Boolean>(setLoadingActionType, isLoading)
    class SetError(val throwable: Throwable) : Action<Throwable>(setError, throwable)
    class SetStops(val stops: List<StopPoint>) : Action<List<StopPoint>>(setStops, stops)
    class SetArrivals(val arrivalList: List<Arrival>) :
        Action<List<Arrival>>(setArrivals, arrivalList)

    class ClockTick(val refreshInstant: Instant) : Action<Instant>(setClock, refreshInstant)

}


interface LineDetailsActions {
    class SetLoading(val isLoading: Boolean) : Action<Boolean>(setLoadingActionType)
    class SetError(val throwable: Throwable) : Action<Throwable>(setError, throwable)
    class Init(val passedLineId: String, val passedStopPoint: StopPoint) :
        Action<StopPoint>(initLineDetails, passedStopPoint)

    class SetLineDetails(val details: LineDetails) :
        Action<LineDetails>(setLineDetailsActionType, details)
}