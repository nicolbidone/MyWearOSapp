package com.example.companion

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.companion.Presenter.ConfigurationPresenter
import com.example.companion.Presenter.ConfigurationPresenterImpl
import com.example.companion.View.ConfigurationMvpView

class MainActivity : BaseGoogleApiActivity(), ConfigurationMvpView {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = getString(R.string.main_label)
        mConfigurationPresenter.register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mConfigurationPresenter.unregister(this)
    }

    var mConfigurationPresenter: ConfigurationPresenter = ConfigurationPresenterImpl()

    fun onSendPhotoClick(view: View) {
        mConfigurationPresenter.changeContentImage(isConnected, BACKGROUND_CHOOSER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        mConfigurationPresenter.onActivityResult(resultCode, data, requestCode, mGoogleApiClient)
    }

    override fun startFileChooser(intent: Intent, requestCode: Int) {
        startActivityForResult(
            Intent.createChooser(intent, getString(R.string.select_file)),
            requestCode
        )
    }

    override fun showStatusMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun getContext(): Context {
        return this
    }


}
