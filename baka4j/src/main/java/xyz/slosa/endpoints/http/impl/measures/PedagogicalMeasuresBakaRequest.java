package xyz.slosa.endpoints.http.impl.measures;

import org.json.JSONArray;
import org.json.JSONObject;
import xyz.slosa.endpoints.http.request.types.AbstractBakaHttpGETRequest;
import xyz.slosa.objects.impl.measures.PedagogicalMeasure;
import xyz.slosa.objects.impl.measures.PedagogicalMeasuresObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles the HTTP GET request for retrieving pedagogical measures (disciplinary actions and commendations)
 * from the Bakalari API.
 */
public class PedagogicalMeasuresBakaRequest extends AbstractBakaHttpGETRequest<PedagogicalMeasuresObject> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    /**
     * Constructs a request to retrieve pedagogical measures.
     * No additional parameters are needed for this endpoint.
     */
    public PedagogicalMeasuresBakaRequest() {
        super("/api/3/marks/measures");
    }

    /**
     * Deserializes the JSON response into a PedagogicalMeasuresObject.
     *
     * @param jsonObject the JSON response object containing pedagogical measures
     * @return a PedagogicalMeasuresObject containing the array of pedagogical measures
     */
    @Override
    public PedagogicalMeasuresObject deserialize(final JSONObject jsonObject) {
        final JSONArray measuresArray = jsonObject.optJSONArray("PedagogicalMeasures");

        // Early exit if no measures are present
        if (measuresArray == null || measuresArray.length() == 0) {
            return new PedagogicalMeasuresObject(new PedagogicalMeasure[0]);
        }

        final PedagogicalMeasure[] measures = new PedagogicalMeasure[measuresArray.length()];

        for (int i = 0; i < measuresArray.length(); i++) {
            final JSONObject measureObj = measuresArray.optJSONObject(i);

            measures[i] = new PedagogicalMeasure(
                    measureObj.optString("SchoolYear"),
                    measureObj.optString("Semester"),
                    measureObj.optString("TypeLabel"),
                    LocalDateTime.parse(measureObj.optString("Date"), DATE_TIME_FORMATTER),
                    measureObj.optString("TypeId"),
                    measureObj.optString("Text")
            );
        }

        return new PedagogicalMeasuresObject(measures);
    }
}