package com.dharmesh.bizzbullindiaproject.common

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.dharmesh.bizzbullindiaproject.R
import com.dharmesh.bizzbullindiaproject.SignupActivity
import com.google.gson.GsonBuilder
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

class AppConstants(var context: Context) {
    //Get color code from color.
    fun getColorString(color: Int): String {
        return String.format("#%06x", ContextCompat.getColor(context, color) and 0xffffff)
    }

    //set image drawable dynamic
    fun getDrawable(resId: Int): Drawable? {
        return ContextCompat.getDrawable(context, resId)
    }

    //set image color dynamic
    fun getColor(colorId: Int): Int {
        return ResourcesCompat.getColor(context.resources, colorId, null)
    }

    @SuppressLint("NewApi", "Recycle")
    fun getFilePath(uri: Uri): String? {
        var uri = uri
        var selection: String? = null
        var selectionArgs: Array<String>? = null
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(
                context.applicationContext,
                uri
            )
        ) {
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                uri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)
                )
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                if ("image" == type) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                selection = "_id=?"
                selectionArgs = arrayOf(
                    split[1]
                )
            }
        }
        if ("content".equals(uri.scheme, ignoreCase = true)) {
            val projection = arrayOf(
                MediaStore.Images.Media.DATA
            )
            var cursor: Cursor? = null
            try {
                cursor = context.contentResolver
                    .query(uri, projection, selection, selectionArgs, null)
                val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index)
                }
            } catch (ignored: Exception) {
            }
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }

    //show toast message
    fun toast(msg: String?) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    //Get value form saved preference
    fun getPrefValue(key: String?): String? {
        return Preferences.getValueString(context, key)
    }

    //save value to the preference
    fun setPrefValue(key: String?, value: String?) {
        Preferences.setValueString(context, key, value)
    }

    //save value to the preference
    fun setPrefValue(key: String?, value: Boolean) {
        Preferences.setValueBoolean(context, key, value)
    }

    //save value to the preference
    fun setPrefValue(key: String?, value: Int) {
        Preferences.setValueInteger(context, key, value)
    }

    //Get value form saved preference
    fun getPrefBooleanValue(key: String?): Boolean {
        return Preferences.getValueBoolean(context, key, false)
    }

    //Get value form saved preference
    fun getPrefIntValue(key: String?): Int {
        return Preferences.getValueInteger(context, key, 0)
    }

    fun getText(editText: EditText): String {
        return editText.text.toString().trim { it <= ' ' }
    }

    //Validate email
    fun validateEmail(email: String?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    //Print API response from server
    fun printRes(response: Response<*>) {
        Log.e("TAG", "Url : " + response.raw().request.url)
        Log.e(
            "TAG",
            "Response : " + GsonBuilder().setPrettyPrinting().create().toJson(response.body())
        )
    }

    //get path to copy folder
    val path: String
        get() {
            val basePath = context.getString(R.string.app_name).replace(" ", "_") + "/"
            createPath(basePath)
            val state = Environment.getExternalStorageState()
            return if (Environment.MEDIA_MOUNTED == state) {
                File(
                    Environment.getExternalStorageDirectory(),
                    basePath + randomImageName
                ).absolutePath
            } else {
                File(context.filesDir, basePath + randomImageName).absolutePath
            }
        }

    //if copy folder path not exits then create dir
    fun createPath(path: String?) {
        val state = Environment.getExternalStorageState()
        val file: File
        file = if (Environment.MEDIA_MOUNTED == state) {
            File(Environment.getExternalStorageDirectory(), path)
        } else {
            File(context.filesDir, path)
        }
        if (!file.exists()) file.mkdirs()
    }

    //get name of copied file name
    fun getRandomImageName(context: Context): String {
        val r = Random()
        val i1 = r.nextInt(1000 - 1) + 65
        return context.getString(R.string.app_name).replace(" ", "_") + "_" + i1 + ".jpg"
    }

    //Get current app dir
    fun getAppDir(context: Context): File {
        val basedPath = context.getString(R.string.app_name).replace(" ", "_") + "/compress/"
        val state = Environment.getExternalStorageState()
        val file: File
        file = if (Environment.MEDIA_MOUNTED == state) {
            File(Environment.getExternalStorageDirectory(), basedPath)
        } else {
            File(context.filesDir, basedPath)
        }
        return file
    }

    fun resetPrefrences() {
        setPrefValue(Preferences.IS_LOGIN, false)
        setPrefValue(Preferences.USER_ID, "")
        setPrefValue(Preferences.TOKEN, "")
        setPrefValue(Preferences.USER_FIRSTNAME, "")
        setPrefValue(Preferences.USER_LASTNAME, "")
        setPrefValue(Preferences.USER_TYPE, "")
        setPrefValue(Preferences.EMAIL, "")
        setPrefValue(Preferences.MOBILE_NUMBER, "")
        setPrefValue(Preferences.REGISTRATION_DATE, "")
    }

    fun capFirstLater(str: String): String {
        return if (str != "") str.substring(0, 1)
            .uppercase(Locale.getDefault()) + str.substring(1) else ""
    }

    fun forcelogout() {
        resetPrefrences()
        val intent = Intent(context, SignupActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
        toast("Logout Successfully!")
    }

    fun isverifynull(value: String?): String {
        return value ?: ""
    }

    companion object {
        const val root_link = "http://bizbullsindia.in/staging/BizbullsIndiaWeb/api/" // test

        //public static final String root_link = "http://bizbullsindia.in/staging/BizbullsIndiaWeb/api/";
        const val APP_NAME = "Biz Bulls"
        fun getMd5(input: String): String {
            return try {

                // Static getInstance method is called with hashing MD5
                val md = MessageDigest.getInstance("MD5")

                // digest() method is called to calculate message digest
                // of an input digest() return array of byte
                val messageDigest = md.digest(input.toByteArray())

                // Convert byte array into signum representation
                val no = BigInteger(1, messageDigest)

                // Convert message digest into hex value
                var hashtext = no.toString(16)
                while (hashtext.length < 32) {
                    hashtext = "0$hashtext"
                }
                hashtext
            } // For specifying wrong message digest algorithms
            catch (e: NoSuchAlgorithmException) {
                throw RuntimeException(e)
            }
        }

        fun storeImageToSDCard(processedBitmap: Bitmap, context: Context): String {
            var path = ""
            try {
                // TODO Auto-generated method stub
                val output: OutputStream
                try {
                    val file = createFile(getFile(context).absolutePath)
                    output = FileOutputStream(file)
                    processedBitmap.compress(Bitmap.CompressFormat.PNG, 100, output)
                    output.flush()
                    output.close()
                    path = file.absolutePath
                } catch (e: Exception) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }
            } catch (e: Exception) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
            Log.e(context.javaClass.simpleName, "Save image path : $path")
            return path
        }

        @Throws(IOException::class)
        fun createFile(absolutePath: String?): File {
            val dir = File(absolutePath)
            dir.mkdirs()
            val file = File(dir, randomImageName)
            return if (file.exists()) {
                file.delete()
                file.createNewFile()
                file
            } else {
                file.createNewFile()
                file
            }
        }

        //get name of copied file name
        val randomImageName: String
            get() {
                val r = Random()
                val i1 = r.nextInt(1000 - 1) + 65
                return APP_NAME.replace(" ", "_") + "_" + i1 + ".jpg"
            }

        fun getFile(context: Context): File {
            val state = Environment.getExternalStorageState()
            return if (Environment.MEDIA_MOUNTED == state) {
                File(Environment.getExternalStorageDirectory(), APP_NAME)
            } else {
                File(context.filesDir, APP_NAME)
            }
        }

        fun isExternalStorageDocument(uri: Uri): Boolean {
            return "com.android.externalstorage.documents" == uri.authority
        }

        fun isDownloadsDocument(uri: Uri): Boolean {
            return "com.android.providers.downloads.documents" == uri.authority
        }

        fun isMediaDocument(uri: Uri): Boolean {
            return "com.android.providers.media.documents" == uri.authority
        }
    }
}