package com.mjdinteractive.applymjd

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.mjdinteractive.applymjd.api.ApiClient
import com.mjdinteractive.applymjd.api.CatApi
import com.mjdinteractive.applymjd.api.CatList
import retrofit.Callback
import retrofit.RetrofitError
import retrofit.client.Response

class MainActivity : AppCompatActivity() {

    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapter: RecyclerAdapter

    private val api: CatApi by lazy { ApiClient.catDataApi }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRecyclerView = findViewById(R.id.cat_view) as RecyclerView
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        //load cat data here

    }

    fun getCatData() {
        api.getContent(object : Callback<CatList> {
            override fun success(data: CatList, response: Response) {
                mAdapter = RecyclerAdapter(this@MainActivity, data)
                mRecyclerView.adapter = mAdapter
            }

            override fun failure(error: RetrofitError) {
                //handle failed API callback
            }
        })
    }
}