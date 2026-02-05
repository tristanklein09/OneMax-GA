public class Timer {

    public long startTimer() {
        return System.nanoTime();
    }

    public double stopTimer(long startTime) {
        double elapsedNanos = System.nanoTime() - startTime;
        return elapsedNanos / 1_000_000.0; //Converting to milliseconds
    }
}
