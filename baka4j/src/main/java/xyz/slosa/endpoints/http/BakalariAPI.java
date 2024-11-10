package xyz.slosa.endpoints.http;

import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
                    if (response.statusCode() == 200) {
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
}