package xyz.slosa.objects.impl.absence;

import xyz.slosa.objects.BakaObject;

public record SubjectAbsenceData(String name,
                                 int lessonCount,
                                 int base,
                                 int late,
                                 int soon,
                                 int school,
                                 int distantTeaching) implements BakaObject {
}
