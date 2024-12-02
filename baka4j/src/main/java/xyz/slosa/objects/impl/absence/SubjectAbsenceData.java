package xyz.slosa.objects.impl.absence;

import xyz.slosa.objects.BakaObject;

public record SubjectAbsenceData(String subjectName,
                                 int lessonsCount,
                                 int base,
                                 int late,
                                 int soon,
                                 int school,
                                 int distanceTeaching) implements BakaObject {
}
