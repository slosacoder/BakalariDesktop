package xyz.slosa.objects.impl.absence;

import xyz.slosa.objects.BakaObject;

import java.time.LocalDateTime;

public record AbsenceDataObject(float threshold, LocalDateTime time,
                                SubjectAbsenceData... subjectAbsences) implements BakaObject {
}
