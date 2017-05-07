package com.mjdinteractive.applymjd.api

import com.google.gson.GsonBuilder
import com.mjdinteractive.applymjd.BuildConfig
import com.squareup.okhttp.OkHttpClient
import retrofit.Callback
import retrofit.RestAdapter
import retrofit.RestAdapter.LogLevel
import retrofit.android.MainThreadExecutor
import retrofit.client.OkClient
import retrofit.converter.GsonConverter
import retrofit.http.GET
import java.util.*
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit


object ApiClient {
    const val DOMAIN_PROD = "https://careers.mjdinteractive.com"
    const val TIMEOUT_SECS = 60L

    val catDataApi: CatApi get() = restAdapter.create(CatApi::class.java)

    private var mRestAdapter: RestAdapter? = null
    val restAdapter: RestAdapter
        @Synchronized get() {
            if (mRestAdapter == null) {
                mRestAdapter = newAdapter()
            }
            return mRestAdapter!!
        }

    private val mClient by lazy { OkHttpClient() }

    fun initClient() {
        mClient.setConnectTimeout(TIMEOUT_SECS, TimeUnit.SECONDS)
        mClient.setReadTimeout(TIMEOUT_SECS, TimeUnit.SECONDS)
    }

    private fun newAdapter(): RestAdapter {
        val gson = GsonBuilder()
                .registerTypeAdapter(Array<CatList>::class.java, arrayDeserializer<CatList>())
                .registerTypeAdapter(Date::class.java, DateDeserializer())
                .create()
        val gsonConverter = GsonConverter(gson)

        val pool = ThreadPoolExecutor(2, 4, 1000L, TimeUnit.MILLISECONDS, ArrayBlockingQueue(128))
        return RestAdapter.Builder()
                .setEndpoint(DOMAIN_PROD)
                .setClient(OkClient(mClient))
                .setConverter(gsonConverter)
                .setExecutors(pool, MainThreadExecutor())
                .setLogLevel(if (BuildConfig.DEBUG) LogLevel.FULL else LogLevel.NONE)
                .build()
    }
}

interface CatApi {
    companion object {
        const val ENDPOINT = "/data/cats.json"
    }

    @GET(ENDPOINT)
    fun getContent(callback: Callback<CatList>)

}


