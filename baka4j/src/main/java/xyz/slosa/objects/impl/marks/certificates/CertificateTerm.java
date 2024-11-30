package xyz.slosa.objects.impl.marks.certificates;

import xyz.slosa.objects.impl.marks.Subject;

import java.time.LocalDateTime;

public record CertificateTerm(
        FinalMark[] finalMarks,
        Subject[] subjects,
        String gradeName,
        int grade,
        int yearInSchool,
        String schoolYear,
        String semester,
        String semesterName,
        boolean repeated,
        boolean closed,
        String achievementText,
        double marksAverage,
        int absentHours,
        int notExcusedHours,
        LocalDateTime certificateDate
) {
}
