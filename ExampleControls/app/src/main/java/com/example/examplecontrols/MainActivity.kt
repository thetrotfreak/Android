package com.example.examplecontrols

import android.icu.util.Calendar
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.DatePicker
import android.widget.MediaController
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import android.widget.VideoView

class MainActivity : AppCompatActivity() {

//    private lateinit var videoview: VideoView
//    private val videoUrl:Uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/do-more-at-a-glance-with-new-productivity-features-for-your-android-devices.mp4")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Timepicker
        val timePicker = findViewById<TimePicker>(R.id.timepicker)
        timePicker.setOnTimeChangedListener { _, hour, minute -> var hour = hour
            var am_pm = ""
            // AM_PM decider logic
            when {hour == 0 -> { hour += 12
                am_pm = "AM"
            }
                hour == 12 -> am_pm = "PM"
                hour > 12 -> { hour -= 12
                    am_pm = "PM"
                }
                else -> am_pm = "AM"
            }
            val hr = if (hour < 10) "0" + hour else hour
            val min = if (minute < 10) "0" + minute else minute
            // display format of time
            val msg = "Time selected : $hr : $min $am_pm"
            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
        }
        //Datepicker
        val datePicker = findViewById<DatePicker>(R.id.datepicker)
        val today = Calendar.getInstance()
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->
            val month = month + 1
            val msg = "You Selected: $day/$month/$year"
            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
        }

//        Log.i("External Storage Dir",Environment.getExternalStorageDirectory().getPath())
//        //video view
//        videoview = findViewById(R.id.YTvideoView)
//        videoview.setVideoURI(videoUrl)
//        val mediaController = MediaController(this)
////        mediaController.setAnchorView(videoview)
////        mediaController.setMediaPlayer(videoview)
//        videoview.setMediaController(mediaController)
//        videoview.start()
//        videoview.setOnCompletionListener {
//            videoview.start()
//        }

        //webview
        val myWebView: WebView = findViewById(R.id.webview)
        myWebView.loadUrl("https://developer.android.com/")
    }

}