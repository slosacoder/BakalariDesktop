package xyz.slosa.endpoints.http.impl.measures;

import com.fasterxml.jackson.core.JsonProcessingException;
import xyz.slosa.endpoints.http.request.types.AbstractBakaHttpGETRequest;
import xyz.slosa.objects.impl.measures.PedagogicalMeasuresObject;

/**
 * Handles the HTTP GET request for retrieving pedagogical measures (disciplinary actions and commendations)
 * from the Bakalari API.
 */
public class PedagogicalMeasuresBakaRequest extends AbstractBakaHttpGETRequest<PedagogicalMeasuresObject> {


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
     * @param json the JSON response object containing pedagogical measures
     * @return a PedagogicalMeasuresObject containing the array of pedagogical measures
     */
    @Override
    public PedagogicalMeasuresObject deserialize(final String json) throws JsonProcessingException {
        return deserializer().readValue(json, PedagogicalMeasuresObject.class);
    }
}