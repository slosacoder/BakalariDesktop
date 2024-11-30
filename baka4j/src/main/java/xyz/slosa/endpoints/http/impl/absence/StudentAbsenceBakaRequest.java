package xyz.slosa.endpoints.http.impl.absence;

import org.json.JSONArray;
import org.json.JSONObject;
import xyz.slosa.endpoints.http.request.types.AbstractBakaHttpGETRequest;
import xyz.slosa.objects.impl.absence.AbsenceData;
import xyz.slosa.objects.impl.absence.AbsenceDataObject;
import xyz.slosa.objects.impl.absence.SubjectAbsenceData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles the HTTP GET request for retrieving a student's absence data from the API.
 * This class retrieves absence data, including overall absence statistics and per-subject breakdown,
 * for a specific student based on an access token.
 *
 * <p>Example usage involves making a GET request to <code>/api/3/absence/student</code> with the
 *
 * <p><b>Response Structure:</b></p>
 * <ul>
 *     <li><b>PercentageThreshold</b> - The threshold percentage for absence (e.g., 0.18)</li>
 *     <li><b>Absences</b> - List of absence records for the student, including details for each day:</li>
 *     <ul>
 *         <li><b>Date</b> - The date of absence (e.g., "2020-02-30T00:00:00+01:00")</li>
 *         <li><b>Unsolved</b> - Number of unsolved absences</li>
 *         <li><b>Ok</b> - Number of valid absences</li>
 *         <li><b>Missed</b> - Number of missed lessons</li>
 *         <li><b>Late</b> - Number of late arrivals</li>
 *         <li><b>Soon</b> - Number of upcoming absences</li>
 *         <li><b>School</b> - Number of school-related absences</li>
 *         <li><b>DistanceTeaching</b> - Number of distance teaching absences</li>
 *     </ul>
 *     <li><b>AbsencesPerSubject</b> - Breakdown of absences per subject:</li>
 *     <ul>
 *         <li><b>SubjectName</b> - Name of the subject (e.g., "Český jazyk a literatura")</li>
 *         <li><b>LessonsCount</b> - Total number of lessons for the subject</li>
 *         <li><b>Base</b> - Base number of absences allowed</li>
 *         <li><b>Late</b> - Number of late arrivals for the subject</li>
 *         <li><b>Soon</b> - Number of upcoming absences for the subject</li>
 *         <li><b>School</b> - Number of school-related absences for the subject</li>
 *         <li><b>DistanceTeaching</b> - Number of distance teaching absences for the subject</li>
 *     </ul>
 * </ul>
 *
 * <p>Note: This request requires a valid access token passed via the <code>Authorization</code> header as <code>Bearer ACCESS_TOKEN</code>.</p>
 *
 * @see AbstractBakaHttpGETRequest
 * @see AbsenceDataObject
 */
public class StudentAbsenceBakaRequest extends AbstractBakaHttpGETRequest<AbsenceDataObject> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    /**
     * Constructs a request to retrieve the student's absence data.
     * The access token should be provided in the request header as <code>Authorization: Bearer ACCESS_TOKEN</code>.
     */
    public StudentAbsenceBakaRequest() {
        super("/api/3/absence/student");
    }

    /**
     * Deserializes the JSON response into an <code>AbsenceDataObject</code>.
     *
     * @param jsonObject the JSON response object containing the absence data
     * @return an <code>AbsenceDataObject</code> representing the absence details
     */
    @Override
    public AbsenceDataObject deserialize(final JSONObject jsonObject) {
        // Deserialize PercentageThreshold
        final float percentageThreshold = (float) jsonObject.optDouble("PercentageThreshold");

        // Deserialize Absences array
        final JSONArray absences = jsonObject.optJSONArray("Absences");

        final AbsenceData[] absenceData = new AbsenceData[absences.length()];
        for (int i = 0; i < absences.length(); i++) {
            final JSONObject obj = absences.optJSONObject(i);
            absenceData[i] = new AbsenceData(
                    LocalDateTime.parse(obj.optString("Date"), DATE_TIME_FORMATTER),
                    obj.optInt("Unsolved"),
                    obj.optInt("Ok"),
                    obj.optInt("Missed"),
                    obj.optInt("Late"),
                    obj.optInt("Soon"),
                    obj.optInt("School"),
                    obj.optInt("DistanceTeaching")
            );
        }

        // Deserialize AbsencesPerSubject array
        JSONArray subjectAbsencesArray = jsonObject.optJSONArray("AbsencesPerSubject");

        SubjectAbsenceData[] subjectAbsenceData = new SubjectAbsenceData[subjectAbsencesArray.length()];
        for (int i = 0; i < subjectAbsencesArray.length(); i++) {
            JSONObject obj = subjectAbsencesArray.optJSONObject(i);
            subjectAbsenceData[i] = new SubjectAbsenceData(
                    obj.optString("SubjectName"),
                    obj.optInt("LessonsCount"),
                    obj.optInt("Base"),
                    obj.optInt("Late"),
                    obj.optInt("Soon"),
                    obj.optInt("School"),
                    obj.optInt("DistanceTeaching")
            );
        }

        // Create and return the AbsenceDataObject
        return new AbsenceDataObject(percentageThreshold, absenceData, subjectAbsenceData);
    }
}
