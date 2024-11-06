package com.example.myfundamentalsubmission.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event_finished")
class EventFinishedEntity {
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "name")
    var name: String = ""

    @ColumnInfo(name = "image_logo")
    var imageLogo: String? = null

    @ColumnInfo(name = "summary")
    var summary: String? = null

    @ColumnInfo(name = "media_cover")
    var mediaCover: String? = null

    @ColumnInfo(name = "owner_name")
    var ownerName: String? = null

    @ColumnInfo(name = "begin_time")
    var beginTime: String? = null

    @ColumnInfo(name = "end_time")
    var endTime: String? = null

    @ColumnInfo(name = "quota")
    var quota: Int? = null

    @ColumnInfo(name = "registrants")
    var registrants: Int? = null

    @ColumnInfo(name = "description")
    var description: String? = null
}