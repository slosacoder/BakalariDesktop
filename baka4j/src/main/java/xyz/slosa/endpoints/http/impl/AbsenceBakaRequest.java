package xyz.slosa.endpoints.http.impl;

import org.json.JSONObject;
import xyz.slosa.endpoints.http.AbstractBakaHttpRequest;
import xyz.slosa.objects.BakaObject;

/**
 * @author : slosa
 * @created : 10.11.24, Sunday
 * CC BakalariDesktop's contributors, use according to the license!
 **/
public class AbsenceBakaRequest extends AbstractBakaHttpRequest<> {

    public AbsenceBakaRequest(String endpoint) {
        super(endpoint);
    }

    @Override
    public BakaObject deserialize(JSONObject jsonObject) {
        return null;
    }
}
