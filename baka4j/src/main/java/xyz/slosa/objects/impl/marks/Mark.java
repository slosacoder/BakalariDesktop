package xyz.slosa.objects.impl.marks;

import java.time.LocalDateTime;

/**
 * Represents a single mark received by a student.
 */
public record Mark(
        LocalDateTime markDate,
        LocalDateTime editDate,
        String caption,
        String theme,
        String markText,
        String teacherId,
        String type,
        String typeNote,
        int weight,
        String subjectId,
        boolean isNew,
        boolean isPoints,
        String calculatedMarkText,
        String classRankText,
        String id,
        String pointsText,
        int maxPoints
) {
}
