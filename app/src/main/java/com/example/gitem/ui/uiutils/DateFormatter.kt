package com.example.gitem.ui.uiutils

import android.content.Context
import com.example.gitem.R
import java.time.Instant
import java.time.Period
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun getElapsedPeriod(context: Context, pastDateString: String): String {
    val formatter = DateTimeFormatter.ISO_DATE_TIME
    val pastDate = ZonedDateTime.parse(pastDateString, formatter)

    val currentDate = Instant.now().atZone(pastDate.zone)

    val period = Period.between(pastDate.toLocalDate(), currentDate.toLocalDate())

    val years = period.years
    val months = period.months
    val days = period.days

    return if (years > 0) {
        context.getString(R.string.years_ago, years)
    } else if (months > 0) {
        context.getString(R.string.months_ago, months)
    } else if (days > 1) {
        context.getString(R.string.days_ago, days)
    } else if (days == 1) {
        context.getString(R.string.yesterday)
    } else {
        context.getString(R.string.today)
    }
}