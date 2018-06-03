package com.ruthlessprogramming.tubetime.features.stations.epoxy

import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelClass
import com.ruthlessprogramming.domain.Arrival
import com.ruthlessprogramming.domain.StopPoint
import com.ruthlessprogramming.tubetime.R
import com.ruthlessprogramming.tubetime.features.stations.StationsViewImpl
import kotlinx.android.synthetic.main.item_station.view.rvStationArrivals
import kotlinx.android.synthetic.main.item_station.view.tvDistance
import kotlinx.android.synthetic.main.item_station.view.tvStationName
import kotlin.math.roundToInt

@EpoxyModelClass
abstract class EpoxyStationModel : EpoxyModel<CardView>() {

    @EpoxyAttribute
    lateinit var stop: StopPoint

    @EpoxyAttribute
    var arrivals: List<Arrival>? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var callback: StationsViewImpl.StationViewCallbacks? = null


    override fun getDefaultLayout(): Int = R.layout.item_station

    override fun bind(view: CardView) {

        view.tvStationName.text = stop.commonName

        val meters = (Math.floor(stop.distance / 10) * 10).roundToInt()
        view.tvDistance.text = view.context.getString(R.string.info_station_distance, meters)


        arrivals?.let { arrivals ->
            val models =
                arrivals.sortedBy { it.timeToStation }
                    .take(3)
                    .map { arrival ->
                        EpoxyArrivalModel_()
                            .id(arrival.id)
                            .arrival(arrival)
                            .clickListener { model, _, _, _ ->
                                callback?.onLineChosenForDetail(stop, model.arrival().lineId)
                            }
                    }

            view.rvStationArrivals.layoutManager =
                    LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            view.rvStationArrivals.setModels(models)
        }


    }

}