package com.dharmesh.bizzbullindiaproject.common

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Appitsimple11 on 28-03-2017.
 */
object ActivityHelper {
    fun initialize(activity: AppCompatActivity) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}