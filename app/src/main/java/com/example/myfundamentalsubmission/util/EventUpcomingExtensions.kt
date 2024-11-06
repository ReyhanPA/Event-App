package com.example.myfundamentalsubmission.util

import com.example.myfundamentalsubmission.data.local.entity.EventUpcomingEntity
import com.example.myfundamentalsubmission.data.remote.response.ListEventsItem

fun EventUpcomingEntity.toListEventsItem(): ListEventsItem {
    return ListEventsItem(
        id = this.id,
        name = this.name,
        imageLogo = this.imageLogo,
        summary = this.summary,
        mediaCover = this.mediaCover,
        ownerName = this.ownerName,
        beginTime = this.beginTime,
        endTime = this.endTime,
        quota = this.quota,
        registrants = this.registrants,
        description = this.description
    )
}