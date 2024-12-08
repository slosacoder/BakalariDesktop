package xyz.slosa.endpoints.http.impl.gdpr;

import com.fasterxml.jackson.core.JsonProcessingException;
import xyz.slosa.endpoints.http.request.types.AbstractBakaHttpGETRequest;
import xyz.slosa.objects.impl.gdpr.GdprCommissionersObject;

/**
 * Handles the HTTP GET request for retrieving GDPR commissioners data from the API.
 * This class fetches information about commissioners responsible for data protection.
 */
public class GDPRCommissionersBakaRequest extends AbstractBakaHttpGETRequest<GdprCommissionersObject> {

    /**
     * Constructs a request to retrieve GDPR commissioners data.
     * Requires a valid access token in the header.
     */
    public GDPRCommissionersBakaRequest() {
        super("/api/3/gdpr/commissioners");
    }

    /**
     * Deserializes the JSON response into a <code>GdprCommissionersObject</code>.
     *
     * @param json the JSON response object containing the commissioners data
     * @return a <code>GdprCommissionersObject</code> containing the array of commissioners
     */
    @Override
    public GdprCommissionersObject deserialize(final String json) throws JsonProcessingException {
        return deserializer().readValue(json, GdprCommissionersObject.class);
    }
}
