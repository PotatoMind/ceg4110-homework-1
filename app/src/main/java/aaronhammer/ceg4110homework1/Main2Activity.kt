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
import android.graphics.drawable.BitmapDrawable
import android.content.pm.PackageManager
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import java.io.File
import java.util.*


class Main2Activity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    /*
     * If the clear or save buttons are clicked, their respective functions are called
     */
    override fun onClick(v: View?) {
        val finger = findViewById<FingerPaintImageView>(R.id.finger)
        when (v?.id) {
            R.id.buttonClear -> finger.clear()
            R.id.buttonSave -> saveImage()
        }
    }

    /*
     * Gets the result of the permission request. Will tell the program that the permission was granted or not
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                } else {
                    // Do nothing
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }

    /*
     * Checks to see if the permissions to saved images are there. If not, it requests the permission
     */
    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        1)
            }
        } else {
            // Permission has already been granted
        }
    }

    /*
     * Gets the bitmap, asks for saving permissions, and then saves to external storage
     */
    fun saveImage() {
        val finger = findViewById<FingerPaintImageView>(R.id.finger)
        val drawable = finger.drawable
        val bitmapDrawable = drawable as BitmapDrawable
        val bitmap = bitmapDrawable.bitmap
        val fileName = "AwesomePic"
        checkPermission()
        saveToExternalStorage(bitmap, fileName)
    }

    /*
     * Saves bitmap to external storage
     */
    private fun saveToExternalStorage(bitmapImage: Bitmap, fileName: String): String {
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
            val toast = Toast.makeText(this, "Picture couldn't be saved!", Toast.LENGTH_LONG)
            toast.show()
        }

        //sendBroadcast(Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())))

        val toast = Toast.makeText(this, "Picture saved in $root/saved_images", Toast.LENGTH_LONG)
        toast.show()
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

    /*
     * Creates the action bar menu options when it is opened
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item, menu)
        return true
    }

    /*
     * When an option in the action bar is selected, it changes the activity of the program
     */
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_color -> {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.action_drawing -> {
            val intent = Intent(this, Main2Activity::class.java)
            startActivity(intent)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}