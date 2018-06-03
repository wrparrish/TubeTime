package com.ruthlessprogramming.tubetime.common

import zendesk.suas.Action
import zendesk.suas.Listener
import zendesk.suas.State
import zendesk.suas.Store

interface SuasStoreModel<out T> {
    val store: Store
        get() = setStore()

    fun setStore(): Store
    fun dispatch(action: Action<*>) = store.dispatch(action)

    fun getModelState(): T
    fun addListener(listener: Listener<State>) = store.addListener(listener)
    fun removeListener(listener: Listener<State>) = store.removeListener(listener)
}