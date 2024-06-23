package com.example.herbs

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.herbs.databinding.ActivityResultBinding
import com.example.herbs.ml.SpiceClassifierModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private var imageSize = 150
    private lateinit var classes: Array<String>
    private lateinit var classesText: Array<String>
    private lateinit var classToText: Map<String, String>
    private var predictionText: String? = null
    private var prediction: String = "No Prediction"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        classes = SpiceConstants.classes
        classesText = SpiceConstants.classesText
        classToText = SpiceConstants.classToTextMap

        val extras = intent.extras
        val picture: Bitmap? = extras?.getParcelable("picture")

        if (picture != null) {
            // Now 'image' contains the Bitmap extra named "image"
            // You can use 'image' as needed in your activity
            classifyImage(picture)

            Glide.with(this)
                .load(picture)
                .fitCenter()
                .into(binding.ivClassifiedImage)
        }

        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.detailsButton.setOnClickListener {
            val intent = Intent(this, DetailSpiceActivity::class.java)
            intent.putExtra("name", predictionText)
            //intent.putExtra("type", predictionText)
            //intent.putExtra("picture", predictionText)
            //intent.putExtra("desc", predictionText)
            startActivity(intent)
        }
    }

    private fun classifyImage(image: Bitmap) {
        try {
            val model: SpiceClassifierModel = SpiceClassifierModel.newInstance(applicationContext)
            // Creates inputs for reference.
            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1, 150, 150, 3), DataType.FLOAT32)
            val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
            byteBuffer.order(ByteOrder.nativeOrder())
            val intValues = IntArray(imageSize * imageSize)
            image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
            var pixel = 0
            //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
            for (i in 0 until imageSize) {
                for (j in 0 until imageSize) {
                    val `val` = intValues[pixel++] // RGB
                    byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 255f))  // Normalize to [0, 1]
                    byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 255f))
                    byteBuffer.putFloat((`val` and 0xFF) * (1f / 255f))
                }
            }
            inputFeature0.loadBuffer(byteBuffer)

            // Runs model inference and gets result.
            val outputs: SpiceClassifierModel.Outputs = model.process(inputFeature0)
            val outputFeature0: TensorBuffer = outputs.outputFeature0AsTensorBuffer
            val confidences = outputFeature0.floatArray
            // find the index of the class with the biggest confidence.
            var maxPos = 0
            var maxConfidence = 0f
            for (i in confidences.indices) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i]
                    maxPos = i
                }
            }
            prediction = classes[maxPos]
            predictionText = classToText[prediction]
            binding.tvResult.text = predictionText
            // Releases model resources if no longer used.
            model.close()
            Toast.makeText(this, "Predict is successful", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            // TODO Handle the exception
            Toast.makeText(this, "Fail to predict, an error had occured", Toast.LENGTH_SHORT).show()
        }
    }

}