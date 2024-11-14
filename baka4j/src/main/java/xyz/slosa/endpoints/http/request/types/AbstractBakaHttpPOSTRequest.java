package xyz.slosa.endpoints.http.request.types;

import xyz.slosa.endpoints.http.request.AbstractBakaHttpRequest;
import xyz.slosa.objects.BakaObject;

/**
 * Abstract base class for handling HTTP POST requests to the Bakalari API.
 * This class extends {@link AbstractBakaHttpRequest} to support POST requests with a request body.
 *
 * @param <T> The type of the object that will hold the deserialized data.
 */
public abstract class AbstractBakaHttpPOSTRequest<T extends BakaObject> extends AbstractBakaHttpRequest<T> {

    private final String requestBody;

    /**
     * Constructor to initialize the endpoint for the HTTP POST request with a request body.
     *
     * @param endpoint    the API endpoint for this request
     * @param requestBody the body content to be sent with the POST request
     */
    public AbstractBakaHttpPOSTRequest(final String endpoint, final String requestBody) {
        super(endpoint);
        this.requestBody = requestBody;
    }

    /**
     * Returns the request body associated with this POST request.
     *
     * @return the request body string
     */
    public String getRequestBody() {
        return requestBody;
    }
}