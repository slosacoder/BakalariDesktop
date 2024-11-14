# Baka4J

**Baka4J** is a simple, yet fast & reliable object wrapper for the bakalari-3 API.
This wrapper aims to provide a modern and efficient interface for interacting with the Bakalari system.

## Motivations

The existing Java wrapper for the [Bakalari API (Baka4J)](https://github.com/PanJohnny/Baka4J),
is no longer maintained and relies on outdated and unnecessary dependencies. Additionally,
the codebase of the existing wrapper does not align with my vision for simplicity and efficiency.

With **Baka4J**, I aim to create a more streamlined and dependency-light library to meet the needs of developers who work with Bakalari.

---

## Serialization Magic ✨

With Baka4J, the JSON serialization to Java objects is as seamless as slicing through warm butter. Imagine this: a JSON object arrives from the Bakalari API, wrapped up in all its key-value pair glory, and **Baka4J** performs its magic to unwrap it, breathing life into Java objects like they're born anew.

## Features

- Lightweight: Minimizes dependencies to keep the library fast and manageable.
- Reliable: Provides stable access to Bakalari’s API without frequent updates.
- Developer-friendly: The codebase is designed for clarity and ease of use, following modern Java best practices. With nice documentation and sample code you can use the wrapper with ease!

## Getting Started with Baka4J

To start using **Baka4J**, you’ll need to initialize the `BakalariAPI` with your school’s Bakalari API URL and a logger
if desired. The example below demonstrates a basic login flow, which retrieves an access token by authenticating with a
username and password.

#### Asynchronous Design with CompletableFuture

In **Baka4J**, all API requests are wrapped in `CompletableFuture` to enable easy, non-blocking asynchronous operations.
The Bakalari API often requires multiple interactions, such as logging in, retrieving data, and performing updates,
which can become time-consuming if done sequentially. Using `CompletableFuture` allows each request to be executed
independently in the background, freeing up the main thread and improving application responsiveness.

### Step 1: Add Baka4J as a Dependency

Make sure you’ve added **Baka4J** to your project. Using Maven or Gradle (WIP)

### Step 2: Sample Authentication

This example demonstrates how to initialize `BakalariAPI`, authenticate using a username and password, and retrieve the
access token asynchronously.

```java
// Initialize BakalariAPI with the school URL and your logger output (System.out)
BakalariAPI api = new BakalariAPI("https://yourschool.com", System.out::println);

// Create a login request with the user's credentials
LoginWithCredentialsBakaRequest loginRequest = new LoginWithCredentialsBakaRequest("username", "password");

// Send the login request asynchronously and handle the response
CompletableFuture<LoginWithCredentialsBakaRequest> futureResponse = api.requestPost(loginRequest);

// Process the response 
futureResponse.thenAccept(response -> {
    LoginDataObject loginData = response.getData();
    if (loginData != null) {
        System.out.println("Login successful! Access token: " + loginData.token());
    } else {
        System.out.println("Login failed.");
    }
});
