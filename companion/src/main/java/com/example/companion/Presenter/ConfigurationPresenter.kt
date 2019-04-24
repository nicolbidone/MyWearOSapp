package com.example.companion.Presenter

import android.content.Intent
import com.example.companion.View.ConfigurationMvpView
import com.google.android.gms.common.api.GoogleApiClient

interface ConfigurationPresenter : Presenter<ConfigurationMvpView> {

    fun onActivityResult(resultCode: Int, data: Intent, requestCode: Int, googleApiClient: GoogleApiClient)

    fun changeContentImage(isConnected: Boolean, type: Int)

    fun saveConfig()

}
