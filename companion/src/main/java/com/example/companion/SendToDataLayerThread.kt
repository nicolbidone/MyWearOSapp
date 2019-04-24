package com.example.companion

import android.graphics.Bitmap
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.wearable.Wearable
import java.io.ByteArrayOutputStream

class SendToDataLayerThread(
    private val path: String,
    private val bitmap: Bitmap,
    private val mGoogleApiClient: GoogleApiClient,
    private val mDataLayerListener: DataLayerListener
) : Thread() {

    companion object{
        private val MAX_SIZE = 2000000
    }

    override fun run() {
        val nodes = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await()
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        var message = "$path was sent successfully"
        if (bitmap.byteCount < MAX_SIZE) {
            for (node in nodes.nodes) {
                Wearable.MessageApi.sendMessage(mGoogleApiClient, node.id, path, stream.toByteArray()).await()
            }
        } else {
            message = "Big image file, try to use another"
        }
        mDataLayerListener.onSuccess(message)
    }

    interface DataLayerListener {
        fun onSuccess(message: String)
    }
}
