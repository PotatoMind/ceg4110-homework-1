package aaronhammer.ceg4110homework1

import android.Manifest
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
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import java.io.File
import java.util.*


class Main2Activity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    override fun onClick(v: View?) {
        val finger = findViewById<FingerPaintImageView>(R.id.finger)
        when (v?.id) {
            R.id.buttonClear -> finger.clear()
            R.id.buttonSave -> saveImage()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }

    fun checkPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        1)

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }

    fun saveImage() {
        val finger = findViewById<FingerPaintImageView>(R.id.finger)
        val drawable = finger.drawable
        val bitmapDrawable = drawable as BitmapDrawable
        val bitmap = bitmapDrawable.bitmap
        val fileName = "AwesomePic"
        checkPermission()
        saveToExternalStorage(bitmap, fileName)
    }

    private fun saveToExternalStorage(bitmapImage: Bitmap, fileName: String): String {
        // path to /data/data/yourapp/app_data/imageDir
        val root = Environment.getExternalStorageDirectory().toString()
        val dir = File("$root/saved_images")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val rnd = Random()
        val num = rnd.nextInt(10000)
        val file = File(dir, "$fileName-$num.png")
        if (file.exists())
            file.delete()
        try {
            val out = FileOutputStream(file)
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //sendBroadcast(Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())))
        return file.absolutePath
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