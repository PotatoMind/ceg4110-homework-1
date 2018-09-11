package aaronhammer.ceg4110homework1

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.util.*
import android.view.MenuInflater
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val txtView = findViewById<EditText>(R.id.editText)
            val redTextView = findViewById<TextView>(R.id.textViewRed)
            val greenTextView = findViewById<TextView>(R.id.textViewGreen)
            val blueTextView = findViewById<TextView>(R.id.textViewBlue)
            val hexTextView = findViewById<TextView>(R.id.textViewHex)
            val rnd = Random()
            val xr = rnd.nextInt(256)
            val xg = rnd.nextInt(256)
            val xb = rnd.nextInt(256)
            val hex = String.format("#%02x%02x%02x", xr, xg, xb)
            redTextView.text = getString(
                    R.string.colorText,
                    "Red",
                    xr.toString()
            )
            greenTextView.text = getString(
                    R.string.colorText,
                    "Green",
                    xg.toString()
            )
            blueTextView.text = getString(
                    R.string.colorText,
                    "Blue",
                    xb.toString()
            )
            hexTextView.text = getString(
                    R.string.colorText,
                    "Hex",
                    hex
            )
            val color = Color.argb(255, xr, xg, xb)
            txtView.setTextColor(color)
            val chngColor = Toast.makeText(this, "I'm a potato", Toast.LENGTH_LONG)
            chngColor.show()
        }
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
