package com.dellpoc.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class APIUtils {
    private static final Logger log = LoggerFactory.getLogger(APIUtils.class);

    public static void setBaseURI(String baseURI) {
        RestAssured.baseURI = baseURI;
    }

    public static Response sendGetRequest(String endpoint) {
        return RestAssured.get(endpoint);
    }

    public static Response sendPostRequest(String endpoint, String body) {
        return RestAssured.given().body(body).post(endpoint);
    }

    public static Response get(String endpoint) {
        log.info("Performing GET request to: " + endpoint);
        Serenity.recordReportData().withTitle("API GET Request").andContents("Performing GET request to: " + endpoint);
        Response response = RestAssured.get(endpoint);
        log.info("Response: " + response.asString());
        Serenity.recordReportData().withTitle("API GET Response").andContents("Response: " + response.asString());
        return response;
    }

    public static Response post(String endpoint, String body) {
        log.info("Performing POST request to: " + endpoint + " with body: " + body);
        Serenity.recordReportData().withTitle("API POST Request").andContents("Performing POST request to: " + endpoint + " with body: " + body);
        Response response = RestAssured.given().body(body).post(endpoint);
        log.info("Response: " + response.asString());
        Serenity.recordReportData().withTitle("API POST Response").andContents("Response: " + response.asString());
        return response;
    }

    public static Response put(String endpoint, String body) {
        log.info("Performing PUT request to: " + endpoint + " with body: " + body);
        Serenity.recordReportData().withTitle("API PUT Request").andContents("Performing PUT request to: " + endpoint + " with body: " + body);
        Response response = RestAssured.given().body(body).put(endpoint);
        log.info("Response: " + response.asString());
        Serenity.recordReportData().withTitle("API PUT Response").andContents("Response: " + response.asString());
        return response;
    }

    public static Response patch(String endpoint, String body) {
        log.info("Performing PATCH request to: " + endpoint + " with body: " + body);
        Serenity.recordReportData().withTitle("API PATCH Request").andContents("Performing PATCH request to: " + endpoint + " with body: " + body);
        Response response = RestAssured.given().body(body).patch(endpoint);
        log.info("Response: " + response.asString());
        Serenity.recordReportData().withTitle("API PATCH Response").andContents("Response: " + response.asString());
        return response;
    }

    public static Response delete(String endpoint) {
        log.info("Performing DELETE request to: " + endpoint);
        Serenity.recordReportData().withTitle("API DELETE Request").andContents("Performing DELETE request to: " + endpoint);
        Response response = RestAssured.delete(endpoint);
        log.info("Response: " + response.asString());
        Serenity.recordReportData().withTitle("API DELETE Response").andContents("Response: " + response.asString());
        return response;
    }
}