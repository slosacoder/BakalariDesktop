package xyz.slosa.objects.impl.marks;

public record SubjectMarks(
        Mark[] marks,
        Subject subject,
        String averageText,
        String temporaryMark,
        String subjectNote,
        String temporaryMarkNote,
        boolean pointsOnly,
        boolean markPredictionEnabled
) {
}
