package xyz.slosa.endpoints.http.impl.marks.certificates;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONArray;
import org.json.JSONObject;
import xyz.slosa.endpoints.http.request.types.AbstractBakaHttpGETRequest;
import xyz.slosa.objects.impl.event.EventDetailsObject;
import xyz.slosa.objects.impl.marks.Subject;
import xyz.slosa.objects.impl.marks.certificates.CertificateTerm;
import xyz.slosa.objects.impl.marks.certificates.CertificatesObject;
import xyz.slosa.objects.impl.marks.certificates.FinalMark;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StudentCertificatesRequest extends AbstractBakaHttpGETRequest<CertificatesObject> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    /**
     * Constructs a request to retrieve final certificate marks.
     * Requires a valid access token in the header.
     */
    public StudentCertificatesRequest() {
        super("/api/3/marks/final");
    }

    /**
     * Deserializes the JSON response into a CertificatesObject.
     *
     * @param jsonObject the JSON response object containing certificate marks data
     * @return a {@link CertificatesObject} containing the final marks
     */
    @Override
    public CertificatesObject deserialize(final JSONObject jsonObject) {
        final JSONArray certificateTermsArray = jsonObject.optJSONArray("CertificateTerms");

        // Early exit if no certificate terms are present
        if (certificateTermsArray == null || certificateTermsArray.length() == 0) {
            return new CertificatesObject(new CertificateTerm[0]);
        }

        final CertificateTerm[] certificateTerms = new CertificateTerm[certificateTermsArray.length()];
        for (int i = 0; i < certificateTermsArray.length(); i++) {
            final JSONObject termObj = certificateTermsArray.optJSONObject(i);

            // Deserialize subjects
            final JSONArray subjectsArray = termObj.optJSONArray("Subjects");
            final Subject[] subjects = new Subject[subjectsArray != null ? subjectsArray.length() : 0];
            if (subjectsArray != null) {
                for (int j = 0; j < subjectsArray.length(); j++) {
                    final JSONObject subjectObj = subjectsArray.optJSONObject(j);
                    subjects[j] = new Subject(
                            subjectObj.optString("Id"),
                            subjectObj.optString("Abbrev"),
                            subjectObj.optString("Name")
                    );
                }
            }

            // Deserialize final marks
            final JSONArray finalMarksArray = termObj.optJSONArray("FinalMarks");
            final FinalMark[] finalMarks = new FinalMark[finalMarksArray != null ? finalMarksArray.length() : 0];
            if (finalMarksArray != null) {
                for (int j = 0; j < finalMarksArray.length(); j++) {
                    final JSONObject markObj = finalMarksArray.optJSONObject(j);
                    finalMarks[j] = new FinalMark(
                            LocalDateTime.parse(markObj.optString("MarkDate"), DATE_TIME_FORMATTER),
                            LocalDateTime.parse(markObj.optString("EditDate"), DATE_TIME_FORMATTER),
                            markObj.optString("MarkText"),
                            markObj.optString("SubjectId"),
                            markObj.optString("Id")
                    );
                }
            }

            // Create certificate term
            certificateTerms[i] = new CertificateTerm(
                    finalMarks,
                    subjects,
                    termObj.optString("GradeName"),
                    termObj.optInt("Grade"),
                    termObj.optInt("YearInSchool"),
                    termObj.optString("SchoolYear"),
                    termObj.optString("Semester"),
                    termObj.optString("SemesterName"),
                    termObj.optBoolean("Repeated"),
                    termObj.optBoolean("Closed"),
                    termObj.optString("AchievementText"),
                    termObj.optDouble("MarksAverage"),
                    termObj.optInt("AbsentHours"),
                    termObj.optInt("NotExcusedHours"),
                    LocalDateTime.parse(termObj.optString("CertificateDate"), DATE_TIME_FORMATTER)
            );
        }

        return new CertificatesObject(certificateTerms);
    }

    @Override
    public CertificatesObject deserialize(String json) throws JsonProcessingException {
        return getDeserializer().readValue(json, CertificatesObject.class);
    }
}
