package com.antarikshc.stockplay.utils

import android.text.format.DateUtils
import java.util.*

fun Date.getRelativeTime(): String {
    val now = Date().time
    val difference = now - time

    val relativeTime = when {
        difference < 2000L -> "Just now"

        difference < DateUtils.MINUTE_IN_MILLIS -> DateUtils.getRelativeTimeSpanString(
            time,
            now,
            DateUtils.SECOND_IN_MILLIS
        )
        difference < DateUtils.HOUR_IN_MILLIS -> DateUtils.getRelativeTimeSpanString(
            time,
            now,
            DateUtils.MINUTE_IN_MILLIS
        )
        difference < DateUtils.DAY_IN_MILLIS -> DateUtils.getRelativeTimeSpanString(
            time,
            now,
            DateUtils.HOUR_IN_MILLIS
        )
        difference < DateUtils.WEEK_IN_MILLIS -> DateUtils.getRelativeTimeSpanString(
            time,
            now,
            DateUtils.DAY_IN_MILLIS
        )
        else -> DateUtils.getRelativeTimeSpanString(time, now, DateUtils.WEEK_IN_MILLIS)
    }

    return relativeTime.toString()
}