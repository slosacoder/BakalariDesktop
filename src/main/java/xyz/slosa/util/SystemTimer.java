package xyz.slosa.util;

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
