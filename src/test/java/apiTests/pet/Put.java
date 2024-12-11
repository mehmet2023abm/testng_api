package apiTests.pet;

import baseURL.BaseURL;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class Put extends BaseURL {

    @Test(groups = "positive")
    public void testPutPositive() {
        // Sadece 'status' değeri değiştirildi
        String requestBody = "{\n" +
                "  \"id\": 987654321,\n" +
                "  \"category\": {\n" +
                "    \"id\": 10009,\n" +
                "    \"name\": \"string\"\n" +
                "  },\n" +
                "  \"name\": \"doggie\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 10007,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"sold\"\n" +
                "}";

        Response response = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .put("/pet");

        // response
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
        // assertions
        // status code
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");
        // name
        String petName = response.jsonPath().getString("name");
        Assert.assertEquals(petName, "doggie", "Pet name is incorrect");
        // category
        String categoryName = response.jsonPath().getString("category.name");
        Assert.assertEquals(categoryName, "string", "Category name is incorrect");
        // status
        String status = response.jsonPath().getString("status");
        Assert.assertEquals(status, "sold", "Status is incorrect");
        // photo URL
        List<String> photoUrls = response.jsonPath().getList("photoUrls");
        Assert.assertTrue(photoUrls.contains("string"), "Photo URL is incorrect");
        // tag
        int tagId = response.jsonPath().getInt("tags[0].id");
        Assert.assertEquals(tagId, 10007, "Tag ID is incorrect");
        String tagName = response.jsonPath().getString("tags[0].name");
        Assert.assertEquals(tagName, "string", "Tag name is incorrect");
    }

    @Test(groups = "negative")
    public void testPutNegative() {
        // ID'siz negatif test
        String requestBody = "{\n" +
                "  \"id\": \"\", \n" +
                "  \"category\": {\n" +
                "    \"id\": 10009,\n" +
                "    \"name\": \"string\"\n" +
                "  },\n" +
                "  \"name\": \"doggie\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 10007,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"sold\"\n" +
                "}";

        Response response = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .put("/pet");

        // response
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
        // status code assertions
        Assert.assertEquals(response.getStatusCode(), 400, "Status code is not 400");

        // HATA : Invalid ID supplied için 400 beklenirken 200 dönüyor
    }
}






