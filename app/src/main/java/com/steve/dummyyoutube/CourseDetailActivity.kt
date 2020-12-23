package com.steve.dummyyoutube

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import com.steve.dummyyoutube.CustomViewHolder.Companion.VIDEO_TITLE_KEY
import okhttp3.*
import java.io.IOException

class CourseDetailActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       val navBar= intent.getStringExtra(CustomViewHolder.VIDEO_TITLE_KEY)
        supportActionBar?.title=navBar

        recyclerView=findViewById(R.id.recycler_main)
        recyclerView.layoutManager=LinearLayoutManager(this)

        fetchJson()
    }

    private fun fetchJson() {

        val videoId=intent.getStringExtra(CustomViewHolder.VIDEO_ID_KEY)
        val courseDetailUrl="https://api.letsbuildthatapp.com/youtube/course_detail?id="+videoId
        val request= Request.Builder().url(courseDetailUrl).build()
        val client= OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body= response.body?.string()
                println(body)
                val gson= GsonBuilder().create()
                val courseLessons= gson.fromJson(body,Array<CourseLesson>::class.java)
                runOnUiThread{
                    recyclerView.adapter=CourseDetailAdapter(courseLessons)
                }

            }
            override fun onFailure(call: Call, e: IOException) {
                println("failed to execute ")

            }

        })
    }

    private class CourseDetailAdapter(val courseLesson: Array<CourseLesson>):RecyclerView.Adapter<CourseDetailViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseDetailViewHolder {
          val custom=LayoutInflater.from(parent.context).inflate(R.layout.custom_row,parent,false)
            return CourseDetailViewHolder(custom)
        }

        override fun onBindViewHolder(holder: CourseDetailViewHolder, position: Int) {
            val courseLesson=courseLesson.get(position)
            holder.customView.findViewById<TextView>(R.id.cutsomTitle).text=courseLesson.name
            holder.customView.findViewById<TextView>(R.id.customduration).text=courseLesson.duration

            val image=holder.customView.findViewById<ImageView>(R.id.customImage)
            Picasso.get().load(courseLesson.imageUrl).into(image)

            holder.courseLesson=courseLesson

        }
        override fun getItemCount(): Int {
           return courseLesson.size
        }

    }
    class CourseDetailViewHolder(val customView:View,var courseLesson:CourseLesson?=null):RecyclerView.ViewHolder(customView){
        companion object{
            const val COURSE_LESSON_LINK_KEY="COURSE_LESSON_LINK"
        }
        init {
            customView.setOnClickListener {
                val intent= Intent(customView.context,CourseLessonActivity::class.java)
                intent.putExtra(COURSE_LESSON_LINK_KEY,courseLesson?.link)
                customView.context.startActivity(intent)
            }
        }

    }
}