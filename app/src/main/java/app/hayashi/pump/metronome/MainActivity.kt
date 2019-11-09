package app.hayashi.pump.metronome

import android.media.MediaPlayer
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    var timerCount: Int = 0
    val handler: Handler = Handler()
    var count = 60
    var time = (60000/count).toLong()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView.text = "♩=" + count.toString()

        var mediaPlayer = MediaPlayer.create(this, R.raw.metronome2)

        var metro = timer(period = time) {
        }
        stop.setOnClickListener {
            metro.cancel()
        }
        start.setOnClickListener {
            metro.cancel()
            timerCount = 0
            metro = timer(period = time) {
                handler.post {
                    timerCount++
                    mediaPlayer.stop()
                    mediaPlayer.prepare()
                    mediaPlayer.start()
                }
            }
        }
        plus.setOnClickListener {
            count++
            time = (60000/count).toLong()
            textView.text = "♩=" + count.toString()
        }
        minus.setOnClickListener {
            count++
            time = (60000/count).toLong()
            textView.text = "♩=" + count.toString()
        }
    }
}
