package xyz.slosa.objects.impl.absence;

import xyz.slosa.objects.BakaObject;

public record AbsenceDataObject(float percentageThreshold,
                                AbsenceData[] absences,
                                SubjectAbsenceData[] absencesPerSubject) implements BakaObject {
}
