package xyz.slosa.ui.animation.engine.transition;

import xyz.slosa.ui.animation.engine.SystemTimer;

/**
 * @author slosa
 * @created 03.11.24, Sunday
 * CC BakalariDesktop's contributors, use according to the license!
 **/
public abstract class Transition {
    protected final double expectedEndResult;
    private final SystemTimer timer = new SystemTimer();
    protected long duration;
    protected TransitionDirection direction;

    public Transition(final long duration, final double expectedEndResult) {
        this.duration = duration;
        this.expectedEndResult = expectedEndResult;
    }

    public abstract double process(final double x);

    public final void reset() {
        timer.reset();
    }

    public final boolean isDone() {
        return timer.elapsed(duration);
    }

    public final double getOutput() {
        if (direction == TransitionDirection.IN) {
            if (isDone()) {
                return expectedEndResult;
            }
            // if in direction, check time and * with end value
            return (process(timer.delay()) * expectedEndResult);
        }

        if (isDone()) {
            return 0;
        }
        // invert via 1/x
        return (1D - process(timer.delay())) * expectedEndResult;
    }

    public double getExpectedEndResult() {
        return expectedEndResult;
    }

    public long getDuration() {
        return duration;
    }

    public TransitionDirection getDirection() {
        return direction;
    }

    public final void setDirection(final TransitionDirection direction) {
        if (this.direction != direction) {
            this.direction = direction;
            // Calculate start time
            final long delay = duration - Math.min(duration, timer.delay());
            timer.setCurrentTime(System.currentTimeMillis() - delay);
        }
    }
}