package com.steve.dummyyoutube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView

class CourseLessonActivity : AppCompatActivity() {
    lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_lesson)

        webView=findViewById(R.id.web_courselesson)
        webView.settings.javaScriptEnabled=true
        webView.settings.loadWithOverviewMode=true
        webView.settings.useWideViewPort=true
        val courselink=intent.getStringExtra(CourseDetailActivity.CourseDetailViewHolder.COURSE_LESSON_LINK_KEY)

        if (courselink != null) {
            webView.loadUrl(courselink)
        }

    }
}