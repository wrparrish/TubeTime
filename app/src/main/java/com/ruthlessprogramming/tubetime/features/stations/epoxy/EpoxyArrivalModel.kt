package com.ruthlessprogramming.tubetime.features.stations.epoxy

import android.support.constraint.ConstraintLayout
import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelClass
import com.ruthlessprogramming.domain.Arrival
import com.ruthlessprogramming.tubetime.R
import kotlinx.android.synthetic.main.item_arrival.view.tvArrivalInfo
import kotlinx.android.synthetic.main.item_arrival.view.tvArrivalTime


@EpoxyModelClass
abstract class EpoxyArrivalModel : EpoxyModel<ConstraintLayout>() {

    @EpoxyAttribute
    lateinit var arrival: Arrival

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var clickListener: View.OnClickListener = View.OnClickListener { }


    override fun getDefaultLayout(): Int = R.layout.item_arrival

    override fun bind(view: ConstraintLayout) {
        view.setOnClickListener(clickListener)

        var context = view.context

        view.tvArrivalInfo.text = context.getString(
            R.string.info_arrival,
            arrival.lineName,
            arrival.destinationName,
            arrival.platformName
        )

        val minsAway = arrival.timeToStation / 60
        view.tvArrivalTime.text = context.getString(R.string.info_arrival_time_minutes, minsAway)
    }

}