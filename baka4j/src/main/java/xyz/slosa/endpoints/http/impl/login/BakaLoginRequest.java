package xyz.slosa.endpoints.http.impl.login;

import org.json.JSONObject;
import xyz.slosa.endpoints.http.AbstractBakaHttpRequest;
import xyz.slosa.objects.impl.LoginDataObject;

public class BakaLoginRequest extends AbstractBakaHttpRequest<LoginDataObject> {

    public BakaLoginRequest(final String login, final String password) {
        super(String.format("/api/login/client_id=ANDR&grant_type=password&username=%s&password=%s"));
    }

    @Override
    public LoginDataObject deserialize(final JSONObject jsonObject) {
        return null;
    }

}
