package br.edu.utf.project.util;

public enum TimeInMs {
    SECOND(1000),
    MINUTE(60_000),
    HOUR(3_600_000),
    DAY(86_400_000);

    private final long millis;

    TimeInMs(long millis) {
        this.millis = millis;
    }

    public long toMillis() {
        return millis;
    }

    public long toSeconds() {
        return millis / 1000;
    }

    public long toMinutes() {
        return millis / (60 * 1000);
    }

    public long toHours() {
        return millis / (60 * 60 * 1000);
    }

    public long toDays() {
        return millis / (24 * 60 * 60 * 1000);
    }
}
