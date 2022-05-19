package com.dharmesh.bizzbullindiaproject.common

import android.content.Context
import com.google.gson.Gson

object Preferences {
    const val APP_PREFERENCES = "BizbullsIndia2022"
    const val IS_LOGIN = "islogin"
    const val USER_ID = "userid"
    const val TOKEN = "token"
    const val USER_FIRSTNAME = "firstname"
    const val USER_LASTNAME = "lastname"
    const val USER_TYPE = "usertype"
    const val EMAIL = "email"
    const val MOBILE_NUMBER = "mobilenumber"
    const val REGISTRATION_DATE = "registration_date"
    const val DESIGNATION = "DESIGNATION"
    const val IP_ADDRESS = "IPADDRESS"
    const val LOCATION = "LOCATION"
    const val DEVICEACTION = "DEVICEACTION"
    const val DEVICEUSED = "DEVICEUSED"

    //static data
    const val DOC_AADHAR_CARD = "aadharcard"
    const val DOC_EDUCATION_CERTIFICATE = "educationcertificate"
    const val DOC_PANCARD = "pancard"
    const val DOC_PASSBOOK = "passbook"
    const val DOC_LICENSE = "license"
    const val VISIT_CURRENT = "currentvisit"
    const val VISIT_CLIENTID = "clientid"
    const val VISIT_CLIENTNAME = "clientname"
    const val VISIT_APPOINTMENTID = "appointmentid"
    const val VISIT_TYPE = "visittype"
    const val VISIT_TYPEID = "visitid"
    fun setValueString(context: Context, Key: String?, Value: String?) {
        val settings = context.getSharedPreferences(
            APP_PREFERENCES, 0
        )
        val editor = settings.edit()
        editor.putString(Key, Value)
        editor.apply()
    }

    fun getValueString(context: Context, Key: String?): String? {
        val settings = context.getSharedPreferences(APP_PREFERENCES, 0)
        return settings.getString(Key, "")
    }

    fun setValueBoolean(context: Context, Key: String?, Value: Boolean) {
        val settings = context.getSharedPreferences(APP_PREFERENCES, 0)
        settings.edit().putBoolean(Key, Value).apply()
    }

    fun getValueBoolean(context: Context?, Key: String?, Default: Boolean): Boolean {
        return if (context != null) {
            val settings = context.getSharedPreferences(
                APP_PREFERENCES,
                0
            )
            settings.getBoolean(Key, Default)
        } else {
            false
        }
    }

    fun setValueInteger(context: Context, Key: String?, Value: Int) {
        val settings = context.getSharedPreferences(
            APP_PREFERENCES, 0
        )
        val editor = settings.edit()
        editor.putInt(Key, Value)
        editor.apply()
    }

    fun getValueInteger(context: Context?, Key: String?, Default: Int): Int {
        return if (context != null) {
            val settings = context.getSharedPreferences(
                APP_PREFERENCES,
                0
            )
            settings.getInt(Key, Default)
        } else {
            0
        }
    }

    fun saveMap(context: Context, key: String?, map: Map<String?, String?>?) {
        val settings = context.getSharedPreferences(APP_PREFERENCES, 0)
        val editor = settings.edit()
        val gson = Gson()
        val json = gson.toJson(map)
        editor.putString(key, json)
        editor.apply()
    }

    fun getMap(context: Context, key: String?): String? {
        val settings = context.getSharedPreferences(APP_PREFERENCES, 0)
        return settings.getString(key, "")
    }
}