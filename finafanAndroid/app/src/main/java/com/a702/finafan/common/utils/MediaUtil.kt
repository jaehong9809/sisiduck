package com.a702.finafan.common.utils

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ImageDecoder
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import kotlin.math.roundToInt

object MediaUtil {

    private const val IMAGE_SIZE = 1024 // 이미지 최소 사이즈

    fun openAlbum(resultIntent: ActivityResultLauncher<Intent>?) {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
            addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION)
        }
        resultIntent?.launch(intent)
    }

    fun resizeBitmap(context: Context, imgUri: Uri): Bitmap? {
        return try {
            val selPhoto = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, imgUri))
            } else {
                @Suppress("DEPRECATION")
                MediaStore.Images.Media.getBitmap(context.contentResolver, imgUri)
            }

            Log.d("MediaUtil", "resizeBitmap originWidth: ${selPhoto.width}, originHeight: ${selPhoto.height}")
            resizeDownBySideLength(selPhoto, IMAGE_SIZE).also {
                Log.d("MediaUtil", "resizeBitmap resizeWidth: ${it?.width}, resizeHeight: ${it?.height}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun resizeBitmapByScale(bitmap: Bitmap, scale: Float): Bitmap? {
        if (scale >= 1.0f) return bitmap

        val width = (bitmap.width * scale).roundToInt()
        val height = (bitmap.height * scale).roundToInt()
        val target = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        Canvas(target).apply {
            scale(scale, scale)
            drawBitmap(bitmap, 0f, 0f, Paint(Paint.FILTER_BITMAP_FLAG or Paint.DITHER_FLAG))
        }
        return target
    }

    private fun resizeDownBySideLength(bitmap: Bitmap, maxLength: Int): Bitmap? {
        val scale = minOf(maxLength.toFloat() / bitmap.width, maxLength.toFloat() / bitmap.height)
        return resizeBitmapByScale(bitmap, scale)
    }

    fun checkPermission(context: Context): Boolean {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(requestPermissionLauncher: ActivityResultLauncher<String>) {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
        requestPermissionLauncher.launch(permission)
    }

    fun Uri.asMultipart(name: String, contentResolver: ContentResolver): MultipartBody.Part? {
        contentResolver.query(this, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                val displayName = if (index != -1) cursor.getString(index) else "temp_file"

                val inputStream = contentResolver.openInputStream(this) ?: return null
                val byteArray = inputStream.use { it.readBytes() }

                val requestBody = object : RequestBody() {
                    override fun contentType(): MediaType? {
                        return contentResolver.getType(this@asMultipart)?.toMediaType()
                    }

                    override fun writeTo(sink: BufferedSink) {
                        sink.write(byteArray)
                    }
                }

                return MultipartBody.Part.createFormData(name, displayName, requestBody)
            }
        }
        return null
    }


}
