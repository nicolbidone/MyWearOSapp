package com.example.companion

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.util.*

class ConfigurationManager {

    private val DEFAULT_JSON_CONFIGURATION = "{seconds_offset:0,minutes_offset:0,hours_offset:0}"
    private val configMap = HashMap<String, Bitmap>()
    private lateinit var mJsonObjectConfig: JSONObject
    private lateinit var mJsonFile: File

    fun init(context: Context) {
        val resources = context.resources
        //fillMap(resources)
        mJsonObjectConfig = getJsonString()
    }



    private fun getJsonString(): JSONObject {
        val builder = StringBuilder()
        var jsonObject = JSONObject()
        try {
            val sdcard = File(Environment.getExternalStorageDirectory().absolutePath + "/configuration/")
            if (!sdcard.exists()) {
                sdcard.mkdirs()
            }
            mJsonFile = File(sdcard, "configuration.json")
            jsonObject = if (!mJsonFile.exists()) {
                val writer = FileWriter(mJsonFile)
                writer.append(DEFAULT_JSON_CONFIGURATION)
                writer.flush()
                writer.close()
                JSONObject(DEFAULT_JSON_CONFIGURATION)
            } else {
                val bufferedReader = BufferedReader(FileReader(mJsonFile))
                for (str in bufferedReader.readLine()) {
                    builder.append(str)
                }
                JSONObject(builder.toString())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return jsonObject
    }

    fun updateField(key: Int, bitmap: Bitmap) {
        val key = Constants.resourceKeyMap[key]
        key?.let { configMap.put(it, bitmap) }
    }

    fun saveConfiguration() {
        for ((key, value) in configMap) {
            val pictureFile = getOutputMediaFile(key)
            try {
                val fos = FileOutputStream(pictureFile)
                value.compress(Bitmap.CompressFormat.PNG, 90, fos)
                fos.close()
                updateConfigurationFile()
            } catch (e: FileNotFoundException) {
                Log.d("Configuration", "File not found: " + e.message)
            } catch (e: IOException) {
                Log.d("Configuration", "Error accessing file: " + e.message)
            }

        }
    }

    private fun getOutputMediaFile(fileName: String): File {
        val mediaStorageDir = File(Environment.getExternalStorageDirectory().absolutePath + "/drawable-nodpi/")
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs()
        }
        return File(mediaStorageDir.path + File.separator + fileName + ".png")
    }

    @Throws(IOException::class)
    private fun updateConfigurationFile() {
        val writer = FileWriter(mJsonFile)
        writer.append(mJsonObjectConfig.toString())
        writer.flush()
        writer.close()
    }
}
