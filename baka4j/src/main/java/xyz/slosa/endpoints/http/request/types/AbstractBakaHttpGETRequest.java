package xyz.slosa.endpoints.http.request.types;

import xyz.slosa.endpoints.http.request.AbstractBakaHttpRequest;
import xyz.slosa.objects.BakaObject;

/**
 * Abstract base class for handling HTTP GET requests to the Bakalari API.
 * This class extends {@link AbstractBakaHttpRequest} to provide support specifically for GET requests,
 * which typically do not require a request body.
 *
 * @param <T> The type of the object that will hold the deserialized data, extending {@link BakaObject}.
 */
public abstract class AbstractBakaHttpGETRequest<T extends BakaObject> extends AbstractBakaHttpRequest<T> {

    /**
     * Constructor to initialize the endpoint for the HTTP GET request.
     *
     * @param endpoint the API endpoint for this request
     */
    public AbstractBakaHttpGETRequest(final String endpoint) {
        super(endpoint);
    }
}