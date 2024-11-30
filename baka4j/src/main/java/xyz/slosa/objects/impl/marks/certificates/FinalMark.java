package xyz.slosa.objects.impl.marks.certificates;

import java.time.LocalDateTime;

public record FinalMark(
        LocalDateTime markDate,
        LocalDateTime editDate,
        String markText,
        String subjectId,
        String id
) {
}