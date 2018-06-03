package com.ruthlessprogramming.tubetime.common

import android.arch.lifecycle.ViewModel
import zendesk.suas.Action
import zendesk.suas.Listener
import zendesk.suas.State
import zendesk.suas.Store

abstract class SuasViewModel : ViewModel() {
    val store: Store by lazy { setStore() }
    abstract fun setStore(): Store

    fun dispatch(action: Action<*>) = store.dispatch(action)

    fun addListener(listener: Listener<State>) = store.addListener(listener)
    fun removeListener(listener: Listener<State>) = store.removeListener(listener)
}