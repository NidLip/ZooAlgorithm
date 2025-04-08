package utils;

public class Timer
{
    private long startTime;

    public void start()
    {
        startTime = System.nanoTime();
    }

    public double stop()
    {
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000.0;
    }
}