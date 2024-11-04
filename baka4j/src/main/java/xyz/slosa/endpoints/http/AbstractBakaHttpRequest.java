package xyz.slosa.endpoints.http;

import org.json.JSONObject;

/**
 * @author slosa
 * @created 03.11.24, Sunday
 * CC BakalariDesktop's contributors, use according to the license!
 **/
public abstract class AbstractBakaHttpRequest<T> {
    private final String endpoint;
    private T object;
    private BakaRequestStatus status;

    public AbstractBakaHttpRequest(final String endpoint) {
        this.endpoint = endpoint;
        this.status = BakaRequestStatus.UNHANDLED;
    }

    public abstract T deserialize(final JSONObject jsonObject);

    public void setObject(final T object) {
        this.object = object;
    }

    public T getData() {
        if (object == null || status == BakaRequestStatus.UNHANDLED || status == BakaRequestStatus.ERROR) {
            throw new RuntimeException("Object is null or request wasn't handled yet! (object: " + object + ", request: " + getClass().getSimpleName()); // you are trying to get data before request was issued or there was a error while issuing, can't happen!
        } else {
            return object; // Success!
        }
    }

    enum BakaRequestStatus {
        HANDLED, UNHANDLED, ERROR;
    }

    public void setHandled() {
        status = BakaRequestStatus.HANDLED;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
