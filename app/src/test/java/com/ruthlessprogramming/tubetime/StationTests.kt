package com.ruthlessprogramming.tubetime

import android.content.res.Resources
import com.nhaarman.mockito_kotlin.mock
import com.ruthlessprogramming.data.ArrivalDataModel
import com.ruthlessprogramming.data.StopPointDataModel
import com.ruthlessprogramming.data.toDomain
import com.ruthlessprogramming.domain.StopPoint
import com.ruthlessprogramming.tubetime.common.StationActions
import com.ruthlessprogramming.tubetime.features.stations.StationsViewImpl
import com.ruthlessprogramming.tubetime.features.stations.epoxy.EpoxyLoadingModel
import com.ruthlessprogramming.tubetime.features.stations.epoxy.EpoxyStationModel
import com.ruthlessprogramming.tubetime.features.stations.epoxy.StationsController
import com.ruthlessprogramming.tubetime.features.stations.state.StationState
import junit.framework.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [21])
@RunWith(RobolectricTestRunner::class)
class StationTests : JvmStateTest() {

    val resources = mock<Resources> {}
    val callbacks = object : StationsViewImpl.StationViewCallbacks {
        override fun onLineChosenForDetail(stopPoint: StopPoint, lineId: String) {

        }

        override fun onRetryClicked() {

        }

    }

    var controller = StationsController(resources, callbacks)
    val givenState = StationState()

    val givenStops = listOf(
        StopPointDataModel(
            id = "3",
            naptanId = "3",
            stationNaptan = "3",
            distance = 500.0
        ).toDomain(),
        StopPointDataModel(
            id = "1",
            naptanId = "1",
            stationNaptan = "1",
            distance = 125.0
        ).toDomain(),
        StopPointDataModel(
            id = "2",
            naptanId = "2",
            stationNaptan = "2",
            distance = 200.0
        ).toDomain()
    )


    @Test
    fun `it should respect loading state`() {

        val state = givenState.copy(isLoading = true)
        controller.setData(state)
        val modelList = controller.adapter.copyOfModels
        assertTrue(modelList.size == 1 && modelList.first() is EpoxyLoadingModel)

    }

    @Test
    fun `it should show stations in sorted order by distance`() {

        store.dispatch(StationActions.SetStops(givenStops))
        val resolvedState = store.state.getState(StationState::class.java)
        controller.setData(resolvedState)
        val modelList = controller.adapter.copyOfModels
        val firstModel = modelList.first() as EpoxyStationModel
        assertTrue(firstModel.stop.id == "1")

    }

    @Test
    fun `it should show arrivals in sorted order by time`() {
        val givenArrivalList = listOf(
            ArrivalDataModel(timeToStation = 90, naptanId = "2").toDomain(),
            ArrivalDataModel(timeToStation = 32, naptanId = "2").toDomain(),
            ArrivalDataModel(timeToStation = 5, naptanId = "2").toDomain()
        )

        store.dispatch(StationActions.SetStops(givenStops))
        store.dispatch(StationActions.SetArrivals(givenArrivalList))

        val resolvedState = store.state.getState(StationState::class.java)
        val neededStop = givenStops[2]
        val arrivals = resolvedState?.arrivals!![neededStop]
        assertTrue(arrivals?.first()?.timeToStation == 5)

    }

}