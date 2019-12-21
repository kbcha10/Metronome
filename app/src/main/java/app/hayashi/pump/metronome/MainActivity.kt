package app.hayashi.pump.metronome

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var BPM :Int = 40
        val handler: Handler = Handler()
        var interval = (60000/BPM).toLong()
        val mediaPlayer = MediaPlayer.create(this, R.raw.metronome)
        var metronome = timer(period = interval) {}
        var isPlaying : Boolean = false

        BPMPicker.minValue = 40
        BPMPicker.maxValue = 208

        fun setMetronome() {
            interval = (60000/BPM).toLong()
            metronome.cancel()
            metronome = timer(period = interval) {
                handler.post {
                    mediaPlayer.stop()
                    mediaPlayer.prepare()
                    mediaPlayer.start()
                }
            }
        }

        BPMPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            BPM = newVal
            if(isPlaying) setMetronome()
        }

        start.setOnClickListener {
            when(isPlaying) {
                true -> {
                    start.setImageResource(R.drawable.start)
                    metronome.cancel()
                    isPlaying = false
                }
                false -> {
                    setMetronome()
                    start.setImageResource(R.drawable.stop)
                    isPlaying = true
                }
            }
        }
    }
}
