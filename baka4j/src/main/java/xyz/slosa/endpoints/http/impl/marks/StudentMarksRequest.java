package xyz.slosa.endpoints.http.impl.marks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONArray;
import org.json.JSONObject;
import xyz.slosa.endpoints.http.request.types.AbstractBakaHttpGETRequest;
import xyz.slosa.objects.impl.event.EventDetailsObject;
import xyz.slosa.objects.impl.marks.Mark;
import xyz.slosa.objects.impl.marks.MarksObject;
import xyz.slosa.objects.impl.marks.Subject;
import xyz.slosa.objects.impl.marks.SubjectMarks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StudentMarksRequest extends AbstractBakaHttpGETRequest<MarksObject> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    /**
     * Constructs a request to retrieve marks data.
     * Requires a valid access token in the header.
     */
    public StudentMarksRequest() {
        super("/api/3/marks");
    }

    /**
     * Deserializes the JSON response into a <code>MarksObject</code>.
     *
     * @param jsonObject the JSON response object containing marks data
     * @return a <code>MarksObject</code> containing the marks for all subjects
     */
    @Override
    public MarksObject deserialize(final JSONObject jsonObject) {
        final JSONArray subjectsArray = jsonObject.optJSONArray("Subjects");

        // Early exit if no subjects are present
        if (subjectsArray == null || subjectsArray.length() == 0) {
            return new MarksObject(new SubjectMarks[0]);
        }

        final SubjectMarks[] subjectMarksArray = new SubjectMarks[subjectsArray.length()];
        for (int i = 0; i < subjectsArray.length(); i++) {
            final JSONObject subjectMarksObj = subjectsArray.optJSONObject(i);

            // Deserialize individual marks
            final JSONArray marksArray = subjectMarksObj.optJSONArray("Marks");
            final Mark[] marks = new Mark[marksArray.length()];
            for (int j = 0; j < marksArray.length(); j++) {
                JSONObject markObj = marksArray.optJSONObject(j);
                marks[j] = new Mark(
                        LocalDateTime.parse(markObj.optString("MarkDate"), DATE_TIME_FORMATTER),
                        LocalDateTime.parse(markObj.optString("EditDate"), DATE_TIME_FORMATTER),
                        markObj.optString("Caption"),
                        markObj.optString("Theme"),
                        markObj.optString("MarkText"),
                        markObj.optString("TeacherId"),
                        markObj.optString("Type"),
                        markObj.optString("TypeNote"),
                        markObj.optInt("Weight"),
                        markObj.optString("SubjectId"),
                        markObj.optBoolean("IsNew"),
                        markObj.optBoolean("IsPoints"),
                        markObj.optString("CalculatedMarkText"),
                        markObj.optString("ClassRankText"),
                        markObj.optString("Id"),
                        markObj.optString("PointsText"),
                        markObj.optInt("MaxPoints")
                );
            }

            // Deserialize subject data
            final JSONObject subjectObj = subjectMarksObj.optJSONObject("Subject");
            final Subject subject = new Subject(
                    subjectObj.optString("Id"),
                    subjectObj.optString("Abbrev"),
                    subjectObj.optString("Name")
            );

            subjectMarksArray[i] = new SubjectMarks(
                    marks,
                    subject,
                    subjectMarksObj.optString("AverageText"),
                    subjectMarksObj.optString("TemporaryMark"),
                    subjectMarksObj.optString("SubjectNote"),
                    subjectMarksObj.optString("TemporaryMarkNote"),
                    subjectMarksObj.optBoolean("PointsOnly"),
                    subjectMarksObj.optBoolean("MarkPredictionEnabled")
            );
        }

        return new MarksObject(subjectMarksArray);
    }

    @Override
    public MarksObject deserialize(String json) throws JsonProcessingException {
        return getDeserializer().readValue(json, MarksObject.class);
    }
}

