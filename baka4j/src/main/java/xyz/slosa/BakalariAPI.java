package xyz.slosa;

import com.fasterxml.jackson.core.JsonProcessingException;
import xyz.slosa.endpoints.http.request.types.AbstractBakaHttpGETRequest;
import xyz.slosa.endpoints.http.request.types.AbstractBakaHttpPOSTRequest;
import xyz.slosa.objects.BakaObject;

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

    /**
     * Sends an asynchronous HTTP GET request to the specified endpoint with the given access token.
     * The response is logged and deserialized into the provided `AbstractBakaHttpRequest` object.
     *
     * @param <T>         the type of the object that will hold the deserialized data
     * @param <R>         the type of the HTTP request extending `AbstractBakaHttpRequest`
     * @param httpRequest the HTTP request object containing the endpoint and deserialization logic
     * @param accessToken the access token for authorization header
     * @return a `CompletableFuture` that completes with the deserialized request object `R`
     */
    public <T extends BakaObject, R extends AbstractBakaHttpGETRequest<T>> CompletableFuture<R> request(final R httpRequest, final String accessToken) {
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
                // On success
                logger.accept("Successfully received response: " + response.body());
            } else {
                // On failure, log the status code and body of the response
                logger.accept("Response failed: " + response.statusCode());
                logger.accept("Response: " + response.body());
            }
            // Deserialize the response body into the request object
            try {
                httpRequest.fillData(httpRequest.deserialize(response.body()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return httpRequest; // Return the request
        }).exceptionally(ex -> {
            // Handle any exceptions that occurred during the request
            logger.accept("Request failed with exception: " + ex.getMessage());
            return httpRequest;
        });
    }

    /**
     * Sends a POST request asynchronously using the provided HTTP request object and returns a CompletableFuture of the request object.
     *
     * @param <T>         The type of the object that will hold the deserialized data.
     * @param <R>         The type of the HTTP request object which extends AbstractBakaHttpRequest.
     * @param httpRequest The HTTP request object that includes endpoint information and data deserialization method.
     * @return A CompletableFuture of the HTTP request object.
     */
    public <T extends BakaObject, R extends AbstractBakaHttpPOSTRequest<T>> CompletableFuture<R> requestPost(final R httpRequest) {
        assert logger != null : "Logger is NULL";

        // Prepare the login URL
        final String loginUrl = String.format("%s/%s", schoolURL, httpRequest.getEndpoint());

        // Prepare the form data for the request body
        final String requestBody = httpRequest.getRequestBody();

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
                // On success
                logger.accept("Successfully received response: " + response.body());
            } else {
                // On failure, log the status code and body of the response
                logger.accept("Response failed: " + response.statusCode());
                logger.accept("Response: " + response.body());
            }
            // Deserialize the response body into the request object
            try {
                httpRequest.fillData(httpRequest.deserialize(response.body()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return httpRequest; // Return the request
        }).exceptionally(ex -> {
            // Handle any exceptions that occurred during the request
            logger.accept("Request failed with exception: " + ex.getMessage());
            return httpRequest;
        });
    }
}