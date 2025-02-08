package apiTests.pet;

import baseURL.BaseURL;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;
import static io.restassured.RestAssured.given;

public class Post_Body extends BaseURL {

    @Test(groups = "positive")
    public void testPostPositive() {

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
                "  \"status\": \"available\"\n" +
                "}";

        Response response = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/pet");

        // response
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
        //assertions
        // status code
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");
        // name
        String petName = response.jsonPath().getString("name");
        Assert.assertEquals(petName, "doggie", "Pet name is incorrect");
        //category
        String categoryName = response.jsonPath().getString("category.name");
        Assert.assertEquals(categoryName, "string", "Category name is incorrect");
        //status
        String status = response.jsonPath().getString("status");
        Assert.assertEquals(status, "available", "Status is incorrect");
        // photo url
        List<String> photoUrls = response.jsonPath().getList("photoUrls");  //swagger dökümanında array olarak tanımlandığı için List kullanıldı
        Assert.assertFalse(photoUrls.isEmpty(), "Photo URLs list is empty");
        Assert.assertTrue(photoUrls.contains("string"), "Photo URL is incorrect");
        // tag
        List<String> tagNames = response.jsonPath().getList("tags.name");
        Assert.assertFalse(tagNames.isEmpty(), "Tags list is empty");
        Assert.assertTrue(tagNames.contains("string"), "Tag name is incorrect");
    }

    @Test(groups = "negative")
    public void testPostNegative() {
        //  'POST' yerine 'GET' metodu kullanımı ile negative test case
        String requestBody = "{\n" +
                "  \"id\": 9876543210,\n" +
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
                "  \"status\": \"available\"\n" +
                "}";
        try {
            Response response = RestAssured.given()
                    .contentType("application/json")
                    .body(requestBody)
                    .when()
                    .get("/pet");
            // response
            System.out.println("Response Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody().asString());
            // status code 405 bekleniyor (Method Not Allowed)
            Assert.assertEquals(response.getStatusCode(), 405);

        } catch (Exception e) {
            System.err.println("Oluşan hata: " + e.getMessage());
        }
    }
}

