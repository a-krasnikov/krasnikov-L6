import java.text.SimpleDateFormat
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicLong

fun main() {

    var counter = AtomicLong(0)

    fun incCounter() {
        while (true) {
            counter.incrementAndGet()

            try {
                Thread.sleep(10);
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt();
                break
            }
        }
    }

    val dateFormat = SimpleDateFormat("HH:mm:ss.SSS")
    var currentTime = System.currentTimeMillis()
    val end = currentTime + 6000

    val executor = Executors.newFixedThreadPool(4)
    executor.execute(::incCounter)
    executor.execute(::incCounter)
    executor.execute(::incCounter)
    executor.execute(::incCounter)

    do {
        Thread.sleep(1000)
        currentTime = System.currentTimeMillis()
        println("${dateFormat.format(currentTime)}: $counter")
    } while (currentTime < end)

    executor.shutdownNow()
}