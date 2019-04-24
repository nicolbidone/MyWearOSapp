package com.example.wear

import android.graphics.Bitmap

class WatchfaceUpdatedEvent {

    private var resourceKey = ""
    private lateinit var resourceBitmap: Bitmap
    private var offset = 0

    constructor(resourceKey: String, resourceBitmap: Bitmap) {
        this.resourceKey = resourceKey
        this.resourceBitmap = resourceBitmap
    }

    constructor(key: String, offset: Int) {
        this.offset = offset
        resourceKey = key
    }
}
