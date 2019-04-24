package com.example.companion.Presenter

import com.example.companion.View.MvpView

interface Presenter<T : MvpView> {

    fun register(view: T)

    fun unregister(view: T)

}
