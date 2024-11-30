package xyz.slosa.objects.impl.event;


import xyz.slosa.objects.BakaObject;

public record EventDetails(
        String id,
        String title,
        String description,
        EventData[] times,
        String eventType, // Name of the event type
        String dateChanged) implements BakaObject {
}
