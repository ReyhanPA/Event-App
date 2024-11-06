package com.example.myfundamentalsubmission.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_event")
class FavoriteEventEntity {
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = "id")
    var id: Int = 0

    @field:ColumnInfo(name = "name")
    var name: String = ""

    @field:ColumnInfo(name = "image_logo")
    var imageLogo: String? = null

    @field:ColumnInfo(name = "summary")
    var summary: String? = null
}