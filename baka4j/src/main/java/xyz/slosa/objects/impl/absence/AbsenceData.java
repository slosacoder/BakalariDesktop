package xyz.slosa.objects.impl.absence;

import java.time.LocalDateTime;

public record AbsenceData(LocalDateTime date,
                          int unsolved,
                          int ok,
                          int missed,
                          int late,
                          int soon,
                          int school,
                          int distanceTeaching) {
}
