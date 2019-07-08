package com.practice.cleankanban.usecase.workItem;

public class CycleTime {

    private long diffDays;
    private long diffHours;
    private long diffMinutes;
    private long diffSeconds;


    public CycleTime(long diffDays, long diffHours, long diffMinutes, long diffSeconds) {
        this.diffDays = diffDays;
        this.diffHours = diffHours;
        this.diffMinutes = diffMinutes;
        this.diffSeconds = diffSeconds;
    }

    public long getDays() {
        return diffDays;
    }

    public long getHours() {
        return diffHours;
    }

    public long getMinutes() {
        return diffMinutes;
    }

    public long getSeconds() {
        return diffSeconds;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cycle time = ")
                     .append(getDays())
                     .append(" days, ")
                     .append(diffHours)
                     .append(" hours, ")
                     .append(diffMinutes)
                     .append(" minutes, ")
                     .append(diffSeconds)
                     .append(" seconds.");
        return stringBuilder.toString();
    }
}
