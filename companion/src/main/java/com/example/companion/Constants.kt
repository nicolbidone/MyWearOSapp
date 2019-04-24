package com.example.companion

import java.util.HashMap

const val SECOND_CHOOSER = 1
const val BACKGROUND_CHOOSER = 2
const val HOUR_CHOOSER = 3
const val MINUTE_CHOOSER = 4
const val BACKGROUND_AMBIENT = 5
const val HOUR_AMBIENT = 6
const val MINUTE_AMBIENT = 7

object Constants {

    var resourceKeyMap: MutableMap<Int, String> = HashMap()

    init {
        resourceKeyMap[SECOND_CHOOSER] = "tick"
        resourceKeyMap[BACKGROUND_CHOOSER] = "bg"
        resourceKeyMap[HOUR_CHOOSER] = "hrs"
        resourceKeyMap[MINUTE_CHOOSER] = "min"
        resourceKeyMap[BACKGROUND_AMBIENT] = "bg_ambient"
        resourceKeyMap[HOUR_AMBIENT] = "hrs_ambient"
        resourceKeyMap[MINUTE_AMBIENT] = "min_ambient"
    }
}
