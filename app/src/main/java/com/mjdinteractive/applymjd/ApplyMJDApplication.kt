package com.mjdinteractive.applymjd

/**
 * Created by ASErickson on 3/13/17.
 */

import android.app.Application
import com.mjdinteractive.applymjd.api.ApiClient

class ApplyMJDApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ApiClient.initClient()
    }
}