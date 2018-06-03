package com.ruthlessprogramming.tubetime.features.stations.epoxy

import android.support.constraint.ConstraintLayout
import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelClass
import com.ruthlessprogramming.tubetime.R
import kotlinx.android.synthetic.main.epoxy_error.view.tvErrorLabel
import kotlinx.android.synthetic.main.epoxy_error.view.tvRetry


@EpoxyModelClass
abstract class EpoxyErrorModel : EpoxyModel<ConstraintLayout>() {

    @EpoxyAttribute
    var errorMessage: String = ""

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var clickListener: View.OnClickListener = View.OnClickListener { }


    override fun getDefaultLayout(): Int = R.layout.epoxy_error

    override fun bind(view: ConstraintLayout) {
        view.tvErrorLabel.text = errorMessage
        view.tvRetry.setOnClickListener(clickListener)
    }

}