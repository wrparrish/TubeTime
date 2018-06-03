package com.ruthlessprogramming.tubetime.features.linedetails.epoxy

import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelClass
import com.ruthlessprogramming.domain.MatchedStop
import com.ruthlessprogramming.tubetime.R
import kotlinx.android.synthetic.main.item_line_station_detail.view.ivLocationMarker
import kotlinx.android.synthetic.main.item_line_station_detail.view.tvStationName

@EpoxyModelClass
abstract class EpoxyLineStationModel : EpoxyModel<ConstraintLayout>() {

    @EpoxyAttribute
    lateinit var stop: MatchedStop

    @EpoxyAttribute
    var selectedStation: Boolean = false

    @EpoxyAttribute
    var closestStation: Boolean = false


    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var clickListener: View.OnClickListener = View.OnClickListener { }

    override fun getDefaultLayout(): Int = R.layout.item_line_station_detail

    override fun bind(view: ConstraintLayout) {
        view.setOnClickListener(clickListener)
        view.tvStationName.text = stop.name

        if (selectedStation) {
            view.tvStationName.textSize = 18f
            view.tvStationName.setTextColor(
                ContextCompat.getColor(
                    view.context,
                    R.color.aqua_green
                )
            )

        } else {
            view.tvStationName.textSize = 14f
            view.tvStationName.setTextColor(
                ContextCompat.getColor(
                    view.context,
                    R.color.purplish_grey
                )
            )

        }

        if (closestStation) {
            view.ivLocationMarker.visibility = View.VISIBLE
        } else {
            view.ivLocationMarker.visibility = View.GONE
        }


    }

}