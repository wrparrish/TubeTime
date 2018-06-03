package com.ruthlessprogramming.tubetime.features.stations

import com.ruthlessprogramming.tubetime.common.SuasStoreModel
import com.ruthlessprogramming.tubetime.features.stations.state.StationState
import zendesk.suas.Store
import javax.inject.Inject

class StationsModel @Inject constructor(private val s: Store) : SuasStoreModel<StationState> {

    override fun getModelState(): StationState =
        store.state.getState(StationState::class.java) ?: StationState()


    override fun setStore(): Store = s

}