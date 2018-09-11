package aaronhammer.ceg4110homework1

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main2.*
import tech.picnic.fingerpaintview.FingerPaintImageView
import java.io.FileOutputStream
import java.io.IOException
import android.graphics.drawable.BitmapDrawable
import android.content.ContextWrapper
import android.util.Log
import java.io.File


class Main2Activity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    override fun onClick(v: View?) {
        val finger = findViewById<FingerPaintImageView>(R.id.finger)
        when (v?.id) {
            R.id.buttonClear -> finger.clear()
            R.id.buttonSave -> saveImage()
        }
    }

    fun saveImage() {
        val finger = findViewById<FingerPaintImageView>(R.id.finger)
        val drawable = finger.drawable
        val bitmapDrawable = drawable as BitmapDrawable
        val bitmap = bitmapDrawable.bitmap
        val fileName = "AwesomePic.png"
        saveToInternalStorage(bitmap, fileName)
    }

    private fun saveToInternalStorage(bitmapImage: Bitmap, fileName: String): String {
        val cw = ContextWrapper(applicationContext)
        // path to /data/data/yourapp/app_data/imageDir
        val directory = cw.getDir("imageDir", MODE_PRIVATE)
        Log.d("Test", "/$directory")
        val myPath = File(directory, fileName)
        var os: FileOutputStream? = null
        try {
            os = FileOutputStream(myPath)
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, os)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                os!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return directory.absolutePath
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // my_child_toolbar is defined in the layout file
        setSupportActionBar(findViewById(R.id.my_toolbar2))

        // Get a support ActionBar corresponding to this toolbar and enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val clearButton = findViewById<Button>(R.id.buttonClear)
        val saveButton = findViewById<Button>(R.id.buttonSave)
        val red = findViewById<SeekBar>(R.id.seekBarRed)
        val green = findViewById<SeekBar>(R.id.seekBarGreen)
        val blue = findViewById<SeekBar>(R.id.seekBarBlue)
        val colorPreview = findViewById<TextView>(R.id.textView)
        red.setOnSeekBarChangeListener(this)
        green.setOnSeekBarChangeListener(this)
        blue.setOnSeekBarChangeListener(this)
        clearButton.setOnClickListener(this)
        saveButton.setOnClickListener(this)
        val color = Color.argb(255, 0, 0, 0)
        finger.strokeColor = color
        colorPreview.setBackgroundColor(color)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (seekBar?.id == R.id.seekBarRed || seekBar?.id == R.id.seekBarGreen || seekBar?.id == R.id.seekBarBlue) {
            val finger = findViewById<FingerPaintImageView>(R.id.finger)
            val colorPreview = findViewById<TextView>(R.id.textView)
            val red = findViewById<SeekBar>(R.id.seekBarRed)
            val green = findViewById<SeekBar>(R.id.seekBarGreen)
            val blue = findViewById<SeekBar>(R.id.seekBarBlue)
            val r = red.progress
            val g = green.progress
            val b = blue.progress
            val color = Color.argb(255, r, g, b)
            val hex = String.format("#%02x%02x%02x", r, g, b)
            finger.strokeColor = color
            colorPreview.setBackgroundColor(color)
            colorPreview.text = hex
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_color -> {
            val intent = Intent(this, MainActivity::class.java)
            // start your next activity
            startActivity(intent)
            true
        }

        R.id.action_drawing -> {
            val intent = Intent(this, Main2Activity::class.java)
            startActivity(intent)
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
}