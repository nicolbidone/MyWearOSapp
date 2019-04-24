package com.example.companion.View

import android.content.Intent

interface ConfigurationMvpView : MvpView {

    fun startFileChooser(intent: Intent, requestCode: Int)

    fun showStatusMessage(message: String)
}
