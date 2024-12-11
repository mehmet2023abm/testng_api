package apiTests.store;

import baseURL.BaseURL;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Post extends BaseURL {

    @Test(priority = 1)
    public void testPositive() {

        String requestBody = "{\n" +
                "  \"id\":987654321,\n" +
                "  \"petId\": 0,\n" +
                "  \"quantity\": 0,\n" +
                "  \"shipDate\": \"2024-12-10T05:52:38.181Z\",\n" +
                "  \"status\": \"placed\",\n" +
                "  \"complete\": true\n" +
                "}";

        Response response = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/store/order");

        // Response log
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());

        // Assertions
        // status code
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");
        // id
        int orderId = response.jsonPath().getInt("id");
        Assert.assertEquals(orderId, 987654321, "Order ID is incorrect");
    }

    @Test(priority = 2)
    public void testNegative() {

        // boş Id ile
        String requestBody = "{\n" +
                "  \"id\":,\n" +
                "  \"petId\": 0,\n" +
                "  \"quantity\": 0,\n" +
                "  \"shipDate\": \"2024-12-10T05:52:38.181Z\",\n" +
                "  \"status\": \"placed\",\n" +
                "  \"complete\": true\n" +
                "}";

        Response response = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/store/order");

        // Response log
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());

        // Assertions
        // status code (Invalid Order)
        Assert.assertEquals(response.getStatusCode(), 400);
    }
}
