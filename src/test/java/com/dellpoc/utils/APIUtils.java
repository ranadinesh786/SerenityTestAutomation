package com.dellpoc.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class APIUtils {

    private static final Logger log = LoggerFactory.getLogger(APIUtils.class);

    /**
     * Sets the base URI for the API requests.
     * @param baseURI The base URI to set.
     */
    public static void setBaseURI(String baseURI) {
        RestAssured.baseURI = baseURI;
    }

    /**
     * Generates common headers for API requests.
     * @param additionalHeaders Additional headers to include.
     * @return A map of headers.
     */
    public static Map<String, String> generateHeaders(Map<String, String> additionalHeaders) {
        // Add common headers here
        Map<String, String> headers = new java.util.HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");

        // Add additional headers
        if (additionalHeaders != null) {
            headers.putAll(additionalHeaders);
        }

        return headers;
    }

    /**
     * Sends a GET request to the specified endpoint.
     * @param endpoint The endpoint to send the GET request to.
     * @return The response of the GET request.
     */
    public static Response sendGetRequest(String endpoint) {
        return RestAssured.get(endpoint);
    }

    /**
     * Sends a POST request to the specified endpoint with the given body.
     * @param endpoint The endpoint to send the POST request to.
     * @param body The body of the POST request.
     * @return The response of the POST request.
     */
    public static Response sendPostRequest(String endpoint, String body) {
        return RestAssured.given().body(body).post(endpoint);
    }

    /**
     * Sends a GET request to the specified endpoint and logs the request and response.
     * @param endpoint The endpoint to send the GET request to.
     * @return The response of the GET request.
     */
    public static Response get(String endpoint) {
        log.info("Performing GET request to: " + endpoint);
        Serenity.recordReportData().withTitle("API GET Request").andContents("Performing GET request to: " + endpoint);
        Response response = RestAssured.get(endpoint);
        log.info("Response: " + response.asString());
        Serenity.recordReportData().withTitle("API GET Response").andContents("Response: " + response.asString());
        return response;
    }

    /**
     * Sends a POST request to the specified endpoint with the given body and logs the request and response.
     * @param endpoint The endpoint to send the POST request to.
     * @param body The body of the POST request.
     * @return The response of the POST request.
     */
    public static Response post(String endpoint, String body) {
        log.info("Performing POST request to: " + endpoint + " with body: " + body);
        Serenity.recordReportData().withTitle("API POST Request").andContents("Performing POST request to: " + endpoint + " with body: " + body);
        Response response = RestAssured.given().body(body).post(endpoint);
        log.info("Response: " + response.asString());
        Serenity.recordReportData().withTitle("API POST Response").andContents("Response: " + response.asString());
        return response;
    }

    /**
     * Sends a PUT request to the specified endpoint with the given body and logs the request and response.
     * @param endpoint The endpoint to send the PUT request to.
     * @param body The body of the PUT request.
     * @return The response of the PUT request.
     */
    public static Response put(String endpoint, String body) {
        log.info("Performing PUT request to: " + endpoint + " with body: " + body);
        Serenity.recordReportData().withTitle("API PUT Request").andContents("Performing PUT request to: " + endpoint + " with body: " + body);
        Response response = RestAssured.given().body(body).put(endpoint);
        log.info("Response: " + response.asString());
        Serenity.recordReportData().withTitle("API PUT Response").andContents("Response: " + response.asString());
        return response;
    }

    /**
     * Sends a PATCH request to the specified endpoint with the given body and logs the request and response.
     * @param endpoint The endpoint to send the PATCH request to.
     * @param body The body of the PATCH request.
     * @return The response of the PATCH request.
     */
    public static Response patch(String endpoint, String body) {
        log.info("Performing PATCH request to: " + endpoint + " with body: " + body);
        Serenity.recordReportData().withTitle("API PATCH Request").andContents("Performing PATCH request to: " + endpoint + " with body: " + body);
        Response response = RestAssured.given().body(body).patch(endpoint);
        log.info("Response: " + response.asString());
        Serenity.recordReportData().withTitle("API PATCH Response").andContents("Response: " + response.asString());
        return response;
    }

    /**
     * Sends a DELETE request to the specified endpoint and logs the request and response.
     * @param endpoint The endpoint to send the DELETE request to.
     * @return The response of the DELETE request.
     */
    public static Response delete(String endpoint) {
        log.info("Performing DELETE request to: " + endpoint);
        Serenity.recordReportData().withTitle("API DELETE Request").andContents("Performing DELETE request to: " + endpoint);
        Response response = RestAssured.delete(endpoint);
        log.info("Response: " + response.asString());
        Serenity.recordReportData().withTitle("API DELETE Response").andContents("Response: " + response.asString());
        return response;
    }

    /**
     * Sends a GET request to the specified endpoint with headers.
     * @param endpoint The endpoint to send the GET request to.
     * @param headers The headers to include in the GET request.
     * @return The response of the GET request.
     */
    public static Response getWithHeaders(String endpoint, Map<String, String> headers) {
        headers = generateHeaders(headers);
        log.info("Performing GET request to: " + endpoint + " with headers: " + headers);
        Serenity.recordReportData().withTitle("API GET Request with Headers").andContents("Performing GET request to: " + endpoint + " with headers: " + headers);
        Response response = RestAssured.given().headers(headers).get(endpoint);
        log.info("Response: " + response.asString());
        Serenity.recordReportData().withTitle("API GET Response with Headers").andContents("Response: " + response.asString());
        return response;
    }

    /**
     * Sends a POST request to the specified endpoint with headers and body.
     * @param endpoint The endpoint to send the POST request to.
     * @param headers The headers to include in the POST request.
     * @param body The body of the POST request.
     * @return The response of the POST request.
     */
    public static Response postWithHeaders(String endpoint, Map<String, String> headers, String body) {
        headers = generateHeaders(headers);
        log.info("Performing POST request to: " + endpoint + " with headers: " + headers + " and body: " + body);
        Serenity.recordReportData().withTitle("API POST Request with Headers").andContents("Performing POST request to: " + endpoint + " with headers: " + headers + " and body: " + body);
        Response response = RestAssured.given().headers(headers).body(body).post(endpoint);
        log.info("Response: " + response.asString());
        Serenity.recordReportData().withTitle("API POST Response with Headers").andContents("Response: " + response.asString());
        return response;
    }

    /**
     * Sends a PUT request to the specified endpoint with headers and body.
     * @param endpoint The endpoint to send the PUT request to.
     * @param headers The headers to include in the PUT request.
     * @param body The body of the PUT request.
     * @return The response of the PUT request.
     */
    public static Response putWithHeaders(String endpoint, Map<String, String> headers, String body) {
        headers = generateHeaders(headers);
        log.info("Performing PUT request to: " + endpoint + " with headers: " + headers + " and body: " + body);
        Serenity.recordReportData().withTitle("API PUT Request with Headers").andContents("Performing PUT request to: " + endpoint + " with headers: " + headers + " and body: " + body);
        Response response = RestAssured.given().headers(headers).body(body).put(endpoint);
        log.info("Response: " + response.asString());
        Serenity.recordReportData().withTitle("API PUT Response with Headers").andContents("Response: " + response.asString());
        return response;
    }

    /**
     * Sends a DELETE request to the specified endpoint with headers.
     * @param endpoint The endpoint to send the DELETE request to.
     * @param headers The headers to include in the DELETE request.
     * @return The response of the DELETE request.
     */
    public static Response deleteWithHeaders(String endpoint, Map<String, String> headers) {
        headers = generateHeaders(headers);
        log.info("Performing DELETE request to: " + endpoint + " with headers: " + headers);
        Serenity.recordReportData().withTitle("API DELETE Request with Headers").andContents("Performing DELETE request to: " + endpoint + " with headers: " + headers);
        Response response = RestAssured.given().headers(headers).delete(endpoint);
        log.info("Response: " + response.asString());
        Serenity.recordReportData().withTitle("API DELETE Response with Headers").andContents("Response: " + response.asString());
        return response;
    }

    /**
     * Sends a GET request to the specified endpoint with query parameters.
     * @param endpoint The endpoint to send the GET request to.
     * @param queryParams The query parameters to include in the GET request.
     * @return The response of the GET request.
     */
    public static Response getWithQueryParams(String endpoint, Map<String, String> queryParams) {
        log.info("Performing GET request to: " + endpoint + " with query parameters: " + queryParams);
        Serenity.recordReportData().withTitle("API GET Request with Query Parameters").andContents("Performing GET request to: " + endpoint + " with query parameters: " + queryParams);
        Response response = RestAssured.given().queryParams(queryParams).get(endpoint);
        log.info("Response: " + response.asString());
        Serenity.recordReportData().withTitle("API GET Response with Query Parameters").andContents("Response: " + response.asString());
        return response;
    }

    /**
     * Sends a POST request to the specified endpoint with form data.
     * @param endpoint The endpoint to send the POST request to.
     * @param formData The form data to include in the POST request.
     * @return The response of the POST request.
     */
    public static Response postWithFormData(String endpoint, Map<String, String> formData) {
        log.info("Performing POST request to: " + endpoint + " with form data: " + formData);
        Serenity.recordReportData().withTitle("API POST Request with Form Data").andContents("Performing POST request to: " + endpoint + " with form data: " + formData);
        Response response = RestAssured.given().formParams(formData).post(endpoint);
        log.info("Response: " + response.asString());
        Serenity.recordReportData().withTitle("API POST Response with Form Data").andContents("Response: " + response.asString());
        return response;
    }

    /**
     * Sends a GET request to the specified endpoint with basic authentication.
     * @param endpoint The endpoint to send the GET request to.
     * @param username The username for basic authentication.
     * @param password The password for basic authentication.
     * @return The response of the GET request.
     */
    public static Response getWithBasicAuth(String endpoint, String username, String password) {
        log.info("Performing GET request to: " + endpoint + " with basic authentication");
        Serenity.recordReportData().withTitle("API GET Request with Basic Auth").andContents("Performing GET request to: " + endpoint + " with basic authentication");
        Response response = RestAssured.given().auth().basic(username, password).get(endpoint);
        log.info("Response: " + response.asString());
        Serenity.recordReportData().withTitle("API GET Response with Basic Auth").andContents("Response: " + response.asString());
        return response;
    }

    /**
     * Sends a POST request to the specified endpoint with basic authentication and body.
     * @param endpoint The endpoint to send the POST request to.
     * @param username The username for basic authentication.
     * @param password The password for basic authentication.
     * @param body The body of the POST request.
     * @return The response of the POST request.
     */
    public static Response postWithBasicAuth(String endpoint, String username, String password, String body) {
        log.info("Performing POST request to: " + endpoint + " with basic authentication and body: " + body);
        Serenity.recordReportData().withTitle("API POST Request with Basic Auth").andContents("Performing POST request to: " + endpoint + " with basic authentication and body: " + body);
        Response response = RestAssured.given().auth().basic(username, password).body(body).post(endpoint);
        log.info("Response: " + response.asString());
        Serenity.recordReportData().withTitle("API POST Response with Basic Auth").andContents("Response: " + response.asString());
        return response;
    }

    /**
     * Sends a GET request to the specified endpoint with bearer token authentication.
     * @param endpoint The endpoint to send the GET request to.
     * @param token The bearer token for authentication.
     * @return The response of the GET request.
     */
    public static Response getWithBearerToken(String endpoint, String token) {
        log.info("Performing GET request to: " + endpoint + " with bearer token authentication");
        Serenity.recordReportData().withTitle("API GET Request with Bearer Token").andContents("Performing GET request to: " + endpoint + " with bearer token authentication");
        Response response = RestAssured.given().auth().oauth2(token).get(endpoint);
        log.info("Response: " + response.asString());
        Serenity.recordReportData().withTitle("API GET Response with Bearer Token").andContents("Response: " + response.asString());
        return response;
    }

    /**
     * Sends a POST request to the specified endpoint with bearer token authentication and body.
     * @param endpoint The endpoint to send the POST request to.
     * @param token The bearer token for authentication.
     * @param body The body of the POST request.
     * @return The response of the POST request.
     */
    public static Response postWithBearerToken(String endpoint, String token, String body) {
        log.info("Performing POST request to: " + endpoint + " with bearer token authentication and body: " + body);
        Serenity.recordReportData().withTitle("API POST Request with Bearer Token").andContents("Performing POST request to: " + endpoint + " with bearer token authentication and body: " + body);
        Response response = RestAssured.given().auth().oauth2(token).body(body).post(endpoint);
        log.info("Response: " + response.asString());
        Serenity.recordReportData().withTitle("API POST Response with Bearer Token").andContents("Response: " + response.asString());
        return response;
    }
}
