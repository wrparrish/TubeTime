package com.ruthlessprogramming.tubetime.features.stations.epoxy

import android.content.res.Resources
import com.airbnb.epoxy.TypedEpoxyController
import com.ruthlessprogramming.tubetime.R
import com.ruthlessprogramming.tubetime.features.stations.StationsViewImpl
import com.ruthlessprogramming.tubetime.features.stations.state.StationState

class StationsController(
    private val resources: Resources,
    private val callback: StationsViewImpl.StationViewCallbacks
) :
    TypedEpoxyController<StationState>() {


    override fun buildModels(data: StationState) {

        when {
            data.isLoading -> {
                epoxyLoading {
                    id("loadingModel")
                        loadingMessage(resources.getString(R.string.loading_arrivals))
                }
                return
            }

            data.isInErrorState -> {
                epoxyError {
                    id("errorModel")
                        .errorMessage(data.errorMessage)
                        .clickListener({ _ ->
                            callback.onRetryClicked()
                        })
                }
                return
            }
        }

        data.stops.forEach { stop ->
            epoxyStation {
                id(stop.id)
                stop(stop)
                arrivals(data.arrivals?.get(stop))
                callback(callback)

            }

        }
    }
}



