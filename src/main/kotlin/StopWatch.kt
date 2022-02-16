import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.*

class StopWatch {

    var formattedTime by mutableStateOf("00:00:000")

    private var coroutineScope = CoroutineScope(Dispatchers.Main)
    private var isActive = false

    private var timeMillis = 0L
    private var lastTimestamp = 0L

    fun start() {
        if(isActive) return

        coroutineScope.launch {
            this@StopWatch.isActive = true
            while(this@StopWatch.isActive) {
                lastTimestamp = System.currentTimeMillis()
                delay(1L)
                timeMillis += System.currentTimeMillis() - lastTimestamp
                formattedTime = formatTime(timeMillis)
            }
        }
    }




    fun pause() {
        isActive = false
    }

    fun reset() {
        coroutineScope.cancel()
        coroutineScope = CoroutineScope(Dispatchers.Main)
        timeMillis = 0L
        lastTimestamp = 0L
        formattedTime = "00:00:000"
        isActive = false
    }

    private fun formatTime(timeMillis: Long): String {

        var seconds=((timeMillis/1000)%60).toString()
        if(seconds.length==1) seconds= "0$seconds"
        var minutes=((timeMillis/(1000*60))%60).toString()
        if(minutes.length==1) minutes="0$minutes"
        var millis=((timeMillis)%1000).toString()
        if(millis.length==1) millis="00$millis"
        else if(millis.length==2) millis="0$millis"

        return "$minutes:$seconds:$millis"
    }
}