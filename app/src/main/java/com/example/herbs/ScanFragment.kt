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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.herbs.databinding.FragmentScanBinding
import java.io.IOException

class ScanFragment : Fragment() {
    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!

    private var imageSize = 150
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private var picture: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                    requireContext(),
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraLauncher.launch(cameraIntent)
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CAMERA),
                    100
                )
                Toast.makeText(requireContext(), "Permission denied, please enable it via settings", Toast.LENGTH_SHORT).show()
            }
        }

        binding.galleryButton.setOnClickListener {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
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
                Toast.makeText(requireContext(), "Please pick an image or take a photo", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(requireContext(), ResultActivity::class.java)
                intent.putExtra("picture", picture)
                startActivity(intent)
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun onCameraActivityResult(result: androidx.activity.result.ActivityResult) {
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val data: Intent? = result.data
            if (data != null && data.hasExtra("data")) {
                var imageData = data.getParcelableExtra<Bitmap>("data")
                val dimension = imageData!!.width.coerceAtMost(imageData.height)
                imageData = ThumbnailUtils.extractThumbnail(imageData, dimension, dimension)
                picture = Bitmap.createScaledBitmap(imageData, imageSize, imageSize, false)
                Glide.with(this)
                    .load(picture)
                    .fitCenter()
                    .into(binding.imageint)
            }
        }
    }

    private fun onGalleryActivityResult(result: androidx.activity.result.ActivityResult) {
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val data: Intent? = result.data
            if (data != null && data.data != null) {
                val dat: Uri = data.data!!
                val imageData: Bitmap?
                try {
                    val source = ImageDecoder.createSource(requireContext().contentResolver, dat)
                    imageData = ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
                        decoder.setTargetSampleSize(1)
                        decoder.isMutableRequired = true
                    }
                    picture = Bitmap.createScaledBitmap(imageData, imageSize, imageSize, false)
                    Glide.with(this)
                        .load(picture)
                        .fitCenter()
                        .into(binding.imageint)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
