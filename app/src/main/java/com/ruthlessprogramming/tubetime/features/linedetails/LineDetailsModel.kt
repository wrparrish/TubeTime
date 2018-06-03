package com.ruthlessprogramming.tubetime.features.linedetails

import com.ruthlessprogramming.tubetime.common.SuasStoreModel
import com.ruthlessprogramming.tubetime.features.linedetails.state.LineDetailsState
import zendesk.suas.Store
import javax.inject.Inject

class LineDetailsModel  @Inject constructor(private val s: Store) : SuasStoreModel<LineDetailsState> {
    override fun setStore(): Store = s

    override fun getModelState(): LineDetailsState {
        return store.state.getState(LineDetailsState::class.java)!!
    }
}