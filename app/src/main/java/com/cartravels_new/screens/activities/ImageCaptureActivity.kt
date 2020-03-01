package com.cartravels_new.screens.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cartravels_new.R
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_image_capture.*
import java.lang.Exception
import android.content.Intent
import android.content.res.Resources
import android.graphics.*
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.view.View
import android.graphics.RectF
import android.net.Uri
import android.widget.*
import android.provider.MediaStore.Images
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


private var txtDescription: TextView? = null


class ImageCaptureActivity : AppCompatActivity() {
    private var filePath: String? = null
    var path: String? = null
    var output: Bitmap? = null
    var fileUri: Uri? = null

    val fileName: String = "CarTravelsImages"

    val fileExtension: String = "jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_image_capture)

            btnCapturePicture.setOnClickListener {

                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
            }


        } catch (e: Exception) {
            e.message
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                fileUri = result.getUri();
                addImageWaterMark()
            }
        }

    }

    private fun previewCapturedImage() {
        try {

            imgPreview.visibility = View.VISIBLE

            imgPreview.setImageBitmap(output)
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss",
                    Locale.getDefault()).format(Date())
            saveImage(output!!, "carTravelsImage_" + timeStamp)

        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

    }

    private fun saveImage(finalBitmap: Bitmap, image_name: String) {
        val root = Environment.getExternalStorageDirectory().toString()
        val myDir = File("$root/CarTravlesImages/")

        filePath = "$root/CarTravlesImages/$image_name.jpg"

        myDir.mkdirs()
        val fname = "$image_name.jpg"
        val file = File(myDir, fname)
        if (file.exists()) file.delete()
        Log.i("LOAD", root + fname)
        try {
            val out = FileOutputStream(file)
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    private fun addImageWaterMark() {
        var bitmap: Bitmap? = null

        try {

            val f = File(fileUri?.path)
            if (f.exists()) {
                val options = BitmapFactory.Options()
                options.inPreferredConfig = Bitmap.Config.ARGB_8888
                try {
                    bitmap = BitmapFactory.decodeStream(FileInputStream(f), null, options)
                    output = addWatermark(resources, bitmap!!)
                    previewCapturedImage()
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    fun addWatermark(res: Resources, source: Bitmap): Bitmap {
        val w: Int
        val h: Int
        val c: Canvas
        val paint: Paint
        val bmp: Bitmap
        val watermark: Bitmap

        val matrix: Matrix
        val scale: Float
        val r: RectF

        w = source.width
        h = source.height

        bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)

        paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG or Paint.FILTER_BITMAP_FLAG)

        c = Canvas(bmp)
        c.drawBitmap(source, 0f, 0f, paint)

        watermark = BitmapFactory.decodeResource(res, R.drawable.logo)
        scale = (h.toFloat() * 0.08 / watermark.height.toFloat()).toFloat()

        matrix = Matrix()
        matrix.postScale(scale, scale)
        r = RectF(0f, 0f, watermark.width.toFloat(), watermark.height.toFloat())
        matrix.mapRect(r)
        matrix.postTranslate(w - r.width(), h - r.height())

        c.drawBitmap(watermark, matrix, paint)
        watermark.recycle()

        return bmp

    }


}

