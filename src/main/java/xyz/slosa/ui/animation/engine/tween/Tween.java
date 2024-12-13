package xyz.slosa.ui.animation.engine.tween;

// Class used for interpolating between 2 poses
public abstract class Tween {
    protected final double duration;
    private long startTime;
    private double start;
    private double end;

    public Tween(final double duration) {
        this.duration = duration;
    }

    public void tween(final double start, final double end) {
        this.start = start;
        this.end = end;
        this.startTime = System.currentTimeMillis();
    }

    public abstract float process(final double x);

    public boolean isDone() {
        return (System.currentTimeMillis() - startTime) >= duration;
    }

    public double getValue() {
        double elapsed = (double) (System.currentTimeMillis() - startTime);
        if (elapsed < duration) {
            return start + (end - start) * process(elapsed); // Lerp
        } else {
            return end;
        }
    }
}
