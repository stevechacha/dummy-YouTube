package com.steve.dummyyoutube

import java.time.Duration

class HomeFeed(val videos:List<Video>)
class Video (val id:Int,val name:String,val link:String,val imageUrl:String,val numberOfViews:Int,val channel: Channel)
class Channel(val name:String,val profileImage:String)
class CourseLesson(val name:String,val duration: String,val number:Int,val imageUrl: String,val link: String)