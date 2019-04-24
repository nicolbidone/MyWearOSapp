package com.example.companion.Presenter

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import com.example.companion.App
import com.example.companion.Constants
import com.example.companion.SendToDataLayerThread
import com.example.companion.View.ConfigurationMvpView
import com.google.android.gms.common.api.GoogleApiClient
import java.io.IOException

class ConfigurationPresenterImpl : ConfigurationPresenter,
    SendToDataLayerThread.DataLayerListener {

    private var mConfigurationView: ConfigurationMvpView? = null

    override fun onActivityResult(resultCode: Int, data: Intent, requestCode: Int, googleApiClient: GoogleApiClient) {
        try {
            if (resultCode == Activity.RESULT_OK) {
                val selectedImageUri = data.data
                val bitmap =
                    MediaStore.Images.Media.getBitmap(
                        mConfigurationView?.getContext()?.contentResolver,
                        selectedImageUri
                    )
                App().getConfigurationManager().updateField(requestCode, bitmap)
                Constants.resourceKeyMap[requestCode]?.let {
                    SendToDataLayerThread(
                        it,
                        bitmap,
                        googleApiClient,
                        this
                    ).start()
                }
            }
        } catch (e: IOException) {
            Log.e("TAG", e.message)
        }

    }

    override fun changeContentImage(isConnected: Boolean, type: Int) {
        if (isConnected) {
            startFileChooser(type)
        }
    }

    private fun startFileChooser(fileSelectCode: Int) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        mConfigurationView?.startFileChooser(intent, fileSelectCode)
    }

    override fun saveConfig() {
        App().getConfigurationManager().saveConfiguration()
    }

    override fun register(holder: ConfigurationMvpView) {
        mConfigurationView = holder
    }

    override fun unregister(holder: ConfigurationMvpView) {
        mConfigurationView = null
    }

    override fun onSuccess(message: String) {
        mConfigurationView?.showStatusMessage(message)
    }
}
