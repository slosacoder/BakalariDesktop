package xyz.slosa.endpoints.http.impl.login;

import org.json.JSONObject;
import xyz.slosa.endpoints.http.request.types.AbstractBakaHttpPOSTRequest;
import xyz.slosa.objects.impl.LoginDataObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Handles the login request for the API using a refresh token. This class allows for the retrieval of an access token,
 * refresh token, and optional ID token by providing a valid refresh token to authenticate the request.
 * This is intended for renewing the access token without requiring the user’s credentials (username and password).
 *
 * <p>Example usage involves making a POST request to <code>/api/login</code> with the refresh token in the request body.
 * A valid access token is required for accessing other endpoints, and it should be passed in the request header
 * as <code>Authorization: Bearer ACCESSTOKEN</code>.</p>
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
 *     <li><code>400 Bad Request</code> - Invalid refresh token or missing required parameters</li>
 *     <li><code>invalid_grant</code> - Incorrect refresh token, expired token, or reused refresh token</li>
 *     <li><code>invalid_request</code> - Missing <code>grant_type</code> or <code>client_id</code></li>
 * </ul>
 *
 * <p>Note: This class only supports login using a refresh token. The login response is deserialized to a
 * <code>LoginDataObject</code> that holds the access token, the login and password are NULL.</p>
 *
 * @see AbstractBakaHttpPOSTRequest
 * @see LoginDataObject
 */
public class LoginWithRefreshTokenBakaRequest extends AbstractBakaHttpPOSTRequest<LoginDataObject> {

    private final static String REQUEST_BODY = "client_id=ANDR&grant_type=refresh_token&refresh_token=%s";

    /**
     * Constructs a login request using a refresh token to obtain a new access token and other details.
     *
     * @param refreshToken the refresh token to use for login and obtaining a new access token
     */
    public LoginWithRefreshTokenBakaRequest(final String refreshToken) {
        super("/api/login", String.format(REQUEST_BODY,
                URLEncoder.encode(refreshToken, StandardCharsets.UTF_8)));
    }

    /**
     * Deserializes the response JSON to obtain the access token, refresh token, and other details.
     * Since login is done via a refresh token, the username and password are not required and are set to null.
     *
     * @param jsonObject the JSON object containing the API response
     * @return a <code>LoginDataObject</code> containing the login details and the new access token
     */
    @Override
    public LoginDataObject deserialize(final JSONObject jsonObject) {
        final String accessToken = jsonObject.optString("access_token"); // Main thing we want is the access token
        // Since login is via refresh token, login and password fields are null
        return new LoginDataObject(null, null, accessToken);
    }
}

