package com.steve.dummyyoutube

import android.graphics.Color.*
import android.hardware.camera2.params.RggbChannelVector.BLUE
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView=findViewById(R.id.recycler_main)
        recyclerView.layoutManager=LinearLayoutManager(this)

        fetchJson()
    }

     private fun fetchJson() {
        val url="https://api.letsbuildthatapp.com/youtube/home_feed"
        val request= Request.Builder().url(url).build()
        val client=OkHttpClient()
        client.newCall(request).enqueue(object :Callback{
            override fun onResponse(call: Call, response: Response) {
                val body= response.body?.string()
                println(body)
                val gson=GsonBuilder().create()
                val homeFeed= gson.fromJson(body,HomeFeed::class.java)
               runOnUiThread{
                   recyclerView.adapter=MainAdapter(homeFeed)
               }

            }
            override fun onFailure(call: Call, e: IOException) {
                println("failed to execute ")

            }

        })
    }
}

