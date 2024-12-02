package xyz.slosa.endpoints.http.impl.gdpr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.json.JSONArray;
import org.json.JSONObject;
import xyz.slosa.endpoints.http.request.types.AbstractBakaHttpGETRequest;
import xyz.slosa.objects.impl.gdpr.GdprCommissioner;
import xyz.slosa.objects.impl.gdpr.GdprCommissionersObject;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Handles the HTTP GET request for retrieving GDPR commissioners data from the API.
 * This class fetches information about commissioners responsible for data protection.
 */
public class GdprCommissionersRequest extends AbstractBakaHttpGETRequest<GdprCommissionersObject> {

    /**
     * Constructs a request to retrieve GDPR commissioners data.
     * Requires a valid access token in the header.
     */
    public GdprCommissionersRequest() {
        super("/api/3/gdpr/commissioners");
    }

    /**
     * Deserializes the JSON response into a <code>GdprCommissionersObject</code>.
     *
     * @param jsonObject the JSON response object containing the commissioners data
     * @return a <code>GdprCommissionersObject</code> containing the array of commissioners
     */
    @Override
    public GdprCommissionersObject deserialize(final JSONObject jsonObject) {
        final JSONArray commissionersArray = jsonObject.optJSONArray("Commissioners");

        // Early exit if no commissioners are present
        if (commissionersArray == null || commissionersArray.length() == 0) {
            return new GdprCommissionersObject(new GdprCommissioner[0]);
        }

        final GdprCommissioner[] commissioners = new GdprCommissioner[commissionersArray.length()];
        for (int i = 0; i < commissionersArray.length(); i++) {
            JSONObject commissionerObj = commissionersArray.optJSONObject(i);
            commissioners[i] = new GdprCommissioner(
                    commissionerObj.optString("Id"),
                    commissionerObj.optString("Name"),
                    commissionerObj.optString("Mobile"),
                    commissionerObj.optString("Phone"),
                    commissionerObj.optString("Email"),
                    commissionerObj.optString("Web"),
                    commissionerObj.optString("DataBox")
            );
        }

        return new GdprCommissionersObject(commissioners);
    }

    @Override
    public GdprCommissionersObject deserialize(String json) throws JsonProcessingException {
        return getDeserializer().readValue(json, GdprCommissionersObject.class);
    }
}
