package xyz.slosa.ui.animation.engine;

/**
 * @author slosa
 * @created 03.11.24, Sunday
 * CC BakalariDesktop's contributors, use according to the license!
 **/
public final class SystemTimer {
    private long currentTimeMillis = System.currentTimeMillis();

    public boolean elapsed(final long amount) {
        return delay() >= amount;
    }

    public long delay() {
        return System.currentTimeMillis() - currentTimeMillis;
    }

    public boolean reset() {
        currentTimeMillis = System.currentTimeMillis();
        return true;
    }

    public void setCurrentTime(long currentTimeMillis) {
        this.currentTimeMillis = currentTimeMillis;
    }
}
