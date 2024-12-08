package xyz.slosa.endpoints.http.impl.marks;

import com.fasterxml.jackson.core.JsonProcessingException;
import xyz.slosa.endpoints.http.request.types.AbstractBakaHttpGETRequest;
import xyz.slosa.objects.impl.marks.MarksObject;

public class StudentMarksBakaRequest extends AbstractBakaHttpGETRequest<MarksObject> {

    /**
     * Constructs a request to retrieve marks data.
     * Requires a valid access token in the header.
     */
    public StudentMarksBakaRequest() {
        super("/api/3/marks");
    }

    /**
     * Deserializes the JSON response into a <code>MarksObject</code>.
     *
     * @param json the JSON response object containing marks data
     * @return a <code>MarksObject</code> containing the marks for all subjects
     */
    @Override
    public MarksObject deserialize(final String json) throws JsonProcessingException {
        return deserializer().readValue(json, MarksObject.class);
    }
}

