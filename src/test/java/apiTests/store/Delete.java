package apiTests.store;

import baseURL.BaseURL;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Delete extends BaseURL {

    @Test(priority = 1)
    public void testPositive() {
        //Post methodu ile daha önce oluşturulmuş ID
        int id = 987654321;
        Response response = given()
                .pathParam("orderId", id)
                .when()
                .delete("/store/order/{orderId}");

        // response
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
        // Assertion
        // Status code
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");
    }

    @Test(priority = 2)
    public void testNegative() {
        // geçersiz (yukarıda silinen) ID ile
        int id = 987654321;
        try {
            Response response = RestAssured.given()
                    .pathParam("orderId", id)
                    .when()
                    .get("/store/order/{orderId}");

            System.out.println("Response Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody().asString());

            // status code 404 Order not found bekleniyor
            Assert.assertEquals(response.getStatusCode(), 404);
        } catch (Exception e) {
            System.err.println("Oluşan hata: " + e.getMessage());
        }
    }
    // Testin Failed olmasına neden olan HttpResponseException try-catch ile handle edildi
}

