package xyz.slosa.endpoints.http.impl.login;

import org.json.JSONObject;
import xyz.slosa.endpoints.http.AbstractBakaHttpRequest;
import xyz.slosa.objects.impl.LoginData;

public class LoginWithCredentialsBakaRequest extends AbstractBakaHttpRequest<LoginData> {

    private final String login, password;

    public LoginWithCredentialsBakaRequest(final String login, final String password) {
        super("/api/login");
        this.login = login;
        this.password = password;
    }

    @Override
    public LoginData deserialize(final JSONObject jsonObject) {
        final String accessToken = jsonObject.optString("access_token"); // Main thing we want is the access token
        return new LoginData(login, password, accessToken);
    }
}
