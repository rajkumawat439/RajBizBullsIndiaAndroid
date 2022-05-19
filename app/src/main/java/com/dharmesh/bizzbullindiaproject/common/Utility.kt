package com.dharmesh.bizzbullindiaproject.common

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.media.MediaScannerConnection
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.*
import androidx.annotation.RawRes
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.dharmesh.bizzbullindiaproject.BuildConfig
import com.dharmesh.bizzbullindiaproject.R
import com.google.gson.Gson
import java.io.*
import java.util.*

object Utility {
    private const val TAG = "Utility"
    private val progressDialog: Dialog? = null
    val gson = Gson()
    private val typedValue = TypedValue()
    fun share(context: Context, title: String?, extra: String?) {
        try {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, extra)
            intent.putExtra(Intent.EXTRA_SUBJECT, title)
            context.startActivity(Intent.createChooser(intent, "Share"))
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun MergeColors(backgroundColor: Int, foregroundColor: Int): Int {
        val ALPHA_CHANNEL: Byte = 24
        val RED_CHANNEL: Byte = 16
        val GREEN_CHANNEL: Byte = 8
        val BLUE_CHANNEL: Byte = 0
        var a = 0
        var r = 0
        var g = 0
        var b = 0
        try {
            val ap1 = (backgroundColor shr ALPHA_CHANNEL.toInt() and 0xff).toDouble() / 255.0
            val ap2 = (foregroundColor shr ALPHA_CHANNEL.toInt() and 0xff).toDouble() / 255.0
            val ap = ap2 + ap1 * (1 - ap2)
            val amount1 = ap1 * (1 - ap2) / ap
            val amount2 = amount1 / ap
            a = (ap * 255.0).toInt() and 0xff
            r = ((backgroundColor shr RED_CHANNEL.toInt() and 0xff).toFloat() * amount1 +
                    (foregroundColor shr RED_CHANNEL.toInt() and 0xff).toFloat() * amount2).toInt() and 0xff
            g = ((backgroundColor shr GREEN_CHANNEL.toInt() and 0xff).toFloat() * amount1 +
                    (foregroundColor shr GREEN_CHANNEL.toInt() and 0xff).toFloat() * amount2).toInt() and 0xff
            b = ((backgroundColor and 0xff).toFloat() * amount1 +
                    (foregroundColor and 0xff).toFloat() * amount2).toInt() and 0xff
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return a shl ALPHA_CHANNEL.toInt() or (r shl RED_CHANNEL.toInt()) or (g shl GREEN_CHANNEL.toInt()) or (b shl BLUE_CHANNEL.toInt())
    }

    fun IsInternetConnected(context: Context): Boolean {
        var isConnected = false
        try {
            val connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val info = connectivityManager.allNetworkInfo
                if (info != null) {
                    for (i in info.indices) {
                        if (info[i].state == NetworkInfo.State.CONNECTED) {
                            isConnected = true
                        }
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return isConnected
    }

    fun getProgressDialogCircle(context: Context?): ProgressDialog? {
        var progressDialog: ProgressDialog? = null
        try {
            //progressDialog = new ProgressDialog(context, R.style.DialogStyle);
            /* progressDialog = new ProgressDialog(context);
            //progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(context.getResources().getString(R.string.loading));//context.getString(R.string.loading)
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setIndeterminate(true);*/
            // show it
            progressDialog = ProgressDialog(context)
            progressDialog.setCancelable(false)
            progressDialog.setMessage("")
            //            progressDialog.setMessage(context.getResources().getString(R.string.loading));
            progressDialog.isIndeterminate = true
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return progressDialog
    }

    fun saveImageGallery(
        context: Context,
        bitmap: Bitmap,
        filename: String?,
        imgActualPath: String?
    ): String {
        var file: File? = null
        var galleryDirectory: File
        val directory: File
        val root: String
        val byteArrayData: ByteArray
        val localBitMap: Bitmap
        val fos: FileOutputStream
        var isGallery = false
        try {
            root = Environment.getExternalStorageDirectory().toString()
            directory = File(root + "/" + context.getString(R.string.app_name))
            if (!directory.exists()) directory.mkdirs()

            /* galleryDirectory = directory.getParentFile();
            galleryDirectory = new File(directory + "/" + context.getString(R.string.app_name) + " Images");
            if (!galleryDirectory.exists())
                galleryDirectory.mkdirs();*/if (filename != null) {
                if (filename.endsWith(".jpg")
                    || filename.endsWith(".png")
                    || filename.endsWith(".jpeg")
                ) {
                    file = File(directory, filename)
                    isGallery = true
                }
            }
            if (file != null && file.exists()) file.delete()
            try {
                if (file != null) {
                    fos = FileOutputStream(file, false)
                    if (isGallery) {
                        if (imgActualPath != null) {
                            localBitMap = BitmapFactory.decodeFile(imgActualPath)
                            val stream = ByteArrayOutputStream()
                            localBitMap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                            byteArrayData = stream.toByteArray()
                            fos.write(byteArrayData)
                        } else bitmap.compress(Bitmap.CompressFormat.JPEG, 95, fos)
                    }
                    fos.flush()
                    fos.close()
                    MediaScannerConnection
                        .scanFile(context, arrayOf(file.absolutePath), arrayOf("image/jpeg"), null)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            } catch (ex: OutOfMemoryError) {
                ex.printStackTrace()
            }
            refreshGallery(context, "$directory/$filename")
        } catch (e: Exception) {
            e.printStackTrace()
            // TODO: handle exception
        }
        return ""
    }

    fun saveVideoToInternalStorage(context: Context, filePath: String?) {
        val newfile: File
        try {
            val currentFile = File(filePath)
            val root = Environment.getExternalStorageDirectory().toString()
            val wallpaperDirectory = File(root + "/" + context.getString(R.string.app_name))
            //File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + context.getString(R.string.app_name));
            newfile =
                File(wallpaperDirectory, Calendar.getInstance().timeInMillis.toString() + ".mp4")
            if (!wallpaperDirectory.exists()) {
                wallpaperDirectory.mkdirs()
            }
            if (currentFile.exists()) {
                val `in`: InputStream = FileInputStream(currentFile)
                val out: OutputStream = FileOutputStream(newfile)

                // Copy the bits from instream to outstream
                val buf = ByteArray(1024)
                var len: Int
                while (`in`.read(buf).also { len = it } > 0) {
                    out.write(buf, 0, len)
                }
                `in`.close()
                out.close()
                Log.e(TAG, "saveVideoToInternalStorage Video file saved successfully.")
            } else {
                Log.e(TAG, "saveVideoToInternalStorage Video saving failed. Source file missing.")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(
                TAG,
                "saveVideoToInternalStorage Video saving failed. Source file missing.Exception - " + e.message
            )
        }
    }

    private fun refreshGallery(context: Context, url: String) {
        try {
            val file = File(url)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                val f =
                    File("file://" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES))
                val contentUri = Uri.fromFile(file)
                mediaScanIntent.data = contentUri
                context.sendBroadcast(mediaScanIntent)
            } else {
                context.sendBroadcast(Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse(url)))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun readFileFromRawDirectory(context: Context, @RawRes id: Int): String {
        val iStream: InputStream
        var byteStream: ByteArrayOutputStream? = null
        try {
            iStream = context.resources.openRawResource(id)
            val buffer = ByteArray(iStream.available())
            iStream.read(buffer)
            byteStream = ByteArrayOutputStream()
            byteStream.write(buffer)
            byteStream.close()
            iStream.close()
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return byteStream.toString()
    }

    fun loadGlide(
        context: Context,
        profileIV: ImageView?,
        profileImagePath: String?,
        logo_border: Int
    ) {
        try {
            context.resources.getValue(R.dimen.imageLoaderCircleStroke, typedValue, true)
            val strokeWidth = typedValue.float
            context.resources.getValue(R.dimen.imageLoaderCircleRadius, typedValue, true)
            val centerRadius = typedValue.float
            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = strokeWidth
            circularProgressDrawable.centerRadius = centerRadius
            circularProgressDrawable.setColorSchemeColors(context.resources.getColor(R.color.orange_700))
            circularProgressDrawable.start()
            Glide.with(context)
                .load(profileImagePath)
                .transition(DrawableTransitionOptions.withCrossFade()) //faded animation before set image
                .apply(
                    RequestOptions.placeholderOf(circularProgressDrawable)
                        .error(logo_border)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .fitCenter()
                        .skipMemoryCache(false)
                        .dontAnimate()
                )
                .into(profileIV!!)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun loadGlideVideo(
        context: Context,
        profileIV: ImageView?,
        profileImagePath: String?,
        logo_border: Int
    ) {
        try {
            context.resources.getValue(R.dimen.imageLoaderCircleStroke, typedValue, true)
            val strokeWidth = typedValue.float
            context.resources.getValue(R.dimen.imageLoaderCircleRadius, typedValue, true)
            val centerRadius = typedValue.float
            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = strokeWidth
            circularProgressDrawable.centerRadius = centerRadius
            circularProgressDrawable.setColorSchemeColors(context.resources.getColor(R.color.orange_700))
            circularProgressDrawable.start()
            Glide.with(context)
                .asBitmap()
                .load(profileImagePath)
                .apply(
                    RequestOptions.placeholderOf(circularProgressDrawable)
                        .error(logo_border)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .fitCenter()
                        .skipMemoryCache(false)
                        .dontAnimate()
                )
                .into(profileIV!!)
            Glide
                .with(context)
                .asBitmap()
                .load(profileImagePath)
                .into(profileIV)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun loadGlideSkipCache(
        context: Context,
        profileIV: ImageView?,
        profileImagePath: String?,
        logo_border: Int
    ) {
        try {
            context.resources.getValue(R.dimen.imageLoaderCircleStroke, typedValue, true)
            val strokeWidth = typedValue.float
            context.resources.getValue(R.dimen.imageLoaderCircleRadius, typedValue, true)
            val centerRadius = typedValue.float
            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = strokeWidth
            circularProgressDrawable.centerRadius = centerRadius
            //circularProgressDrawable.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            circularProgressDrawable.setColorSchemeColors(context.resources.getColor(R.color.orange_700))
            circularProgressDrawable.start()
            Glide.with(context)
                .load(profileImagePath)
                .transition(DrawableTransitionOptions.withCrossFade()) //faded animation before set image
                .apply(
                    RequestOptions.placeholderOf(circularProgressDrawable)
                        .error(logo_border)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .fitCenter()
                        .skipMemoryCache(true)
                        .dontAnimate()
                )
                .into(profileIV!!)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun loadGlideSkipCacheProgress(
        context: Context,
        profileIV: ImageView?,
        profileImagePath: String?,
        logo_border: Int,
        progressBar: ProgressBar
    ) {
        try {
            context.resources.getValue(R.dimen.imageLoaderCircleStroke, typedValue, true)
            val strokeWidth = typedValue.float
            context.resources.getValue(R.dimen.imageLoaderCircleRadius, typedValue, true)
            val centerRadius = typedValue.float
            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = strokeWidth
            circularProgressDrawable.centerRadius = centerRadius
            circularProgressDrawable.setColorSchemeColors(context.resources.getColor(R.color.orange_700))
            circularProgressDrawable.start()
            Glide.with(context)
                .load(profileImagePath)
                .transition(DrawableTransitionOptions.withCrossFade()) //faded animation before set image
                .apply(
                    RequestOptions.placeholderOf(circularProgressDrawable)
                        .error(logo_border)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .fitCenter()
                        .skipMemoryCache(true)
                        .dontAnimate()
                )
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable?>,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any,
                        target: Target<Drawable?>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }
                })
                .into(profileIV!!)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun loadGlideWithCircleSkipCache(
        context: Context,
        profileIV: ImageView,
        profileImagePath: String?,
        logo_border: Int
    ) {
        try {
            profileIV.setBackgroundColor(context.resources.getColor(android.R.color.transparent))
            Glide.with(context)
                .load(profileImagePath) //.transition(DrawableTransitionOptions.withCrossFade())
                .apply(
                    RequestOptions.placeholderOf(logo_border)
                        .error(logo_border)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .fitCenter()
                        .circleCrop()
                        .skipMemoryCache(true)
                        .dontAnimate()
                )
                .into(profileIV)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun loadGlideWithCircleSkipCacheProgress(
        context: Context?,
        profileIV: ImageView?,
        profileImagePath: String?,
        logo_border: Int,
        progressBar: ProgressBar
    ) {
        try {
            Glide.with(context!!)
                .load(profileImagePath)
                .apply(
                    RequestOptions.placeholderOf(logo_border)
                        .error(logo_border)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .fitCenter()
                        .circleCrop()
                        .skipMemoryCache(true)
                        .dontAnimate()
                )
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable?>,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any,
                        target: Target<Drawable?>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }
                })
                .into(profileIV!!)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    } /*public static String findImageURL(List<PaintsFile> files, int profileID) {
        String url = "";
        try {
            for (PaintsFile paintsFile : files) {
                try {

                    if (profileID == paintsFile.getId()) {
                        url = paintsFile.getDownloadUrl();
                        break;
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return url;
    }
*/
    private const val maxLogSize = 4050

    fun logD(tag: String, msg: String, tr: Throwable? = null) {
        if (BuildConfig.DEBUG) {
            if (tr != null) {
                msg.chunked(maxLogSize).forEach { Log.d(tag, it, tr) }
            } else
                msg.chunked(maxLogSize).forEach { Log.d(tag, it) }
        }
    }
}