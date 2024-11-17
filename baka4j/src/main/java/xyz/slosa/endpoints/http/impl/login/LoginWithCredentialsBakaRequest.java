package xyz.slosa.endpoints.http.impl.login;

import org.json.JSONObject;
import xyz.slosa.endpoints.http.request.types.AbstractBakaHttpPOSTRequest;
import xyz.slosa.objects.impl.login.LoginDataObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Handles the login request for the API, allowing the retrieval of an access token, refresh token, and optional ID token.
 * This class is designed for initial login using client credentials (username and password) and processes the response
 * to extract tokens for further API access.
 *
 * <p>Example usage involves making a POST request to <code>/api/login</code> with credentials in the request body.
 * A valid access token is required for accessing other endpoints, and it should be passed in the request header
 * as <code>Authorization: Bearer ACCESSTOKEN</code>.
 *
 * <p><b>Response Structure:</b>
 * <ul>
 *     <li><b>access_token</b> - Main token required for authorization (usually 2556 characters)</li>
 *     <li><b>refresh_token</b> - Token used to renew the access token (usually 3459 characters)</li>
 *     <li><b>id_token</b> - Optional, encoded in JWT format, may include user and authentication details</li>
 *     <li><b>token_type</b> - Indicates the token type, typically "Bearer"</li>
 *     <li><b>expires_in</b> - Time in seconds until token expiration (e.g., 3599 for one hour)</li>
 *     <li><b>scope</b> - Permissions associated with the token</li>
 * </ul>
 *
 * <p><b>Error Handling:</b>
 * Common errors include:
 * <ul>
 *     <li><code>400 Bad Request</code> - Invalid credentials or missing required parameters</li>
 *     <li><code>invalid_grant</code> - Incorrect login credentials, expired token, or reused refresh token</li>
 *     <li><code>invalid_request</code> - Missing <code>grant_type</code> or <code>client_id</code></li>
 * </ul>
 *
 * <p>Note: This class only supports initial login with username and password. The login response is
 * deserialized to a <code>LoginDataObject</code> that holds the access token and other key details, for further logging in use
 *
 * @see AbstractBakaHttpPOSTRequest
 * @see LoginDataObject
 */
public class LoginWithCredentialsBakaRequest extends AbstractBakaHttpPOSTRequest<LoginDataObject> {

    private final String login, password;
    private final static String REQUEST_BODY = "client_id=ANDR&grant_type=password&username=%s&password=%s";

    /**
     * Constructs a login request with specified credentials.
     *
     * @param login    the username of the client
     * @param password the password of the client
     */
    public LoginWithCredentialsBakaRequest(final String login, final String password) {
        super("/api/login", String.format(REQUEST_BODY,
                URLEncoder.encode(login, StandardCharsets.UTF_8),
                URLEncoder.encode(password, StandardCharsets.UTF_8)));
        this.login = login;
        this.password = password;
    }

    /**
     * Deserializes the response JSON to obtain the access token, refresh token, and other details.
     *
     * @param jsonObject the JSON object containing the API response
     * @return a <code>LoginDataObject</code> containing the login details and access token
     */
    @Override
    public LoginDataObject deserialize(final JSONObject jsonObject) {
        final String accessToken = jsonObject.optString("access_token"); // Main thing we want is the access token
        return new LoginDataObject(login, password, accessToken);
    }
}

