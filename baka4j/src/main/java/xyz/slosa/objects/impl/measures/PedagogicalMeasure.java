package xyz.slosa.objects.impl.measures;

import java.time.LocalDateTime;

public record PedagogicalMeasure(
        String schoolYear,
        String semester,
        String typeLabel,
        LocalDateTime date,
        String typeId,
        String text
) {
}
