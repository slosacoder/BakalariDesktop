package xyz.slosa.objects.impl.event;

import xyz.slosa.objects.BakaObject;

import java.time.LocalDateTime;

public record EventData(
        boolean wholeDay,
        LocalDateTime startTime,
        LocalDateTime endTime) implements BakaObject {
}