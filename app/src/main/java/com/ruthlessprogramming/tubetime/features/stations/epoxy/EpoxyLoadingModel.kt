package com.ruthlessprogramming.tubetime.features.stations.epoxy

import android.support.constraint.ConstraintLayout
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelClass
import com.ruthlessprogramming.tubetime.R
import kotlinx.android.synthetic.main.epoxy_loading.view.tvProgress


@EpoxyModelClass
abstract class EpoxyLoadingModel : EpoxyModel<ConstraintLayout>() {
    @EpoxyAttribute
    var loadingMessage: String = ""

    override fun getDefaultLayout(): Int = R.layout.epoxy_loading

    override fun bind(view: ConstraintLayout) {
        view.tvProgress.text = loadingMessage
    }
}