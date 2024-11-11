package xyz.slosa.endpoints.http.request;

import org.json.JSONObject;
import xyz.slosa.objects.BakaObject;

/**
 * Abstract base class for handling HTTP requests to the Bakalari API.
 * This class defines how requests should be processed and deserialized into specific objects.
 *
 * @param <T> The type of the object that will hold the deserialized data.
 */
public abstract class AbstractBakaHttpRequest<T extends BakaObject> {
    private final String endpoint;
    private T object;

    /**
     * Constructor to initialize the endpoint for the HTTP request.
     *
     * @param endpoint the API endpoint for this request
     */
    public AbstractBakaHttpRequest(final String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * This method should populate the `object` field with the deserialized data from the JSON object.
     * It can also return the deserialized object if needed.
     *
     * @param jsonObject the JSON object containing the data to be deserialized
     * @return the deserialized object of type O
     */
    public abstract T deserialize(final JSONObject jsonObject);

    /**
     * Returns the deserialized object data, if it exists.
     *
     * @return the deserialized object of type O
     * @throws NullPointerException if the object has not been deserialized (i.e., is null)
     */
    public T getData() {
        if (object == null)
            throw new NullPointerException("There was an error while issuing the request: " + getClass().getSimpleName() + ", hash(" + hashCode() + ")");
        return object; // Return the actual object data on success
    }

    public void fillData(T object) {
        this.object = object;
    }

    /**
     * Returns the API endpoint associated with this request.
     *
     * @return the API endpoint string
     */
    public String getEndpoint() {
        return endpoint;
    }
}