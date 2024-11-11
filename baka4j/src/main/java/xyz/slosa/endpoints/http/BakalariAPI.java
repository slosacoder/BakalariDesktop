package xyz.slosa.endpoints.http;

import org.json.JSONObject;
import xyz.slosa.objects.BakaObject;

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
    private final HttpClient client = HttpClient.newHttpClient();

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

    public <T extends BakaObject, R extends AbstractBakaHttpRequest<T>> CompletableFuture<R> request(final R httpRequest, final String accessToken) {
        assert logger != null : "Logger is NULL";

        // Construct the full URL for the request
        final String requestUrl = String.format("%s/%s", schoolURL, httpRequest.getEndpoint());

        // Create the GET request with the necessary headers, including authorization
        final HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(requestUrl))
                .header("Content-Type", CONTENT_TYPE)
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();

        // Send the request asynchronously and handle the response
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(response -> {
            if (response.statusCode() == 200) { // OK
                // On success, deserialize the response body into the request object
                httpRequest.fillData(httpRequest.deserialize(new JSONObject(response.body())));
                logger.accept("Successfully received response: " + response.body());
            } else {
                // On failure, log the status code and body of the response
                logger.accept("Response failed: " + response.statusCode());
                logger.accept("Response: " + response.body());
            }
            return httpRequest;
        }).exceptionally(ex -> {
            // Handle any exceptions that occurred during the request
            logger.accept("Request failed with exception: " + ex.getMessage());
            return httpRequest;
        });
    }

    public <T extends BakaObject, R extends AbstractBakaHttpRequest<T>> CompletableFuture<R> requestPost(final R httpRequest) {
        assert logger != null : "Logger is NULL";

        // Prepare the login URL
        final String loginUrl = String.format("%s/%s", schoolURL, httpRequest.getEndpoint());

        // Prepare the form data for the request body (assuming this is for login)
        final String requestBody = String.format("client_id=ANDR&grant_type=password&username=%s&password=%s",
                URLEncoder.encode("skibidi", StandardCharsets.UTF_8),
                URLEncoder.encode("test", StandardCharsets.UTF_8));

        // Create the POST request
        final HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(loginUrl))
                .header("Content-Type", CONTENT_TYPE)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        // Send the request asynchronously and handle the response
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(response -> {
            if (response.statusCode() == 200) { // OK
                // On success, deserialize the response body into the request object
                httpRequest.fillData(httpRequest.deserialize(new JSONObject(response.body())));
                logger.accept("Successfully received response: " + response.body());
            } else {
                // On failure, log the status code and body of the response
                logger.accept("Response failed: " + response.statusCode());
                logger.accept("Response: " + response.body());
            }
            return httpRequest;
        }).exceptionally(ex -> {
            // Handle any exceptions that occurred during the request
            logger.accept("Request failed with exception: " + ex.getMessage());
            return httpRequest;
        });
    }
}