package xyz.slosa.endpoints.http;

import org.json.JSONObject;
import xyz.slosa.objects.ErrorObject;
import xyz.slosa.objects.BakaObject;

/**
 * @author slosa
 * @created 03.11.24, Sunday
 * CC BakalariDesktop's contributors, use according to the license!
 **/
public abstract class AbstractBakaHttpRequest<O extends BakaObject> {
    private final String endpoint;
    private O object;
    private BakaRequestStatus status;

    public AbstractBakaHttpRequest(final String endpoint) {
        this.endpoint = endpoint;
        this.status = BakaRequestStatus.UNHANDLED;
    }

    public abstract O deserialize(final JSONObject jsonObject);

    public void setObject(final O object) {
        this.object = object;
    }

    public O getData() {
        if (object == null || status == BakaRequestStatus.UNHANDLED || status == BakaRequestStatus.ERROR) {
            return (O) new ErrorObject(); // you are trying to get data before request was issued or there was a error while issuing, can't happen!
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
