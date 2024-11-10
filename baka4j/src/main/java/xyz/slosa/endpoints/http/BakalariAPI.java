package xyz.slosa.endpoints.http;

import org.json.JSONObject;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * A class for interacting with the Bakalari API, which facilitates sending HTTP requests
 * to the Bakalari server and handling the responses.
 */
public class BakalariAPI {

    private final String schoolURL;
    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    private final Consumer<String> logger;
    private String accessToken;

    /**
     * Constructor for the BakalariAPI that includes logging functionality.
     *
     * @param schoolURL the base URL of the school Bakalari API
     * @param logger    a Consumer that logs messages (can be null for non-debug purposes)
     */
    public BakalariAPI(final String schoolURL, final Consumer<String> logger) {
        this.schoolURL = schoolURL;
        this.logger = logger;
    }

    /**
     * Constructor for the BakalariAPI that does not include logging functionality.
     *
     * @param schoolURL the base URL of the school Bakalari API
     */
    public BakalariAPI(final String schoolURL) {
        this.schoolURL = schoolURL;
        this.logger = null;
    }

    /**
     * Sends an HTTP request to the Bakalari API asynchronously and processes the response.
     *
     * @param httpRequest the request to be sent, extending AbstractBakaHttpRequest
     * @return a CompletableFuture containing the updated request object
     */
    public CompletableFuture<AbstractBakaHttpRequest> request(AbstractBakaHttpRequest httpRequest) {
        if (accessToken == null && logger != null)
            throw new NullPointerException("No access token provided! Cannot retrieve data!");

        final HttpClient client = HttpClient.newHttpClient();

        // Construct the full URL for the request
        final String requestUrl = String.format("%s/%s", schoolURL, httpRequest.getEndpoint());

        // Create the GET request with the necessary headers, including authorization
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestUrl))
                .header("Content-Type", CONTENT_TYPE)
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();

        // Send the request asynchronously and handle the response
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() == 200) { // OK
                        // On success, deserialize the response body into the request object
                        httpRequest.deserialize(new JSONObject(response.body()));
                        if (logger != null) {
                            logger.accept("Successfully received response: " + response.body());
                        }
                    } else {
                        // On failure, log the status code and body of the response
                        if (logger != null) {
                            logger.accept("Response failed: " + response.statusCode());
                            logger.accept("Response: " + response.body());
                        }
                    }
                    return httpRequest; // Return the httpRequest object, regardless of success or failure
                })
                .exceptionally(ex -> {
                    // Handle any exceptions that occurred during the request
                    if (logger != null) {
                        logger.accept("Request failed with exception: " + ex.getMessage());
                    }
                    return httpRequest; // Return the httpRequest object, even in case of an exception
                });
    }

    /**
     * Authenticates the user with the Bakalari API using username and password,
     * and retrieves the access and refresh tokens.
     *
     * @param username The username for the API.
     * @param password The password for the API.
     * @return A CompletableFuture that completes with a boolean indicating success (true) or failure (false).
     */
    public CompletableFuture<Boolean> authenticate(String username, String password) {
        final HttpClient client = HttpClient.newHttpClient();

        // Prepare the login URL
        final String loginUrl = String.format("%s/api/login", schoolURL);

        // Prepare the form data for the request body
        String requestBody = String.format("client_id=ANDR&grant_type=password&username=%s&password=%s",
                URLEncoder.encode(username, StandardCharsets.UTF_8),
                URLEncoder.encode(password, StandardCharsets.UTF_8));

        // Create the POST request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(loginUrl))
                .header("Content-Type", CONTENT_TYPE)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        // Send the request asynchronously
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() == 200) { // OK
                        // Parse the response JSON
                        JSONObject jsonResponse = new JSONObject(response.body());
                        accessToken = jsonResponse.optString("access_token", null); // Only access token needed

                        if (logger != null) {
                            logger.accept("Authentication successful. Access Token obtained: " + accessToken);
                        }
                        return true; // Authentication succeeded
                    } else {
                        if (logger != null) {
                            logger.accept("Authentication failed with status code: " + response.statusCode());
                            logger.accept("Response: " + response.body());
                        }
                        return false; // Authentication failed
                    }
                })
                .exceptionally(ex -> {
                    if (logger != null) {
                        logger.accept("Authentication request failed with exception: " + ex.getMessage());
                    }
                    return false; // Authentication failed due to exception
                });
    }
}