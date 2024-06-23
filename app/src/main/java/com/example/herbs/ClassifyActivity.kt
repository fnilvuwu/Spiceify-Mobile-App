package com.example.herbs

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.example.herbs.databinding.ActivityClassifyBinding
import java.io.IOException

class ClassifyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClassifyBinding

    private var imageSize = 150
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private var picture: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClassifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the ActivityResultLauncher
        cameraLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                onCameraActivityResult(result)
            }
        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                onGalleryActivityResult(result)
            }

        binding.cameraButton.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraLauncher.launch(cameraIntent)
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    100
                )
                Toast.makeText(this, "Permission denied, please enable it via settings", Toast.LENGTH_SHORT).show()
            }
        }

        binding.galleryButton.setOnClickListener {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            // Start the activity using the launcher
            galleryLauncher.launch(galleryIntent)
        }

        binding.imageint.setOnClickListener {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            // Start the activity using the launcher
            galleryLauncher.launch(galleryIntent)
        }

        binding.classifyButton.setOnClickListener {
            if (picture == null) {
                Toast.makeText(this, "Please pick an image or take a photo", Toast.LENGTH_SHORT).show()

            } else {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("picture", picture)
                startActivity(intent)
            }
        }

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    @Suppress("DEPRECATION")
    private fun onCameraActivityResult(result: androidx.activity.result.ActivityResult) {

        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            if (data != null && data.hasExtra("data")) {
                //Non-deprecated method require API 33 (Tiramisu)
                //Use the type-safer getParcelableExtra(String, Class) starting from Android Build.VERSION_CODES.TIRAMISU.
                var imageData = data.getParcelableExtra<Bitmap>("data")
                val dimension = imageData!!.width.coerceAtMost(imageData.height)
                imageData = ThumbnailUtils.extractThumbnail(imageData, dimension, dimension)
                //val intent = Intent(this, ResultActivity::class.java)
                picture = Bitmap.createScaledBitmap(imageData, imageSize, imageSize, false)
                Glide.with(this)
                    .load(picture)
                    .fitCenter()
                    .into(binding.imageint)
                //intent.putExtra("picture",picture)
                //startActivity(intent)
            }
        }
    }

    private fun onGalleryActivityResult(result: androidx.activity.result.ActivityResult) {
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            if (data != null && data.data != null) {
                val dat: Uri = data.data!!
                val imageData: Bitmap?
                try {
                    val source = ImageDecoder.createSource(this.contentResolver, dat)
                    imageData = ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
                        decoder.setTargetSampleSize(1) // shrinking by
                        decoder.isMutableRequired =
                            true // this resolves the hardware type of bitmap problem
                    }
                    //val intent = Intent(this, ResultActivity::class.java)
                    picture = Bitmap.createScaledBitmap(imageData, imageSize, imageSize, false)
                    Glide.with(this)
                        .load(picture)
                        .fitCenter()
                        .into(binding.imageint)
                    //intent.putExtra("picture", picture)
                    //startActivity(intent)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}